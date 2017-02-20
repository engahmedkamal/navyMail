package navymail.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesLoader{

	static private PropertiesLoader instance;
	private Map<String, Map<String, String>> prop = new HashMap<String, Map<String,String>>();
	private PropertiesLoader() throws IOException {
		Properties config = new Properties();
		System.out.println("Loading Properties");
		config.load(new InputStreamReader(new FileInputStream(Environment.PROJECT_PATH + "/res/config.properties")));
		System.out.println("Properties loaded.");
		for(String key : config.stringPropertyNames()) {
			  String value = config.getProperty(key);
			  String[] keys = key.split("\\|");
			  System.out.println(keys[0] + " [" + keys[1] + " => " + value + "]");
			  Map<String, String> m = prop.get(keys[0]);
			  if(m==null)
				  prop.put(keys[0], m = new HashMap<String, String>());
			  m.put(keys[1], value);
		}
		System.out.println("Bundles Count = " + prop.size());
	};
	public static PropertiesLoader getInstance(){
		if(instance == null)
			try {
				instance = new PropertiesLoader();
				System.out.println("PropertiesLoader created successfully");
			} catch (IOException e) {
				e.printStackTrace();
			}
		return instance;
	}
	
	public String getValue(String bundle, String key){
		 Map<String, String> m = prop.get(bundle);
		  if(m==null){
			  System.out.println("Failed to load bundle: '" + bundle + "'");
			  return "";
		  }
		  String v = m.get(key);
		  if(v==null){
			  System.out.println("Failed to load key: '" + bundle + "' '" + key + "'");
			  return "";
		  }
		  return v;
	}
}
