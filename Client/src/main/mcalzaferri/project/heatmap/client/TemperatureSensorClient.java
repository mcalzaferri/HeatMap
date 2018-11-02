package mcalzaferri.project.heatmap.client;

import java.io.IOException;
import java.util.Date;

import mcalzaferri.geo.GeoLocation;
import mcalzaferri.project.heatmap.common.entities.TemperatureSensorData;

public abstract class TemperatureSensorClient extends SensorClient{

	public TemperatureSensorClient(long defaultId, String host, GeoLocation location) throws IOException {
		super(defaultId, host, location);
	}
	
	public TemperatureSensorClient(String host, GeoLocation location) throws IOException {
		super(host, location);
	}
	
	@Override
	protected Object getSensorData() {
		return new TemperatureSensorData(getTemperature(), new Date());
	}
	
	public abstract double getTemperature();
	

}
