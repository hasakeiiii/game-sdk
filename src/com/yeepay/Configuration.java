package com.yeepay;

import java.util.ResourceBundle;

 
/**
 * @author lu.li
 *
 */
public class Configuration {
	

	private static Object lock              = new Object();
	private static Configuration config     = null;
	private static ResourceBundle rb        = null;
	private static final String CONFIG_FILE = "merchantInfo";
	
	private Configuration() {
		rb = ResourceBundle.getBundle(CONFIG_FILE);
	}
	
	private Configuration(String filename) {
		rb = ResourceBundle.getBundle(filename);
	}
	
	public static Configuration getInstance() {
		synchronized(lock) {
			if(null == config) {
				config = new Configuration();
			}
		}
		return (config);
	}
	
	/*public static Configuration getInstance(String filename) {
		synchronized(lock) {
			if(null == config) {
				config = new Configuration(filename);
			}
		}
		return (config);
	}*/
	
	public String getValue(String key) {
		return (rb.getString(key));
	}
}
