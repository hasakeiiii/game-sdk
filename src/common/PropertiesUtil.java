package common;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesUtil {
	public static String getProp(String propName) {
		String result = "";
		try {
			InputStream fis = PropertiesUtil.class.getResourceAsStream("system.properties");
			//InputStream fis= PropertiesUtil.class.getResourceAsStream("system.properties");
			Properties prop = new Properties();
			prop.load(fis);
			//prop.list(System.out);
			result = prop.getProperty(propName);
			if(result==null){
				result="";
			}
			fis.close();
		} catch (Exception e) {
			System.out.println("PropertiesUtil.getProp error:" + propName);
			System.out.println(e);
		}
		return result;
	}

	public static void setProp(String key, String value) {
		try {
			String outpath=PropertiesUtil.class.getResource("").getPath()+"system.properties";
			//System.out.println(outpath);
			Properties prop = new Properties();
			//FileInputStream fis = new FileInputStream("system.properties");
			InputStream fis= PropertiesUtil.class.getResourceAsStream("system.properties");
			prop.load(fis);
			OutputStream fos = new FileOutputStream(outpath);
            prop.setProperty(key, value);
            prop.store(fos, "system properties");
            fis.close();
			fos.close();
		} catch (Exception e) {
			System.out.println("PropertiesUtil.setProp error:" + key+"="+value);
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		System.out.println(PropertiesUtil.getProp("foo1"));
		//PropertiesUtil.setProp("foo7","aaaa7777");
		System.out.println("ok");
	}
}
