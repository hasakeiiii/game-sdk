<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ page import="java.util.*" %>
<%
String sort_by= JspUtil.getStr(request.getParameter("sort_by"),"");
String sort_order= JspUtil.getStr(request.getParameter("sort_order"),"");
StringBuffer sqlSb=new StringBuffer("select * from cfg_sp_send");
azul.JspUtil.appendOrder(sqlSb,sort_by,sort_order,"sid","");
CfgSpSendDao cfgSpSendDao=new CfgSpSendDao();
List list=cfgSpSendDao.getList(sqlSb.toString());

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
<td width="6%">序号</td>
<td width="5%" id="js_sort_sid">SID</td>
<td width="7%" id="js_sort_msg">命令</td>
<td width="10%" id="js_sort_spnum">地址</td>
<td width="12%">地区</td>
<td width="28%">mo地址</td>
<td width="26%">mr地址</td>
<td width="6%">操作</td></tr>
<%
ArrayList<String> msgList=new ArrayList<String>();
for(int i=0;i<list.size();i++){
	CfgSpSend cfgSpSend=(CfgSpSend)list.get(i);
	String msg=cfgSpSend.getMsg();
	String sid=cfgSpSend.getSid();
	String spnum=cfgSpSend.getSpnum();
	String areaCode=azul.KeyTool.getAreaByCode(cfgSpSend.getProvince());
	if("".equals(areaCode)){
		areaCode="全部";
	}
	else{
		areaCode="<span class=\"tdGreen\">"+areaCode+"</span>";
	}
	String tdClass="";
    if(!msgList.contains(sid+"_"+msg)){
    	msgList.add(sid+"_"+msg);
    }
    else{
    	tdClass="tdRed";
    }
%>
<tr>
<td title="<%=cfgSpSend.getCfgSpSendId()%>"><%=i+1%></td>
<td><%=azul.JspUtil.getLink(sid,sidMap)%></td>
<td class="<%=tdClass%>"><%=msg%></td>
<td><%=spnum%></td>
<td><%=areaCode%></td>
<td><%=cfgSpSend.getMo()%></td>
<td><%=cfgSpSend.getMr()%></td>
<td><a href="#" onclick="editAct('<%=cfgSpSend.getCfgSpSendId()%>')"><img src="../_js/ico/btn_edit.gif" border="0" alt="编辑"/></a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="deleteAct('<%=cfgSpSend.getCfgSpSendId()%>')"><img src="../_js/ico/btn_delete.gif" border="0" alt="删除"/></a></td>
</tr>
<%
}
%>
</table>
</form>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableSort.js"></script>
<script>
function addAct(){
    window.location="cfgSpSendEdit.jsp";
}
function editAct(cfg_sp_send_id){
    window.location="cfgSpSendEdit.jsp?cfg_sp_send_id="+cfg_sp_send_id;
}
function deleteAct(cfg_sp_send_id){
    if(window.confirm("确定删除选定信息?")){
        document.mainForm.action="cfgSpSendAction.jsp?op=delete&cfg_sp_send_id="+cfg_sp_send_id;
        document.mainForm.submit();
    }
}
TableSort.mySort("<%=sort_by%>","<%=sort_order%>","");
</script>
</body>
</html>
