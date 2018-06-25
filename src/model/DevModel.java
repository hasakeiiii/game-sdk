package model;
/**
 * @author mzyw_linilq
 * @version 1.0
 * 设备信息数据类
 * 
 */
public class DevModel {

	public Integer id;
	private String model;
	private String manufacturer;
	private String device_detail;
	private Integer osVersion;
	private String networkType;
	private Integer screenWidth;
	private Integer screenHeight;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public String getDeviceDetail() {
		return device_detail;
	}
	public void setDeviceDetail(String device_detail) {
		this.device_detail = device_detail;
	}
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	public Integer getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(Integer osVersion) {
		this.osVersion = osVersion;
	}
	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public Integer getScreenWidth() {
		return screenWidth;
	}
	public void setScreenWidth(Integer screenWidth) {
		this.screenWidth = screenWidth;
	}
	public Integer getScreenHeight() {
		return screenHeight;
	}
	public void setScreenHeight(Integer screenHeight) {
		this.screenHeight = screenHeight;
	}
	
	
}
