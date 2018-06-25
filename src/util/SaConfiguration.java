package util;

import java.util.ResourceBundle;

 
/**
 * @author lu.li
 *
 */
public class SaConfiguration {
	

	private static Object lock              = new Object();
	private static SaConfiguration config     = null;
	private static ResourceBundle rb        = null;
	private static final String CONFIG_FILE = "";
	
	private SaConfiguration() {
		rb = ResourceBundle.getBundle(CONFIG_FILE);
	}
	
	private SaConfiguration(String filename) {
		rb = ResourceBundle.getBundle(filename);
	}
	
	public static SaConfiguration getInstance() {
		synchronized(lock) {
			if(null == config) {
				config = new SaConfiguration();
			}
		}
		return (config);
	}
	
	public static SaConfiguration getInstance(String filename) {
		synchronized(lock) {
			if(null == config) {
				config = new SaConfiguration(filename);
			}
		}
		return (config);
	}
	
	public String getValue(String key) {
		String ret = "";
		if(rb.containsKey(key))
		{
			ret = rb.getString(key);
		}
		return ret;//(rb.getString(key));
	}
}
