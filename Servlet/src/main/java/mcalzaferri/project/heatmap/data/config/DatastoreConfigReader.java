package mcalzaferri.project.heatmap.data.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import mcalzaferri.project.heatmap.data.RequestedRessource;

public class DatastoreConfigReader {
	private DatastoreConfig config;
	private static DatastoreConfigReader instance;
	
	public static void initInstance(String configFilePath) throws IOException {
		instance = new DatastoreConfigReader(configFilePath);
	}
	
	public static DatastoreConfigReader getInstance() {
		return instance;
	}
	
	private DatastoreConfigReader(String configFilePath) throws IOException {
		this.config = DatastoreConfigFactory.buildFromJson(readFile(configFilePath));
	}
	
	private String readFile(String configFilePath) throws IOException{
		StringBuffer sb = new StringBuffer();
		BufferedReader r = Files.newBufferedReader(Paths.get(configFilePath));
		r.lines().forEach(sb::append);
		r.close();
		return sb.toString();
	}
	
	public EntityDefinition getEntityDefinition(RequestedRessource requestedRessource) throws RessourceNotFoundException {
		RequestedRessource res = requestedRessource.getRoot();
		List<EntityDefinition> defs = config.getEntities();
		return findRessourceInDefinitons(res, defs);
	}
	
	public EntityDefinition findRessourceInDefinitons(RequestedRessource res, List<EntityDefinition> defs) throws RessourceNotFoundException {
		for(EntityDefinition def : defs) {
			if(def.name.equalsIgnoreCase(res.getName()) || def.alias.equalsIgnoreCase(res.getName())) {
				if(res.getChild() != null){
					return verifyNextRessource(res.getChild(), def);
				}else {
					return def;
				}
			}
		}
		throw new RessourceNotFoundException("The requested ressource could not been found in the configuration");
	}
	
	public EntityDefinition verifyNextRessource(RequestedRessource res,  EntityDefinition def) throws RessourceNotFoundException {
		if(def.entities != null){
			return findRessourceInDefinitons(res, def.entities);
		}else {
			throw new RessourceNotFoundException("The requested ressource could not been found in the configuration");
		}
	}
}
