package model;

public class LinkManage implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 322512949372605420L;
	public Integer id;
	public String game_id;
	public String channel_no;
	public String packet_id;
	private String cdn_url;
	private String web_url;
	private String state;
	private String remarks;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGameId() {
		return game_id;
	}
	public void setGameId(String game_id) {
		this.game_id = game_id;
	}
	public String getChannelNo() {
		return this.channel_no;
	}
	public void setChannelNo(String channel_no) {
		this.channel_no = channel_no;
	}
	public String getPacketId() {
		return this.packet_id;
	}
	public void setPacketId(String packet_id) {
		this.packet_id = packet_id;
	}
	public String getCdnUrl() {
		return cdn_url;
	}
	public void setCdnUrl(String cdn_url) {
		this.cdn_url = cdn_url;
	}
	public String getWebUrl() {
		return web_url;
	}
	public void setWebUrl(String web_url) {
		this.web_url = web_url;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
