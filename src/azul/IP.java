package azul;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Modelip;
import net.sf.json.JSONObject;
import util.DebuUtil;
import util.StringUtil;

import com.yeepay.HttpUtils;

import dao.ModelipDao;


public class IP {

	 public static String getIPFromSrv(String IP)
	 {
			String strUrl = " http://apis.haoservice.com/getLocationbyip";   
	    
	        Map<String,String> map=new HashMap<String,String>();    
	        map.put("ip", IP);     
	        map.put("key", "ccf428507bce4c4e9999e12d4d0f0ca5");
	        String msmrsq = "";
			try {
				List list= HttpUtils.URLGet(strUrl, map);
				for(int i = 0; i < list.size() ; i++)
				{
					String str = (String) list.get(i);
					msmrsq += str;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DebuUtil.log("msmrsq="+msmrsq);
			return msmrsq;
	 }
	 
	 public static String getAddr(String IP)
	 {
		 
		 String ret = "";
		 ModelipDao IPDao = new ModelipDao();
		 ret = IPDao.getIP(IP);
		 
		 if(StringUtil.is_nullString(ret))
		 {
		    String msmrsq=getIPFromSrv(IP);
			JSONObject json = JSONObject.fromObject(msmrsq);
			int ErrCode = json.getInt("error_code");
			
			if(ErrCode == 0)
			{
				JSONObject result=(JSONObject)json.get("result");
				
				String city = result.getString("city");
				String province = result.getString("province");

				ret = province+"_"+city;
				if(ret.length() > 30)
				{
					ret = ret.substring(0, 30);
				}
				Modelip ip = new Modelip();
				ip.setIp(IP);
				ip.setAddr(ret);
				IPDao.add(ip);
			}
		 }
		return ret;
	 }
	 
}
