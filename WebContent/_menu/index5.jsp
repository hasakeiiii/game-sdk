<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link href="css.css" type="text/css" rel="stylesheet" />
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<style type="text/css">
<!--
.STYLE2 {
	color: #FF0000;
	font-weight: bold;
}
-->
</style>
</head>
<STYLE>
*{ padding:0; margin:0;}
body{ margin:0 auto; font-size:12px; color:#666; width:100%;}
.L_1{ height:100%; background:url(../_images/bg.jpg) repeat-x;}
.L_2{ height:108px; width:1000px;}
.L_3{ margin:0 auto; width:513px; padding-left:77px; height:98px;}
.L_4{ margin:0 auto; width:590px; height:300px; background:url(../_images/bg_05.jpg) no-repeat;}
.L_5{ height:84px;}
.L_6{ padding-left:296px; width:186px; height:142px; }
.L_6 DL dd { float:left; width:54px; margin-bottom:10px;}
.L_6 DL dt { float:left; margin-bottom:10px; }
.L_6 DL span dd { width:186px;}
.STYLE1 {
	font-size: 18px;
	font-weight: bold;
}
</STYLE>
<body>
<form name="mainForm" id="mainForm" method="post">
<div style="height:80px"></div>
<div class="L_1"><div class="L_3"><img src="../_images/logo.jpg" /></div>
  <div class="L_4">
	       <div class="L_5"></div>
		   <div class="L_6">
		        <dL>
				<dd>用户名：</dd><dt>
				  <input name="username" id="username" type="text" style="width:120px"/>
				</dt>
				<dd>密　码：</dd><dt>
				  <input type="password" name="password" id="password" style="width:120px" />
				</dt>
				<dd>记住密码</dd><dt>
				  <input type="checkbox" name="rember" value="1"/>
				</dt>
				<span><dd><a href="#" onClick="login()"><img src="../_images/14.gif" border="0"/></a></dd>
				</span>
				</dL>
	    </div>
    </div>
</div>

<div class="layout_centered">
  <div id="layout_footer_columns" align="center"><span class="STYLE1">© 2009 DingXue All Rights Reserved. 粤ICP备10001665号</span></div>
</div>
</form>
</body>
</html>
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