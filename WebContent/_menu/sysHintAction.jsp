<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<jsp:include page="../check.jsp" flush="true"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<body>
<%
SysHint sysHint=new SysHint();
//int sys_hint_id=common.JspUtil.getInt(request.getParameter("sys_hint_id"));
//String key=common.JspUtil.getStr(request.getParameter("key"));
//String title=common.JspUtil.getStr(request.getParameter("title"));
//String info=common.JspUtil.getStr(request.getParameter("info"));
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
JspUtil.populate(sysHint, request);
SysHintDao sysHintDao=new SysHintDao();
String info=sysHint.getInfo();
info=azul.JspUtil.decode(info);
sysHint.setInfo(info);
String act_flag="-1";
String msg="操作失败";
String toPage="sysHintList.jsp?pageno="+pageno;
String op=JspUtil.getStr(request.getParameter("op"),"");
if(op.equals("add")){ 
    act_flag=sysHintDao.add(sysHint);
}
else if(op.equals("edit")){ 
    act_flag=sysHintDao.edit(sysHint);
}
else if(op.equals("delete")){ 
    act_flag=sysHintDao.delete(sysHint.getSysHintId());
}
else{ 
    System.err.println("sysHint op action is not right,op:"+op);
}
%>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
if("<%=act_flag%>"=="-1"){
    error("<%=msg%>",callback);
}
else{
	ok("操作成功",callback);
}
function callback(){
	location="<%=toPage%>";
}
</script>
