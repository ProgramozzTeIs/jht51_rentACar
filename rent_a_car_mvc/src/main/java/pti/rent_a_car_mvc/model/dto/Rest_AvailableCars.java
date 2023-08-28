package pti.rent_a_car_mvc.model.dto;

import java.time.LocalDate;
import java.util.List;

import pti.rent_a_car_mvc.model.Car;

public class Rest_AvailableCars {
	
	private LocalDate statDate;
	private LocalDate endDate;
	private List<Car> availableCars;
	
	
	public Rest_AvailableCars(LocalDate statDate, LocalDate endDate, List<Car> availableCars) {
		super();
		this.statDate = statDate;
		this.endDate = endDate;
		this.availableCars = availableCars;
	}


	public LocalDate getStatDate() {
		return statDate;
	}
	
	public void setStatDate(LocalDate statDate) {
		this.statDate = statDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public List<Car> getAvailableCars() {
		return availableCars;
	}

	public void setAvailableCars(List<Car> availableCars) {
		this.availableCars = availableCars;
	}


	@Override
	public String toString() {
		return "Rest_AvailableCars [statDate=" + statDate + ", endDate=" + endDate + ", availableCars=" + availableCars
				+ "]";
	}
	

}
