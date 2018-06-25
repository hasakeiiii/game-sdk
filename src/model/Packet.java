package model;

public class Packet {
	private Integer id;
	private String packetId;
	private String preVersion;
	private String dUrl;
	private String packetNo;
	
	
	
	public String getPacketNo() {
		return packetNo;
	}
	public void setPacketNo(String packetNo) {
		this.packetNo = packetNo;
	}
	public String getDUrl() {
		return dUrl;
	}
	public void setDUrl(String dUrl) {
		this.dUrl = dUrl;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPacketId() {
		return packetId;
	}
	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}
	public String getPreVersion() {
		return preVersion;
	}
	public void setPreVersion(String preVersion) {
		this.preVersion = preVersion;
	}
	
	
}
