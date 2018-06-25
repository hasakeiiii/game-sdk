package model;
public class CfgCidUrlIvr implements java.io.Serializable{
private static final long serialVersionUID = 487084532614610695L;
private Integer cfg_cid_url_ivr_id;
private String cid;
private String url;
public Integer getCfgCidUrlIvrId(){
return cfg_cid_url_ivr_id;
}
public void setCfgCidUrlIvrId(Integer cfg_cid_url_ivr_id){
this.cfg_cid_url_ivr_id=cfg_cid_url_ivr_id;
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