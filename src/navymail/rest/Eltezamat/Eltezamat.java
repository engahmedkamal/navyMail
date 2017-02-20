package navymail.rest.Eltezamat;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import navymail.API.EltezamatAPI.EltezamatAPI;
import navymail.Helpers.Master.MyResponse;
import navymail.Queries.Insert;
import navymail.Queries.SeLect;
import navymail.Queries.Update;
import navymail.models.Eltezamat.Eltezam;
import navymail.util.Jason;

@Path("eltezamat")
public class Eltezamat implements EltezamatAPI {

	public Response get_ka2ed() throws Exception {
		String query = "select * from eltezamat where type = 1  ORDER BY seq";
		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<Eltezam> res = q.getEltezamat();
		q.closeConnection();
		String output = Jason.toJson(res);
		return new MyResponse(output).success();
	}

	public Response get_arkan() throws Exception {
		String query = "select * from eltezamat where type = 2";
		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<Eltezam> res = q.getEltezamat();
		q.closeConnection();
		String output = Jason.toJson(res);
		return new MyResponse(output).success();
	}

	public Response remove_ka2ed() throws Exception {
		String query = "delete from eltezamat where type = 1";
		new Update(query);
		return new MyResponse().success();
	}

	public Response save_ka2ed(int seq, String date, String time, String title,
			String place, int trans_out_num, String trans_out_date, String unit)
			throws Exception {
		String query = String.format(
				"Insert into eltezamat values (%d,%d,\'%s\',\'%s\',\'%s\',\'%s\',%d,\'%s\',\'%s\');",
				1, seq, date, time, title, place, trans_out_num,
				trans_out_date, unit);
		new Insert(query);
		return new MyResponse().success();
	}

	public Response remove_arkan() throws Exception {
		String query = "delete from eltezamat where type = 2";
		new Update(query);
		return new MyResponse().success();
	}

	public Response save_arkan(int seq, String date, String time, String title,
			String place, int trans_out_num, String trans_out_date, String unit)
			throws Exception {
		String query = String.format(
				"Insert into eltezamat values (%d,%d,\'%s\',\'%s\',\'%s\',\'%s\',%d,\'%s\',\'%s\');",
				2, seq, date, time, title, place, trans_out_num,
				trans_out_date, unit);
		new Insert(query);
		return new MyResponse().success();
	}

}
