<%@ page contentType="text/html;charset=gb2312"%>
<%
/*?link_id=xxx& mo=xxx&mo_to=xxx& msg=xxx
LINKID link_id
手机号码 mo
端口号 	mo_to
指令信息 msg
*/
System.out.println("xinqing.mo 1023-------------------");
String linkid=request.getParameter("link_id")==null?"":request.getParameter("link_id");
if(linkid.equals("")){
	System.out.println("xinqing.mo linkid is null");
	return;
}
String mobile=request.getParameter("mo")==null?"":request.getParameter("mo");
String msg=request.getParameter("msg")==null?"":request.getParameter("msg");
String spnum=request.getParameter("mo_to")==null?"":request.getParameter("mo_to");
int fee=1;
if(azul.SpTool.mo(linkid,"1023",fee,mobile,msg,spnum)){
	out.println("OK");
}
else{
	out.println("ERR");
}
//if(msg.indexOf("11")>-1){
//	azul.KeyTool.send("http://m.ztytech.com.cn/gate/zy17/Mo.jsp?"+request.getQueryString());
//}
%>