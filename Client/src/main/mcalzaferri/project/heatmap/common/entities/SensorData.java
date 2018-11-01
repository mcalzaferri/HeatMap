package mcalzaferri.project.heatmap.common.entities;

import java.util.Date;

public class SensorData {

	private long unixTimestamp;
	
	public SensorData(Date timestamp) {
		setTimestamp(timestamp);
	}

	public Date getTimestamp() {
		return new Date(unixTimestamp);
	}

	public void setTimestamp(Date timestamp) {
		this.unixTimestamp = timestamp.getTime();
	}
	
	

}
