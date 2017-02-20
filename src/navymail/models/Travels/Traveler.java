package navymail.models.Travels;

import navymail.util.Environment;

public class Traveler implements Comparable<Traveler> {

	public static final String MILITARY_NUM_COL = "military_num";
	public static final String RANK_ID_COL = "rank_id";
	public static final String NAME_COL = "name";
	public static final String SENIORITY_NUM_COL = "seniority_num";
	public static final String JOB_DESCRIPTION_COL = "job_description";
	public static final String UNIT_ID_COL = "unit_id";
	public static final String NOTES_COL = "notes";
	public static final String Security_COL = "security_status";

	private long military_num;
	private int rank_id;
	private String name;
	private String seniority_num;
	private int unit_id;
	private String job_description;
	private String notes;
	private int security_status;
	private int position;
	public String notes_mission;
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public long getMilitary_num() {
		return military_num;
	}

	public void setMilitary_num(long military_num) {
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

	public String getSeniority_num() {
		return seniority_num;
	}

	public void setSeniority_num(String seniority_num) {
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

	public void setSecurity_status(int security_status) {
		this.security_status = security_status;
	}

	public int getSecurity_status() {
		return security_status;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public int compareTo(Traveler t2) {
		System.err.println(rank_id+"             "+Environment.hm.get(rank_id));
		if (Environment.hm.get(rank_id) > Environment.hm.get(t2.rank_id))
			return 1;
		if (Environment.hm.get(rank_id) < Environment.hm.get(t2.rank_id))
			return -1;

		// if(Environment.hm.get(rank_id)==Environment.hm.get(t2.rank_id)){
		if (military_num > t2.military_num)
			return 1;
		return -1;
		// }

		// return -1;
	}

}
