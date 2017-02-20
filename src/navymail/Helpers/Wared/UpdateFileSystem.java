package navymail.Helpers.Wared;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import navymail.models.Wared.WaredDocument;
import navymail.util.Environment;

/**
 * 
 * @author zankalony
 * 
 */
public class UpdateFileSystem {

	public void renameFolder(String oldfolder, String newfolder, String oldurl,
			int status, int type, int target) {

		String newurl = oldurl.replace(oldfolder, newfolder);
		File f = new File(oldurl);
		File f2 = new File(newurl);
		DocUtils.assert_exist_path(f2.getAbsolutePath());
		f.renameTo(f2);

		if (status == Environment.STATUS_UNDER_PREPARE
				|| status == Environment.STATUS_TABLET) {
			String path = DocUtils.getPath(type, target, status);
			String oldPath = path + "/" + oldfolder;
			String newPath = path + "/" + newfolder;
			f = new File(oldPath);
			f2 = new File(newPath);
			DocUtils.assert_exist_path(f2.getAbsolutePath());
			f.renameTo(f2);

		}

	}
	
	/////////////////Sader///////////////
	public void renameFolderSader(String oldfolder, String newfolder, String oldurl) {
		String newurl = oldurl.replace(oldfolder, newfolder);
		File f = new File(oldurl);
		File f2 = new File(newurl);
		DocUtils.assert_exist_path(f2.getAbsolutePath());
		f.renameTo(f2);
	}
	
	public String change_type(String oldfolder, int status, int type,
			int target, String url) {

		if (status == Environment.STATUS_UNDER_PREPARE
				|| status == Environment.STATUS_TABLET) {
			String path = DocUtils.getPath(type, target, status);
			String oldPath = path + "/" + oldfolder;
			String newPath = "";
			if (type == Environment.KHARGY) {
				newPath = oldPath.replace(Environment.khargy_folderName,
						Environment.da5ly_folderName);
			} else {
				newPath = oldPath.replace(Environment.da5ly_folderName,
						Environment.khargy_folderName);
			}
			File f = new File(oldPath);
			File f2 = new File(newPath);
			DocUtils.assert_exist_path(f2.getAbsolutePath());
			f.renameTo(f2);
		}

		String newURL = url;
		if (type == Environment.KHARGY) {
			newURL = url.replace(Environment.khargy_folderName,
					Environment.da5ly_folderName);
		} else {
			newURL = url.replace(Environment.da5ly_folderName,
					Environment.khargy_folderName);
		}

		File f = new File(url);
		File f2 = new File(newURL);
		DocUtils.assert_exist_path(f2.getAbsolutePath());
		f.renameTo(f2);

		return newURL;

	}
	public String change_type_Sader(String oldfolder, int type, String url) {
		String newURL = url;
		if (type == Environment.KHARGY) {
			newURL = url.replace(Environment.khargy_folderName,
					Environment.da5ly_folderName);
		} else {
			newURL = url.replace(Environment.da5ly_folderName,
					Environment.khargy_folderName);
		}
		File f = new File(url);
		File f2 = new File(newURL);
		DocUtils.assert_exist_path(f2.getAbsolutePath());
		f.renameTo(f2);
		return newURL;

	}
	public void change_status(String foldername, WaredDocument d, int new_status)
			throws Exception {

		int status = d.getStatus_id();
		int type = d.getDoc_type();
		int target = d.getTarget_id();
		String url = d.getUrl();
		String prev_id = d.getPrev_doc_id();

		if (new_status == Environment.STATUS_UNDER_PREPARE) {
			change_status_to_under_prepare(status, type, target, prev_id, url,
					foldername);
		} else if (new_status == Environment.STATUS_FINISHED
				|| new_status == Environment.STATUS_MO3LLAK) {
			change_status_to_finished_or_mo3alak(status, type, target,
					foldername);

		} else if (new_status == Environment.STATUS_TABLET) {
			change_status_to_Tablet(status, type, target, prev_id, url,
					foldername);

		}

	}

	private void change_status_to_Tablet(int status, int type, int target,
			String prev_id_str, String url, String folder) throws Exception {

		if (status == Environment.STATUS_UNDER_PREPARE) {
			String path = DocUtils.getPath(type, target, status);
			// get from path
			String old_path = path + "/" + folder;


			String from = (target == Environment.TARGET_ARKAN) ? Environment.ARKAN_TAGHEZ_FOLDER
					: Environment.KA2ED_TAGHEZ_FOLDER;

			String to = (target == Environment.TARGET_ARKAN) ? Environment.ARKAN_TABLET_FOLDER
					: Environment.KA2ED_TABLET_FOLDER;

			String newPath = old_path.replace(from, to);

			File f1 = new File(old_path);
			File f2 = new File(newPath);
			DocUtils.assert_exist_path(f2.getAbsolutePath());
			f1.renameTo(f2);

		} else {
			// get from dataBase
			DocUtils.copy_doc_from_DB_to_Tablet(folder, type, target,
					Environment.STATUS_TABLET, prev_id_str, url);

		}

	}

	private void change_status_to_finished_or_mo3alak(int status, int type,
			int target, String foldername) throws Exception {

		if (status == Environment.STATUS_MO3LLAK)
			return;

		String path = DocUtils.getPath(type, target, status);
		File f1 = new File(path + "/" + foldername);

		FileUtils.deleteDirectory(f1);
	}

	private void change_status_to_under_prepare(int status, int type,
			int target, String prev_id_str, String url, String folder)
			throws Exception {

		if (status == Environment.STATUS_UNDER_PREPARE)
			throw new Exception(
					"Not EXpected .. Status Already Under Prepare status !");

		if (status == Environment.STATUS_TABLET) {// TABLET
			String path = DocUtils.getPath(type, target, status);
			// get from path
			String old_path = path + "/" + folder;

			String from = (target == Environment.TARGET_ARKAN) ? Environment.ARKAN_TAGHEZ_FOLDER
					: Environment.KA2ED_TAGHEZ_FOLDER;

			String to = (target == Environment.TARGET_ARKAN) ? Environment.ARKAN_TABLET_FOLDER
					: Environment.KA2ED_TABLET_FOLDER;

			String newPath = old_path.replace(to, from);

			File f1 = new File(old_path);
			File f2 = new File(newPath);
			DocUtils.assert_exist_path(f2.getAbsolutePath());
			f1.renameTo(f2);

		} else {
			// get from dataBase
			DocUtils.copy_doc_from_DB_to_Tablet(folder, type, target,
					Environment.STATUS_UNDER_PREPARE, prev_id_str, url);

		}
	}

	public void change_target(String currFolder, WaredDocument d, int target_id)
			throws IOException {

		int type = d.getDoc_type();
		int target = d.getTarget_id();
		int status = d.getStatus_id();

		String path = DocUtils.getPath(type, target, status);

		String oldPath = path + "/" + currFolder;
		String newPath = "";
		if (target_id == Environment.TARGET_KA2ED) {

			newPath = oldPath.replace(Environment.ARKAN_TAGHEZ_FOLDER,
					Environment.KA2ED_TAGHEZ_FOLDER);

			newPath = newPath.replace(Environment.ARKAN_TABLET_FOLDER,
					Environment.KA2ED_TABLET_FOLDER);

		} else if (target_id == Environment.TARGET_ARKAN) {
			newPath = oldPath.replace(Environment.KA2ED_TAGHEZ_FOLDER,
					Environment.ARKAN_TAGHEZ_FOLDER);

			newPath = newPath.replace(Environment.KA2ED_TABLET_FOLDER,
					Environment.ARKAN_TABLET_FOLDER);

		} else {
			// remove
			String pathx = DocUtils.getPath(type, target, status);
			File f1 = new File(pathx + "/" + currFolder);
			FileUtils.deleteDirectory(f1);
			return;
		}
		File f = new File(oldPath);
		File f2 = new File(newPath);
		DocUtils.assert_exist_path(f2.getAbsolutePath());
		f.renameTo(f2);

	}

}
