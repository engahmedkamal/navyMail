package navymail.Helpers.Wared;

import java.util.ArrayList;
import java.util.Iterator;

import navymail.params.Wared.UpdateParams;

/**
 * 
 * @author zankalony
 * @editor Kamal
 */
public class Update_parser {

	public ArrayList<String> get_query(UpdateParams params, int id, int check, int flag,Iterator<Integer> add, Iterator<Integer> remove) {
		ArrayList<String> q = new ArrayList<>();
		if (params.notes == null || params.notes.equals("null"))
			params.notes = "";
		String s;
		if (params.trans_out_num == 0) {
			s = String.format(
					"UPDATE documents SET prev_doc_id=\"%s\" , title=\"%s\", doc_number=%d, created_at=\'%s\', doc_out_number=%d, "
							+ "doc_out_date=\'%s\', unit_id=%d, doc_type=%d, target_id=%d, status_id=%d, notes=\"%s\", url=\'%s\' "
							+ "where id =%d",
					params.prev_id, params.title, params.doc_num, params.created_at, params.doc_out_num,
					params.doc_out_date, params.unit_id, params.doc_type, params.target_id, params.status_id,
					params.notes, params.url, id);
		} else {
			s = String.format(
					"UPDATE documents SET prev_doc_id=\"%s\" , title=\"%s\", doc_number=%d, created_at=\'%s\', doc_out_number=%d, "
							+ "doc_out_date=\'%s\', unit_id=%d, doc_type=%d, target_id=%d, status_id=%d, notes=\"%s\", trans_out_num=%d, trans_out_date=\'%s\' , url=\'%s\' "
							+ "where id =%d",
					params.prev_id, params.title, params.doc_num, params.created_at, params.doc_out_num,
					params.doc_out_date, params.unit_id, params.doc_type, params.target_id, params.status_id,
					params.notes, params.trans_out_num, params.trans_out_date, params.url, id);
		}
		q.add(s);
		if (check != 0) {
			final String x = String.format("DELETE FROM Motb3a_tashira " + "where id=%d", id);
			q.add(x);
		//	final String y = String.format("DELETE FROM Motb3a " + "where id=%d", id);
//			q.add(y);
//	
//
		
		if ((params.tashira.length>0 && Integer.parseInt(params.tashira[0]) != -1)||
				(params.tashira_Quad.length>0 && Integer.parseInt(params.tashira_Quad[0]) != -1)) {
//			final String z = String.format(
//					"INSERT INTO Motb3a(`id`,`doc_date`,`target_id`)" + "Values ( %d ,\'%s\', %d)", id,
//					params.created_at, 2);
//			q.add(z);
			q.add(String.format(
					"UPDATE documents SET doc_need_replay=%d"+ " where id =%d",
					2, id));
		}else{
			q.add(String.format(
					"UPDATE documents SET doc_need_replay=%d"+ " where id =%d",
					0, id));
		}


			for (int i = 0; i < params.tashira.length; i++) {
				if (!params.tashira[i].equals("") && Integer.parseInt(params.tashira[i]) != -1)
					q.add(String.format("INSERT INTO Motb3a_tashira (`id`,`tashira`,`target`) " + "Values ( %d , %d, %d)", id,
							Integer.parseInt(params.tashira[i]),2));
			}
			for (int i = 0; i < params.tashira_Quad.length; i++) {
				if (!params.tashira_Quad[i].equals("") && Integer.parseInt(params.tashira_Quad[i]) != -1)
					q.add(String.format("INSERT INTO Motb3a_tashira (`id`,`tashira`,`target`) " + "Values ( %d , %d, %d)", id,
							Integer.parseInt(params.tashira_Quad[i]),1));
			}

		}
		if (flag != 0) {
			for (Iterator iterator =remove; iterator.hasNext();) {
				Integer integer = (Integer) iterator.next();
				q.add(String.format("DELETE FROM Resp_unit " + "where id=%d and unit=%d", id,integer));	
			}
			for (Iterator iterator =add; iterator.hasNext();) {
				Integer integer = (Integer) iterator.next();
				if (!integer.equals("") && integer != -1)
					q.add(String.format("INSERT INTO Resp_unit (`id`,`unit`,`status`)" + "Values ( %d , %d, %d)", id,
							integer, 1));
			}
		}
		return q;
	}
}
