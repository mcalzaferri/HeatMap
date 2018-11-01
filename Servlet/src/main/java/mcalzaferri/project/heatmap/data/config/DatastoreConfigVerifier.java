package mcalzaferri.project.heatmap.data.config;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import mcalzaferri.project.heatmap.data.RequestedRessource;

public class DatastoreConfigVerifier {
	private DatastoreConfigReader configReader;
	
	public DatastoreConfigVerifier(DatastoreConfigReader configReader) {
		this.configReader = configReader;
	}
	
	public void verifyJson(RequestedRessource requestedRessource, String json) throws VerificationException, RessourceNotFoundException {
		JsonElement element = new JsonParser().parse(json);
		JsonObject jsonObj = element.getAsJsonObject();
		verifyJsonObj(requestedRessource, jsonObj);
	}
	
	public void verifyJsonObj(RequestedRessource requestedRessource, JsonObject jsonObj) throws VerificationException, RessourceNotFoundException {
		//First configuration of requested ressource
		EntityDefinition def = configReader.getEntityDefinition(requestedRessource);
		verifyEntityJson(def, jsonObj);
	}
	
	public void verifyEntityJson(EntityDefinition def, JsonObject jsonObj) throws VerificationException {
		if(jsonObj == null || def == null) {
			throw new NullPointerException();
		}
		//First check if json does not contain more data then required
		boolean found;
		for(Entry<String, JsonElement> entry : jsonObj.entrySet()) {
			found = false;
			for(FieldDefinition field : def.fields) {
				found |= field.name.equalsIgnoreCase(entry.getKey());
			}
			if(!found) {
				throw new TooManyFieldsException("The field " + entry.getKey() + " of the json is too much and could not been found in the configuration");
			}
		}
		//Now check if json contains all required data
		for(FieldDefinition field : def.fields) {
			if(field.required) {
				found = false;
				for(Entry<String, JsonElement> entry : jsonObj.entrySet()) {
					found |= field.name.equalsIgnoreCase(entry.getKey());
				}
				if(!found) {
					throw new FieldNotFoundException("The field " + field.name + " is missing in the json");
				}
			}
		}
	}
}
