package navymail.rest.Tablet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import com.itextpdf.text.pdf.codec.wmf.Point;
import navymail.API.TabletAPI.TabletAPI;
import navymail.Helpers.Master.MyResponse;
import navymail.Helpers.Wared.DocUtils;
import navymail.Queries.SeLect;
import navymail.models.Wared.WaredDocument;
import navymail.rest.Wared.Documents;
import navymail.util.Environment;
import navymail.util.Jason;

/**
 * 
 * @author Zankalony 11 may 2015
 */
@Path("tablet")
public class Tablet implements TabletAPI {

	/**
	 * يجب الأخد في الأعتبار انه قد يكون هناك اتنين أو أكثر عند القائد أو رئيس
	 * الأركان
	 */

	private static final int arkan = 2;
	private static final int ka2ed = 1;
	// we want khargy and da5ly to contain documents on the tablet
	// note that may be there are two tablet have the same status
	// يعني القائد أو رئيس الأركان عنده تابين
	// وانت محتاج تسحب تاب واحد بس منهم
	private ArrayList<WaredDocument> khargy;
	private ArrayList<WaredDocument> dakhly;

	// to know which documents currently on the tablet
	private HashSet<String> navy_commander = new HashSet<String>();
	private HashSet<String> navy_print = new HashSet<String>();

	/**
	 * to detect the unsigned documents , if it does not exist in navy_print row
	 * 0 for da5ly , 1 for khargy if visited[i][j]=false then it should go back
	 * to under prepare
	 */
	private boolean[][] Visited = new boolean[2][];

	private int da5ly_cnt = 0;
	private int khargy_cnt = 0;

	public Response tablet_arkan() throws Exception {
		fill_hash_with_current_documents();

		// STEP 1 replace Images
		replace_image_for(arkan);

		change_status_to_kaed_underprepare();

		// change the some docs arkan under prepare to finish ;)
		change_signed_to_finish();

		String output = Jason.toJson(new Point(da5ly_cnt, khargy_cnt));

		return new MyResponse(output).success();
	}

	private void change_signed_to_finish() throws Exception {
		Documents doc = new Documents();
		for (int j = 0; j < dakhly.size(); j++) {
			String name = getDocName(dakhly.get(j));
			if (contains(navy_print, name) && !contains(navy_commander, name))
				doc.update(dakhly.get(j).getId(), 2, 3);
		}
		for (int j = 0; j < khargy.size(); j++) {
			String name = getDocName(khargy.get(j));
			if (contains(navy_print, name) && !contains(navy_commander, name))
				doc.update(khargy.get(j).getId(), 2, 3);
		}
	}

	private void change_status_to_kaed_underprepare() throws Exception {
		Documents doc = new Documents();
		for (int j = 0; j < dakhly.size(); j++) {
			String name = getDocName(dakhly.get(j));
			if (contains(navy_commander, name)) {
				doc.update(dakhly.get(j).getId(), 1, 4);
				doc.update(dakhly.get(j).getId(), 1, 1);

			}
		}
		for (int j = 0; j < khargy.size(); j++) {
			String name = getDocName(khargy.get(j));
			if (contains(navy_commander, name)) {
				doc.update(khargy.get(j).getId(), 1, 4);
				doc.update(khargy.get(j).getId(), 1, 1);
			}
		}
	}

	private void change_unsigned_to_underprepare(int target, HashSet<String> set)
			throws Exception {
		Documents doc = new Documents();
		for (int i = 0; i < Visited[0].length; i++)
			if (!Visited[0][i] && contains(set, getDocName(dakhly.get(i))))
				doc.update(dakhly.get(i).getId(), target, 1);

		for (int i = 0; i < Visited[1].length; i++)
			if (!Visited[1][i] && contains(set, getDocName(khargy.get(i))))
				doc.update(khargy.get(i).getId(), target, 1);
	}

	private boolean contains(HashSet<String> set, String title) {
		for (String str : set)
			if (str.contains(title))
				return true;
		return false;
	}

	private void change_signed_to_finished(int target) throws Exception {
		Documents doc = new Documents();
		for (int i = 0; i < Visited[0].length; i++)
			if (Visited[0][i]
					&& contains(navy_commander, getDocName(dakhly.get(i))))
				doc.update(dakhly.get(i).getId(), target, 3);

		for (int i = 0; i < Visited[1].length; i++)
			if (Visited[1][i]
					&& contains(navy_commander, getDocName(khargy.get(i))))
				doc.update(khargy.get(i).getId(), target, 3);
	}

	public String getDocName(WaredDocument d) {
		return d.getDoc_number() + " " + d.getTitle();
	}

	private void replace_image_for(int target) throws SQLException,
			InterruptedException, IOException {
		String query = "select * from documents where status_id=2 and target_id="
				+ target + " and doc_type=1";
		SeLect q = new SeLect();
		q.openConnection(query);
		khargy = q.getDocumentsSearch();
		q.closeConnection();
		Visited[1] = new boolean[khargy.size()];
		Arrays.fill(Visited[1], false);
		replace_image_to_original_document(Environment.navy_print_path_khargy,
				khargy, 1);

		query = "select * from documents where status_id=2 and target_id="
				+ target + " and doc_type=0";
		q.openConnection(query);
		dakhly = q.getDocumentsSearch();
		q.closeConnection();
		Visited[0] = new boolean[dakhly.size()];
		Arrays.fill(Visited[0], false);
		replace_image_to_original_document(Environment.navy_print_path_da5ly,
				dakhly, 0);
	}

	public Response tablet_kaed() throws Exception {
		fill_hash_with_current_documents();
		replace_image_for(ka2ed);// arkan
		// STEP 2 change unsigned documents to under prepare
		// change_unsigned_to_underprepare(ka2ed, navy_commander);
		change_signed_to_finished(ka2ed);
		String output = Jason.toJson(new Point(da5ly_cnt, khargy_cnt));

		return new MyResponse(output).success();
	}

	private void replace_image_to_original_document(String path,
			ArrayList<WaredDocument> data, int index)
			throws InterruptedException, IOException {

		File f = new File(path);
		if (f.exists()) {
			File[] list = f.listFiles();
			for (int i = 0; i < list.length; i++) {
				for (int j = 0; j < data.size(); j++) {
					String name = getDocName(data.get(j));
					if (list[i].getName().contains(name)) {
						Visited[index][j] = true;
						String database_path = data.get(j).getUrl();
						File[] images = list[i].listFiles();
						for (int k = 0; k < images.length; k++) {
							File imgk = images[k];
							String image_on_database = database_path + "/"
									+ imgk.getName();
							File deletefromdatabase = new File(
									image_on_database);
							//System.err.println("image name"+imgk.getName());
							if (deletefromdatabase.exists())
								deletefromdatabase.delete();
							System.err.println("image name"+imgk.getName());
							if(!imgk.getName().equals("i21.jpg")&&!imgk.getName().equals("i22.jpg"))
							DocUtils.copy_folder(imgk.getAbsolutePath(),
									image_on_database);
						}
					}
				}
			}
		}
	}

	private void fill_hash_with_current_documents() {
		navy_commander.clear();
		navy_print.clear();
		da5ly_cnt = 0;
		khargy_cnt = 0;
		// read from navy print

		File f = new File(Environment.navy_print_path_da5ly);
		if (f.exists()) {
			File[] list = f.listFiles();
			da5ly_cnt = list.length;
			for (int i = 0; i < list.length; i++)
				navy_print.add(list[i].getName());
		}
		f = new File(Environment.navy_print_path_khargy);
		if (f.exists()) {
			File[] list = f.listFiles();
			khargy_cnt = list.length;
			for (int i = 0; i < list.length; i++)
				navy_print.add(list[i].getName());
		}
		// read from navy commander
		f = new File(Environment.navy_commander_path_da5ly);
		if (f.exists()) {
			File[] list = f.listFiles();
			for (int i = 0; i < list.length; i++)
				navy_commander.add(list[i].getName());
		}
		f = new File(Environment.navy_commander_path_khargy);
		if (f.exists()) {
			File[] list = f.listFiles();
			for (int i = 0; i < list.length; i++)
				navy_commander.add(list[i].getName());
		}
	}

	// 1 ka2eed / 2 raes arkan
	int[] dx = { 1, 2 };
	// 1 t7t el tagheez / 2 3ala el tablet
	int[] dy = { 1, 2 };
	// 0 da5ly / 1 khargy
	int[] dz = { 0, 1 };

	public Response current_status() throws Exception {
		ArrayList<Integer> status = new ArrayList<Integer>();
		SeLect q = new SeLect();
		for (int i = 0; i < dx.length; i++) {
			for (int j = 0; j < dy.length; j++) {
				for (int k = 0; k < dz.length; k++) {
					String query = "SELECT COUNT(*) FROM documents where target_id="
							+ dx[i]
							+ " and status_id="
							+ dy[j]
							+ " and doc_type=" + dz[k];
					q.openConnection(query);
					status.add(q.getCount());
					q.closeConnection();
				}
			}
		}
		String output = Jason.toJson(status);
		return new MyResponse(output).success();
	}
}
