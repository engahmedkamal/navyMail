package navymail.API.MasterAPI;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * 
 * @author zankalony
 *
 */
@Path("users")
public interface UserAPI {
	
	@GET
	@Path("login")
	public Response login(@DefaultValue("root") @QueryParam("userid") String userid,
			@DefaultValue("root") @QueryParam("password") String password) throws Exception;
	
	/**
	 * 
	 * @param group_id  show all users who have group_id equals gid
	 * @param user_id  ..
	 * @param username ..
	 * @return Json Object 
	 * @throws Exception
	 */
	@GET
	@Path ("show")
	public Response show(
			@DefaultValue("-1") @QueryParam("group_id") int group_id,
			@DefaultValue("-1") @QueryParam("user_id") int user_id,
			@DefaultValue("root") @QueryParam("username") String username)
				throws Exception;
	
	
}
