package model;



public class Gamepack implements java.io.Serializable{

	private static final long serialVersionUID = 5747273887337957356L;
	private Integer id;
	private String packet_no;
	private String game_id;
	private String file_name;
	private String version;
	private Integer sole;
	private String time;
	private String game_name;
	public String getGameName() {
		return game_name;
	}
	public void setGameName(String game_name) {
		this.game_name = game_name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPacketNo() {
		return packet_no;
	}
	public void setPacketNo(String packet_no) {
		this.packet_no = packet_no;
	}
	public String getGameId() {
		return game_id;
	}
	public void setGameId(String game_id) {
		this.game_id = game_id;
	}
	public String getFileName() {
		return file_name;
	}
	public void setFileName(String file_name) {
		this.file_name = file_name;
	}

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Integer getSole() {
		return sole;
	}
	public void setSole(Integer sole) {
		this.sole = sole;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	
	
}
