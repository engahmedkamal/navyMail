package navymail.Helpers.Wared;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import com.itextpdf.text.pdf.PdfWriter;

import navymail.Queries.SeLect;
import navymail.models.Sader.SaderDocument;
import navymail.models.Wared.WaredDocument;
import navymail.util.Environment;
import navymail.util.log.Loggable;

/**
 * 
 * Contains any help function used in documents rock and roll :D
 * 
 * @author zankalony
 * 
 */

public class DocUtils extends Loggable {

	public static void assert_exist_path(String path) {
		String[] path_spliter = path.split("/");

		String ongoing_path = "";
		for (int i = 0; i < path_spliter.length - 1; i++) {
			ongoing_path += "/" + path_spliter[i];
			File file = new File(ongoing_path);
			if (!file.exists())
				file.mkdir();
		}
	}

	public static void copy_doc_from_DB_to_Tablet(String folder_name, int type,
			int target, int status, String prev_id_str, String url)
			throws Exception {
		// SECOND
		logger.info("TABLET : COPY DOCUMENT FROM DATABASE TO TABLET");

		int page_index = (new File(url)).listFiles().length;

		if (status == Environment.STATUS_UNDER_PREPARE
				|| status == Environment.STATUS_TABLET) {

			String dest = DocUtils.getPath(type, target, status);
			new File(dest).mkdir();
			dest += "/" + folder_name;

			logger.info("TABLET : destination path := " + dest);
			DocUtils.copy_folder(url, dest);
			int[] prev_id;

			logger.info("TABLET : get Parents ID ");
			prev_id = DocUtils.getPrevIdList(prev_id_str);
			res.clear();
			System.out.println("<<<<<<<<<<**********************>>>>>>>>>>");
			System.out.println("GET ALL PARENTS ID : FOR THE LIST (PREV_DOC) "
					+ Arrays.toString(prev_id));
			HashSet<Integer> map = new HashSet<>();
			for (int i = 0; i < prev_id.length; i++) {
				if (!map.contains(prev_id[i])) {
					map.add(prev_id[i]);
					concatinate(prev_id[i], map);
				}
			}
			System.out.println(res.toString());

			System.out.println("==================");
			logger.info("TABLET : parents are :: " + res.toString());
			for (int i = 0; i < res.size(); i++) {
				ArrayList<String> filelist = ImageUtils.getListOfImages(res
						.get(i));
				int num_pages = Math.min(filelist.size(),
						Environment.MAX_PAGE_PER_DOC);

				logger.info("TABLET : Folder:" + (i + 1) + " contains "
						+ num_pages + " page");

				for (int j = 0; j < num_pages; j++) {
					String des0 = dest + ((page_index >= 9) ? "/i" : "/i0")
							+ (page_index + 1) + ".jpg";
					DocUtils.copy_folder(filelist.get(j), des0);
					page_index++;
				}
			}

		}
	}

	static ArrayList<String> res = new ArrayList<String>();

	private static void concatinate(int id, HashSet<Integer> map)
			throws SQLException {

		WaredDocument d = DocUtils.findDocById(id);

		if (d == null)
			return;

		res.add(d.getUrl());

		String prev_doc_id = d.getPrev_doc_id();

		if (prev_doc_id.equals("") || prev_doc_id == null)
			return;

		int[] ids = DocUtils.getPrevIdList(prev_doc_id);

		for (int i : ids) {
			if (!map.contains(i)) {
				map.add(i);
				concatinate(i, map);
			}
		}
	}

	public static int[] getPrevIdList(String s) {
		if (s == null || s.equals(""))
			return new int[] {};
		String str[] = s.replace(",", " ").trim().split(" ");
		int[] res = new int[str.length];
		for (int i = 0; i < str.length; i++) {
			res[i] = Integer.parseInt(str[i]);
		}
		return res;
	}

	public static boolean move(String src, String dest) {
		File source = new File(src);
		File destination = new File(dest);
		DocUtils.assert_exist_path(dest);
		boolean f = source.renameTo(destination);
		return f;
	}

	public static void copy_folder(String srcPath, String destPath)
			throws InterruptedException, IOException {

		File source = new File(srcPath);
		File dest = new File(destPath);

		// make sure source exists
		if (!source.exists()) {
			System.out.println("Directory does not exist.");
			// just exit
		} else {
			try {
				copyFolder(source, dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void copyFolder(File src, File dest) throws IOException {

		if (src.isDirectory()) {

			// if directory not exists, create it
			if (!dest.exists()) {
				dest.mkdir();
				System.out.println("Directory copied from " + src + "  to "
						+ dest);
			}

			// list all the directory contents
			String files[] = src.list();

			for (String file : files) {
				// construct the src and dest file structure
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// recursive copy
				copyFolder(srcFile, destFile);
			}

		} else {
			// if file, then copy it
			// Use bytes stream to support all file types
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
		}
	}

	// can be modified and be better
	// set parent_link for each doc u call
	public static String getLinks(String prev_id) throws SQLException {
		if (prev_id == null || prev_id.equals(""))
			return "";

		String str[] = prev_id.split(",");
		int[] ids = new int[str.length];
		for (int i = 0; i < ids.length; i++)
			ids[i] = Integer.parseInt(str[i]);

		String links = "";
		for (int i = 0; i < ids.length; i++) {
			if (i > 0)
				links += "#";
			WaredDocument d = findDocById(ids[i]);
			if (d == null)
				continue;
			links += d.getId() + "#" + d.getDoc_number() + "#" + d.getTitle();
		}

		return links;
	}

	public static WaredDocument findDocById(int id) throws SQLException {

		String q = "select * from documents where id = " + id;
		SeLect s = new SeLect();
		s.openConnection(q);
		ArrayList<WaredDocument> res = s.getDocuments();
		s.closeConnection();
		System.out.println(id);
		if (res.size() == 0)
			return null;
		WaredDocument currDoc = res.get(0);
		return currDoc;
	}
	public static SaderDocument findDocByIdSader(int id) throws SQLException {

		String q = "select * from sader_documents where id = " + id;
		SeLect s = new SeLect();
		s.openConnection(q);
		ArrayList<SaderDocument> res = s.getSaderDocuments();
		s.closeConnection();
		System.out.println(id);
		if (res.size() == 0)
			return null;
		SaderDocument currDoc = res.get(0);
		return currDoc;
	}
	public static String getPath(int type, int target, int status) {
		if (status == Environment.STATUS_FINISHED
				|| status == Environment.STATUS_MO3LLAK)
			return null;

		String dest = "";
		if (status == Environment.STATUS_UNDER_PREPARE) {
			if (target == Environment.TARGET_ARKAN)
				dest = (type == Environment.DA5LY) ? Environment.ARKAN_UNDER_PREPARE_DA5LY
						: Environment.ARKAN_UNDER_PREPARE_KHARGY;

			if (target == Environment.TARGET_KA2ED)
				dest = (type == Environment.DA5LY) ? Environment.KA2ED_UNDER_PREPARE_DA5LY
						: Environment.KA2ED_UNDER_PREPARE_KHARGY;

		} else {
			if (target == Environment.TARGET_ARKAN)
				dest = (type == Environment.DA5LY) ? Environment.ARKAN_TABLET_DA5LY
						: Environment.ARKAN_TABLET_KHARGY;

			if (target == Environment.TARGET_KA2ED)
				dest = (type == Environment.DA5LY) ? Environment.KA2ED_TABLET_DA5LY
						: Environment.KA2ED_TABLET_KHARGY;
		}

		return dest;
	}

	public static void print(String path) {
		com.itextpdf.text.Document document = new com.itextpdf.text.Document();

		ArrayList<String> pages = ImageUtils.getListOfImages(path);

		if (pages.size() == 0)
			return;

		String output = Environment.LAPPATH + "/tmp.pdf";
		try {
			FileOutputStream fos = new FileOutputStream(output);
			PdfWriter writer = PdfWriter.getInstance(document, fos);
			writer.open();
			document.open();
			for (int i = 0; i < pages.size(); i++) {
				com.itextpdf.text.Image img = com.itextpdf.text.Image
						.getInstance(pages.get(i));

				float scaler = ((document.getPageSize().getWidth()
						- document.leftMargin() - document.rightMargin()) / img
						.getWidth()) * 100;

				img.scalePercent(scaler);

				document.add(img);
			}
			document.close();
			writer.close();
			// Desktop.getDesktop().open(new File(output));
			// or we can make a thread

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static boolean isOnTablet(int status) {
		return status == Environment.STATUS_TABLET
				|| status == Environment.STATUS_UNDER_PREPARE;
	}
}