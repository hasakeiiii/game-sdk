package model;


public class CpSettle implements java.io.Serializable{


	/**
	 * cp扣量表
	 * 
	 */
	private static final long serialVersionUID = -2040420780977305004L;
	
	public Integer id;
	public String game_id;
	public String BusinessNo;
	public String channel;
	public String packet_id;
	public String date;
	
	public Integer pay;
	public Integer settlePay;
	
	public Integer activateNum;
	public Integer settleActivateNum;
	
	public Integer loginNum;
	public Integer settleLoginNum;
	
	public Integer day1Pay;
	public Integer settleDay1Pay;

	public Integer day7Pay;
	public Integer settleDay7Pay;
	
	public Integer dayAllPayAcount;
	public Integer dayAllAcount;
	public Integer feeNum;
	public Integer reqOkNum;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getDayAllAcount() {
		return dayAllAcount;
	}
	public void setDayAllAcount(Integer dayAllAcount) {
		this.dayAllAcount = dayAllAcount;
	}
	public Integer getDayAllPayAcount() {
		return dayAllPayAcount;
	}
	public void setDayAllPayAcount(Integer dayAllPayAcount) {
		this.dayAllPayAcount = dayAllPayAcount;
	}
	public Integer getFeeNum() {
		return feeNum;
	}
	public void setFeeNum(Integer feeNum) {
		this.feeNum = feeNum;
	}
	public Integer getReqOkNum() {
		return reqOkNum;
	}
	public void setReqOkNum(Integer reqOkNum) {
		this.reqOkNum = reqOkNum;
	}
	public String getGameId() {
		return game_id;
	}
	public void setGameId(String game_id) {
		this.game_id = game_id;
	}
	public String getBusinessNo() {
		return BusinessNo;
	}
	public void setBusinessNo(String businessNo) {
		BusinessNo = businessNo;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getPacketId() {
		return packet_id;
	}
	public void setPacketId(String packet_id) {
		this.packet_id = packet_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getPay() {
		return pay;
	}
	public void setPay(Integer pay) {
		this.pay = pay;
	}
	public Integer getSettlePay() {
		return settlePay;
	}
	public void setSettlePay(Integer settlePay) {
		this.settlePay = settlePay;
	}
	public Integer getActivateNum() {
		return activateNum;
	}
	public void setActivateNum(Integer activateNum) {
		this.activateNum = activateNum;
	}
	public Integer getSettleActivateNum() {
		return settleActivateNum;
	}
	public void setSettleActivateNum(Integer settleActivateNum) {
		this.settleActivateNum = settleActivateNum;
	}
	public Integer getLoginNum() {
		return loginNum;
	}
	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}
	public Integer getSettleLoginNum() {
		return settleLoginNum;
	}
	public void setSettleLoginNum(Integer settleLoginNum) {
		this.settleLoginNum = settleLoginNum;
	}
	public Integer getDay1Pay() {
		return day1Pay;
	}
	public void setDay1Pay(Integer day1Pay) {
		this.day1Pay = day1Pay;
	}
	public Integer getSettleDay1Pay() {
		return settleDay1Pay;
	}
	public void setSettleDay1Pay(Integer settleDay1Pay) {
		this.settleDay1Pay = settleDay1Pay;
	}
	public Integer getDay7Pay() {
		return day7Pay;
	}
	public void setDay7Pay(Integer day7Pay) {
		this.day7Pay = day7Pay;
	}
	public Integer getSettleDay7Pay() {
		return settleDay7Pay;
	}
	public void setSettleDay7Pay(Integer settleDay7Pay) {
		this.settleDay7Pay = settleDay7Pay;
	}
	
}
