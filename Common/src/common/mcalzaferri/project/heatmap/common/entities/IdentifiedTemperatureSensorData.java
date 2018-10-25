package mcalzaferri.project.heatmap.common.entities;

import java.util.Date;

public class IdentifiedTemperatureSensorData extends IdentifiedSensorData {
	private double temperature;
	
	public IdentifiedTemperatureSensorData(double temperature, Date timestamp, long sensorId) {
		super(timestamp, sensorId);
		setTemperature(temperature);
	}
	
	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

}
