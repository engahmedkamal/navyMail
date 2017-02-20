package navymail.API.TravelAPI;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("rank_degrees")
public interface Rank_degreeAPI {

	@GET
	@Path("show")
	public Response showRank() throws Exception;

	@GET
	@Path("insert")
	public Response insert_rank(@QueryParam("rank_name") String rank_name,
			@QueryParam("rank_type") int rank_type)
	throws Exception;
	
//	@GET
//	@Path("search")
//	public Response search(@QueryParam("id")int id) throws Exception;
}