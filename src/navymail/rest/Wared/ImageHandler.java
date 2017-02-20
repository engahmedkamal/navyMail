package navymail.rest.Wared;

import java.io.InputStream;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import navymail.API.WaredAPI.ImageHandlerAPI;
import navymail.Helpers.Wared.DocUtils;
import navymail.Helpers.Wared.ImageUtils;
import navymail.models.Wared.WaredDocument;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

@Path("image")
public class ImageHandler implements ImageHandlerAPI {

	public Response delete_image(int id, String path, String title)
			throws Exception {
	
		// Step 1 , delete from database
		ImageUtils.delete_from_database(path);

		// step2 // delete from tab folders if found
		WaredDocument d = DocUtils.findDocById(id);
		int status = d.getStatus_id();
		if (status == 1 || status == 2) {
			String tabPath = DocUtils.getPath(d.getDoc_type(),
					d.getTarget_id(), status);
			String imgName = path.substring(path.lastIndexOf("/"));
			ImageUtils.delete_from_database(tabPath + "/" + title + imgName);
		}

		return new Documents().show(id);
	}

	public Response add_image(int id, String path, String title, InputStream img, boolean insert)
			throws Exception {
		InputStream copy[] = ImageUtils.cloneInputStream(img);
		// step 1 , add image to database
		ImageUtils.add_to_database(path, copy[0] ,insert);

		// step2 // add image to tab folders if found
		WaredDocument d = DocUtils.findDocById(id);
		int status = d.getStatus_id();
		if (status == 1 || status == 2) {
			String tabPath = DocUtils.getPath(d.getDoc_type(),
					d.getTarget_id(), status);
			String imgName = path.substring(path.lastIndexOf("/"));
			String tmp_name = tabPath + "/" + title + imgName;
			ImageUtils.add_to_database(tmp_name, copy[1],insert);
		}
		return new Documents().show(id);
	}

	public Response uploadImage(InputStream uploadedInputStream,
			FormDataContentDisposition fileDetail, int id, int operation,
			String img_path, String title) throws Exception {

		boolean insert = operation == 1;

		add_image(id, img_path, title, uploadedInputStream, insert);

		return new Documents().show(id);
	}

}
