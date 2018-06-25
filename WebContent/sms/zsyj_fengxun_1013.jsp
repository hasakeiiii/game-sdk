<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/dingxue/sms/sina/sina.jsp?mobile=13900000000&msg=mo&longphone=10665366001&linkid=s22r3ax234zzz
//http://localhost:8080/dingxue/sms/sina/sina.jsp?mobile=13900000000&state=0&longphone=10665366001&linkid=s22r3ax234zzz
//azul.JspUtil.p(request);

String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("fengxun linkid is null");
	return;
}
String status=request.getParameter("stat")==null?"":request.getParameter("stat");
if("DELIVRD".equals(status)){
	String mobile=request.getParameter("mobi")==null?"":request.getParameter("mobi");
	String msg=request.getParameter("msg")==null?"":request.getParameter("msg");
	String spNum=request.getParameter("spc")==null?"":request.getParameter("spc");
	if(azul.SpTool.charge_ok(linkid,"1013",1,mobile,msg,spNum)){
		out.println("ok");
	}
	else{
		out.println("ERR");
	}
}
%>