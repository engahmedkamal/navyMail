package navymail.Helpers.Wared;

import java.sql.SQLException;
import java.util.ArrayList;

import navymail.models.Wared.WaredDocument;
import navymail.util.Jason;

/**
 * 
 * return the final json Object to render
 * 
 * @author Zankalony
 * 
 */
public class Pagination {

	public ArrayList<WaredDocument> documents;
	public int res_size;
	public int page_num;
	public int page_size;
	public int page_count;

	public Pagination(ArrayList<WaredDocument> res, int page_num, int page_size)
			throws SQLException {
		int res_size = res.size();

		/*
		 * int remains = res_size - page_num * page_size; if (page_size >
		 * remains) page_size = remains > 0 ? remains : 0;
		 */
		int page_count = (int) Math.ceil(res_size * 1.0 / page_size);
		if (page_num > page_count) {
			page_num = page_count;
		}

		ArrayList<WaredDocument> docs = new ArrayList<WaredDocument>();
		int start = page_num * page_size;
		int end = Math.min(start + page_size, res_size);
		for (int i = start; i < end; i++) {
			WaredDocument d = res.get(i);
			d.setAbsolut_num(i);
			d.setParents(DocUtils.getLinks(d.getPrev_doc_id()));
			docs.add(d);
		}

		for (int j = 0; j < docs.size(); j++) {
			WaredDocument d = docs.get(j);		
			d.setAllPreviousTarbet(ImageUtils.getAllImgs(d.getId()));
		}

		this.documents = docs;
		this.res_size = res_size;
		this.page_num = page_num;
		this.page_size = page_size;
		this.page_count = (int) Math.ceil(res_size * 1.0 / page_size);
	}

	@Override
	public String toString() {
		synchronized (this) {
			// TODO Auto-generated method stub
			try {
				return Jason.toJson(this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

}
