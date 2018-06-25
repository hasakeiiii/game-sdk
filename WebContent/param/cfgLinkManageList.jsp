<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
DebuUtil.log("LinkManagelist");
LinkManageDao linkmanageDao=new LinkManageDao();
String op =  request.getParameter("op");
String keyWord=azul.JspUtil.getStr(request.getParameter("keyWord"),"");
String pageSql="select * from link_manage where 1=1";
if(!keyWord.equals("")){
	   pageSql+=" and name like '%"+keyWord+"%'";
	   DebuUtil.log("pageSql="+pageSql);
}
pageSql+=" order by id desc";
int pagesize=30;//每页记录数
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int recordcount=linkmanageDao.getRecordCount(pageSql);//得到记录总数
List<LinkManage> list=linkmanageDao.getList(pageno,pagesize,pageSql);

ChannelDao channelDao = new ChannelDao();
String strSQL = "select * from channel";
ArrayList channellist = channelDao.getList(strSQL);
String channel_name="";
String app_name="";

AppDao appDao = new AppDao();
strSQL = "select * from app";
ArrayList applist = appDao.getList(strSQL);


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<%
String game=request.getParameter("game");
String channelNo=request.getParameter("channel");
java.util.Map<String,String> gameMap=linkmanageDao.getSelectMap("select no,concat(name,'(',`no`,')') from app,link_manage where no=link_manage.game_id");

java.util.Map channelMap=new HashMap<String,String>();
String sql = String.format("select no,concat(name,'(',`no`,')') from channel,link_manage where no=link_manage.channel_no and game_id='%s'",game);
channelMap=linkmanageDao.getSelectMap(sql);

if((!StringUtil.is_nullString(op))&&(op.equals("sch"))){
	String schSql = "select * from link_manage";
	if(StringUtil.is_nullString(game)){
		DebuUtil.log(schSql);
		list=linkmanageDao.getList(pageno,pagesize,schSql);
	}else{
		schSql = schSql+ " where game_id='"+game+"'";
		if(StringUtil.is_nullString(channelNo)){
			DebuUtil.log(schSql);
			list=linkmanageDao.getList(pageno,pagesize,schSql);
		}else{
			schSql = schSql + " and channel_no='"+channelNo+"'";
			DebuUtil.log(schSql);
			list=linkmanageDao.getList(pageno,pagesize,schSql);
		}
	}
}
%>
<td width="15%">
	游戏&nbsp;&nbsp;
	<select name="game" id="game" onchange=gamechg(this)>
		<option value="">请选择</option>
	</select>&nbsp;&nbsp;&nbsp;&nbsp;
	渠道&nbsp;&nbsp;
	<select name="channel" id="channel">
		<option value="">请选择</option>
	</select>&nbsp;&nbsp;&nbsp;&nbsp;
	
	<a href="#" onclick="goSearch()""><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a></td>
<td width="10%" align="right">
	<a href="#" onclick="addAct()"><img src="../_js/ico/btn_add.gif" border="0" alt="增加" align="absmiddle"/></a>
</td>
</tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
	<td>序号</td>
	<td>游戏代号</td>
	<td>游戏名称</td>	
	<td>渠道代号</td>
	<td>渠道名称</td>
	<td>APK包号</td>
	<td>cdn下载链接</td>
	<td>二级页面链接</td>
	<td>当前投放状态</td>
	<td>备注</td>
	<td>编辑</td>
</tr>
<%
for(int i=0;i<list.size();i++){
    LinkManage linkmanage=(LinkManage)list.get(i);
    for(int j=0;j<channellist.size();j++)
	{	
		Channel channel=(Channel)channellist.get(j);
		if(channel.no.equals(linkmanage.channel_no))
		{
			channel_name = channel.name;
		}
	}
	
	for(int j=0;j<applist.size();j++)
	{
		App app=(App)applist.get(j);
		if(app.no.equals(linkmanage.game_id))
		{
			app_name = app.name;
		}
	}
%>
<tr>
	<td><%=linkmanage.getId()%></td>
	<td><%=linkmanage.getGameId()%></td>
	<td><%=channel_name %></td>
	<td><%=linkmanage.getChannelNo()%></td>
	<td><%=app_name %></td>
	<td><%=linkmanage.getPacketId()%></td>
	<td><a href="<%=linkmanage.getCdnUrl()%>"><%=linkmanage.getCdnUrl()%></a></td>
	<td><a href="<%=linkmanage.getWebUrl()%>"><%=linkmanage.getWebUrl()%></a></td>
	<td><%=linkmanage.getState()%></td>
	<td><%=linkmanage.getRemarks()%></td>
	<td>
	<a href="#" onclick="editAct('<%=linkmanage.getId()%>')"><img src="../_js/ico/btn_edit.gif" border="0" alt="修改"/></a>
	</td>
</tr>
<%
}
%>
</table>
</form>
<%
String linkParam="";
out.print(azul.JspUtil.getPageCode(pageno,pagesize,recordcount,request,linkParam));
%>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
function addAct(){
    window.location="cfgLinkManageEdit.jsp?op=edit";
}
function editAct(id){
    window.location="cfgLinkManageEdit.jsp?op=add&pageno=<%=pageno%>&id="+id;
}
function infoAct(id){
    //window.location="cfgSpInfo.jsp?id="+id;
}
function goSearch(){
    document.mainForm.action="cfgLinkManageList.jsp?op=sch";
	document.mainForm.submit();
}
function gamechg(obj)
{
	document.mainForm.action="cfgLinkManageList.jsp";
	document.mainForm.submit();
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
<script type="text/javascript">
<%out.println(azul.JspUtil.initSelect("game",gameMap,game));%>
<%out.println(azul.JspUtil.initSelect("channel",channelMap,channelNo));%>
</script>