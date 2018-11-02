package mcalzaferri.project.heatmap.client;

import java.io.IOException;
import java.security.SecureRandom;

import mcalzaferri.geo.GeoLocation;

public class RandomTemperatureSensorClient extends TemperatureSensorClient{

	public RandomTemperatureSensorClient(long defaultId, String host, GeoLocation location) throws IOException {
		super(defaultId, host, location);
		// TODO Auto-generated constructor stub
	}
	
	public RandomTemperatureSensorClient(String host, GeoLocation location) throws IOException {
		super(host, location);
	}
	
	public double getTemperature() {
		return (new SecureRandom().nextDouble()) * 70 - 30; //Generates temperatures between -30 and + 40 degrees
	}
}
