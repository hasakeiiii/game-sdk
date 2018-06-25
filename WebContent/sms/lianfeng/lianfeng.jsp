<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/sp/sms/beiqingbao/beiqingbao.jsp?linkid=123456789&destaddr=1066889925&phone=13751180191&message=T12
//http://localhost:8080/sp/sms/beiqingbao/beiqingbao.jsp?linkid=123456789&serviceid=delivrd&destaddr=0003&phone=13751180191&spnum=3
//http://119.147.23.178:8080/sms/beiqingbao/beiqingbao.jsp?linkid=123456789&destaddr=1066889925&phone=13751180191&message=T12
//http://119.147.23.178:8080/sms/beiqingbao/beiqingbao.jsp?linkid=123456789&serviceid=delivrd&destaddr=0003&phone=13751180191&spnum=1066889925
//System.out.println(request.getQueryString());
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("lianfeng linkid is null");
	return;
}
String sid="1004";
String stat=request.getParameter("retvalue")==null?"":request.getParameter("retvalue");
if(!"".equals(stat)){
	//System.out.println("lianfeng.mr "+sid+"===============");
	String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
	String key="DELIVRD";
	int operator=azul.KeyTool.getWap("",mobile);
	if(operator==2){
		key="0";
	}
	if(azul.SpTool.mr(sid,linkid,mobile,"",stat,key)){
		out.println("0");
	}
	else{
		out.println("1");
	}
}
else{
	//System.out.println("lianfeng.mo "+sid+"-------------------");
	String mobile=request.getParameter("srcnum")==null?"":request.getParameter("srcnum");
	String momsg=request.getParameter("content")==null?"":request.getParameter("content");
	String destaddr=request.getParameter("destnum")==null?"":request.getParameter("destnum");
	int fee=1;
	if("3".equals(momsg)){

	}
	if(azul.SpTool.mo(linkid,sid,fee,mobile,momsg,destaddr)){
		out.println("0");
	}
	else{
		out.println("1");
	}
}
%>