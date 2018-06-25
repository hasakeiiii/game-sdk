<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/dingxue/sms/tom/tomMo.jsp?linkid=1234567&mobile=13751180191&msg=796SSXCXACXAA
//azul.JspUtil.p(request);
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("yangzhou linkid is null");
	return;
}
String msgid=request.getParameter("msgid")==null?"":request.getParameter("msgid");
if("".equals(msgid)){
	System.out.println("yangzhou.mr 1009-------------------");
	String mobile=request.getParameter("phone")==null?"":request.getParameter("phone");
		String msg=request.getParameter("msg")==null?"":request.getParameter("msg");
		if(azul.KeyTool.mo(linkid,"1009",2,mobile,msg,"")){
			out.println("OK");
		}
		else{
			out.println("ERR");
		}
}
else{
	System.out.println("feilong mr-------------------");
	String status=request.getParameter("status")==null?"":request.getParameter("status");
	if(azul.KeyTool.mr(linkid,status,"DELIVRD")){
		out.println("OK");
	}
	else{
		out.println("ERR");
	}
}
%>