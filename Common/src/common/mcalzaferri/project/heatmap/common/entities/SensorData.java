package mcalzaferri.project.heatmap.common.entities;

import java.util.Date;

public class SensorData {
	private double temperature;
	private long unixTimestamp;
	
	public SensorData(double temperature, Date timestamp) {
		setTemperature(temperature);
		setTimestamp(timestamp);
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public Date getTimestamp() {
		return new Date(unixTimestamp);
	}

	public void setTimestamp(Date timestamp) {
		this.unixTimestamp = timestamp.getTime();
	}
	
	

}
