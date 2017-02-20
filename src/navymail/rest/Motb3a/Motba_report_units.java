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
import java.util.Iterator;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
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
import navymail.API.Motb3aAPI.Units_Report;
import navymail.Helpers.Master.MyResponse;
import navymail.Queries.SeLect;
import navymail.models.Master.Units;
import navymail.models.Motb3a.istagel;
import navymail.models.Motb3a.motb3a_Tashirat_name;
import navymail.models.Wared.WaredDocument;
import navymail.util.Environment;
/**
 * 
 * @author Kamal
 */
@Path("/units_report")
public class Motba_report_units implements Units_Report {
	private static String FILE_MOTB3A = Environment.LAPPATH + "/units.pdf";
	private static BaseFont bf;
	private static Font font;
	private static Font fontS;
	private static Font fonte;
	private static Font fontBlod;
	int index;
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	Document document = new Document(PageSize.A4.rotate());
	
	@Override
	public Response print_motb3a(String date_from, String date_to,int unit_id) throws Exception {
		PdfWriter.getInstance(document, new FileOutputStream(FILE_MOTB3A));
		document.open();
		Image image1 = Image.getInstance(Environment.LAPPATH + "/navy.png");
		image1.scaleAbsolute(100, 110);
		image1.setAbsolutePosition(20f, 480f);
		document.add(image1);
		HashMap<Integer, String> hm=getUnits();
		String arr[] = { "بيان بالموضوعات التى لم يتم الرد عليها","من جهة "+hm.get(unit_id),
				"----------------" };
		
		addTitlePage(document, arr);
		document.add(createMotabaTable(getData(unit_id,date_from, date_to), unit_id));
		document.close();
		return  new MyResponse().success();
	}
	public PdfPTable createMotabaTable(ArrayList<WaredDocument> doc , int unit) throws DocumentException, Exception, SQLException {
		// TODO Auto-generated method stub
		
		int index = 1;
		Collections.sort(doc);
		HashMap<Integer, String> un = getUnits();
		HashMap<Integer, String> ts = getTashira();
		PdfPTable table = new PdfPTable(10);
		PdfPCell cell; 
		table.setWidthPercentage(100);
		table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		float[] cw = { 25f, 25f,25f, 35f, 15f,9f,20f, 15f, 15f, 8f }; 
		//float[] cw = { 28f, 24f, 25f, 50f, 25f, 20f, 20f, 8f };
		// float[] cw = { 12f, 20f , 20f, 25f, 50f,25f,20f,28f};
		table.setWidths(cw);
		bf = BaseFont.createFont(Environment.LAPPATH + "/trado.ttf", BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
		font = new Font(bf, 12);
		fontS = new Font(bf, 12);
		fonte = new Font(bf, 10);
		
		Font green=new Font(bf,12,Font.BOLD);
		green.setColor(0,180,50);
		Font red=new Font(bf,12,Font.BOLD);
		red.setColor(254, 0, 0);
//		PdfPTable ist_table_2 = new PdfPTable(3);
//		PdfPCell ist_cell;
//		ist_table_2.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
//		float[] cw_2 = { 10f, 20f, 20f };
//		ist_table_2.setWidths(cw_2);
		// LanguageProcessor al = new ArabicLigaturizer();
		Paragraph ph;
		String arry[] = { "الإستعجالات","تاشيرة السيد القائد","تاشيرة السيد رئيس الأركان", "الموضوع", "تاريخ الصادر المحول","رقم الصادر المحول","الجهة", "تاريخ الوارد",
				"رقم الوارد", "م" };
		
		for (int i = arry.length-1 ; i >= 0; i--) {
			ph = new Paragraph(arry[i], font);
			cell = new PdfPCell(ph);
			cell.setMinimumHeight(25f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			table.setHeaderRows(1);
		}
		//table.addCell(ist_table_2);
		for (int i = 0; i < doc.size(); i++) {
			
			WaredDocument d = doc.get(i);
			Paragraph preface = new Paragraph();
			Paragraph preface2  = new Paragraph();
			Paragraph preface3 = new Paragraph();
//			Paragraph preface3 = new Paragraph();
			String[] date0 = d.getCreated_at().toString().split("-");
			String date = date0[2] + "-" + date0[1] + "-" + date0[0];
			String[] dates;
			String datet;
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
			ph = new Paragraph(toArabic("" + d.getDoc_number()), fontS);
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
			for (int element : d.getTashira()) {
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
			for (int element : d.getTashira_qaud()) {
				if(element==7){
					continue;
				}
				ph = new Paragraph(ts.get(element) + "", red);
				ph.add(Chunk.NEWLINE);
				ph.setAlignment(Element.ALIGN_CENTER);
				addEmptyLine(ph, 1);
				preface3.add(ph);
			}
			cell = new PdfPCell(preface3);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setMinimumHeight(50f);
			table.addCell(cell);
			ArrayList<istagel>s=d.getEstagel();
			int x=0;
					for (istagel istagel : s) {
						if(istagel.getUnit()==unit){
							String[] datex = istagel.getDate().toString().split("-");
							String datez = datex[2] + "-" + datex[1] + "-" + datex[0];
							ph = new Paragraph(toArabic("رقم"+ ++x+": "+ datez), fontS);
							ph.setAlignment(Element.ALIGN_CENTER);
							ph.add(Chunk.NEWLINE);
							preface2.add(ph);
						}
					}
					cell = new PdfPCell(preface2);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setMinimumHeight(50f);
					table.addCell(cell);

			index++;
		}

		return table;

	}
	public ArrayList<WaredDocument> getData( int unit_id ,String date_from, String date_to) throws Exception {
		String query = "select id from Resp_unit where status =1 and unit="+unit_id;
		System.out.println(query);
		SeLect s = new SeLect();
		s.openConnection(query);
		ArrayList<Integer> arry = new ArrayList<>();
		arry = s.getResp_unit_doc_id();
		s.closeConnection();
		ArrayList<WaredDocument> W_arry = new ArrayList<>();
		for (Iterator iterator = arry.iterator(); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();
			System.err.println(""+integer);
			if(search_by_id(integer,date_from,date_to)!=null)
			W_arry.add(search_by_id(integer, date_from,date_to));			
		}
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
	public static WaredDocument search_by_id(int id,String date_from, String date_to) throws Exception {
		String query = "select * from documents where id = " + id+" and doc_need_replay=2";
		String dateCondition1 = dateCondition("created_at", date_from, date_to);
		if(dateCondition1!=null)
		query += " and "+dateCondition1;
		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<WaredDocument> res = q.getDocuments();
		q.closeConnection();
		if(res.size()>0)
		return res.get(0);
		return null;
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
