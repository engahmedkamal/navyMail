package navymail.rest.Travels;

import java.util.ArrayList;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import navymail.API.TravelAPI.PortAPI;
import navymail.Helpers.Master.MyResponse;
import navymail.Queries.Insert;
import navymail.models.Travels.Port;
import navymail.rest.Travels.DBQueries.SeLectFromDB;
import navymail.util.Jason;

@Path("/ports")
public class Ports implements PortAPI {

	@Override
	public Response showPorts() throws Exception {
		
		String query = "select * from ports";
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Port> res = q.getPorts();
		q.closeConnection();
		String output = Jason.toJson(res);
//	System.out.println(output);
		return new MyResponse(output).success();
	}
	
	public Response insert_port(String port_name)
			throws Exception {
		String query = String.format(
				"Insert into ports (name) values (\'%s\');",
				port_name);
		new Insert(query);
		return new MyResponse().success();
	}

}
