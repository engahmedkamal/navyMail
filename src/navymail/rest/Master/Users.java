package navymail.rest.Master;

import java.util.ArrayList;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import navymail.API.MasterAPI.UserAPI;
import navymail.Helpers.Master.MyResponse;
import navymail.Queries.SeLect;
import navymail.models.Master.User;
import navymail.util.Environment;
import navymail.util.Jason;
import navymail.util.log.Loggable;

@Path("/users")
public class Users extends Loggable implements UserAPI {

	String comment1 = "Admin request to look up users where group id = (%d) user id = (%d) and username = (%s)";
	String comment2 = "A query =(%s) passed to sql DB ";
	String comment3 = "return (%s) a jason object";

	public Response show(int group_id, int user_id, String username)
			throws Exception {

		logger.info(String.format(comment1, group_id, user_id, username));
		String query = parse_params(group_id, user_id, username);
		SeLect q = new SeLect();
		logger.info(String.format(comment2, query));
		q.openConnection(query);
		ArrayList<User> res = q.getUsers();
		q.closeConnection();
		String output = Jason.toJson(res);
		logger.info(String.format(comment3, output));

		return new MyResponse(output).success();
	}

	String comment4 = "user (%s) tried to enter the website with password (%s)";
	String comment5 = "can`t find user (%s)";
	String comment6 = "user (%s) entered wrong password";
	String comment7 = "user (%s) successfully enterd the website";

	@Override
	public Response login(String userid, String password) throws Exception {
		logger.info(String.format(comment4, userid, password));

		String query = "select * from users where id=\"" + userid
				+ "\"";

		SeLect q = new SeLect();
		q.openConnection(query);
		ArrayList<User> res = q.getUsers();
		q.closeConnection();

		if (res.size() == 0) {
			logger.info(String.format(comment5, userid));
			return new MyResponse().failed();
		}
		User user = res.get(0);
		if (!user.getPassword().equals(password)) {
			logger.info(String.format(comment6, userid));
			return new MyResponse().failed();
		}

		String output = Jason.toJson(res);
		logger.info(String.format(comment7, userid));
		return new MyResponse(output).success();
	}

	private String parse_params(int group_id, int user_id, String username) {
		String query = "select * from users ";
		if (group_id != -1)
			query += "where " + User.GROUP_ID_COL + " = " + group_id;
		else if (user_id != -1)
			query += "where " + User.ID_COL + " = " + user_id;
		else if (!username.equals("root"))
			query += "where " + User.USERNAME_COL + " = " + username;
		return query;
	}

}
