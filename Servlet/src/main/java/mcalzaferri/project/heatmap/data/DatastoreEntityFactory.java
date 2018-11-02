package mcalzaferri.project.heatmap.data;

import java.util.Date;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.LatLng;
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
				System.out.print("set " + field.name + "to: ");
				switch(field.type) {
				case "Double":
					builder.set(field.name, jsonObject.get(field.name).getAsDouble());
					break;
				case "Long":
					builder.set(field.name, jsonObject.get(field.name).getAsLong());
					break;
				case "Timestamp":
					builder.set(field.name, Timestamp.of(new Date(jsonObject.get(field.name).getAsLong())));
					break;
				case "LatLng":
					builder.set(field.name, 
							LatLng.of(jsonObject.get(field.name).getAsJsonObject().get("latitude").getAsDouble(), 
									jsonObject.get(field.name).getAsJsonObject().get("longitude").getAsDouble()));
					break;
				case "Boolean":
					builder.set(field.name, jsonObject.get(field.name).getAsBoolean());
					break;
				default:
					builder.set(field.name, jsonObject.get(field.name).getAsString());
					break;
				}
			}else {
			}
		}
		return builder.build();
	}
	
}
