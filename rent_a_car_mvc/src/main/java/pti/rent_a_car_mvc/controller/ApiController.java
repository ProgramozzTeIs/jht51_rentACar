package pti.rent_a_car_mvc.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pti.rent_a_car_mvc.model.Car;
import pti.rent_a_car_mvc.model.dto.ReservationDto;
import pti.rent_a_car_mvc.model.dto.Rest_AvailableCars;
import pti.rent_a_car_mvc.model.dto.Rest_ConfirmationOfReservation;
import pti.rent_a_car_mvc.service.AppService;

@RestController
public class ApiController {

	private AppService service;
	
	@Autowired
	public ApiController(AppService service) {
		this.service = service;
	}
	
	
	@GetMapping("/api/freecars")
	public Rest_AvailableCars getAvailableCars(
				@RequestParam(name = "start_date") LocalDate startDate,
				@RequestParam(name = "end_date") LocalDate endDate
			) {
		
		List<Car> cars = service.getAvailableCars(startDate, endDate);
		Rest_AvailableCars rest_cars = new Rest_AvailableCars(startDate, endDate, cars);
		
		return rest_cars;
	}
	
	@PostMapping("/api/reservecar")
	public Rest_ConfirmationOfReservation finishReservation(
				@RequestParam(name="carid") int carId,
				@RequestParam(name="name") String userName,
				@RequestParam(name="email") String userEmail,
				@RequestParam(name="phone") String userPhone,
				@RequestParam(name="address") String userAddress,
				@RequestParam(name = "start_date") LocalDate startDate,
				@RequestParam(name = "end_date") LocalDate endDate
			) {
		
		ReservationDto reservation = new ReservationDto();
		reservation.setCarId(carId);
		reservation.setUsername(userName);
		reservation.setUserEmail(userEmail);
		reservation.setUserPhone(userPhone);
		reservation.setUserAddress(userAddress);
		reservation.setStartDate(startDate);
		reservation.setEndDate(endDate);
		
		String resultMessage = service.makeCarReservation(reservation);
		
		Rest_ConfirmationOfReservation confirmation = new Rest_ConfirmationOfReservation(resultMessage, reservation);
		
		return confirmation;
	}
}
