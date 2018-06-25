<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/dingxue/sms/hongbo/hbMo.jsp?link_id=456789&mobile=1375333&msg=512
System.out.println("hb.mo 1002-------------------");
String linkid=request.getParameter("link_id")==null?"":request.getParameter("link_id");
if(linkid.equals("")){
	System.out.println("hb.mo linkid is null");
	return;
}

String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
String msg=request.getParameter("msg")==null?"":java.net.URLEncoder.encode(request.getParameter("msg"));
if(azul.KeyTool.mo(linkid,"1002",1,mobile,msg,"")){
	String serviceid=request.getParameter("svcode")==null?"":request.getParameter("svcode");
	String msg_id=request.getParameter("msg_id")==null?"":request.getParameter("msg_id");
	String spnum=request.getParameter("spnum")==null?"":request.getParameter("spnum");
	String url="http://210.51.0.191/mt/SendServlet?ip="+request.getRemoteAddr()+"&cp_id=243&cp_pass=&union_id=243&item_id=243&backurl=&dstmobile="+mobile+"&feemobile="+mobile+"&msg="+msg+"&svcode="+serviceid+"&spnum="+spnum+"&link_id="+linkid+"&msg_id="+msg_id;
	azul.KeyTool.send(url);
	out.println("OK");
}
else{
	out.println("ERR");
}
%>