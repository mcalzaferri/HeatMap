package mcalzaferri.project.heatmap.client;

import java.io.IOException;

import mcalzaferri.geo.CapitalManager;
import mcalzaferri.geo.GeoLocation;
import mcalzaferri.project.heatmap.common.entities.TemperatureSensorData;

public abstract class TemperatureSensorClient extends SensorClient{

	public TemperatureSensorClient(String host, CapitalManager manager) throws IOException {
		super(host, manager);
	}
	
	@Override
	protected Object getSensorData(GeoLocation location) {
		return getTemperatureSensorData(location);
	}
	
	public abstract TemperatureSensorData getTemperatureSensorData(GeoLocation location);
	
	

}
