package navymail.Helpers.Wared;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * Used only for
 * 
 * @author zankalony 5-1-2015
 * 
 */

public class Search_parser {

	// search query url will be like this

	/**
	 * http://localhost:8080/rest/documents?document_number=&date_from=1-2-2002&
	 * date_to
	 * =&document_out_number=&date_from_sader=&date_to_sader=&trans_out_num
	 * =&date_from_trans
	 * =&date_to_trans=&unit=&target=&status=&document_title=&page= &page_size=
	 * 
	 **/

	// search query params
	private static String title;
	private static int doc_number;
	private static int status_id;
	private static int target_id;
	private static Date date_from;
	private static Date date_to;
	private static int doc_out_number;
	private static int unit_id;
	private static int doctype;

	private static int trans_out_num;
	private static Date date_from_sader;
	private static Date date_to_sader;
	private static Date date_from_trans;
	private static Date date_to_trans;

	// to change date format
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	// Data U can use , EveryThing else is PRIVATE!
	private String query;
	private int page_num;
	private int page_size;

	public String getQuery() {
		return query;
	}

	public int getPage_num() {
		return page_num;
	}

	public int getPage_size() {
		return page_size;
	}

	//
	public Search_parser(UriInfo info) throws ParseException {
		read_map(info);

		query = set_up_search_query();
	}

	// build search query
	private String set_up_search_query() {
		String query = "select * from documents ";
		String where_statment = "";

		if (doc_number != -1)
			where_statment = "doc_number = " + doc_number;

		if (title != null && !title.equals("")) {
			if (where_statment.length() != 0)
				where_statment += " or ";
			title = title.replaceAll(" ", "%");
			where_statment += "title like '%" + title + "%\'";
		}

		if (doc_out_number != -1) {
			if (where_statment.length() != 0)
				where_statment += " or ";
			where_statment += "doc_out_number = " + doc_out_number;
		}
		if (trans_out_num != -1) {
			if (where_statment.length() != 0)
				where_statment += " or ";
			where_statment += "trans_out_num = " + trans_out_num;
		}

		// Code can be better

		String dateCondition1 = dateCondition("created_at", date_from,
				addDay(date_to));

		String dateCondition2 = dateCondition("doc_out_date", date_from_sader,
				addDay(date_to_sader));

		String dateCondition3 = dateCondition("trans_out_date",
				date_from_trans, addDay(date_to_trans));

		if (dateCondition1 != null) {
			if (where_statment.length() == 0)
				where_statment = dateCondition1;
			else
				where_statment = "(" + where_statment + ") and ("
						+ dateCondition1 + ")";
		}

		if (dateCondition2 != null) {
			if (where_statment.length() == 0)
				where_statment = dateCondition2;
			else
				where_statment = "(" + where_statment + ") and ("
						+ dateCondition2 + ")";
		}

		if (dateCondition3 != null) {
			if (where_statment.length() == 0)
				where_statment = dateCondition2;
			else
				where_statment = "(" + where_statment + ") and ("
						+ dateCondition3 + ")";
		}

		String doc_properities = "";
		if (target_id != -1)
			doc_properities += "target_id = " + target_id;

		if (unit_id != -1) {
			if (doc_properities.length() != 0)
				doc_properities += " and ";
			doc_properities += "unit_id = " + unit_id;
		}

		if (status_id != -1) {
			if (doc_properities.length() != 0)
				doc_properities += " and ";
			doc_properities += "status_id = " + status_id;
		}
		
		if (doctype != -1) {
			if (doc_properities.length() != 0)
				doc_properities += " and ";
			doc_properities += "doc_type = " + doctype;
		}

		if (where_statment.length() > 0) {
			if (doc_properities.length() != 0)
				where_statment = where_statment + " and (" + doc_properities
						+ ")";
		} else {
			where_statment = doc_properities;
		}

		if (where_statment.equals(""))
			return "";
		if(status_id==1 || status_id == 2){
			where_statment = "(" + where_statment + ")"
					+ " ORDER BY doc_type DESC, doc_number ASC LIMIT 100";
			
		}else{
			where_statment = "(" + where_statment + ")"
					+ " ORDER BY created_at DESC LIMIT 100";
			
		}

		query += " where " + where_statment;
		System.out.println(query);
		return query;
	}

	// simple ^_^
	//
	private String dateCondition(String attr, Date from, Date to) {
		if (from == null && to == null)
			return null;

		String col = " documents." + attr;

		if (from == null)
			return col + " <=  '" + format.format(to) + "'";

		if (to == null)
			return col + " >= '" + format.format(from) + "'";

		return col + " BETWEEN '" + format.format(from) + "' and '"
				+ format.format(to) + "'";

	}

	private Date to_D(String s/* "dd-MM-yyyy" */) throws ParseException {
		if (s == null || s.equals(""))
			return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date d = dateFormat.parse(s);
		return d;
	}

	private Date addDay(Date d) {
		if (d == null)
			return null;
		return new Date(d.getTime() );
	}

	// just parser
	private void read_map(UriInfo info) throws ParseException {

		MultivaluedMap<String, String> map = info.getQueryParameters();

		doc_number = to_int(map.getFirst("document_number"));

		title = map.getFirst("document_title");
		// created_at
		date_from = to_D(map.getFirst("date_from"));
		date_to = to_D(map.getFirst("date_to"));

		doc_out_number = to_int(map.getFirst("document_out_number"));

		status_id = to_int(map.getFirst("status"));
		target_id = to_int(map.getFirst("target"));

		unit_id = to_int(map.getFirst("unit"));
		trans_out_num = to_int(map.getFirst("trans_out_num"));

		// sader => doc_out_date
		date_from_sader = to_D(map.getFirst("date_from_sader"));
		date_to_sader = to_D(map.getFirst("date_to_sader"));
		
		doctype = to_int(map.getFirst("doc_type"));
		// sader mo7awal =>trans_out_date
		date_from_trans = to_D(map.getFirst("date_from_trans"));
		date_to_trans = to_D(map.getFirst("date_to_trans"));

		page_num = to_int(map.getFirst("page"));

		page_num = page_num < 0 ? 0 : page_num;

		page_size = to_int(map.getFirst("page_size"));

		page_size = page_size < 0 ? 20 : page_size;

	}

	private Integer to_int(String s) {
		return (s == null || s.equals("")) ? -1 : Integer.parseInt(s);
	}

}
