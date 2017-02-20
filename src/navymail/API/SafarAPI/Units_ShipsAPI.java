package navymail.API.SafarAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("units_ships")
public interface Units_ShipsAPI {
	
	@GET
	@Path("show")
	public Response showUnits_Ships() throws Exception;

}
