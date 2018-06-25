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
String status=request.getParameter("status");
if(status==null){
	String mobile=request.getParameter("phone")==null?"":request.getParameter("phone");
	String msg=request.getParameter("content")==null?"":request.getParameter("content");
	String spNum=request.getParameter("desttermid")==null?"":request.getParameter("desttermid");
	if(azul.SpTool.mo(linkid,"1008",1,mobile,msg,spNum)){
		out.println("0");
	}
	else{
		out.println("1");
	}
}
else{
	String feetermid=request.getParameter("feetermid")==null?"":request.getParameter("feetermid");
	String flag="200";
	int operator=azul.KeyTool.getWap("",feetermid);
	if(operator==3){
		flag="1";
	}
	if(azul.SpTool.mr("1008",linkid,"","",status,flag)){
		out.println("0");
	}
	else{
		out.println("1");
	}
}
%>