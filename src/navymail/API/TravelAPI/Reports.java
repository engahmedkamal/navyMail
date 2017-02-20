package navymail.API.TravelAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("Report")
public interface Reports {
	@GET
	@Path("Print")
	public Response print_Missions(@QueryParam("mission_id") int mission_id, @QueryParam("type") int type,
			@QueryParam("ship_id") int ship_id, @QueryParam("mission_type") int mission_type) throws Exception;
}
