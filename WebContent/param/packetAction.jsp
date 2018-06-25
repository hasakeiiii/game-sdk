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
String packetNo = azul.JspUtil.getStr(request.getParameter("packet_no"),"");
String packetId = azul.JspUtil.getStr(request.getParameter("packet_id"),"");
String preVersion = "";

GamepackDao gpDao = new GamepackDao();
preVersion = gpDao.getRecordByPackNo(packetNo).getVersion();

PacketDao pDao=new PacketDao();

String act_flag="-1";
String msg="操作失败";
String toPage="packetList.jsp?pageno="+pageno;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
if(op.equals("add")){ 
	
	Packet packet = new Packet();
	packet.setPacketId(packetId);
	packet.setPacketNo(packetNo);
	packet.setPreVersion(preVersion);
	boolean hasone = pDao.checkByPacketId(packetId);
	if(!hasone){
		int ret= Integer.parseInt(pDao.add(packet));
		if(ret == 1)
		{
	   		act_flag="1";
		}
	}else{
		msg="游戏包号添加重复";
	}
}
else if(op.equals("edit")){ 
	Packet packet = (Packet)pDao.getById(id);
	
	act_flag = pDao.edit(packet);
	
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