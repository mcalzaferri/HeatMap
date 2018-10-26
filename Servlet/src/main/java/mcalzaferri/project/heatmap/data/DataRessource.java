package mcalzaferri.project.heatmap.data;

import java.util.Map;

public class DataRessource {
	private String kind;
	private Map<String, Long> parents;
	

	
	public DataRessource(String kind, Map<String, Long> parents) {
		this.kind = kind;
		this.parents = parents;
	}
	
	public String getKind() {
		return kind;
	}
	
	public Map<String, Long> getParents(){
		return parents;
	}
	
}
