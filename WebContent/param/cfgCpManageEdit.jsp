 <%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check=cfgCpManageEdit.jsp" flush="true" />
<%
DebuUtil.log("channeledit");
String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id=azul.JspUtil.getInt(request.getParameter("id"),0);
 //int uerid = azul.JspUtil.getInt(request.getParameter("uerid"),0);

java.util.Map roleMap=new HashMap<String,String>();
roleMap.put("CPS", "CPS");
roleMap.put("CPA", "CPA");
roleMap.put("CPA_R", "CPA_R");

String cp_name="";
String cp_address="";
String cp_web="";
String cp_username="";
String cp_password="";
int cp_no = 0;
String remarks="";
int login_user= 0;

if(id!=0){
    pageTitle="编辑信息ID:"+id;
    CpManageDao cpmanageDao=new CpManageDao();
    UserinfoDao userinfodao = new UserinfoDao();
    CpManage cpmanage = (CpManage)cpmanageDao.getById(id);
    cp_name = cpmanage.getName();
    cp_address = cpmanage.getAddress();
    cp_web = cpmanage.getWeb();
    cp_no = cpmanage.getCpNo();
    remarks = cpmanage.getRemarks();
    login_user = cpmanage.getLoginUser();
    
    Userinfo userinfo = (Userinfo)userinfodao.getById(login_user);
    cp_username = userinfo.getUsername();
    cp_password = userinfo.getPass();
}
else 
{
	CpManageDao cpmanageDao=new CpManageDao();
	ArrayList<Object> list=cpmanageDao.getList("select * from cp_manage order by id desc limit 1");
	cp_no = cpmanageDao.getCpNo();
	
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
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
    //if(!CheckForm.checkForm("mainForm"))return;
    if('<%=id%>'=="0"){
        document.mainForm.action="cfgCpManageAction.jsp?op=add";
        document.mainForm.submit();
    }
   else{//编辑功能
        document.mainForm.action="cfgCpManageAction.jsp?op=edit&pageno=<%=pageno%>&id=<%=id%>";
        document.mainForm.submit();
    }
}
</script>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_cp_no">公司代号</td>
    <td width="33%"><input name="cp_no" alt="cp_no" value="<%=cp_no %>" maxlength="250"/></td>
</tr>

<tr align="center">
    <td width="33%" id="title_cp_name">公司名称</td>
    <td width="33%"><input name="cp_name" alt="cp_name" value="<%=cp_name %>" maxlength="250"/></td>
<td>
<span id="warn_sp_name"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_cp_address">公司地址</td>
    <td width="33%"><input name="cp_address" alt="cp_address" value="<%=cp_address %>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_cp_web">公司官网</td>
    <td width="33%"><input name="cp_web" alt="cp_web" value="<%=cp_web %>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_cp_username">登录用户名</td>
    <td width="33%"><input name="cp_username" alt="cp_username" value="<%=cp_username %>" maxlength="250"/></td>
<td>

<tr align="center">
    <td width="33%" id="title_cp_password">登录密码</td>
    <td width="33%"><input name="cp_password" alt="cp_password" value="<%=cp_password %>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_remarks">备注</td>
    <td width="33%"><input name="remarks" alt="remarks" value="<%=remarks %>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
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
<script type="text/javascript">
</script>
