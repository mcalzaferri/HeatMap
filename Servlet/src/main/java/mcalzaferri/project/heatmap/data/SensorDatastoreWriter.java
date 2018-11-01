package mcalzaferri.project.heatmap.data;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.gson.JsonObject;

import mcalzaferri.project.heatmap.data.config.EntityDefinition;
import mcalzaferri.project.heatmap.data.config.RessourceNotFoundException;

public class SensorDatastoreWriter {
	private HeatmapDatastore datastore;
	
	public SensorDatastoreWriter(HeatmapDatastore datastore) {
		this.datastore = datastore;
	}
	
	public long storeJsonObject(RequestedRessource res, JsonObject jsonObj) throws RessourceNotFoundException {
		return storeJsonObject(res, datastore.getConfigReader().getEntityDefinition(res), jsonObj);
	}
	
	private long storeJsonObject(RequestedRessource res, EntityDefinition def, JsonObject jsonObject) {
		return storeJsonObject(datastore.getKeyFactory().createRessourceKey(res), def, jsonObject);
	}
	
	private long storeJsonObject(IncompleteKey key, EntityDefinition def, JsonObject jsonObject) {
		return storeEntity(datastore.getEntityFactory().buildFromJsonObject(key, def, jsonObject));
	}
	
	private long storeEntity(FullEntity<IncompleteKey> incEntity) {
		Entity entity = datastore.getDatastore().add(incEntity);
		return entity.getKey().getId();
	}
}
