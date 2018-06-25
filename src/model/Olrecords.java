package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Olrecords {
	private Integer id;
	private String username;
	private String gameId;
	private String date;
	private String time;
	private String status;
	private String payNo;
	private Integer amount;
	private Integer sendAmount;
	private Integer off;
	private String gsNo;
	private String operator;
	
	public Olrecords(){
		id = 0;
		username = "";
		gameId = "";
		date = "";
		time = "";
		status = "";
		payNo = "";
		amount = 0;
		sendAmount = 0;
		off = 0;
		gsNo = "";
		operator = "";
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPayNo() {
		return payNo;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getOff() {
		return off;
	}
	public void setOff(Integer off) {
		this.off = off;
	}
	public String getGsNo() {
		return gsNo;
	}
	public void setGsNo(String gsNo) {
		this.gsNo = gsNo;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Integer getSendAmount() {
		return sendAmount;
	}
	public void setSendAmount(Integer sendAmount) {
		this.sendAmount = sendAmount;
	}
	
	
	public static String getOlRechargeNo() {
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
		key = "ol"+key;
		
		return key;
	}
}
