package pti.rent_a_car_mvc.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import pti.rent_a_car_mvc.model.Car;
import pti.rent_a_car_mvc.model.Reservation;
import pti.rent_a_car_mvc.model.dto.ReservationDto;
import pti.rent_a_car_mvc.service.AppService;

@Controller
public class AppController {
	
	private final AppService service;
	
	@Autowired
	public AppController(AppService service) {
		
		this.service = service;
	}
	
	@GetMapping("/")
	public String showIndex() {
		
		return "index";
	}
	
	@GetMapping("/freecars")
	public String showAvailableCars(
				@RequestParam(name = "start_date") LocalDate startDate,
				@RequestParam(name = "end_date") LocalDate endDate,
				Model model
			) {
		
		model.addAttribute("freeCars", service.getAvailableCars(startDate, endDate));
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		return "index";
	}
	
	@GetMapping("/startreservecar/{carid}")
	public String showReservationPage(
				@PathVariable(name = "carid") int carId, 
				@RequestParam(name = "start_date") LocalDate startDate,
				@RequestParam(name = "end_date") LocalDate endDate,
				Model model
			) {
		
		model.addAttribute("car", service.getCarById(carId));
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("reservation", new ReservationDto());
		
		return "reservecar";
	}
	
	@PostMapping("/finishreservecar")
	public String finishReservation(
				Model model,
				@ModelAttribute ReservationDto reservation
			) {
		
		String message = service.makeCarReservation(reservation);
		model.addAttribute("message", message);
		
		return "index.html";
	}
	
	@GetMapping("/admin")
	public String showAdminPage(Model model) {
		
		List<Reservation> allReservations = service.getAllReservations();
		List<Car> allCars = service.getAllCars();
		
		model.addAttribute("allReservations", allReservations);
		model.addAttribute("allCars", allCars);
		
		return "admin.html";
	}
	
	@PostMapping("/admin/addcar")
	public String addNewCar(
				@RequestParam(name = "type") String type,
				@RequestParam(name = "price") int price,
				@RequestParam(name = "availability") boolean availability,
				@RequestParam(name = "file") MultipartFile file,
				Model model
			) {
		
		String message = null;
		
		try {
			
			byte[] imageData = file.getBytes();
			message = service.addNewCar(type, price, availability, imageData);
			
		} catch (IOException e) {
			
			message = "Something went wrong while we tried to get the image file. Try again!";
		}
		
		List<Reservation> allReservations = service.getAllReservations();
		List<Car> allCars = service.getAllCars();
		
		model.addAttribute("allReservations", allReservations);
		model.addAttribute("allCars", allCars);
		model.addAttribute("message", message);
		
		return "admin";
	}
	
	@GetMapping("/admin/editcar")
	public String loadEditCarPage(
				Model model, 
				@RequestParam(name="carid") int carId
			) {
		
		Car car = service.getCarById(carId);
		
		model.addAttribute("car", car);
		
		return "editcar.html";
	}
	
	@PostMapping("/admin/editcar")
	public String editCarDetails(
				Model model,
				@RequestParam(name="carid") int carId,
				@RequestParam(name="type") String type,
				@RequestParam(name="price") int price,
				@RequestParam(name="availability") boolean availability,
				@RequestParam(name="file", required=false) MultipartFile file
			) {
		
		String targetPage = "admin.html";

		try {
			
			service.updateCarDetails(carId, type, price, availability, file);
			
			model.addAttribute("allReservations", service.getAllReservations());
			model.addAttribute("allCars", service.getAllCars());
			model.addAttribute("message", type + " (ID: " + carId + ") data updated successfully.");
		}
		catch(Exception e) {
			
			targetPage = "editcar.html";
			Car carToEdit = service.getCarById(carId);	
			model.addAttribute("car", carToEdit);
			model.addAttribute("message", "Error occured while processing your request: " + e.getMessage());
		}
		
		
		return targetPage;
	}
	
}
