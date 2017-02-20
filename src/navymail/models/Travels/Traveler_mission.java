package navymail.models.Travels;


public class Traveler_mission {
	
	public static final String TRAVELER_ID_COL = "traveler_id";
	public static final String MISSION_ID_COL = "mission_id";
	public static final String SHIP_ID_COL = "ship_id";
	public static final String POSTION_COL = "position";
	public static final String Note_COL = "notes";
	private long traveler_id;
	private int mission_id;
	private int ship_id;
	private int position;
	private String notes;
	
	public long getTraveler_id() {
		return traveler_id;
	}
	public void setTraveler_id(long traveler_id) {
		this.traveler_id = traveler_id;
	}
	public int getMission_id() {
		return mission_id;
	}
	public void setMission_id(int mission_id) {
		this.mission_id = mission_id;
	}
	public int getPostion() {
		return position;
	}
	public void setPostion(int postion) {
		this.position = postion;
	}
	public int getShip_id() {
		return ship_id;
	}
	public void setShip_id(int ship_id) {
		this.ship_id = ship_id;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
	
