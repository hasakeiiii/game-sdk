package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import dao.CooperationDao;
import sdkReq.GetAmountIndex;
import util.DateUtil;
import util.NumberUtil;
import util.StringUtil;
import net.sf.json.JSONObject;


public class Activate implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1763613717486795589L;
	public Integer id;
	public String device_id;
	public String packet_id;
	public String game_id;
	
	public String date;
	public String time;
	public String loginDate="";
	
	public Integer dateo;
	public Integer dates;
	public Integer datem;
	
	public String type;
	public Integer reg;
	public String business_no;
	public String channel_no;
	public String addr;
	public String ver;
	public Integer onlinetime;
	public Integer level;
	
	public static int DATEODay = 1;
	public static int DATESDay = 7;
	public static int DATEMDay = 30;
	
	public void setLevel(Integer level){
		this.level=level;
	}
	public Integer getLevel(){
		return this.level;
	}
	
	public void setOnlinetime(Integer onlinetime){
		this.onlinetime=onlinetime;
	}
	public Integer getOnlinetime(){
		return this.onlinetime;
	}
	
	public boolean is_real_user()
	{
		boolean ret = false;
		if((this.onlinetime >= Register.RealOnLineTime) || (this.level >= Register.RealLevel))
		{
			ret = true;
		}
		return ret;
	}
	
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	public void setLoginDate(String loginDate){
		this.loginDate=loginDate;
	}
	public String getLoginDate( ){
		return this.loginDate;
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
	
	public void setReg(Integer reg){
		this.reg=reg;
	}
	public Integer getReg(){
		return this.reg;
	}
	
	public void setType(String type){
		this.type=type;
	}
	public String getType( ){
		return this.type;
	}
	
	public Activate()
	{
		
	}
	public Activate(JSONObject json)
	{
		device_id=json.getString("device_id");
		packet_id=json.getString("packet_id");
		game_id=json.getString("game_id");
		date = DateUtil.getDate();
		ver = StringUtil.getString(json,"ver");
		time = DateUtil.getTime();
		
		dateo = 0;
		dates = 0;
		datem = 0;
		type = "";
		loginDate = null;
		
		CooperationDao cooperationDao=new CooperationDao();
		Cooperation cooperation = cooperationDao.getRecord(packet_id);
		if(cooperation != null)
		{
			business_no = cooperation.getBusinessNo();
			channel_no = cooperation.getChannelNo();
		}
	}
	
	  public static String getOutTradeNo(int count) {
			SimpleDateFormat format = new SimpleDateFormat("ssmm");//HHddMM
			Date date = new Date();
			String key = format.format(date);

			java.util.Random r = new java.util.Random();
			
			int ran = r.nextInt();
			if(ran < 0)
			{
				ran = 0-ran;
			}
			key = ran+key;
			
			key = key.substring(0, count);
			//key = "010217153010087";//lsl
			//Log.d(TAG, "outTradeNo: " + key);
			return key;
	}
	 public static String getIMEI(String head)//getIMEI
	  {
	     String ret = "";//((TelephonyManager)context.getSystemService("phone")).getDeviceId();	 
	     ret =head+ getOutTradeNo(15-head.length());//9//"863590"
	     return ret;
 }
	public  Activate(String packet_id,String date)
	{
		device_id="86"+NumberUtil.getRandom(0, 9)+StringUtil.getNo(12);//getIMEI("860");
		this.packet_id=packet_id;
		//game_id=json.getString("game_id");
		this.date = date;
		ver = "1.35.1";//StringUtil.getString(json,"ver");
		int minhour = 0;
		int maxhour = 23;
		if(NumberUtil.getRandom(0, 10) < 7)
		{
			minhour = 9;
			maxhour = 22;
		}
		time = NumberUtil.getRandom(minhour, maxhour)+":"+NumberUtil.getRandom(0, 59)+":"+NumberUtil.getRandom(0, 59);//DateUtil.getTime();
		
		dateo = 0;
		dates = 0;
		datem = 0;
		type = "B";
		loginDate = date;
		
		CooperationDao cooperationDao=new CooperationDao();
		Cooperation cooperation = cooperationDao.getRecord(packet_id);
		if(cooperation != null)
		{
			business_no = cooperation.getBusinessNo();
			channel_no = cooperation.getChannelNo();
			game_id = cooperation.getAppNo();
		}
	}
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}
	public void setDateo(Integer dateo){
		this.dateo=dateo;
	}
	public Integer getDateo(){
		return this.dateo;
	}
	public void setDates(Integer dates){
		this.dates=dates;
	}
	public Integer getDates(){
		return this.dates;
	}	
	public void setDatem(Integer datem){
		this.datem=datem;
	}
	public Integer getDatem(){
		return this.datem;
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
	
	public void setGameId(String game_id){
		this.game_id=game_id;
	}	
	
	public String getGameId(){
		return this.game_id;
	}	
	
	public void setDate(String date){
		this.date=date;
	}	
	
	public String getDate(){
		return this.date;
	}	
	
	public void setTime(String time){
		this.time=time;
	}	
	
	public String getTime(){
		return this.time;
	}	
}
