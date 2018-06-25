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
int id=azul.JspUtil.getInt(request.getParameter("id"),0);
String gameId=azul.JspUtil.getStr(request.getParameter("gameId"),"");
String channelNo=azul.JspUtil.getStr(request.getParameter("channelNo"),"");
String packetId=azul.JspUtil.getStr(request.getParameter("packet_id"),"");
String cdnUrl=azul.JspUtil.getStr(request.getParameter("cdn_url"),"");
String webUrl=azul.JspUtil.getStr(request.getParameter("web_url"),"");
String state=azul.JspUtil.getStr(request.getParameter("state"),"");
String remarks=azul.JspUtil.getStr(request.getParameter("remarks"),"");

LinkManageDao linkmanageDao=new LinkManageDao();
String act_flag="-1";
String msg="操作失败";
String toPage="cfgLinkManageList.jsp?pageno="+pageno;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");

LinkManage linkmanage = new LinkManage();
linkmanage.setId(id);
linkmanage.setGameId(gameId);
linkmanage.setChannelNo(channelNo);
linkmanage.setPacketId(packetId);
linkmanage.setCdnUrl(cdnUrl);
linkmanage.setWebUrl(webUrl);
linkmanage.setState(state);
linkmanage.setRemarks(remarks);

if(op.equals("add")){ 
	act_flag=linkmanageDao.addItem(linkmanage);
}
else if(op.equals("edit")){ 
	linkmanage.setId(id);
	act_flag=linkmanageDao.edit(linkmanage);
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