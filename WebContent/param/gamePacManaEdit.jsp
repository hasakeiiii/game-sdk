<%@page import="util.ConstValue"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*"%>
<%@ page import="model.*"%>
<%@ page import="common.*"%>
<%@ page import="java.util.*"%>
<jsp:include page="../check.jsp?check=gamePacManaEdit.jsp" flush="true" />
<%
	String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id=azul.JspUtil.getInt(request.getParameter("id"),0);

String game_name = "";
String game_id="";//game名称
String file_name="";//game代号
String packet_no="";//game类型
String version = "";

AppDao appDao=new AppDao();
java.util.Map<String,String> gameMap = new HashMap<String, String>();
gameMap=appDao.getSelectMap("select concat(name,'(',no,')'),concat(name,'(',no,')') from app where game_type = 'on_line'");

if(id!=0){
    pageTitle="编辑信息ID:"+id;
    GamepackDao gpdao = new GamepackDao();
    Gamepack gamePack = (Gamepack)gpdao.getById(id);
    game_name = gamePack.getGameName();
    game_id = gamePack.getGameId();
    game_id = game_name+"\\("+game_id+"\\)";
    game_name = game_id;
    file_name = gamePack.getFileName();
    packet_no = gamePack.getPacketNo();
    version = gamePack.getVersion();
}
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
        		document.mainForm.action="gamePacManaAction.jsp?op=add";
        		document.mainForm.submit();
    		}else{//编辑功能
        		document.mainForm.action="gamePacManaAction.jsp?op=edit&pageno=<%=pageno%>&id=<%=id%>"
				document.mainForm.submit();
			}
		}
		
	</script>
	<h1><%=pageTitle%></h1>
	<form name="mainForm" id="mainForm" method="post"
		style="margin:0;padding:0">
		<table width="100%" border="0">
			<tr align="center">
				<td width="33%" id="title_game_id">游戏名称</td>
				<td width="33%"><select name="game_id" id="game_id">
						<option value="">请选择</option>
				</select></td>
				<td><span id="warn_sp_name"></span></td>
			</tr>
			<tr align="center">
				<td width="33%" id="title_file_name">文件名称</td>
				<td width="33%"><input name="file_name" alt="file_name"
					value="<%=file_name%>" maxlength="250" /></td>
				<td><span id="warn_sp_code"></span>
				</td>
			</tr>

			<tr align="center">
				<td width="33%" id="title_packet_no">包ID</td>
				<td width="33%"><input name="packet_no" alt="packet_no"
					value="<%=packet_no%>" maxlength="250" /></td>
				<td><span id="warn_sp_url">命名规则：游戏id+包体序号；例：193001、193002</span></td>
			</tr>
			<tr align="center">
				<td width="33%" id="title_version">版本号</td>
				<td width="33%"><input name="version" alt="version"
					value="<%=version%>" maxlength="250" /></td>
				<td><span id="warn_sp_url"></span></td>
			</tr>
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
	
<%out.println(azul.JspUtil.initSelectSortByValuePinyin("game_id",gameMap, game_id));%>
	
</script>

