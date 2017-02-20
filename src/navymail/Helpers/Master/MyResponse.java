package navymail.Helpers.Master;

import navymail.util.Environment;
/**
 * 
 * @author zankalony
 *
 */
public class MyResponse {
	String success = "{\"error\": 0}";
	String failed = "{\"error\": 1}";

	public MyResponse(String in) {
		success = in;
	}

	public MyResponse() {
		// TODO Auto-generated constructor stub
	}

	public javax.ws.rs.core.Response success() {
		return javax.ws.rs.core.Response.status(200).entity(success)
				.header(Environment.CONTENT, Environment.FORMAT).build();
	}

	public javax.ws.rs.core.Response failed() {
		return javax.ws.rs.core.Response.status(200).entity(failed)
				.header(Environment.CONTENT, Environment.FORMAT).build();
	}

}
