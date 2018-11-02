package mcalzaferri.project.heatmap.common.entities;

import java.util.Date;

public class SensorData {

	private long timestamp;
	
	public SensorData(Date timestamp) {
		setTimestamp(timestamp);
	}

	public Date getTimestamp() {
		return new Date(timestamp);
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp.getTime();
	}
	
	

}
