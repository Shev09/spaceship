package br.com.elo7.spaceshipmanager.exception;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_NOT_FOUND_MSG = "Resource not found: ";

    public ResourceNotFoundException(String msg) {
        super(RESOURCE_NOT_FOUND_MSG + msg);
    }

	public ResourceNotFoundException(String msg, Throwable e) {
		super(RESOURCE_NOT_FOUND_MSG + msg, e);
	}

}
