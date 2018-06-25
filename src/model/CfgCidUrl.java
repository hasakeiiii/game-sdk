package model;
public class CfgCidUrl implements java.io.Serializable{
private static final long serialVersionUID = 793413573237940641L;
private Integer cfg_cid_url_id;
private String cid;
private String url;
public Integer getCfgCidUrlId(){
return cfg_cid_url_id;
}
public void setCfgCidUrlId(Integer cfg_cid_url_id){
this.cfg_cid_url_id=cfg_cid_url_id;
}
public String getCid(){
return cid;
}
public void setCid(String cid){
this.cid=cid;
}
public String getUrl(){
return url;
}
public void setUrl(String url){
this.url=url;
}
}