package mcalzaferri.net.rest;

public class UriDecoder {
	private String uri;
	private String root;
	private String[] uriParts;
	public UriDecoder(String uri) {
		this.uri = uri;
		while(this.uri.startsWith("/")) {
			this.uri = this.uri.substring(1);
		}
		decode();
	}
	
	private void decode() {
		String[] uriParts = uri.split("/");
		if(uriParts.length > 1) {
			this.uriParts = new String[uriParts.length - 1];
		}
		for(int i = 0; i < uriParts.length; i++) {
			if(i == 0) {
				root = uriParts[i];
			}else {
				this.uriParts[i-1] = uriParts[i];
			}
		} 
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String[] getUriParts() {
		return uriParts;
	}

	public void setUriParts(String[] uriParts) {
		this.uriParts = uriParts;
	}
	
}
