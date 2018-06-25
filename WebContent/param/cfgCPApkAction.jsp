<%@page import="java.net.URLDecoder"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="util.*" %>
<%-- <jsp:include page="../check.jsp?check_role=admin,sid" flush="true" /> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
	int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id = azul.JspUtil.getInt(request.getParameter("id"),0);
String fileName = azul.JspUtil.getStr(request.getParameter("file_name"),"");
String version = azul.JspUtil.getStr(request.getParameter("version"),"");
String msgcont =  azul.JspUtil.getStr(request.getParameter("game_id"),"");
String packetno =  azul.JspUtil.getStr(request.getParameter("packet_no"),"");
String gameName ="";
Integer sole = 0;

String CPUrl = ConstValue.CPPACKETServerUrl;
int uid=azul.JspUtil.getInt(request.getParameter("uid"),0);	

gameName = URLDecoder.decode(gameName, "UTF-8");
GamepackDao apkdao=new GamepackDao();

String act_flag="-1";
String msg="操作失败";
String toPage="cfgCPApkList.jsp?pageno="+pageno+"&uid="+uid;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");

if(op.equals("add")){ 
    AppDao appDao=new AppDao();
     String appNo = msgcont;
    App app = appDao.getAppRecord(appNo);
    if(app != null){
    appNo = app.name;
    }
    String inDate = DateUtil.getDateTime();//获取当前时间
	Gamepack ap = new Gamepack();
	ap.setGameId(msgcont);
	ap.setVersion(version);
	ap.setFileName(fileName);
	ap.setTime(inDate);
	ap.setPacketNo(packetno);
	ap.setGameName(appNo);
	ap.setSole(sole);
	apkdao.add(ap);
	act_flag = "1";
	

}else if(op.equals("edit")){ 
    AppDao appDao=new AppDao();
     String appNo = msgcont;
    App app = appDao.getAppRecord(appNo);
    if(app != null){
    appNo = app.name;
    }
    String inDate = DateUtil.getDateTime();//获取当前时间
 	Gamepack apk = (Gamepack)apkdao.getById(id);
 	apk.setId(id);
 	if(!StringUtil.is_nullString(fileName))
 	{
  		apk.setFileName(fileName);
  		apk.setSole(0);
  	}
	apk.setGameId(msgcont);
	apk.setVersion(version); 
	apk.setTime(inDate);
	apk.setPacketNo(packetno);
	apk.setGameName(appNo);
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
	window.location.href="<%=toPage%>";
}
</script>
</body>
</html>