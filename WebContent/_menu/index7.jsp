<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>welcome veeya.net</title>
<style type="text/css">
<!--
* {margin: 0px;padding: 0px;border-top-width: 0px;border-right-width: 0px;border-bottom-width: 0px;border-left-width: 0px;text-decoration:none; list-style:none;}
body {font-family: "宋体";font-size: 12px;color: #666;text-align:left; background-color:#DCEDF7; background:url(../_images/bg.jpg) repeat-x; height:600px;}
.header01{width:590px; margin:0px auto;}
.L_3 {width:513px;height:100px; padding-left:70px;}
.L_4 {width:590px; height:409px; background:url(../_images/bg_08.jpg) no-repeat;}
.L_5 {height:80px;}
.L_6 {padding-left:270px; width:186px; height:100px; line-height:22px;}

-->
</style>
<link href="css.css" type="text/css" rel="stylesheet" />
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
<form name="mainForm" id="mainForm" method="post">
<div style="height:66px"></div>
<div class="L_3 header01"><img src="../_images/logo_veeya.jpg" width="282" height="97" /></div>
<div class="L_4 header01">
  <div class="L_5"></div>
  <div class="L_6">
    <dl>
      <dd>用户名</dd>
      <dt>
        <input name="username" id="username" type="text" style="border:1px #999 solid;width:120px;">
      </dt>
      <dd>密码</dd>
      <dt>
        <input type="password" name="password" id="password" style="border:1px #999 solid;width:120px;">
      </dt>
      <dd>记住密码</dd>
      <dt>
        <input type="checkbox" name="rember" value="1"/>&nbsp;&nbsp;&nbsp;<a href="#" onclick="login()"><img src="../_images/14.gif" border="0"/></a>
      </dt>
    </dl>
  </div>
</div>
<div class="layout_centered">
  <div id="layout_footer_columns" align="center"><span class="font1">&copy; 2009 Veeya All Rights Reserved. 辽ICP备10206142号</span></div>
</div>
</form>
</body>
</html>
<script type="text/javascript" src="../_js/md5.js"></script>
<script type="text/javascript" src="../_js/jquery.js"></script>
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
}
%>
</script>