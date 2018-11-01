package mcalzaferri.project.heatmap.data;

import static org.junit.Assert.assertEquals;

import org.junit.*;


public class RequestedRessourceFactoryTest {
	private final String resName = "ressource";
	private final Long resId = (long)1234567890;
	private final String subResName = "subressource";
	private final Long subResId = (long)234567890;
	private final String uriWithoutId = "/root/" + resName + "/" + resId + "/" + subResName;
	private final String uriWithId = uriWithoutId + "/" + subResId;
	
	@Test
	public void validUriWithoutIdShouldBeCorrectlyResolved() {
		RequestedRessource ressource = RequestedRessourceFactory.newFactory().buildFromUri(uriWithoutId);
		assertRessourceEquals(ressource, subResName, null, true, false);
		ressource = ressource.getParent();
		assertRessourceEquals(ressource, resName, resId, false, true);
	}
	
	@Test
	public void validUriWithIdShouldBeCorrectlyResolved() {
		RequestedRessource ressource = RequestedRessourceFactory.newFactory().buildFromUri(uriWithId);
		assertRessourceEquals(ressource, subResName, subResId, true, false);
		ressource = ressource.getParent();
		assertRessourceEquals(ressource, resName, resId, false, true);
	}
	
	@Test(expected = NumberFormatException.class)
	public void nameInsteadOfIdInUriShouldThrowNumberFormatException() {
		RequestedRessourceFactory.newFactory().buildFromUri("/root/res/subresinsteadofId");
	}
	
	private void assertRessourceEquals(RequestedRessource res, String name, Long id, boolean hasParent, boolean hasChild) {
		assertEquals(name, res.getName());
		assertEquals(id, res.getId());
		assertEquals(hasParent, res.getParent() != null);
		assertEquals(hasChild, res.getChild() != null);
	}
	
}
