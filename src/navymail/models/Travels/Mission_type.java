package navymail.models.Travels;


public class Mission_type {

	public static final String ID_COL = "id";
	public static final String TYPE_COL = "type";
	public static final String NAME_COL = "name";
	
	
	private int id;
	private int type;
	private String name;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
	
	
	
}
