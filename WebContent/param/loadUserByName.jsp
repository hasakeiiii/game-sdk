<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<%@ page import="net.sf.json.JSONObject" %>
<%
String username = azul.JspUtil.getStr(request.getParameter("username"),"");
LoginDao dao = new LoginDao();

Login login = dao.getRecord(username);
JSONObject jo = new JSONObject();


if(login != null){
	jo.put("result",1);
	String password = login.getPass();
	String md5_pwd = Rsa.getMD5(password);
	Integer accountId = login.getId();
	jo.put("password",md5_pwd);
	jo.put("accountId",accountId);
}else{
	jo.put("result",-1);
}

out.print(jo.toString());

%>

