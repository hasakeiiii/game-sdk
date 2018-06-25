<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/sp/sms/xunrui/xunrui.jsp?link_id=123456789&mobile=13751180191&momsg=AD2
//http://localhost:8080/sp/sms/xunrui/xunrui.jsp?link_id=123456789&stat=DELIVRD&mobile=13751180191&spnum=3
//http://119.147.23.178:8080/sms/xunrui/xunrui.jsp?link_id=123456789&mobile=13751180191&momsg=AD2
//http://119.147.23.178:8080/sms/xunrui/xunrui.jsp?link_id=123456789&stat=DELIVRD&mobile=13751180191&spnum=1066938866
//azul.JspUtil.p(request);
String linkid=request.getParameter("link_id")==null?"":request.getParameter("link_id");
if(linkid.equals("")){
	System.out.println("xunrui.mo linkid is null");
	return;
}
String sid="1002";
String stat=request.getParameter("stat")==null?"-999":request.getParameter("stat");
if("-999".equals(stat)){
	System.out.println("xunrui.mo "+sid+"-------------------");
	String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
	String momsg=request.getParameter("momsg")==null?"":request.getParameter("momsg");
	if(azul.SpTool.mo(linkid,sid,1,mobile,momsg,"")){
		out.println("OK");
	}
	else{
		out.println("ERR");
	}
}
else{
	System.out.println("xunrui.mr "+sid+"===============");
	String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
	String spNum=request.getParameter("spnum")==null?"":request.getParameter("spnum");
	if(azul.SpTool.mr(sid,linkid,mobile,spNum,stat,"delivrd")){
		out.println("OK");
	}
	else{
		out.println("ERR");
	}
}
%>