package navymail.rest.Travels;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.languages.ArabicLigaturizer;
import com.itextpdf.text.pdf.languages.LanguageProcessor;
import navymail.API.TravelAPI.Report_with_units;
import navymail.Helpers.Master.MyResponse;
import navymail.Queries.SeLect;
import navymail.models.Master.Units;
import navymail.models.Travels.Mission;
import navymail.models.Travels.Rank_degree;
import navymail.models.Travels.Traveler;
import navymail.models.Travels.Traveler_mission;
import navymail.rest.Travels.DBQueries.SeLectFromDB;
import navymail.util.Environment;
@Path("/ReportUnits")
public class travels_report_units implements Report_with_units{
	private static String FILE_Students = Environment.LAPPATH + "/StudentsReport.pdf";
	private static String FILE_SubOfficer = Environment.LAPPATH + "/SubOfficerReport.pdf";
	private static String FILE_Officer = Environment.LAPPATH + "/OfficerReport.pdf";
	private static BaseFont bf;
	private static Font font;
	private static Font fontSmall;
	private static Font fontBlod;
	static HashMap<Integer, Float> minsize;
	Document document = new Document();
	int index;

	public static void main(String[] args) throws Exception {
		// Travels_report tr = new Travels_report();
		// tr.print_Missions(1, 1, 1, 2);

	}

	public void pushInHashMap() {
		int[] fonts = { 14, 15, 16, 17, 18, 19, 20, 21, 22 };
		float[] size = { 40, 38, 36, 34, 32, 30, 29, 28, 27 };
		minsize = new HashMap<>();
		for (int i = 0; i < fonts.length; i++) {
			minsize.put(fonts[i], size[i]);
		}

	}

	public Response print_Missions(int mission_id, int type, int ship_id, int mission_type) throws Exception {
		pushInHashMap();
		index = 1;
		String ship_name = get_ship_name(ship_id);
		String[] StudentTitle = null;
		String[] OfficerTitle = null;
		String[] SubOfficerTitle = null;
		Travels_report tr = new Travels_report();
		String[] date = tr.getdate(mission_id);
		String[] date0 = date[0].split("-");
		String[] date1 = date[1].split("-");
		String date_0 = date0[2] + "-" + date0[1] + "-" + date0[0];
		String date_1 = date1[2] + "-" + date1[1] + "-" + date1[0];
		System.out.println(date_0);
		System.out.println(date_1);
		if (mission_type == 1) {
			String[] arrStudent = { "كشف بأسماء", " الطلبة المقرر سفرهم على متن سفينة الأمداد " + ship_name,
					"  أثناء الرحلة التدريبية لطلبة الكلية البحرية فى الفترة من" + date_1 + " إلى " + date_0,
					"_____________" };
			String[] arrSubOfficer = { "كشف بأسماء", " ضباط الصف المقرر سفرهم على متن سفينة الأمداد " + ship_name,
					"  أثناء الرحلة التدريبية لطلبة الكلية البحرية فى الفترة من" + date_1 + " إلى " + date_0,
					"_____________" };
			String arrOfficers[] = { "كشف بأسماء", " الضباط المقرر سفرهم على متن سفينة الأمداد " + ship_name,
					"  أثناء الرحلة التدريبية لطلبة الكلية البحرية فى الفترة من" + date_1 + " إلى " + date_0,
					"_____________" };
			StudentTitle = arrStudent;
			OfficerTitle = arrOfficers;
			SubOfficerTitle = arrSubOfficer;
		} else {
			String arrStudent[] = { "كشف بأسماء", " الطلبة المقرر سفرهم على متن سفينة الأمداد " + ship_name,
					"  أثناء الرحلة التدريبية فى الفترة من" + date_1 + " إلى " + date_0, "_____________" };
			String arrSubOfficer[] = { "كشف بأسماء", " ضباط الصف المقرر سفرهم على متن سفينة الأمداد " + ship_name,
					"  أثناء الرحلة التدريبية فى الفترة من" + date_1 + " إلى " + date_0, "_____________" };
			String arrOfficers[] = { "كشف بأسماء", "الضباط المقرر سفرهم على متن سفينة الأمداد " + ship_name,
					"  أثناء الرحلة التدريبية فى الفترة من" + date_1 + " إلى " + date_0, "_____________" };
			StudentTitle = arrStudent;
			OfficerTitle = arrOfficers;
			SubOfficerTitle = arrSubOfficer;
		}
		System.err.println("typeeeeeeee" + type);
		switch (type) {
		case 3:
			ArrayList<Traveler> t = showStudent(mission_id, ship_id);
			Collections.sort(t);
			System.err.println("Arry size" + t.size());
			PdfWriter.getInstance(document, new FileOutputStream(FILE_Students));
			if (t.size() > 0)
				t.add(0, t.get(0));
			document.open();
			while (index < t.size()) {
				addTitlePage(document, StudentTitle);
				document.add(createStudenetTable(t));
				document.newPage();
			}
			break;
		case 2:
			ArrayList<Traveler> t_2 = showSafZobat(mission_id, ship_id);
			Collections.sort(t_2);
			PdfWriter.getInstance(document, new FileOutputStream(FILE_SubOfficer));
			if (t_2.size() > 0)
				t_2.add(0, t_2.get(0));
			document.open();
			while (index < t_2.size()) {
				addTitlePage(document, SubOfficerTitle);
				document.add(createSubOfficerTable(t_2));
				document.newPage();
			}
			break;
		case 1:
			ArrayList<Traveler> t_3 = showofficers(mission_id, ship_id);
			Collections.sort(t_3);
			if (t_3.size() > 0)
				t_3.add(0, t_3.get(0));
			PdfWriter.getInstance(document, new FileOutputStream(FILE_Officer));
			document.open();
			while (index < t_3.size()) {
				addTitlePage(document, OfficerTitle);
				document.add(createOfficerTable(t_3));
				document.newPage();
			}

			break;
		}
		document.close();
		return new MyResponse().success();

	}

	public String[] getdate(int mission_id) {
		String[] date = new String[2];
		String query = "SELECT * FROM missions where id=" + mission_id;
		SeLectFromDB q;
		try {
			q = new SeLectFromDB();
			q.openConnection(query);
			ArrayList<Mission> list = q.getMissions();
			String txt;
			txt = list.get(0).getDate_to();
			Travels_report tr = new Travels_report();
			date[0] = tr.toArabic(txt);
			txt = list.get(0).getDate_from();
			date[1] = tr.toArabic(txt);
			q.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public String get_ship_name(int ship_id) throws SQLException {
		String query = "SELECT name FROM ships where id=" + ship_id;
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		String ship_name = q.getShipName();
		q.closeConnection();
		return ship_name;
	}

	public ArrayList<Traveler> getData__byship_id(int mission_id, int type, int ship_id) throws Exception {
		String query = "select * from travelers_missions where mission_id = " + mission_id + " and ship_id=" + ship_id
				+ " and position=1";
		System.out.println(query);
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Traveler_mission> res = q.getTravelers_missions();
		System.err.println("Sizeeeee" + res.size());
		q.closeConnection();
		Rank_degrees myObj = new Rank_degrees();
		HashMap<Integer, Integer> ranks = myObj.getRanks();

		Travelers obj = new Travelers();
		ArrayList<Traveler> t = new ArrayList<Traveler>();
		for (int i = 0; i < res.size(); i++) {
			Traveler curr = obj.search_by_military(res.get(i).getTraveler_id());
			if (ranks.get(curr.getRank_id()) == type)
				t.add(curr);
		}
		System.err.println("Sizeeeee after rank" + t.size());
		return t;
	}

	public HashMap<Integer, String> getRanks() throws SQLException {
		String query = "select * from rank_degree";
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Rank_degree> res = q.getRank_degree();
		q.closeConnection();
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		for (int i = 0; i < res.size(); i++) {
			hm.put(res.get(i).getId(), res.get(i).getRank_name());
		}
		return hm;
	}
	public HashMap<Integer, String> getUnits() throws Exception {
		String query = "select * from units";
		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<Units> res = q.getUnits();
		q.closeConnection();
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		for (int i = 0; i < res.size(); i++) {
			hm.put(res.get(i).getId(), res.get(i).getName());
		}
		return hm;
	}
	public ArrayList<Traveler> showStudent(int mission_id, int ship_id) throws Exception {
		return getData__byship_id(mission_id, 3, ship_id);
	}

	public ArrayList<Traveler> showSafZobat(int mission_id, int ship_id) throws Exception {
		return getData__byship_id(mission_id, 2, ship_id);
	}

	public ArrayList<Traveler> showofficers(int mission_id, int ship_id) throws Exception {
		return getData__byship_id(mission_id, 1, ship_id);
	}

	public String toArabic(String en) {
		System.out.println("number" + en);
		String alph = "٠١٢٣٤٥٦٧٨٩";
		String answer = "";
		for (int i = 0; i < en.length(); i++) {
			int v = en.charAt(i) - '0';
			if (en.charAt(i) >= '0' && en.charAt(i) <= '9')
				answer += alph.charAt(v);
			else
				answer += en.charAt(i);

		}
		System.out.println(answer);
		return answer;
	}

	public PdfPTable createSubOfficerTable(ArrayList<Traveler> traveler)
			throws Exception {
		// TODO Auto-generated method stub
		int ArrySize = traveler.size();
		int NoOfRecords = numofpages(ArrySize);

		PdfPTable table = new PdfPTable(5);
		PdfPCell cell;
		table.setWidthPercentage(100);
		float[] cw = { 40f, 40f, 25f, 28f, 10f };
		table.setWidths(cw);
		bf = BaseFont.createFont(Environment.LAPPATH + "/trado.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		font = new Font(bf, 18);
		fontSmall=new Font(bf, 13);
		HashMap<Integer, String> hm = getRanks();
		HashMap<Integer, String> un = getUnits();
		// we add a cell with colspan 3
		LanguageProcessor al = new ArabicLigaturizer();
		Paragraph ph;
		String arry[] = { "الوحدة", "أسـم", "درجة", "الرقم العسكرى", "م" };
		for (int i = 0; i < arry.length; i++) {
			ph = new Paragraph(al.process(arry[i]), font);
			cell = new PdfPCell(ph);
			cell.setMinimumHeight(30f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}
		int i = 1;
		for (int j = index; j < traveler.size(); j++) {
			Traveler trav = traveler.get(j);
			index++;
			if (j % NoOfRecords != 0) {
				System.out.println("errror" + j % NoOfRecords);
				ph = new Paragraph(al.process(un.get(trav.getUnit_id())), fontSmall);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setMinimumHeight(minsize.get(NoOfRecords));
				table.addCell(cell);
				ph = new Paragraph(al.process(trav.getName()), fontSmall);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(hm.get(trav.getRank_id())), fontSmall);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(toArabic("" + trav.getMilitary_num())), fontSmall);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(toArabic("" + j)), fontSmall);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				i++;

			} else {
				ph = new Paragraph(al.process(un.get(trav.getUnit_id())), fontSmall);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setMinimumHeight(minsize.get(NoOfRecords));
				table.addCell(cell);
				ph = new Paragraph(al.process(trav.getName()), fontSmall);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(hm.get(trav.getRank_id())), fontSmall);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(toArabic("" + trav.getMilitary_num())), fontSmall);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(toArabic("" + j)), fontSmall);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				return table;
			}

		}
		return table;
	}

	public PdfPTable createOfficerTable(ArrayList<Traveler> traveler)
			throws Exception {
		// TODO Auto-generated method stub
		int ArrySize = traveler.size();
		int NoOfRecords = numofpages(ArrySize);
		System.err.println("errror" + NoOfRecords);
		PdfPTable table = new PdfPTable(6);
		PdfPCell cell;
		table.setWidthPercentage(100);
		float[] cw = { 44f, 45f, 29f, 29f, 28f, 10f };
		table.setWidths(cw);
		bf = BaseFont.createFont(Environment.LAPPATH + "/trado.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		font = new Font(bf, 13);
		Font font_High = new Font(bf, 18);
		HashMap<Integer, String> hm = getRanks();
		HashMap<Integer, String> un = getUnits();
		// we add a cell with colspan 3
		LanguageProcessor al = new ArabicLigaturizer();
		Paragraph ph;
		String arry[] = { "الوحدة", "أسـم", "درجة", "الرقم العسكرى", "الأقدمية", "م" };
		for (int i = 0; i < arry.length; i++) {
			ph = new Paragraph(al.process(arry[i]), font_High);
			cell = new PdfPCell(ph);
			cell.setMinimumHeight(30f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

		}
		// int i = 1;
		for (int j = index; j < traveler.size(); j++) {
			Traveler trav = traveler.get(j);
			index++;
			if (j % NoOfRecords != 0) {
				ph = new Paragraph(al.process(un.get(trav.getUnit_id())), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setMinimumHeight(minsize.get(NoOfRecords));
				table.addCell(cell);
				ph = new Paragraph(al.process(trav.getName()), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(hm.get(trav.getRank_id())), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(toArabic("" + trav.getMilitary_num())), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(toArabic("" + trav.getSeniority_num())), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(toArabic("" + j)), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				// i++;
			} else {
				ph = new Paragraph(al.process(un.get(trav.getUnit_id())), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setMinimumHeight(minsize.get(NoOfRecords));
				table.addCell(cell);
				ph = new Paragraph(al.process(trav.getName()), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(hm.get(trav.getRank_id())), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(toArabic("" + trav.getMilitary_num())), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(toArabic("" + trav.getSeniority_num())), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(toArabic("" + j)), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				// i++;
				// for (int j = 0; j < arry.length; j++) {
				// ph = new Paragraph(al.process(arry[j]), font);
				// cell = new PdfPCell(ph);
				// cell.setMinimumHeight(30f);
				// cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				// table.addCell(cell);
				return table;
			}
		}

		return table;
	}

	public PdfPTable createStudenetTable(ArrayList<Traveler> traveler)
			throws DocumentException, IOException, SQLException {
		// TODO Auto-generated method stuby
		int ArrySize = traveler.size();
		int NoOfRecords = numofpages(ArrySize);
		System.out.println(ArrySize);
		PdfPTable table = new PdfPTable(4);
		PdfPCell cell;
		table.setWidthPercentage(100);
		float[] cw = { 45f, 30f, 30f, 10f };
		table.setWidths(cw);
		bf = BaseFont.createFont(Environment.LAPPATH + "/trado.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		font = new Font(bf, 14);
		Font font_High = new Font(bf, 18);
		HashMap<Integer, String> hm = getRanks();
		// we add a cell with colspan 3
		LanguageProcessor al = new ArabicLigaturizer();
		Paragraph ph;
		String arry[] = { "أسـم", "رتبة", "الرقم العسكرى", "م" };
		for (int i = 0; i < arry.length; i++) {
			ph = new Paragraph(al.process(arry[i]), font_High);
			cell = new PdfPCell(ph);
			cell.setMinimumHeight(30f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}
		int i = 1;
		for (int j = index; j < traveler.size(); j++) {
			Traveler trav = traveler.get(j);
			index++;
			if (j % NoOfRecords != 0) {
				ph = new Paragraph(al.process(trav.getName()), font);
				cell = new PdfPCell(ph);
				cell.setMinimumHeight(minsize.get(NoOfRecords));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(hm.get(trav.getRank_id())), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(toArabic("" + trav.getMilitary_num())), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(toArabic("" + (j))), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				// i++;
			} else {
				ph = new Paragraph(al.process(trav.getName()), font);
				cell = new PdfPCell(ph);
				cell.setMinimumHeight(minsize.get(NoOfRecords));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(hm.get(trav.getRank_id())), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(toArabic("" + trav.getMilitary_num())), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				ph = new Paragraph(al.process(toArabic("" + (j))), font);
				cell = new PdfPCell(ph);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				// for (int z = 0; z < arry.length; z++) {
				// ph = new Paragraph(al.process(arry[z]), font);
				// cell = new PdfPCell(ph);
				// cell.setMinimumHeight(30f);
				// cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				// table.addCell(cell);
				// }
				// i++;
				return table;
			}
		}
		return table;
	}

	public int numofpages(int nr) {
		int recordperpage = 22;
		for (int z = 22; z >= 14 && nr > z / 2; z--) {
			if (nr % z == 0 || (nr % z) >= (z / 2)) {
				recordperpage = z;
				break;
			}
		}
		return recordperpage;

	}

	public void addTitlePage(Document document, String title[]) throws DocumentException, IOException {
		// TODO Auto-generated method stub
		bf = BaseFont.createFont(Environment.LAPPATH + "/trado.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		font = new Font(bf, 18);
		fontBlod = new Font(bf, 18, Font.BOLD);
		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 1);
		// Lets write a big header
		LanguageProcessor al = new ArabicLigaturizer();
		Paragraph ph;
		for (String element : title) {
			ph = new Paragraph(al.process(element), fontBlod);
			ph.setAlignment(Element.ALIGN_CENTER);
			preface.add(ph);
		}
		addEmptyLine(preface, 1);
		document.add(preface);
	}

	public void addEmptyLine(Paragraph paragraph, int number) {
		// TODO Auto-generated method stub
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}
