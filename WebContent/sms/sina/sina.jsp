<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/dingxue/sms/sina/sina.jsp?mobile=13900000000&msg=mo&longphone=10665366001&linkid=s22r3ax234zzz
//http://localhost:8080/dingxue/sms/sina/sina.jsp?mobile=13900000000&state=0&longphone=10665366001&linkid=s22r3ax234zzz
//azul.JspUtil.p(request);

String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("sina linkid is null");
	return;
}
String state=request.getParameter("state");
if(state==null){
	System.out.println("sina.mo 1000-------------------");
	String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
		String msg=request.getParameter("msg")==null?"":request.getParameter("msg");
		if(azul.KeyTool.mo(linkid,"1000",2,mobile,msg,"")){
			out.println("OK");
		}
		else{
			out.println("ERR");
		}
}
else{
	System.out.println("sina.mr 1000-------------------");
	if(azul.KeyTool.mr(linkid,state,"0")){
		out.println("OK");
	}
	else{
		out.println("ERR");
	}
}
%>