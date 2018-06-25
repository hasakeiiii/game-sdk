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
CfgSpSend cfgSpSend=new CfgSpSend();
//int cfg_sp_code_id=azul.JspUtil.getInt(request.getParameter("cfg_sp_code_id"));
//String sid=azul.JspUtil.getStr(request.getParameter("sid"));
//String cid=azul.JspUtil.getStr(request.getParameter("cid"));
//String sp_code=azul.JspUtil.getStr(request.getParameter("sp_code"));
//String url=azul.JspUtil.getStr(request.getParameter("url"));
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
JspUtil.populate(cfgSpSend, request);
if("".equals(cfgSpSend.getProvince())){
	cfgSpSend.setProvince("00");
}
CfgSpSendDao cfgSpSendDao=new CfgSpSendDao();

String act_flag="-1";
String msg="操作失败";
String toPage="cfgSpSendList.jsp?pageno="+pageno;
String op=JspUtil.getStr(request.getParameter("op"),"");
if(op.equals("add")){ 
    act_flag=cfgSpSendDao.add(cfgSpSend);
}
else if(op.equals("edit")){ 
    act_flag=cfgSpSendDao.edit(cfgSpSend);
}
else if(op.equals("delete")){ 
    act_flag=cfgSpSendDao.delete(cfgSpSend.getCfgSpSendId());
}
else{ 
    System.err.println("cfgSpSend op action is not right,op:"+op);
}
//更新缓存内容
CacheSp.initSpParam();
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
