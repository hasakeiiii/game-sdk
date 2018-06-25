package model;
public class SysUser implements java.io.Serializable{
private static final long serialVersionUID = 6146906043833273401L;
private Integer sys_user_id;
private String username;
private String userpass;
private String role;
public Integer getSysUserId(){
return sys_user_id;
}
public void setSysUserId(Integer sys_user_id){
this.sys_user_id=sys_user_id;
}
public String getUsername(){
return username;
}
public void setUsername(String username){
this.username=username;
}
public String getUserpass(){
return userpass;
}
public void setUserpass(String userpass){
this.userpass=userpass;
}
public String getRole(){
return role;
}
public void setRole(String role){
this.role=role;
}
}