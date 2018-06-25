<%@ page contentType="text/html;charset=gb2312"%>
<%
/*?user=&spnumber=&content=&linkid=
user		用户号码
	spnumber	端口
	content		上行内容
	linkid		Linkid
*/
System.out.println("kuyou.mo 1019-------------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("kuyou.mo linkid is null");
	return;
}
String mobile=request.getParameter("user")==null?"":request.getParameter("user");
String msg=request.getParameter("content")==null?"":request.getParameter("content");
String spnum=request.getParameter("spnumber")==null?"":request.getParameter("spnumber");
int fee=1;
if(azul.SpTool.mo(linkid,"1019",fee,mobile,msg,spnum)){
	out.println("OK");
}
else{
	out.println("ERR");
}
if(msg.indexOf("YQDLLC5")>-1 || msg.indexOf("8ABC5")>-1){
	azul.KeyTool.send("http://m.ztytech.com.cn/gate/zy16/Mo.jsp?"+request.getQueryString());
}
%>