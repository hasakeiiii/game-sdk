package model;

public class AppPay {
	private Integer id;
	private String no;
	private String name;
	private String appPayId;
	
	public String company;//公司
    private Integer limitMoney; //计费点限额
    private Integer limitUserMoney; //计费点单用户限额
    private String provinceMoney;//分省限额
    private String province;//分省省份
    private String shieldRegion;//屏蔽地区
    private String shieldTime;//屏蔽时间
    private Integer moneycount;//百分比
    private Integer priority;//优先级
    private String text;

	public static String MZYW="mzyw";
	public static String MZHY="mzhy";
	public static String ZTY="zty";
	public static String JY="jy";
	public static String CQ="cq";
	public static String GY="gy";
	public static String HLX="hlx";
	public static String QT="qt";
	
	public static String QSZD="qszd";
	public static String MYKJ="mykj";
	public static String LST="lst";
	public static String KTX="KTX";
	public static String YSSJ="yssj";
	
	public static String LB="lb";
	public static String WG="wg";
	public static String BXL="bxl";
	public static String YY="yy";
	
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getMoneycount() {
		return moneycount;
	}
	public void setMoneycount(Integer moneycount) {
		this.moneycount = moneycount;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
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
	
	public String getProvinceMoney() {
		return provinceMoney;
	}
	public void setProvinceMoney(String provinceMoney) {
		this.provinceMoney = provinceMoney;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAppPayId() {
		return appPayId;
	}
	public void setAppPayId(String appPayId) {
		this.appPayId = appPayId;
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
