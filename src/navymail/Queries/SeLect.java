package navymail.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import navymail.Helpers.Wared.ImageUtils;
import navymail.models.Eltezamat.Eltezam;
import navymail.models.Master.Group;
import navymail.models.Master.Privilege;
import navymail.models.Master.SaderUnits;
import navymail.models.Master.SaderUnitsName;
import navymail.models.Master.SavedFile;
import navymail.models.Master.Status;
import navymail.models.Master.Sub_Main;
import navymail.models.Master.Sub_br;
import navymail.models.Master.Target;
import navymail.models.Master.Tashira;
import navymail.models.Master.Units;
import navymail.models.Master.User;
import navymail.models.Motb3a.Motb3a_db;
import navymail.models.Motb3a.Motb3a_tashira_ids;
import navymail.models.Motb3a.Resp_unit;
import navymail.models.Motb3a.istagel;
import navymail.models.Motb3a.motb3a_Tashirat_name;
import navymail.models.Sader.SaderDocument;
import navymail.models.Wared.WaredDocument;
import navymail.util.ConnectionHandler;

public class SeLect {
	public Connection con;
	public ResultSet res;

	public SeLect() throws SQLException {

	}

	public void openConnection(String q) throws SQLException {
		con = ConnectionHandler.getInstance().getDBConnection(ConnectionHandler.NAVYMAIL);
		PreparedStatement st = con.prepareStatement(q);
		res = st.executeQuery(q);
	}

	public int getCount() throws SQLException {
		while (res.next()) {
			return res.getInt("COUNT(*)");
		}
		return res.getInt("COUNT(*)");
	}

	public ArrayList<Integer> getUnits_Id() throws SQLException {
		ArrayList<Integer> list = new ArrayList<>();
		while (res.next()) {
			list.add(res.getInt(SaderUnits.UNIT_id_COL));
		}
		return list;
	}

	public ArrayList<Integer> getTashiraId() throws SQLException {
		ArrayList<Integer> list = new ArrayList<>();
		while (res.next()) {
			list.add(res.getInt(Motb3a_tashira_ids.TASHIRA_COL));
		}
		return list;
	}

	public ArrayList<Integer> getMotb3aIds() throws SQLException {
		ArrayList<Integer> list = new ArrayList<>();
		while (res.next()) {
			list.add(res.getInt(Motb3a_db.ID_COL));
		}
		return list;
	}
	public ArrayList<Date> getIstagelDate() throws SQLException {
		ArrayList<Date> list = new ArrayList<>();
		while (res.next()) {
			list.add(res.getDate(istagel.DATE_COL));
		}
		return list;
	}
	public ArrayList<Eltezam> getEltezamat() throws SQLException {
		ArrayList<Eltezam> list = new ArrayList<Eltezam>();
		while (res.next()) {
			Eltezam tmp = new Eltezam();
			tmp.setType(res.getInt(Eltezam.TYPE_COL));
			tmp.setSeq(res.getInt(Eltezam.SEQ_COL));
			tmp.setTrans_out_num(res.getInt(Eltezam.TRANS_OUT_NUM_COL));
			tmp.setDate(res.getString(Eltezam.DATE_COL));
			tmp.setTime(res.getString(Eltezam.TIME_COL));
			tmp.setTitle(res.getString(Eltezam.TITLE_COL));
			tmp.setPlace(res.getString(Eltezam.PLACE_COL));
			tmp.setTrans_out_date(res.getString(Eltezam.TRANS_OUT_DATE_COL));
			tmp.setUnit(res.getString(Eltezam.UNIT_COL));
			list.add(tmp);
		}
		return list;
	}

	public ArrayList<Tashira> getTashira() throws SQLException {
		ArrayList<Tashira> list = new ArrayList<>();
		while (res.next()) {
			Tashira tash = new Tashira();
			tash.setId(res.getInt(Tashira.ID_COL));
			tash.setName(res.getString(Tashira.NAME_COL));
			list.add(tash);
		}
		return list;
	}
	public ArrayList<istagel> getIsatgel() throws SQLException {
		ArrayList<istagel> list = new ArrayList<>();
		while (res.next()) {
			istagel tash = new istagel();
			tash.setId(res.getInt(istagel.ID_COL));
			tash.setUnit(res.getInt(istagel.UNIT_COL));;
			tash.setDate(res.getDate(istagel.DATE_COL));
			list.add(tash);
		}
		return list;
	}
	public ArrayList<Sub_br> getSub_br() throws SQLException {
		ArrayList<Sub_br> list = new ArrayList<>();
		while (res.next()) {
			Sub_br save = new Sub_br();
			save.setId(res.getInt(Sub_br.ID_COL));
			save.setName(res.getString(Sub_br.NAME_COL));
			list.add(save);
		}
		return list;
	}

	public ArrayList<Sub_Main> getSub_Main() throws SQLException {
		ArrayList<Sub_Main> list = new ArrayList<>();
		while (res.next()) {
			Sub_Main save = new Sub_Main();
			save.setId(res.getInt(Sub_Main.ID_COL));
			save.setName(res.getString(Sub_Main.NAME_COL));
			list.add(save);
		}
		return list;
	}

	public ArrayList<Resp_unit> getResp_unit() throws SQLException {
		ArrayList<Resp_unit> list = new ArrayList<>();
		while (res.next()) {
			Resp_unit save = new Resp_unit();
			save.setId(res.getInt(Resp_unit.ID_COL));
			save.setUnit(res.getInt(Resp_unit.UNIT_COL));
			save.setStatus(res.getInt(Resp_unit.STATUS_COL));
			save.setResp_date(res.getDate(Resp_unit.RESP_DATE_COL));
			list.add(save);
		}
		return list;
	}
	public int getResp_unit_status() throws SQLException {
//		ArrayList<Integer> list = new ArrayList<>();
//		while (res.next()) {
//			list.add(res.getInt(Resp_unit.STATUS_COL));
//		}
		res.next();
		return res.getInt(Resp_unit.STATUS_COL);
	}
	public ArrayList<Integer> getResp_unit_id() throws SQLException {
		ArrayList<Integer> list = new ArrayList<>();
		while (res.next()) {
//			int x=res.getInt(Resp_unit.UNIT_COL);
//			int z=x*10+res.getInt(Resp_unit.STATUS_COL);
			list.add(res.getInt(Resp_unit.UNIT_COL));
//			list.add(res.getInt(Resp_unit.STATUS_COL));
		}
		return list;
	}
	public ArrayList<Integer> getResp_unit_doc_id() throws SQLException {
		ArrayList<Integer> list = new ArrayList<>();
		while (res.next()) {
			list.add(res.getInt(Resp_unit.ID_COL));
		}
		return list;
	}
	public ArrayList<Integer> getdoc_id() throws SQLException {
		ArrayList<Integer> list = new ArrayList<>();
		while (res.next()) {
			list.add(res.getInt(WaredDocument.ID_COL));
		}
		return list;
	}
	public ArrayList<User> getUsers() throws SQLException {
		ArrayList<User> list = new ArrayList<User>();
		while (res.next()) {
			User usr = new User();
			usr.setId(res.getInt(User.ID_COL));
			usr.setUsername(res.getString(User.USERNAME_COL));
			usr.setPassword(res.getString(User.PASSWORD_COL));
			usr.setPrivilege_id(res.getInt(User.PRIVILEGE_ID_COL));
			usr.setCreated_at(res.getDate(User.CREATED_AT_COL));
			usr.setUpdated_at(res.getDate(User.UPDATED_AT_COL));
			usr.setGroup_id(res.getInt(User.GROUP_ID_COL));
			list.add(usr);
		}
		return list;
	}

	public ArrayList<Privilege> getPrivilege() throws SQLException {
		ArrayList<Privilege> list = new ArrayList<Privilege>();
		while (res.next()) {
			Privilege pr = new Privilege();
			pr.setId(res.getInt(Privilege.ID_COL));
			pr.setDescription(res.getString(Privilege.DESCRIPTION_COL));
			pr.setCannadd(res.getInt(Privilege.CANADD_COL) == 1);
			pr.setCandelete(res.getInt(Privilege.CANDELETE_AT_COL) == 1);
			pr.setCanupdate(res.getInt(Privilege.CANUPDATE_AT_COL) == 1);
			pr.setUpdated_at(res.getDate(Privilege.UPDATED_AT_COL));
			pr.setCreated_at(res.getDate(Privilege.CREATED_AT_COL));
			list.add(pr);

		}
		return list;
	}

	public ArrayList<SavedFile> getSavedFiles() throws SQLException {
		ArrayList<SavedFile> list = new ArrayList<SavedFile>();
		while (res.next()) {
			SavedFile gr = new SavedFile();
			gr.setCode(res.getInt(SavedFile.CODE_COL));
			gr.setName(res.getString(SavedFile.NAME_COL));
			gr.setFile_type(res.getInt(SavedFile.FILE_TYP_COL));
			list.add(gr);
		}
		return list;
	}

	public ArrayList<motb3a_Tashirat_name> getmotb3a_tashirat_name() throws SQLException {
		ArrayList<motb3a_Tashirat_name> list = new ArrayList<motb3a_Tashirat_name>();
		while (res.next()) {
			motb3a_Tashirat_name gr = new motb3a_Tashirat_name();
			gr.setId(res.getInt(motb3a_Tashirat_name.ID_COL));
			gr.setName(res.getString(motb3a_Tashirat_name.NAME_COL));
			list.add(gr);
		}
		return list;
	}

	public ArrayList<Motb3a_tashira_ids> getMotb3a_tashira_ids() throws SQLException {
		ArrayList<Motb3a_tashira_ids> list = new ArrayList<Motb3a_tashira_ids>();
		while (res.next()) {
			Motb3a_tashira_ids gr = new Motb3a_tashira_ids();
			gr.setId(res.getInt(Motb3a_tashira_ids.ID_COL));
			gr.setTashira_id(res.getString(Motb3a_tashira_ids.TASHIRA_COL));
			gr.setTarget(res.getInt(Motb3a_tashira_ids.TARGET_COL));
			list.add(gr);
		}
		return list;
	}

	public ArrayList<Motb3a_db> getMotb3a_db() throws SQLException {
		ArrayList<Motb3a_db> list = new ArrayList<Motb3a_db>();
		while (res.next()) {
			Motb3a_db gr = new Motb3a_db();
			gr.setId(res.getInt(Motb3a_db.ID_COL));
			gr.setDoc_date(res.getDate(Motb3a_db.DOC_DATE_COL));
			gr.setTarget_id(res.getInt(Motb3a_db.TARGET_COL));
			list.add(gr);
		}
		return list;
	}

	public ArrayList<Group> getGroups() throws SQLException {
		ArrayList<Group> list = new ArrayList<Group>();
		while (res.next()) {
			Group gr = new Group();
			gr.setId(res.getInt(Group.ID_COL));
			gr.setName(res.getString(Group.NAME_COL));
			gr.setUpdated_at(res.getDate(Group.UPDATED_AT_COL));
			gr.setCreated_at(res.getDate(Group.CREATED_AT_COL));
			list.add(gr);

		}
		return list;
	}

	public ArrayList<SaderDocument> getSaderDocuments() throws SQLException {
		ArrayList<SaderDocument> list = new ArrayList<SaderDocument>();
		while (res.next()) {
			SaderDocument doc = new SaderDocument();
			// Integers
			doc.setId(res.getInt(SaderDocument.ID_COL));
			doc.setDoc_number(res.getInt(SaderDocument.DOC_NUM_COL));
			doc.setDoc_type(res.getInt(SaderDocument.DOC_TYPE_COL));
			doc.setUnit_id(res.getString(SaderDocument.UNIT_ID_COL));
			doc.setFile_save_num(res.getInt(SaderDocument.FILE_SAVE_NUM_COL));
			doc.setSub_file_save_num(res.getInt(SaderDocument.SUB_FILE_SAVE_NUM_COL));
			doc.setTashira(res.getInt(SaderDocument.TASHIRA_COL));
			doc.setTarget_id(res.getInt(SaderDocument.TARGET_ID_COL));
			doc.setSecurity_type(res.getInt(SaderDocument.SECURITY_TYPE_COL));
			// Strings
			doc.setTitle(res.getString(SaderDocument.TITLE_COL));
			doc.setPrev_doc_id(res.getString(SaderDocument.PREV_DOC_ID_COL));
			doc.setUrl(res.getString(SaderDocument.URL_COL));
			// Dates
			doc.setdoc_date(res.getDate(SaderDocument.DOC_DATE_COL));
			doc.setstart_date(res.getDate(SaderDocument.START_DATE_COL));
			doc.setFinish_date(res.getDate(SaderDocument.FINISH_DATE_COL));
			// ArrayList<String> arr = new ArrayList<String>();
			// String url = Environment.LAPPATH + doc.getUrl();
			// if (new File(url).isDirectory()) {
			// File f[] = new File(url).listFiles();
			// for (int i = 0; i < f.length; i++) {
			// if (f[i].getName().endsWith(".jpg"))
			// arr.add(f[i].getAbsolutePath().replace(Environment.LAPPATH, ""));
			// }
			// } else {
			// System.out.println("FILE NOT FOUND");
			// }
			//
			// doc.setImgs(arr);
			ArrayList<String> arr = ImageUtils.getListOfImages(doc.getUrl());
			doc.setImgs(arr);
			String quary = "SELECT unit_id FROM sader_dest_unit where doc_number=" + doc.getDoc_number()
					+ " and doc_date like '%" + doc.getdoc_date() + "%';";
			SeLect s = new SeLect();
			s.openConnection(quary);
			doc.setUnits_sader(s.getUnits_Id());
			s.closeConnection();
			list.add(doc);
		}
		return list;
	}

	public ArrayList<WaredDocument> getDocuments() throws SQLException {
		ArrayList<WaredDocument> list = new ArrayList<WaredDocument>();
		while (res.next()) {
			WaredDocument doc = new WaredDocument();
			// Integers
			doc.setId(res.getInt(WaredDocument.ID_COL));
			doc.setDoc_number(res.getInt(WaredDocument.DOC_NUM_COL));
			doc.setDoc_type(res.getInt(WaredDocument.DOC_TYPE_COL));
			doc.setStatus_id(res.getInt(WaredDocument.STATUS_ID_COL));
			doc.setTarget_id(res.getInt(WaredDocument.TARGET_ID_COL));
			doc.setDoc_out_number(res.getInt(WaredDocument.DOC_OUT_NUMBER_COL));
			doc.setDoc_save_number(res.getInt(WaredDocument.DOC_SAVE_NUMBER_COL));
			doc.setDoc_need_replay(res.getInt(WaredDocument.DOC_NEED_REPLY_COL));
			doc.setUnit_id(res.getInt(WaredDocument.UNIT_ID_COL));
			doc.setSub_unit_id(res.getInt(WaredDocument.SUB_UNIT_ID_COL));
			doc.setTrans_out_num(res.getInt(WaredDocument.TRANS_OUT_NUM_COL));

			// Strings
			doc.setTitle(res.getString(WaredDocument.TITLE_COL));
			doc.setPrev_doc_id(res.getString(WaredDocument.PREV_DOC_ID_COL));
			doc.setUrl(res.getString(WaredDocument.URL_COL));
			doc.setNotes(res.getString(WaredDocument.NOTES_COL));

			// Dates
			doc.setCreated_at(res.getDate(WaredDocument.CREATED_AT_COL));
			doc.setUpdated_at(res.getDate(WaredDocument.UPDATED_AT_COL));
			doc.setDoc_out_date(res.getDate(WaredDocument.DOC_OUT_DATE_COL));
			doc.setTrans_out_date(res.getDate(WaredDocument.TRANS_OUT_DATE_COL));
			ArrayList<String> arr = ImageUtils.getListOfImages(doc.getUrl());
			doc.setImgs(arr);

			// tashira
			String quary = "SELECT tashira FROM Motb3a_tashira where id=" + res.getInt(WaredDocument.ID_COL)+" and target=2";
			SeLect s = new SeLect();
			s.openConnection(quary);
			doc.setTashira(s.getTashiraId());
			s.closeConnection();
			// tashira_quad
						String quary_q = "SELECT tashira FROM Motb3a_tashira where id=" + res.getInt(WaredDocument.ID_COL)+" and target=1";
						SeLect s_q = new SeLect();
						s_q.openConnection(quary_q);
						doc.setTashira_qaud(s_q.getTashiraId());
						s_q.closeConnection();
			//resp_units
			String quary2 = "SELECT unit FROM Resp_unit where id=" + res.getInt(WaredDocument.ID_COL);
			SeLect s2 = new SeLect();
			s2.openConnection(quary2);
			doc.setUnits_resp(s2.getResp_unit_id());
			s2.closeConnection();
			String quary4 = "SELECT * FROM Resp_unit where id=" + res.getInt(WaredDocument.ID_COL);
			SeLect s4 = new SeLect();
			s4.openConnection(quary4);
			doc.setResp_unit(s4.getResp_unit());
			s4.closeConnection();
			String quary3 = "SELECT * FROM istagel where id=" + doc.getId();
			s.openConnection(quary3);
			doc.setEstagel(s.getIsatgel());
			s.closeConnection();
			list.add(doc);
		}

		return list;
	}
	public ArrayList<WaredDocument> getDocumentsSearch() throws SQLException {
		ArrayList<WaredDocument> list = new ArrayList<WaredDocument>();
		while (res.next()) {
			WaredDocument doc = new WaredDocument();
			// Integers
			doc.setId(res.getInt(WaredDocument.ID_COL));
			doc.setDoc_number(res.getInt(WaredDocument.DOC_NUM_COL));
			doc.setDoc_type(res.getInt(WaredDocument.DOC_TYPE_COL));
			doc.setStatus_id(res.getInt(WaredDocument.STATUS_ID_COL));
//			doc.setTarget_id(res.getInt(WaredDocument.TARGET_ID_COL));
			doc.setDoc_out_number(res.getInt(WaredDocument.DOC_OUT_NUMBER_COL));
//			doc.setDoc_save_number(res.getInt(WaredDocument.DOC_SAVE_NUMBER_COL));
//			doc.setDoc_need_replay(res.getInt(WaredDocument.DOC_NEED_REPLY_COL));
			doc.setUnit_id(res.getInt(WaredDocument.UNIT_ID_COL));
//			doc.setSub_unit_id(res.getInt(WaredDocument.SUB_UNIT_ID_COL));
			doc.setTrans_out_num(res.getInt(WaredDocument.TRANS_OUT_NUM_COL));

			// Strings
			doc.setTitle(res.getString(WaredDocument.TITLE_COL));
			doc.setPrev_doc_id(res.getString(WaredDocument.PREV_DOC_ID_COL));
			doc.setUrl(res.getString(WaredDocument.URL_COL));
//			doc.setNotes(res.getString(WaredDocument.NOTES_COL));

			// Dates
			doc.setCreated_at(res.getDate(WaredDocument.CREATED_AT_COL));
//			doc.setUpdated_at(res.getDate(WaredDocument.UPDATED_AT_COL));
//			doc.setDoc_out_date(res.getDate(WaredDocument.DOC_OUT_DATE_COL));
			doc.setTrans_out_date(res.getDate(WaredDocument.TRANS_OUT_DATE_COL));
//			ArrayList<String> arr = ImageUtils.getListOfImages(doc.getUrl());
//			doc.setImgs(arr);
//
//			// tashira
//			String quary = "SELECT tashira FROM Motb3a_tashira where id=" + res.getInt(WaredDocument.ID_COL);
//			SeLect s = new SeLect();
//			s.openConnection(quary);
//			doc.setTashira(s.getTashiraId());
//			s.closeConnection();
//			//resp_units
//			String quary2 = "SELECT unit FROM Resp_unit where id=" + res.getInt(WaredDocument.ID_COL);
//			SeLect s2 = new SeLect();
//			s2.openConnection(quary2);
//			doc.setUnits_resp(s2.getResp_unit_id());
//			s2.closeConnection();
//			String quary4 = "SELECT * FROM Resp_unit where id=" + res.getInt(WaredDocument.ID_COL);
//			SeLect s4 = new SeLect();
//			s4.openConnection(quary4);
//			doc.setResp_unit(s4.getResp_unit());
//			s4.closeConnection();
//			String quary3 = "SELECT * FROM istagel where id=" + doc.getId();
//			s.openConnection(quary3);
//			doc.setEstagel(s.getIsatgel());
//			s.closeConnection();
			list.add(doc);
		}

		return list;
	}
	public ArrayList<Status> getStatus() throws Exception {
		ArrayList<Status> list = new ArrayList<Status>();
		while (res.next()) {
			Status st = new Status();
			st.setId(res.getInt(Status.ID_COL));
			st.setName(res.getString(Status.NAME_COL));
			list.add(st);

		}
		return list;
	}

	public ArrayList<Target> getTarget() throws Exception {
		ArrayList<Target> list = new ArrayList<Target>();
		while (res.next()) {
			Target tr = new Target();
			tr.setId(res.getInt(Target.ID_COL));
			tr.setName(res.getString(Target.NAME_COL));
			list.add(tr);
		}
		return list;
	}

	public ArrayList<Units> getUnits() throws Exception {
		ArrayList<Units> list = new ArrayList<Units>();
		while (res.next()) {
			Units tr = new Units();
			tr.setId(res.getInt(Units.ID_COL));
			tr.setName(res.getString(Units.NAME_COL));
			tr.setUnit_type(res.getInt(Units.UNIT_TYPE_COL));
			list.add(tr);
		}
		return list;
	}

	public ArrayList<SaderUnitsName> getSaderUnitsName() throws Exception {
		ArrayList<SaderUnitsName> list = new ArrayList<>();
		while (res.next()) {
			SaderUnitsName tr = new SaderUnitsName();
			tr.setId(res.getInt(SaderUnitsName.ID_COL));
			tr.setName(res.getString(SaderUnitsName.NAME_COL));
			tr.setUnit_type(res.getInt(SaderUnitsName.UNIT_TYPE_COL));
			list.add(tr);
		}
		return list;
	}

	public void closeConnection() throws SQLException {
		con.close();
		res.close();
	}

	public ArrayList<WaredDocument> getminiDocuments() throws SQLException {
		ArrayList<WaredDocument> list = new ArrayList<WaredDocument>();
		while (res.next()) {
			WaredDocument doc = new WaredDocument();
			// Integers
			doc.setId(res.getInt(WaredDocument.ID_COL));
			doc.setDoc_number(res.getInt(WaredDocument.DOC_NUM_COL));
			doc.setDoc_type(res.getInt(WaredDocument.DOC_TYPE_COL));
			doc.setDoc_out_number(res.getInt(WaredDocument.DOC_OUT_NUMBER_COL));
			doc.setStatus_id(res.getInt(WaredDocument.STATUS_ID_COL));
			// Strings
			doc.setTitle(res.getString(WaredDocument.TITLE_COL));
			// Dates
			doc.setCreated_at(res.getDate(WaredDocument.CREATED_AT_COL));
			list.add(doc);
		}
		return list;
	}

}
