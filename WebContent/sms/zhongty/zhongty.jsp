<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/dingxue/sms/tom/tomMo.jsp?linkid=1234567&mobile=13751180191&msg=796SSXCXACXAA
//azul.JspUtil.p(request);
String linkid=request.getParameter("msgid")==null?"":request.getParameter("msgid");
if(linkid.equals("")){
	System.out.println("zhongty linkid is null");
	return;
}
String status=request.getParameter("status")==null?"":request.getParameter("status");
if("".equals(status)){
	//上行
	System.out.println("zhongty.mo 1011-------------------");
	String mobile=request.getParameter("orgmobile")==null?"":request.getParameter("orgmobile");
		String msg=request.getParameter("content")==null?"":request.getParameter("content");
		if(azul.KeyTool.mo(linkid,"1011",1,mobile,msg,"")){
			out.println("OK");
		}
		else{
			out.println("ERR");
		}
}
else{
	//状态报告
	System.out.println("zhongty.mr 1011-------------------");
	if(azul.KeyTool.mr(linkid,status,"DELIVRD")){
		out.println("OK");
	}
	else{
		out.println("ERR");
	}
}

%>