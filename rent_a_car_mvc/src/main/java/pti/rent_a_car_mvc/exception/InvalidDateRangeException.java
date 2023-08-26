package pti.rent_a_car_mvc.exception;

public class InvalidDateRangeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidDateRangeException(String message) {
		
		super(message);
	}
}
