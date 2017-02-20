package navymail.models.Master;
/**
 * 
 * @author zankalony
 *
 */
public class Status {
	public static String ID_COL = "id";
	public static String NAME_COL = "name";

	int id;
	String name;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
