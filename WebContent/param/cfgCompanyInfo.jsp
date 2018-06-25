<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<jsp:include page="../check.jsp?check_role=admin,cid" flush="true" />
<%
int cfg_company_id=azul.JspUtil.getInt(request.getParameter("cfg_company_id"),0);
CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
CfgCompany cfgCompany=(CfgCompany)cfgCompanyDao.getById(cfg_company_id);
String name=cfgCompany.getName();
String cid=cfgCompany.getCid();
String cid_user=cfgCompany.getCidUser();
String cid_pass=cfgCompany.getCidPass();
double rate=cfgCompany.getRate();
String address=cfgCompany.getAddress();
String contact=cfgCompany.getContact();

CfgSellDao cfgSellDao=new CfgSellDao();
java.util.Map map=cfgSellDao.getSelectMap("select cfg_sell_id,name from cfg_sell");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<h1>记录详细信息ID:<%=cfg_company_id%></h1>
<table width="100%" border="0">
<tr align="center">
    <td width="50%">公司名称</td>
    <td><%=name%></td>
</tr>
<tr align="center">
    <td width="50%">所属业务员</td>
    <td><%=azul.JspUtil.getLink(cfgCompany.getCfgSellId(),map)%></td>
</tr>
<tr align="center">
    <td width="50%">公司CID</td>
    <td><%=cid%></td>
</tr>
<tr align="center">
    <td width="50%">登录用户名</td>
    <td><%=cid_user%></td>
</tr>
<tr align="center">
    <td width="50%">登录密码</td>
    <td><%=cid_pass%></td>
</tr>
<tr align="center">
    <td width="50%">分成比例</td>
    <td><%=rate%></td>
</tr>
<tr align="center">
    <td width="50%">公司地址</td>
    <td><%=address%></td>
</tr>
<tr align="center">
    <td width="50%">联系人</td>
    <td><%=contact%></td>
</tr>
  <tr>
    <td colspan="2" align="center">
        <a href="#" onclick="javascript:history.back()"><img src="../_js/ico/btn_back.gif" border="0" alt="返回" align="absmiddle"/></a>
    </td>
  </tr>
</table>
</body>
</html>
