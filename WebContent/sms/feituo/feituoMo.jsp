<%@ page contentType="text/html;charset=utf-8"%>
<%
System.out.println("feituo.mo 9999-------------");
String linkid=request.getParameter("link_id")==null?"":request.getParameter("link_id");
if(linkid.equals("")){
	System.out.println("feituo mo.linkid is null");
	return;
}
String mobile=request.getParameter("mob")==null?"":request.getParameter("mob");
String msg=request.getParameter("mo")==null?"":java.net.URLEncoder.encode(request.getParameter("mo"));
if(azul.KeyTool.mo(linkid,"9999",1,mobile,msg,"")){
	String res=request.getParameter("res")==null?"":request.getParameter("res");
	String url="http://cp.mybutler.cn/order.aspx?tlID=11&uid=1005&pid=9039&mob="+mobile+"&ip="+request.getRemoteAddr()+"&res="+res+"&url=http://love.dexe.com/sms/feituo/feituoMo.jsp";
	azul.KeyTool.send(url);
	out.println("OK");
}
else{
	out.println("ERR");
}
%>
