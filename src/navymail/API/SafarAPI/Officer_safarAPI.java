package navymail.API.SafarAPI;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("officer_safar")
public interface Officer_safarAPI {

	@GET
	@Path("show")
	public Response showOfficer_safar() throws Exception;
	
}