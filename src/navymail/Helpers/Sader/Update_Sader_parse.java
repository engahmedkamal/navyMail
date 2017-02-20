package navymail.Helpers.Sader;

import java.util.ArrayList;

import navymail.params.Sader.UpdateSaderparam;

public class Update_Sader_parse {
	public ArrayList<String> get_query(UpdateSaderparam params, int id, int check) {
		ArrayList<String> q = new ArrayList<>();

		final String s = String.format(
				"UPDATE sader_documents SET prev_doc_id=\"%s\" , title=\"%s\", doc_number=%d, doc_date=\'%s\', file_save_num=%d, "
						+ " unit_id=%d, doc_type=%d, target_id=%d, security_type=%d, tashira=%d, sub_file_save_num=%d , url=\'%s\' "
						+ "where id =%d",
				params.prev_id, params.title, params.doc_num, params.doc_date, params.file_save_num, params.unit_id,
				params.doc_type, params.target_id, params.security_type, params.tashira, params.sub_file_save_num,
				params.url, id);

		q.add(s);
		System.out.println("s" + s);
		if (check != 0) {
			for (int i = 0; i < params.unit_ids.length; i++) {
				final String x = String.format(
						"DELETE FROM `sader_dest_unit`" + "where doc_number=%d and doc_date=\'%s\'", params.doc_num,
						params.doc_date);
				q.add(x);
			}
			for (int i = 0; i < params.unit_ids.length; i++) {
				if (!params.unit_ids[i].equals("") && Integer.parseInt(params.unit_ids[i]) != -1)
				q.add(String.format(
						"INSERT INTO sader_dest_unit (`doc_number`,`doc_date`,`unit_id`)" + "Values ( %d,\'%s\', %d)",
						params.doc_num, params.doc_date, Integer.parseInt(params.unit_ids[i])));
			}
		}
		return q;
	}
}