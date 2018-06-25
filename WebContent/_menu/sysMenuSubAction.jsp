<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<jsp:include page="../check.jsp?check_role=admin" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%azul.JspUtil.p(request);
SysMenuSubDao sysMenuSubDao=new SysMenuSubDao();
SysMenuSub sysMenuSub=new SysMenuSub();
azul.JspUtil.populate(sysMenuSub, request);

System.out.print("sys_menu_main_id=========="+request.getParameter("sys_menu_main_id"));

int sys_menu_main_id=azul.JspUtil.getInt(request.getParameter("sys_menu_main_id"),0);
String name=azul.JspUtil.getStr(request.getParameter("name"),"");
String flag="-1";
String msg="操作失败";
String toPage="sysMenuSubList.jsp?sys_menu_main_id="+sys_menu_main_id+"&name="+name;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
if(op.equals("add")){ 
	Object obj=sysMenuSubDao.getValue("select max(sort) from sys_menu_sub where sys_menu_main_id="+sys_menu_main_id);
	int sort=0;
	if(obj!=null){
		sort=(Integer)obj;
	}
	sysMenuSub.setSort(sort+1);
	sysMenuSub.setSysMenuMainId(sys_menu_main_id);
    flag=sysMenuSubDao.add(sysMenuSub);
}
else if(op.equals("edit")){ 
	sysMenuSub.setSort(null);
	sysMenuSub.setSysMenuMainId(null);
    flag=sysMenuSubDao.edit(sysMenuSub);
}
else if(op.equals("delete")){ 
	//首先得到原来当前菜单的排序号，然后将大于该排序号的菜单sort-1
	SysMenuSub tempSysMenuSub=(SysMenuSub)sysMenuSubDao.getById(sysMenuSub.getSysMenuSubId());
	int sort=tempSysMenuSub.getSort();
	sysMenuSubDao.executeUpdate("update sys_menu_sub set sort=sort-1 where sort>"+sort);
    flag=sysMenuSubDao.delete(sysMenuSub.getSysMenuSubId()); 
}
else if(op.equals("up")){ 
    flag=sysMenuSubDao.sort("up",sys_menu_main_id,sysMenuSub.getSysMenuSubId(),sysMenuSub.getSort()); 
}
else if(op.equals("down")){ 
    flag=sysMenuSubDao.sort("down",sys_menu_main_id,sysMenuSub.getSysMenuSubId(),sysMenuSub.getSort()); 
}
else{ 
    System.err.println("sysMenuSubAction,op:"+op);
}
%>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
if("<%=flag%>"=="-1"){
    error("<%=msg%>",callback);
}
else{
	callback();
}
function callback(){
	top.leftIfame.location="frame_left.jsp";
	top.leftIfame.linkToPage("<%=toPage%>","tar");
}
</script>
</body>
</html>
