package model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alipay.sign.Base64;
import com.yeepay.HttpUtils;

import dao.AppDao;
import dao.CooperationDao;
import dao.RegisterDao;


import net.sf.json.JSONObject;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.JsonUtil;
import util.Rsa;
import util.StringUtil;

public class Pay implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2180589212064454090L;
	public static final  String YeePayType ="yeepay";
	public static final  String AliPayType ="alipay";
	public static final  String UnionPayType ="unionpay";
	public static final  String TenPayType ="tenpay";
	public static final  String MmPayType ="mmpay";
	public static final  String TCPayType ="tcpay";
	public static final  String UniPayType ="unipay";
	public static final  String MzPayType ="mzpay";
	public static final  String WoPayType ="wopay";
	public static final  String OpenPayType ="openpay";
	public static final  String OnlyPayType ="onlypay";
	public static final  String JDPayType ="jdpay";

	public static final  String WxPayType = "wxpay";
	

	public static final  String FanHeType ="fanhe";

	public static final  String LeDongType ="leDong";
	public static final  String MmJDType ="mmjd";
	public static final  String ANANType ="ananpay";
	public static final  String PowerType ="powerpay";
	public static final  String SentType ="sendpay";

    //public static int Serv = 1;
	public Integer id;
	public String pay_no;
	public String username;
	private Integer level;
	
	public String device_id;
	public String packet_id;
	public String packet_id2;
	public String game_id;
	public String business_no;
	public String channel_no;
	public String server_id;
	public String cp_order_id;
	public String pay_type;
	public String thir_pay_id;
	public Integer amount;
	public Integer order_amount;
	public Integer ratio;
	public String coin_name;
	public Integer state;
	public String date;
	public String time;
    public Integer notisfy;
    
	public String type;
	public String ext;
	public Integer ncount = 0;
	public Integer is_pay_finish;
	 
	public String ver;
	
	public String app_pay_id;
	public String extkey;
	public Integer oldpay;
	
    public String ip;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getExtkey() {
		return extkey;
	}
	public void setExtkey(String extkey) {
		this.extkey = extkey;
	}
	static String mlastKey = "";
	static Integer mOrderIndex=100;
	static  Object xlock = new Object();
	
	
	
	public Integer getOldpay() {
		return oldpay;
	}
	public void setOldpay(Integer oldpay) {
		this.oldpay = oldpay;
	}
	public Integer getIsPayFinish() {
		return is_pay_finish;
	}
	public void setIsPayFinish(Integer is_pay_finish) {
		this.is_pay_finish = is_pay_finish;
	}
	
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public void setExt(String ext){
		this.ext=ext;
	}
	public String getExt( ){
		return this.ext;
	}
	
	 public void setNcount(Integer ncount){
			this.ncount=ncount;
	}
	 
	public Integer getNcount(){
			return this.ncount;
	}
		
	public void setNotisfy(Integer notisfy){
		this.notisfy=notisfy;
	}
	public Integer getNotisfy(){
		return this.notisfy;
	}
	

	public void setType(String type){
		this.type=type;
	}
	public String getType( ){
		return this.type;
	}
	
	public void setTime(String time){
		this.time=time;
	}	
	
	public String getTime(){
		return this.time;
	}		
	public Pay()
	{
		ncount = 0;
	}
	
	public void testdecode(String str,String signstr)
	{
		String content = str;
		String sign = signstr;

		content = new String(Base64.decode(content));

		String mysign=Rsa.getMD5(content+"&key=zty_150");
		if(mysign.equals(sign))//签名正确,做业务逻辑处理
		{
		   JSONObject json = JSONObject.fromObject(content);
		   pay_no=json.getString("pay_no");
		   username=json.getString("username");
		   device_id=json.getString("device_id");
		   server_id=json.getString("server_id");
			packet_id=json.getString("packet_id");
		    game_id=json.getString("game_id");
			cp_order_id=json.getString("cp_order_id");
			pay_type=json.getString("pay_type");
			amount=json.getInt("amount");
			int payStatus = json.getInt("payStatus");
				
		}

	}
	
	public int rsqCallbackUrl2(int payStatus)
	{
		int bret = 0;
		String ret ="";// String.format("pay_no=%s&username=%s&device_id=%s&game_id=%s&server_id=%s&cp_order_id=%s&pay_type=%s&amount=%d&payStatus=%d"
		   //,pay_no,username,device_id,game_id,server_id,cp_order_id,pay_type,amount,0);
		String content = "";
		String sign="";
		String url = null;
		String key="";
		AppDao appdao = new AppDao();
		App appinfo = appdao.getAppRecord(game_id);
		
		////////////////////////////////////////////////
		/*
		 appinfo = new App();
		appinfo.setUrl("http://14.17.122.192:5678/xjpayServer/ztypay/callback");
		appinfo.setMykey("zty_160");
		*/
		/////////////////////////////////////
		
		DebuUtil.log("通知交易");
		/*if(game_id.equals("150"))
		{
			url = "http://120.31.132.93:8956/zjs_op/bwzzty/callback";
			key = "zty_150";
		}
		else if(game_id.equals("151"))
		{
			url = "http://183.61.119.134/addmoney_MM.php";
			key = "zty_151";
		}*/
		if(appinfo != null)
		{
			url = appinfo.getUrl();
			key = appinfo.getMykey();
		}
		
		//content = new String(Base64.decode(content));/////////////////
	    String responseStr="";
	   
	    if(!StringUtil.is_nullString(url))
	    {
	    	JSONObject json =new  JSONObject();
	    	model.Register register = null;
			RegisterDao dao = new RegisterDao();
			register=dao.getRegisterRecord(username); 
			
			 json.put("pay_no",pay_no);
			 
			/*if(game_id.equals("151"))
			 {
			     try {
			    	 json.put("username",username.getBytes("UTF-8"));
			    	 
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 else
			 {
			    json.put("username",username);
			
			 }*/
			 
			// ;
			 json.put("username",username);////"徐海垚"
			 /////////////////////////////////
			 //if(register != null)
			 json.put("user_id",register.getId());
			 ///////////////////////////////
			 //json.put("user_id",134570);/////////////////////////
			 
			 json.put("device_id",device_id);
			 json.put("server_id",server_id);
			 json.put("packet_id",packet_id);
			 json.put("game_id",game_id);
			 json.put("cp_order_id",cp_order_id);
			 json.put("pay_type",pay_type);
			 int payamount = amount;
			 if(pay_type.equals("jcard"))
			 {
				 payamount = amount*80/100;
			 }
			 json.put("amount",payamount);
			 json.put("payStatus",payStatus);
			 ret = json.toString();
			 
			 //////////////////////////////////////
			 //byte [] buf = username.getBytes("UTF-8");
			/// for(byte tem:buf)
			// {
				 
			 //}
			 ///////////////////////////////////////
			 
	    	sign=util.Rsa.getMD5(ret+"&key="+key);
	    	//DebuUtil.log(ret+"&key="+key);
	    	//DebuUtil.log(sign);
			content = Base64.encode(ret.getBytes());
			ret = String.format("content=%s&sign=%s",content,sign);
			DebuUtil.log(ret);
			/////////////////////////////////
			//content="eyJwYXlfbm8iOiIwNTIzMDEwNDI4LTEwMDMiLCJ1c2VybmFtZSI6IuW+kOa1t+WemiIsInVzZXJfaWQiOjE0ODg2MCwiZGV2aWNlX2lkIjoiODYzNzM4MDI5ODE2NTkzIiwic2VydmVyX2lkIjoiNDEwIiwicGFja2V0X2lkIjoiMjgwMTU4MDAyIiwiZ2FtZV9pZCI6IjE1OCIsImNwX29yZGVyX2lkIjoiMTQwNTIzMDEwNDQ3MTI4MTAwMDEwNzQxIiwicGF5X3R5cGUiOiJhbGlwYXkiLCJhbW91bnQiOjEwMDAsInBheVN0YXR1cyI6MH0=";
			 // content="eyJwYXlfbm8iOiIwNTIzMDEwNDI4LTEwMDMiLCJ1c2VybmFtZSI6ItDsuqOIkCIsInVzZXJfaWQiOjE0ODg2MCwiZGV2aWNlX2lkIjoiODYzNzM4MDI5ODE2NTkzIiwic2VydmVyX2lkIjoiNDEwIiwicGFja2V0X2lkIjoiMjgwMTU4MDAyIiwiZ2FtZV9pZCI6IjE1OCIsImNwX29yZGVyX2lkIjoiMTQwNTIzMDEwNDQ3MTI4MTAwMDEwNzQxIiwicGF5X3R5cGUiOiJhbGlwYXkiLCJhbW91bnQiOjEwMDAsInBheVN0YXR1cyI6MH0=";
			content = new String(Base64.decode(content));
			DebuUtil.log("decode="+content);
			///////////////////////
			
			//DebuUtil.log("发送内容："+ret);
			
			try {
				DebuUtil.log("给PC发送通知交易");
				responseStr = HttpUtils.URLPostUTF8(url, ret);//lsl
				if(responseStr != null)
				{
				   DebuUtil.log("CP回应："+responseStr);
				   if(responseStr.equalsIgnoreCase("success"))
				   {
					   DebuUtil.log("通知成功");
					   bret = 1;
					   this.notisfy = 1;
				   }
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
		return bret;
	}
	public int rsqCallbackUrl(int payStatus)
	{
		int bret = 0;
		String ret ="";
		String content = "";
		String sign="";
		String url = "";
		String key="";
		
		if(payStatus != 0)
		{
			
			return 0;
		}
		
		 if(state != 1)
		 {
			 return 0;
		 }
		AppDao appdao = new AppDao();
		App appinfo = appdao.getAppRecord(game_id);
		
		DebuUtil.log("通知交易");
		if(appinfo != null)
		{
			url = appinfo.getUrl();
			key = appinfo.getMykey();
		}
		DebuUtil.log("通知交易地址："+url);
		DebuUtil.log("密匙："+key);
	    String responseStr="";
	   
	    if(!StringUtil.is_nullString(url))
	    {
	    	
	    	//if(StringUtil.is_nullString(username))
	    	//{
	    		//return 0;
	    	//}
	    	JSONObject json =new  JSONObject();
	    	model.Register register = null;
			RegisterDao dao = new RegisterDao();
			register=dao.getRegisterRecord(username); 
			
			json.put("pay_no",pay_no);
			 
			 if(game_id.equals("158"))
			 {
				 json.put("username",URLEncoder.encode(username));
			 }
			 else
			 {
				 json.put("username",username);
			 }
			 /////////////////////////////////
			 if(register != null)
			 {
			   json.put("user_id",register.getId());
			 }
			 else
			 {
				 json.put("user_id",0);
			 }
			 ///////////////////////////////
			 //json.put("user_id",134570);/////////////////////////
			 
			 json.put("device_id",device_id);
			 json.put("server_id",server_id);
			 json.put("packet_id",packet_id);
			 json.put("game_id",game_id);
			 json.put("cp_order_id",cp_order_id);
			 json.put("pay_type",pay_type);
			 json.put("ext",extkey);
			 int payamount = amount;
			 if(pay_type.equals("jcard"))
			 {
				 payamount = amount*80/100;
			 }
			 json.put("amount",payamount);
			 json.put("payStatus",payStatus);
			 ret = json.toString();
			 
	    	sign=util.Rsa.getMD5(ret+"&key="+key);
	    	DebuUtil.log(ret+"&key="+key);
	    	DebuUtil.log(sign);
			content = Base64.encode(ret.getBytes());
			ret = String.format("content=%s&sign=%s",content,sign);
			
			try {
				DebuUtil.log("给PC发送通知交易");
				responseStr = HttpUtils.URLPostUTF8(url, ret);//lsl
				if(responseStr != null)
				{
				   DebuUtil.log("CP回应："+responseStr);
				   if(responseStr.equalsIgnoreCase("success"))
				   {
					   DebuUtil.log("通知成功");
					   bret = 1;
					   this.notisfy = 1;
				   }
				}
				this.ncount ++;
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    else
	    {
	    	 this.ncount = 10;
	    	 bret = 1;
	    }
	    
		return bret;
	}
	
	public int rsqSACallbackUrl()
	{
		int bret = 0;
		String ret ="";
		String content = "";
		String sign="";
		String url = "";
		String key="";
		
		int payStatus = -1;
		
		if(this.state == 1)
		{
			payStatus = 0;
		}
		AppDao appdao = new AppDao();
		App appinfo = appdao.getAppRecord(game_id);
		
		DebuUtil.log("通知交易");
		if(appinfo != null)
		{
			url = appinfo.getUrl();
			key = appinfo.getMykey();
		}
		DebuUtil.log("通知交易地址："+url);
		DebuUtil.log("密匙："+key);
	    String responseStr="";
	   
	    if(!StringUtil.is_nullString(url))
	    {
	    	
	    	//if(StringUtil.is_nullString(username))
	    	//{
	    		//return 0;
	    	//}
	    	JSONObject json =new  JSONObject();
	    	model.Register register = null;
			RegisterDao dao = new RegisterDao();
			register=dao.getRegisterRecord(username); 
			
			json.put("pay_no",pay_no);
			 
			 if(game_id.equals("158"))
			 {
				 json.put("username",URLEncoder.encode(username));
			 }
			 else
			 {
				 json.put("username",username);
			 }
			 /////////////////////////////////
			 if(register != null)
			 {
			   json.put("user_id",register.getId());
			 }
			 else
			 {
				 json.put("user_id",0);
			 }
			 ///////////////////////////////
			 //json.put("user_id",134570);/////////////////////////
			 
			 json.put("device_id",device_id);
			 json.put("server_id",server_id);
			 json.put("packet_id",packet_id);
			 json.put("game_id",game_id);
			 json.put("cp_order_id",cp_order_id);
			 json.put("pay_type",pay_type);
			 json.put("ext",extkey);
			 int payamount = amount;
			 if(pay_type.equals("jcard"))
			 {
				 payamount = amount*80/100;
			 }
			 json.put("amount",payamount);
			 json.put("payStatus",payStatus);
			 ret = json.toString();
			 
	    	sign=util.Rsa.getMD5(ret+"&key="+key);
	    	DebuUtil.log(ret+"&key="+key);
	    	DebuUtil.log(sign);
			content = Base64.encode(ret.getBytes());
			ret = String.format("content=%s&sign=%s",content,sign);
			
			try {
				DebuUtil.log("给PC发送通知交易");
				responseStr = HttpUtils.URLPostUTF8(url, ret);//lsl
				if(responseStr != null)
				{
				   DebuUtil.log("CP回应："+responseStr);
				   if(responseStr.equalsIgnoreCase("success"))
				   {
					   DebuUtil.log("通知成功");
					   bret = 1;
					   this.notisfy = 1;
				   }
				}
				this.ncount ++;
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    else
	    {
	    	 this.ncount = 10;
	    	 bret = 1;
	    }
	    
		return bret;
	}
	
	public static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String key = format.format(date);

		java.util.Random r = new java.util.Random();
		
		int ran = r.nextInt();
		if(ran < 0)
		{
			ran = 0-ran;
		}
		key += ran;
		
		key = key.substring(0, 15);
		//key = "010217153010087";//lsl
		//Log.d(TAG, "outTradeNo: " + key);
		return key;
	}
	
	public static String getOutTradeNoINC() {
		synchronized(xlock)
		{
			SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
			Date date = new Date();
			String key = format.format(date);
			int year = date.getYear()%10;
			key = year+key;
			if(!mlastKey.equals(key))
			{
				mlastKey = key;
				mOrderIndex = 0;
			}
			
			mOrderIndex++;
	   		key += mOrderIndex;
		
			if(key.length()>15)
			{
			   key = key.substring(0, 15);
			}
			//DebuUtil.log("oderid="+key);
			return key;
		}
	}
	public static boolean isFen(String gameid)
    {
    	boolean ret = false;
    	if(gameid.equals("171"))
    	{
    		ret = true;
    	}
    	else if(gameid.equals("172"))
    	{
    		ret = true;
    	}
    	else if(gameid.equals("199"))
    	{
    		ret = true;
    	}
    	else
    	{
	    	AppDao appdao = new AppDao();
			App appinfo = appdao.getAppRecord(gameid);
			if(App.isStandAloneGame(appinfo))
			{			
				ret = true;			
			}
    	}
    	return ret;
    }
	 
	public Pay(JSONObject json)
	{
/*		device_id=json.getString("device_id");
		packet_id=json.getString("packet_id");
		game_id=json.getString("game_id");
		server_id=json.getString("game_server_id");
		cp_order_id=json.getString("cp_order_id");
		username=json.getString("user_id");*/
		
		device_id=JsonUtil.getString(json, "device_id");
		packet_id=JsonUtil.getString(json, "packet_id");
		game_id=JsonUtil.getString(json, "game_id");
		server_id=JsonUtil.getString(json, "game_server_id");
		cp_order_id=JsonUtil.getString(json, "cp_order_id");
		username=JsonUtil.getString(json, "user_id");
		
		level = JsonUtil.getInt(json, "level");
		
		pay_type = JsonUtil.getString(json, "payway");
		app_pay_id = JsonUtil.getString(json, "app_pay_id");
		extkey = JsonUtil.getString(json, "ext");
		
		if(isFen(game_id))
		{
			amount=JsonUtil.getInt(json,"total_fee");
		}
		else
		{
			amount=JsonUtil.getInt(json,"total_fee")*100;
		}
		order_amount=StringUtil.getInt(json,"order_amount")*100;
		
		/*if(pay_type.equals("mzpay")){
			amount=json.getInt("total_fee")/10*100;
		}*/
		/*boolean fen = isFen(game_id);
		if(game_id.equals("172"))
    	{
			DebuUtil.log3("amount="+amount);
    	}*/
		ratio=JsonUtil.getInt(json,"ratio");
		coin_name=StringUtil.getString(json,"coin_name");
		//if(ConstValue.bNew == 1)
		{
			String payname=StringUtil.getString(json,"payname");
			if(!StringUtil.is_nullString(payname))
			{
				coin_name = payname;
			}
		}
		
		date = DateUtil.getDate();
		time = DateUtil.getTime();
		//pay_no = getOutTradeNo();
		pay_no = getOutTradeNoINC();
		thir_pay_id = StringUtil.getString(json,"tradeID");
		type = "";
		state = 0;
		String statestr = StringUtil.getString(json,"state");
		ver = StringUtil.getString(json,"ver");
		if(!StringUtil.is_nullString(ver) && ver.compareTo("2.01.1") >= 0)
		{
			
		}
		else
		{
			order_amount = 0;
		}
		if(!StringUtil.is_nullString(statestr))
		{
			state = Integer.valueOf(statestr);
		}
		business_no = "";
		channel_no = "";
		ncount = 0;
		CooperationDao cooperationDao=new CooperationDao();
		Cooperation cooperation = cooperationDao.getRecord(packet_id);
		if(cooperation != null)
		{
			business_no = cooperation.getBusinessNo();
			channel_no = cooperation.getChannelNo();
		}
	}
	
	public String getAppPayId() {
		return app_pay_id;
	}
	public void setAppPayId(String app_pay_id) {
		this.app_pay_id = app_pay_id;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}
	
	
	public void setPayNo(String pay_no){
		this.pay_no=pay_no;
	}
	public String getPayNo(){
		return this.pay_no;
	}	
	
	public void setUsername(String username){
		this.username=username;
	}
	public String getUsername( ){
		return this.username;
	}	
	
	public void setDeviceId(String device_id){
		this.device_id=device_id;
	}
	public String getDeviceId( ){
		return this.device_id;
	}
	
	public void setPacketId(String packet_id){
		this.packet_id=packet_id;
	}
	
	public String getPacketId(){
		return this.packet_id;
	}
	
	public void setPacketId2(String packet_id2){
		this.packet_id2=packet_id2;
	}
	
	public String getPacketId2(){
		return this.packet_id2;
	}
	
	public void setGameId(String game_id){
		this.game_id=game_id;
	}	
	
	public String getGameId(){
		return this.game_id;
	}	

	public void setServerId(String server_id){
		this.server_id=server_id;
	}	
	public String getServerId(){
		return this.server_id;
	}	
	
	public void setCpOrderId(String cp_order_id){
		this.cp_order_id=cp_order_id;
	}	
	public String getCpOrderId(){
		return this.cp_order_id;
	}	
	
	public void setThirPayId(String thir_pay_id){
		this.thir_pay_id=thir_pay_id;
	}	
	public String getThirPayId(){
		return this.thir_pay_id;
	}	

	public void setCoinName(String coin_name){
		this.coin_name=coin_name;
	}	
	public String getCoinName(){
		return this.coin_name;
	}	
	
	
	public void setAmount(Integer amount){
		this.amount=amount;
	}
	public Integer getAmount(){
		return this.amount;
	}
	
	public void setOrderAmount(Integer order_amount){
		this.order_amount=order_amount;
	}
	public Integer getOrderAmount(){
		return this.order_amount;
	}
	
	public void setBusinessNo(String business_no){
		this.business_no=business_no;
	}
	public String getBusinessNo( ){
		return this.business_no;
	}
	public void setChannelNo(String channel_no){
		this.channel_no=channel_no;
	}
	public String getChannelNo( ){
		return this.channel_no;
	}
	
	public void setState(Integer state){
		this.state=state;
	}
	public Integer getState(){
		return this.state;
	}
	            
	public void setRatio(Integer ratio){
		this.ratio=ratio;
	}
	public Integer getRatio(){
		return this.ratio;
	}
	
	public void setPayType(String pay_type){
		this.pay_type=pay_type;
	}	
	public String getPayType(){
		return this.pay_type;
	}	
	
	public void setDate(String date){
		this.date=date;
	}	
	public String getDate(){
		return this.date;
	}	
}
