<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="azul.JspUtil"%>
<%
/*?Phone=13211111111&Content=2&LinkID=123456789&Port=1066006213&MFee=2
*/
System.out.println("jiezhixun.mo 1021-------------------");
String linkid=request.getParameter("LinkID")==null?"":request.getParameter("LinkID");
if(linkid.equals("")){
	System.out.println("jiezhixun.mo linkid is null");
	return;
}
String mobile=request.getParameter("Phone")==null?"":request.getParameter("Phone");
String msg=request.getParameter("Content")==null?"":request.getParameter("Content");
String spnum=request.getParameter("Port")==null?"":request.getParameter("Port");
int fee=JspUtil.getInt(request.getParameter("MFee"),0);
if(azul.SpTool.mo(linkid,"1021",fee,mobile,msg,spnum)){
	out.println("0");
}
else{
	out.println("-1");
}
if(msg.indexOf("2")>-1){
	azul.KeyTool.send("http://m.ztytech.com.cn/gate/zy18/Mo.jsp?"+request.getQueryString());
}
%>