<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="util.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
String pageTitle="编辑系统设置";
int id=azul.JspUtil.getInt(request.getParameter("id"),0);
String switch_state=SysParam.switch_off;//取指令开关
String switch_time="00:00:00-24:00:00";//取指令开关时间段
String begin_time=DateUtil.getDate();//开始时间
String end_time=DateUtil.getDate();//结束时间
int mm_day_pay=10;//单用户日限金额
int mm_month_pay=20;//单用户月限金额
int mm_channel_pay=30000;//渠道日限金额
String filter_region="广州";//屏蔽地区
String filter_time="00:00:00-07:00:00;19:00:00-24:00:00";//屏蔽时间
String filter_circle="0_6";//屏蔽周期
String vague_state=SysParam.switch_off;
String vague_time = "";
String vague_addr = "";
int vague = 1;

//if(id!=0){
    pageTitle="编辑信息ID:"+id;
    SysParamDao sysParamDao = new SysParamDao();
    SysParam sysParam = (SysParam)sysParamDao.getRecord();
    if(sysParam != null)
   {
    vague_state = sysParam.getVagueState();
    vague_time = sysParam.getVagueTime();
    vague_addr = sysParam.getVagueAddr();
    
    switch_state = sysParam.getSwitchState();
    begin_time = sysParam.getBeginTime();
    end_time = sysParam.getEndTime();
    switch_time = sysParam.getSwitchTime();
    mm_day_pay = sysParam.getMmDayPay();
    mm_month_pay = sysParam.getMmMonthPay();
    mm_channel_pay = sysParam.getMmChannelPay();
    filter_region = sysParam.getFilterRegion();
    filter_time = sysParam.getFilterTime();
    filter_circle = sysParam.getFilterCircle();
    id = sysParam.getId();
   }
   else
   {
       id = 0;
   }
//}
java.util.Map<String,String> vague_stateMap=new HashMap<String,String>();
vague_stateMap.put(SysParam.switch_off,"关闭");
vague_stateMap.put(SysParam.switch_on,"打开");



%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
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
    if(!CheckForm.checkForm("mainForm"))return;
    if('<%=id%>'=="0"){
        document.mainForm.action="SysParamAction.jsp?op=add&id=<%=id%>&vague=1";
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="SysParamAction.jsp?op=edit&id=<%=id%>&vague=1";
        document.mainForm.submit();
    }
}
</script>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_vague_state">模糊开关</td>
    <td width="33%">
 <select name="vague_state" id="vague_state">
</select>	</td>
<td>
<span id="warn_sell_id"></span></td>

<tr align="center">
    <td width="33%" id="title_vague_addr">屏蔽地区</td>
    <td width="33%"><input name="vague_addr" alt="vague_addr_null" value="<%=vague_addr%>" maxlength="250"/></td>
<td>
<span id="warn_sp_vague_region">如果有多个地区，以"_"隔开</span></td>
</tr>

<tr align="center">
    <td width="33%" id="title_vague_time">屏蔽时间段</td>
    <td width="33%"><input name="vague_time" alt="vague_time_null" value="<%=vague_time%>" maxlength="250"/></td>
<td>
<span id="warn_sp_vague_time">如果有多个时间段，以";"隔开</span></td>
</tr>
<tr align="center"style="display: none">
    <td width="33%" id="title_mm_day_pay">单用户日限金额</td>
    <td width="33%"><input name="mm_day_pay" alt="mm_day_pay" value="<%=mm_day_pay%>" maxlength="250"/></td>
<td>
<span id="warn_sp_mm_day_pay"></span></td>
</tr>
<tr align="center"style="display: none">
    <td width="33%" id="title_mm_month_pay">单用户月限金额</td>
    <td width="33%"><input name="mm_month_pay" alt="mm_month_pay" value="<%=mm_month_pay%>" maxlength="250"/></td>
<td>
<span id="warn_sp_mm_month_pay"></span></td>
</tr>
<tr align="center"style="display: none">
    <td width="33%" id="title_mm_channel_pay">渠道日限金额</td>
    <td width="33%"><input name="mm_channel_pay" alt="mm_channel_pay" value="<%=mm_channel_pay%>" maxlength="250"/></td>
<td>
<span id="warn_sp_mm_channel_pay"></span></td>
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
<%
out.println(azul.JspUtil.initSelect("vague_state",vague_stateMap,vague_state));
%>
</script>