package co.codehaven.seco.exceptions;

public class ManholeAlreadyInsertedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userEmail;

	public ManholeAlreadyInsertedException(){
		super(ExceptionMessages.MANHOLE_NOT_FOUND);
	}

	public String getPedroInfoEmail() {
		return userEmail;
	}

	public void setPedroInfoEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
}
