package model;

public class PayCompany {
	private Integer id;
	private String no;
	private String name;
    private String shieldRegion;//屏蔽地区
    private String shieldTime;//屏蔽时间
    private Integer limitMoney; //计费点限额
    private Integer limitUserMoney; //计费点单用户限额
    private String test;
    
    
    
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public Integer getLimitMoney() {
		return limitMoney;
	}
	public void setLimitMoney(Integer limitMoney) {
		this.limitMoney = limitMoney;
	}
	public Integer getLimitUserMoney() {
		return limitUserMoney;
	}
	public void setLimitUserMoney(Integer limitUserMoney) {
		this.limitUserMoney = limitUserMoney;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShieldRegion() {
		return shieldRegion;
	}
	public void setShieldRegion(String shieldRegion) {
		this.shieldRegion = shieldRegion;
	}
	public String getShieldTime() {
		return shieldTime;
	}
	public void setShieldTime(String shieldTime) {
		this.shieldTime = shieldTime;
	}
	
    
}
