<%@page import="azul.JspUtil"%>
<%@page import="util.ConstValue"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*"%>
<%@ page import="model.*"%>
<%@ page import="common.*"%>
<%@ page import="java.util.*"%>
<jsp:include page="../check.jsp?check=packetEdit.jsp" flush="true" />
<%
	String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id=azul.JspUtil.getInt(request.getParameter("id"),0);
String packetNo = azul.JspUtil.getStr(request.getParameter("packet_no"),"");
String channelNo = azul.JspUtil.getStr(request.getParameter("channel_no"),"");
String packetId = azul.JspUtil.getStr(request.getParameter("packet_id"),"");
String gameId = "";
if(!packetNo.isEmpty())
	gameId = packetNo.substring(0,3);



GamepackDao gpDao=new GamepackDao();
Gamepack gamePack = gpDao.getRecordByPackNo(packetNo);
if(gamePack != null)
{
   gameId = gamePack.getGameId();
}
java.util.Map<String,String> packetNoMap = new HashMap<String, String>();
packetNoMap=gpDao.getSelectMap("select packet_no,concat(game_name,'(',packet_no,')') from gamepack where 1=1");

CooperationDao cpDao = new CooperationDao();
java.util.Map<String,String> channeNameMap = new HashMap<String, String>();
String channelNamesql = "select channel_no,concat(name,'(',channel_no,')') from cooperation,channel"+
								" where cooperation.channel_no=channel.`no`";
if(!gameId.isEmpty())
	channelNamesql=String.format(channelNamesql+" and app_no='%s'",gameId);
channeNameMap = cpDao.getSelectMap(channelNamesql);

if(id!=0){
    
}
else
{
	
}

GamepackDao gameDao=new GamepackDao();
java.util.Map<String,String> packetbagMap = new HashMap<String, String>();
String packIdSql = "select packet_id,concat(packet_id) from cooperation where channel_no='"+channelNo+"' and app_no='"+gameId+"'";
packetbagMap=gameDao.getSelectMap(packIdSql);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css" />

<style>
#dj {
	display: none;
}
</style>

</head>
<body>
	<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css"
		media="screen" />
	<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
	<script type="text/javascript" src="../_js/Check.js"></script>
	<script type="text/javascript" src="../_js/CheckForm.js"></script>
	<script language="javascript">
		window.onload = function() {
			CheckForm.formBind("mainForm");
		}

		function btnSave() {
			if(!CheckForm.checkForm("mainForm"))return;
			
    		if('<%=id%>'=="0"){
        		document.mainForm.action="packetAction.jsp?op=add";
        		document.mainForm.submit();
    		}else{//编辑功能
        		document.mainForm.action="packetAction.jsp?op=edit&pageno=<%=pageno%>&id=<%=id%>"
				document.mainForm.submit();
			}
		}
		
		function oncnochan(obj){
			document.mainForm.action="packetEdit.jsp?pageno=<%=pageno%>&id=<%=id%>";
			document.mainForm.submit();
		}
		function onpnochan(obj){
			document.mainForm.action="packetEdit.jsp?pageno=<%=pageno%>&id=<%=id%>";
			document.mainForm.submit();
		}
		function bagid(obj){
			document.mainForm.action="packetEdit.jsp?pageno=<%=pageno%>&id=<%=id%>";
			document.mainForm.submit();
		}
	</script>
	<h1><%=pageTitle%></h1>
	<form name="mainForm" id="mainForm" method="post"
		style="margin:0;padding:0">
		<table width="100%" border="0">
			<tr align="center">
				<td width="33%" id="title_packet_no">母包</td>
				<td width="33%"><select name="packet_no" id="packet_no" onchange="onpnochan(this)">
						<option value="">请选择</option>
				</select></td>
				<td><span id="warn_sp_name"></span></td>
			</tr>
			<tr align="center">
				<td width="33%" id="title_channel_no">渠道</td>
				<td width="33%"><select name="channel_no" id="channel_no" onchange="oncnochan(this)">
						<option value="">请选择</option>
				</select></td>
				<td><span id="warn_sp_code"></span>
				</td>
			</tr>
			<tr align="center">
				<td width="33%" id="title_packet_id">包ID</td>
				<td width="33%"><select name="packet_id" id="packet_id" onchange="bagid(this)">
						<option value="">请选择</option>
				</select></td>
				<td><span id="warn_sp_url"></span>
				</td>
			</tr>

<!-- 			<tr align="center"> -->
<!-- 				<td width="33%" id="title_packet_id">包ID</td> -->
<!-- 				<td width="33%"><input name="packet_id" alt="packet_id" -->
<!-- 					value="<%=packetId%>" maxlength="250" /></td> -->
<!-- 				<td><span id="warn_sp_url"></span></td> -->
<!-- 			</tr> -->
			<tr align=center>
				<td colspan=3>
				<a href="#" onclick="btnSave()"><img src="../_js/ico/btn_save.gif" border="0" alt="保存"
						align="absmiddle"/> </a>&nbsp;&nbsp;&nbsp; 
				<a href="#"onclick="javascript:history.back()"><img src="../_js/ico/btn_back.gif" border="0" alt="返回"
						align="absmiddle" /> </a>
				</td>
			</tr>
		</table>

	</form>
</body>
</html>

<script type="text/javascript" src="../_js/elemUtil.js"></script>

<script type="text/javascript">
	
<%out.println(azul.JspUtil.initSelectSortByValuePinyin("packet_no",packetNoMap, packetNo));%>
<%out.println(azul.JspUtil.initSelectSortByValuePinyin("channel_no",channeNameMap, channelNo));%>
<%out.println(azul.JspUtil.initSelectSortByValuePinyin("packet_id",packetbagMap, packetId));%>
</script>

