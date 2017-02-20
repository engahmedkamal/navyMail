package navymail.rest.Travels;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import navymail.API.TravelAPI.Traveler_MissionAPI;
import navymail.Helpers.Master.MyResponse;
import navymail.Queries.Insert;
import navymail.Queries.Update;
import navymail.models.Travels.Traveler;
import navymail.models.Travels.Traveler_mission;
import navymail.rest.Travels.DBQueries.SeLectFromDB;
import navymail.util.Jason;

@Path("/travelers_missions")
public class Travelers_missions implements Traveler_MissionAPI {

	public Response showTravelers_missions(int id) throws Exception {
		String query = "select * from travelers_missions where mission_id = " + id;
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Traveler_mission> res = q.getTravelers_missions();
		q.closeConnection();
		Travelers obj = new Travelers();
		Traveler[] t = new Traveler[res.size()];
		for (int i = 0; i < t.length; i++) {
			t[i] = obj.search_by_military(res.get(i).getTraveler_id());
			t[i].setPosition(res.get(i).getPostion());

		}
		String output = Jason.toJson(t);
		return new MyResponse(output).success();
	}

	public Response showTravelers_missions_byship(int id, int ship_id, int postion) throws Exception {
		String query = "select * from travelers_missions where mission_id = " + id + " and ship_id= " + ship_id
				+ " and postion=" + postion;
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Traveler_mission> res = q.getTravelers_missions();
		q.closeConnection();
		Travelers obj = new Travelers();
		Traveler[] t = new Traveler[res.size()];
		for (int i = 0; i < t.length; i++) {
			t[i] = obj.search_by_military(res.get(i).getTraveler_id());
			t[i].setPosition(res.get(i).getPostion());
		}
		String output = Jason.toJson(t);
		return new MyResponse(output).success();
	}

	public Response getData(int mission_id, int type, int ship) throws Exception {
		String query = "select * from travelers_missions where mission_id = " + mission_id + " and ship_id = " + ship;
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		System.err.println(query);
		ArrayList<Traveler_mission> res = q.getTravelers_missions();
		q.closeConnection();
		Rank_degrees myObj = new Rank_degrees();
		HashMap<Integer, Integer> ranks = myObj.getRanks();

		Travelers obj = new Travelers();
		ArrayList<Traveler> t = new ArrayList<Traveler>();
		for (int i = 0; i < res.size(); i++) {
			Traveler curr = obj.search_by_military(res.get(i).getTraveler_id());
			curr.setPosition(res.get(i).getPostion());
			curr.notes_mission=res.get(i).getNotes();
			if (ranks.get(curr.getRank_id()) == type)
				t.add(curr);
		}
		String output = Jason.toJson(t);
		return new MyResponse(output).success();
	}

	public Response showStudent(int mission_id, int ship) throws Exception {
		return getData(mission_id, 3, ship);
	}

	public Response showSafZobat(int mission_id, int ship) throws Exception {
		return getData(mission_id, 2, ship);
	}

	public Response showofficers(int mission_id, int ship) throws Exception {
		return getData(mission_id, 1, ship);
	}

	public int[] showTraveler_missions(long m_num) throws Exception {
		String query = "select * from travelers_missions where traveler_id = " + m_num;
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Traveler_mission> res = q.getTravelers_missions();
		q.closeConnection();
		int[] missions_id = new int[res.size()];
		for (int i = 0; i < missions_id.length; i++) {
			missions_id[i] = res.get(i).getMission_id();
		}
		return missions_id;
	}

	public Response insert_traveler_mission(long traveler_id, int mission_id, int ship, int position ,String notes) throws Exception {
		String query = String.format(
				"Insert into travelers_missions (traveler_id, mission_id, ship_id , position , notes) values (%d, %d, %d, %d,\'%s\');",
				traveler_id, mission_id, ship, position,notes);
		Insert e = new Insert(query);
		if (!e.operation_success)
			return new MyResponse().failed();
			System.out.println("Ay 7a5666666666666666ga666666666666");
			return new MyResponse().success();
	}

	public Response remove_traveler_mission(long traveler_id, int mission_id) throws Exception {
		String query = String.format("delete FROM travelers_missions where traveler_id=%d and mission_id=%d ;",
				traveler_id, mission_id);
		new Update(query);
		return new MyResponse().success();
	}

}
