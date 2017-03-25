
public class Identity {

	
	private String id;
	private String node;
	private String name;
	
	public Identity() {
		super();
	}
	
	public Identity(String id, String node, String name) {
		super();
		this.id = id;
		this.node = node;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
