package model;

public class PayTypeCount {
	private Integer id;
	private String no;
	private String name;
	private Integer count;
	private Integer settleCount;
	private String payType;
	
	
	
	public Integer getSettleCount() {
		return settleCount;
	}
	public void setSettleCount(Integer settleCount) {
		this.settleCount = settleCount;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
