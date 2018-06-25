<%@ page contentType="text/html;charset=utf-8"%>
<%
//http://localhost:8080/sp/sms/beiqingbao/beiqingbao.jsp?mobile=15608888132&spnum=10669516&msg=831532222&linkid=8166883
//http://localhost:8080/sp/sms/beiqingbao/beiqingbao.jsp?mobile=15608888132&spnum=10669516&msg=831532222&linkid=8166883
//http://119.147.23.178:8080/sms/beiwei/beiweiMo.jsp?mobile=15608888132&spnum=10669516&msg=831532222&linkid=123456
//http://119.147.23.178:8080/sms/beiqingbao/beiqingbao.jsp?linkid=123456789&serviceid=delivrd&destaddr=0003&phone=13751180191&spnum=1066889925
System.out.println("beiwei.mo 1005-------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("beiwei mo.linkid is null");
	return;
}
String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
String msg=request.getParameter("msg")==null?"":java.net.URLEncoder.encode(request.getParameter("msg"));
String spNum=request.getParameter("spnum")==null?"":request.getParameter("spnum");
int fee=2;
if("2".equals(msg)){
   fee=3;
}
if(azul.SpTool.mo(linkid,"1005",fee,mobile,msg,spNum)){
	out.println("ok");
}
else{
	out.println("ERR");
}
%>
