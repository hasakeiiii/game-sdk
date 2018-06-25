package model;
public class SysRole implements java.io.Serializable{
private static final long serialVersionUID = -5470527368190615895L;
private Integer sys_role_id;
private Integer sys_user_id;
private String sys_menu_sub;
public Integer getSysRoleId(){
return sys_role_id;
}
public void setSysRoleId(Integer sys_role_id){
this.sys_role_id=sys_role_id;
}
public Integer getSysUserId(){
return sys_user_id;
}
public void setSysUserId(Integer sys_user_id){
this.sys_user_id=sys_user_id;
}
public String getSysMenuSub(){
return sys_menu_sub;
}
public void setSysMenuSub(String sys_menu_sub){
this.sys_menu_sub=sys_menu_sub;
}
}