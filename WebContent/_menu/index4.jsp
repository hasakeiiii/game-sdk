<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML lang=en xml:lang="en" xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>后台管理系统登录</TITLE>
<META http-equiv=content-type content="text/xhtml; charset=utf-8">
<LINK href="/favicon.ico" type=image/x-icon rel="shortcut icon">
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<style type="text/css">
HTML {
	PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; FONT: 100%/150% Arial, Helvetica, sans-serif; PADDING-TOP: 0px
}
BODY {
	PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; FONT: 100%/150% Arial, Helvetica, sans-serif; PADDING-TOP: 0px
}
P {
	PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 1em 0px; PADDING-TOP: 0px
}
PRE {
	PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 1em 0px; PADDING-TOP: 0px
}
INPUT {
	PADDING-RIGHT: 2px; PADDING-LEFT: 2px; PADDING-BOTTOM: 2px; FONT: 100%/150% Arial,Helvetica,sans-serif; PADDING-TOP: 2px
}
SELECT {
	PADDING-RIGHT: 2px; PADDING-LEFT: 2px; PADDING-BOTTOM: 2px; FONT: 100%/150% Arial,Helvetica,sans-serif; PADDING-TOP: 2px
}
TEXTAREA {
	PADDING-RIGHT: 2px; PADDING-LEFT: 2px; PADDING-BOTTOM: 2px; FONT: 100%/150% Arial,Helvetica,sans-serif; PADDING-TOP: 2px
}

BODY#log_in {
	BACKGROUND-COLOR: #262626
}
.clear {
	CLEAR: both
}
.no_bot_margin {
	MARGIN-BOTTOM: 0px
}
DIV#header {
	PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; WIDTH: 100%; PADDING-TOP: 0px; BACKGROUND-COLOR: #3d3d3d
}
DIV#links_wrap {
	FLOAT: left;
	WIDTH: 100%;
	COLOR: #fff;
	BACKGROUND-COLOR: #262626;
	height: 80px;
}
DIV#nav {
	MARGIN: 0px auto; WIDTH: 960px
}
UL#menu {
	BORDER-RIGHT: #000 1px solid; PADDING-RIGHT: 0px; PADDING-LEFT: 0px; FONT-SIZE: 16px; FLOAT: left; PADDING-BOTTOM: 0px; MARGIN: 0px; WIDTH: 960px; PADDING-TOP: 0px; LIST-STYLE-TYPE: none
}
UL#menu LI {
	PADDING-RIGHT: 0px; DISPLAY: inline; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-TOP: 0px
}
UL#menu A {
	PADDING-RIGHT: 15px; PADDING-LEFT: 15px; FONT-WEIGHT: bold; FONT-SIZE: 11px; FLOAT: right; PADDING-BOTTOM: 5px; TEXT-TRANSFORM: uppercase; BORDER-LEFT: #000 1px solid; COLOR: #fff; PADDING-TOP: 5px; TEXT-DECORATION: none
}
UL#menu A:hover {
	COLOR: #f8b31a
}
UL#menu A.current {
	COLOR: #f8b31a
}
DIV#sign_up_box {
	BACKGROUND: url(../images/log_in_box_bot_bk.png) no-repeat left bottom;
	MARGIN: 30px auto;
	WIDTH: 520px
}
DIV#sign_up_box DIV.form-container {
	PADDING-RIGHT: 30px; PADDING-LEFT: 30px; PADDING-BOTTOM: 22px; COLOR: #333; PADDING-TOP: 20px
}
DIV#sign_up_box DIV.form-container FIELDSET.styled {
	BORDER-RIGHT: #fde4ac 1px solid; PADDING-RIGHT: 10px; BORDER-TOP: #fde4ac 1px solid; PADDING-LEFT: 10px; PADDING-BOTTOM: 2px; MARGIN: 10px 0px; BORDER-LEFT: #fde4ac 1px solid; PADDING-TOP: 12px; BORDER-BOTTOM: #fde4ac 1px solid; BACKGROUND-COLOR: #ffffd7
}
DIV#sign_up_box DIV.form-container LABEL {
	PADDING-RIGHT: 10px; DISPLAY: block; FLOAT: left; WIDTH: 145px; MARGIN-RIGHT: 10px; POSITION: relative; TEXT-ALIGN: right
}
DIV#sign_up_box DIV.form-container SPAN.label {
	PADDING-RIGHT: 10px; DISPLAY: block; FLOAT: left; WIDTH: 145px; MARGIN-RIGHT: 10px; POSITION: relative; TEXT-ALIGN: right
}
DIV#sign_up_box DIV.form-container DIV {
	MARGIN-BOTTOM: 10px
}
DIV#sign_up_box DIV.form-container INPUT#username {
	WIDTH: 200px
}

DIV#sign_up_box DIV.form-container INPUT#password {
	WIDTH: 200px
}

DIV#sign_up_box DIV.form-container FORM P.note {
	PADDING-RIGHT: 0px; PADDING-LEFT: 0px; FONT-SIZE: 90%; PADDING-BOTTOM: 0px; MARGIN: 0px 0px 0px 170px; COLOR: #666666; PADDING-TOP: 0px
}
DIV#sign_up_box DIV.form-container H3 {
	FONT-WEIGHT: normal; FONT-SIZE: 140%; MARGIN: 0px
}
BODY#log_in DIV#options_wrap {
	MIN-HEIGHT: 400px; PADDING-BOTTOM: 10px
}
DIV#options {
	BORDER-TOP: #474747 1px solid; MARGIN: 0px auto; WIDTH: 960px; COLOR: #ccc
}
DIV#main {
	FLOAT: left; WIDTH: 715px; MARGIN-RIGHT: 20px
}
BODY#home DIV#main {
	FLOAT: left; WIDTH: 600px
}
DIV#footer {
	PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; COLOR: #fff; PADDING-TOP: 10px; BACKGROUND-COLOR: #3d3d3d
}
DIV#meta {
	FONT-SIZE: 90%;
	MARGIN: 0px auto;
	WIDTH: 960px;
	COLOR: #999;
	text-align: center;
}
</style>
</HEAD>
<BODY id=log_in>
<DIV id=wrapper>
<DIV id=header>
<DIV id=links_wrap>
</DIV>
<H1 align="center"><img alt=CurdBee src="../_js/ico/logo2.jpg"></H1>
<DIV id=options_wrap>
<DIV id=options>
<DIV id=sign_up_box>
<DIV class=form-container>
<form name="mainForm" id="mainForm" method="post">
<FIELDSET class=styled>
<DIV style="padding:10px">
<DIV>
<LABEL for=user_name>用户名</LABEL> 
<input name="username" type="text" id="username" tabIndex="1" size="30" value=""/>
</DIV>
<DIV>
  <LABEL for=user_password>密码</LABEL> 
  <input  type="password" name="password" id="password" class="inputpassword" tabIndex="2" size="30"/>
</DIV>
<div> <input type="checkbox" name="rember" value="1"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;记住密码</div>
</DIV>
</FIELDSET> 
</form></DIV>
<DIV class=buttonrow align="center">
  <input id="create_account_bt" type="image" alt="login" tabIndex="3" src="../_js/ico/login_bt.png" onClick="login()">
</DIV></DIV></DIV></DIV></DIV></DIV>
<DIV id=meta_wrap>
<DIV id=meta>
© 2009 DingXue All Rights Reserved.
</DIV>
</DIV>
<P></P>
</BODY>
</HTML>
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
<%
}
%>
</script>
