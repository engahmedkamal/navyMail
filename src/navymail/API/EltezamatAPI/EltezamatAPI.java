package navymail.API.EltezamatAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("eltezamat")
public interface EltezamatAPI {

	@GET
	@Path("view/ka2ed")
	public Response get_ka2ed() throws Exception;

	@GET
	@Path("remove/ka2ed")
	public Response remove_ka2ed()throws Exception;

	@GET
	@Path("save/ka2ed")
	public Response save_ka2ed(@QueryParam("seq") int seq,
			@QueryParam("date") String date, @QueryParam("time") String time,
			@QueryParam("title") String title,
			@QueryParam("place") String place,
			@QueryParam("sader_num") int trans_out_num,
			@QueryParam("sader_date") String trans_out_date,
			@QueryParam("unit") String unit)

	throws Exception;

	@GET
	@Path("view/arkan")
	public Response get_arkan() throws Exception;

	
	@GET
	@Path("remove/arkan")
	public Response remove_arkan()throws Exception;

	@GET
	@Path("save/arkan")
	public Response save_arkan(@QueryParam("seq") int seq,
			@QueryParam("date") String date, @QueryParam("time") String time,
			@QueryParam("title") String title,
			@QueryParam("place") String place,
			@QueryParam("sader_num") int trans_out_num,
			@QueryParam("sader_date") String trans_out_date,
			@QueryParam("unit") String unit)
	throws Exception;

	
}
