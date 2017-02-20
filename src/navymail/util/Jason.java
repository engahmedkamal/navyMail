package navymail.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

public class Jason {
	static ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

	public Jason() {
		// TODO Auto-generated constructor stub
	}

	public static  String toJson(Object ob) throws Exception {
		synchronized (ob) {
			String json = ow.writeValueAsString(ob);
			return json;
		}
	}
}
