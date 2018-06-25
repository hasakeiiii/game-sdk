package model;

import java.util.Date;

import dao.CooperationDao;
import net.sf.json.JSONObject;
import util.DateUtil;
import util.DebuUtil;
import util.NumberUtil;
import util.StringUtil;

public class Register implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4856376710292391524L;
	public static int RealOnLineTime = 300;
	public static int RealLevel = 10;

	public Integer id;
	public String username;
	public String pass;
	public String device_id;
	public String packet_id;
	public String game_id;
	public String date;
	public String time;

	public String type;
	public Integer onlinetime;
	public Integer level;
	public Integer valid;

	public String business_no;
	public String channel_no;

	private Integer account_state;// 账号状态
	private Integer locked_time;// 上锁时间
	private Integer passfail_times;// 验证失败次数

	private String phoneNum;// 绑定号码2014-12-15
	private Integer accountBalance;

	public Integer getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Integer accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phone_num) {
		this.phoneNum = phone_num;
	}

	public static int LOCKED = 1;
	public static int UNLOCK = 0;

	

	public Integer getAccountState() {
		return account_state;
	}

	public void setAccountState(Integer account_state) {
		this.account_state = account_state;
	}

	public Integer getLockedTime() {
		return locked_time;
	}

	public void setLockedTime(Integer locked_time) {
		this.locked_time = locked_time;
	}

	public Integer getPassfailTimes() {
		return passfail_times;
	}

	public void setPassfailTimes(Integer passfail_times) {
		this.passfail_times = passfail_times;
	}

	public void setBusinessNo(String business_no) {
		this.business_no = business_no;
	}

	public String getBusinessNo() {
		return this.business_no;
	}

	public void setChannelNo(String channel_no) {
		this.channel_no = channel_no;
	}

	public String getChannelNo() {
		return this.channel_no;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getValid() {
		return this.valid;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return this.time;
	}

	public Register() {

	}

	public Register(JSONObject json) {
		username = json.getString("login_account");
		device_id = json.getString("device_id");
		packet_id = json.getString("packet_id");
		game_id = json.getString("game_id");
		pass = json.getString("password");
		date = DateUtil.getDate();
		time = DateUtil.getTime();
		type = "";
		onlinetime = 0;
		level = 0;

		CooperationDao cooperationDao = new CooperationDao();
		Cooperation cooperation = cooperationDao.getRecord(packet_id);
		if (cooperation != null) {
			business_no = cooperation.getBusinessNo();
			channel_no = cooperation.getChannelNo();
		}
	}
	
	public static String addTime(String datestr,String time)
	{
		String ret = "";
		Date date =  DateUtil.strToDate(datestr+" "+time);
		date = DateUtil.addDate("mm", NumberUtil.getRandom(0, 2), date);
		date = DateUtil.addDate("ss", NumberUtil.getRandom(5, 59), date);
		ret = DateUtil.getTime(date);//DebuUtil.log("time="+DateUtil.getTime(date));
		return ret;
	}
	
	public static String addDay(String datestr,int day)
	{
		String ret = "";
		Date date =  DateUtil.strToDate(datestr);
		date = DateUtil.addDate("dd", day, date);
		//date = DateUtil.addDate("ss", NumberUtil.getRandom(5, 59), date);
		
		long days = DateUtil.getDayDiff(DateUtil.getDate(date),util.DateUtil.getDate());
		//DebuUtil.log("days="+days);
		if(days < 0)
		{
			date = new Date();
		}
		
		ret = DateUtil.getDate(date);//DebuUtil.log("time="+DateUtil.getTime(date));
		return ret;
	}
	
	public static String reduseTime(String datestr,String time)
	{
		String ret = "";
		Date date =  DateUtil.strToDate(datestr+" "+time);
		date = DateUtil.addDate("mm", -NumberUtil.getRandom(3, 5), date);
		date = DateUtil.addDate("ss", -NumberUtil.getRandom(5, 59), date);
		ret = DateUtil.getTime(date);//DebuUtil.log("time="+DateUtil.getTime(date));
		return ret;
	}
	
	public Register(String packet_id,String device_id,String date,String time) {
		username = StringUtil.getNo(8);
		pass =  StringUtil.getNo(6);
		
		this.device_id = device_id;
		this.packet_id = packet_id;
		//game_id = json.getString("game_id");
		
		this.date = date;
		this.time = addTime(date,time);//NumberUtil.getRandom(0, 23)+":"+NumberUtil.getRandom(0, 59)+":"+NumberUtil.getRandom(0, 59);//DateUtil.getTime();
		type = "B";
		onlinetime = 0;
		level = 0;
        
		CooperationDao cooperationDao = new CooperationDao();
		Cooperation cooperation = cooperationDao.getRecord(packet_id);
		if (cooperation != null) {
			business_no = cooperation.getBusinessNo();
			channel_no = cooperation.getChannelNo();
			game_id = cooperation.getAppNo();
		}
	}

	public Register(JSONObject json, int papa) {
		username = json.getString("device_id");
		device_id = json.getString("device_id");
		packet_id = json.getString("packet_id");
		game_id = json.getString("game_id");
		pass = "0";
		date = DateUtil.getDate();
		time = DateUtil.getTime();
		type = "";
		onlinetime = json.getInt("adff2");
		level = json.getInt("adff3");

		CooperationDao cooperationDao = new CooperationDao();
		Cooperation cooperation = cooperationDao.getRecord(packet_id);
		if (cooperation != null) {
			business_no = cooperation.getBusinessNo();
			channel_no = cooperation.getChannelNo();
		}
	}

	public boolean is_real_user() {
		boolean ret = false;
		if ((this.onlinetime >= this.RealOnLineTime)
				|| (this.level >= this.RealLevel)) {
			ret = true;
		}
		return ret;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setOnlinetime(Integer onlinetime) {
		this.onlinetime = onlinetime;
	}

	public Integer getOnlinetime() {
		return this.onlinetime;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPass() {
		return this.pass;
	}

	public void setDeviceId(String device_id) {
		this.device_id = device_id;
	}

	public String getDeviceId() {
		return this.device_id;
	}

	public void setPacketId(String packet_id) {
		this.packet_id = packet_id;
	}

	public String getPacketId() {
		return this.packet_id;
	}

	public void setGameId(String game_id) {
		this.game_id = game_id;
	}

	public String getGameId() {
		return this.game_id;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return this.date;
	}
}
