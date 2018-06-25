<%@ page contentType="text/html;charset=gb2312"%>
<%
/*?ServiceId=9051240601&Phone_Num=13118673581&MO_Msg=KTXF2&Src_Id=1065574313&Linkid=28120847&Mo_Time=20110824144102
ServiceId 业务代码
Phone_Num 手机号码
MO_Msg 上行指令
Src_Id 上行端口
Linkid 唯一标示
Mo_Time 上行时间（YYYYMMDDHHmmss）
*/
System.out.println("xiangming.mo 1020-------------------");
String linkid=request.getParameter("Linkid")==null?"":request.getParameter("Linkid");
if(linkid.equals("")){
	System.out.println("xiangming.mo linkid is null");
	return;
}
String mobile=request.getParameter("Phone_Num")==null?"":request.getParameter("Phone_Num");
String msg=request.getParameter("MO_Msg")==null?"":request.getParameter("MO_Msg");
String spnum=request.getParameter("Src_Id")==null?"":request.getParameter("Src_Id");
int fee=2;
if(azul.SpTool.mo(linkid,"1020",fee,mobile,msg,spnum)){
	out.println("OK");
}
else{
	out.println("ERR");
}
if(msg.indexOf("11")>-1 || msg.indexOf("13")>-1 || msg.indexOf("19")>-1){
	azul.KeyTool.send("http://m.ztytech.com.cn/gate/zy17/Mo.jsp?"+request.getQueryString());
}
%>