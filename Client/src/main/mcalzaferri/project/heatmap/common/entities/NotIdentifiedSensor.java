package mcalzaferri.project.heatmap.common.entities;

import mcalzaferri.geo.CountryCapital;
import mcalzaferri.geo.GeoLocation;

public class NotIdentifiedSensor {
	private GeoLocation location;
	
	public NotIdentifiedSensor(CountryCapital capital) {
		this(capital.getLocation());
	}
	
	public NotIdentifiedSensor(GeoLocation location) {
		this.location = location;
	}

	public GeoLocation getLocation() {
		return location;
	}

	public void setLocation(GeoLocation location) {
		this.location = location;
	}
}
