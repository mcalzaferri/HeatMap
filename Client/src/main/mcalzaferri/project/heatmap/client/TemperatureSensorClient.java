package mcalzaferri.project.heatmap.client;

import java.io.IOException;
import java.util.Date;

import mcalzaferri.geo.GeoLocation;
import mcalzaferri.net.http.HttpPostClient;
import mcalzaferri.project.heatmap.common.entities.IdentifiedTemperatureSensorData;

public abstract class TemperatureSensorClient extends SensorClient{

	public TemperatureSensorClient(long defaultId, HttpPostClient client, GeoLocation location) throws IOException {
		super(defaultId, client, location);
	}
	
	public TemperatureSensorClient(HttpPostClient client, GeoLocation location) throws IOException {
		super(client, location);
	}
	
	@Override
	protected Object getSensorData() {
		return new IdentifiedTemperatureSensorData(getTemperature(), new Date(), getId());
	}
	
	public abstract double getTemperature();
	

}
