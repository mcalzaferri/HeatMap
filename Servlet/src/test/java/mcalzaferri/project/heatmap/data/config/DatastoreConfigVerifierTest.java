package mcalzaferri.project.heatmap.data.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.*;

import mcalzaferri.project.heatmap.data.RequestedRessource;

public class DatastoreConfigVerifierTest {
	private static final String rootPath = "src/test/java/mcalzaferri/project/heatmap/data/config/";
	private DatastoreConfigVerifier verifier;
	private RequestedRessource testEntityRes;
	private RequestedRessource testSubEntityRes;
	private RequestedRessource testSubSubEntityRes;
	
	@Before
	public void initTest() throws IOException {
		DatastoreConfigReader.initInstance(rootPath + "TestConfiguration.json");
		DatastoreConfigReader reader = DatastoreConfigReader.getInstance();
		this.verifier = DatastoreConfigVerifier.getInstance(reader);
		testEntityRes = new RequestedRessource("testentity", null);
		testSubEntityRes = new RequestedRessource("testentity", null);
		testSubEntityRes.setChild(new RequestedRessource("testsubentity", null));
		testSubSubEntityRes = new RequestedRessource("testentity", null);
		testSubSubEntityRes.setChild(new RequestedRessource("testsubentity", null));
		testSubSubEntityRes.getChild().setChild(new RequestedRessource("testsubsubentit",null));
	}
	
	@Test(timeout = 1000)
	public void validTestEntitiesShouldPass() throws VerificationException, RessourceNotFoundException {
		verifier.verifyJson(testEntityRes, readFile("ValidTestEntity.json"));
		verifier.verifyJson(testSubEntityRes, readFile("ValidTestSubEntity.json"));
	}
	@Test(expected = TooManyFieldsException.class, timeout = 1000)
	public void tooManyFieldsShouldThrowTooManyFieldsException() throws VerificationException, RessourceNotFoundException {
		verifier.verifyJson(testSubEntityRes, readFile("TooManyFieldsTestSubEntity.json"));
	}
	@Test(expected = FieldNotFoundException.class, timeout = 1000)
	public void notEnoughFieldsShouldThrowFieldNotFoundException() throws VerificationException, RessourceNotFoundException {
		verifier.verifyJson(testSubEntityRes, readFile("NotEnoughFieldsTestSubEntity.json"));
	}
	@Test(expected = RessourceNotFoundException.class, timeout = 1000)
	public void wrongRessourceShouldThrowRessourceNotFoundException() throws RessourceNotFoundException, VerificationException {
		verifier.verifyJson(testSubSubEntityRes,  readFile("ValidTestSubEntity.json"));
	}
	
	private String readFile(String file){
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader r = Files.newBufferedReader(Paths.get(rootPath + file));
			r.lines().forEach(sb::append);
			r.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
