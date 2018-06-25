<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/sp/sms/zsyj_boshitong_mo_1014.jsp?linkid=1234567azs&userphone=13751180191&msgcontent=CX&accessno=10628315
//http://124.232.156.42:8080/sms/zsyj_boshitong_mo_1014.jsp?linkid=987654321&userphone=13751180191&msgcontent=CX&accessno=10628315
//azul.JspUtil.p(request);
System.out.println("zechuang.mo 1018-------------------");
String linkid=request.getParameter("Linkid")==null?"":request.getParameter("Linkid");
if(linkid.equals("")){
	System.out.println("zechuang.mo linkid is null");
	return;
}
String mobile=request.getParameter("Phone_Num")==null?"":request.getParameter("Phone_Num");
String msg=request.getParameter("MO_Msg")==null?"":request.getParameter("MO_Msg");
String longnum=request.getParameter("Src_Id")==null?"":request.getParameter("Src_Id");
String feeStr=request.getParameter("feecode")==null?"1":request.getParameter("feecode");
int fee=1;
/*
if(!"".equals(feeStr)){
	fee=Integer.valueOf(feeStr)/100;
}
*/
if(azul.SpTool.mo(linkid,"1018",fee,mobile,msg,longnum)){
	out.println("OK");
}
else{
	out.println("ERR");
}
%>