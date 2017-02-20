package navymail.API.WaredAPI;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import navymail.Helpers.Master.MyResponse;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("image")
public interface ImageHandlerAPI {

	@GET
	@Path("delete")
	public Response delete_image(@QueryParam("id") int id,
			@QueryParam("path") String path, @QueryParam("title") String title)
			throws Exception;

	@POST
	@Path("edit")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadImage(
			@FormDataParam("uploadfile") InputStream uploadedInputStream,
			@FormDataParam("uploadfile") FormDataContentDisposition fileDetail,
			@FormDataParam("id") int doc_id,
			@FormDataParam("operation") int operation,
			@FormDataParam("img_path") String img_path,
			@FormDataParam("title") String title)
			throws FileNotFoundException, IOException, Exception;

}
