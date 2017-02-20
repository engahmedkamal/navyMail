package navymail.API.TravelAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("missions")
public interface MissionAPI {
	@GET
	@Path("show")
	public Response showMission() throws Exception;

	@GET
	@Path("insert")
	public Response insert_mission(@QueryParam("name") String name,
			@QueryParam("type_id") int type_id,
			@QueryParam("activity_name") String activity_name,
			@QueryParam("date_from") String date_from,
			@QueryParam("date_to") String date_to,
			@QueryParam("document_id") int document_id,
			@QueryParam("confimation_no") String confirmation_no,
			@QueryParam("confimation_date") String confirmation_date,
			@QueryParam("commander_id") long commander_id,
			@QueryParam("second_commander_id") long second_commander_id,
			@QueryParam("ships") String ships,
			@QueryParam("ports") String ports,
			@QueryParam("direction") int direction) throws Exception;

	@GET
	@Path("search")
	public Response search_mission(@QueryParam("type_id") int type_id,
			@QueryParam("date_from") String date_from,
			@QueryParam("date_to") String date_to) throws Exception;



}
