package navymail.models.Master;

import java.sql.Date;

public class SaderUnits {
	public static String DOC_NUMBER_COL= "doc_number";
	public static String DOC_DATE_COL ="doc_date";
	public static String UNIT_id_COL ="unit_id";
	private Date doc_date;
	private int doc_number;
	private int unit_id;
	public int getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(int unit_id) {
		this.unit_id = unit_id;
	}
	public int getDoc_number() {
		return doc_number;
	}
	public void setDoc_number(int doc_number) {
		this.doc_number = doc_number;
	}
	public Date getDoc_date() {
		return doc_date;
	}
	public void setDoc_date(Date doc_date) {
		this.doc_date = doc_date;
	}
	
}	