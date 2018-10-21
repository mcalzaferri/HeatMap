package mcalzaferri.project.heatmap.data.objects;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;

public class TemperatureData implements IEntityObject{
	private long sensorId;
	private long id;
	private Timestamp timestamp;
	private double temperature;
	public static final String SENSORID = "sensorId";
	public static final String TIMESTAMP = "timestamp";
	public static final String TEMPERATURE = "temperature";
	
	public TemperatureData(long sensorId, Timestamp timestamp, double temperature) {
		this.setSensorId(sensorId);
		this.setTimestamp(timestamp);
		this.setTemperature(temperature);
	}
	
	public TemperatureData(Entity entity) {
		this.setSensorId(entity.getLong(SENSORID));
		this.setTimestamp(entity.getTimestamp(TIMESTAMP));
		this.setTemperature(entity.getDouble(TEMPERATURE));
		this.setId(entity.getKey().getId());
	}
	
	public long getSensorId() {
		return sensorId;
	}

	public void setSensorId(long sensorId) {
		this.sensorId = sensorId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	@Override
	public FullEntity<IncompleteKey> getFullEntity(IncompleteKey key) {
		return Entity.newBuilder(key)
				.set(SENSORID, getSensorId())
				.set(TIMESTAMP, getTimestamp())
				.set(TEMPERATURE, getTemperature())
				.build();
	}

	public long getId() {
		return id;
	}
	
	@Override
	public void setId(long id) {
		this.id = id;
		
	}
}
