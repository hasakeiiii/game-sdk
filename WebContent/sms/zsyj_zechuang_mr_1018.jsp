<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/sp/sms/zsyj_boshitong_mr_1014.jsp?linkid=1234567azs&flag=1
//http://114.80.96.28:85/yuanjin.aspx?linkid=1234567azs&mobile=13751180191&spNum=1062880892&momsg=CX
System.out.println("zechuang.mr 1018-------------------");
String linkid=request.getParameter("Linkid")==null?"":request.getParameter("Linkid");
if(linkid.equals("")){
	System.out.println("zechuang.mr linkid is null");
	return;
}
String msg=request.getParameter("stat")==null?"":request.getParameter("stat");
String mobile=request.getParameter("statphone")==null?"":request.getParameter("statphone");
String flag="DELIVRD"; 
if(azul.KeyTool.getWap("",mobile)==2){
   flag="0"; 
}
if(azul.SpTool.mr("1018",linkid,mobile,"",msg,flag)){
	out.println("OK");
}
else{
	out.println("ERR");
}
%>