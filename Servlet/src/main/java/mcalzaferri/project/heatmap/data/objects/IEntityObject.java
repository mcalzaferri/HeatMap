package mcalzaferri.project.heatmap.data.objects;

import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;

public interface IEntityObject {
	void setId(long id);
	FullEntity<IncompleteKey> getFullEntity(IncompleteKey key);
}
