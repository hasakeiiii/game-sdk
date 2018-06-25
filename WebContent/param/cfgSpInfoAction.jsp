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
//int cfg_sp_info_id=azul.JspUtil.getInt(request.getParameter("cfg_sp_info_id"));
//String trone=azul.JspUtil.getStr(request.getParameter("trone"));
//String command=azul.JspUtil.getStr(request.getParameter("command"));
//int operator=azul.JspUtil.getInt(request.getParameter("operator"));
//int price=azul.JspUtil.getInt(request.getParameter("price"));
//String province=azul.JspUtil.getStr(request.getParameter("province"));
//String city=azul.JspUtil.getStr(request.getParameter("city"));
//int exact=azul.JspUtil.getInt(request.getParameter("exact"));
//String info=azul.JspUtil.getStr(request.getParameter("info"));
//int replay=azul.JspUtil.getInt(request.getParameter("replay"));
//String max=azul.JspUtil.getStr(request.getParameter("max"));
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
CfgSpInfo cfgSpInfo=new CfgSpInfo();
JspUtil.populate(cfgSpInfo, request);

String act_flag="-1";
String msg="操作失败";
String toPage="cfgSpInfoList.jsp?pageno="+pageno;
String op=JspUtil.getStr(request.getParameter("op"),"");
CfgSpInfoDao cfgSpInfoDao=new CfgSpInfoDao();
if(op.equals("add")){ 
    act_flag=cfgSpInfoDao.add(cfgSpInfo);
}
else if(op.equals("edit")){ 
    act_flag=cfgSpInfoDao.edit(cfgSpInfo);
}
else if(op.equals("delete")){ 
    act_flag=cfgSpInfoDao.delete(cfgSpInfo.getCfgSpInfoId());
}
else{ 
    System.err.println("cfgSpInfo op action is not right,op:"+op);
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