package navymail.Helpers.Wared;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import navymail.Helpers.Master.ImageSort;
import navymail.models.Wared.WaredDocument;
import navymail.util.Environment;

/**
 * 
 * @author zankalony
 * 
 */
public class ImageUtils {

	public static void delete_from_database(String path) {
		/*
		 * String filePath = path.replace(Environment.SERVER_PATH + "/public",
		 * Environment.LAPPATH);
		 */

		String filePath = path.replace(Environment.SERVER_PATH,
				Environment.LAPPATH);

		File f = new File(filePath);
		if (f.exists()) {
			f.delete();
		}
		String folderPath = filePath.substring(0, filePath.lastIndexOf("/"));

		rename_files(folderPath);
	}

	private static void rename_files(String folderpath) {

		ArrayList<String> list = getListOfImages(folderpath);

		for (int i = 1; i <= list.size(); i++) {
			File tmp = new File(list.get(i - 1));
			String to = folderpath + ((i > 9) ? "/i" : "/i0") + i + ".jpg";
			if (to.equals(tmp.getAbsoluteFile()))
				continue;
			File dest = new File(to);
			tmp.renameTo(dest);
		}

	}

	public static File[] check_files(File[] f) {
		ArrayList<File> arr = new ArrayList<File>();

		for (File file : f) {
			if (file.getAbsolutePath().endsWith(".jpg"))
				arr.add(file);
		}
		File[] answer = new File[arr.size()];
		for (int i = 0; i < answer.length; i++) {
			answer[i] = arr.get(i);
		}
		return answer;
	}

	public static ArrayList<String> getListOfImages(String path) {
		File f = new File(path);
		if (!f.exists() || f == null)
			return new ArrayList<String>();
		File[] listOfImages = f.listFiles();
		listOfImages = check_files(listOfImages);
		ImageSort[] img = new ImageSort[listOfImages.length];
		ArrayList<String> listAnswer = new ArrayList<String>();

		for (int i = 0; i < img.length; i++) {
			img[i] = new ImageSort(listOfImages[i].getAbsolutePath());
		}
		Arrays.sort(img);
		for (int i = 0; i < img.length; i++) {
			listAnswer.add(img[i].img);
		}
		return listAnswer;
	}

	public static void add_to_database(String path, InputStream file,
			boolean isInsertion) throws FileNotFoundException, IOException {

		// String filePath = path.replace(Environment.SERVER_PATH + "/public",
		// Environment.LAPPATH);

		String filePath = path.replace(Environment.SERVER_PATH,
				Environment.LAPPATH);

		String folderpath = filePath.substring(0, filePath.lastIndexOf("/"));

		ArrayList<String> list = getListOfImages(folderpath);

		int num = list.size();
		if (isInsertion)
			num++;

		int index = list.size() - 1;
		while (index >= 0) {
			File tmp = new File(list.get(index--));
			String to = folderpath + ((num > 9) ? "/i" : "/i0") + num + ".jpg";
			if (tmp.equals(new File(filePath))) {
				create_imge(file, to);
				break;
			}

			File dest = new File(to);
			tmp.renameTo(dest);
			num--;
		}

	}

	public static void create_imge(InputStream uploadedInputStream, String path)
			throws FileNotFoundException, IOException {
		OutputStream fileOutputStream = new FileOutputStream(path);
		int read = 0;
		final byte[] bytes = new byte[1024];
		while ((read = uploadedInputStream.read(bytes)) != -1) {
			fileOutputStream.write(bytes, 0, read);
		}
		fileOutputStream.flush();
		fileOutputStream.close();

	}

	// stackoverflow
	public static InputStream[] cloneInputStream(InputStream input)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// Fake code simulating the copy
		// You can generally do better with nio if you need...
		// And please, unlike me, do something about the Exceptions :D
		byte[] buffer = new byte[1024];
		int len;
		while ((len = input.read(buffer)) > -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();

		// Open new InputStreams using the recorded bytes
		// Can be repeated as many times as you wish
		InputStream is1 = new ByteArrayInputStream(baos.toByteArray());
		InputStream is2 = new ByteArrayInputStream(baos.toByteArray());

		return new InputStream[] { is1, is2 };
	}

	// given id , return all page from all previous documents
	public static ArrayList<String> getAllImgs(int id) throws SQLException {
		ArrayList<String> s = new ArrayList<String>();
		WaredDocument d = DocUtils.findDocById(id);
		if (d == null)
			return s;
		System.out.println("********************************");
		System.out.println("FOR DOC WITH ID = " + id + " GET ALL PAGES ...");
		HashSet<Integer> map = new HashSet<>();
		map.add(id);
		ArrayList<String> folders = new ArrayList<String>();
		int[] prev_id = DocUtils.getPrevIdList(d.getPrev_doc_id());
		for (int i = 0; i < prev_id.length; i++) {
			if (!map.contains(prev_id[i])) {
				map.add(prev_id[i]);
				add(prev_id[i], folders, map);
			}
		}
		System.out.println(folders.toString());
		System.out.println("...................................");

		for (int i = 0; i < folders.size(); i++) {
			ArrayList<String> filelist = getListOfImages(folders.get(i));
			for (int j = 0; j < filelist.size(); j++) {
				s.add(filelist.get(j));
			}
		}
		return s;
	}

	private static void add(int id, ArrayList<String> res, HashSet<Integer> map)
			throws SQLException {
		for (Integer s : map) {
			System.out.println("********** >>>>>>>>>>> " + s);
		}

		WaredDocument d = DocUtils.findDocById(id);
		if (d == null)
			return;
		res.add(d.getUrl());
		String prev_doc_id = d.getPrev_doc_id();
		if (prev_doc_id == null || prev_doc_id.equals(""))
			return;
		int[] ids = DocUtils.getPrevIdList(prev_doc_id);
		for (int prv_ids : ids) {
			if (!map.contains(prv_ids)) {
				map.add(prv_ids);
				add(prv_ids, res, map);
			}
		}
	}
}
