package navymail.rest.Travels;


import java.util.ArrayList;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import navymail.API.TravelAPI.ShipAPI;
import navymail.Helpers.Master.MyResponse;
import navymail.Queries.Insert;
import navymail.models.Travels.Ship;
import navymail.rest.Travels.DBQueries.SeLectFromDB;
import navymail.util.Jason;

@Path("/ships")
public class Ships implements ShipAPI {

	public Response showShips() throws Exception {
	
		String query = "select * from ships";
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Ship> res = q.getShips();
		q.closeConnection();
		String output = Jason.toJson(res);
//	System.out.println(output);
		return new MyResponse(output).success();
	}

	public Response insert_ship(String ship_name, String ship_type, int unit_id)
			throws Exception {
		String query = String.format(
				"Insert into ships (name, ship_type, unit_id) values (\'%s\', \'%s\',%d);",
				ship_name, ship_type, unit_id);
		new Insert(query);
		return new MyResponse().success();
	}
	
	
}
