package mcalzaferri.project.heatmap.data;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.LatLng;

import mcalzaferri.project.heatmap.common.entities.*;

public class SensorEntityFactory {
	public static final String LOCATION = "location";
	public static final String SENSORID = "sensorid";
	public static final String TIMESTAMP = "timestamp";
	public static final String TEMPERATURE = "temperature";
	
	private SensorEntityFactory() {
		
	}
	
	public static SensorEntityFactory newBuilder() {
		return new SensorEntityFactory();
	}
	
	public FullEntity<IncompleteKey> buildSensorEntity(NotIdentifiedSensor sensor, IncompleteKey key) {
		LatLng latlng = LatLng.of(sensor.getLocation().getLatitude(), sensor.getLocation().getLatitude());
		return Entity.newBuilder(key)
				.set(LOCATION, latlng)
				.build();
	}
	
	public FullEntity<IncompleteKey> buildTemperatureSensorDataEntity(TemperatureSensorData data, IncompleteKey key){
		Timestamp timestamp = Timestamp.of(data.getTimestamp());
		return Entity.newBuilder(key)
				.set(TIMESTAMP, timestamp)
				.set(TEMPERATURE, data.getTemperature())
				.build();
	}
	
}
