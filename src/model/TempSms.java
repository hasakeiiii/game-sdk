package model;
/**
 * TempSms类为暂存验证码信息的数据模型
 * @author Administrator
 *
 */
public class TempSms {
	
	private Integer Id;
	private String userName;
	private String identiCode;
	private String addDate;
	private String addTime;
	
	private String checkinTime;
	private String phoneNum;
	
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdentiCode() {
		return identiCode;
	}
	public void setIdentiCode(String identiCode) {
		this.identiCode = identiCode;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	
	public String getCheckinTime() {
		return checkinTime;
	}
	public void setCheckinTime(String checkinTime) {
		this.checkinTime = checkinTime;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	@Override
	public String toString() {
		return "TempSms [Id=" + Id + ", userName=" + userName + ", identiCode="
				+ identiCode + ", addDate=" + addDate + ", addTime=" + addTime
				+ ", checkinTime=" + checkinTime + ", phoneNum=" + phoneNum
				+ "]";
	}

	/**
	 * 验证码不存在：001
	 */
	public static String NoCodeExistFail = "001";
	/**
	 * 验证码超时：002
	 */
	public static String CodeOverTimeFail = "002";
	/**
	 * 验证成功：000
	 */
	public static String Success = "000";
	
	
	

}
