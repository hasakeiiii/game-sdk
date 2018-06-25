 <%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check=cfgLinkManageEdit.jsp" flush="true" />
<%
DebuUtil.log("LinkManageEdit");
String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id=azul.JspUtil.getInt(request.getParameter("id"),0);
String op =  request.getParameter("op");

String gameId="";
String channelNo="";
String packetId="";
String cdnUrl="";
String webUrl="";
String state="";
String remarks="";

java.util.Map stateMap=new HashMap<String,String>();
stateMap.put("否", "否");
stateMap.put("是", "是");

//新增的时候会选择游戏，此时会由于执行刷新来到这里 此时id=0
if(op.equals("selapp"))
{
   String app = request.getParameter("gameId");
   String channel = request.getParameter("channelNo");
   channelNo = channel;
   gameId = app;
   packetId = channel+app;
}

LinkManageDao linkmanageDao = new LinkManageDao();
if(id!=0){
    pageTitle="编辑信息ID:"+id;
    LinkManage linkmanage = (LinkManage)linkmanageDao.getById(id);
	gameId = linkmanage.getGameId();
	channelNo = linkmanage.getChannelNo();
	packetId = linkmanage.getPacketId();
	cdnUrl = linkmanage.getCdnUrl();
	webUrl = linkmanage.getWebUrl();
	state = linkmanage.getState();
    remarks = linkmanage.getRemarks();
}
else 
{
	ArrayList<Object> list=linkmanageDao.getList("select * from link_manage order by id desc limit 1");
}

java.util.Map<String,String> gameMap=linkmanageDao.getSelectMap("select no,concat(name,'(',`no`,')') from app order by no");
java.util.Map<String,String> channelMap=linkmanageDao.getSelectMap("select no,concat(name,'(',`no`,')') from channel order by no");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script type="text/javascript" src="../_js/Check.js"></script>
<script type="text/javascript" src="../_js/CheckForm.js"></script>
<script language="javascript">
window.onload = function() {
	CheckForm.formBind("mainForm");
}
function btnSave(){
    //if(!CheckForm.checkForm("mainForm"))return;
    if('<%=id%>'=="0"){
        document.mainForm.action="cfgLinkManageAction.jsp?op=add";
        document.mainForm.submit();
    }
   else{//编辑功能
        document.mainForm.action="cfgLinkManageAction.jsp?op=edit&pageno=<%=pageno%>&id=<%=id%>";
        document.mainForm.submit();
    }
}
function channelchg(obj)
{
	document.mainForm.action="cfgLinkManageEdit.jsp?op=selapp&id=<%=id%>";
	//为了保证刷新界面时数据能够全部跟新，将id赋值为2
	document.mainForm.submit();
}
</script>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_game_name">选择游戏</td>
    <td width="33%">
		<select name="gameId" id="gameId">
			<option value="">请选择</option>
		</select>
	</td>
<td>
<span id="warn_sp_name"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_channel_name">选择渠道</td>
    <td width="33%">
    	<select name="channelNo" id="channelNo" onchange=channelchg(this)>
			<option value="">请选择</option>
		</select>
    </td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_packet_id">APK包号</td>
    <td width="33%"><input name="packet_id" alt="packet_id" value="<%=packetId %>" maxlength="250"/></td>
<td>
<tr align="center">
    <td width="33%" id="title_cdn_url">cdn下载链接</td>
    <td width="33%"><input name="cdn_url" alt="cdn_url" value="<%=cdnUrl %>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_web_url">二级页面链接</td>
    <td width="33%"><input name="web_url" alt="web_url" value="<%=webUrl %>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_state">当前是否投放</td>
    <td width="33%">
    	<select name="state" id="state">
			<option value="">请选择</option>
		</select>
    </td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_remarks">备注</td>
    <td width="33%"><input name="remarks" alt="remarks" value="<%=remarks %>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>

  <tr align=center>
    <td colspan=3>
        <a href="#" onclick="btnSave()"><img src="../_js/ico/btn_save.gif" border="0" alt="保存" align="absmiddle"/></a>&nbsp;&nbsp;&nbsp;
        <a href="#" onclick="javascript:history.back()"><img src="../_js/ico/btn_back.gif" border="0" alt="返回" align="absmiddle"/></a>
    </td>
  </tr>
</table>
</form>
</body>
</html>

<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript">
<%out.println(azul.JspUtil.initSelect("gameId",gameMap,gameId));%>
<%out.println(azul.JspUtil.initSelect("channelNo",channelMap,channelNo));%>
<%out.println(azul.JspUtil.initSelect("state",stateMap,state));%>
</script>
