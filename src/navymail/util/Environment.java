package navymail.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import navymail.models.Travels.Rank_degree;
import navymail.rest.Travels.DBQueries.SeLectFromDB;

public class Environment {
	public static final String SERVER_PATH = "http://100.100.100.253:3000";
	public static final String PROJECT_PATH = "/media/backup/workspaceKamal/Navyadmin";
	public static final String LAPPATH = "/media/backup/navyadmin/public";

	private static final String BUNDLE_ENV = "ENV";
	private static final String WORKING_DIR = "workingDir";
	private static final String RESOURCE_DIR = "resourceDir";
	// /<zank>

	public static final String FORMAT = MediaType.APPLICATION_JSON
			+ "; charset=UTF-8";
	public static final String CONTENT = HttpHeaders.CONTENT_TYPE;

	public static HashMap<Integer, Integer> hm=new HashMap<>();
	//sa7b mn el tablet
	public static final String navy_print_path_da5ly = LAPPATH + "/working_folders/NavyMail/navy_print/da5ly";
	public static final String navy_print_path_khargy = LAPPATH + "/working_folders/NavyMail/navy_print/khargy";

	public static final String navy_commander_path_da5ly = LAPPATH + "/working_folders/NavyMail/navy_commander/da5ly";
	public static final String navy_commander_path_khargy = LAPPATH + "/working_folders/NavyMail/navy_commander/khargy";

	public static final String navy_arkan_path_da5ly = LAPPATH + "/working_folders/NavyMail/navy_arkan(commander_backup)/da5ly";
	public static final String navy_arkan_path_khargy = LAPPATH + "/working_folders/NavyMail/navy_arkan(commander_backup)/khargy";

	
	
	
	public static final String sader_DB_KHARGY = LAPPATH + "/sader/khargy";
	public static final String sader_DB_DA5LY = LAPPATH + "/sader/da5ly";
	// ka2ed folders
	
	
	public static final String DB_KHARGY = LAPPATH + "/documents/khargy";
	public static final String DB_DA5LY = LAPPATH + "/documents/da5ly";
	// ka2ed folders
	public static final String KA2ED_UNDER_PREPARE_KHARGY = LAPPATH
			+ "/working_folders/navy_tablets_dir/عرض القائد تحت التجهيز/khargy";
	public static final String KA2ED_UNDER_PREPARE_DA5LY = LAPPATH
			+ "/working_folders/navy_tablets_dir/عرض القائد تحت التجهيز/da5ly";
	public static final String KA2ED_TABLET_KHARGY = LAPPATH
			+ "/working_folders/navy_tablets_dir/عرض القائد على التابلت/khargy";
	public static final String KA2ED_TABLET_DA5LY = LAPPATH
			+ "/working_folders/navy_tablets_dir/عرض القائد على التابلت/da5ly";
	// ra2ees arkan folders
	public static final String ARKAN_UNDER_PREPARE_KHARGY = LAPPATH
			+ "/working_folders/navy_tablets_dir/عرض رئيس أركان تحت التجهيز/khargy";
	public static final String ARKAN_UNDER_PREPARE_DA5LY = LAPPATH
			+ "/working_folders/navy_tablets_dir/عرض رئيس أركان تحت التجهيز/da5ly";
	public static final String ARKAN_TABLET_KHARGY = LAPPATH
			+ "/working_folders/navy_tablets_dir/عرض رئيس أركان على التابلت/khargy";
	public static final String ARKAN_TABLET_DA5LY = LAPPATH
			+ "/working_folders/navy_tablets_dir/عرض رئيس أركان على التابلت/da5ly";
	public static String TASWEER_PATH[] = {
		LAPPATH + "/working_folders/scanner/Scanned Documents" , 
		LAPPATH+ "/working_folders/scanner/scanned Douments(Pro X)/Scanned Documents",
		LAPPATH + "/working_folders/scanner/scanned Douments(digital)"};

	public static final String khargy_folderName = "khargy";
	public static final String da5ly_folderName = "da5ly";

	public static final String KA2ED_TAGHEZ_FOLDER = "عرض القائد تحت التجهيز";
	public static final String KA2ED_TABLET_FOLDER = "عرض القائد على التابلت";
	public static final String ARKAN_TABLET_FOLDER = "عرض رئيس أركان على التابلت";
	public static final String ARKAN_TAGHEZ_FOLDER = "عرض رئيس أركان تحت التجهيز";

	public static final int TARGET_KA2ED = 1;
	public static final int TARGET_ARKAN = 2;
	public static final int TARGET_KA2ED_ARKAN = 3;
	public static final int TARGET_3MEED = 4;

	public static final int STATUS_UNDER_PREPARE = 1;
	public static final int STATUS_TABLET = 2;
	public static final int STATUS_FINISHED = 3;
	public static final int STATUS_MO3LLAK = 4;

	public static final int KHARGY = 1;
	public static final int DA5LY = 0;
	public static final int MAX_PAGE_PER_DOC = 3;

	// </zank>
	private static Environment environment;
	private final String workingDir;
	private final String resourceDir;

	public static Environment getInstance() {
		if (environment == null)
			try {
				environment = new Environment();
				fill_hashmap();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		return environment;
	}

	public Environment() throws FileNotFoundException, IOException, SQLException {
		//
		PropertiesLoader conload = PropertiesLoader.getInstance();
		workingDir = conload.getValue(BUNDLE_ENV, WORKING_DIR);
		resourceDir = conload.getValue(BUNDLE_ENV, RESOURCE_DIR);
		
	}

	public String getWorkingDir() {
		return PROJECT_PATH + workingDir;
	}

	public String getResourceDir() {
		return PROJECT_PATH + resourceDir;
	}
	public static void fill_hashmap() throws SQLException{
		String query = "select * from rank_degree";
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Rank_degree> res = q.getRank_degree();
		q.closeConnection();
		for (int i = 0; i < res.size(); i++) {
			hm.put(res.get(i).getId(), res.get(i).getGroup_id());
		}
	}
}
