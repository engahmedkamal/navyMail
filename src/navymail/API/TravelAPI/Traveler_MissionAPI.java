package navymail.API.TravelAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("travelers_missions")
public interface Traveler_MissionAPI {

	@GET
	@Path("search")
	public Response showTravelers_missions(@QueryParam("mission_id") int mission_id) throws Exception;

	@GET
	@Path("search/Students")
	public Response showStudent(@QueryParam("mission_id") int mission_id, @QueryParam("ship_id") int ship_id)
			throws Exception;

	@GET
	@Path("search/Assistants")
	public Response showSafZobat(@QueryParam("mission_id") int mission_id, @QueryParam("ship_id") int ship_id)
			throws Exception;

	@GET
	@Path("search/Officers")
	public Response showofficers(@QueryParam("mission_id") int mission_id, @QueryParam("ship_id") int ship_id)
			throws Exception;

	@GET
	@Path("insert")
	public Response insert_traveler_mission(@QueryParam("traveler_id") long traveler_id,
			@QueryParam("mission_id") int mission_id, @QueryParam("traveler_ship") int traveler_ship,
			@QueryParam("traveler_position") int traveler_position,@QueryParam("traveler_note") String notes) throws Exception;

	@GET
	@Path("remove")
	public Response remove_traveler_mission(@QueryParam("traveler_id") long traveler_id,
			@QueryParam("mission_id") int mission_id) throws Exception;

}