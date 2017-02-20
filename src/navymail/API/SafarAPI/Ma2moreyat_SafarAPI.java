package navymail.API.SafarAPI;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("ma2moreyat_safar")
public interface Ma2moreyat_SafarAPI {
	@GET
	@Path("show")
	public Response showMa2moreyat_Safar() throws Exception;

}