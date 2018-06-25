package model;


public class BusinessProfit implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3170548659695530967L;
	public Integer id;
	public String date;
	public Integer mobilePay;
	public Integer telePay;
	public Integer unicomPay;
	public Integer payNum;
	public Integer payPer;
	public Integer cpsCount;
	public Integer cpaCount;
	public Integer activate;
	public Integer sumPay;
	public Integer profit;
	public Integer install;
	
	
	public Integer getInstall() {
		return install;
	}
	public void setInstall(Integer install) {
		this.install = install;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMobilePay() {
		return mobilePay;
	}
	public void setMobilePay(Integer mobilePay) {
		this.mobilePay = mobilePay;
	}
	public Integer getTelePay() {
		return telePay;
	}
	public void setTelePay(Integer telePay) {
		this.telePay = telePay;
	}

	public Integer getUnicomPay() {
		return unicomPay;
	}
	public void setUnicomPay(Integer unicomPay) {
		this.unicomPay = unicomPay;
	}
	public Integer getPayNum() {
		return payNum;
	}
	public void setPayNum(Integer payNum) {
		this.payNum = payNum;
	}
	public Integer getPayPer() {
		return payPer;
	}
	public void setPayPer(Integer payPer) {
		this.payPer = payPer;
	}
	public Integer getCpsCount() {
		return cpsCount;
	}
	public void setCpsCount(Integer cpsCount) {
		this.cpsCount = cpsCount;
	}
	public Integer getCpaCount() {
		return cpaCount;
	}
	public void setCpaCount(Integer cpaCount) {
		this.cpaCount = cpaCount;
	}
	public Integer getActivate() {
		return activate;
	}
	public void setActivate(Integer activate) {
		this.activate = activate;
	}
	public Integer getSumPay() {
		return sumPay;
	}
	public void setSumPay(Integer sumPay) {
		this.sumPay = sumPay;
	}
	public Integer getProfit() {
		return profit;
	}
	public void setProfit(Integer profit) {
		this.profit = profit;
	}
	

	
	
}
