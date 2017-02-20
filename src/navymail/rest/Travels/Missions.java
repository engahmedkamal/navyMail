package navymail.rest.Travels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import navymail.API.TravelAPI.MissionAPI;
import navymail.Helpers.Master.MyResponse;
import navymail.Helpers.Travels.Utils;
import navymail.Queries.Insert;
import navymail.models.Travels.Mission;
import navymail.rest.Travels.DBQueries.SeLectFromDB;
import navymail.util.Jason;

@Path("/missions")
public class Missions implements MissionAPI {

	public Response showMission() throws Exception {

		String query = "SELECT * FROM missions";
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Mission> res = q.getMissions();
		q.closeConnection();
		String output = Jason.toJson(res);
		// System.out.println(output);
		return new MyResponse(output).success();
	}

	// to change date format
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public Response insert_mission(String name, int type_id, String activity_name, String date_from, String date_to,
			int document_id, String confirmation_no, String confirmation_date, long commander_id,
			long second_commander_id, String ships, String ports, int direction) throws Exception {
		Date dateTo = Utils.stringToDate(date_to);
		Date dateFrom = Utils.stringToDate(date_from);
		Date confirmationDate = Utils.stringToDate(confirmation_date);
		date_to = format.format(dateTo);
		date_from = format.format(dateFrom);
		confirmation_date = format.format(confirmationDate);
		String query = new String();
		if (type_id == 2) {
			Travelers t = new Travelers();
			if (!t.istraveler(0)) {
				t.insert_traveler(0, 1, "رئيس أركان التشكيل ( رحلة الطلبة )", "", 1, "", "", 1);
			}
			second_commander_id = 0;
		}
		query = String.format(
				"Insert into missions (name, type_id, activity_name, date_from, date_to, "
						+ "document_id, confirmation_no, confirmation_date, commander_id, second_commander_id, "
						+ "ships, ports, direction) "
						+ "values (\'%s\', %d, \'%s\', \'%s\', \'%s\', %d, \'%s\', \'%s\', %d, %d, \'%s\', \'%s\', %d);",
				name, type_id, activity_name, date_from, date_to, document_id, confirmation_no, confirmation_date,
				commander_id, second_commander_id, ships, ports, direction);

		Insert in = new Insert(query);
		int id = in.getid();
		String output = Jason.toJson(id);
		return new MyResponse(output).success();
	}

	public Response search_mission(int type_id, String date_from, String date_to) throws Exception {
		Date dateTo = Utils.stringToDate(date_to);
		Date dateFrom = Utils.stringToDate(date_from);
		String whereSt = Utils.dateCondition("date_from", dateFrom, dateTo);
		String query = "select * from missions ";
		if (whereSt != null) {
			query += " where " + whereSt;
			if (type_id != 0)
				query += " and type_id = " + type_id;
		} else if (type_id != 0)
			query += " where type_id = " + type_id;

		SeLectFromDB q = new SeLectFromDB();
		System.out.println(query);
		q.openConnection(query);
		ArrayList<Mission> res = q.getMissions();
		q.closeConnection();
		String output = Jason.toJson(res);
		return new MyResponse(output).success();
	}

	public Response search_mission(int type_id) throws Exception {
		String query = "SELECT * FROM missions where id = " + type_id;
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Mission> res = q.getMissions();
		q.closeConnection();
		String output = Jason.toJson(res);
		// System.out.println(output);
		return new MyResponse(output).success();
	}

	public Mission get_mission(int type_id) throws Exception {
		String query = "SELECT * FROM missions where id = " + type_id;
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Mission> res = q.getMissions();
		q.closeConnection();
		if (res.size() == 0)
			return null;
		return res.get(0);
	}

}
