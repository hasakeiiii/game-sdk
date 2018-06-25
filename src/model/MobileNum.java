package model;


import util.DateUtil;
import net.sf.json.JSONObject;


public class MobileNum implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2485746413219117796L;
	public Integer id;
	public String num;
	public String content;
	
	
	public String getNum() {
		return num;
	}


	public void setNum(String num) {
		this.num = num;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}	
	
	
}
