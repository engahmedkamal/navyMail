package navymail.models.Master;

import java.sql.Date;

/**
 * 
 * @author zankalony
 *
 */
public class User {
	public static final String ID_COL = "id";
	public static final String USERNAME_COL = "username";
	public static final String PASSWORD_COL = "password";
	public static final String PRIVILEGE_ID_COL = "pivilege_id";
	public static final String GROUP_ID_COL = "group_id";
	public static final String CREATED_AT_COL = "created_at";
	public static final String UPDATED_AT_COL = "updated_at";

	private int id;
	private	String username;
	private String password;
	private Date created_at;
	private	Date updated_at;
	private	int privilege_id;
	private	int group_id;


	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public int getPrivilege_id() {
		return privilege_id;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPrivilege_id(int privilege_id) {
		this.privilege_id = privilege_id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	

}
