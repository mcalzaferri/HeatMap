package mcalzaferri.project.heatmap.data.config;

public class RessourceNotFoundException extends Exception {
	private static final long serialVersionUID = 1666988151741180451L;

	public RessourceNotFoundException() {
		super();
	}

	public RessourceNotFoundException(String message) {
		super(message);
	}
	
	public RessourceNotFoundException(Throwable cause) {
		super(cause);
	}
	
	public RessourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
