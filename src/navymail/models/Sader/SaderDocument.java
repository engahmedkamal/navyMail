package navymail.models.Sader;

import java.sql.Date;
import java.util.ArrayList;

public class SaderDocument {

	private int id;
	private String title;
	private int doc_number;
	private int doc_type;
	private String prev_doc_id;
	private String url;
	private Date doc_date;
	private Date start_date;
	private Date finish_date;
	private String unit_id;
	private int file_save_num;
	private int sub_file_save_num;
	private int tashira;
	private int target_id;
	private int security_type;
	private int absolut_num;
	ArrayList<String> allPreviousTarbet;
	private ArrayList<Integer> unit_ids;
	// for linking
	private String parents;
	ArrayList<String> imgs;
	public static final String ID_COL = "id";
	public static final String TITLE_COL = "title";
	public static final String DOC_NUM_COL = "doc_number";
	public static final String DOC_TYPE_COL = "doc_type";
	public static final String PREV_DOC_ID_COL = "prev_doc_id";
	public static final String URL_COL = "url";
	public static final String DOC_DATE_COL = "doc_date";
	public static final String START_DATE_COL = "start_date";
	public static final String FINISH_DATE_COL = "finish_date";
	public static final String UNIT_ID_COL = "unit_id";
	public static final String FILE_SAVE_NUM_COL = "file_save_num";
	public static final String SUB_FILE_SAVE_NUM_COL = "sub_file_save_num";
	public static final String TASHIRA_COL = "tashira";
	public static final String TARGET_ID_COL = "target_id";
	public static final String SECURITY_TYPE_COL = "security_type";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDoc_number() {
		return doc_number;
	}

	public void setDoc_number(int doc_number) {
		this.doc_number = doc_number;
	}

	public int getDoc_type() {
		return doc_type;
	}

	public int getAbsolut_num() {
		return absolut_num;
	}

	public void setAbsolut_num(int absolut_num) {
		this.absolut_num = absolut_num;
	}

	public void setDoc_type(int doc_type) {
		this.doc_type = doc_type;
	}

	public String getPrev_doc_id() {
		return prev_doc_id;
	}

	public void setPrev_doc_id(String prev_doc_id) {
		this.prev_doc_id = prev_doc_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getdoc_date() {
		return doc_date;
	}

	public void setdoc_date(Date created_at) {
		this.doc_date = created_at;
	}

	public Date getstart_date() {
		return start_date;
	}

	public void setstart_date(Date updated_at) {
		this.start_date = updated_at;
	}

	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	public int getFile_save_num() {
		return file_save_num;
	}

	public void setFile_save_num(int file_save_num) {
		this.file_save_num = file_save_num;
	}

	public String getParents() {
		return parents;
	}

	public void setParents(String parents) {
		this.parents = parents;
	}

	public ArrayList<String> getImgs() {
		return imgs;
	}

	public void setImgs(ArrayList<String> imgs) {
		this.imgs = imgs;
	}

	public int getSub_file_save_num() {
		return sub_file_save_num;
	}

	public void setSub_file_save_num(int sub_file_save_num) {
		this.sub_file_save_num = sub_file_save_num;
	}

	public Date getFinish_date() {
		return finish_date;
	}

	public void setFinish_date(Date finish_date) {
		this.finish_date = finish_date;
	}

	public int getSecurity_type() {
		return security_type;
	}

	public void setSecurity_type(int security_type) {
		this.security_type = security_type;
	}

	public int getTarget_id() {
		return target_id;
	}

	public void setTarget_id(int target_id) {
		this.target_id = target_id;
	}

	public int getTashira() {
		return tashira;
	}

	public void setTashira(int tashira) {
		this.tashira = tashira;
	}

	public ArrayList<String> getAllPreviousTarbet() {
		return allPreviousTarbet;
	}

	public void setAllPreviousTarbet(ArrayList<String> allPreviousTarbet) {
		this.allPreviousTarbet = allPreviousTarbet;
	}

	public ArrayList<Integer> getUnits_sader() {
		return unit_ids;
	}

	public void setUnits_sader(ArrayList<Integer> units_sader) {
		this.unit_ids = units_sader;
	}

}
