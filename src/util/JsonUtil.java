package util;

import net.sf.json.JSONObject;

public class JsonUtil {

	public static String getString(JSONObject json,String tag)
    {
    	String ret="";
    	if(json.has(tag))
		{
		   ret = json.getString(tag);
		}
		return ret;
		
    }
    
	public static int getInt(JSONObject json,String tag)
    {
    	int ret = 0;
    	if(json.has(tag))
		{
		   ret = json.getInt(tag);
		}
		return ret;
		
    }
	
	
}
