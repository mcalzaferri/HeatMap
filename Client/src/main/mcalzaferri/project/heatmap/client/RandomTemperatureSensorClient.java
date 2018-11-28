package mcalzaferri.project.heatmap.client;

import java.io.IOException;
import java.security.SecureRandom;

import mcalzaferri.geo.CapitalManager;

public class RandomTemperatureSensorClient extends TemperatureSensorClient{

	public RandomTemperatureSensorClient(String host, CapitalManager manager) throws IOException {
		super(host, manager);
	}
	
	public double getTemperature() {
		return (new SecureRandom().nextDouble()) * 70 - 30; //Generates temperatures between -30 and + 40 degrees
	}
}
