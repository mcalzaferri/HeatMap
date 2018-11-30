package mcalzaferri.project.heatmap.client;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;

import mcalzaferri.geo.CapitalManager;
import mcalzaferri.geo.GeoLocation;
import mcalzaferri.project.heatmap.common.entities.TemperatureSensorData;

public class RandomTemperatureSensorClient extends TemperatureSensorClient{

	public RandomTemperatureSensorClient(String host, CapitalManager manager) throws IOException {
		super(host, manager);
	}

	@Override
	public TemperatureSensorData getTemperatureSensorData(GeoLocation location) {
		return new TemperatureSensorData(getTemperature(), new Date());
	}
	
	private double getTemperature() {
		return (new SecureRandom().nextDouble()) * 70 - 30; //Generates temperatures between -30 and + 40 degrees
	}
}
