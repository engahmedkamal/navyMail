package navymail.rest.Wared;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import navymail.API.WaredAPI.WaredDocumentAPI;
import navymail.Helpers.Master.MyResponse;
import navymail.Helpers.Wared.CreateFileSystem;
import navymail.Helpers.Wared.Create_Parser;
import navymail.Helpers.Wared.DocUtils;
import navymail.Helpers.Wared.ImageUtils;
import navymail.Helpers.Wared.Pagination;
import navymail.Helpers.Wared.Search_parser;
import navymail.Helpers.Wared.UpdateFileSystem;
import navymail.Helpers.Wared.Update_parser;
import navymail.Queries.Insert;
import navymail.Queries.SeLect;
import navymail.Queries.Update;
import navymail.models.Wared.WaredDocument;
import navymail.params.Wared.CreateParams;
import navymail.params.Wared.UpdateParams;
import navymail.util.Environment;
import navymail.util.Jason;
import navymail.util.log.Loggable;

@Path("/documents")
public class Documents extends Loggable implements WaredDocumentAPI {

	String comment1 = "search request attemp check Search_parser for more details, connect to DB with Query=(%s) ";
	String comment2 = "(%d) documents that match the search query ";
	String comment3 = "a user request page number =(%d) , with size = (%d)  ";

	public Response search(UriInfo info){
		try{
		Search_parser parser = new Search_parser(info);

		String query = parser.getQuery();
//		logger.info(String.format(comment1, query));

		if (query.equals("")) {
//			logger.info("something goes wrong");
			return new MyResponse().failed();
		}

		SeLect q = new SeLect();
		q.openConnection(query);
		// YES we get back all the fucken documents
//		ArrayList<WaredDocument> res = q.getDocumentsSearch();
		ArrayList<WaredDocument> res = q.getDocuments();
		q.closeConnection();
//		logger.info(String.format(comment2, res.size()));

		int pnum = parser.getPage_num();
		int psize = parser.getPage_size();
//		logger.info(String.format(comment3, pnum, psize));

		Pagination page = new Pagination(res, pnum, psize);
		String output = page.toString();

		return new MyResponse(output).success();
		}catch(Exception e){
			e.printStackTrace();
			return new MyResponse().failed();
		}

	}
	
	
	public Response search_motb3a(UriInfo info) {
		try{
		Search_parser parser = new Search_parser(info);

		String query = parser.getQuery();
//		logger.info(String.format(comment1, query));
//		System.out.println(query);
		if (query.equals("")) {
//			logger.info("something goes wrong");
			return new MyResponse().failed();
		}

		SeLect q = new SeLect();
		String qu=query.replace(") ORDER BY created_at DESC LIMIT 100", " and doc_need_replay >=1 ) ORDER BY created_at DESC LIMIT 100");
		System.out.println(qu);
		q.openConnection(qu);
		//q.openConnection(query.substring(0, query.length()-9));
		// YES we get back all the fucken documents
		ArrayList<WaredDocument> res = q.getDocuments();
		q.closeConnection();
//		ArrayList<WaredDocument> out=new ArrayList<>();
//		SeLect motba = new SeLect();
//		for (WaredDocument doc : res) {
//			String quary="select * from Motb3a where id="+doc.getId();
//			motba.openConnection(quary);
//			if(!(motba.getMotb3a_db().size()>0)){
//				motba.closeConnection();
//				continue;}
//			motba.closeConnection();
//			out.add(doc);
//			
//		}
//		if(out.size()>100){
//			out=(ArrayList<WaredDocument>) out.subList(0, 99);
//		}
//		logger.info(String.format(comment2, res.size()));
//		System.err.println("res"+res.size()+"    out"+out.size());
		int pnum = parser.getPage_num();
		int psize = parser.getPage_size();
//		logger.info(String.format(comment3, pnum, psize));

		Pagination page = new Pagination(res, pnum, psize);
		String output = page.toString();

		return new MyResponse(output).success();
		}catch(Exception e){
			e.printStackTrace();
			return new MyResponse().failed();
		}
	}

	String comment4 = "Request document with id = (%d)";
	String comment5 = "Database reply with (%d) documents";
	String comment6 = "Document (%d) connected to (%s)";

	public Response show(int id){
		try{
//		logger.info(String.format(comment4, id));
		System.out.println("................id.........."+id);
		String query = "select * from documents where id = " + id;
		SeLect sc = new SeLect();
		sc.openConnection(query);
		ArrayList<WaredDocument> res = sc.getDocuments();
		sc.closeConnection();
		//System.out.println(res.get(0));
//		logger.info(String.format(comment5, res.size()));

		if (res.size() == 0)
			new MyResponse().failed();

		WaredDocument doc = res.get(0);
		// TODO modify db to contailn parent link <herf> tag for linking
		String prev_id = doc.getPrev_doc_id();
		logger.info(String.format(comment6, id, prev_id));

		doc.setParents(DocUtils.getLinks(prev_id));
		doc.setAllPreviousTarbet(ImageUtils.getAllImgs(id));
		String output = Jason.toJson(res);

		return new MyResponse(output).success();
	}catch(Exception e){
		e.printStackTrace();
		return new MyResponse().failed();
	}
	}

	String comment7 = "create new document with  prev_id=(%s),  img_no=(%d),  title=(%s),"
			+ " doc_num=(%d), created_at=(%s),  doc_out_num=(%d), doc_out_date(%s),  unit_id=(%d),"
			+ "  doc_type=(%d),  target_id=(%d), status_id=(%d)";
	String comment8 = "JUMP INTO Create_Parser";

	String comment9 = "connect to Database with query=(%s)";
	String comment10 = "JUMP INTO UpdateFileSystem";

	public Response create(int folder_id, String prev_id, int img_no, String title, int doc_num, String created_at,
			int doc_out_num, String doc_out_date, int unit_id, int doc_type, int target_id, int status_id)
					throws Exception {

		CreateParams query_params = new CreateParams();
		query_params.folder_id = folder_id;
		query_params.prev_id = prev_id;
		query_params.img_no = img_no;
		query_params.title = title;
		query_params.doc_num = doc_num;
		query_params.created_at = created_at;
		query_params.doc_out_num = doc_out_num;
		query_params.doc_out_date = doc_out_date;
		query_params.unit_id = unit_id;
		query_params.doc_type = doc_type;
		query_params.target_id = target_id;
		query_params.status_id = status_id;

		logger.info(String.format(comment7, prev_id, img_no, title, doc_num, created_at, doc_out_num, doc_out_date,
				unit_id, doc_type, target_id, status_id));

		logger.info(comment8);

		Create_Parser data = new Create_Parser();
		String query = data.get_query(query_params);
		String Select_query = "SELECT id FROM documents where doc_number=" + doc_num + " and created_at like '%"
				+ created_at.substring(0,4)+ "%' and doc_type=" + doc_type;
		SeLect s = new SeLect();
		s.openConnection(Select_query);
		System.out.println(Select_query);
		if (s.getdoc_id().size() == 0) {
			new Insert(query);
			logger.info(String.format(comment9, query));
			System.out.println("done");
			logger.info(comment10);
			// work with fileSystem
			new CreateFileSystem(query_params);
			s.closeConnection();
			return new MyResponse().success();
		} else {
			System.out.println("faild");
			s.closeConnection();
			return new MyResponse().failed();
		}
	}

	String comment11 = "Update document with id =(%d)";
	String comment12 = "document title =(%s), document number = (%d) , target id = (%d) and status id=(%d)";
	String comment13 = "CHANGE IN DATABASE WITH QUERY=(%S)";

	public Response update(int id, String prev_id, String title, int doc_num, String created_at, int doc_out_num,
			String doc_out_date, int unit_id, int trans_out_num, String trans_out_date, int doc_type, int target_id,
			int status_id, String notes , String tashira , String tashira_quad,String resp_unit) throws Exception {

		logger.info(String.format(comment11, id));
		UpdateParams query_params = new UpdateParams();
		query_params.prev_id = prev_id;
		query_params.title = title;
		query_params.doc_num = doc_num;
		query_params.created_at = created_at;
		query_params.doc_out_num = doc_out_num;
		query_params.doc_out_date = doc_out_date;
		query_params.unit_id = unit_id;
		query_params.doc_type = doc_type;
		query_params.target_id = target_id;
		query_params.status_id = status_id;
		query_params.trans_out_date = trans_out_date;
		query_params.trans_out_num = trans_out_num;
		query_params.notes = notes;
		query_params.tashira = tashira.trim().split(" ");
		query_params.tashira_Quad=tashira_quad.trim().split(" ");
		query_params.Resp_unit=resp_unit.trim().split(" ");
		logger.info(String.format(comment12, title, doc_num, target_id, status_id));

		UpdateFileSystem updatefs = new UpdateFileSystem();

		WaredDocument d = DocUtils.findDocById(id);
		// CASE#1 change title or doc number
		String dtitle = d.getTitle();
		int dnum = d.getDoc_number();

		String oldfolder = dnum + " " + dtitle;
		String newfolder = doc_num + " " + title;

		int dtype = d.getDoc_type();
		int dstatus = d.getStatus_id();
		int dtarget = d.getTarget_id();
		ArrayList<Integer> arr = d.getTashira();
		ArrayList<Integer> arr_quad = d.getTashira_qaud();
		ArrayList<Integer> arr_resp_unit = d.getUnits_resp();
		String currFolder = oldfolder;
		int check = 0;
		int flag=0;
		HashSet<Integer> arry_remove=new HashSet<>();
		HashSet<Integer> arry_add=new HashSet<>();
		HashSet<Integer> arry_unit=new HashSet<>();
		
		if( (!arr.equals(query_params.tashira)&&!query_params.tashira.equals("-1")||
				(!arr_quad.equals(query_params.tashira_Quad)&&!query_params.tashira_Quad.equals("-1")))){
			check++;
		}
		if( (!arr_resp_unit.equals(query_params.Resp_unit)&&!query_params.Resp_unit.equals("-1"))){
			flag++;
			Collections.sort(arr_resp_unit);
			Arrays.sort(query_params.Resp_unit);
			for (Iterator iterator = arr_resp_unit.iterator(); iterator.hasNext();) {
				Integer integer = (Integer) iterator.next();
				arry_remove.add(integer);
				arry_unit.add(integer);
				for (int i = 0; i < query_params.Resp_unit.length; i++) {
					if(integer==Integer.parseInt(query_params.Resp_unit[i])){
						arry_remove.remove(integer);
						break;
					}
				}
			}
			for (int i = 0; i < query_params.Resp_unit.length; i++) {
				if(!arry_unit.contains(Integer.parseInt(query_params.Resp_unit[i]))){
					arry_add.add(Integer.parseInt(query_params.Resp_unit[i]));
				}
			}
			
		}
		if (!oldfolder.equals(newfolder)) {
			logger.info("CHANGE FOLDER NAME");
			updatefs.renameFolder(oldfolder, newfolder, d.getUrl(), dstatus, dtype, dtarget);
			// set url
			String newUrl = d.getUrl().replace(oldfolder, newfolder);

			d.setUrl(newUrl);
			// set doc num
			d.setDoc_number(doc_num);
			// set title
			d.setTitle(title);
			dnum = doc_num;
			currFolder = newfolder;
			// to update url
			query_params.url = d.getUrl();
		}

		// CASE#2 change type
		if (doc_type != d.getDoc_type()) {
			logger.info("CHANGE DOCUMENT TYPE");
			String newURL = updatefs.change_type(currFolder, dstatus, dtype, dtarget, d.getUrl());
			d.setDoc_type(doc_type);
			d.setUrl(newURL);
			query_params.url = newURL;
		}

		// special case
		if (dstatus != status_id && dtarget != target_id) {
			if (dstatus == Environment.STATUS_FINISHED || dstatus == Environment.STATUS_MO3LLAK) {
				if (dtarget == Environment.TARGET_3MEED || dtarget == Environment.TARGET_KA2ED_ARKAN) {
					d.setTarget_id(target_id);
					dtarget = target_id;
				}
			}
		}

		// CASE#3 change status
		if (dstatus != status_id) {
			logger.info("CHANGE DOCUMENT STATUS");
			updatefs.change_status(currFolder, d, status_id);
			d.setStatus_id(status_id);
		}
		// Case#4 change target

		if (dtarget != target_id) {
			logger.info("CHANGE TARGET ID");
			updatefs.change_target(currFolder, d, target_id);
		}

		// Case#5 change time
		String new_year = created_at.substring(0, 4);
		String dyear = d.getCreated_at().toString().substring(0, 4);
		if (!new_year.equals(dyear)) {
			logger.info("CHAGE Created Year (Wared) YEAR");
			String url = d.getUrl().replace(dyear, new_year);
			File f = new File(d.getUrl());
			File f2 = new File(url);
			DocUtils.assert_exist_path(f2.getAbsolutePath());
			f.renameTo(f2);
			d.setUrl(url);
		}
		query_params.url = d.getUrl();
		Iterator<Integer> arr_remove=arry_remove.iterator();
		Iterator<Integer> arr_add=arry_add.iterator();
//		Integer.parseInt(arr_add);
		Update_parser data = new Update_parser();
		ArrayList<String> query = data.get_query(query_params, id,check,flag,arr_add,arr_remove);
		logger.info(String.format(comment13, query));
		for (String string : query) {
			System.out.println(string);
			new Update(string);
		}

		return show(id);
	}

	public Response print(int id)  {
		try {
			WaredDocument d = DocUtils.findDocById(id);

			DocUtils.print(d.getUrl());
		} catch (Exception e) {
			return new MyResponse().failed();
		}
		return new MyResponse().success();
	}

	public Response show_document(int id)  {
		return show(id);
	}

	public Response update(int id, int target_id, int status_id) throws Exception {

		WaredDocument d = DocUtils.findDocById(id);
		String dtitle = d.getTitle();
		int dnum = d.getDoc_number();
		String currFolder = dnum + " " + dtitle;
		UpdateFileSystem updatefs = new UpdateFileSystem();
		// CASE#3 change status
		if (d.getStatus_id() != status_id) {
			updatefs.change_status(currFolder, d, status_id);
			d.setStatus_id(status_id);
		}
		// Case#4 change target

		if (d.getTarget_id() != target_id) {
			updatefs.change_target(currFolder, d, target_id);
		}

		String query = String.format("UPDATE documents SET  target_id=%d, status_id=%d where id =%d", target_id,
				status_id, id);
		new Update(query);

		return new MyResponse().success();
	}

	public Response minisearch(int doc_num, int doc_out_num) throws Exception {
		// no data
		if (doc_num == 0 && doc_out_num == 0)
			return new MyResponse().success();

		String str = new String();
		if (doc_num != 0) {
			str = " ( doc_number = " + doc_num;
			if (doc_out_num != 0)
				str += " and doc_out_number = " + doc_out_num;

			str += " )";
		} else {
			str = " doc_out_number = " + doc_out_num;
		}
		String query = "select * from documents where " + str + " ORDER BY created_at DESC LIMIT 20";
		System.out.println(query);
		SeLect q = new SeLect();
		q.openConnection(query);
		// YES we get back all the fucken documents
		ArrayList<WaredDocument> res = q.getminiDocuments();
		q.closeConnection();

		String output = Jason.toJson(res);

		return new MyResponse(output).success();

	}

///////////////////// Motba Update //////////
	@Override
	public Response update(int id, int status, int unit ,String date , int answer,String note)  {
		try{
		if(status==1)
		new Update(String.format("UPDATE Resp_unit SET status=%d , resp_date=\'%s\' where id =%d and unit=%d",status,"",id,unit));
		else
		new Update(String.format("UPDATE Resp_unit SET status=%d , resp_date=\'%s\' where id =%d and unit=%d",status,date,id,unit));
		if(note!=null)
		new Update(String.format("UPDATE documents SET  notes=\'%s\' where id =%d",note, id));
		new Update(String.format("UPDATE documents SET  doc_need_replay=%d where id =%d",answer, id));
		return show(id);
		}catch(Exception e){
			e.printStackTrace();
			return new MyResponse().failed();
		}
	}


	@Override
	public Response update(int id, String date, int unit)  {
		new Insert(String.format("INSERT INTO istagel (id,date,unit) Values ( %d, \'%s\',%d)",id,date,unit));
		return show(id);
	}


	@Override
	public Response delete(int id, String date, int unit) {
		String s="DELETE FROM  istagel where id= "+id+" and  date like '%"+date+"%' and unit="+unit;
		System.err.println(s);
		new Update(s);
		return show(id);
	}

}
