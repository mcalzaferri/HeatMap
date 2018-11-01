package mcalzaferri.project.heatmap.data.config;

public class TooManyFieldsException extends VerificationException{
	private static final long serialVersionUID = 4661624807206127116L;
	
	public TooManyFieldsException() {
		super();
	}

	public TooManyFieldsException(String message) {
		super(message);
	}
	
	public TooManyFieldsException(Throwable cause) {
		super(cause);
	}
	
	public TooManyFieldsException(String message, Throwable cause) {
		super(message, cause);
	}
}
