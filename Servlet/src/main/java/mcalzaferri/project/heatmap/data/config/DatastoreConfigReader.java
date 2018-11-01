package mcalzaferri.project.heatmap.data.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import mcalzaferri.project.heatmap.data.RequestedRessource;

public class DatastoreConfigReader {
	private DatastoreConfig config;
	
	public DatastoreConfigReader(String configFilePath) throws IOException {
		this.config = DatastoreConfigFactory.buildFromJson(readFile(configFilePath));
	}
	
	public EntityDefinition getEntityDefinition(RequestedRessource requestedRessource) throws RessourceNotFoundException {
		RequestedRessource res = requestedRessource.getRoot();
		List<EntityDefinition> defs = config.getEntities();
		boolean found;
		while(defs != null) {
			found = false;
			for(EntityDefinition def : defs) {
				if(def.name.equalsIgnoreCase(res.getName()) || def.alias.equalsIgnoreCase(res.getName())) {
					res = res.getChild();
					if(res == null) {
						return def;
					}else {
						defs = def.entities;
						found = true;
						break;
					}
				}else {
					throw new RessourceNotFoundException("The requested ressource could not been found in the configuration");
				}
			}
			if(!found) {
				throw new RessourceNotFoundException("The requested ressource could not been found in the configuration");
			}
		}
		throw new RessourceNotFoundException("The requested ressource could not been found in the configuration");
	}
	
	private String readFile(String configFilePath) throws IOException{
		StringBuffer sb = new StringBuffer();
		BufferedReader r = Files.newBufferedReader(Paths.get(configFilePath));
		r.lines().forEach(sb::append);
		r.close();
		return sb.toString();
	}
}
