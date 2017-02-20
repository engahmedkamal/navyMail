package navymail.API.TravelAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("ships")
public interface ShipAPI {
	
	@GET
	@Path("show")
	public Response showShips() throws Exception;

	@GET
	@Path("insert")
	public Response insert_ship(@QueryParam("ship_name") String ship_name,
			@QueryParam("ship_type") String ship_type, 
			@QueryParam("unit_id") int unit_id)
	throws Exception;
}
