package navymail.API.TravelAPI;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("internal_units")
public interface Internal_UnitsAPI {

	@GET
	@Path("show")
	public Response showInternal_Units() throws Exception;
	
//	@GET
//	@Path("search")
//	public Response searchInternalUnits(@QueryParam("id") int id) throws Exception;
//	
}
