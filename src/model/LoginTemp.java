package model;

import net.sf.json.JSONObject;
import util.DateUtil;
import util.NumberUtil;
import util.StringUtil;
import dao.CooperationDao;

public class LoginTemp implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7574426727288875166L;
	public Integer id;
	public String username;
	public String device_id;
	public String packet_id;
	public String game_id;
	public String business_no;
	public String channel_no;
	public String pass;
	public String date;
	public String time;

	public String ip;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
	
	public void setTime(String time){
		this.time=time;
	}	
	
	public String getTime(){
		return this.time;
	}	
	
	public LoginTemp()
	{
		
	}
	
	public void setCurPacketId(String packet_id)
	{
		this.packet_id = packet_id;
		CooperationDao cooperationDao=new CooperationDao();
		Cooperation cooperation = cooperationDao.getRecord(packet_id);
		if(cooperation != null)
		{
			business_no = cooperation.getBusinessNo();
			channel_no = cooperation.getChannelNo();
		}
	}
	public LoginTemp(JSONObject json)
	{
		username=json.getString("login_account");
		device_id=json.getString("device_id");
		packet_id=json.getString("packet_id");
		game_id=json.getString("game_id");
		pass=json.getString("password");
		date = DateUtil.getDate();
		time = DateUtil.getTime();
		setCurPacketId(packet_id);
	}
	
	public LoginTemp(String packet_id,String username,String device_id,String date,String time)
	{
		this.username=username;
		this.device_id=device_id;
		this.packet_id=packet_id;
		//game_id=json.getString("game_id");
		//pass=json.getString("password");
		this.date = date;
		if(StringUtil.is_nullString(time))
		{
		   this.time = NumberUtil.getRandom(0, 23)+":"+NumberUtil.getRandom(0, 59)+":"+NumberUtil.getRandom(0, 59);
		}
		else
		{
			this.time = time;
		}
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

	public void setPass(String pass){
		this.pass=pass;
	}
	public String getPass( ){
		return this.pass;
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
	

}
