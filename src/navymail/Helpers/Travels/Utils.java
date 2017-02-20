package navymail.Helpers.Travels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	public static Date stringToDate(String s/* "dd-MM-yyyy" */) throws ParseException {
		if (s == null || s.equals(""))
			return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date d = dateFormat.parse(s);
		return d;
	}
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	static public String dateCondition(String attr, Date from, Date to) {
		if (from == null && to == null)
			return null;

		String col = " missions." + attr;

		if (from == null)
			return col + " <=  '" + format.format(to) + "'";

		if (to == null)
			return col + " >= '" + format.format(from) + "'";

		return col + " BETWEEN '" + format.format(from) + "' and '"
				+ format.format(to) + "'";

	}
}
