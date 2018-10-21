package mcalzaferri.project.heatmap.data.objects;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.LatLng;

public class TemperatureSensor implements IEntityObject{
	private LatLng location;
	private long id;
	
	public static final String LOCATION = "location";
	
	public TemperatureSensor(LatLng location) {
		setLocation(location);
	}
	
	public TemperatureSensor(Entity entity) {
		setLocation(entity.getLatLng(LOCATION));
		setId(entity.getKey().getId());
	}
	
	public LatLng getLocation() {
		return location;
	}

	public void setLocation(LatLng location) {
		this.location = location;
	}

	@Override
	public FullEntity<IncompleteKey> getFullEntity(IncompleteKey key) {
		return Entity.newBuilder(key)
				.set(LOCATION, getLocation())
				.build();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
