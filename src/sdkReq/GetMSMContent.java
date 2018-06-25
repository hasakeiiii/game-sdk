package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeepay.HttpUtils;


import model.MsmList;
import model.Pay;
//import mobi.zty.sdk.game.Constants;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;
import util.StringUtil;

import dao.MsmListDao;
import dao.RegisterDao;

public class GetMSMContent extends HttpServlet{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8783499538690341229L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	//http://124.202.141.81:8080/MMSocketSer/MMServlet?chn=2502&imei=869630019000979&imsi=460026894200239&xexData=234234234234234
	public String getMSM(String chn,String imei,String imsi,String uData)
	{
		String strUrl = "http://124.202.141.81:8080/MMSocketSer/MMServlet";   
        
        Map<String,String> map=new HashMap<String,String>();    
        map.put("chn", chn);    
        map.put("imei", imei);   
        map.put("imsi", imsi);  
        map.put("xexdata", uData);   
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
		return msmrsq;
	}
	public void test()
	{
		
		MsmList msmList = new MsmList();
		msmList.setChn("2015");
        msmList.setImei("645647876564" );
        msmList.setImsi("6478746544");
        msmList.setXexdata("1456465");
        
        MsmListDao  msmListDao = new MsmListDao();
		msmListDao.add(msmList);
	}
	public void HandleReq(String reqStr, HttpServletResponse response)
	{
		JSONObject json = JSONObject.fromObject(reqStr);
		
		//int amount = json.getInt("amount");
		int index = json.getInt("index")+1;
		int amount = json.getInt("amount");
		String gameId = json.getString("gameId");//
		String imsi = json.getString("imsi");
		String imei = json.getString("imei");
		String packet_id = json.getString("userdata");
		
		GetAmountIndex.HandleReq(amount,imei,imsi,packet_id,gameId,"",response);
	}
	public void HandleReq2(String reqStr, HttpServletResponse response)
	{
		PrintWriter out = null;
		int ret = ConstValue.Fail;
		//model.Login obj = null;
		String Username="";
		int time = 0;
		String ver="";
		String device_id="";
		String game_id = "";
		int level = 0;
		JSONObject rsqjson = new JSONObject();
		
		response.setContentType("text/html;charset=utf8");
		
		try {
			 out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(reqStr != null)
		{
			MsmList msmList = new MsmList();
			
			DebuUtil.log("请求数据"+reqStr);
			JSONObject json = JSONObject.fromObject(reqStr);
			
			//int amount = json.getInt("amount");
			int index = json.getInt("index")+1;
			int amount = json.getInt("amount");
			String gameId = json.getString("gameId");//
			String imsi = json.getString("imsi");
			String imei = json.getString("imei");
			String packet_id = json.getString("userdata");
			
			String chnno = "";
			String bDel = "0";
			String fillCon = "中国移动";
	        if(packet_id.equals("381180001"))
	        {
	        	chnno = "25";
	        }
	        else if(packet_id.equals("381182001"))
	        {
	        	chnno = "26";
	        }
	        
	        String chn = String.format("%s%02d",chnno,index);
	        
	        //DebuUtil.log3("请求数据="+chn+imei+imsi+packet_id);
	        
	        String msmrsq = getMSM( chn, imei, imsi, packet_id);
	        msmList.setChn(chn);
	        msmList.setImei(imei );
	        msmList.setImsi(imsi);
	        msmList.setXexdata(packet_id);
	        msmList.setAmount(amount);
	        
	        DebuUtil.log("回应数据="+msmrsq);
			JSONObject mssjson = null;
			if(!StringUtil.is_nullString(msmrsq))
			{
				mssjson = JSONObject.fromObject(msmrsq);
			}
			
			if(mssjson == null)
			{
				rsqjson.put("num","");
				rsqjson.put("content","");
				rsqjson.put("bDel","");
				rsqjson.put("FillCon","");
			}
			else				
			{
				String num = mssjson.getString("desttermid");
				String content = mssjson.getString("msg");
				String code =mssjson.getString("code"); 
				
				msmList.setNum(num);
				msmList.setMsg(content);
				msmList.setCode(code);
				MsmListDao  msmListDao = new MsmListDao();
				msmListDao.add(msmList);
				
				if(code.equals("1"))
				{
					num = "";
				}
				rsqjson.put("num",num);//imei
				rsqjson.put("content",content);//imei
				rsqjson.put("bDel",bDel);
				rsqjson.put("FillCon",fillCon);
			}
			
		}
		
		out.print(rsqjson.toString());
			
		//out.print("OK");
		out.close();
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//DebuUtil.log("gameExit doGet");
		HandleReq(request.getQueryString(),response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//DebuUtil.log("gameExit doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		if(ConstValue.RDR == 1)
		{
		    //str = URLDecoder.decode(str,"UTF-8");	
		}
		HandleReq(str,response);
	}
	

}
