package model;
public class CfgArea implements java.io.Serializable{
private static final long serialVersionUID = 4258630378455396512L;
private Integer cfg_area_id;
private Long star;
private Long ends;
private String province;
private String city;
private String net;


public Integer getCfgAreaId(){
return cfg_area_id;
}
public void setCfgAreaId(Integer cfg_area_id){
this.cfg_area_id=cfg_area_id;
}
public Long getStar(){
return star;
}
public void setStar(Long star){
this.star=star;
}
public Long getEnds(){
return ends;
}
public void setEnds(Long ends){
this.ends=ends;
}
public String getProvince(){
return province;
}
public void setProvince(String province){
this.province=province;
}
public String getCity(){
return city;
}
public void setCity(String city){
this.city=city;
}
public String getNet(){
return net;
}
public void setNet(String net){
this.net=net;
}
}