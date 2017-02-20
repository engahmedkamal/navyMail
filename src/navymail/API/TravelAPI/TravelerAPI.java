package navymail.API.TravelAPI;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;


@Path("travelers")
public interface TravelerAPI {

	@GET
	@Path("show")
	public Response showTravelers() throws Exception;

	@GET
	@Path("insert")
	public Response insert_traveler(
			@QueryParam("military_num") long military_num,
			@QueryParam("rank_id") int rank_id,
			@QueryParam("name") String name,
			@QueryParam("seniority_num") String seniority_num,
			@QueryParam("unit_id") int unit_id,
			@QueryParam("job_desc") String job_desc,
			@QueryParam("notes") String notes,
			@QueryParam("security_status") int security_status
		) throws Exception;
	
	@GET
	@Path("update")
	public Response update_traveler(
			@QueryParam("military_num") long military_num,
			@QueryParam("rank_id") int rank_id,
			@QueryParam("name") String name,
			@QueryParam("seniority_num") String seniority_num,
			@QueryParam("unit_id") int unit_id,
			@QueryParam("job_desc") String job_desc,
			@QueryParam("notes") String notes,
			@QueryParam("security_status") int security_status
		) throws Exception;
	
	
	@GET
	@Path("search")
	public Response search_traveler(
			@QueryParam("military_num") long military_num
		) throws Exception;
	
	

	
	@GET
	@Path("allMissions")
	public Response getAllTravelerMissions(
			@QueryParam("military_num") long military_num
	) throws Exception;
	
}
