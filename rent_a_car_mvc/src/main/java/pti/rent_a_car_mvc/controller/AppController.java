package pti.rent_a_car_mvc.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		
		return "reservecar";
	}
	
	@PostMapping("/finishreservecar")
	public String reserveCar() {
		
		return null;
	}
	
}
