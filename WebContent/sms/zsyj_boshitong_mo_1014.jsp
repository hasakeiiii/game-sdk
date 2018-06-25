<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/sp/sms/zsyj_boshitong_mo_1014.jsp?linkid=1234567azs&userphone=13751180191&msgcontent=CX&accessno=10628315
//http://124.232.156.42:8080/sms/zsyj_boshitong_mo_1014.jsp?linkid=987654321&userphone=13751180191&msgcontent=CX&accessno=10628315
//azul.JspUtil.p(request);
System.out.println("boshitong.mo 1014-------------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("boshitong.mo linkid is null");
	return;
}
String mobile=request.getParameter("userphone")==null?"":request.getParameter("userphone");
String msg=request.getParameter("msgcontent")==null?"":request.getParameter("msgcontent");
String longnum=request.getParameter("accessno")==null?"":request.getParameter("accessno");
String feeStr=request.getParameter("feecode")==null?"1":request.getParameter("feecode");
int fee=1;
/*
if(!"".equals(feeStr)){
	fee=Integer.valueOf(feeStr)/100;
}
*/
if(azul.SpTool.mo(linkid,"1014",fee,mobile,msg,longnum)){
	out.println("OK");
}
else{
	out.println("ERR");
}
%>