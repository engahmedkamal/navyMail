package navymail.models.Master;

import java.sql.Date;
/**
 * 
 * @author zankalony
 *
 */
public class Group {
	
	public static final String ID_COL = "id";
	public static final String NAME_COL = "name";
	public static final String CREATED_AT_COL = "created_at";
	public static final String UPDATED_AT_COL = "updated_at";

	private int id;
	private String name;
	private Date created_at;
	private Date updated_at;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
}
