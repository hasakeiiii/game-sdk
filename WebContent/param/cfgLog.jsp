<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.sql.*" %>
<%
out.print("<wml><structs result=\"true\"/></wml>");
/*
String param=azul.JspUtil.getStr(request.getParameter("type"),"");
org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(getClass()); 
logger.debug("veeya_log="+param);
Connection conn = null;
PreparedStatement pstmtA = null;
try {
	conn = db.ConnectionFactory.getInstance().getConnection();
	pstmtA = conn.prepareStatement("insert into cfg_log (param,date_time) values (?,now())");
	pstmtA.setString(1,param);
	pstmtA.executeUpdate();
} catch (Exception e) {
	System.err.println("cfg_log insert error");
} finally {
	db.ConnectionFactory.close(pstmtA,conn);
}
*/
%>