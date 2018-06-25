package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.CooperationDao;

import util.DateUtil;
import net.sf.json.JSONObject;

public class Mzcharge implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3087545695888656911L;
	public Integer id;
	//userid
	private String username;
	
	private Integer amount;
	private Integer mzAmount;
	

	private Integer ratio;
	private String chargeWay;
	
	private String payNo;
	private String businessNo;
	private String channelNo;
	
	private String packetId;
	private String packetId2;
	private String thirPayId;
	private String deviceId;
	private String gameId;
	
	private String date;
	private String time;
	private Integer status;
	private Integer send;
	private String ext;
	public Integer ncount = 0;
	
	
	public Integer getMzAmount() {
		return mzAmount;
	}
	public void setMzAmount(Integer mzAmount) {
		this.mzAmount = mzAmount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getNcount() {
		return ncount;
	}
	public void setNcount(Integer ncount) {
		this.ncount = ncount;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
	public String getThirPayId() {
		return thirPayId;
	}
	public void setThirPayId(String thirPayId) {
		this.thirPayId = thirPayId;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSend() {
		return send;
	}
	public void setSend(Integer send) {
		this.send = send;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getRatio() {
		return ratio;
	}
	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}
	public String getChargeWay() {
		return chargeWay;
	}
	public void setChargeWay(String chargeWay) {
		this.chargeWay = chargeWay;
	}
	public String getPayNo() {
		return payNo;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getPacketId() {
		return packetId;
	}
	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}
	public String getPacketId2() {
		return packetId2;
	}
	public void setPacketId2(String packetId2) {
		this.packetId2 = packetId2;
	}
	
	public Mzcharge(){
		
	}
	public Mzcharge(JSONObject json){
		username = json.getString("username");
		ratio = json.getInt("ratio");
		chargeWay = json.getString("chargeWay");
		gameId = json.getString("gameId");
		payNo = "mz"+Pay.getOutTradeNoINC();//getRechargeNo();
		if(isFen(gameId)){
			amount=json.getInt("amount");
		}else{
			amount=json.getInt("amount")*100;
		}
		
		mzAmount = 0;
		packetId = json.getString("packetId");
		deviceId = json.getString("deviceId");
		
		if (chargeWay.equals("yeepay")) {
			thirPayId = payNo;
		}else{
			thirPayId = json.getString("thirPayId");
		}
		businessNo = "";
		channelNo = "";
		
		
		
		date = DateUtil.getDate();
		time = DateUtil.getTime();
		status = 0;
		send = 0;
		ext = "";	
	}
	
	public Mzcharge(Pay pay){
		username = pay.getUsername();
		ratio = pay.getRatio();
		chargeWay = pay.getPayType();
		gameId = pay.getGameId();
		payNo = pay.getPayNo();//"mz"+Pay.getOutTradeNoINC();
		amount = pay.getAmount();
		
		mzAmount = pay.getAmount();
		packetId = pay.getPacketId();
		deviceId = pay.getDeviceId();
		
		thirPayId = pay.getThirPayId();
		businessNo = pay.getBusinessNo();
		channelNo = pay.getChannelNo();
		
		date = DateUtil.getDate();
		time = DateUtil.getTime();
		status = pay.getState();
		send = 0;
		ext = "";	
	}
	

	public void initByPc(JSONObject json){
		username = json.getString("username");
		chargeWay = json.getString("chargeWay");
		gameId = json.getString("gameId");
		payNo = "mz" + Pay.getOutTradeNoINC();
		amount = json.getInt("amount")*100;
		
		ratio = 1;
		//amount = (int) (json.getDouble("amount") * 100);
		mzAmount = 0;
		
		packetId = json.getString("packetId");
		
		businessNo = "";
		channelNo = "";
		
		
		date = DateUtil.getDate();
		time = DateUtil.getTime();
		status = 0;
		send = 0;
		ext = "";
	}

	public Mzcharge(JSONObject json,String plat){
		username = json.getString("username");
		chargeWay = json.getString("chargeWay");
		
		payNo = "mz" + Pay.getOutTradeNoINC();
		//TODO
		//amount = json.getInt("amount")*100;
		
		
		amount = (int) (json.getDouble("amount") * 100);
		mzAmount = 0;
		
		packetId = json.getString("packetId");
		
		businessNo = "";
		channelNo = "";
		
		
		date = DateUtil.getDate();
		time = DateUtil.getTime();
		status = 0;
		send = 0;
		ext = "";
	}

	/**
	 * 充值订单号
	 * @return
	 */
	public static String getRechargeNo() {
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
		key = "mz"+key;
		
		return key;
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
    	return ret;
    }
	
}
