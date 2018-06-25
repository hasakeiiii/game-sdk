<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check=cfgChannelEdit.jsp" flush="true" />
<%
DebuUtil.log("channeledit");
String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id=azul.JspUtil.getInt(request.getParameter("id"),0);
int uerid = azul.JspUtil.getInt(request.getParameter("uerid"),0);

java.util.Map roleMap=new HashMap<String,String>();
roleMap.put("CPS", "CPS");
roleMap.put("CPA", "CPA");
roleMap.put("CPA_R", "CPA_R");

String channel_name="";//渠道名称
String channel_code="";//渠道代号
String channel_address="";//渠道地址
String channel_link="";//联系人
String username="";//登录名
String userpass="";//密码
String role="";//角色

if(id!=0){
    pageTitle="编辑信息ID:"+id;
    ChannelDao channelDao=new ChannelDao();
    Channel channel = (Channel)channelDao.getById(id);
    channel_name = channel.getName();
    channel_code = channel.getNo();
    channel_address = channel.getAddr();
    channel_link = channel.getContact();
}
else
{
	ChannelDao channelDao=new ChannelDao();
	ArrayList<Object> list=channelDao.getList("select * from channel order by id desc limit 1");
	if(list.size() > 0)
	{
		Channel app=(Channel)list.get(0);
		int ivalue = Integer.valueOf(app.getNo());
		ivalue ++;
		channel_code = String.valueOf(ivalue);
	}
}
if(uerid!=0){
    UserinfoDao userinfoDao=new UserinfoDao();
    Userinfo userinfo = (Userinfo)userinfoDao.getById(uerid);
    username = userinfo.getUsername();
    userpass = userinfo.getPass();
    role= userinfo.getRole();
}

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
    if(!CheckForm.checkForm("mainForm"))return;
    if('<%=id%>'=="0"){
        document.mainForm.action="cfgChannelAction.jsp?op=add";
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="cfgChannelAction.jsp?op=edit&pageno=<%=pageno%>&id=<%=id%>&uerid=<%=uerid%>";
        document.mainForm.submit();
    }
}
</script>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_channel_name">渠道名称</td>
    <td width="33%"><input name="channel_name" alt="channel_name" value="<%=channel_name%>" maxlength="250"/></td>
<td>
<span id="warn_sp_name"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_channel_code">渠道代号</td>
    <td width="33%"><input name="channel_code" alt="channel_code" value="<%=channel_code%>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_username">登录用户名</td>
    <td width="33%"><input name="username" alt="username" value="<%=username%>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_userpass">登录密码</td>
    <td width="33%"><input name="userpass" alt="userpass" value="<%=userpass%>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_role">角色</td>
    <td width="33%">
  <select name="role" id="role">
</select></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_channel_address">地址</td>
    <td width="33%"><input name="channel_address" alt="channel_address" value="<%=channel_address%>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_channel_link">联系人</td>
    <td width="33%"><input name="channel_link" alt="channel_link" value="<%=channel_link%>" maxlength="250"/></td>
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
<%
out.println(azul.JspUtil.initSelect("role",roleMap,role));
%>
</script>
