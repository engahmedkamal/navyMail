package navymail.Helpers.Sader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import navymail.Queries.SeLect;
import navymail.models.Sader.SaderDocument;

public class Search_quary_creater {
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	Date date_to;
	Date date_from;
	private static String title;
	private static int doc_number;
	private static int doc_type;
	private static int unit_id;
	private int page_num;
	private int page_size;
	public int getPage_num() {
		return page_num;
	}

	public int getPage_size() {
		return page_size;
	}
	private void read_map(UriInfo info) throws ParseException {
		MultivaluedMap<String, String> map = info.getQueryParameters();
		doc_number = to_int(map.getFirst("document_number"));
		title = map.getFirst("document_title");
		// created_at
		date_from = to_D(map.getFirst("date_from"));
		date_to = to_D(map.getFirst("date_to"));
		//doc_out_number = to_int(map.getFirst("document_out_number"));
		//status_id = to_int(map.getFirst("status"));
		//target_id = to_int(map.getFirst("target"));
		unit_id = to_int(map.getFirst("unit"));
		doc_type = to_int(map.getFirst("doc_type"));
		page_num = to_int(map.getFirst("page"));
		page_num = page_num < 0 ? 0 : page_num;
		page_size = to_int(map.getFirst("page_size"));
		page_size = page_size < 0 ? 20 : page_size;

	}
	public ArrayList<SaderDocument> search(UriInfo inf)
			throws Exception {
		read_map(inf);
		String where_statment = "";
		if (doc_number != -1)
			where_statment = "doc_number = " + doc_number;
		if (doc_type != -1) {
			if (where_statment.length() != 0)
				where_statment += " and ";
			where_statment += "doc_type = " + doc_type;
		}
		if (unit_id != -1) {
			if (where_statment.length() != 0)
				where_statment += " and ";
			where_statment += "unit_id = " + unit_id;
		}
		
		if (title != null && !title.equals("")) {
			if (where_statment.length() != 0)
				where_statment += " or ";
			title = title.replaceAll(" ", "%");
			where_statment += "title like '%" + title + "%\'";
		}

		// Code can be better
		String dateCondition1 = dateCondition("doc_date", date_from, addDay(date_to));

		if (dateCondition1 != null) {
			if (where_statment.length() == 0)
				where_statment = dateCondition1;
			else
				where_statment = "(" + where_statment + ") and (" + dateCondition1 + ")";
		}
		String query="";
		if (!where_statment.equals("")){
			
		}
		where_statment= "(" + where_statment + ")"
				+ " ORDER BY doc_date DESC LIMIT 100";
		query = "select * from sader_documents " + "where " + where_statment;
		System.out.println(query);
		SeLect select = new SeLect();
		select.openConnection(query);
		ArrayList<SaderDocument> s = select.getSaderDocuments();
		select.closeConnection();
		return s;
	}

	private Date addDay(Date d) {
		if (d == null)
			return null;
		return new Date(d.getTime() + 24 * 60 * 60 * 1000);
	}

	private Date to_D(String s/* "dd-MM-yyyy" */) throws ParseException {
		if (s == null || s.equals(""))
			return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date d = dateFormat.parse(s);
		return d;
	}

	private String dateCondition(String attr, Date from, Date to) {
		if (from == null && to == null)
			return null;

		String col = " sader_documents." + attr;

		if (from == null)
			return col + " <=  '" + format.format(to) + "'";

		if (to == null)
			return col + " >= '" + format.format(from) + "'";

		return col + " BETWEEN '" + format.format(from) + "' and '" + format.format(to) + "'";

	}
	private Integer to_int(String s) {
		return (s == null || s.equals("")) ? -1 : Integer.parseInt(s);
	}
}
