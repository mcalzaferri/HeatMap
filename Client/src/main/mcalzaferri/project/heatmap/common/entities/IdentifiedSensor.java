package mcalzaferri.project.heatmap.common.entities;

import mcalzaferri.geo.GeoLocation;

public class IdentifiedSensor extends NotIdentifiedSensor{
	private long id;
	
	public IdentifiedSensor(GeoLocation location, long id) {
		super(location);
		this.setId(id);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

}
