package mcalzaferri.project.heatmap.data;

import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.PathElement;

public class DataStoreKeyFactory {
	private Datastore datastore;
	
	public DataStoreKeyFactory(Datastore datastore) {
		this.datastore = datastore;
	}
	
	public IncompleteKey createRessourceKey(DataRessource res) {
		KeyFactory keyfactory = datastore.newKeyFactory().setKind(res.getKind());
		if(res.getParents() != null) {
			for(String parentKind : res.getParents().keySet()) {
				keyfactory.addAncestor(PathElement.of(parentKind, res.getParents().get(parentKind)));
			}
		}
		return keyfactory.newKey();
	}
}