<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/dingxue/sms/sina/sina.jsp?mobile=13900000000&msg=mo&longphone=10665366001&linkid=s22r3ax234zzz
//http://localhost:8080/dingxue/sms/sina/sina.jsp?mobile=13900000000&state=0&longphone=10665366001&linkid=s22r3ax234zzz
//azul.JspUtil.p(request);

String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("lingdong linkid is null");
	return;
}
String status=request.getParameter("status")==null?"":request.getParameter("status");;
if("DELIVRD".equals(status)){
	String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
	String msg=request.getParameter("content")==null?"":request.getParameter("content");
	String spNum=request.getParameter("spnumber")==null?"":request.getParameter("spnumber");
	if(azul.SpTool.charge_ok(linkid,"1009",1,mobile,msg,spNum)){
		out.println("ok");
	}
	else{
		out.println("err");
	}
}
%>