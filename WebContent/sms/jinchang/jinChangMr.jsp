<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/dingxue/sms/tom/tomMr.jsp?linkid=1234567&status=0
System.out.println("jinchang.mr 1007-------------------");
String linkid=request.getParameter("LinkId")==null?"":request.getParameter("LinkId");
if(linkid.equals("")){
	System.out.println("jinchang.mr linkid is null");
	return;
}
String msg=request.getParameter("Re_Stat")==null?"":request.getParameter("Re_Stat");
String TOC=request.getParameter("TOC")==null?"1":request.getParameter("TOC");
if("1".equals(TOC) || "3".equals(TOC)){
	TOC="DELIVRD";
	
}
else{
	TOC="0";
}
boolean ok=azul.KeyTool.mr(linkid,msg,TOC);
if(ok){
	out.println("1");
}
else{
	out.println("ERR");
}

%>