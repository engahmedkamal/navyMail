package navymail.models.Travels;


public class Ship {
	
	public static final String ID_COL = "id";
	public static final String NAME_COL = "name";
	public static final String SHIP_TYPE_COL = "ship_type";
	public static final String UNIT_ID_COL = "unit_id";
	
	
	private int id;
	private String name;
	private int unit_id;
	private String ship_type;
	
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
	public int getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(int unit_id) {
		this.unit_id = unit_id;
	}
	public String getShip_type() {
		return ship_type;
	}
	public void setShip_type(String ship_type) {
		this.ship_type = ship_type;
	}
	
}
