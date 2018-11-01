package mcalzaferri.project.heatmap.data;

import mcalzaferri.net.rest.UriDecoder;


public class RequestedRessourceFactory {
	private String name;
	private Long id;
	private RequestedRessource parent;
	private RequestedRessource child;
	
	private RequestedRessourceFactory() {
		name = null;
		id = null;
	}
	
	public static RequestedRessourceFactory newFactory() {

		return new RequestedRessourceFactory();
	}
	
	public RequestedRessourceFactory setName(String kind) {
		this.name = kind;
		return this;
	}
	
	public RequestedRessourceFactory setId(long id) {
		this.id = id;
		return this;
	}
	
	public RequestedRessourceFactory addParent(String name, Long id) {
		RequestedRessource newParent = new RequestedRessource(name, id);
		if(parent == null) {
			parent = newParent;
		}else {
			parent.getRoot().setParent(newParent);
		}
		return this;
	}
	
	public RequestedRessourceFactory addChild(String name, Long id) {
		RequestedRessource newChild = new RequestedRessource(name, id);
		if(child == null) {
			child = newChild;
		}else {
			child.getLeaf().setChild(newChild);
		}
		return this;
	}
	
	public RequestedRessource build() {
		RequestedRessource result = new RequestedRessource(name, id);
		result.setChild(child);
		result.setParent(parent);
		return result;
	}
	
	public RequestedRessource buildFromUri(String uri) {
		UriDecoder decoder = new UriDecoder(uri);
		String[] uriParts = decoder.getUriParts();
		addUriParts(uriParts, 0);
		return build();
	}
	
	private void addUriParts(String[] uriParts, int currentIndex) {
		if(uriParts.length > currentIndex + 2) {
			addUriParts(uriParts, currentIndex + 2);
			addParent(uriParts[currentIndex], Long.parseLong(uriParts[currentIndex + 1]));
		}else if(uriParts.length - currentIndex == 2){
			setName(uriParts[uriParts.length - 2]);
			setId(Long.parseLong(uriParts[uriParts.length - 1]));
		}else {
			setName(uriParts[uriParts.length - 1]);
		}
	}
}
