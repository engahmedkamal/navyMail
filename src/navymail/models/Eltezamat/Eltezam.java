package navymail.models.Eltezamat;

public class Eltezam {

	public static String TYPE_COL = "type";
	public static String SEQ_COL = "seq";
	public static String DATE_COL = "date";
	public static String TIME_COL = "time";
	public static String TITLE_COL = "title";
	public static String PLACE_COL = "place";
	public static String TRANS_OUT_NUM_COL = "trans_out_num";
	public static String TRANS_OUT_DATE_COL = "trans_out_date";
	public static String UNIT_COL = "unit";

	private int type;
	private int seq;
	private String date;
	private String time;
	private String title;
	private String place;
	private int trans_out_num;
	private String trans_out_date;
	private String unit;

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public int getTrans_out_num() {
		return trans_out_num;
	}
	public void setTrans_out_num(int trans_out_num) {
		this.trans_out_num = trans_out_num;
	}
	public String getTrans_out_date() {
		return trans_out_date;
	}
	public void setTrans_out_date(String trans_out_date) {
		this.trans_out_date = trans_out_date;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	

}
