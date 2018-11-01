package mcalzaferri.project.heatmap.data;

public class RequestedRessource {
	private String name;
	private Long id;
	private RequestedRessource parent;
	private RequestedRessource child;
	
	public RequestedRessource(String name, Long id) {
		this.name = name;
		this.id = id;
	}
	
	public void setChild(RequestedRessource child) {
		this.child = child;
		if(this.child != null && this.child.getParent() != this) {
			this.child.setParent(this);
		}
	}
	
	public void setParent(RequestedRessource parent) {
		this.parent = parent;
		if(this.parent != null && this.parent.getChild() != this) {
			this.parent.setChild(this);
		}
	}
	
	public RequestedRessource getRoot() {
		if(parent != null) {
			return parent.getRoot();
		}else {
			return this;
		}
	}
	
	public RequestedRessource getLeaf() {
		if(child != null) {
			return child.getLeaf();
		}else {
			return this;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public Long getId() {
		return id;
	}
	
	public RequestedRessource getParent(){
		return parent;
	}
	
	public RequestedRessource getChild() {
		return child;
	}
}
