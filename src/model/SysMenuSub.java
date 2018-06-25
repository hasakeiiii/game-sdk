package model;
public class SysMenuSub implements java.io.Serializable{
private static final long serialVersionUID = -8366528148364954833L;
private Integer sys_menu_sub_id;
private Integer sys_menu_main_id;
private String title;
private String links;
private Integer display;
private Integer color;
private Integer sort;
public Integer getSysMenuSubId(){
return sys_menu_sub_id;
}
public void setSysMenuSubId(Integer sys_menu_sub_id){
this.sys_menu_sub_id=sys_menu_sub_id;
}
public Integer getSysMenuMainId(){
return sys_menu_main_id;
}
public void setSysMenuMainId(Integer sys_menu_main_id){
this.sys_menu_main_id=sys_menu_main_id;
}
public String getTitle(){
return title;
}
public void setTitle(String title){
this.title=title;
}
public String getLinks(){
return links;
}
public void setLinks(String links){
this.links=links;
}
public Integer getDisplay(){
return display;
}
public void setDisplay(Integer display){
this.display=display;
}
public Integer getColor(){
return color;
}
public void setColor(Integer color){
this.color=color;
}
public Integer getSort(){
return sort;
}
public void setSort(Integer sort){
this.sort=sort;
}
}