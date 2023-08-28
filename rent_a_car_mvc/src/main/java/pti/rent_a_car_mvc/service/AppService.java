package pti.rent_a_car_mvc.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pti.rent_a_car_mvc.exception.InvalidDateRangeException;
import pti.rent_a_car_mvc.exception.NoCarAvailableException;
import pti.rent_a_car_mvc.model.Car;
import pti.rent_a_car_mvc.model.Reservation;
import pti.rent_a_car_mvc.model.dto.CarReservedTimesDto;
import pti.rent_a_car_mvc.model.dto.ReservationDto;
import pti.rent_a_car_mvc.persistence.Database;

@Service
public class AppService {
	
	private final Database db;
	
	@Autowired
	public AppService(Database db) {
		
		this.db = db;
	}
	
	public List<Car> getAvailableCars(LocalDate startDate, LocalDate endDate) 
			throws InvalidDateRangeException, NoCarAvailableException {
		
		if(startDate.isBefore(LocalDate.now())) {
			
			throw new InvalidDateRangeException("Are you a time-traveller or what?!");
		}
		
		if(startDate.isAfter(endDate)) {
			
			throw new InvalidDateRangeException("The start date must be before the end date!");
		}
		
		List<Car> availableCars = db.findAvailableCars(startDate, endDate);
		
		if(availableCars.isEmpty()) {
			
			throw new NoCarAvailableException();
		}
		
		return availableCars;
	}

	public Car getCarById(int carId) {
		
		Car resultCar = db.findCarById(carId);
		
		return resultCar;
	}

	
	public String makeCarReservation(ReservationDto reservation) {

		String resultResponse = null;
		
		Reservation reservDbObject = new Reservation();
		reservDbObject.setId(0);
		reservDbObject.setUser_name(reservation.getUsername());
		reservDbObject.setUser_email(reservation.getUserEmail());
		reservDbObject.setUser_phone(reservation.getUserPhone());
		reservDbObject.setUser_address(reservation.getUserAddress());
		reservDbObject.setStart_date(reservation.getStartDate());
		reservDbObject.setEnd_date(reservation.getEndDate());

		Car car = new Car();
		car.setId(reservation.getCarId());
		reservDbObject.setCar(car);
		
		try {
			
			db.persistReservation(reservDbObject);
			resultResponse = "Reservation successfully stored.";
		}
		catch(Exception e) {
			
			resultResponse = "Error occured while processing your request: " + e.getMessage();
		}
		
		
		return resultResponse;
	}

	public String addNewCar(String type, int price, boolean availability, byte[] imageData) {
		
		Car newCar = new Car(0, type, availability, price, imageData);
		String message = "Adding the new car failed. Try again!";
		
		if(db.peristCar(newCar)) {
			
			message = "New car added to database successfully.";
		}
		
		return message;
	}
	
	
	public List<Reservation> getAllReservations() {
		
		List<Reservation> reservations = db.getAllReservations();
		
		return reservations;
	}

	
	public List<Car> getAllCars() {

		List<Car> cars = db.getAllCars();
		
		return cars;
	}
	

	public void updateCarDetails(int carId, String type, int price, boolean availability, MultipartFile file) throws IOException {
				
		Car car = new Car();
		car.setId(carId);
		car.setType(type);
		car.setPrice(price);
		car.setAvailable(availability);
		
		byte[] imageData = file.getBytes();

		if(imageData.length > 0) {

			car.encodeRawImageToBase64(imageData);	
			db.mergeCar(car);
		}
		else {
			
			db.mergeCarWithoutBlob(car);
		}
		
		if(car.isAvailable() == false) {
			
			List<CarReservedTimesDto> openReservations = db.getOpenReservationsByCarId(carId);
			Random random = new Random();
			
			for(CarReservedTimesDto reservation : openReservations) {
				
				List<Car> alternatvesAvailable = this.getAvailableCars(reservation.getStartDate(), reservation.getEndDate());
				
				if(alternatvesAvailable.size() > 0) {
					
					Car carAlternative = alternatvesAvailable.get( random.nextInt(alternatvesAvailable.size()) );
					
					db.updateReservationsCarByResId(reservation.getReservationId(), carAlternative.getId());
				}
				else {
					
					db.deleteReservationById(reservation.getReservationId());
				}
			}
		}
		
	}

}
