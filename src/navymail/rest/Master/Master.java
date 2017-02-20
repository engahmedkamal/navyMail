package navymail.rest.Master;

import java.util.ArrayList;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import navymail.API.MasterAPI.MasterAPI;
import navymail.Helpers.Master.MyResponse;
import navymail.Helpers.Wared.ImageUtils;
import navymail.Queries.SeLect;
import navymail.models.Master.SaderUnitsName;
import navymail.models.Master.SavedFile;
import navymail.models.Master.Status;
import navymail.models.Master.Sub_Main;
import navymail.models.Master.Sub_br;
import navymail.models.Master.Target;
import navymail.models.Master.Tashira;
import navymail.models.Master.Units;
import navymail.util.Environment;
import navymail.util.Jason;

@Path("/master")
public class Master implements MasterAPI {

	public Response getUnits(){
		try{
		String query = "select * from units";
		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<Units> res = q.getUnits();
		q.closeConnection();
		String output = Jason.toJson(res);
		return new MyResponse(output).success();
		}catch(Exception e){
			e.printStackTrace();
			return new MyResponse().failed();
		}
	}

	public Response getTarget() {
		try{
		String query = "select * from targets";
		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<Target> res = q.getTarget();
		q.closeConnection();
		String output = Jason.toJson(res);

		return new MyResponse(output).success();
		}catch(Exception e){
			e.printStackTrace();
			return new MyResponse().failed();
		}
		
	}

	public Response getStatus() {
		try{
		String query = "select * from statuses";
		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<Status> res = q.getStatus();
		q.closeConnection();
		String output = Jason.toJson(res);
		return new MyResponse(output).success();
		}catch(Exception e){
			e.printStackTrace();
			return new MyResponse().failed();
		}
	}

	public Response getScanner(int folder_id)  {
		try{
		String scannerPath = Environment.TASWEER_PATH[folder_id - 1];
		ArrayList<String> list = ImageUtils.getListOfImages(scannerPath);
		String output = Jason.toJson(list);
		return new MyResponse(output).success();
		}catch(Exception e){
			e.printStackTrace();
			return new MyResponse().failed();
		}
	}

	public static final <T> void swap(T[] a, int i, int j) {
		T t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	public Response getSaved_files() {
		try{
		String query = "select * from saved_file";
		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<SavedFile> res = q.getSavedFiles();
		q.closeConnection();
		String output = Jason.toJson(res);
		return new MyResponse(output).success();
		}catch(Exception e){
			e.printStackTrace();
			return new MyResponse().failed();
		}

	}

	@Override
	public Response getTashira()  {
		try{
		String query = "select * from TASHIRA";
		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<Tashira> res = q.getTashira();
		q.closeConnection();
		String output = Jason.toJson(res);
		return new MyResponse(output).success();
		}catch(Exception e){
			e.printStackTrace();
			return new MyResponse().failed();
		}
	}

	@Override
	public Response getSub_main() {
		try{
		String query = "select * from SUB_MAIN_TBL";
		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<Sub_Main> res = q.getSub_Main();
		q.closeConnection();
		String output = Jason.toJson(res);
		return new MyResponse(output).success();
		}catch(Exception e){
			e.printStackTrace();
			return new MyResponse().failed();
		}
	}

	@Override
	public Response getSub_br() {
		try{
		String query = "select * from SUB_BR_TBL";
		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<Sub_br> res = q.getSub_br();
		q.closeConnection();
		String output = Jason.toJson(res);
		return new MyResponse(output).success();
		}catch(Exception e){
			e.printStackTrace();
			return new MyResponse().failed();
		}
	}

	@Override
	public Response getSaderUnits(int doc_num, String doc_date) {
		try{
		String query = "SELECT unit_id FROM sader_dest_unit where doc_number="+doc_num+" and doc_date like '"+doc_date+"%'";
		System.out.println(query);
		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<Integer> res = q.getUnits_Id();
		q.closeConnection();
		String output = Jason.toJson(res);
		return new MyResponse(output).success();
		}catch(Exception e){
			e.printStackTrace();
			return new MyResponse().failed();
		}
	}

	@Override
	public Response getSaderUnits() {
		try{
		String query = "select * from SaderUnitsName";
		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<SaderUnitsName> res = q.getSaderUnitsName();
		q.closeConnection();
		String output = Jason.toJson(res);
		return new MyResponse(output).success();
		}catch(Exception e){
			e.printStackTrace();
			return new MyResponse().failed();
		}
	}

}
