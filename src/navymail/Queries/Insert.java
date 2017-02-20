package navymail.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import navymail.util.ConnectionHandler;

public class Insert {

	public boolean operation_success;
	PreparedStatement st;
	int lastrow = 0;
	public Insert(String query) {
		Connection con;
		con = ConnectionHandler.getInstance().getDBConnection(
				ConnectionHandler.NAVYMAIL);
		try {
			st = con.prepareStatement(query);
			System.out.println(query);
			st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = st.getGeneratedKeys();
			if (rs.next())
				lastrow= rs.getInt(1) ; 
			con.close();
			st.close();
			operation_success = true;
		} catch (Exception e) {
			operation_success = false;
			System.err.println("INVALID INSERT QUERY OR SOMETHING WRONGG");
		}
	}

	public int getid() throws SQLException {
		return lastrow;
	}

}
