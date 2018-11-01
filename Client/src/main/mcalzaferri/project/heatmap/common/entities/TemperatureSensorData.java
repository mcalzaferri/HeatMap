package mcalzaferri.project.heatmap.common.entities;

import java.util.Date;

public class TemperatureSensorData extends SensorData{
	private double temperature;

	public TemperatureSensorData(double temperature, Date timestamp) {
		super(timestamp);
		setTemperature(temperature);
	}
	
	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

}
