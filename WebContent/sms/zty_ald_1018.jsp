<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="azul.JspUtil"%>
<%
/*
?zhiling=47&duankou=1066322347&linkid=05153906178300210707&phonenum=13861216673&status=DELIVRD
zhiling   短信内容
duankou   通道号码
linkid    标示
phonenum  用户号码
status    状态值（“DELIVRD”表示成功 ）
*/
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("ald linkid is null");
	return;
}
String sid="1018";

String status=request.getParameter("status")==null?"":request.getParameter("status");
String mobile=request.getParameter("phonenum")==null?"":request.getParameter("phonenum");
String msg=request.getParameter("zhiling")==null?"":request.getParameter("zhiling");
String spNum=request.getParameter("duankou")==null?"":request.getParameter("duankou");
int fee=1;
if(!"DELIVRD".equals(status)){
	System.out.println("ald status is not 1");
	return;
}
if(azul.SpTool.charge_ok(linkid,sid,fee,mobile,msg,spNum)){
	out.print("OK");
}
else{
	out.print("ERROR");
}

%>