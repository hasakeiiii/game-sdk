<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.sql.*" %>
<%
//http://119.147.23.178:8080/sms/yisou/yisouMr.jsp?sid=123456789&status=success
//http://124.232.156.42:8080/sms/esou/yisouMr.jsp?sid=123456789&status=success
//common.JspUtil.p(request);
System.out.println("yisou.mr 1006-------------");
String linkid=azul.JspUtil.getStr(request.getParameter("sid"),"");
if(linkid.equals("")){
	System.out.println("yisou.linkid is null");
	return;
}
else if(linkid.indexOf(",")>-1){
    linkid=linkid.substring(0,linkid.indexOf(","));
}
String msg=azul.JspUtil.getStr(request.getParameter("status"),"");
if(azul.SpTool.mr("1003",linkid,"","",msg,"success")){
	out.println("0");
	Connection connA = null;
	PreparedStatement pstmtA = null;
	ResultSet rsA=null;
	try {
		connA = db.ConnectionFactory.getInstance().getConnection();
		pstmtA = connA.prepareStatement("select cid from charge where linkid=?");
		pstmtA.setString(1,linkid);
		rsA=pstmtA.executeQuery();
		if(rsA.next()){
			String cid=rsA.getString(1);
			//if("000".equals(cid)){
			//	azul.SpTool.send("http://m.ztytech.com.cn/gate/easou/easouMr.jsp?"+request.getQueryString());
			//}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally {
		db.ConnectionFactory.close(rsA,pstmtA,connA);
	}
}
else{
	out.println("1");
}
%>