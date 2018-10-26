package mcalzaferri.project.heatmap.data;

import java.util.HashMap;
import java.util.Map;

public class DataRessourceFactory {
	private String kind;
	private Map<String,Long> parents;
	private DataRessourceFactory() {
		parents = new HashMap<>();
	}
	
	public static DataRessourceFactory newFactory() {
		return new DataRessourceFactory();
	}
	
	public DataRessourceFactory setKind(String kind) {
		this.kind = kind;
		return this;
	}
	
	public DataRessourceFactory addParent(String kind, Long id) {
		this.parents.put(kind,id);
		return this;
	}
	
	public DataRessource build() {
		return new DataRessource(kind, parents);
	}
	
	public static DataRessource buildFromURI(String uri) {
		
	}
}
