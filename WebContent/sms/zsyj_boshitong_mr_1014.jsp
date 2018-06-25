<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/sp/sms/zsyj_boshitong_mr_1014.jsp?linkid=1234567azs&flag=1
//http://114.80.96.28:85/yuanjin.aspx?linkid=1234567azs&mobile=13751180191&spNum=1062880892&momsg=CX
System.out.println("boshitong.mr 1014-------------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("boshitong.mr linkid is null");
	return;
}
String msg=request.getParameter("flag")==null?"":request.getParameter("flag");
String mobile=request.getParameter("userphone")==null?"":request.getParameter("userphone");
if(azul.SpTool.mr("1014",linkid,mobile,"",msg,"1")){
	out.println("OK");
}
else{
	out.println("ERR");
}
%>