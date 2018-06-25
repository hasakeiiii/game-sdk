<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<jsp:include page="../check.jsp?check_role=admin" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
SysMenuMainDao sysMenuMainDao=new SysMenuMainDao();
SysMenuMain sysMenuMain=new SysMenuMain();
azul.JspUtil.populate(sysMenuMain, request);
String flag="-1";
String msg="操作失败";
String toPage="sysMenuMainList.jsp";
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
if(op.equals("add")){ 
	Object obj=sysMenuMainDao.getValue("select max(sort) from sys_menu_main");
	int sort=0;
	if(obj!=null){
		sort=(Integer)obj;
	}
	sysMenuMain.setSort(sort+1);
    flag=sysMenuMainDao.add(sysMenuMain);
}
else if(op.equals("edit")){ 
    sysMenuMain.setSort(null);
    flag=sysMenuMainDao.edit(sysMenuMain);
}
else if(op.equals("delete")){ 
    flag=sysMenuMainDao.delete(sysMenuMain.getSysMenuMainId());
    sysMenuMainDao.executeUpdate("delete from sys_menu_main where sys_menu_main_id="+sysMenuMain.getSysMenuMainId());
}
else if(op.equals("up")){ 
    flag=sysMenuMainDao.sort("up",sysMenuMain.getSysMenuMainId(),sysMenuMain.getSort()); 
}
else if(op.equals("down")){ 
    flag=sysMenuMainDao.sort("down",sysMenuMain.getSysMenuMainId(),sysMenuMain.getSort()); 
}
else{ 
    System.err.println("sysMenuMainAction,op:"+op);
}
%>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
if("<%=flag%>"=="1"){
    ok("操作成功",callback);
}
else{
	error("<%=msg%>",callback);
}
function callback(){
	location="<%=toPage%>";
}
</script>
</body>
</html>
