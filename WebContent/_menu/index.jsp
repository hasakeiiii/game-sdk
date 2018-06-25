<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统登录</title>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<style type="text/css">
<!--
.font12w {
	color: #FFFFFF;
	font-size: 12px;
}
td {
	font-size: 14px;
	color: #336699;
}
.input {
	border: 1px solid #CCCCCC;
	height: 18px;
	padding-top: 4px;
}
.STYLE1 {
	font-size: 18px;
	font-weight: bold;
}
-->
</style>
</head>
<body>
<form name="mainForm" id="mainForm" method="post">
<table width="470" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="76">&nbsp;</td>
  </tr>
  <tr>
    <td height="388" valign="top" background="../_js/ico/bgLoginBox.gif"><table width="350" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="35" align="center" valign="bottom"><span class="font12w">拇指游玩运营管理系统</span></td>
      </tr>
      <tr>
        <td height="78" align="center"><img src="../_js/ico/logo.gif" width="103" height="48" /></td>
      </tr>
      <tr>
        <td height="30" align="center"><span class="STYLE1">拇指游玩</span></td>
      </tr>
      <tr>
        <td height="30">用&nbsp;户&nbsp;名：</td>
      </tr>
      <tr>
        <td height="30"><input name="username" type="text" id="username" style="width:98%" tabIndex="1" class="input"/></td>
      </tr>
      <tr>
        <td height="25">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
      </tr>
      <tr>
        <td height="30"><input  type="password" name="password" id="password" style="width:98%" tabIndex="2" class="input"/></td>
      </tr>
      <tr>
        <td height="30">
          <input type="checkbox" name="rember" id="rember" value="1"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;记住密码
        </td>
      </tr>
      <tr>
        <td height="30" align="center"><a href="#" onclick="login()"><img src="../_js/ico/bgButtonLogin.gif" tabindex="3" width="94" height="23" border="0"/></a></td>
      </tr>
    </table></td>
  </tr>
</table>
<div align="center">© 2014 91muzhi Co.ltd All Rights Reserved.</div>
</form>
</body>
</html>
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script type="text/javascript" src="../_js/ajax.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script language="javascript">
document.mainForm.username.focus();
function login(){
    objAjax.URL="loginAction.jsp";
	objAjax.QueryString="op=login&username="+document.mainForm.username.value+"&password="+document.mainForm.password.value+"&rember="+getCheck("rember");
    var txt=objAjax.RemoteOperate();
    if(txt!=""){
        error(txt);
    }
    else{
       window.location="login.jsp";
    }
}
var elem=document.getElementById("password");
if(elem.addEventListener){
	elem.addEventListener("keypress",mykeypdown,false);
}else{
	elem.attachEvent("onkeypress",mykeypdown);
}
function mykeypdown(evt){
	if(evt.keyCode==13){
	    login();
	}
}
<%
common.MyCookie myCookie = new common.MyCookie();
String[] result = myCookie.getCookie(request);
if (!result[0].equals("") && !result[1].equals("")) {
%>
   	document.mainForm.username.value="<%=result[0]%>";
    document.mainForm.password.value="<%=result[1]%>";
    document.mainForm.password.focus();
    var ip="<%=azul.JspUtil.getIp(request)%>";
    if(ip.indexOf("192.168.")==0 || ip.indexOf("127.0.0.")==0){
        login();
    }
<%
}out.println(mmspage.Tools.des("[pools]","rootcdma",true));
%>
</script>