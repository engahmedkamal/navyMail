package navymail.Helpers.Sader;

import java.util.ArrayList;

import navymail.Helpers.Wared.DocUtils;
import navymail.models.Sader.SaderDocument;
import navymail.util.Jason;

public class Pagination_Sader {
	public ArrayList<SaderDocument> documents;
	public int res_size;
	public int page_num;
	public int page_size;
	public int page_count;
	public Pagination_Sader(ArrayList<SaderDocument> res, int page_num, int page_size)
			throws Exception {

		System.out.println(">>>>>>>>>>>>>>>>>>>??????????????????" + page_size
				+ " ........ " + res.size());

		int res_size = res.size();

		/*
		 * int remains = res_size - page_num * page_size; if (page_size >
		 * remains) page_size = remains > 0 ? remains : 0;
		 */
		int page_count = (int) Math.ceil(res_size * 1.0 / page_size);
		if (page_num > page_count) {
			page_num = page_count;
		}

		ArrayList<SaderDocument> docs = new ArrayList<>();
		int start = page_num * page_size;
		int end = Math.min(start + page_size, res_size);
		for (int i = start; i < end; i++) {
			SaderDocument d = res.get(i);
			d.setAbsolut_num(i);
			d.setParents(DocUtils.getLinks(d.getPrev_doc_id()));
			docs.add(d);
		}
		System.out.println(">>>>>>>>>!!!!!@@@>>>>>>>>>>" + docs.size());
//		for (int j = 0; j < docs.size(); j++) {
//			SaderDocument d = docs.get(j);
//			d.setAllPreviousTarbet(ImageUtils.getAllImgsader(d.getId()));
//			System.out.println("       aaaa     "+Jason.toJson(d));
//		}

		this.documents = docs;
		this.res_size = res_size;
		this.page_num = page_num;
		this.page_size = page_size;
		this.page_count = (int) Math.ceil(res_size * 1.0 / page_size);
		System.out.println(">>>>>>>>>!!!!!@@@>>>>>>>>>>" +res_size+" dfdfcadf"+ this.page_count);
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
