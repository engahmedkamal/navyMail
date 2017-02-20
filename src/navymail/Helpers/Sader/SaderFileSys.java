package navymail.Helpers.Sader;

import java.io.File;
import java.util.ArrayList;

import navymail.Helpers.Wared.DocUtils;
import navymail.Helpers.Wared.ImageUtils;
import navymail.params.Sader.SaderPram;
import navymail.util.Environment;

public class SaderFileSys {
	// list of previous doc id
	private String created_year;

	public SaderFileSys(SaderPram params) throws Exception {
		String scannerPath = Environment.TASWEER_PATH[params.folder_id - 1];
		File src = new File(scannerPath);
		if (!src.exists())
			throw new Exception("scanner folder doesnt exist!");

		ArrayList<String> papers = ImageUtils.getListOfImages(scannerPath);

		int img_no = Math.min(params.img_no, papers.size());
		created_year = params.doc_date.substring(0, 4);

		String path;
		if (params.doc_type == Environment.KHARGY) {
			new File(Environment.sader_DB_KHARGY).mkdir();
			path = Environment.sader_DB_KHARGY + "/" + created_year;
		} else {
			new File(Environment.sader_DB_DA5LY).mkdir();
			path = Environment.sader_DB_DA5LY + "/" + created_year;
		}

		File des = new File(path);
		des.mkdir();

		path += "/" + params.doc_num + " " + params.doc_date;

		des = new File(path);
		des.mkdir();

		for (int i = 0; i < img_no; i++) {
			String src0 = papers.get(i);
			String des0 = des + ((i >= 9) ? "/i" : "/i0") + (i + 1) + ".jpg";
			DocUtils.move(src0, des0);
		}

	}

}
