package model;

public class MmPayData {

	private Integer id;
	private String payName;
	private String payId;
	private String payType;
	private String payAmount;
	private String conpanyName;
	private String payKey;
	private String publicKey;
	private String proId;
	private String md5;
	private String md5dex;
	private String verno;
	private String packageName;
	private String mainActivity;
    private String channel;//移动掌中
    private String payCodeList;
    
  
	private String webPartner;
    private String webKey1;
    private String webKey2;
    private String webPid;
    private String webList;
    private String webUrl;//移动页游
    private String getNum;//获取手机号上行号码
    


	private String unicomId;
    private String unicomUrl;
    private String unBkUrl;
    private String unKey;
    private String unPayName;
    private String unPayList;
    private String unGoodId;
    private String unName; //联通十分科技   、 电信全网
    
    private String tclId;
    private String tclUrl;
    private String tclBkUrl;
    private String tclKey;
    private String tclPayList;
    private String tclName;//电信十分科技
    
    private String boxPayId;
    private String boxPayList;
    private String boxPayName;//联通饭盒支付
    
    private String appPayId;
    private Integer limitMoney; //计费点限额
    private Integer limitUserMoney; //计费点单用户限额
    
    private String type;
    private Integer moneycount;
    private Integer priority;
    
    private String powerId;//动力计费
    private String powerPayCode;//动力计费点
    private String powerList;//动力列表
    private String powerUrl;
    private String powerSonChannel;
    
    private String content;//发送短信
    private String contentTwo;//发送短信#号后
    private String contentNum;

    
    
	public String getPowerUrl() {
		return powerUrl;
	}
	public void setPowerUrl(String powerUrl) {
		this.powerUrl = powerUrl;
	}
	public String getPowerSonChannel() {
		return powerSonChannel;
	}
	public void setPowerSonChannel(String powerSonChannel) {
		this.powerSonChannel = powerSonChannel;
	}
	public String getContentTwo() {
		return contentTwo;
	}
	public void setContentTwo(String contentTwo) {
		this.contentTwo = contentTwo;
	}
	public String getPowerPayCode() {
		return powerPayCode;
	}
	public void setPowerPayCode(String powerPayCode) {
		this.powerPayCode = powerPayCode;
	}
	public String getPowerList() {
		return powerList;
	}
	public void setPowerList(String powerList) {
		this.powerList = powerList;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentNum() {
		return contentNum;
	}
	public void setContentNum(String contentNum) {
		this.contentNum = contentNum;
	}
	public String getPowerId() {
		return powerId;
	}
	public void setPowerId(String powerId) {
		this.powerId = powerId;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getMoneycount() {
		return moneycount;
	}
	public void setMoneycount(Integer moneycount) {
		this.moneycount = moneycount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBoxPayId() {
		return boxPayId;
	}
	public void setBoxPayId(String boxPayId) {
		this.boxPayId = boxPayId;
	}
	public String getBoxPayList() {
		return boxPayList;
	}
	public void setBoxPayList(String boxPayList) {
		this.boxPayList = boxPayList;
	}
	public String getBoxPayName() {
		return boxPayName;
	}
	public void setBoxPayName(String boxPayName) {
		this.boxPayName = boxPayName;
	}
	public String getPayCodeList() {
  		return payCodeList;
  	}
  	public void setPayCodeList(String payCodeList) {
  		this.payCodeList = payCodeList;
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
	public String getGetNum() {
		return getNum;
	}
	public void setGetNum(String getNum) {
		this.getNum = getNum;
	}
    public String getWebPartner() {
		return webPartner;
	}
	public void setWebPartner(String webPartner) {
		this.webPartner = webPartner;
	}
	public String getWebKey1() {
		return webKey1;
	}
	public void setWebKey1(String webKey1) {
		this.webKey1 = webKey1;
	}
	public String getWebKey2() {
		return webKey2;
	}
	public void setWebKey2(String webKey2) {
		this.webKey2 = webKey2;
	}
	public String getWebPid() {
		return webPid;
	}
	public void setWebPid(String webPid) {
		this.webPid = webPid;
	}
	public String getWebList() {
		return webList;
	}
	public void setWebList(String webList) {
		this.webList = webList;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
    public String getAppPayId() {
		return appPayId;
	}
	public void setAppPayId(String appPayId) {
		this.appPayId = appPayId;
	}
	public String getUnicomId() {
		return unicomId;
	}
	public void setUnicomId(String unicomId) {
		this.unicomId = unicomId;
	}
	public String getUnicomUrl() {
		return unicomUrl;
	}
	public void setUnicomUrl(String unicomUrl) {
		this.unicomUrl = unicomUrl;
	}
	public String getUnBkUrl() {
		return unBkUrl;
	}
	public void setUnBkUrl(String unBkUrl) {
		this.unBkUrl = unBkUrl;
	}
	public String getUnKey() {
		return unKey;
	}
	public void setUnKey(String unKey) {
		this.unKey = unKey;
	}
	public String getUnPayName() {
		return unPayName;
	}
	public void setUnPayName(String unPayName) {
		this.unPayName = unPayName;
	}
	public String getUnPayList() {
		return unPayList;
	}
	public void setUnPayList(String unPayList) {
		this.unPayList = unPayList;
	}
	public String getUnGoodId() {
		return unGoodId;
	}
	public void setUnGoodId(String unGoodId) {
		this.unGoodId = unGoodId;
	}
	public String getUnName() {
		return unName;
	}
	public void setUnName(String unName) {
		this.unName = unName;
	}
	public String getTclId() {
		return tclId;
	}
	public void setTclId(String tclId) {
		this.tclId = tclId;
	}
	public String getTclUrl() {
		return tclUrl;
	}
	public void setTclUrl(String tclUrl) {
		this.tclUrl = tclUrl;
	}
	public String getTclBkUrl() {
		return tclBkUrl;
	}
	public void setTclBkUrl(String tclBkUrl) {
		this.tclBkUrl = tclBkUrl;
	}
	public String getTclKey() {
		return tclKey;
	}
	public void setTclKey(String tclKey) {
		this.tclKey = tclKey;
	}
	public String getTclPayList() {
		return tclPayList;
	}
	public void setTclPayList(String tclPayList) {
		this.tclPayList = tclPayList;
	}
	public String getTclName() {
		return tclName;
	}
	public void setTclName(String tclName) {
		this.tclName = tclName;
	}

	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}

	
	public String getMainActivity() {
		return mainActivity;
	}
	public void setMainActivity(String mainActivity) {
		this.mainActivity = mainActivity;
	}
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String getVerno() {
		return verno;
	}
	public void setVerno(String verno) {
		this.verno = verno;
	}
	
	public String getMd5dex() {
		return md5dex;
	}
	public void setMd5dex(String md5dex) {
		this.md5dex = md5dex;
	}
	
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	
	
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	
	
	public String getPayKey() {
		return payKey;
	}
	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}
	public String getConpanyName() {
		return conpanyName;
	}
	public void setConpanyName(String conpanyName) {
		this.conpanyName = conpanyName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPayName() {
		return payName;
	}
	public void setPayName(String payName) {
		this.payName = payName;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	
	
	
}
