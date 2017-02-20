package navymail.rest.Travels;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import navymail.API.TravelAPI.Rank_degreeAPI;
import navymail.Helpers.Master.MyResponse;
import navymail.Queries.Insert;
import navymail.models.Travels.Rank_degree;
import navymail.rest.Travels.DBQueries.SeLectFromDB;
import navymail.util.Jason;

@Path("/rank_degrees")
public class Rank_degrees implements Rank_degreeAPI {

	public Response showRank() throws Exception {

		String query = "select * from rank_degree";
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Rank_degree> res = q.getRank_degree();
		q.closeConnection();
		String output = Jason.toJson(res);
		return new MyResponse(output).success();
	}

	public HashMap<Integer, Integer> getRanks() throws SQLException{
		String query = "select * from rank_degree order by group_id,rank_type";
		SeLectFromDB q = new SeLectFromDB();
		q.openConnection(query);
		ArrayList<Rank_degree> res = q.getRank_degree();
		q.closeConnection();
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
		for (int i = 0; i < res.size(); i++) {
			hm.put(res.get(i).getId(), res.get(i).getRank_type());
		}
		return hm;
	}
	public Response insert_rank(String rank_name, int rank_type) throws Exception {
		String query = String.format(
				"Insert into rank_degree (rank_name, rank_type) values (\'%s\',%d);",
				rank_name, rank_type);
		new Insert(query);
		return new MyResponse().success();
	}
	
//	public Response search(int id) throws Exception {
//		String query = "select * from rank_degree where id =" + id;
//		SeLectFromDB q = new SeLectFromDB();
//		q.openConnection(query);
//		ArrayList<Rank_degree> res = q.getRank_degree();
//		q.closeConnection();
//		String output = Jason.toJson(res);
////	System.out.println(output);
//		return new MyResponse(output).success();
//		
//	}
//	
}
