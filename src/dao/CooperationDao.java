package dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.yeepay.HttpUtils;

import model.Activate;
import model.App;
import model.Channel;
import model.ChannelData;
import model.ChannelDataList;
import model.Cooperation;
import model.Gamedata;
import model.Pay;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;
import azul.BaseDao;
import azul.CacheDao;

public class CooperationDao extends CacheDao{
	public CooperationDao() {
		init();
	}
	public void test()
	{
		Cooperation obj = new Cooperation();
		
		obj.setChannelNo("001");
		obj.setCpNo("001");
		obj.setAppNo("001");
		obj.setPacketId("0005");
		obj.setSettleType("CPA");
		obj.setSettleValue(100);//一元
		obj.setSettleRatio(70);//70%
		obj.setActiveNum(1000);
		obj.setMmAppNo("300002882065");
		obj.setMmAppKey("6DD23E6257198AE7");
		obj.setMmCode("30000288206502");
		//obj.set("30000288206502");
		addCooperation(obj);
		//String sql = String.format("insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','%s','中泰源公司提供',0)", "元宝");
		//String sql = "insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','元宝','中泰源公司提供',0)";
		//this.executeUpdate(sql);
	}
	
	public ArrayList<Object> getRecordsByAppNo(String AppNo)
	{
		
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where app_no='%s'", baseTableName,AppNo);
		objList = getList(sql);
		
		return objList;
	}
	
	public ArrayList<Object> getRecordsByAppNoChannel(String AppNo,String BusinesserNo,String channleno,String packet_id)
	{
		 ArrayList<Object> ret = getRecordsByAppNoChannel("", AppNo, BusinesserNo, channleno, packet_id);
		 return ret;
	}
	public ArrayList<Object> getRecordsByAppNoChannel(String game_type,String AppNo,String BusinesserNo,String channleno,String packet_id)
	{
		//DebuUtil.log("getRecordsByAppNoChannel");
		ArrayList<Object> objList;
		String sql;
		//sql = String.format("select * from %s where 1=1 ", baseTableName);//select * from cooperation,app where 1=1 and cooperation.app_no=app.no
		sql = "select cooperation.*  from cooperation,app where cooperation.app_no=app.no ";
		
		if(!StringUtil.is_nullString(AppNo))
		{
		   sql+=" and cooperation.app_no='"+AppNo+"'";
		}
		
		if(!StringUtil.is_nullString(game_type))
		{
		   sql+=" and app.game_type='"+game_type+"'";
		}
		
		if(!StringUtil.is_nullString(channleno))
		{
		   sql+=" and cooperation.channel_no='"+channleno+"'";
		   
		}
		
		if(!StringUtil.is_nullString(BusinesserNo))
		{
		   sql+=" and cooperation.business_no='"+BusinesserNo+"'";
		}
		
		if(!StringUtil.is_nullString(packet_id))
		{
		   sql+=" and cooperation.packet_id='"+packet_id+"'";
		}
		
		objList = getList(sql);
		
		//DebuUtil.log2("getRecordsByAppNoChannel="+sql);
		return objList;
	}
	
	public ArrayList<Object> getRecordsByChannelNo(String ChannelNo)
	{
		
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where channel_no='%s'", baseTableName,ChannelNo);
		objList = getList(sql);
		
		return objList;
	}
	
	public void updateChannelName(String ChannelNo,String ChannelName)
	{
		DebuUtil.log("更新合作表");
		ArrayList<Object> objList = getRecordsByChannelNo(ChannelNo);
		if(objList.size() > 0)
		{
			for(int i = 0; i < objList.size(); i ++)
			{
				DebuUtil.log("修改渠道名");
				Cooperation cooperation = (Cooperation)objList.get(0);
				cooperation.setChannelName(ChannelName);
				super.edit(cooperation);
			}
		}
	}
	
	public void updateAppName(String AppNo,String AppName)
	{
		DebuUtil.log("更新合作表");
		ArrayList<Object> objList = getRecordsByAppNo(AppNo);
		if(objList.size() > 0)
		{
			for(int i = 0; i < objList.size(); i ++)
			{
				DebuUtil.log("修改游戏名");
				Cooperation cooperation = (Cooperation)objList.get(0);
				cooperation.setAppName(AppName);
				super.edit(cooperation);
			}
		}
	}
	
	public String addItem(Cooperation cooperation)
	{
		Cooperation coop = getRecord(cooperation.packet_id);
		String ret = "-1";
		if(coop == null)
		{
			add(cooperation);
			ret = "1";
		}
		return ret;
	}
	public String EditItem(Cooperation cooperation)
	{
		/*String game_id = cooperation.app_no;
		String packet_id = cooperation.packet_id;
		String datestr;
		String channelType = cooperation.settle_type;
		String dispaytype="";
		
		Date now=new java.util.Date();
		now=util.DateUtil.addDate("dd",-1,now);
		datestr = util.DateUtil.getDate(now);*/
		
		
		//Gamedata.get_GamedataList(cooperation.app_no,cooperation.packet_id,cooperation.settle_type,"",util.DateUtil.getDate(now),util.DateUtil.getDate(now));;
		return edit(cooperation);
	}
	
	public String chngRitio(Cooperation cooperation)
	{
		Date now=new java.util.Date();
		now=util.DateUtil.addDate("dd",-1,now);
		String datestr = util.DateUtil.getDate(now);
		return chngRitio2( cooperation, datestr);
	}
	public Cooperation getRecord(String packet_id)
	{
		Cooperation cooperation = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where packet_id='%s'", baseTableName,packet_id);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			cooperation = (Cooperation)objList.get(0);
		}
		return cooperation;
	}
	
	public String chngRitio(String packet_id,String datestr,int ratio)
	{
		String ret = "";
		Cooperation cooperation = getRecord(packet_id);
		if(cooperation != null)
		{
			cooperation.setSettleRatio(ratio);
			ret = chngRitio2(cooperation,datestr);
		}
		return ret;
		
	}
	

	public String chngRitio2(Cooperation cooperation,String datestr)
	{
		String ret = "";
		
	
		String game_id = cooperation.app_no;
		String packet_id = cooperation.packet_id;
		//String datestr;
		String channelType = cooperation.settle_type;
		String dispaytype="";
		String sql;
		int ratio = cooperation.getSettleRatio();
	    
		if(ConstValue.OPTIMIZE == 1)
		{
			ChannelData channelData = ChannelDataList.getChannelData(packet_id,datestr);
			if(channelData != null)
			{
				channelData.settle(ratio);
				ChannelDataDao channelDataDao = new ChannelDataDao();
				channelDataDao.edit(channelData);
			}
		}
		else
		{
			if(channelType.equals("CPA"))
			{
			   ActivateDao activateDao = new ActivateDao();
			   sql = String.format("UPDATE activate SET type = '' WHERE packet_id='%s' and date='%s'"
		   				,packet_id,datestr);
			   activateDao.executeUpdate(sql);
			   //DebuUtil.log("sql="+sql);
			   activateDao.get_actNum2(game_id,packet_id,datestr,channelType,dispaytype,1,ratio);
			   sql = String.format("delete from data_buf WHERE packet_id='%s' and date='%s'"
		   				,packet_id,datestr);
			   activateDao.executeUpdate(sql);
			}
			else if(channelType.equals("CPA_R"))
			{
			   RegisterDao registerDao = new RegisterDao();
			   sql = String.format("UPDATE register SET type = '' WHERE packet_id='%s' and date='%s'"
	   				,packet_id,datestr);
			   //DebuUtil.log("sql="+sql);
			   registerDao.executeUpdate(sql);
			   registerDao.getRegisterNum2(game_id,packet_id,datestr,channelType,dispaytype,1,ratio);
			   sql = String.format("delete from data_buf WHERE packet_id='%s' and date='%s'"
		   				,packet_id,datestr);
			   registerDao.executeUpdate(sql);
			}
		}
		
		return ret;//
		//return edit(cooperation);
	}
	
	public int addCooperation(Cooperation obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where packet_id='%s'", baseTableName,obj.getPacketId());
		int count = getRecordCount(sql);
	
		DebuUtil.log("合作表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	
	
	public static int GetIntFromMap(Map map,String key)
	{
		int ret = 0;
		String retstr = "";
		retstr = (String) map.get(key);
		if(!StringUtil.is_nullString(retstr))
		{
			ret = Integer.valueOf(retstr);
		}
		return ret;
	}
	
	public void SettelDataFromSrv(ArrayList<String> payurlList,ArrayList<String> requrlList,String date)
	{
		//String url = "";
		Activate activate = null;
		ArrayList<Object> objList;
		String sql;
		//String date =  DateUtil.getDate();//
		sql = String.format("select * from %s ",baseTableName);//and packet_id='211152001' and date='2014-05-04',date<'2014-11-24'
		objList = getList(sql);
		
		//settleDota();
		for(int i=0;i<objList.size();i++)
		{
			Cooperation cooperation =(Cooperation)objList.get(i);
			 int mmpay = 0;
			 int msmNum = 0;
			 int feeNum = 0;
			 int reqNum = 0;
			 int reqOkNum = 0;
				 
			    ChannelData channelData = ChannelDataList.getChannelData(cooperation.packet_id,date);
				 DebuUtil.log("正在同步:"+channelData.packet_id+" , " +channelData.date);
				 AppDao appDao = new AppDao();
				 App app = appDao.getAppRecord(channelData.getGameId());
				if((app != null) && (app.getGameType().equals(App.OFF_LINE)))
				{
				 
				  for(int j=0;j<payurlList.size();j++)
				  {
						 String url =(String)payurlList.get(j);
						 Map<String,String> retMap = GetChannelDataFromSrv(url,channelData.packet_id,channelData.date);
					    
						 mmpay += GetIntFromMap(retMap,"mmpay");//.retMap.get("mmpay");
						 msmNum += GetIntFromMap(retMap,"msmNum");
						 feeNum += GetIntFromMap(retMap,"feeNum");						
						 
						 DebuUtil.log("mmpay:"+mmpay+",msmNum:"+msmNum+",feeNum:"+feeNum);
						 
						 try {
			  					Thread.sleep(50);
			  				} catch (InterruptedException e) {
			  					// TODO Auto-generated catch block
			  					e.printStackTrace();
			  					
			  				}
				 }
				  
				 for(int j=0;j<requrlList.size();j++)
				  {
						 String url =(String)requrlList.get(j);
						 Map<String,String> retMap =GetChannelDataFromSrv(url,channelData.packet_id,channelData.date);
					    
						
						 reqNum += GetIntFromMap(retMap,"reqNum");
						 reqOkNum += GetIntFromMap(retMap,"reqOkNum");
						 
						 DebuUtil.log("reqNum:"+reqNum+",reqOkNum:"+reqOkNum);
						 try {
			  					Thread.sleep(50);
			  				} catch (InterruptedException e) {
			  					// TODO Auto-generated catch block
			  					e.printStackTrace();
			  					
			  				}
				 }
				 if( mmpay > channelData.mmPay)
				 {
				    channelData.mmPay  = mmpay;
				 }
				 if(msmNum > channelData.msmNum)
				 {
				   channelData.msmNum  = msmNum;
				 }
				 if(feeNum > channelData.feeNum)
				 {
				  channelData.feeNum  = feeNum;
				 }
				 if(reqNum > channelData.reqNum)
				 {
				  channelData.reqNum  = reqNum;
				 }
				 if(reqOkNum >  channelData.reqOkNum)
				 {
				     channelData.reqOkNum  = reqOkNum;
				 }
				  channelData.settle(channelData);
				  ChannelDataDao cannelDataDao= new ChannelDataDao();			
				  cannelDataDao.edit(channelData);
				}
						
		}
		
		return ;
	}
	
	 public static Map<String,String> getMapByReqStr(String str)
	 {
	    	//String ip = "packet_id=1235&date=2014-12-20&mmpay=6532";
	        StringTokenizer token=new StringTokenizer(str,"&");  
	        Map<String,String> map = new HashMap<String,String>(); 
	        while(token.hasMoreElements()){  
	        	String splitstr = token.nextToken();
	        	String results[]=splitstr.split("=");
	        	if(results.length > 1)
	        	map.put(results[0], results[1]);
	        } 
	        
	        return map;
	      // System.out.print("date="+map.get("date")); 
	       
	 }
	
	public Map<String,String> GetChannelDataFromSrv(String url,String packet_id,String date)
	{
        Map<String,String> map = new HashMap<String,String>();    
    	String msmrsq="";
    	 Map<String,String> resmap = new HashMap<String,String>();    
    	 
        map.put("packet_id", packet_id);    
        map.put("date", date); 
        
		try {
			List list= HttpUtils.URLGet(url, map,1000*20);
			for(int i = 0; i < list.size() ; i++)
			{
				String str = (String) list.get(i);
				msmrsq += str;
			}
			if(!StringUtil.is_nullString(msmrsq))
			{
				resmap = getMapByReqStr(msmrsq);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resmap;
	}
	
	public List<ArrayList> getApkNo(String gameNo,String channelNo,String businessNo){
		String sql = "SELECT packet_id from cooperation where app_no = '%s' and channel_no = '%s' and business_no = '%s'";
		sql = String.format(sql, gameNo,channelNo,businessNo);
		List<ArrayList> list = getObjectList(sql);
		return list;
	}
	
	public List<ArrayList> getAllGameByChlNo(String channelNo){
		String sql = "select app.name,app.no from app,cooperation where cooperation.app_no = app.no and "
				+ " cooperation.channel_no ='" + channelNo + "'";
		return getObjectList(sql);
	}
	
	
	
}
