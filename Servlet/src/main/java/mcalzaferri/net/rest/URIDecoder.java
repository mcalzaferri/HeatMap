package mcalzaferri.net.rest;

public class URIDecoder {
	private String uri;
	private String root;
	private String[] children;
	public URIDecoder(String uri) {
		this.uri = uri;
		decode();
	}
	
	private void decode() {
		String[] uriParts = uri.split("/");
		if(uriParts.length > 1) {
			children = new String[uriParts.length - 1];
		}
		for(int i = 0; i < uriParts.length; i++) {
			if(i == 0) {
				root = uriParts[i];
			}else {
				children[i-1] = uriParts[i];
			}
		}
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String[] getChildren() {
		return children;
	}

	public void setChildren(String[] children) {
		this.children = children;
	}
	
}
