package navymail.models.Master;

public class SaderUnitsName {
	public static String ID_COL= "id";
	public static String NAME_COL ="name";
	public static String UNIT_TYPE_COL ="type";
	
	String name;
	int id;
	int unit_type;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getUnit_type() {
		return unit_type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUnit_type(int unit_type) {
		this.unit_type = unit_type;
	}
}
