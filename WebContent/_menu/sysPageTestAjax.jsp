<%@ page contentType="text/html;charset=utf-8"%>
<%
String op=request.getParameter("op")==null?"":request.getParameter("op");
if("add".equals(op)){
	String index=request.getParameter("index")==null?"":request.getParameter("index");
	String url=request.getParameter("url")==null?"":request.getParameter("url");
	url=url.replaceAll("ZvZ","&");
	common.ConfigIni.makeValue("PageUrl",index,url);
	response.sendRedirect("sysPageTest.jsp");
}
else if("edit".equals(op)){
	String index=request.getParameter("index")==null?"":request.getParameter("index");
	String url=request.getParameter("url")==null?"":request.getParameter("url");
	url=url.replaceAll("ZvZ","&");
	common.ConfigIni.setValue("PageUrl",index,url);
	response.sendRedirect("sysPageTest.jsp");
}
else if("get".equals(op)){
	  String param=request.getParameter("param")==null?"":request.getParameter("param");
	  param=param.replaceAll("ZvZ","&");
	  //System.out.println("param:"+param);
	  String structTxt=util.FileUtil.runJsp(param+"&src=ie&r="+new java.util.Random().nextLong(),"UTF-8");
	  structTxt=structTxt.replaceAll("<", "&lt;");
	  structTxt=structTxt.replaceAll(">", "&gt;");
	  structTxt=structTxt.replaceAll("\n", "<br>");System.out.println(structTxt);
	  out.print(structTxt);
}
%>