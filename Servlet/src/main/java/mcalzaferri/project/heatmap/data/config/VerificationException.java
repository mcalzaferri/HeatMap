package mcalzaferri.project.heatmap.data.config;

public class VerificationException extends Exception{
	private static final long serialVersionUID = -2726334449322159320L;
	
	public VerificationException() {
		super();
	}

	public VerificationException(String message) {
		super(message);
	}
	
	public VerificationException(Throwable cause) {
		super(cause);
	}
	
	public VerificationException(String message, Throwable cause) {
		super(message, cause);
	}
}
