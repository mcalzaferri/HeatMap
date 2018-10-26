package mcalzaferri.project.heatmap.data;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;

import mcalzaferri.project.heatmap.common.entities.NotIdentifiedSensor;
import mcalzaferri.project.heatmap.common.entities.TemperatureSensorData;

public class SensorDataStoreWriter {
	private Datastore datastore;
	private DataStoreKeyFactory keyFactory;
	
	public SensorDataStoreWriter(Datastore datastore, DataStoreKeyFactory keyFactory) {
		this.datastore = datastore;
		this.keyFactory = keyFactory;
	}
	
	public long createSensor(NotIdentifiedSensor sensor) {
		
		return storeEntity(SensorEntityFactory.newBuilder().buildSensorEntity(sensor, keyFactory.createSensorKey()));
	}
	
	public long storeSensorData(TemperatureSensorData sensorData, long sensorId) {
		return storeEntity(SensorEntityFactory.newBuilder().buildTemperatureSensorDataEntity(sensorData, keyFactory.createSensorDataKey(sensorId)));
	}
	
	private long storeEntity(FullEntity<IncompleteKey> incEntity) {
		Entity entity = datastore.add(incEntity);
		return entity.getKey().getId();
	}
}
