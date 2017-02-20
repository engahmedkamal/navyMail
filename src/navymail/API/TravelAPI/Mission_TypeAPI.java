package navymail.API.TravelAPI;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("missions_types")
public interface Mission_TypeAPI {
	@GET
	@Path("show")
	public Response showMissions_Types() throws Exception;


}