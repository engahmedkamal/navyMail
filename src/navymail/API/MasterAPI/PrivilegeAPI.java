package navymail.API.MasterAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
/**
 * 
 * @author zankalony
 *
 */
@Path("privileges")
public interface PrivilegeAPI {

	@GET
	@Path("show")
	public Response showPrivileges() throws Exception;

}
