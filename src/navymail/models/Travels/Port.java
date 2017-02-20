package navymail.models.Travels;

public class Port {

	public static final String ID_COL = "id";
	public static final String NAME_COL = "name";
	
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}