package navymail.API.MasterAPI;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
/**
 * 
 * @author zankalony
 *
 */
@Path("master")
public interface MasterAPI {

	@GET
	@Path("units")
	public Response getUnits() throws Exception;
	
	@GET
	@Path("sader_units")
	public Response getSaderUnits() throws Exception;
	@GET
	@Path("target")
	public Response getTarget() throws Exception;

	@GET
	@Path("status")
	public Response getStatus() throws Exception;
	
	@GET
	@Path("scanner")
	public Response getScanner(@QueryParam("folder_id") int folder_id) throws Exception;
	
	@GET
	@Path("savedfiles")
	public Response getSaved_files() throws Exception;
	
	@GET
	@Path("tashira")
	public Response getTashira() throws Exception;
	
	@GET
	@Path("sub_main")
	public Response getSub_main() throws Exception;
	
	@GET
	@Path("sub_br")
	public Response getSub_br() throws Exception;
	
	@GET
	@Path("saderunits")
	public Response getSaderUnits(@QueryParam("doc_num") int doc_num,
			@QueryParam("doc_date") String doc_date) throws Exception;

}
