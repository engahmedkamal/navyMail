package navymail.models.Master;

import java.sql.Date;
/**
 * 
 * @author zankalony
 *
 */
public class Privilege {
	public static final String ID_COL = "id";
	public static final String DESCRIPTION_COL = "description";
	public static final String CANADD_COL = "canAdd";
	public static final String CANUPDATE_AT_COL = "canUpdate";
	public static final String CANDELETE_AT_COL = "canDelete";
	public static final String CREATED_AT_COL = "created_at";
	public static final String UPDATED_AT_COL = "updated_at";

	private int id;
	private String description;
	private boolean cannadd;
	private boolean canupdate;
	private boolean candelete;
	private Date created_at;
	private Date updated_at;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean getCannadd() {
		return cannadd;
	}
	public void setCannadd(boolean cannadd) {
		this.cannadd = cannadd;
	}
	public boolean isCanupdate() {
		return canupdate;
	}
	public void setCanupdate(boolean canupdate) {
		this.canupdate = canupdate;
	}
	public boolean isCandelete() {
		return candelete;
	}
	public void setCandelete(boolean candelete) {
		this.candelete = candelete;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
}
