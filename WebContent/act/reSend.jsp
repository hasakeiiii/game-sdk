<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<title>后台管理系统</title>
<script type="text/javascript" src="../_js/dateTimePacker.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
</head>
<body>
<%
dao.CfgCompanyDao cfgCompanyDao=new dao.CfgCompanyDao();
java.util.Map cidMap=cfgCompanyDao.getSelectMap("select cid,concat(name,'(',cid,')') from cfg_company order by name");

String startDate=azul.JspUtil.getStr(request.getParameter("startDate"),"");
String endDate= azul.JspUtil.getStr(request.getParameter("endDate"),"");
String cid= azul.JspUtil.getStr(request.getParameter("cid"),"");
String[] arr=azul.JspUtil.getDateDay(startDate,endDate);
startDate=arr[0];
endDate=arr[1];
String ok=azul.JspUtil.getStr(request.getParameter("ok"),"");
String url=azul.CacheSp.getUrl(cid);
if("1".equals(ok) && !"".equals(url)){
    Connection connA = null;
	PreparedStatement pstmtA = null;
	ResultSet rsA = null;
	ArrayList list=new ArrayList();
	try {
		connA = db.ConnectionFactory.getInstance().getConnection();
		pstmtA = connA.prepareStatement("select * from charge where fee_type='A' and ok=1 and cid='"+cid+"' and date_time>'"+startDate+"' and date_time<'"+endDate+"'");
		System.out.println("select * from charge where fee_type='A' and ok=1 and cid='"+cid+"' and date_time>'"+startDate+"' and date_time<'"+endDate+"'");
		rsA = pstmtA.executeQuery();
		while (rsA.next()) {
			StringBuffer sb=new StringBuffer("");
			sb.append("&linkid=").append(rsA.getString("linkid")).append("&mobile=").append(rsA.getString("mobile")).append("&spNum=").append(rsA.getString("spnum")).append("&momsg=").append(rsA.getString("msg"));
			list.add(sb.toString());
		}
		System.out.println("ok");
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		db.ConnectionFactory.close(rsA, pstmtA, connA);
	}
	if(url.indexOf("?")==-1){
		url+="?1=1";
	}
	for(int i=0;i<list.size();i++){
		String links=(String)list.get(i);
		azul.KeyTool.sendGetData(url+links);
	}
	%>
	<script>
	ok("操作成功",callback);
	function callback(){
		location="reSend.jsp";
	}
	</script>
	<%
}
%>
<form name="mainForm" method="post">
<p>
 起始日期&nbsp;&nbsp;<input name="startDate" type="text" id="startDate" readonly="true" onFocus='javascript:calendar.showCalendar(startDate,null,null)'/>
&nbsp;截止日期&nbsp;
<input name="endDate" type="text" id="endDate" readonly="true" onFocus='javascript:calendar.showCalendar(endDate,null,null)'/>
&nbsp;CID
<select name="cid" id="cid" alt="cid">
  <option value="">请选择</option>
</select>
<a href="#" onclick="mySearch()"><img src="../_js/ico/btn_ok.gif" border="0" alt="确定" align="absmiddle"/></a></p>
</form>
<script>
<%
out.println(azul.JspUtil.initSelect("cid",cidMap,cid));
%>
function mySearch(){
   document.mainForm.action="reSend.jsp?ok=1";
   document.mainForm.submit();
}
</script>
</body>
</html>