package navymail.models.Travels;


public class Rank_degree {
	
	public static final String ID_COL = "id";
	public static final String RANK_NAME_COL = "rank_name";
	public static final String RANK_TYPE_COL= "rank_type";
	public static final String GROUP_ID_COL= "group_id";
	private int id;
	private String rank_name;
	private int rank_type;
	private int group_id;
	public int getRank_type() {
		return rank_type;
	}
	public void setRank_type(int rank_type) {
		this.rank_type = rank_type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRank_name() {
		return rank_name;
	}
	public void setRank_name(String rank_name) {
		this.rank_name = rank_name;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
}
