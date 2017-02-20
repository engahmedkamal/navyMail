package navymail.rest.Sader;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import navymail.API.SaderAPI.SaderDocumentAPI;
import navymail.Helpers.Master.MyResponse;
import navymail.Helpers.Sader.Pagination_Sader;
import navymail.Helpers.Sader.SaderFileSys;
import navymail.Helpers.Sader.Sader_Parser;
import navymail.Helpers.Sader.Search_quary_creater;
import navymail.Helpers.Sader.Update_Sader_parse;
import navymail.Helpers.Wared.DocUtils;
import navymail.Helpers.Wared.ImageUtils;
import navymail.Helpers.Wared.UpdateFileSystem;
import navymail.Queries.Insert;
import navymail.Queries.SeLect;
import navymail.Queries.Update;
import navymail.models.Sader.SaderDocument;
import navymail.params.Sader.SaderPram;
import navymail.params.Sader.UpdateSaderparam;
import navymail.util.Jason;

@Path("SaderDocuments")
public class Documents implements SaderDocumentAPI {

	public Response create(int folder_id, String prev_id, int img_no, String title, int doc_num, String doc_date,
			int unit_id, int doc_type, int file_save_num, int sub_file_save_num, int tashira, int target_id,
			int security_type, String unit_ids) throws Exception {
		int i = 0;
		SaderPram query_params = new SaderPram();
		query_params.folder_id = folder_id;
		query_params.prev_id = prev_id;
		query_params.img_no = img_no;
		query_params.title = title;
		query_params.doc_num = doc_num;
		query_params.doc_date = doc_date;
		query_params.unit_id = unit_id;
		query_params.doc_type = doc_type;
		query_params.file_save_num = file_save_num;
		query_params.sub_file_save_num = sub_file_save_num;
		query_params.tashira = tashira;
		query_params.target_id = target_id;
		query_params.security_type = security_type;
		query_params.unit_ids = unit_ids.trim().split(" ");
		Sader_Parser data = new Sader_Parser();
		ArrayList<String> query = data.get_query(query_params);
		System.out.println(query);
		String select_query = "SELECT * FROM sader_documents where doc_number=" + query_params.doc_num
				+ " and doc_date like '%" + doc_date.substring(0, 4) + "%';";
		SeLect s_q = new SeLect();
		System.out.println(select_query);
		s_q.openConnection(select_query);
		if (s_q.getSaderDocuments().size() == 0) {
			for (String string : query) {
				System.out.println(i++ + "           " + string);
				new Insert(string);
			}
		} else {
			System.out.println("false");
			s_q.closeConnection();
			return new MyResponse().failed();
		}
		s_q.closeConnection();
		new SaderFileSys(query_params);
		System.out.println("done");
		return new MyResponse().success();
	}

	@Override
	public Response search(UriInfo inf) throws Exception {
		Search_quary_creater s = new Search_quary_creater();
		ArrayList<SaderDocument> sadr_records = s.search(inf);
		int pnum = s.getPage_num();
		int psize = s.getPage_size();
		// System.out.println("sadasdfdasdfasdfas"+sadr_records.size());
		Pagination_Sader page = new Pagination_Sader(sadr_records, pnum, psize);
		String output = page.toString();
		return new MyResponse(output).success();
	}

	public Response show(int id) throws Exception {

		String query = "select * from sader_documents where id = " + id;
		SeLect sc = new SeLect();
		sc.openConnection(query);
		ArrayList<SaderDocument> res = sc.getSaderDocuments();
		sc.closeConnection();

		if (res.size() == 0)
			new MyResponse().failed();

		SaderDocument doc = res.get(0);
		// TODO modify db to contailn parent link <herf> tag for linking
		String prev_id = doc.getPrev_doc_id();

		doc.setParents(DocUtils.getLinks(prev_id));
		doc.setAllPreviousTarbet(ImageUtils.getAllImgs(id));
		String output = Jason.toJson(res);

		return new MyResponse(output).success();

	}

	@Override
	public Response update(int id, String prev_id, String title, int doc_num, String doc_date, int units,
			int file_save_num, int sub_file_save_num, int doc_type, int target_id, int tashira, int security_type,
			String unit_ids) throws Exception {
		// TODO Auto-generated method stub
		UpdateSaderparam query_params = new UpdateSaderparam();
		query_params.prev_id = prev_id;
		query_params.title = title;
		query_params.doc_num = doc_num;
		query_params.doc_date = doc_date;
		query_params.unit_id = units;
		query_params.doc_type = doc_type;
		query_params.file_save_num = file_save_num;
		query_params.sub_file_save_num = sub_file_save_num;
		query_params.tashira = tashira;
		query_params.target_id = target_id;
		query_params.security_type = security_type;
		query_params.unit_ids = unit_ids.trim().split(" ");
		UpdateFileSystem updatefs = new UpdateFileSystem();
		SaderDocument doc = DocUtils.findDocByIdSader(id);
		Date ddate = doc.getdoc_date();
		int dnum = doc.getDoc_number();
		ArrayList<Integer> arr = doc.getUnits_sader();
		System.out.println("NOW WE UPDAAAAAAAAAAATE");
		System.out.println(query_params.unit_ids.length);
		int check = 0;
		
		if ((doc_num != dnum) || (!arr.equals(query_params.unit_ids)&&!query_params.unit_ids.equals("-1"))) {
			check++;
			System.out.println(query_params.unit_ids.toString() + "asdasd");
		}
		

		// if (arr.size() != query_params.unit_ids.length) {
		// check++;
		// } else {
		// for (Integer x : arr) {
		// for (int i = 0; i < query_params.unit_ids.length; i++) {
		// if (Integer.parseInt(query_params.unit_ids[i]) != x) {
		// check++;
		// break;
		// }
		// }
		// if (check > 0)
		// break;
		// }
		// }
		String oldfolder = dnum + " " + ddate;
		String newfolder = doc_num + " " + doc_date;
		int dtype = doc.getDoc_type();
		String currFolder = oldfolder;
		if (!oldfolder.equals(newfolder)) {
			updatefs.renameFolderSader(oldfolder, newfolder, doc.getUrl());
			// set url
			String newUrl = doc.getUrl().replace(oldfolder, newfolder);

			doc.setUrl(newUrl);
			// set doc num
			doc.setDoc_number(doc_num);
			// set title
			doc.setTitle(title);
			dnum = doc_num;
			currFolder = newfolder;
			// to update url
			query_params.url = doc.getUrl();
		}
		// CASE#2 change type
		if (doc_type != doc.getDoc_type()) {
			String newURL = updatefs.change_type_Sader(currFolder, dtype, doc.getUrl());
			doc.setDoc_type(doc_type);
			doc.setUrl(newURL);
			query_params.url = newURL;
		}
		// CASE 3 CHANGE YEAR
		String new_year = doc_date.substring(0, 4);
		String dyear = doc.getdoc_date().toString().substring(0, 4);
		if (!new_year.equals(dyear)) {
			String url = doc.getUrl().replace(dyear, new_year);
			File f = new File(doc.getUrl());
			File f2 = new File(url);
			DocUtils.assert_exist_path(f2.getAbsolutePath());
			f.renameTo(f2);
			doc.setUrl(url);
		}
		query_params.url = doc.getUrl();
		Update_Sader_parse update = new Update_Sader_parse();
		ArrayList<String> query = update.get_query(query_params, id, check);
		System.out.println(query.size());
		for (String string : query) {
			System.out.println(string);
			new Update(string);
		}

		return show(id);
	}

	@Override
	public Response print(int id) throws Exception {
		try {
			SaderDocument d = DocUtils.findDocByIdSader(id);
			DocUtils.print(d.getUrl());
		} catch (Exception e) {
			return new MyResponse().failed();
		}
		return new MyResponse().success();
	}
}
