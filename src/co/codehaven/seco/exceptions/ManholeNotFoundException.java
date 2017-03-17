package co.codehaven.seco.exceptions;

public class ManholeNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer manholeId;

	public ManholeNotFoundException(){
		super(ExceptionMessages.MANHOLE_NOT_FOUND);
	}

	public Integer getPedroInfoEmail() {
		return manholeId;
	}

	public void setPedroInfoEmail(Integer manholeId) {
		this.manholeId = manholeId;
	}
	
}
