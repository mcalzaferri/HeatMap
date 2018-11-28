package mcalzaferri.project.heatmap.data;

import com.google.cloud.datastore.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import mcalzaferri.project.heatmap.data.config.EntityDefinition;
import mcalzaferri.project.heatmap.data.config.FieldNotFoundException;
import mcalzaferri.project.heatmap.data.config.RessourceNotFoundException;

public class SensorDatastoreReader {
	private HeatmapDatastoreToolkit datastore;
	private static SensorDatastoreReader instance;
	
	public static SensorDatastoreReader getInstance(HeatmapDatastoreToolkit datastore) {
		if(instance == null) {
			instance = new SensorDatastoreReader(datastore);
		}
		return instance;
	}
	
	private SensorDatastoreReader(HeatmapDatastoreToolkit datastore) {
		this.datastore = datastore;
	}
	
	public JsonArray query(RequestedRessource res, String queryString) throws FieldNotFoundException, RessourceNotFoundException {
		EntityDefinition requestedEntity = datastore.getConfigReader().getEntityDefinition(res);
		QueryResults<Entity> queryResult = datastore.getDatastore().run(datastore.getQueryFactory()
				.newBuilder()
				.setRequestedRessource(res)
				.setEntityDefinition(requestedEntity)
				.setQueryString(queryString)
				.build());
		return queryResultToJson(queryResult);
	}
	
	private JsonArray queryResultToJson(QueryResults<Entity> queryResult) {
		JsonArray jsonArray = new JsonArray();
		while(queryResult != null && queryResult.hasNext()) {
			Entity entity = queryResult.next();
			jsonArray.add(entityToJsonObject(entity));
		}
		return jsonArray;
	}
	
	private JsonObject entityToJsonObject(Entity entity) {
		JsonObject json = new JsonObject();
		json.addProperty("id", entity.getKey().getId());
		for(String property : entity.getNames()) {
			Value<?> v = entity.getValue(property);
			switch(v.getType()) {
			case BOOLEAN:
				json.addProperty(property, entity.getBoolean(property));
				break;
			case STRING:
				json.addProperty(property, entity.getString(property));
				break;
			case DOUBLE:
				json.addProperty(property, entity.getDouble(property));
				break;
			case LONG:
				json.addProperty(property, entity.getLong(property));
				break;
			case LAT_LNG:
				Gson gson = new Gson();
				json.add(property, gson.toJsonTree(entity.getLatLng(property)));
				break;
			case TIMESTAMP:
				json.addProperty(property, entity.getTimestamp(property).toDate().getTime());
				break;
			default:
				json.addProperty(property, entity.getValue(property).toString());
				break;
			}
		}
		return json;
	}
}
