package navymail.models.Motb3a;

import java.sql.Date;

public class Motb3a_db {
	private int id;
	private int target_id;
	private Date doc_date;
	public static final String ID_COL = "id";
	public static final String TARGET_COL = "target_id";
	public static final String DOC_DATE_COL = "doc_date";
	public Date getDoc_date() {
		return doc_date;
	}
	public void setDoc_date(Date doc_date) {
		this.doc_date = doc_date;
	}
	public int getTarget_id() {
		return target_id;
	}
	public void setTarget_id(int target_id) {
		this.target_id = target_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
