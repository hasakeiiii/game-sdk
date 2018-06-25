<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check=BusinesserEdit.jsp" flush="true" />
<%
String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id=azul.JspUtil.getInt(request.getParameter("id"),0);
int uerid = azul.JspUtil.getInt(request.getParameter("uerid"),0);

String name="";//game名称
String no="";//game代号
String username="";//登录名
String userpass="";//密码
String role="";//角色

if(id!=0){
    pageTitle="编辑信息ID:"+id;
    BusinesserDao businesserDao=new BusinesserDao();
    Businesser cfgSp=(Businesser)businesserDao.getById(id);
    name=cfgSp.getName();
    no=cfgSp.getNo();
}
else
{
	/*ArrayList<Object> objList = getRecordsByAppNo(AppNo);
	if(objList.size() > 0)
	{
		for(int i = 0; i < objList.size(); i ++)
		{
			DebuUtil.log("修改游戏名");
			Cooperation cooperation = (Cooperation)objList.get(0);
			cooperation.setAppName(AppName);
			super.edit(cooperation);
		}
	}*/
	
	BusinesserDao businesserDao=new BusinesserDao();
	ArrayList<Object> list=businesserDao.getList("select * from businesser order by id desc limit 1");
	if(list.size() > 0)
	{
		Businesser app=(Businesser)list.get(0);
		int ivalue = Integer.valueOf(app.getNo());
		ivalue ++;
		no = String.valueOf(ivalue);
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
<title>游戏运营管理系统</title>
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
        document.mainForm.action="BusinesserAction.jsp?op=add";
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="BusinesserAction.jsp?op=edit&pageno=<%=pageno%>&id=<%=id%>&uerid=<%=uerid%>";
        document.mainForm.submit();
    }
}
</script>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_name">商务姓名</td>
    <td width="33%"><input name="name" alt="name" value="<%=name%>" maxlength="250"/></td>
<td>
<span id="warn_sp_name"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_no">商务代号</td>
    <td width="33%"><input name="no" alt="no" value="<%=no%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_username">登录用户名</td>
    <td width="33%"><input name="username" alt="username" value="<%=username%>" maxlength="250"/></td>
<td>
<span id="warn_sp_username"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_userpass">登录密码</td>
    <td width="33%"><input name="userpass" alt="userpass" value="<%=userpass%>" maxlength="250"/></td>
<td>
<span id="warn_sp_userpass"></span></td>
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