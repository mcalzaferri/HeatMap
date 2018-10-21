package mcalzaferri.project.heatmap.data;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.cloud.datastore.*;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

import mcalzaferri.project.heatmap.data.objects.*;

public class TemperatureDataStore {
	private Datastore datastore;
	private KeyFactory temperatureDataKeyFactory;
	private KeyFactory sensorKeyFactory;
	
	private static final String TEMPERATUREDATA = "TemperatureData";
	private static final String SENSOR = "Sensor";
	private static TemperatureDataStore instance;
	
	public static TemperatureDataStore getInstance() {
		if(instance == null) {
			instance = new TemperatureDataStore();
		}
		return instance;
	}
	
	private TemperatureDataStore() {
		datastore = DatastoreOptions.newBuilder().setHost("http://localhost:8081").setProjectId("heatmap-219120").build().getService();
		//datastore = DatastoreOptions.getDefaultInstance().getService();
		temperatureDataKeyFactory = datastore.newKeyFactory().setKind(TEMPERATUREDATA);
		sensorKeyFactory = datastore.newKeyFactory().setKind(SENSOR);
	}
	
	public long createSensor(TemperatureSensor sensor) {
		return storeEntityObject(sensor, sensorKeyFactory);
	}
	
	public long storeData(TemperatureData data) {
		return storeEntityObject(data, temperatureDataKeyFactory);
	}
	
	private long storeEntityObject(IEntityObject object, KeyFactory keyFactory) {
		IncompleteKey key = keyFactory.newKey();
		FullEntity<IncompleteKey> incEntity = object.getFullEntity(key);
		Entity entity = datastore.add(incEntity);
		object.setId(entity.getKey().getId());
		return entity.getKey().getId();
	}
	
	public TemperatureSensor readSensor(long id) {
		Entity sensor = datastore.get(sensorKeyFactory.newKey(id));
		if(sensor != null) {
			return new TemperatureSensor(sensor);
		}else {
			throw new NoSuchElementException("No sensor with id " + id + " found");
		}
	}
	
	public boolean containsSensor(long id) {
		Entity sensor = datastore.get(sensorKeyFactory.newKey(id));
		return sensor != null;
	}
	
	public List<TemperatureSensor> listSensors(){
		Query<Entity> query = Query.newEntityQueryBuilder()
				.setKind(SENSOR)
				.build();
		QueryResults<Entity> resultList = datastore.run(query);
		List<TemperatureSensor> sensorList = new ArrayList<>();
		while(resultList.hasNext()) {
			sensorList.add(new TemperatureSensor(resultList.next()));
		}
		return sensorList;
	}
	
	public List<TemperatureData> listSensorData(long sensorId){
		Query<Entity> query = Query.newEntityQueryBuilder()
				.setKind(TEMPERATUREDATA)
				.setFilter(PropertyFilter.eq(TemperatureData.SENSORID, sensorId))
				.build();
		QueryResults<Entity> resultList = datastore.run(query);
		List<TemperatureData> dataList = new ArrayList<>();
		while(resultList.hasNext()) {
			dataList.add(new TemperatureData(resultList.next()));
		}
		return dataList;
	}
	
}
