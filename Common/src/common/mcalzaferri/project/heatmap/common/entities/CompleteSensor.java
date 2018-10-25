package mcalzaferri.project.heatmap.common.entities;

import mcalzaferri.geo.GeoLocation;

public class CompleteSensor extends IdentifiedSensor{
	
	private SensorData[] data;

	public CompleteSensor(GeoLocation location, long id, SensorData[] data) {
		super(location, id);
		this.setData(data);
	}

	public SensorData[] getData() {
		return data;
	}

	public void setData(SensorData[] data) {
		this.data = data;
	}

}
