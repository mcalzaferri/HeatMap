package mcalzaferri.project.heatmap.data.config;

public class FieldNotFoundException extends VerificationException {
	private static final long serialVersionUID = 6211779792015131417L;
	
	public FieldNotFoundException() {
		super();
	}

	public FieldNotFoundException(String message) {
		super(message);
	}
	
	public FieldNotFoundException(Throwable cause) {
		super(cause);
	}
	
	public FieldNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}
