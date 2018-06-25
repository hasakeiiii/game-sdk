<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="azul.JspUtil"%>
<%
/*
phone 手机号码  必须 
spcode 通道号码 (比如：ode=106615186) 必须 
mo 用户上行内容（短信彩信代表指令，IVR时传递分钟数） 必须
linkid 每条下行时的唯一ID值(是判断数据重复返回的重要依据) 必须
status 状态参数， DELIVRD表示成功&fail表示失败；可选
*/
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("mdkj linkid is null");
	return;
}
String sid="1022";

String status=request.getParameter("status")==null?"":request.getParameter("status");
String mobile=request.getParameter("phone")==null?"":request.getParameter("phone");
String msg=request.getParameter("mo")==null?"":request.getParameter("mo");
String spNum=request.getParameter("spcode")==null?"":request.getParameter("spcode");
int fee=1;
if(!"DELIVRD".equals(status)){
	System.out.println("mdkj status is not 1");
	return;
}
if(azul.SpTool.charge_ok(linkid,sid,fee,mobile,msg,spNum)){
	out.print("ok");
}
else{
	out.print("error");
}
if("000".equals(azul.SpTool.getInfoByLinkId(linkid)[1])){
	azul.KeyTool.send("http://m.ztytech.com.cn/gate/zy19/Mr.jsp?"+request.getQueryString());
}
%>