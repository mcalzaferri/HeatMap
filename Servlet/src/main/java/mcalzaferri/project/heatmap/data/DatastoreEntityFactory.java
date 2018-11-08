package mcalzaferri.project.heatmap.data;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.gson.JsonObject;

import mcalzaferri.project.heatmap.data.config.EntityDefinition;
import mcalzaferri.project.heatmap.data.config.FieldDefinition;

public class DatastoreEntityFactory {
	
	private DatastoreEntityFactory() {
		
	}
	
	public static DatastoreEntityFactory newFactory() {
		return new DatastoreEntityFactory();
	}
	
	public FullEntity<IncompleteKey> buildFromJsonObject(IncompleteKey key, EntityDefinition def, JsonObject jsonObject) {
		FullEntity.Builder<IncompleteKey> builder = Entity.newBuilder(key);
		for(FieldDefinition field : def.fields) {
			if(jsonObject.has(field.name)) {
				builder.set(field.name, field.parse(jsonObject.get(field.name)));
			}
		}
		return builder.build();
	}
	
}
