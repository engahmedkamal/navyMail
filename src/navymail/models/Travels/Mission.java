package navymail.models.Travels;



public class Mission {

	public static final String ID_COL = "id";
	public static final String NAME_COL = "name";
	public static final String TYPE_ID_COL = "type_id";
	public static final String ACTIVITY_NAME_COL = "activity_name";
	public static final String DATE_FROM_COL = "date_from";
	public static final String DATE_TO_COL = "date_to";
	public static final String DOCUMNET_ID_COL = "document_id"; 
	public static final String CONFIRMATION_NO_COL = "confirmation_no";
	public static final String CONFIRMATION_DATE_COL = "confirmation_date";
	public static final String COMMANDER_ID_COL = "commander_id";
	public static final String SECOND_COMMANDER_ID_COL = "second_commander_id";
	public static final String SHIPS_COL = "ships";
	public static final String PORTS_COL = "ports";
	public static final String DIRECTION_COL = "direction";
		
	private int id;
	private String name;
	private int type_id;
	private String activity_name;
	private String date_from;
	private String date_to;
	private String document_id;
	private String confirmation_no;
	private String confirmation_date;
	private long commander_id;
	private long second_commander_id;
	private String ships;
	private String ports;
	private int direction;
	
	
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
	}
	
	public String getActivity_name() {
		return activity_name;
	}
	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}
	public String getDate_from() {
		return date_from;
	}
	public void setDate_from(String date_from) {
		this.date_from = date_from;
	}
	public String getDate_to() {
		return date_to;
	}
	public void setDate_to(String date_to) {
		this.date_to = date_to;
	}
	public String getDocument_id() {
		return document_id;
	}
	public void setDocument_id(String document_id) {
		this.document_id = document_id;
	}
	public String getConfirmation_no() {
		return confirmation_no;
	}
	public void setConfirmation_no(String confirmation_no) {
		this.confirmation_no = confirmation_no;
	}
	public String getConfirmation_date() {
		return confirmation_date;
	}
	public void setConfirmation_date(String confirmation_date) {
		this.confirmation_date = confirmation_date;
	}
	public long getCommander_id() {
		return commander_id;
	}
	public void setCommander_id(long commander_id) {
		this.commander_id = commander_id;
	}
	public long getSecond_commander_id() {
		return second_commander_id;
	}
	public void setSecond_commander_id(long second_commander_id) {
		this.second_commander_id = second_commander_id;
	}
	public String getShips() {
		return ships;
	}
	public void setShips(String ships) {
		this.ships = ships;
	}
	public String getPorts() {
		return ports;
	}
	public void setPorts(String ports) {
		this.ports = ports;
	}
	
	
}
