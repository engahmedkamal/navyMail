package navymail.API.TabletAPI;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("tablet")
public interface TabletAPI {
		//سحب
		@GET
		@Path("arkan")
		public Response tablet_arkan() throws SQLException, Exception;
		@GET
		@Path("kaed")
		public Response tablet_kaed() throws Exception;
		
		//الموقف الحالي
		@GET
		@Path("status")
		public Response current_status() throws Exception;
		
		
}

