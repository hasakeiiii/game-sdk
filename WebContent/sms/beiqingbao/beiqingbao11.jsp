<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/sp/sms/beiqingbao/beiqingbao.jsp?linkid=123456789&destaddr=1066889925&phone=13751180191&message=T12
//http://localhost:8080/sp/sms/beiqingbao/beiqingbao.jsp?linkid=123456789&serviceid=delivrd&destaddr=0003
//http://119.147.23.178:8080/sms/beiqingbao/beiqingbao.jsp?linkid=123456789&destaddr=1066889925&phone=13751180191&message=T12
//http://119.147.23.178:8080/sms/beiqingbao/beiqingbao.jsp?linkid=123456789&serviceid=delivrd&destaddr=0003&phone=13751180191&spnum=1066889925
//System.out.println(request.getQueryString());
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("beiqingbao linkid is null");
	return;
}
String sid="1004";
String stat=request.getParameter("destaddr")==null?"-999":request.getParameter("destaddr");
if("0003".equals(stat)){
	String serviceid=request.getParameter("serviceid")==null?"-999":request.getParameter("serviceid");
	//System.out.println("beiqingbao.mr "+sid+"===============");
	String mobile=request.getParameter("phone")==null?"":request.getParameter("phone");
	String spNum=request.getParameter("spnum")==null?"":request.getParameter("spnum");
	if(azul.SpTool.mr(sid,linkid,mobile,spNum,serviceid,"delivrd")){
		out.println("ok");
	}
	else{
		out.println("ERR");
	}
	//同步给中泰源
	if("000".equals(azul.SpTool.getInfoByLinkId(linkid)[1])){
		azul.KeyTool.send("http://m.ztytech.com.cn/gate/beiqing/beiqingMr.jsp?"+request.getQueryString());
	}
}
else{
	//System.out.println("beiqingbao.mo "+sid+"-------------------");
	String mobile=request.getParameter("phone")==null?"":request.getParameter("phone");
	String momsg=request.getParameter("message")==null?"":request.getParameter("message");
	String destaddr=request.getParameter("destaddr")==null?"":request.getParameter("destaddr");
	int fee=1;
	if("3".equals(momsg)){
		fee=2;
		sid="1010";
	}
	if(azul.SpTool.mo(linkid,sid,fee,mobile,momsg,destaddr)){
		out.println("ok");
	}
	else{
		out.println("ERR");
	}
	//同步给中泰源
	if("000".equals(azul.SpTool.getInfoByLinkId(linkid)[1])){
		azul.KeyTool.send("http://m.ztytech.com.cn/gate/beiqing/beiqingMo.jsp?"+request.getQueryString());
	}
}
%>