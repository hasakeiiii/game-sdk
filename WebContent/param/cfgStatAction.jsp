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
//int cfg_stat_id=azul.JspUtil.getInt(request.getParameter("cfg_stat_id"));
//String sid=azul.JspUtil.getStr(request.getParameter("sid"));
//String cid=azul.JspUtil.getStr(request.getParameter("cid"));
//String province=azul.JspUtil.getStr(request.getParameter("province"));
//String msg=azul.JspUtil.getStr(request.getParameter("msg"));
//int num=azul.JspUtil.getInt(request.getParameter("num"));
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
CfgStat cfgStat=new CfgStat();
JspUtil.populate(cfgStat, request);
if(!"".equals(cfgStat.getProvince())){
	cfgStat.setProvince(azul.JspUtil.undecode(cfgStat.getProvince()));
}

String act_flag="-1";
String msg="操作失败";
String toPage="cfgStatList.jsp?pageno="+pageno;
String op=JspUtil.getStr(request.getParameter("op"),"");
CfgStatDao cfgStatDao=new CfgStatDao();
if(op.equals("add")){ 
    act_flag=cfgStatDao.add(cfgStat);
}
else if(op.equals("edit")){ 
    act_flag=cfgStatDao.edit(cfgStat);
}
else if(op.equals("delete")){ 
    act_flag=cfgStatDao.delete(cfgStat.getCfgStatId());
}
else{ 
    System.err.println("cfgStat op action is not right,op:"+op);
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