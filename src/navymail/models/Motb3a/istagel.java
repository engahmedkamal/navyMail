package navymail.models.Motb3a;

import java.sql.Date;

public class istagel {
	private int id;
	private int unit;
	private Date date;
	public static final String ID_COL = "id";
	public static final String UNIT_COL = "unit";
	public static final String DATE_COL = "date";
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUnit() {
		return unit;
	}
	public void setUnit(int unit) {
		this.unit = unit;
	}
}
