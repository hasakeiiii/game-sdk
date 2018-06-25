<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<%@ page import="net.sf.json.JSONObject" %>
<%
String login_account=azul.JspUtil.getStr(request.getParameter("login_account"),"");
String password=azul.JspUtil.getStr(request.getParameter("password"),"");
String packet_id=azul.JspUtil.getStr(request.getParameter("packet_id"),"");
String game_id=azul.JspUtil.getStr(request.getParameter("game_id"),"");
String device_id=azul.JspUtil.getStr(request.getParameter("device_id"),"");

if(StringUtil.is_nullString(packet_id))
{
   packet_id = "100299001";
}
if(StringUtil.is_nullString(game_id))
{
   packet_id = "299";
}

if(StringUtil.is_nullString(device_id))
{
   device_id = "";
}

JSONObject rsqjson = new JSONObject();          
rsqjson.put("login_account", login_account);
rsqjson.put("password", password);
rsqjson.put("device_id", device_id);
rsqjson.put("packet_id", packet_id);
rsqjson.put("game_id", game_id);
model.Login obj = new model.Login(rsqjson);
LoginDao dao = new LoginDao();
int ret = dao.login(obj);//

String rsq="";
if(ret == ConstValue.OK)
{
    rsq="success";
}
else
{
   rsq="fail";
}
out.println(rsq);
%>

