package navymail.rest.Travels;

import java.util.ArrayList;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import navymail.API.TravelAPI.TravelerAPI;
import navymail.Helpers.Master.MyResponse;
import navymail.Queries.Insert;
import navymail.Queries.Update;
import navymail.models.Travels.Mission;
import navymail.models.Travels.Traveler;
import navymail.rest.Travels.DBQueries.SeLectFromDB;
import navymail.util.Jason;

@Path("/travelers")
public class Travelers implements TravelerAPI {

	public Response showTravelers() throws Exception {
		String query = "select * from travelers";
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Traveler> res = q.getTravelers();
		q.closeConnection();
		String output = Jason.toJson(res);
		// System.out.println(output);
		return new MyResponse(output).success();
	}

	public Response insert_traveler(long military_num, int rank_id, String name,
			String seniority_num, int unit_id, String job_desc, String notes,
			int security_status) throws Exception {

		if (istraveler(military_num))
			return new MyResponse().success();

		String query = String
				.format("Insert into travelers (military_num, rank_id, name, seniority_num, "
						+ "unit_id, job_description, notes, security_status) "
						+ "values (%d, %d, \'%s\', \'%s\', %d, \'%s\', \'%s\', %d);",
						military_num, rank_id, name, seniority_num, unit_id,
						job_desc, notes, security_status);
		new Insert(query);
		return new MyResponse().success();
	}

	// search by military_num
	public Response search_traveler(long military_num) throws Exception {
		String query = "select * from travelers where military_num = "
				+ military_num;
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Traveler> res = q.getTravelers();
		q.closeConnection();
		if (res.size() == 0)
			return new MyResponse().failed();
		String output = Jason.toJson(res);
		return new MyResponse(output).success();
	}

	public boolean istraveler(long military_num) throws Exception {
		String query = "select * from travelers where military_num = "
				+ military_num;
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Traveler> res = q.getTravelers();
		q.closeConnection();
		return (res.size() != 0);
	}

	public Traveler search_by_military(long military_num) throws Exception {
		String query = "select * from travelers where military_num = "
				+ military_num;
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Traveler> res = q.getTravelers();
		q.closeConnection();
		
		return res.get(0);
	}

	public Response getAllTravelerMissions(long military_num) throws Exception {
		Travelers_missions t = new Travelers_missions();
		int[] missions_ids = t.showTraveler_missions(military_num);
		Missions missions = new Missions();
		ArrayList<Mission> res = new ArrayList<Mission>();
		for (int i = 0; i < missions_ids.length; i++) {
			Mission m = missions.get_mission(missions_ids[i]);
			if (m != null)
				res.add(m);
		}
		String output = Jason.toJson(res);
		return new MyResponse(output).success();
	}

	@Override
	public Response update_traveler(long military_num, int rank_id, String name, String seniority_num, int unit_id,
			String job_desc, String notes, int security_status) throws Exception {
		String query = String
				.format("UPDATE travelers SET name = \"%s\", rank_id = %d , seniority_num =\"%s\" "
						+ ",job_description = \"%s\", unit_id = %d, security_status =  %d, notes = \"%s\""
						+"WHERE military_num = %d ;",
						name, rank_id,  seniority_num, job_desc,unit_id,  security_status,notes,military_num);
		new Update(query);
		return new MyResponse().success();
	}
}