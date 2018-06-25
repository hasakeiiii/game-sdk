<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<jsp:include page="../check.jsp?check=cfgCompanyEdit.jsp" flush="true" />
<%
String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int cfg_company_id=azul.JspUtil.getInt(request.getParameter("cfg_company_id"),0);
String name="";//公司名称
String cid="";//公司CID
String cid_user="";//登录用户名
String cid_pass="";//登录密码
String address="";//公司地址
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
</head>
<body>
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
    if('<%=cfg_company_id%>'=="0"){
        document.mainForm.action="cfgCompanyAction.jsp?op=add";
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="cfgCompanyAction.jsp?op=edit&pageno=<%=pageno%>&cfg_company_id=<%=cfg_company_id%>";
        document.mainForm.submit();
    }
}
</script>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_name">公司名称</td>
    <td width="33%"><input name="name" alt="name" value="<%=name%>" maxlength="250"/></td>
<td>
<span id="warn_name"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_cid">公司CID</td>
    <td width="33%"><input name="cid" alt="cid" value="<%=cid%>" maxlength="33"/></td>
<td>
<span id="warn_cid"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_sell_id">所属业务员</td>
    <td width="33%">
<select name="cfg_sell_id" id="cfg_sell_id">
  <option value="0">暂无</option>
</select>	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_cid_user">登录用户名</td>
    <td width="33%"><input name="cid_user" alt="cid_user" value="<%=cid_user%>" maxlength="250"/></td>
<td>
<span id="warn_cid_user"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_cid_pass">登录密码</td>
    <td width="33%"><input name="cid_pass" alt="cid_pass" value="<%=cid_pass%>" maxlength="250"/></td>
<td>
<span id="warn_cid_pass"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_address">公司地址</td>
    <td width="33%"><input name="address" value="<%=address%>" maxlength="250"/></td>
<td>
<span id="warn_address"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_contact">联系人</td>
    <td width="33%"><input name="contact" value="<%=contact%>" maxlength="250"/></td>
<td>
<span id="warn_contact"></span></td>
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
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript">
<%
out.println(azul.JspUtil.initSelect("cfg_sell_id",map,cfg_sell_id));
%>
</script>
