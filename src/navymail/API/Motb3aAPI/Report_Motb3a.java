package navymail.API.Motb3aAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("motb3a_report")
public interface Report_Motb3a {
	@GET
	@Path("print")
	public Response print_motb3a(@QueryParam("date_from") String date_from
			,@QueryParam("date_to") String date_to
			,@QueryParam("unit") int unit_id
			,@QueryParam("type") int type)
			throws Exception;
}
