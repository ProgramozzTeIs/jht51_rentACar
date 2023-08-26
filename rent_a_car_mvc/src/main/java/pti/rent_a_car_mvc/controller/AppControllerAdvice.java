package pti.rent_a_car_mvc.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pti.rent_a_car_mvc.exception.InvalidDateRangeException;
import pti.rent_a_car_mvc.exception.NoCarAvailableException;

@ControllerAdvice
public class AppControllerAdvice {

	@ExceptionHandler(InvalidDateRangeException.class)
	public String invalidDateRangeExceptionHandler(InvalidDateRangeException e, Model model) {
		
		model.addAttribute("message", e.getMessage());
		
		return "index";
	}
	
	@ExceptionHandler(NoCarAvailableException.class)
	public String noCarAvailableExceptionHandler(NoCarAvailableException e, Model model) {
		
		model.addAttribute("message", e.getMessage());
		
		return "index";
	}
}
