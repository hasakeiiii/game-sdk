package model;

import dao.BusinesserDao;
import dao.ChannelDao;
import dao.CooperationDao;

public class PacketMana {
	private Integer id;
	private String packetId;
	private String fileName;
	private String gameName;
	private String preVersion;
	private String newVersion;
	private String dUrl;
	private String packetNo;
	private String channleName = "";
	private String businessName = "";
	
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	public String getChannleName() {
		return channleName;
	}
	public void setChannleName(String channleName) {
		this.channleName = channleName;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getdUrl() {
		return dUrl;
	}
	public void setdUrl(String dUrl) {
		this.dUrl = dUrl;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
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
	public String getNewVersion() {
		return newVersion;
	}
	public void setNewVersion(String newVersion) {
		this.newVersion = newVersion;
	}
	public String getDUrl() {
		return dUrl;
	}
	public void setDUrl(String dUrl) {
		this.dUrl = dUrl;
	}
	public String getPacketNo() {
		return packetNo;
	}
	public void setPacketNo(String packetNo) {
		this.packetNo = packetNo;
	}
	
	public void getName()//
	{
		CooperationDao cooperationDao = new CooperationDao();
	    Cooperation cooperation = cooperationDao.getRecord(getPacketId());
	    if(cooperation != null)
	    {
	          BusinesserDao businesserDao = new BusinesserDao();
	          businessName = businesserDao.getBusinesserName(cooperation.getBusinessNo());
	          
	          ChannelDao channelDao = new ChannelDao();
	          this.channleName = channelDao.getChannelName(cooperation.getChannelNo());
	    }
	}

}
