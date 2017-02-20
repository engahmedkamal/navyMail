package navymail.models.Master;
/**
 * 
 * @author zankalony
 *
 */
public class Target {
	public static String ID_COL = "id";
	public static String NAME_COL = "name";
	String name;
	int id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
