package mcalzaferri.project.heatmap.client;

import java.io.IOException;
import java.security.SecureRandom;

import mcalzaferri.geo.GeoLocation;
import mcalzaferri.net.http.HttpPostClient;

public class RandomTemperatureSensorClient extends TemperatureSensorClient{

	public RandomTemperatureSensorClient(long defaultId, HttpPostClient client, GeoLocation location) throws IOException {
		super(defaultId, client, location);
		// TODO Auto-generated constructor stub
	}
	
	public RandomTemperatureSensorClient(HttpPostClient client, GeoLocation location) throws IOException {
		super(client, location);
	}
	
	public double getTemperature() {
		return (new SecureRandom().nextDouble()) * 70 - 30; //Generates temperatures between -30 and + 40 degrees
	}
}
