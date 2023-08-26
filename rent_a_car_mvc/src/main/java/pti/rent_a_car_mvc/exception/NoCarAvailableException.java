package pti.rent_a_car_mvc.exception;

public class NoCarAvailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NoCarAvailableException() {
		
		super("Sorry, we don't have any car to rent in this time period.");
	}

}
