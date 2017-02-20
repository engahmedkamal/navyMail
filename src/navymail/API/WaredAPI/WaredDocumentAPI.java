package navymail.API.WaredAPI;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * 
 * @author zankalony
 * 
 */
@Path("documents")
public interface WaredDocumentAPI {

	@GET
	@Path("minisearch")
	public Response minisearch(@QueryParam("doc_num") int doc_num,
			@QueryParam("doc_out_num") int doc_out_num) throws Exception;

	@GET
	@Path("search")
	public Response search(@Context UriInfo info) throws Exception;
	
	@GET
	@Path("search_motba")
	public Response search_motb3a(@Context UriInfo info) throws Exception;

	@GET
	@Path("{id}")
	public Response show(@PathParam("id") int id) throws Exception;

	@GET
	@Path("show")
	public Response show_document(@QueryParam("id") int id) throws Exception;

	@GET
	@Path("new")
	public Response create(@QueryParam("folder_id") int folder_id,
			@QueryParam("prev_doc_id") String prev_id,
			@QueryParam("img_no") int img_no, @QueryParam("title") String title,
			@QueryParam("doc_num") int doc_num,
			@QueryParam("created_at") String created_at,
			@QueryParam("doc_out_num") int doc_out_num,
			@QueryParam("doc_out_date") String doc_out_date,
			@QueryParam("unit_id") int unit_id,
			@QueryParam("doc_type") int doc_type,
			@QueryParam("target_id") int target_id,
			@QueryParam("status_id") int status_id) throws Exception;

	@GET
	@Path("edit")
	public Response update(@QueryParam("id") int id,
			@DefaultValue("") @QueryParam("prev_doc_id") String prev_id,
			@QueryParam("title") String title,
			@QueryParam("doc_num") int doc_num,
			@QueryParam("created_at") String created_at,
			@QueryParam("doc_out_num") int doc_out_num,
			@QueryParam("doc_out_date") String doc_out_date,
			@QueryParam("unit_id") int unit_id,
			@QueryParam("trans_out_num") int trans_out_num,
			@QueryParam("trans_out_date") String trans_out_date,
			@QueryParam("doc_type") int doc_type,
			@QueryParam("target_id") int target_id,
			@QueryParam("status_id") int status_id,
			@QueryParam("notes") String notes,
			@QueryParam("tashira") String tashira,
			@QueryParam("tashira_quad") String tashira_quad,
			@QueryParam("resp_unit") String resp_unit) throws Exception;

	@GET
	@Path("editStatus")
	public Response update(@QueryParam("id") int id,
			@QueryParam("target_id") int target_id,
			@QueryParam("status_id") int status_id) throws Exception;
	
	@GET
	@Path("editIsatgel")
	public Response update(@QueryParam("id") int id,
			@QueryParam("status") int status_id,
			@QueryParam("unit") int unit,
			@QueryParam("date") String date,
			@QueryParam("answer") int answer,
			@QueryParam("note") String note) throws Exception;
	
	@GET
	@Path("insertIsatgel")
	public Response update(@QueryParam("id") int id,
			@QueryParam("date") String date,
			@QueryParam("unit") int unit) throws Exception;

	@GET
	@Path("deleteIsatgel")
	public Response delete(@QueryParam("id") int id,
			@QueryParam("date") String date,
			@QueryParam("unit") int unit) throws Exception;
	
	@GET
	@Path("print")
	public Response print(@QueryParam("id") int id) throws Exception;
	
}
