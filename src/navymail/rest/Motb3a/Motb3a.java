package navymail.rest.Motb3a;

import java.util.ArrayList;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import navymail.API.Motb3aAPI.Motb3aDocAPI;
import navymail.Helpers.Master.MyResponse;
import navymail.Queries.SeLect;
import navymail.models.Motb3a.motb3a_Tashirat_name;
import navymail.util.Jason;

/**
 * @author Kamal
 * 
 */

@Path("/motba")
public class Motb3a implements Motb3aDocAPI{
	@Override
	public Response ShowTashira() throws Exception {
		String query = "select * from motb3a_Tashirat_name";
//		System.out.println(query);
		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<motb3a_Tashirat_name> res = q.getmotb3a_tashirat_name();
		q.closeConnection();
		String output = Jason.toJson(res);
//		System.out.println(output);
		return new MyResponse(output).success();
	}

}
