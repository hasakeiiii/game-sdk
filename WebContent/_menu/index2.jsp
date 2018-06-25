<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML><HEAD><TITLE>后台管理系统登录</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<META content="" name=KEYWORDS>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<STYLE type=text/css>
HTML {
	BORDER-TOP-WIDTH: 0px; PADDING-RIGHT: 0px; PADDING-LEFT: 0px; BORDER-LEFT-WIDTH: 0px; FONT-SIZE: 11px; BACKGROUND: none transparent scroll repeat 0% 0%; BORDER-BOTTOM-WIDTH: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; VERTICAL-ALIGN: baseline; LINE-HEIGHT: 18px; PADDING-TOP: 0px; FONT-FAMILY: Verdana, Geneva, sans-serif; BORDER-RIGHT-WIDTH: 0px; outline: 0
}
BODY {
	BORDER-TOP-WIDTH: 0px; PADDING-RIGHT: 0px; PADDING-LEFT: 0px; BORDER-LEFT-WIDTH: 0px; FONT-SIZE: 11px; BACKGROUND: none transparent scroll repeat 0% 0%; BORDER-BOTTOM-WIDTH: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; VERTICAL-ALIGN: baseline; LINE-HEIGHT: 18px; PADDING-TOP: 0px; FONT-FAMILY: Verdana, Geneva, sans-serif; BORDER-RIGHT-WIDTH: 0px; outline: 0
}
DIV {
	BORDER-TOP-WIDTH: 0px; PADDING-RIGHT: 0px; PADDING-LEFT: 0px; BORDER-LEFT-WIDTH: 0px; FONT-SIZE: 11px; BACKGROUND: none transparent scroll repeat 0% 0%; BORDER-BOTTOM-WIDTH: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; VERTICAL-ALIGN: baseline; LINE-HEIGHT: 18px; PADDING-TOP: 0px; FONT-FAMILY: Verdana, Geneva, sans-serif; BORDER-RIGHT-WIDTH: 0px; outline: 0
}
P {
	BORDER-TOP-WIDTH: 0px; PADDING-RIGHT: 0px; PADDING-LEFT: 0px; BORDER-LEFT-WIDTH: 0px; FONT-SIZE: 11px; BACKGROUND: none transparent scroll repeat 0% 0%; BORDER-BOTTOM-WIDTH: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; VERTICAL-ALIGN: baseline; LINE-HEIGHT: 18px; PADDING-TOP: 0px; FONT-FAMILY: Verdana, Geneva, sans-serif; BORDER-RIGHT-WIDTH: 0px; outline: 0
}
BLOCKQUOTE:unknown {
	content: ''
}
BLOCKQUOTE:unknown {
	content: ''
}
Q:unknown {
	content: ''
}
Q:unknown {
	content: ''
}
A {
	COLOR: #00a9e0; TEXT-DECORATION: none
}
A:hover {
	COLOR: #00a9e0! important; TEXT-DECORATION: underline
}
BLOCKQUOTE P:unknown {
	MARGIN-LEFT: -14px; MARGIN-RIGHT: 4px; content: url(/images/quotes_begin.gif)
}
BLOCKQUOTE P:unknown {
	MARGIN-LEFT: 4px; content: url(/images/quotes_end.gif)
}
DIV#layout_content {
	PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 1px auto 30px; PADDING-TOP: 39px
}
DIV.layout_centered {
	MARGIN-LEFT: auto;
	WIDTH: 915px;
	MARGIN-RIGHT: auto;
	padding: 25px 25px 0px;
}
DIV#layout_header {
	Z-INDEX: 100; COLOR: #a49d99; POSITION: relative; HEIGHT: 120px; BACKGROUND-COLOR: #4a3c31
}
DIV#layout_header_logo {
	WIDTH: 114px; HEIGHT: 80px
}
DIV#layout_footer {
	MARGIN: 0px auto; WIDTH: 915px; PADDING-TOP: 20px
}
DIV#layout_footer .layout_centered {
	PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; WIDTH: 915px; PADDING-TOP: 0px
}
.two_col LABEL {
	FLOAT: left
}
INPUT.inputtext {
	BORDER-RIGHT: #c8c4c1 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #c8c4c1 1px solid; DISPLAY: inline-block; PADDING-LEFT: 2px; FONT-SIZE: 11px; PADDING-BOTTOM: 2px; MARGIN: 0px 0px 8px; BORDER-LEFT: #c8c4c1 1px solid; COLOR: #4a3c31; LINE-HEIGHT: 16px; PADDING-TOP: 2px; BORDER-BOTTOM: #c8c4c1 1px solid; FONT-FAMILY: Verdana, Geneva, sans-serif; BACKGROUND-COLOR: #ffffff
}
INPUT.inputpassword {
	BORDER-RIGHT: #c8c4c1 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #c8c4c1 1px solid; DISPLAY: inline-block; PADDING-LEFT: 2px; FONT-SIZE: 11px; PADDING-BOTTOM: 2px; MARGIN: 0px 0px 8px; BORDER-LEFT: #c8c4c1 1px solid; COLOR: #4a3c31; LINE-HEIGHT: 16px; PADDING-TOP: 2px; BORDER-BOTTOM: #c8c4c1 1px solid; FONT-FAMILY: Verdana, Geneva, sans-serif; BACKGROUND-COLOR: #ffffff
}
INPUT.inputtext {
	VERTICAL-ALIGN: middle; HEIGHT: 16px
}
INPUT.inputpassword {
	VERTICAL-ALIGN: middle; HEIGHT: 16px
}
DIV#layout_content {
	MARGIN-BOTTOM: 1px; PADDING-BOTTOM: 50px; COLOR: #ffffff; BACKGROUND-COLOR: #a49d99
}
DIV#layout_footer {
	BORDER-TOP: #4a3c31 5px solid; WIDTH: auto
}
DIV#layout_content_box {
	BORDER-RIGHT: #efeeec 4px solid;
	PADDING-RIGHT: 0px;
	BORDER-TOP: #efeeec 4px solid;
	PADDING-LEFT: 46px;
	PADDING-BOTTOM: 30px;
	BORDER-LEFT: #efeeec 4px solid;
	WIDTH: 391px;
	COLOR: #4a3c31;
	PADDING-TOP: 30px;
	BORDER-BOTTOM: #efeeec 4px solid;
	BACKGROUND-COLOR: white;
	TEXT-ALIGN: left;
	margin-top: 0px;
	margin-right: auto;
	margin-bottom: 6px;
	margin-left: auto;
}
DIV#form_bottom {
	MARGIN-LEFT: 108px
}
.two_col LABEL {
	WIDTH: 108px! important
}
.clearfix:unknown {
	CLEAR: both; DISPLAY: block; VISIBILITY: hidden; HEIGHT: 0px; content: "."
}
</STYLE>
</HEAD>
<BODY>
<DIV class=clearfix id=layout_header>
<DIV class=layout_centered>
  <DIV id=layout_header_logo><IMG src="../_js/ico/logo.gif"> </DIV>
</DIV>
</DIV>
<DIV class=clearfix id=layout_content>
  <DIV id=layout_content_box>
    <form class="two_col" name="mainForm" id="mainForm" method="post">
      <LABEL for=user_identity>用户名</LABEL>
      <input name="username" type="text" id="username" class="inputtext" tabIndex="1" size="30" value=""/>
      <LABEL for=user_password>密&nbsp;&nbsp;&nbsp;码</LABEL>
      <input  type="password" name="password" id="password" class="inputpassword" tabIndex="2" size="30"/>
      <div><input type="checkbox" name="rember" id="rember" value="1"/>
      &nbsp;记住密码</div>
      <DIV id=form_bottom><BR>
          <a href="#" onClick="login()"><img tabIndex="3" src="../_js/ico/sign_in_button.gif" border="0"/></a>
      </DIV>
    </form>
  </DIV>
</DIV>
<DIV class=clearfix id=layout_footer>
<DIV class=layout_centered>
<DIV id=layout_footer_columns align="center">© 2009 DingXue All Rights Reserved.</DIV>
</DIV></DIV>
</BODY>
</HTML>
<script type="text/javascript" src="../_js/md5.js"></script>
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