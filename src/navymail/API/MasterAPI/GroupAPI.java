package navymail.API.MasterAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
/**
 * 
 * @author zankalony
 *
 */
@Path("groups")
public interface GroupAPI {
	
	@GET
	@Path("show")
	public Response showGroups() throws Exception;

}
