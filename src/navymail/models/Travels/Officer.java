package navymail.models.Travels;



public class Officer {

	public static final String ID_COL = "id";
	public static final String MILITARY_NUM_COL = "military_num";
	public static final String RANK_ID_COL = "rank_id";
	public static final String NAME_COL = "name";
	public static final String SENIORITY_NUM_COL = "seniority_num";
	public static final String JOB_DESCRIPTION_COL = "job_description";
	public static final String UNIT_ID_COL = "unit_id";
	public static final String UNIT_TYPE_COL = "unit_type";
	public static final String RANK_TYPE_COL = "rank_type";
	public static final String NOTES_COL = "notes";
	
	private int id;
	private int military_num;
	private int rank_id;
	private String name;
	private int seniority_num;
	private String job_description;
	private int unit_id;
	private static final int UNIT_TYPE=0;
	private int rank_type;
	
	public int getRank_type() {
		return rank_type;
	}
	public void setRank_type(int rank_type) {
		this.rank_type = rank_type;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	private String notes;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMilitary_num() {
		return military_num;
	}
	public void setMilitary_num(int military_num) {
		this.military_num = military_num;
	}
	public int getRank_id() {
		return rank_id;
	}
	public void setRank_id(int rank_id) {
		this.rank_id = rank_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSeniority_num() {
		return seniority_num;
	}
	public void setSeniority_num(int seniority_num) {
		this.seniority_num = seniority_num;
	}
	public String getJob_description() {
		return job_description;
	}
	public void setJob_description(String job_description) {
		this.job_description = job_description;
	}
	public int getUnit_id() {
		return unit_id;
	}
	public void setUnit_id(int unit_id) {
		this.unit_id = unit_id;
	}
	public static int getUnitType() {
		return UNIT_TYPE;
	}
	
	
	
}
