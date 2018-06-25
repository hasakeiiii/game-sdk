<%@page import="azul.JspUtil"%>
<%@page import="java.net.URLDecoder"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
	int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id = azul.JspUtil.getInt(request.getParameter("id"),0);
String fileName = azul.JspUtil.getStr(request.getParameter("file_name"),"");
String version = azul.JspUtil.getStr(request.getParameter("version"),"");
String msgcont =  azul.JspUtil.getStr(request.getParameter("game_id"),"");
String gameName ="";
gameName = URLDecoder.decode(gameName, "UTF-8");
GamepackDao apkdao=new GamepackDao();

String act_flag="-1";
String msg="操作失败";
String toPage="cfgCPApkRunList.jsp?pageno="+pageno;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
if(op.equals("add")){ 

    String inDate = DateUtil.getDateTime();//获取当前时间
	Gamepack ap = new Gamepack();
	ap.setGameId(msgcont);
	ap.setVersion(version);
	ap.setFileName(fileName);
	ap.setTime(inDate);
	apkdao.add(ap);
	act_flag = "1";

}else if(op.equals("edit")){ 
    String inDate = DateUtil.getDate();//获取当前时间
 	Gamepack apk = (Gamepack)apkdao.getById(id);
	apk.setSole(1);
	act_flag = apkdao.edit(apk);

	 
}
%>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
if("<%=act_flag%>"=="-1"){
    error("<%=msg%>",callback);
}
else{
	ok("操作成功",callback);
}
function callback(){
	location="<%=toPage%>";
}
</script>
</body>
</html>