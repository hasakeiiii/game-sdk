package model;
/**
 * @author mzyw_linilq
 * @version 1.0
 * 设备信息数据类
 * 
 */
public class MmUser {

	public Integer id;
	private String packet_id;
	private String imei;
	private String imsi;
	private String mac;
	
	private Integer model;
	private String sid;
	
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	
	public String getPacketId() {
		return packet_id;
	}
	public void setPacketId(String packet_id) {
		this.packet_id = packet_id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public Integer getModel() {
		return model;
	}
	public void setModel(Integer model) {
		this.model = model;
	}
	
	
	
}
