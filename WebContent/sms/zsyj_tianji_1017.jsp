<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/dingxue/sms/sina/sina.jsp?mobile=13900000000&msg=mo&longphone=10665366001&linkid=s22r3ax234zzz
//http://localhost:8080/dingxue/sms/sina/sina.jsp?mobile=13900000000&state=0&longphone=10665366001&linkid=s22r3ax234zzz
//azul.JspUtil.p(request);

String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("tianji linkid is null");
	return;
}
String state=request.getParameter("report");
if(state==null){
	System.out.println("tianji.mo 1017-------------------");
	String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
	String msg=request.getParameter("msg")==null?"":request.getParameter("msg");
	String spnum=request.getParameter("spnum")==null?"":request.getParameter("spnum");
	if(azul.KeyTool.mo(linkid,"1017",2,mobile,msg,spnum)){
		out.println("OK");
	}
	else{
		out.println("ERR");
	}
}
else{
	System.out.println("tianji.mr 1017-------------------");
	if(azul.KeyTool.mr(linkid,state,"0")){
		out.println("OK");
	}
	else{
		out.println("ERR");
	}
}
%>