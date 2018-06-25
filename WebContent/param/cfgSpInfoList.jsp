<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ page import="java.util.*" %>
<%@ include file="../check.jsp"%>
<%
CfgSpInfoDao cfgSpInfoDao=new CfgSpInfoDao();
ArrayList list=cfgSpInfoDao.getList("select * from cfg_sp_info order by sid");

CfgSpDao cfgSpDao=new CfgSpDao();
Map sidMap=cfgSpDao.getSelectMap("select sp_code,concat(sp_name,'(',sp_code,')') from cfg_sp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<td width="90%">&nbsp;</td>
<td width="10%" align="right"><a href="#" onclick="addAct()"><img src="../_js/ico/btn_add.gif" border="0" alt="增加" align="absmiddle"/></a></td></tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td width="9%">SID</td>
<td width="9%">地址</td>
<td width="8%">命令</td>
<td width="5%">运营商</td>
<td width="4%">价格</td>
<td width="11%">省份</td>
<td width="14%">屏蔽地区</td>
<td width="29%">下发语</td>
<td width="6%">回复</td>
<td width="7%">上限</td>
<td width="7%">操作</td>
</tr>
<%
for(int i=0;i<list.size();i++){
    CfgSpInfo cfgSpInfo=(CfgSpInfo)list.get(i);
    String trone=cfgSpInfo.getTrone();
    String command=cfgSpInfo.getCommand();
    String oper=cfgSpInfo.getOperator()==1?"移动":"联通";
    int price=cfgSpInfo.getPrice();
    String province=azul.KeyTool.getAreaByCode(cfgSpInfo.getProvince());
    String city=azul.KeyTool.getCityByCode(cfgSpInfo.getCity());
    String info=cfgSpInfo.getInfo();
    String replay=cfgSpInfo.getReplay();
    String max=cfgSpInfo.getMax();
%>
<tr>
<td><%=azul.JspUtil.getLink(cfgSpInfo.getSid(),sidMap)%></td>
<td><%=trone%></td>
<td><%=command%></td>
<td><%=oper%></td>
<td><%=price%></td>
<td><%=province%></td>
<td><%=city%></td>
<td><%=info%></td>
<td><%=replay%></td>
<td><%=max%></td>
<td><a href="#" onclick="editAct('<%=cfgSpInfo.getCfgSpInfoId()%>')"><img src="../_js/ico/btn_edit.gif" border="0" alt="编辑"/></a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="deleteAct('<%=cfgSpInfo.getCfgSpInfoId()%>')"><img src="../_js/ico/btn_delete.gif" border="0" alt="删除"/></a></td>
</tr>
<%
}
%>
</table>
</form>
<script>
function addAct(){
    window.location="cfgSpInfoEdit.jsp";
}
function editAct(cfg_sp_info_id){
    window.location="cfgSpInfoEdit.jsp?cfg_sp_info_id="+cfg_sp_info_id;
}
function infoAct(cfg_sp_info_id){
    window.location="cfgSpInfoInfo.jsp?cfg_sp_info_id="+cfg_sp_info_id;
}
function deleteAct(cfg_sp_info_id){
    if(window.confirm("确定删除选定信息?")){
        document.mainForm.action="cfgSpInfoAction.jsp?op=delete&cfg_sp_info_id="+cfg_sp_info_id;
        document.mainForm.submit();
    }
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableRowBlank.js"></script>
</body>
</html>