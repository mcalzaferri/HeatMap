package mcalzaferri.project.heatmap.client;

import java.io.IOException;
import java.util.Date;

import mcalzaferri.geo.CapitalManager;
import mcalzaferri.project.heatmap.common.entities.TemperatureSensorData;

public abstract class TemperatureSensorClient extends SensorClient{

	public TemperatureSensorClient(String host, CapitalManager manager) throws IOException {
		super(host, manager);
	}
	
	@Override
	protected Object getSensorData() {
		return new TemperatureSensorData(getTemperature(), new Date());
	}
	
	public abstract double getTemperature();
	

}
