package pti.rent_a_car_mvc.model.dto;

import java.time.LocalDate;

public class CarReservedTimesDto {

	private int carId;
	private int reservationId;
	private LocalDate startDate;
	private LocalDate endDate;
	
	
	public CarReservedTimesDto(int carId, int reservationId, LocalDate startDate, LocalDate endDate) {
		super();
		this.carId = carId;
		this.reservationId = reservationId;
		this.startDate = startDate;
		this.endDate = endDate;
	}


	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}


	@Override
	public String toString() {
		return "CarReservedTimesDto [carId=" + carId + ", reservationId=" + reservationId + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}
	
}
