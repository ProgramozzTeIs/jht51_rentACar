package pti.rent_a_car_mvc.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pti.rent_a_car_mvc.exception.InvalidDateRangeException;
import pti.rent_a_car_mvc.exception.NoCarAvailableException;
import pti.rent_a_car_mvc.model.Car;
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

}
