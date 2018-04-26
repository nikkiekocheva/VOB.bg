package util.exceptions;

public class InvalidUserDataException extends Exception {

	private String reason;
	
	public InvalidUserDataException(String reason)  {
		this.reason = reason;
	}
	
	@Override
	public String getMessage() {
		return "Invalid credentials: " + reason;
	}
	
}
