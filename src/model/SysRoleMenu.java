package model;
public class SysRoleMenu implements java.io.Serializable{
private static final long serialVersionUID = 1873499340735985752L;
private Integer sys_role_menu_id;
private String sys_role;
private String sys_menu_sub;
public Integer getSysRoleMenuId(){
return sys_role_menu_id;
}
public void setSysRoleMenuId(Integer sys_role_menu_id){
this.sys_role_menu_id=sys_role_menu_id;
}
public String getSysRole(){
return sys_role;
}
public void setSysRole(String sys_role){
this.sys_role=sys_role;
}
public String getSysMenuSub(){
return sys_menu_sub;
}
public void setSysMenuSub(String sys_menu_sub){
this.sys_menu_sub=sys_menu_sub;
}
}