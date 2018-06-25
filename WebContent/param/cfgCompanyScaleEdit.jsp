<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<jsp:include page="../check.jsp?check=cfgCompanyScaleEdit.jsp" flush="true" />
<%
String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int cfg_company_id=azul.JspUtil.getInt(request.getParameter("cfg_company_id"),0);
String name="";//公司名称
String cid="";//公司CID
String cid_user="";//登录用户名
String cid_pass="";//登录密码
String address="";//公司地址
double gprs=0; 
double rate=0;
int scale=2;
int scale_anchor=2;
int open=0;
String contact="";//联系人
int cfg_sell_id=0;
if(cfg_company_id!=0){
    pageTitle="编辑信息ID:"+cfg_company_id;
    CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
    CfgCompany cfgCompany=(CfgCompany)cfgCompanyDao.getById(cfg_company_id);
    name=cfgCompany.getName();
    cid=cfgCompany.getCid();
    cid_user=cfgCompany.getCidUser();
    cid_pass=cfgCompany.getCidPass();
    scale=cfgCompany.getScale();
	scale_anchor=cfgCompany.getScaleAnchor();
	open=cfgCompany.getOpen();
	gprs=cfgCompany.getGprs();
	rate=cfgCompany.getRate();
    address=cfgCompany.getAddress();
    contact=cfgCompany.getContact();
    cfg_sell_id=cfgCompany.getCfgSellId();
}

CfgSellDao cfgSellDao=new CfgSellDao();
java.util.Map map=cfgSellDao.getSelectMap("select cfg_sell_id,name from cfg_sell");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}
-->
</style>
</head>
<body>
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script type="text/javascript" src="../_js/Check.js"></script>
<script type="text/javascript" src="../_js/CheckForm.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script language="javascript">
window.onload = function() {
	CheckForm.formBind("mainForm");
}
function btnSave(){
    if(!CheckForm.checkForm("mainForm"))return;
    if('<%=cfg_company_id%>'=="0"){
        error("无法取到厂商编号");
    }
    else{//编辑功能
        document.mainForm.action="cfgCompanyAction.jsp?op=editScale&pageno=<%=pageno%>&cfg_company_id=<%=cfg_company_id%>";
        document.mainForm.submit();
    }
}
</script>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_name">公司名称</td>
    <td width="37%"><%=name%></td>
<td width="30%">
<span id="warn_name"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_cid">公司CID</td>
    <td width="37%"><%=cid%></td>
<td>
<span id="warn_cid"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_scale">扣费比例</td>
    <td width="37%">
      <span class="STYLE1">0为不扣费 1为减扣1条 2为配置扣费 3为增扣1条 4为增扣2条</span><br>
      <input name="scale" alt="scale_int" value="<%=scale%>" maxlength="22"/></td>
    <td>
<span id="warn_scale"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_scale_anchor">包月扣费比例</td>
    <td width="37%">
      <span class="STYLE1">0为不扣费 1为减扣1条 2为配置扣费 3为增扣1条 4为增扣2条</span><br>
      <input name="scale_anchor" alt="scale_anchor_int" value="<%=scale_anchor%>" maxlength="22"/></td>
    <td>
<span id="warn_scale_anchor"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_rate">用户分成</td>
    <td width="37%">
      <input name="rate" alt="rate_double" value="<%=rate%>" maxlength="22"/></td>
    <td>
<span id="warn_rate"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_gprs">用户销统</td>
    <td width="37%">
      <input name="gprs" alt="gprs_double" value="<%=gprs%>" maxlength="22"/></td>
    <td>
<span id="warn_gprs"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_open">用户计费</td>
    <td width="37%">
      <input type="radio" value="0" name="open"/> 关闭<input type="radio" value="1" name="open"/> 打开</td>
    <td>
<span id="warn_open"></span></td>
</tr>
  <tr align=center>
    <td colspan=3>
        <a href="#" onclick="btnSave()"><img src="../_js/ico/btn_save.gif" border="0" alt="保存" align="absmiddle"/></a>&nbsp;&nbsp;&nbsp;
        <a href="#" onclick="javascript:history.back()"><img src="../_js/ico/btn_back.gif" border="0" alt="返回" align="absmiddle"/></a>    </td>
  </tr>
</table>
</form>
</body>
</html>
<script>
initElem("open","<%=open%>");
</script>