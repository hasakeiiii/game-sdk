<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check=channelMoneyEdit.jsp" flush="true" />

<%
String pageTitle="添加信息";
Userinfo sysUser=(Userinfo)session.getAttribute("sysUser");
String role = sysUser.getRole();
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id=azul.JspUtil.getInt(request.getParameter("id"),0);

String op =  request.getParameter("op");


String channel_no="";//渠道代号

String packet_id="";//渠道号
String app_pay = "";
int limitMoney = 50000;

DebuUtil.log("cooperationedit");

//新增的时候会选择游戏，此时会由于执行刷新来到这里 此时id=0
if(op.equals("selapp"))
{

}

java.util.Map<String,String> pay_markMap=new HashMap<String,String>();
pay_markMap.put("5","掌中破解");
pay_markMap.put("4","安安破解");
pay_markMap.put("3","联网破解");
pay_markMap.put("2","本地破解2");
pay_markMap.put("1","本地破解");
pay_markMap.put( "0","关");

java.util.Map<String,String> b_delMap=new HashMap<String,String>();
b_delMap.put( "0","关闭");
b_delMap.put( "1","拦截");
java.util.Map<String,String> vagueMap=new HashMap<String,String>();
vagueMap.put( "0","关闭");
vagueMap.put( "1","打开");

java.util.Map<String,String> settle_typeMap=new HashMap<String,String>();
settle_typeMap.put("CPS", "CPS");
settle_typeMap.put("CPA", "CPA");
settle_typeMap.put("CPA_R", "CPA_R");

//当处于编辑信息时，即修改某个游戏的信息时
if(id!=0){
    CooperationMoneyDao cooperationMoneyDao=new CooperationMoneyDao();
	if(op.equals("edit")){
    pageTitle="编辑信息ID:"+id;
    CooperationMoney cooperationMoney=(CooperationMoney)cooperationMoneyDao.getById(id);
    channel_no = cooperationMoney.getChannelNo();
    
    AppDao appDao=new AppDao();
    packet_id = cooperationMoney.getPacketId();
    app_pay = cooperationMoney.getAppPay();
    limitMoney = cooperationMoney.getLimitMoney();
	}else if(op.equals("delete")){
		 int delete = cooperationMoneyDao.deleteCooperationMoney(String.valueOf(id));
		 
	}

}
else
{
	
}
ChannelDao channelDao=new ChannelDao();
java.util.Map<String,String> channelMap=channelDao.getSelectMap("select no,concat(name,'(',no,')') from channel order by no");
AppPayDao appPayDao=new AppPayDao();
java.util.Map<String,String> appPayMap=channelDao.getSelectMap("select no,concat(name,'(',no,')') from app_pay order by no");
//Map sidMap=cfgSpDao.getSelectMap("select sp_code,concat(sp_name,'(',sp_code,')') from cfg_sp");
AppDao appDao=new AppDao();
java.util.Map<String,String> appMap = new HashMap<String, String>();
if((role.equals("adminOnline"))  || (role.equals("oper_Online")) ){//网游管理员角色
 appMap=channelDao.getSelectMap("select no,name from app where game_type = 'on_line'");
}else{
 appMap=channelDao.getSelectMap("select no,name from app where (game_type = 'off_line' or game_type = 'mm_on_line')");
}
BusinesserDao businesserDao=new BusinesserDao();
java.util.Map<String,String> businesserMap=businesserDao.getSelectMap("select no,name from businesser");

MmPayDataDao mmPayDataDao = new MmPayDataDao();
Map<String,String> pay_idMap = mmPayDataDao.getSelectMap("select pay_id,pay_name from mm_pay_data");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="../_css/easyui.css"/>
</head>
<body>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script type="text/javascript" src="../_js/Check.js"></script>
<script type="text/javascript" src="../_js/CheckForm.js"></script>
<script type="text/javascript" src="../_js/jquery.min.js"></script>
<script type="text/javascript" src="../_js/jquery.easyui.min.js"></script>
<script language="javascript">
window.onload = function() {
	CheckForm.formBind("mainForm");
}
function chngRitio(){
    if(!CheckForm.checkForm("mainForm"))return;
    
     document.mainForm.action="channelMoneyAction.jsp?op=chngRitio&pageno=<%=pageno%>&id=<%=id%>";
     document.mainForm.submit();
}
function btnSave(){
    if(!CheckForm.checkForm("mainForm"))return;
    if('<%=id%>'=="0"){
        document.mainForm.action="channelMoneyAction.jsp?op=add";
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="channelMoneyAction.jsp?op=edit&pageno=<%=pageno%>&id=<%=id%>";
        document.mainForm.submit();
    } 
}
function channelchg(obj)
{
	document.mainForm.action="cfgCooperationEdit.jsp?op=selapp&id=<%=id%>";
	//为了保证刷新界面时数据能够全部跟新，将id赋值为2
	document.mainForm.submit();
}


</script>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">

<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_channel_no">渠道名称</td>
    <td width="33%">	
	<select name="channel_no" id="channel_no" alt="channel_no">
	<option value="">请选择</option>
	</select></td>
	<td>
	<span id="warn_sell_id"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_app_pay">计费游戏</td>
    <td width="33%">	
	<select name="app_pay" id="app_pay" alt="app_pay">
	<option value="">请选择</option>
	</select></td>
	<td>
	<span id="warn_sell_id"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_packet_id">APK包号</td>
    <td width="33%"><input name="packet_id" alt="packet_id" value="<%=packet_id%>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>

<tr align="center">
    <td width="33%" id="title_limitMoney">总限额</td>
    <td width="33%"><input name="limitMoney" alt="limitMoney" value="<%=limitMoney%>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>

<%--<tr align="center">
    <td width="33%" id="title_mm_day_pay">单用户日限金额</td>
    <td width="33%"><input name="mm_day_pay" alt="mm_day_pay_null" value="<%=mm_day_pay%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>

<tr align="center">
    <td width="33%" id="title_mm_month_pay">单用户月限金额</td>
    <td width="33%"><input name="mm_month_pay" alt="mm_month_pay_null" value="<%=mm_month_pay%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>

<tr align="center">
    <td width="33%" id="title_mm_channel_pay">渠道日限金额</td>
    <td width="33%"><input name="mm_channel_pay" alt="mm_channel_pay_null" value="<%=mm_channel_pay%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>


</table>
</div>



<table width="100%" border="0">

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

<%
String str = azul.JspUtil.initSelectSortByValuePinyin("channel_no",channelMap,channel_no);
String str1 = azul.JspUtil.initSelectSortByValuePinyin("app_pay",appPayMap,app_pay);
out.println(str);
out.println(str1);

%>
</script>
<script type="text/javascript">
</script>
