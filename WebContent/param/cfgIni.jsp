<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.sql.*" %>
<jsp:include page="../check.jsp?check_role=admin" flush="true" />
<%
	//ConfigIni.makeValue("feeCut","feeCut","9");
String feeCut=common.ConfigIni.getValue("feeCut","feeCut");

Connection conn = null;
Statement stmt = null;
ResultSet rs = null;
java.util.HashMap map = new java.util.HashMap();
try {
	conn = db.ConnectionFactory.getInstance().getConnection();
	stmt = conn.createStatement();
	rs = stmt.executeQuery("show tables");
	while (rs.next()) {
		String table_name = rs.getString(1);
		String model_name = table_name.toLowerCase();
		map.put(model_name, table_name);
	}
} catch (Exception e) {
	System.err.println(e);
} finally {
	db.ConnectionFactory.close(rs,stmt,conn);
}
String noCidTrigger="";//azul.CacheFee.noCidTrigger?"关闭无厂商包月":"开启无厂商包月";
String openSql=application.getAttribute("_openSql")==null?"":application.getAttribute("_openSql").toString();
openSql=openSql.equals("true")?"关闭sql输出":"开启sql输出";
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
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/Check.js"></script>
<script type="text/javascript" src="../_js/CheckForm.js"></script>
<script language="javascript">
function saveFeeCut(){
    if(!CheckForm.checkForm("mainForm"))return;
    document.mainForm.action="cfgIniAction.jsp?op=feeCut";
    document.mainForm.submit();
}
function setArea(){
    if(window.confirm("确定同步费用地区信息?")){
        location="cfgIniAction.jsp?op=setArea";
    }
}
function setSmsArea(){
    if(window.confirm("确定同步销统地区?")){
        location="cfgIniAction.jsp?op=setSmsArea";
    }
}
function setSms(){
    if(window.confirm("确定同步销统费用?")){
        location="cfgIniAction.jsp?op=setSms";
    }
}
function statCharge(){
    if(window.confirm("确定根据销统同步无厂商数据?")){
        location="cfgIniAction.jsp?op=statCharge";
    }
}
function feeCid(){
    if(window.confirm("确定根据设定同步无厂商数据?")){
        location="cfgIniAction.jsp?op=feeCid";
    }
}
function setCid(){
    if(window.confirm("确定厂商默认权限?")){
        location="cfgIniAction.jsp?op=setCid";
    }
}
function setSell(){
    if(window.confirm("确定商务默认权出?")){
        location="cfgIniAction.jsp?op=setSell";
    }
}
function noCidTrigger(){
    if(window.confirm("操作无厂商包月开关?")){
        location="cfgIniAction.jsp?op=noCidTrigger";
    }
}
function monthTable(){
    if(window.confirm("本操作只能在每月初日运行，不要多次运行")){
        location="cfgIniAction.jsp?op=monthTable";
    }
}
function getWeather(){
    if(window.confirm("确定更新天气情况？")){
        location="cfgIniAction.jsp?op=getWeather";
    }
}
function setCache(){
	if(getElem("table_name")=="-1"){
	    alert("请选择需要清除的表名");
		return;
	}
    if(window.confirm("确定清除对应缓存?")){
        location="cfgIniAction.jsp?op=clearCache&table_name="+getElem("table_name");
    }
}
function setSql(){
   location="cfgIniAction.jsp?op=setSql";
}
</script>
<h1>系统参数设置</h1><span class="tdRed">不清楚具体作用请不要使用功能,部分操作会影响扣费</span>
<table width="100%" border="1">
  <tr>
    <td height="60"></td>
    <td>&nbsp;</td>
    <td><input name="button2" type="button" onclick="setCid()" value="厂商默认权限"/></td>
    <td><input name="button3" type="button" onclick="setSell()" value="商务默认权出"/></td>
    <td></td>
  </tr>
  <tr>
    <td height="60"><input type="button" value="费用地区信息" onclick="setArea()"/></td>
    <td><input type="button" value="同步销统费用" onclick="setSms()"/></td>
    <td><input type="button" value="同步销统地区" onclick="setSmsArea()"/></td>
    <td><input name="button5" type="button" onclick="feeCid()" value="设定同步厂商数据"/></td>
    <td><input name="button5" type="button" onclick="statCharge()" value="销统同步厂商数据"/></td>
  </tr>
  <tr>
    <td height="60"><input name="button" type="button" onclick="setSql()" value="<%=openSql%>"/></td>
    <td><input name="button4" type="button" onclick="noCidTrigger()" value="<%=noCidTrigger%>"/></td>
    <td><input name="button5" type="button" onclick="monthTable()" value="月初自动分表"/></td>
    <td><input name="button6" type="button" onclick="getWeather()" value="更新天气情况"/></td>
    <td>&nbsp;</td>
  </tr>
</table>
<br>
请选择需要清除缓存的数据表&nbsp;&nbsp;
 <select name="table_name" id="table_name">
        <option value="-1">请选择</option>
 </select>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="清除缓存" onclick="setCache()"/>
</body>
</html>
<script>
<%
out.println(azul.JspUtil.initSelect("table_name",map,""));
%>
</script>
