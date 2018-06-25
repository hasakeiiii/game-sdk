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
CfgLog cfgLog=new CfgLog();
//int cfg_log_id=common.JspUtil.getInt(request.getParameter("cfg_log_id"));
//String type=common.JspUtil.getStr(request.getParameter("type"));
//String cid=common.JspUtil.getStr(request.getParameter("cid"));
//String ip=common.JspUtil.getStr(request.getParameter("ip"));
//String param=common.JspUtil.getStr(request.getParameter("param"));
//String date_time=common.JspUtil.getStr(request.getParameter("date_time"));
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
JspUtil.populate(cfgLog, request);
CfgLogDao cfgLogDao=new CfgLogDao();

String act_flag="-1";
String msg="操作失败";
String toPage="cfgLogList.jsp?pageno="+pageno;
String op=JspUtil.getStr(request.getParameter("op"),"");
if(op.equals("add")){ 
    act_flag=cfgLogDao.add(cfgLog);
}
else if(op.equals("edit")){ 
    act_flag=cfgLogDao.edit(cfgLog);
}
else if(op.equals("delete")){ 
    act_flag=cfgLogDao.delete(cfgLog.getCfgLogId());
}
else{ 
    System.err.println("cfgLog op action is not right,op:"+op);
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
