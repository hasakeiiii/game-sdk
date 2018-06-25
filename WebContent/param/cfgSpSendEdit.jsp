<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ page import="java.util.*" %>
<%@ include file="../check.jsp"%>
<%
String pageTitle="编辑信息";
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
int cfg_sp_send_id=JspUtil.getInt(request.getParameter("cfg_sp_send_id"),0);
String sid="";//SID
String msg="";//命令
String spnum="";
String mo="";
String mr="";
String province="";
if(cfg_sp_send_id!=0){
    pageTitle="编辑信息ID:"+cfg_sp_send_id;
    CfgSpSendDao cfgSpSendDao=new CfgSpSendDao();
    CfgSpSend cfgSpSend=(CfgSpSend)cfgSpSendDao.getById(cfg_sp_send_id);
    sid=cfgSpSend.getSid();
    spnum=cfgSpSend.getSpnum();
    msg=cfgSpSend.getMsg();
    province=cfgSpSend.getProvince();
    mo=cfgSpSend.getMo();
    mr=cfgSpSend.getMr();
}

CfgSpDao cfgSpDao=new CfgSpDao();
Map sidMap=cfgSpDao.getSelectMap("select sp_code,concat(sp_name,'(',sp_code,')') from cfg_sp");

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
    <td width="19%" id="title_sid">SID</td>
    <td width="59%">
<select name="sid" id="sid">
  <option value="">请选择</option>
</select></td>
<td width="22%">
<span id="warn_sid"></span></td>
</tr>
<tr align="center">
    <td width="19%" id="title_msg">命令</td>
    <td width="59%"><input name="msg" alt="msg" value="<%=msg%>"/></td>
<td>
<span id="warn_msg"></span></td>
</tr>
<tr align="center">
    <td width="19%" id="title_spnum">地址</td>
    <td width="59%"><input name="spnum" alt="spnum" value="<%=spnum%>"/></td>
<td>
<span id="warn_spnum"></span></td>
</tr>
<tr align="center">
    <td width="19%" id="title_mo">上行地址</td>
    <td width="59%"><input name="mo" value="<%=mo%>" size="50" alt="mo"/></td>
<td>
<span id="warn_mo"></span></td>
</tr>
<tr align="center">
    <td width="19%" id="title_mr">状态地址</td>
    <td width="59%"><input name="mr" value="<%=mr%>" size="50" alt="mr"/></td>
<td>
<span id="warn_mr"></span></td>
</tr>
<tr align="center">
    <td width="19%" id="title_province">省份</td>
    <td width="59%">
	           <%
					for (int i = 0; i < common.Constant.AREA.length; i++) {
					if(i%11==1){
					    out.print("<br>");
					}
				%>
				<input type="checkbox" name="province" value="<%=common.Constant.AREA_CODE[i]%>"/>
				<%=common.Constant.AREA[i]%>
	            <%
					}
				%></td>
<td>
<span id="warn_province"></span></td>
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
<script type="text/javascript" src="../_js/Check.js"></script>
<script type="text/javascript" src="../_js/CheckForm.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script language="javascript">
window.onload = function() {
	CheckForm.formBind("mainForm");
	CheckForm.alertType="";
}
function btnSave(){
    if(!CheckForm.checkForm("mainForm"))return;
    var paramKey="&province="+getElem("province");
    if("<%=cfg_sp_send_id%>"=="0"){
        document.mainForm.action="cfgSpSendAction.jsp?op=add"+paramKey;
        document.mainForm.submit();
    }
    else{
        document.mainForm.action="cfgSpSendAction.jsp?op=edit&pageno=<%=pageno%>&cfg_sp_send_id=<%=cfg_sp_send_id%>"+paramKey;
        document.mainForm.submit();
    }
}
initElem("province","<%=province%>");
<%
out.println(JspUtil.initSelect("sid",sidMap,sid));
%>
</script>
