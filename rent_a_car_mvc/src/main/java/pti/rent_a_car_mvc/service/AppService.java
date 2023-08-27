package pti.rent_a_car_mvc.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pti.rent_a_car_mvc.exception.InvalidDateRangeException;
import pti.rent_a_car_mvc.exception.NoCarAvailableException;
import pti.rent_a_car_mvc.model.Car;
import pti.rent_a_car_mvc.model.Reservation;
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

}
