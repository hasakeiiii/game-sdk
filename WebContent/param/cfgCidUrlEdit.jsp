<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ include file="../check.jsp?check=cfgCidUrlEdit.jsp"%>
<%
String pageTitle="编辑信息";
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
int cfg_cid_url_id=JspUtil.getInt(request.getParameter("cfg_cid_url_id"),0);
String cid="";//
String url="";//
if(cfg_cid_url_id!=0){
    pageTitle="编辑信息ID:"+cfg_cid_url_id;
    CfgCidUrlDao cfgCidUrlDao=new CfgCidUrlDao();
    CfgCidUrl cfgCidUrl=(CfgCidUrl)cfgCidUrlDao.getById(cfg_cid_url_id);
    cid=cfgCidUrl.getCid();
    url=cfgCidUrl.getUrl();
}

CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
java.util.Map cidMap=cfgCompanyDao.getSelectMap("select cid,concat(name,'(',cid,')') from cfg_company order by name");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_cid">CID</td>
    <td width="33%"><select name="cid" id="cid" alt="cid">
<option value="">请选择</option>
</select></td>
<td>
<span id="warn_cid"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_url">同步地址</td>
    <td width="33%"><input name="url" alt="url" value="<%=url%>" size="50"/></td>
<td>
<span id="warn_url"></span></td>
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
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/Check.js"></script>
<script type="text/javascript" src="../_js/CheckForm.js"></script>
<script language="javascript">
window.onload = function() {
	CheckForm.formBind("mainForm");
	CheckForm.alertType="";
}
function btnSave(){
    if(!CheckForm.checkForm("mainForm"))return;
    var paramKey="";
    if("<%=cfg_cid_url_id%>"=="0"){
        document.mainForm.action="cfgCidUrlAction.jsp?op=add"+paramKey;
        document.mainForm.submit();
    }
    else{
        document.mainForm.action="cfgCidUrlAction.jsp?op=edit&pageno=<%=pageno%>&cfg_cid_url_id=<%=cfg_cid_url_id%>"+paramKey;
        document.mainForm.submit();
    }
}
<%
out.println(JspUtil.initSelect("cid",cidMap,cid));
%>
</script>