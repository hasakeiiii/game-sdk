<%@ page contentType="text/html;charset=gb2312"%>
<%
/*
?MO_Msg=KTXF2&Dest_Id=1065574313&statphone=13118673581&result=0&stat=0&Mt_Time=20110824144102&Linkid=28120847
MO_Msg		上行指令
Dest_Id		上行端口
statphone	电话号码
result          下发状态
stat            成功的状态报告值：移动：DELIVRD 联通：0 电信：DeliveredToTerminal
Linkid          唯一标识
Mt_Time		状态报告返回时间（YYYYMMDDHHmmss）
*/
System.out.println("xiangming.mr 1020-------------------");
String linkid=request.getParameter("Linkid")==null?"":request.getParameter("Linkid");
if(linkid.equals("")){
	System.out.println("xiangming.mr linkid is null");
	return;
}
String msg=request.getParameter("stat")==null?"":request.getParameter("stat");
if(azul.SpTool.mr("1020",linkid,"","",msg,"DELIVRD")){
	out.println("OK");
}
else{
	out.println("ERR");
}
if("000".equals(azul.SpTool.getInfoByLinkId(linkid)[1])){
	azul.KeyTool.send("http://m.ztytech.com.cn/gate/zy17/Mr.jsp?"+request.getQueryString());
}
%>