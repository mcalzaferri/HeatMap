package mcalzaferri.project.heatmap.client;

import java.io.IOException;

import mcalzaferri.geo.GeoLocation;
import mcalzaferri.net.http.HttpPostClient;

public abstract class TemperatureSensorClient extends SensorClient{

	public TemperatureSensorClient(long defaultId, HttpPostClient client, GeoLocation location) throws IOException {
		super(defaultId, client, location);
		// TODO Auto-generated constructor stub
	}
	
	public TemperatureSensorClient(HttpPostClient client, GeoLocation location) throws IOException {
		super(client, location);
	}
	
	@Override
	protected Object getSensorData() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public abstract double getTemperature();
	

}
