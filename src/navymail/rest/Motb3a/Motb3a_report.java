package navymail.rest.Motb3a;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.languages.ArabicLigaturizer;
import com.itextpdf.text.pdf.languages.LanguageProcessor;
import navymail.API.Motb3aAPI.Report_Motb3a;
import navymail.Helpers.Master.MyResponse;
import navymail.Queries.SeLect;
import navymail.models.Master.Units;
import navymail.models.Motb3a.Resp_unit;
import navymail.models.Motb3a.istagel;
import navymail.models.Motb3a.motb3a_Tashirat_name;
import navymail.models.Wared.WaredDocument;
import navymail.util.Environment;
/**
 * 
 * @author Kamal
 */
@Path("/motb3a_report")
public class Motb3a_report implements Report_Motb3a {
	private static String FILE_MOTB3A = Environment.LAPPATH + "/MOTB3A.pdf";
	private static BaseFont bf;
	private static Font font;
	private static Font fontS;
	private static Font fonte;
	private static Font fontBlod;
	int index;
	Document document = new Document(PageSize.A4.rotate());

	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public Response print_motb3a(String date_from, String date_to,int unit_id,int type) throws Exception {
		System.err.println("Prinnnnnnnnnt");
		PdfWriter.getInstance(document, new FileOutputStream(FILE_MOTB3A));
		document.open();
		Image image1 = Image.getInstance(Environment.LAPPATH + "/navy.png");
		image1.scaleAbsolute(100, 110);
		image1.setAbsolutePosition(20f, 480f);
		Image image2 = Image.getInstance(Environment.LAPPATH + "/map.png");
		image2.scaleAbsolute(200, 110);
		image2.setAbsolutePosition(620f, 480f);
		String date_0 = "";
		String date_1 = "";
		date_0 = toArabic(date_from);
		date_1 = toArabic(date_to);
		String arr[] = { "بيان بموضوعات المتابعة خلال الفترة من", date_0+" إلى "+ date_1,
				 "----------------" };
		document.add(image1);
		document.add(image2);
		addTitlePage(document, arr);
		document.add(createMotabaTable(getData(date_from, date_to,unit_id),type));
		document.close();
		return new MyResponse().success();
	}

	public ArrayList<WaredDocument> getData(String date_from, String date_to , int unit_id) throws Exception {
		String query = "select * from documents where doc_need_replay =2 ";
		System.out.println(date_from+"  "+date_to);
		String dateCondition1 = dateCondition("created_at", date_from, date_to);
		if (unit_id>0) {
			query += "and unit_id = " + unit_id;
		}
		if(dateCondition1!=null)
		query += " and "+dateCondition1;
		System.out.println(query);
		SeLect s = new SeLect();
		s.openConnection(query);
		ArrayList<WaredDocument> W_arry = new ArrayList<>();
		W_arry = s.getDocuments();
		s.closeConnection();
		return W_arry;
	}

	private static String dateCondition(String attr, String from, String to) throws ParseException {
		if ((from == null||from.equals("")) && (to == null||to.equals("")))
			return null;

		String col = " documents." + attr;

		if (from == null||from.equals(""))
			return col + " <=  '" + format.format(to_D(to)) + "'";

		if (to == null||to.equals(""))
			return col + " >= '" + format.format(to_D(from)) + "'";

		return col + " BETWEEN '" + format.format(to_D(from)) + "' and '" + format.format(to_D(to)) + "'";

	}

	private static Date to_D(String s/* "dd-MM-yyyy" */) throws ParseException {
		if (s == null || s.equals(""))
			return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date d = dateFormat.parse(s);
		return d;
	}

	public static WaredDocument search_by_id(int id)  {
		try{
		String query = "select * from documents where id = " + id;
		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<WaredDocument> res = q.getDocuments();
		q.closeConnection();
		return res.get(0);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
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
	public HashMap<Integer, String> getTashira() throws Exception {
		String query = "select * from motb3a_Tashirat_name";
		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<motb3a_Tashirat_name> res = q.getmotb3a_tashirat_name();
		q.closeConnection();
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		for (int i = 0; i < res.size(); i++) {
			hm.put(res.get(i).getId(), res.get(i).getName());
		}
		return hm;
	}

	public PdfPTable createMotabaTable(ArrayList<WaredDocument> doc , int type) throws DocumentException, Exception, SQLException {
		// TODO Auto-generated method stub
		int index = 1;
		Collections.sort(doc);
		HashMap<Integer, String> un = getUnits();
		HashMap<Integer, String> ts = getTashira();
		BaseColor red_b=new BaseColor(243, 105, 115); // Created to use in highlight color for mosalsal
		BaseColor blue_b=new BaseColor(132,117,250);  // Created to use in highlight color for mosalsal
		BaseColor green_b=new BaseColor(38,245,114);// Created to use in highlight color for mosalsal
		BaseColor yellow_b=new BaseColor(249,238,81);// Created to use in highlight color for mosalsal
		PdfPCell cell; 
		PdfPTable table ;
		Paragraph ph;
		PdfPTable ist_table_2 = new PdfPTable(3);
		PdfPCell ist_cell;
		ist_table_2.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		float[] cw_2 = { 10f, 25f, 20f };
		if(type ==1){  //quad
			ist_table_2.setWidths(cw_2);
			table	= new PdfPTable(9);
		table.setWidthPercentage(100);
		table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		float[] cw = { 48f,15f, 33f, 15f,9f,20f, 15f, 9f, 8f }; /**edit H&B***/ 
		//float[] cw = { 28f, 24f, 25f, 50f, 25f, 20f, 20f, 8f };
		// float[] cw = { 12f, 20f , 20f, 25f, 50f,25f,20f,28f};
		table.setWidths(cw);
		String arry[] = { "موقف الرد","الإستعجالات", "جهة المتابعة/الرد","تاشيرة السيد القائد", "الموضوع", "تاريخ الصادر المحول","رقم الصادر المحول","الجهة", "تاريخ الوارد",
				"رقم الوارد", "م" };
		for (int i = arry.length-1 ; i >= 3; i--) {
			ph = new Paragraph(arry[i], font);
			cell = new PdfPCell(ph);
			cell.setMinimumHeight(25f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
		}
		for (int i = 2 ; i >= 0; i--) {
			ph = new Paragraph(arry[i], font);
			ist_cell = new PdfPCell(ph);
			ist_cell.setMinimumHeight(25f);
			ist_cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			ist_table_2.addCell(ist_cell);
		}
		
		table.setHeaderRows(1);
		table.addCell(ist_table_2);
		}else if(type ==2){  //arkan
			
			ist_table_2.setWidths(cw_2);
			 table = new PdfPTable(9);
			//PdfPCell cell; 
			table.setWidthPercentage(100);
			table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
			float[] cw = { 48f,15f,  33f, 15f,9f,20f, 15f, 9f, 8f }; /**edit H&B***/ 
			//float[] cw = { 28f, 24f, 25f, 50f, 25f, 20f, 20f, 8f };
			// float[] cw = { 12f, 20f , 20f, 25f, 50f,25f,20f,28f};
			table.setWidths(cw);
			String arry[] = { "موقف الرد","الإستعجالات", "جهة المتابعة/الرد","تاشيرة السيد رئيس الأركان", "الموضوع", "تاريخ الصادر المحول","رقم الصادر المحول","الجهة", "تاريخ الوارد",
					"رقم الوارد", "م" };
			
			for (int i = arry.length-1 ; i >= 3; i--) {
				ph = new Paragraph(arry[i], font);
				cell = new PdfPCell(ph);
				cell.setMinimumHeight(25f);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}
			for (int i = 2 ; i >= 0; i--) {
				ph = new Paragraph(arry[i], font);
				ist_cell = new PdfPCell(ph);
				ist_cell.setMinimumHeight(25f);
				ist_cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				ist_table_2.addCell(ist_cell);
			}
			
			table.setHeaderRows(1);
			table.addCell(ist_table_2);
		}else{  //all
			ist_table_2.setWidths(cw_2);
			 table = new PdfPTable(10);
				//PdfPCell cell; 
				table.setWidthPercentage(100);
				table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
				float[] cw = { 48f,15f, 15f, 33f, 15f,9f,20f, 15f, 9f, 8f }; /**edit H&B***/ 
				//float[] cw = { 28f, 24f, 25f, 50f, 25f, 20f, 20f, 8f };
				// float[] cw = { 12f, 20f , 20f, 25f, 50f,25f,20f,28f};
				table.setWidths(cw);
				String arry[] = { "موقف الرد","الإستعجالات", "جهة المتابعة/الرد","تاشيرة السيد القائد","تاشيرة السيد رئيس الأركان", "الموضوع", "تاريخ الصادر المحول","رقم الصادر المحول","الجهة", "تاريخ الوارد",
						"رقم الوارد", "م" };
				for (int i = arry.length-1 ; i >= 3; i--) {
					ph = new Paragraph(arry[i], font);
					cell = new PdfPCell(ph);
					cell.setMinimumHeight(25f);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
				}
				for (int i = 2 ; i >= 0; i--) {
					ph = new Paragraph(arry[i], font);
					ist_cell = new PdfPCell(ph);
					ist_cell.setMinimumHeight(25f);
					ist_cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					ist_table_2.addCell(ist_cell);
				}
				
				table.setHeaderRows(1);
				table.addCell(ist_table_2);
		}
		bf = BaseFont.createFont(Environment.LAPPATH + "/trado.ttf", BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
		font = new Font(bf, 12);
		fontS = new Font(bf, 12);
		fonte = new Font(bf, 10);
		Font green=new Font(bf,12,Font.BOLD);
		green.setColor(0,180,50);
		Font red=new Font(bf,12,Font.BOLD);
		red.setColor(254, 0, 0);
		// LanguageProcessor al = new ArabicLigaturizer();
		
		
		
		
		for (int i = 0; i < doc.size(); i++) {
			PdfPTable ist_table = new PdfPTable(3);
			ist_table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
			float[] cw_3 = { 10f, 25f, 20f };
			ist_table.setWidths(cw_3);
			boolean flag_drase=false;
			boolean flag_else=false;
			boolean flag_dated=false;
			WaredDocument d = doc.get(i);
			Paragraph preface = new Paragraph();
			Paragraph preface2 ;
			Paragraph preface3 = new Paragraph();
			Paragraph preface4 = new Paragraph();
			String[] date0 = d.getCreated_at().toString().split("-");
			String date = date0[2] + "-" + date0[1] + "-" + date0[0];
			String[] dates;
			String datet;
			for (int element : d.getTashira()) {
				if(element==1)
					flag_drase=true;
				else if(element==6)
					flag_else=true;
				else if(element==7)
					flag_dated=true;
				
			}
			//System.out.println(d.getTrans_out_date().equals(null));
			if(d.getTrans_out_num()<0){
				d.setTrans_out_num(0);
			}

			if(d.getTrans_out_date()==null){
				// dates = d.getTrans_out_date().toString().split("-");
				 datet = "";
			}else{
			 dates = d.getTrans_out_date().toString().split("-");
			 datet = dates[2] + "-" + dates[1] + "-" + dates[0];
			}
			ph = new Paragraph(toArabic("" + index), fontS);
			cell = new PdfPCell(ph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			//ph = new Paragraph(toArabic("" + d.getDoc_number()), fontS);
			/***Begin Code highlight**/
			Chunk chunk = new Chunk(toArabic(""+d.getDoc_number()), fontS);
			if(d.getUnit_id()==1)
			chunk.setBackground(red_b);
			else if(flag_drase){
			chunk.setBackground(yellow_b);
			}
//			else if(flag_else){
//			//chunk.setBackground(blue_b);
//			}
			else if(flag_dated){
				chunk.setBackground(green_b);
				}
		      /***END**/
			
			ph = new Paragraph(chunk); // el line da badl mn el line al ta7t
			//ph = new Paragraph(toArabic("" + index), fontS);
			/****w 7atet el Font in c ****/
			cell = new PdfPCell(ph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			ph = new Paragraph(toArabic("" + date), fontS);
			cell = new PdfPCell(ph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			/***** edit code H&B*****/
			
			ph = new Paragraph(un.get(d.getUnit_id()), fontS);
			cell = new PdfPCell(ph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			/*** end edit code H&B*****/
			
			ph = new Paragraph(toArabic("" + d.getTrans_out_num()), fontS);
			cell = new PdfPCell(ph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			ph = new Paragraph(toArabic("" + datet), fontS);
			cell = new PdfPCell(ph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
				
			ph = new Paragraph(toArabic(d.getTitle()), fontS);
			cell = new PdfPCell(ph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			if(type>=2){
			for (int element : d.getTashira()) {
				if(element==7){
					continue;
				}
				ph = new Paragraph(ts.get(element) + "", green);
				ph.add(Chunk.NEWLINE);
				ph.setAlignment(Element.ALIGN_CENTER);
				addEmptyLine(ph, 1);
				preface.add(ph);
			}
			cell = new PdfPCell(preface);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setMinimumHeight(50f);
			table.addCell(cell);
			}
			if(type==1||type==3){
			for (int element : d.getTashira_qaud()) {
				if(element==7){
					continue;
				}
				ph = new Paragraph(ts.get(element) + "", red);
				ph.add(Chunk.NEWLINE);
				ph.setAlignment(Element.ALIGN_CENTER);
				addEmptyLine(ph, 1);
				preface4.add(ph);
			}
			cell = new PdfPCell(preface4);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setMinimumHeight(50f);
			table.addCell(cell);
			}
			//preface.clear();
			ArrayList<istagel>s=d.getEstagel();
			for (Resp_unit element : d.getResp_unit()){
					ph = new Paragraph(un.get(element.getUnit()) + "", fontS);
					ph.add(Chunk.NEWLINE);
					ph.setAlignment(Element.ALIGN_CENTER);
					addEmptyLine(ph, 1);
					ist_cell = new PdfPCell(ph);
					ist_cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					ist_table.addCell(ist_cell);
					int x=0;
					 preface2 = new Paragraph();
					for (istagel istagel : s) {
						if(istagel.getUnit()==element.getUnit()){
							String[] datex = istagel.getDate().toString().split("-");
							String datez = datex[2] + "-" + datex[1] + "-" + datex[0];
							ph = new Paragraph(toArabic("رقم"+ ++x+": "+ datez), fontS);
							ph.setAlignment(Element.ALIGN_CENTER);
							ph.add(Chunk.NEWLINE);
							preface2.add(ph);
						}
					}
					ist_cell = new PdfPCell(preface2);
					ist_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					ist_table.addCell(ist_cell);
					int status=element.getStatus();
					
					if(status==2)
					{
						ph = new Paragraph("تم الرد", new Font(bf,12,Font.BOLD,BaseColor.BLUE));
						ist_cell = new PdfPCell(ph);
						ist_cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						ist_table.addCell(ist_cell);
					}else
					{
						ph = new Paragraph("لم يتم الرد", new Font(bf,12,Font.BOLD,BaseColor.RED));
						ist_cell = new PdfPCell(ph);
						ist_cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						ist_table.addCell(ist_cell);
					}
				}
			
			table.addCell(ist_table);
			index++;
		}

		return table;

	}

	public String toArabic(String en) {
		en = en.replaceAll("0", "٠");
		en = en.replaceAll("1", "١");
		en = en.replaceAll("2", "٢");
		en = en.replaceAll("3", "٣");
		en = en.replaceAll("4", "٤");
		en = en.replaceAll("5", "٥");
		en = en.replaceAll("6", "٦");
		en = en.replaceAll("7", "٧");
		en = en.replaceAll("8", "٨");
		en = en.replaceAll("9", "٩");
		return en;
	}


	public void addTitlePage(Document document, String title[]) throws DocumentException, IOException {
		// TODO Auto-generated method stub
		bf = BaseFont.createFont(Environment.LAPPATH + "/trado.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		font = new Font(bf, 15);
		fontBlod = new Font(bf, 15, Font.BOLD);
		Paragraph preface = new Paragraph();
		// We add one empty line
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
