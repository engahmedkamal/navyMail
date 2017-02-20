package navymail.API.SaderAPI;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * 
 * @author zankalony
 * 
 */
@Path("SaderDocuments")
public interface SaderDocumentAPI {

	@GET
	@Path("new")
	public Response create(@QueryParam("folder_id") int folder_id, @QueryParam("prev_doc_id") String prev_id,
			@QueryParam("img_no") int img_no, @QueryParam("title") String title, @QueryParam("doc_num") int doc_num,
			@QueryParam("doc_date") String doc_date, @QueryParam("units") int units,
			@QueryParam("doc_type") int doc_type, @QueryParam("file_save_num") int file_save_num,
			@QueryParam("sub_file_save_num") int sub_file_save_num, @QueryParam("tashira") int tashira,
			@QueryParam("target_id") int target_id, @QueryParam("security_type") int security_type,
			@QueryParam("units_ids") String unit_ids) throws Exception;

	@GET
	@Path("sadersearch")
	public Response search(@Context UriInfo info) throws Exception;

	@GET
	@Path("edit")
	public Response update(@QueryParam("id") int id, @DefaultValue("") @QueryParam("prev_doc_id") String prev_id,
			@QueryParam("title") String title, @QueryParam("doc_num") int doc_num,
			@QueryParam("doc_date") String doc_date, @QueryParam("units") int units,
			@QueryParam("file_save_num") int file_save_num, @QueryParam("sub_file_save_num") int sub_file_save_num,
			@QueryParam("doc_type") int doc_type,
			@QueryParam("target_id") int target_id, @QueryParam("tashira") int tashira,@QueryParam("security_type") int security_type,
			@QueryParam("unit_ids") String unit_ids) throws Exception;

	@GET
	@Path("print")
	public Response print(@QueryParam("id") int id) throws Exception;
}
