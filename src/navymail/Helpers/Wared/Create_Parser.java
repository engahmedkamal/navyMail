package navymail.Helpers.Wared;

import navymail.params.Wared.CreateParams;
import navymail.util.Environment;

/**
 * 
 * @author zankalony
 * 
 */
public class Create_Parser {
	String url;

	public String get_query(CreateParams params) {

		if (params.notes == null || params.notes.equals("null"))
			params.notes = "";

		url = (params.doc_type == Environment.KHARGY) ? Environment.DB_KHARGY
				: Environment.DB_DA5LY;

		url += "/" + params.created_at.substring(0, 4) + "/" + params.doc_num
				+ " " + params.title;

		String s = String
				.format("Insert into documents (prev_doc_id, title, doc_number, created_at, doc_out_number, doc_out_date, unit_id, doc_type, target_id, status_id, url, notes) "
						+ " Values (\"%s\" , \"%s\" , %d ,\'%s\' , %d , \'%s\' , %d , %d , %d ,%d ,\"%s\" , \"%s\" )",
						params.prev_id, params.title, params.doc_num,
						params.created_at, params.doc_out_num,
						params.doc_out_date, params.unit_id, params.doc_type,
						params.target_id, params.status_id, url, params.notes);

		return s;
	}
}
