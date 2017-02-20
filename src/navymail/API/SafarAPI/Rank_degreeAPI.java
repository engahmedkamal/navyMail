package navymail.API.SafarAPI;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("rank_degrees")
public interface Rank_degreeAPI {

	@GET
	@Path("show")
	public Response showRank() throws Exception;

}
