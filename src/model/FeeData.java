package model;

import util.StringUtil;
import dao.CooperationDao;
/**
 * 计费点
 */
public class FeeData implements java.io.Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7339357202206878651L;
	public Integer id;
	public String game_id;
	public String BusinessNo;
	public String channel_no;
	public String packet_id;
	
	public String fee_name;
	public Integer amount;
	
	//订单次数
	public Integer order_timers;
	public Integer game_fee_timers;
	

	//成功次数
	public Integer fee_timers;
	public String date;

	Object xlockdata = new Object();
	
	public FeeData()
	{
		
		game_id = "";
		BusinessNo = "";
		channel_no = "";
		packet_id = "";
		fee_name = "";
		amount = 0;
		order_timers = 0;
		fee_timers = 0;
	}
	
	
	public void clearData()
	{
		game_id = null;
		BusinessNo = null;
		channel_no = null;
		packet_id = null;
		fee_name = null;
		amount = null;
		order_timers = null;
	}
	
	public void amountInc(int count)
	{
		synchronized(xlockdata)
		{
			if(this.amount == null)
			{
				this.amount = 0;
			}
			this.amount += count;
		}
	}	
	
	public void orderTimersInc(int count)
	{
		synchronized(xlockdata)
		{
			if(order_timers == null)
			{
				order_timers = 0;
			}
			order_timers += count;
		}
	}	
	
	public void feeTimersInc(int count)
	{
		synchronized(xlockdata)
		{
			if(fee_timers == null)
			{
				fee_timers = 0;
			}
			fee_timers += count;
		}
	}	
	
	public void setChannelNo(String channel_no){
		this.channel_no=channel_no;
	}
	public String getChannelNo( ){
		return this.channel_no;
	}
	
	public void setDate(String date){
		this.date=date;
	}	
	
	public String getDate(){
		return this.date;
	}	
	
	
	public void setAcount(Integer amount ){
		 this.amount = amount;
	}	
	
	public Integer getAcount( ){
		 return this.amount;
	}	
	
	public void  setOrderTimers(Integer order_timers ){
		 this.order_timers = order_timers;
	}
	
	public Integer getOrderTimers( ){
		return this.order_timers;
	}
	
	public String getFeeName() {
		return fee_name;
	}
	public void setFeeName(String fee_name) {
		this.fee_name = fee_name;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}	
	public void setPacketId(String packet_id){		
			this.packet_id=packet_id;		
	}
	public String getPacketId( ){
		return this.packet_id;
	}

	
	public void setBusinessNo(String BusinessNo){
		this.BusinessNo=BusinessNo;
	}
	public String getBusinessNo( ){
		return this.BusinessNo;
	}
	
	public void setGameId(String game_id){
		this.game_id=game_id;
	}
	public String getGameId( ){
		return this.game_id;
	}
	
	public Integer getAmount() {
		return amount;
	}


	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getGameFeeTimers() {
		return game_fee_timers;
	}


	public Integer getFeeTimers() {
		return fee_timers;
	}


	public void setFeeTimers(Integer fee_timers) {
		this.fee_timers = fee_timers;
	}


	public void setGameFeeTimers(Integer game_fee_timers) {
		this.game_fee_timers = game_fee_timers;
	}
}
