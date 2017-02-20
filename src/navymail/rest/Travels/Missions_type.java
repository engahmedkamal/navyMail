package navymail.rest.Travels;


import java.util.ArrayList;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import navymail.API.TravelAPI.Mission_TypeAPI;
import navymail.Helpers.Master.MyResponse;
import navymail.models.Travels.Mission_type;
import navymail.rest.Travels.DBQueries.SeLectFromDB;
import navymail.util.Jason;

@Path("/missions_types")
public class Missions_type implements Mission_TypeAPI {

	
	public Response showMissions_Types() throws Exception {
		String query = "select * from missions_types";
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Mission_type> res = q.getMissions_type();
		q.closeConnection();
		String output = Jason.toJson(res);
//	System.out.println(output);
		return new MyResponse(output).success();
	}



}
