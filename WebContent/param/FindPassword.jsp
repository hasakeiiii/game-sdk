<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
String username=azul.JspUtil.getStr(request.getParameter("username"),"");
String deviceid=azul.JspUtil.getStr(request.getParameter("deviceid"),"");
String action=azul.JspUtil.getStr(request.getParameter("op"),"");
RegisterDao registerDao = new RegisterDao();
Register register = null;
ArrayList<Object> list = new ArrayList<Object>();
     if(action.equals("ation"))
     {
    	 if(username.equals("1234"))
    	 {
    		 /*PayDao payDao = new PayDao();
    		 payDao.test_temp();*/
                 //ChannelDataDao channelDataDao = new ChannelDataDao();
	         //channelDataDao.settleDayPayNum();
    		 
    		 /*ActivateDao activateDao = new ActivateDao();
    		 activateDao.test_temp();
    		 		 
    		 RegisterDao registerDao2 = new RegisterDao();
    		 registerDao2.test_temp();
    		 
    		 LoginDao loginDao = new LoginDao();
    		 loginDao.test_temp();*/
                  ChannelDataDao channelDataDao = new ChannelDataDao();
	         channelDataDao.settleXingXingPay();
    		 
    	 }
    	 else
    	 {
    	     list=registerDao.getRegisterList(username,deviceid);
    	 }
     }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<td width="10%">账号&nbsp;&nbsp;<input name="username" value="<%=username%>" />&nbsp;&nbsp;
</td>
<td width="10%">设备IMEI&nbsp;&nbsp;<input name="deviceid" value="<%=deviceid%>" />&nbsp;&nbsp;
</td>

<td width="10%">
<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/>
</a></td>
</tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td>帐号ID</td>
<td>帐号</td>
<td>密码</td>
<td>IMEI</td>
<td>包体号</td>
<td>注册时间</td>
<td>在线时长</td>
<td>角色级别</td>
<%
    for(int i=0;i<list.size();i++)
    {
	   String time="";
	   String onlinetime="";
	   String level="";
	   register=(Register)list.get(i);
	   time=register.getDate()+register.getTime();
	   onlinetime = String.format("%d", register.getOnlinetime());
	   level = String.format("%d", register.getLevel());
%>
<tr>
<td><%=register.getId()%></td>
<td><%=register.getId()%></td>
<td><%=register.getUsername()%></td>
<td><%=register.getPass()%></td>
<td><%=register.getDeviceId()%></td>
<td><%=register.getPacketId()%></td>
<td><%=time%></td>
<td><%=onlinetime%></td>
<td><%=level%></td>
</tr>
<%
}
%>
</table>
</form>

<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
function addAct(){
   
}

function editAct(id){
    //window.location="notisfyCPAction.jsp?id="+id;
	//PayDao  payDao=new PayDao();
}
function infoAct(id){
    //window.location="cfgSpInfo.jsp?id="+id;
}
function goSearch(){
    /*if(document.mainForm.keyWord.value==""){
	     error("请输入查询关键字");
		 document.mainForm.keyWord.focus();
		 return;
	}*/
	document.mainForm.action="FindPassword.jsp?op=ation";
	document.mainForm.submit();
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
