package navymail.rest.Master;

import java.util.ArrayList;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import navymail.API.MasterAPI.GroupAPI;
import navymail.Helpers.Master.MyResponse;
import navymail.Queries.SeLect;
import navymail.models.Master.Group;
import navymail.util.Jason;


@Path("/groups")
public class Groups implements GroupAPI{

	public Response showGroups()  {
		try{
		String query = "select * from groups";
		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<Group> res = q.getGroups();
		q.closeConnection();
		String output = Jason.toJson(res);

		return new MyResponse(output).success();
		}catch(Exception e){
			e.printStackTrace();
			return new MyResponse().failed();
		}

	}
}
