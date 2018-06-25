package model;
public class SysMenuMain implements java.io.Serializable{
private static final long serialVersionUID = 8913163681573307684L;
private Integer sys_menu_main_id;
private String name;
private Integer sort;
public Integer getSysMenuMainId(){
return sys_menu_main_id;
}
public void setSysMenuMainId(Integer sys_menu_main_id){
this.sys_menu_main_id=sys_menu_main_id;
}
public String getName(){
return name;
}
public void setName(String name){
this.name=name;
}
public Integer getSort(){
return sort;
}
public void setSort(Integer sort){
this.sort=sort;
}
}