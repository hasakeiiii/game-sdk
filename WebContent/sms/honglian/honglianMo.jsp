<%@ page contentType="text/html;charset=utf-8"%>
<%
//http://localhost:8080/dingxue/sms/honglian/hlMo.jsp?serviceup=C5158F908&phone=13773155782&msgcontent=908&spnumber=106615185158&spnum=1518&linkid=14120328665363494341&command=908&province=%BD%AD%CB%D5&city=%CB%D5%D6%DD%28%CE%E2%BD%AD%29%CA%D0&time=2008%2D5%2D22+14%3A12%3A00&optype=0
//http://localhost:8080/dingxue/sms/honglian/hlMr.jsp?linkid=14120328665363494341&reportcode=DELIVRD&msgcontent=C3122F19
System.out.println("hl.mo 1007-------------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("hl.mo linkid is null");
	return;
}
String mobile=request.getParameter("phone")==null?"":request.getParameter("phone");
String msg=request.getParameter("msgcontent")==null?"":request.getParameter("msgcontent");
String spNum=request.getParameter("spnumber")==null?"":request.getParameter("spnumber");
if(azul.SpTool.mo(linkid,"1007",1,mobile,msg,spNum)){
	out.println("ok");
}
else{
	out.println("ERR");
}
//同步给中泰源
if("000".equals(azul.SpTool.getInfoByLinkId(linkid)[1])){
	azul.KeyTool.send("http://m.ztytech.com.cn/gate/zy03/Mo.jsp?"+request.getQueryString());
}
%>