package azul;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import util.DebuUtil;
import util.StringUtil;

import com.yeepay.HttpUtils;

import dao.CellDao;


public class LBS {

	 public static String getLBSFromSrv(String mcc,String mnc,String cell_id,String lac)
	 {
			String strUrl = "http://api.haoservice.com/api/getlbs";   
	    
	        Map<String,String> map=new HashMap<String,String>();    
	        map.put("mcc", mcc);    
	        map.put("mnc", mnc);   
	        map.put("cell_id", cell_id);  
	        map.put("lac", lac);   
	        map.put("key", "97608d6d60994c1e86307ea444ec547c");
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
			/*try {
				//msmrsq = new String(msmrsq.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} */
			DebuUtil.log("msmrsq="+msmrsq);
			return msmrsq;
	 }
	 
	 public static String getLBS(String mcc,String mnc,String cell_id,String lac)
	 {
		 int imcc = Integer.valueOf(mcc);
		 int imnc = Integer.valueOf(mnc);
		 int icell_id = Integer.valueOf(cell_id);
		 int ilac = Integer.valueOf(lac);
		 String ret = "";
		 CellDao lBSDao = new CellDao();
		 ret = lBSDao.getLBS(imcc, imnc, icell_id,ilac);
		 
		 if(StringUtil.is_nullString(ret))
		 {
		    String msmrsq=getLBSFromSrv(mcc, mnc, cell_id,lac);//"460","00","28655","17695",
			
			int ErrCode = -1;
			JSONObject json = null;
			if(!StringUtil.is_nullString(msmrsq))
			{
				json = JSONObject.fromObject(msmrsq);
				ErrCode =json.getInt("ErrCode");
			}
			
			if(ErrCode == 0)
			{
				JSONObject location=(JSONObject)json.get("location");
				JSONObject address = (JSONObject)location.get("address");
				
				String region = address.getString("region");
				String city = address.getString("city");
				String county = address.getString("county");
				
				/*try {
					region =new String(region.getBytes(),"UTF-8");
					city =new String(city.getBytes(),"UTF-8");
					county =new String(county.getBytes(),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				
				ret = region+city+county;
				DebuUtil.log("回应地址="+ret);
				model.Cell lbs = new model.Cell();
				lbs.setMcc(imcc);
				lbs.setMnc(imnc);
				lbs.setCellId(icell_id);
				lbs.setLac(ilac);
				lbs.setAddr(ret);
				lBSDao.add(lbs);
			}
		 }
		return ret;
	 }
	 
}
