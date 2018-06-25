<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check=WhiteEdit.jsp" flush="true" />
<%
String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id=azul.JspUtil.getInt(request.getParameter("id"),0);

String imei="";
String no="";

if(id!=0){
    pageTitle="编辑信息ID:"+id;
    WhiteListDao whiteListDao=new WhiteListDao();
    WhiteList cfgSp=(WhiteList)whiteListDao.getById(id);
    imei=cfgSp.getImei();
    no=cfgSp.getNo();
}
else
{
	
	WhiteListDao whiteListDao=new WhiteListDao();
	ArrayList<Object> list=whiteListDao.getList("select * from white_list order by id desc limit 1");
	if(list.size() > 0)
	{
		WhiteList app=(WhiteList)list.get(0);
		int ivalue = Integer.valueOf(app.getNo());
		ivalue ++;
		no = String.valueOf(ivalue);
	}
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
        document.mainForm.action="WhiteAction.jsp?op=add";
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="WhiteAction.jsp?op=edit&pageno=<%=pageno%>&id=<%=id%>";
        document.mainForm.submit();
    }
}
</script>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_no">白名单列号</td>
    <td width="33%"><input name="no" alt="no" value="<%=no%>" maxlength="250"/></td>
<td>
<span id="warn_sp_name"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_imei">imei</td>
    <td width="33%"><input name="imei" alt="imei" value="<%=imei%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span></td>
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