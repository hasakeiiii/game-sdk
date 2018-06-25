<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check=VagueEdit.jsp" flush="true" />
<%
String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id=azul.JspUtil.getInt(request.getParameter("id"),0);

String name="";
String no="";
String vague ="";
String vague_time = "00:00:00-24:00:00";
String vague_addr = "";
String alert = "";
String alert_time="00:00:00-24:00:00";
String alert_addr = "";
String button = "";
String button_time="00:00:00-24:00:00";
String button_addr= "";
String our_alert = "";
String our_alert_time ="00:00:00-24:00:00";
String our_alert_addr = "";
String cootype = "";
String ver = "";

if(id!=0){
    pageTitle="编辑信息ID:"+id;
	VagueDao vagueDao=new VagueDao();
    Vague oldvague=(Vague)vagueDao.getById(id);
    name = oldvague.getName();
    no = oldvague.getNo();
    vague = oldvague.getVague();
    vague_time = oldvague.getVagueTime();
    vague_addr = oldvague.getVagueAddr();
    alert = oldvague.getAlert();
    alert_time = oldvague.getAlertTime();
    alert_addr = oldvague.getAlertAddr();
    button = oldvague.getButton();
    button_time = oldvague.getButtonTime();
    button_addr = oldvague.getButtonAddr();
    our_alert = oldvague.getOurAlert();
    our_alert_time = oldvague.getOurAlertTime();
    our_alert_addr = oldvague.getOurAlertAddr();
    cootype = oldvague.getCootype();
    ver = oldvague.getVer();
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
        document.mainForm.action="VagueAction.jsp?op=add";
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="VagueAction.jsp?op=edit&pageno=<%=pageno%>&id=<%=id%>";
        document.mainForm.submit();
    }
}
</script>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_no">模糊配置编号</td>
    <td width="33%"><input name="no" alt="no" value="<%=no%>" maxlength="250"/></td>
<td>
<span id="warn_sp_name"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_name">配置名称</td>
    <td width="33%"><input name="name" alt="name" value="<%=name%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span></td>
</tr>

<tr align="center">
    <td width="33%" id="title_ver">游戏版本控制</td>
    <td width="33%"><input name="ver" alt="ver_null" value="<%=ver%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span>多个控制用&分开</td>
</tr>
<tr align="center">
    <td width="33%" id="title_alert"> <font color=red >弹窗开关</font></td>
    <td width="33%"><input name="alert" alt="alert_null" value="<%=alert%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span>0：关闭.1：打开</td>
</tr>
<tr align="center">
    <td width="33%" id="title_alert_time">弹窗时间段</td>
    <td width="33%"><input name="alert_time" alt="alert_time_null" value="<%=alert_time%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_alert_addr">弹窗屏蔽地区</td>
    <td width="33%"><input name="alert_addr" alt="alert_addr_null" value="<%=alert_addr%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>

<tr align="center">
    <td width="33%" id="title_cootype">线上线下控制</td>
    <td width="33%"><input name="cootype" alt="cootype_null" value="<%=cootype%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span>0:线上。1:线下</td>
</tr>
<tr align="center">
    <td width="33%" id="title_vague"><font color=red >支付模糊开关</font></td>
    <td width="33%"><input name="vague" alt="vague_null" value="<%=vague%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span>0：关闭.1：打开</td>
</tr>
<tr align="center">
    <td width="33%" id="title_vague_time">模糊时间段</td>
    <td width="33%"><input name="vague_time" alt="vague_time_null" value="<%=vague_time%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_vague_addr">模糊屏蔽地区</td>
    <td width="33%"><input name="vague_addr" alt="vague_addr_null" value="<%=vague_addr%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>


<tr align="center">
    <td width="33%" id="title_button"><font color=red >按钮处理</font></td>
    <td width="33%"><input name="button" alt="button_null" value="<%=button%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span>0：购买.1：确认.2：领取</td>
</tr>
<tr align="center">
    <td width="33%" id="title_button_time">模糊时间段</td>
    <td width="33%"><input name="button_time" alt="button_time_null" value="<%=button_time%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_button_addr">模糊屏蔽地区</td>
    <td width="33%"><input name="button_addr" alt="button_addr_null" value="<%=button_addr%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>

<tr align="center">
    <td width="33%" id="title_our_alert"><font color=red >二次确认开关</font></td>
    <td width="33%"><input name="our_alert" alt="our_alert_null" value="<%=our_alert%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span>0：关闭.1：打开</td>
</tr>
<tr align="center">
    <td width="33%" id="title_our_alert_time">模糊时间段</td>
    <td width="33%"><input name="our_alert_time" alt="our_alert_time_null" value="<%=our_alert_time%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_our_alert_addr">模糊屏蔽地区</td>
    <td width="33%"><input name="our_alert_addr" alt="our_alert_addr_null" value="<%=our_alert_addr%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
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