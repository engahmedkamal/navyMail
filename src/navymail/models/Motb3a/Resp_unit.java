package navymail.models.Motb3a;

import java.sql.Date;

public class Resp_unit {
	private int id;
	private int unit;
	private int status;
	private Date resp_date;
	public static String ID_COL="id";
	public static String UNIT_COL="unit";
	public static String STATUS_COL="status";
	public static String RESP_DATE_COL="resp_date";
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getUnit() {
		return unit;
	}
	public void setUnit(int unit) {
		this.unit = unit;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getResp_date() {
		return resp_date;
	}
	public void setResp_date(Date resp_date) {
		this.resp_date = resp_date;
	}
	
	
	
}
