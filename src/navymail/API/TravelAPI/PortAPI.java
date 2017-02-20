package navymail.API.TravelAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("ports")
public interface PortAPI {
	
	@GET
	@Path("show")
	public Response showPorts() throws Exception;

	@GET
	@Path("insert")
	public Response insert_port(@QueryParam("port_name") String port_name)
	throws Exception;
}
