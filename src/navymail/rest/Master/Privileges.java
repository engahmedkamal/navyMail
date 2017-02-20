package navymail.rest.Master;

import java.util.ArrayList;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import navymail.API.MasterAPI.PrivilegeAPI;
import navymail.Helpers.Master.MyResponse;
import navymail.Queries.SeLect;
import navymail.models.Master.Privilege;
import navymail.util.Jason;


@Path("/privileges")
public class Privileges implements PrivilegeAPI{

	public Response showPrivileges()  {
		try{
		String query = "select * from privileges";

		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<Privilege> res = q.getPrivilege();
		q.closeConnection();
		String output = Jason.toJson(res);

		return new MyResponse(output).success();
		}catch(Exception e){
			e.printStackTrace();
			return new MyResponse().failed();
		}

	}
}
