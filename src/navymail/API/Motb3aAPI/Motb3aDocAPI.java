package navymail.API.Motb3aAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("motb3a")
public interface Motb3aDocAPI {

	@GET
	@Path("tashira")
	public Response ShowTashira()
			throws Exception;
}
