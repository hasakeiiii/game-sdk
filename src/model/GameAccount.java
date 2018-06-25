package model;

import net.sf.json.JSONObject;
import util.DateUtil;

public class GameAccount implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5671039157858029635L;
	public Integer id;
	public String username;
	public String game_id;
	public Integer onlinetime;
	public Integer level;
	
	public void setLevel(Integer level){
		this.level=level;
	}
	public Integer getLevel(){
		return this.level;
	}
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}
	
	public void setOnlinetime(Integer onlinetime){
		this.onlinetime=onlinetime;
	}
	public Integer getOnlinetime(){
		return this.onlinetime;
	}
	
	public void setUsername(String username){
		this.username=username;
	}
	public String getUsername(){
		return this.username;
	}
	
	
	public void setGameId(String game_id){
		this.game_id=game_id;
	}	
	
	public String getGameId(){
		return this.game_id;
	}	
}
