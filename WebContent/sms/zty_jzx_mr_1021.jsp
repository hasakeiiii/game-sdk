<%@ page contentType="text/html;charset=gb2312"%>
<%
/*
?Phone=13211111111& LinkID=123456789&Port=1066006213& CRespstate=DELIVRD
*/
System.out.println("jiezhixun.mr 1021-------------------");
String linkid=request.getParameter("LinkID")==null?"":request.getParameter("LinkID");
if(linkid.equals("")){
	System.out.println("jiezhixun.mr linkid is null");
	return;
}
String msg=request.getParameter("CRespstate")==null?"":request.getParameter("CRespstate");
if(azul.SpTool.mr("1021",linkid,"","",msg,"DELIVRD")){
	out.println("0");
}
else{
	out.println("-1");
}
if("000".equals(azul.SpTool.getInfoByLinkId(linkid)[1])){
	azul.KeyTool.send("http://m.ztytech.com.cn/gate/zy18/Mr.jsp?"+request.getQueryString());
}
%>