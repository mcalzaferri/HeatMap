package mcalzaferri.project.heatmap.common.entities;

import java.util.Date;

public class IdentifiedSensorData extends SensorData{
	private long sensorId;
	public IdentifiedSensorData(Date timestamp, long sensorId) {
		super(timestamp);
		this.setSensorId(sensorId);
	}
	public long getSensorId() {
		return sensorId;
	}
	public void setSensorId(long sensorId) {
		this.sensorId = sensorId;
	}

}
