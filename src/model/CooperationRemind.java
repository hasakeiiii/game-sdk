package model;


public class CooperationRemind implements java.io.Serializable{

	private static final long serialVersionUID = -6753125254411288014L;

	private String app_no;
	private String channel_no;
	private Integer id;
	private String mm_channel_no;
	//请求指令
	private Integer req_num;
	//付费金额
	private Integer mm_pay;
	//第一个运算符
	private Integer operator_first;
	//第二个运算符
	private Integer operator_second;
	private String packet_id;
	//转换率
	private Integer proportion;
	
	//付费转换率
	private Integer pay_proportion;
	//移动
	public String mm_day_pay_province;//省份单用户日限金额
	public String province;//省份单用户日限金额
	public String mm_day_channel_province;//日限金额
	public String channel_province;//日限金额
	//联通
	public String un_day_pay_province;//省份单用户日限金额
	public String un_province;//省份单用户日限金额
	public String un_day_channel_province;//日限金额
	public String un_channel_province;//日限金额
	//电信
	public String tel_day_pay_province;//省份单用户日限金额
	public String tel_province;//省份单用户日限金额
	public String tel_day_channel_province;//日限金额
	public String tel_channel_province;//日限金额
	
	//屏蔽地区
	public String mm_addr;
	public String un_addr;
	public String tel_addr;
	public String mm_addr_time;
	public String un_addr_time;
	public String tel_addr_time;
	
	
	public String getMmAddrTime() {
		return mm_addr_time;
	}
	public void setMmAddrTime(String mm_addr_time) {
		this.mm_addr_time = mm_addr_time;
	}
	public String getUnAddrTime() {
		return un_addr_time;
	}
	public void setUnAddrTime(String un_addr_time) {
		this.un_addr_time = un_addr_time;
	}
	public String getTelAddrTime() {
		return tel_addr_time;
	}
	public void setTelAddrTime(String tel_addr_time) {
		this.tel_addr_time = tel_addr_time;
	}
	public String getMmAddr() {
		return mm_addr;
	}
	public void setMmAddr(String mm_addr) {
		this.mm_addr = mm_addr;
	}
	public String getUnAddr() {
		return un_addr;
	}
	public void setUnAddr(String un_addr) {
		this.un_addr = un_addr;
	}
	public String getTelAddr() {
		return tel_addr;
	}
	public void setTelAddr(String tel_addr) {
		this.tel_addr = tel_addr;
	}
	public String getUnDayPayProvince() {
		return un_day_pay_province;
	}
	public void setUnDayPayProvince(String un_day_pay_province) {
		this.un_day_pay_province = un_day_pay_province;
	}
	public String getUnProvince() {
		return un_province;
	}
	public void setUnProvince(String un_province) {
		this.un_province = un_province;
	}
	public String getUnDayChannelProvince() {
		return un_day_channel_province;
	}
	public void setUnDayChannelProvince(String un_day_channel_province) {
		this.un_day_channel_province = un_day_channel_province;
	}
	public String getUnChannelProvince() {
		return un_channel_province;
	}
	public void setUnChannelProvince(String un_channel_province) {
		this.un_channel_province = un_channel_province;
	}
	public String getTelDayPayProvince() {
		return tel_day_pay_province;
	}
	public void setTelDayPayProvince(String tel_day_pay_province) {
		this.tel_day_pay_province = tel_day_pay_province;
	}
	public String getTelProvince() {
		return tel_province;
	}
	public void setTelProvince(String tel_province) {
		this.tel_province = tel_province;
	}
	public String getTelDayChannelProvince() {
		return tel_day_channel_province;
	}
	public void setTelDayChannelProvince(String tel_day_channel_province) {
		this.tel_day_channel_province = tel_day_channel_province;
	}
	public String getTelChannelProvince() {
		return tel_channel_province;
	}
	public void setTelChannelProvince(String tel_channel_province) {
		this.tel_channel_province = tel_channel_province;
	}
	public String getAppNo() {
		return app_no;
	}
	public String getChannelNo() {
		return channel_no;
	}
	public Integer getId() {
		return id;
	}
	public String getMmChannelNo() {
		return mm_channel_no;
	}
	public Integer getMmPay() {
		return mm_pay;
	}
	public Integer getOperatorFirst() {
		return operator_first;
	}
	public Integer getOperatorSecond() {
		return operator_second;
	}
	public String getPacketId() {
		return packet_id;
	}
	public Integer getPayProportion() {
		return pay_proportion;
	}
	public Integer getProportion() {
		return proportion;
	}
	public Integer getReqNum() {
		return req_num;
	}
	public void setAppNo(String app_no) {
		this.app_no = app_no;
	}
	public void setChannelNo(String channel_no) {
		this.channel_no = channel_no;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setMmChannelNo(String mm_channel_no) {
		this.mm_channel_no = mm_channel_no;
	}
	public void setMmPay(Integer mm_pay) {
		this.mm_pay = mm_pay;
	}
	public void setOperatorFirst(Integer operator_first) {
		this.operator_first = operator_first;
	}
	public void setOperatorSecond(Integer operator_second) {
		this.operator_second = operator_second;
	}
	public void setPacketId(String packet_id) {
		this.packet_id = packet_id;
	}
	public void setPayProportion(Integer pay_proportion) {
		this.pay_proportion = pay_proportion;
	}
	public void setProportion(Integer proportion) {
		this.proportion = proportion;
	}
	public void setReqNum(Integer req_num) {
		this.req_num = req_num;
	}
	public String getMmDayPayProvince() {
		return mm_day_pay_province;
	}
	public void setMmDayPayProvince(String mm_day_pay_province) {
		this.mm_day_pay_province = mm_day_pay_province;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getMmDayChannelProvince() {
		return mm_day_channel_province;
	}
	public void setMmDayChannelProvince(String mm_day_channel_province) {
		this.mm_day_channel_province = mm_day_channel_province;
	}
	public String getChannelProvince() {
		return channel_province;
	}
	public void setChannelProvince(String channel_province) {
		this.channel_province = channel_province;
	}
	
	
}
