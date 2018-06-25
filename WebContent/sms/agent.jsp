<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.sql.*" %>
<%
//azul.JspUtil.p(request);
String cid=request.getParameter("cid")==null?"":request.getParameter("cid");
String type=request.getParameter("cid")==null?"":request.getParameter("type");
String num=request.getParameter("num")==null?"":request.getParameter("num");
String fee=request.getParameter("fee")==null?"":request.getParameter("fee");
String date_time=request.getParameter("date_time")==null?"":request.getParameter("date_time");
//linkid="14332005613389231248";status="DELIVRD";

Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;
try {
	conn = db.ConnectionFactory.getInstance().getConnection();
	pstmt = conn.prepareStatement("insert into charge_agent (cid,num,fee,date_time,type) values (?,?,?,?,?)");
	pstmt.setString(1,cid);
	pstmt.setString(2,num);
	pstmt.setString(3,fee);
	pstmt.setString(4,date_time);
	pstmt.setString(5,type);
	pstmt.executeUpdate();
	System.out.println("同步费用成功:cid:"+cid+"  date_time:"+date_time);
	out.print("ok");
} catch (Exception e) {
	e.printStackTrace();
}
finally {
	db.ConnectionFactory.close(rs,pstmt,conn);
}
%>