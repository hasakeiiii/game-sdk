<%@ page contentType="text/html;charset=utf-8"%>
<%
System.out.println("hl.mr 1007-------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("hl.Mr linkid is null");
	return;
}
String msg=azul.JspUtil.getStr(request.getParameter("reportcode"),"");
if(azul.SpTool.mr("1007",linkid,"","",msg,"DELIVRD")){
	out.println("ok");
}
else{
	out.println("ERR");
}
//同步给中泰源
if("000".equals(azul.SpTool.getInfoByLinkId(linkid)[1])){
	azul.KeyTool.send("http://m.ztytech.com.cn/gate/zy03/Mr.jsp?"+request.getQueryString());
}
%>