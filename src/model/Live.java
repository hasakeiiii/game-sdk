package model;

import net.sf.json.JSONObject;
import util.DateUtil;

public class Live implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -301093509754400316L;

	public Integer id;
	public String device_id;
	public String packet_id;
	public String game_id;
	public String date;
	public String time;

	public void setTime(String time){
		this.time=time;
	}	
	
	public String getTime(){
		return this.time;
	}	
	
	public Live()
	{
		
	}
	
	public Live(JSONObject json)
	{
		device_id=json.getString("device_id");
		packet_id=json.getString("packet_id");
		game_id=json.getString("game_id");
		date = DateUtil.getDate();
		time = DateUtil.getTime();
	}
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
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
