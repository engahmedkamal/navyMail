package navymail.rest.Travels;


import java.util.ArrayList;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import navymail.API.TravelAPI.Internal_UnitsAPI;
import navymail.Helpers.Master.MyResponse;
import navymail.rest.Travels.DBQueries.SeLectFromDB;
import navymail.util.Jason;

@Path("internal_units")
public class Internal_Units implements Internal_UnitsAPI{

	
	public Response showInternal_Units() throws Exception {
		String query = "select * from units where unit_type = 0";
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<navymail.models.Travels.Internal_Unit> res = q.getInternal_Units();
		q.closeConnection();
		String output = Jason.toJson(res);
		return new MyResponse(output).success();
	}

//	public Response searchInternalUnits(int id) throws Exception {
//		String query = "select * from units where unit_type = 0 and id =" + id;
//		SeLectFromDB q = new SeLectFromDB();
//		q.openConnection(query);
//		ArrayList<navymail.models.Travels.Internal_Unit> res = q.getInternal_Units();
//		q.closeConnection();
//		String output = Jason.toJson(res);
//// System.out.println(output);
//		return new MyResponse(output).success();
//	}
}
