package navymail.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;

import navymail.util.ConnectionHandler;

public class Update {

	boolean operation_success;

	public Update(String query) {
		Connection con;
		con = ConnectionHandler.getInstance().getDBConnection(
				ConnectionHandler.NAVYMAIL);
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.executeUpdate(query);
			con.close();
			st.close();
			operation_success = true;
		} catch (Exception e) {
			e.printStackTrace();
			operation_success = false;
			System.out.println(query);
			System.err.println("INVALID Update QUERY OR SOMETHING WRONGG");
		}
	}
}
