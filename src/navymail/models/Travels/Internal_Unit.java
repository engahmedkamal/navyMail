package navymail.models.Travels;


public class Internal_Unit {
	public static String ID_COL= "id";
	public static String NAME_COL ="name";
	public static String UNIT_TYPE_COL ="unit_type";
	
	private String name;
	private int id;
	private static final int UNIT_TYPE = 0;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
