package pti.rent_a_car_mvc.model.dto;

public class Rest_ConfirmationOfReservation {
	
	private String reservationResultMessage;
	private ReservationDto reservationDetails;
	
	
	public Rest_ConfirmationOfReservation(String reservationResultMessage, ReservationDto reservationDetails) {
		super();
		this.reservationResultMessage = reservationResultMessage;
		this.reservationDetails = reservationDetails;
	}


	public String getReservationResultMessage() {
		return reservationResultMessage;
	}

	public void setReservationResultMessage(String reservationResultMessage) {
		this.reservationResultMessage = reservationResultMessage;
	}

	public ReservationDto getReservationDetails() {
		return reservationDetails;
	}

	public void setReservationDetails(ReservationDto reservationDetails) {
		this.reservationDetails = reservationDetails;
	}


	@Override
	public String toString() {
		return "Rest_ConfirmationOfReservation [reservationResultMessage=" + reservationResultMessage
				+ ", reservationDetails=" + reservationDetails + "]";
	}
	
	
}
