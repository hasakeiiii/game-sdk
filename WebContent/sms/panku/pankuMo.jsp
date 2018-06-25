<%@ page contentType="text/html;charset=gbk"%>
<%
//http://localhost:8080/dingxue/sms/panku/pankuMo.jsp?linkid=789456&mobile=1375333&content=144cttxZAAADAB
System.out.println("panku.mo 1000-------------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
//azul.JspUtil.p(request);
if(linkid.equals("")){
	System.out.println("panku.mo linkid is null");
	return;
}
String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
String msg=request.getParameter("content")==null?"":request.getParameter("content");
if(azul.KeyTool.mo(linkid,"1000",1,mobile,msg,"")){
	out.println("ok_ 资费1元/条 询4006578255");
}
else{
	out.println("ERR");
}

%>