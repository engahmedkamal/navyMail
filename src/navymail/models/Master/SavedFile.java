package navymail.models.Master;

public class SavedFile {
	int code;
	String name;
	int file_type;

	public static String CODE_COL = "code";
	public static String NAME_COL = "name";
	public static String FILE_TYP_COL = "file_type";
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFile_type() {
		return file_type;
	}
	public void setFile_type(int file_type) {
		this.file_type = file_type;
	}

	
}
