package navymail.API.SafarAPI;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("officers")
public interface OfficersAPI {

	@GET
	@Path("show")
	public Response showOfficers() throws Exception;


}
