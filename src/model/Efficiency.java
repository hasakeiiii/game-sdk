package model;
/**
 * 计费点效率统计表，每两个小时统计一次，并按照效率高低顺序排序；
 * 新计费点未满50次计费的优先排最前
 * @author linilq
 *
 */
public class Efficiency {
	/**
	 * 计费编号
	 */
	private String payId;
	/**
	 * 计费点
	 */
	private String payItem;
	/**
	 * 金额
	 */
	private String amount;
	/**
	 * 日限额
	 */
	private String limitMoney;
	/**
	 * 屏蔽地区
	 */
	private String sheildAddr;
	private String date;
	private String time;
	/**
	 * 效率
	 */
	private String efficiency;
	/**
	 * 支付类型
	 */
	private String payType;
	/**
	 * 状态：0可用；1新计费点；-1超限额不可用
	 */
	private String state;
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getPayItem() {
		return payItem;
	}
	public void setPayItem(String payItem) {
		this.payItem = payItem;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getLimitMoney() {
		return limitMoney;
	}
	public void setLimitMoney(String limitMoney) {
		this.limitMoney = limitMoney;
	}
	public String getSheildAddr() {
		return sheildAddr;
	}
	public void setSheildAddr(String sheildAddr) {
		this.sheildAddr = sheildAddr;
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
	public String getEfficiency() {
		return efficiency;
	}
	public void setEfficiency(String efficiency) {
		this.efficiency = efficiency;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Efficiency [payId=" + payId + ", payItem=" + payItem
				+ ", amount=" + amount + ", limitMoney=" + limitMoney
				+ ", sheildAddr=" + sheildAddr + ", date=" + date + ", time="
				+ time + ", efficiency=" + efficiency + ", payType=" + payType
				+ ", state=" + state + "]";
	}
	
	
	
}
