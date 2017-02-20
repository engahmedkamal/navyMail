package navymail.Helpers.Sader;

import java.util.ArrayList;

import navymail.params.Sader.SaderPram;
import navymail.util.Environment;

public class Sader_Parser {

	String url;

	public ArrayList<String> get_query(SaderPram params) {

		ArrayList<String> q = new ArrayList<>();
		url = (params.doc_type == Environment.KHARGY) ? Environment.sader_DB_KHARGY : Environment.sader_DB_DA5LY;

		url += "/" + params.doc_date.substring(0, 4) + "/" + params.doc_num + " " + params.doc_date;
		q.add(String.format(
				"INSERT INTO sader_documents (title,doc_number,doc_type,prev_doc_id,url,unit_id,file_save_num,doc_date,sub_file_save_num,finish_date,start_date,tashira,target_id, security_type) "
						+ " Values (\'%s\', %d, %d, \'%s\' , \'%s\' , %d, %d ,\'%s\' , %d , \'%s\' , \'%s\' ,%d , %d ,%d  )",
				params.title, params.doc_num, params.doc_type, params.prev_id, url, params.unit_id,
				params.file_save_num, params.doc_date, params.sub_file_save_num, params.finish_date, params.start_date,
				params.tashira, params.target_id, params.security_type));
		if (params.unit_ids.length != 0) {
			for (int i = 0; i < params.unit_ids.length; i++) {
				if (!params.unit_ids[i].equals("") && Integer.parseInt(params.unit_ids[i]) != -1)
					q.add(String.format(
							"INSERT INTO sader_dest_unit (`doc_number`,`doc_date`,`unit_id`)"
									+ "Values ( %d,\'%s\', %d)",
							params.doc_num, params.doc_date, Integer.parseInt(params.unit_ids[i])));
			}
		}

		return q;
	}

}
