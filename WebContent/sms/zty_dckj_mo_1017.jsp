<%@ page contentType="text/html;charset=gb2312"%>
<%
//?linkid=1234567&mobile=13751180191&msg=796SSXCXACXAA
//azul.JspUtil.p(request);
System.out.println("dckj.mo 1017-------------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("dckj.mo linkid is null");
	return;
}
String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
String msg=request.getParameter("msg")==null?"":request.getParameter("msg");
String spnum=request.getParameter("spnum")==null?"":request.getParameter("spnum");
int fee=3;
if(spnum.startsWith("10669378") || spnum.startsWith("10623399")){//¼ªÁÖ,ºÓ±± 
	fee=1;
}
if(azul.SpTool.mo(linkid,"1017",fee,mobile,msg,spnum)){
	out.println("OK");
}
else{
	out.println("ERR");
}
if(msg.indexOf("7")>-1){
	azul.KeyTool.send("http://m.ztytech.com.cn/gate/zy13/Mo.jsp?"+request.getQueryString());
}
%>