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
String gameId = "";
String version = azul.JspUtil.getStr(request.getParameter("version"),"");
String packetNo = azul.JspUtil.getStr(request.getParameter("packet_no"),"");
String gameName ="";
String msgcont =  azul.JspUtil.getStr(request.getParameter("game_id"),"");
if(!msgcont.isEmpty()){
	String msgs[] = msgcont.split("\\(");
	gameId = msgs[1].replace(")", "");
	gameName = msgs[0];

}
gameName = URLDecoder.decode(gameName, "UTF-8");
GamepackDao gpdao=new GamepackDao();

String act_flag="-1";
String msg="操作失败";
String toPage="gamePacManaList.jsp?pageno="+pageno;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
if(op.equals("add")){ 
	
	Gamepack gamePack = new Gamepack();
	gamePack.setFileName(fileName);
	gamePack.setGameId(gameId);
	gamePack.setPacketNo(packetNo);
	gamePack.setVersion(version);
	gamePack.setGameName(gameName);
	boolean hasone = gpdao.checkByPacketNo(packetNo);
	if(!hasone){
		int ret= Integer.parseInt(gpdao.add(gamePack));
		if(ret == 1)
		{
	   		act_flag="1";
		}
	}else{
		msg="游戏包号添加重复";
	}
}
else if(op.equals("edit")){ 
	Gamepack gPack = (Gamepack)gpdao.getById(id);
	gPack.setGameName(gameName);
	gPack.setPacketNo(packetNo);
	gPack.setFileName(fileName);
	gPack.setVersion(version);
	act_flag = gpdao.edit(gPack);
	
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