package navymail.Helpers.Wared;

import java.io.File;
import java.util.ArrayList;

import navymail.params.Wared.CreateParams;
import navymail.util.Environment;
import navymail.util.log.Loggable;

/***
 * One of the most important classes handle file system after creating new
 * document
 * 
 * @author Zankalony
 * 
 */

public class CreateFileSystem extends Loggable {

	// list of previous doc id
	private String created_year;

	public CreateFileSystem(CreateParams params) throws Exception {
		logger.info("START A NEW DOCUMENT CREATION");
		String scannerPath = Environment.TASWEER_PATH[params.folder_id - 1];
		File src = new File(scannerPath);
		if (!src.exists())
			throw new Exception("scanner folder doesnt exist!");

		ArrayList<String> papers = ImageUtils.getListOfImages(scannerPath);

		int img_no = Math.min(params.img_no, papers.size());
		created_year = params.created_at.substring(0, 4);

		logger.info("MOVE PAPERS TO DATABASE");

		String path;
		if (params.doc_type == Environment.KHARGY) {
			new File(Environment.DB_KHARGY).mkdir();
			path = Environment.DB_KHARGY + "/" + created_year;
		} else {
			new File(Environment.DB_DA5LY).mkdir();
			path = Environment.DB_DA5LY + "/" + created_year;
		}

		File des = new File(path);
		des.mkdir();

		path += "/" + params.doc_num + " " + params.title;

		String folderPath = path;

		des = new File(path);
		des.mkdir();

		for (int i = 0; i < img_no; i++) {
			String src0 = papers.get(i);
			String des0 = des + ((i >= 9) ? "/i" : "/i0") + (i + 1) + ".jpg";
			DocUtils.move(src0, des0);
			logger.info("DATABASE: MOVE " + src0 + " TO " + des0);
		}
		if (params.target_id == Environment.TARGET_3MEED
				|| params.target_id == Environment.TARGET_KA2ED_ARKAN)
			return; // do no thing
		String folderName = params.doc_num + " " + params.title;
		System.out.println(folderName);
		DocUtils.copy_doc_from_DB_to_Tablet(folderName, params.doc_type,
				params.target_id, params.status_id, params.prev_id, folderPath);

	}

}
