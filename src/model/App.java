package model;

import util.StringUtil;

public class App implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8156453662406640377L;
	public Integer id;
	public String no;
	public String name;
	public String cp_no;
	public String tel;
	public String QQ;
	public String url;
    public String mykey;
    
	public String discontent;//登陆跑马灯
	public String discontent2;//支付跑马灯
	public String disurl;//登陆公告
	public String noturl;//支付公告
	public String exiturl;//退出公告
	public String payMark;
	public String payUrl;
	public String gameType;//新增游戏类型，不能为空
	public String pay_urlb;//计费回调
	public String mm_company;//公司
	public Integer off;//专属币折扣
	public String paywaysign;
	private Integer yeediscount;//易宝折扣
	private Integer cpcount;//CP扣量
	private Integer cpcpcount;//CP自己扣量
	
	
	public static String OFF_LINE="off_line";
	public static String ON_LINE="on_line";
	public static String MM_ON_LINE="mm_on_line";
	
	public static String MZYW="mzyw";
	public static String MZHY="mzhy";
	public static String ZTY="zty";
	public static String JY="jy";
	public static String CQ="cq";
	public static String GY="gy";
	public static String HLX="hlx";
	public static String QT="qt";
    
	public static boolean isStandAloneGame(App app)
	{
		boolean ret = false;
		if(app != null)
		{
			if(!StringUtil.is_nullString(app.gameType))
			{
				if(app.gameType.equals(app.OFF_LINE)||app.gameType.equals("1")||app.gameType.equals("2"))
				{
					ret = true;
				}
			}
		}
		return ret;
	}
	
	

	public Integer getCpcpcount() {
		return cpcpcount;
	}



	public void setCpcpcount(Integer cpcpcount) {
		this.cpcpcount = cpcpcount;
	}



	public Integer getCpcount() {
		return cpcount;
	}
	public void setCpcount(Integer cpcount) {
		this.cpcount = cpcount;
	}



	public Integer getYeediscount() {
		return yeediscount;
	}
	public void setYeediscount(Integer yeediscount) {
		this.yeediscount = yeediscount;
	}
	public String getPaywaysign() {
		return paywaysign;
	}
	public void setPaywaysign(String paywaysign) {
		this.paywaysign = paywaysign;
	}



	public Integer getOff() {
		return off;
	}

	public void setOff(Integer off) {
		this.off = off;
	}
	public String getMmCompany() {
		return mm_company;
	}
	public void setMmCompany(String mm_company) {
		this.mm_company = mm_company;
	}
	
	public String getPayUrlb() {
		return pay_urlb;
	}
	public void setPayUrlb(String pay_urlb) {
		this.pay_urlb = pay_urlb;
	}
	public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	public void setPayUrl(String payUrl){
		this.payUrl = payUrl;
	}
	public String getPayUrl(){
		return this.payUrl;
	}
	
	public void setPayMark(String payMark){
		this.payMark = payMark;
	}
	public String getPayMark(){
		return this.payMark;
	}
	
	public void setExiturl(String exiturl){
		this.exiturl = exiturl;
	}
	public String getExiturl(){
		return this.exiturl;
	}
	
	public void setNoturl(String noturl){
		this.noturl = noturl;
	}
	public String getNoturl(){
		return this.noturl;
	}
	
	public void setDiscontent2(String discontent2){
		this.discontent2=discontent2;
	}
	public String getDiscontent2( ){
		return this.discontent2;
	}
	
	public void setDisurl(String disurl){
		this.disurl=disurl;
	}
	public String getDisurl( ){
		return this.disurl;
	}
	
	public void setDiscontent(String discontent){
		this.discontent=discontent;
	}
	public String getDiscontent( ){
		return this.discontent;
	}
	
	public App()
	{
		tel = "";
		QQ = "";
		url = "";
		mykey = "";
		discontent = "";
		discontent2 = "";
		disurl = "";
		noturl = "";
	}
	
	public void setTel(String tel){
		this.tel=tel;
	}
	public String getTel( ){
		return this.tel;
	}
	public void setQq(String QQ){
		this.QQ=QQ;
	}
	public String getQq( ){
		return this.QQ;
	}
	public void setUrl(String url){
		this.url=url;
	}
	public String getUrl( ){
		return this.url;
	}
	public void setMykey(String key){
		this.mykey=key;
	}
	public String getMykey( ){
		return this.mykey;
	}		
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}	
	
	public void setNo(String no){
		this.no=no;
	}
	public String getNo( ){
		return this.no;
	}

	public void setName(String name){
		this.name=name;
	}
	public String getName( ){
		return this.name;
	}	

	public void setCpNo(String cp_no){
		this.cp_no=cp_no;
	}
	public String getCpNo( ){
		return this.cp_no;
	}	
}
