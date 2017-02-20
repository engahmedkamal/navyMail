package navymail.models.Wared;

import java.sql.Date;
import java.util.ArrayList;

import navymail.models.Motb3a.Resp_unit;
import navymail.models.Motb3a.istagel;
import navymail.models.Travels.Traveler;
/**
 * 
 * @author zankalony
 *
 */
public class WaredDocument implements  Comparable<WaredDocument> {

	private int id;
	private String title;
	private int doc_number;
	private int doc_type;
	private String prev_doc_id;
	private int status_id;
	private String url;
	private String notes;
	private int target_id;
	private Date created_at;
	private Date updated_at;
	private int doc_out_number;
	private int doc_save_number;
	private int doc_need_replay;
	private int unit_id;
	private int sub_unit_id;
	private int trans_out_num;
	private Date doc_out_date;
	private Date trans_out_date;
	private ArrayList<Integer> tashira;
	private ArrayList<Integer> tashira_qaud;
	private ArrayList<Integer> units_resp;
	private ArrayList<Resp_unit> resp_unit;
	// for linking
	private String parents;
	private int absolut_num;
	ArrayList<String> imgs;
	ArrayList<String> allPreviousTarbet;
	private ArrayList<istagel> estagel=new ArrayList<>();

	public static final String ID_COL = "id";
	public static final String TITLE_COL = "title";
	public static final String DOC_NUM_COL = "doc_number";
	public static final String DOC_TYPE_COL = "doc_type";
	public static final String PREV_DOC_ID_COL = "prev_doc_id";
	public static final String STATUS_ID_COL = "status_id";
	public static final String URL_COL = "url";
	public static final String NOTES_COL = "notes";
	public static final String TARGET_ID_COL = "target_id";
	public static final String CREATED_AT_COL = "created_at";
	public static final String UPDATED_AT_COL = "updated_at";
	public static final String DOC_OUT_NUMBER_COL = "doc_out_number";
	public static final String DOC_SAVE_NUMBER_COL = "doc_save_number";
	public static final String DOC_NEED_REPLY_COL = "doc_need_replay";
	public static final String UNIT_ID_COL = "unit_id";
	public static final String SUB_UNIT_ID_COL = "sub_unit_id";
	public static final String TRANS_OUT_NUM_COL = "trans_out_num";
	public static final String DOC_OUT_DATE_COL = "doc_out_date";
	public static final String TRANS_OUT_DATE_COL = "trans_out_date";

	public int getId() {
		return id;
	}

	public void setImgs(ArrayList<String> imgs) {
		this.imgs = imgs;
	}
	public ArrayList<String> getImgs() {
		return imgs;
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

	public void setDoc_type(int doc_type) {
		this.doc_type = doc_type;
	}

	public String getPrev_doc_id() {
		return prev_doc_id;
	}

	public void setPrev_doc_id(String prev_doc_id) {
		this.prev_doc_id = prev_doc_id;
	}

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getTarget_id() {
		return target_id;
	}

	public void setTarget_id(int target_id) {
		this.target_id = target_id;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public int getDoc_out_number() {
		return doc_out_number;
	}

	public void setDoc_out_number(int doc_out_number) {
		this.doc_out_number = doc_out_number;
	}

	public int getDoc_save_number() {
		return doc_save_number;
	}

	public void setDoc_save_number(int doc_save_number) {
		this.doc_save_number = doc_save_number;
	}

	public int getDoc_need_replay() {
		return doc_need_replay;
	}

	public void setDoc_need_replay(int doc_need_replay) {
		this.doc_need_replay = doc_need_replay;
	}

	public int getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(int unit_id) {
		this.unit_id = unit_id;
	}

	public int getSub_unit_id() {
		return sub_unit_id;
	}

	public void setSub_unit_id(int sub_unit_id) {
		this.sub_unit_id = sub_unit_id;
	}

	public int getTrans_out_num() {
		return trans_out_num;
	}

	public void setTrans_out_num(int trans_out_num) {
		this.trans_out_num = trans_out_num;
	}

	public Date getDoc_out_date() {
		return doc_out_date;
	}

	public void setDoc_out_date(Date doc_out_date) {
		this.doc_out_date = doc_out_date;
	}

	public Date getTrans_out_date() {
		return trans_out_date;
	}

	public void setTrans_out_date(Date trans_out_date) {
		this.trans_out_date = trans_out_date;
	}

	public String getParents() {
		return parents;
	}

	public void setParents(String parents) {
		this.parents = parents;
	}

	public int getAbsolut_num() {
		return absolut_num;
	}

	public void setAbsolut_num(int absolut_num) {
		this.absolut_num = absolut_num;
	}
	public ArrayList<String> getAllPreviousTarbet() {
		return allPreviousTarbet;
	}

	public void setAllPreviousTarbet(ArrayList<String> allPreviousTarbet) {
		this.allPreviousTarbet = allPreviousTarbet;
	}

	public ArrayList<Integer> getTashira() {
		return tashira;
	}

	public void setTashira(ArrayList<Integer> tashira) {
		this.tashira = tashira;
	}

	public ArrayList<Resp_unit> getResp_unit() {
		return resp_unit;
	}

	public void setResp_unit(ArrayList<Resp_unit> resp_unit) {
		this.resp_unit = resp_unit;
	}

	public ArrayList<istagel> getEstagel() {
		return estagel;
	}

	public void setEstagel(ArrayList<istagel> estagel) {
		this.estagel = estagel;
	}

	@Override
	public int compareTo(WaredDocument arg0) {
		// TODO Auto-generated method stub
		if(created_at.before(arg0.getCreated_at()))
		{
			return -1;
		}else if(created_at.equals(arg0.getCreated_at())){
			if(doc_number>arg0.getDoc_number())
				return 1;
			return -1;
		}else{
			return 1;
		}
	}

	public ArrayList<Integer> getUnits_resp() {
		return units_resp;
	}

	public void setUnits_resp(ArrayList<Integer> units_resp) {
		this.units_resp = units_resp;
	}

	public ArrayList<Integer> getTashira_qaud() {
		return tashira_qaud;
	}

	public void setTashira_qaud(ArrayList<Integer> tashira_qaud) {
		this.tashira_qaud = tashira_qaud;
	}
}