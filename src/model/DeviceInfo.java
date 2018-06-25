package model;

import util.StringUtil;


/**
 * @author mzyw_linilq
 * @version 1.0
 * 设备信息数据类
 * 
 */
public class DeviceInfo {

	public Integer id;
	private String packageId;
	private String imei;
	private String imsi;
	
	private String model;
	private String mac;
	private String osVersion;
	private String networkType;
	private String screenWidth;
	private String screenHeight;
	private String ver;
	private String sid;
	
	public static String getSIMType(String imsi)
	{
	    int i = -1001;
	    String SIMType = "";
	  
	      //TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
	     // String str = localTelephonyManager.getSimOperator();
       String str = "";
       if(!StringUtil.is_nullString(imsi))
       {
    	   if(imsi.length() > 5)
    	   {
    		   str = imsi.substring(0, 5);
    	   }
       }
       
       
        if (str.equals("46000") || str.equals("46002") || str.equals("46007") || str.equals("46020"))	        
        {
        	SIMType = Pay.MmPayType;
        }
        else if (str.equals("46001") || str.equals("46006"))
        {
        	SIMType = Pay.UniPayType; 
        }
        else if (str.equals("46003") || str.equals("46005") || str.equals("46011"))
        {
        	SIMType = Pay.TCPayType;      
        }
        else if(StringUtil.is_nullString(imsi)||str.equals("")) 
	     {
	    	 SIMType = Pay.MmPayType;
	     }
	    return SIMType;
	     
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public String getScreenWidth() {
		return screenWidth;
	}
	public void setScreenWidth(String screenWidth) {
		this.screenWidth = screenWidth;
	}
	public String getScreenHeight() {
		return screenHeight;
	}
	public void setScreenHeight(String screenHeight) {
		this.screenHeight = screenHeight;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	
	
}
