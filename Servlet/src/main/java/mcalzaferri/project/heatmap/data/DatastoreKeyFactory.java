package mcalzaferri.project.heatmap.data;

import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.PathElement;

public class DatastoreKeyFactory {
	private Datastore datastore;
	
	private DatastoreKeyFactory(Datastore datastore) {
		this.datastore = datastore;
	}
	
	public static DatastoreKeyFactory newFactory(Datastore datastore) {
		return new DatastoreKeyFactory(datastore);
	}
	
	public IncompleteKey createRessourceKey(RequestedRessource res) {
		
		return createKeyFactoryFromRequestedRessource(res)
				.newKey();
	}
	
	public Key getRessourceKey(RequestedRessource res) {
		return createKeyFactoryFromRequestedRessource(res)
				.newKey(res.getId());
	}
	
	private KeyFactory createKeyFactoryFromRequestedRessource(RequestedRessource res) {
		KeyFactory keyfactory = datastore.newKeyFactory().setKind(res.getName());
		RequestedRessource subRes = res.getParent();
		while(subRes != null) {
			keyfactory.addAncestor(PathElement.of(subRes.getName(), subRes.getId()));
			subRes = subRes.getParent();
		}
		return keyfactory;
	}
}