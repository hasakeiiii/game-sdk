<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check=cfgCooperationEdit.jsp" flush="true" />
<script>

</script>
<%
String pageTitle="添加信息";
Userinfo sysUser=(Userinfo)session.getAttribute("sysUser");
String role = sysUser.getRole();
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id=azul.JspUtil.getInt(request.getParameter("id"),0);

String op =  request.getParameter("op");

String channel_name="";//渠道名称
String channel_no="";//渠道代号
String app_name="";//游戏名称
String app_no="";//游戏代号
String app_type="";//游戏类型
String business_no="";//商务代号
String packet_id="";//渠道号
String settle_type="";//结算类型
int settle_value = 1000;
int settle_ratio = 100;
String MM_APP_NO = "";
String discontent=ConstValue.discontent;
String discontent2=ConstValue.discontent2;
String disurl="";
String noturl = "";
String exiturl = "";
String pay_mark = "";//计费开关
String pay_url = "";//计费URL
String pay_id = "";//计费编号
String pay_urlb="";//计费回调
String b_del="";//是不是拦截
String fill_con="";//拦截内容
/* String vague ="";
String vague_time = "";
String vague_addr = "";
String alert = "";
String alert_time="";
String alert_addr = "";
String button = "";
String button_time="";
String button_addr= "";
String ppppp = "";
String ppppp_time ="";
String ppppp_addr = "";
String ver = "";
String cootype = ""; */
String mobile_vague = "";
String nuicom_vague = "";
String telecom_vague = "";
String default_vague = "";
String control_time="19:00:00-24:00:00;00:00:00-07:00:00";//控制时间
int mm_day_pay = 0;//MM日限
int mm_month_pay = 0;//MM月限
int mm_channel_pay = 0;//MM渠道限额
String filter_time="07:00:00-19:00:00";//屏蔽时间
String filter_region="广州";//屏蔽地区
String filter_begin_day=DateUtil.getDate();//屏蔽地区
String filter_end_day=DateUtil.getDate();//屏蔽地区
int cpscount = 100;
int cpacount = 100;


DebuUtil.log("cooperationedit");

//新增的时候会选择游戏，此时会由于执行刷新来到这里 此时id=0
if(op.equals("selapp"))
{
   String app = request.getParameter("app_no");
   String channel = request.getParameter("channel_no");
   channel_no = channel;
   app_no = app;
   AppDao appDao=new AppDao();
  
   
   String sql = "select game_type from app where no='"+app+"'";
   
   app_type = (String)appDao.getValue(sql);
 
   packet_id = channel+app;
}

java.util.Map<String,String> pay_markMap=new HashMap<String,String>();
pay_markMap.put("1","打开");

pay_markMap.put( "0","关闭");

java.util.Map<String,String> b_delMap=new HashMap<String,String>();
b_delMap.put( "0","关闭");
b_delMap.put( "1","拦截");
java.util.Map<String,String> vagueMap=new HashMap<String,String>();
vagueMap.put( "0","关闭");
vagueMap.put( "1","打开");
java.util.Map<String,String> alertMap=new HashMap<String,String>();
alertMap.put( "0","关闭");
alertMap.put( "1","打开");
java.util.Map<String,String> buttonMap=new HashMap<String,String>();
buttonMap.put( "0","关闭");
buttonMap.put( "1","打开");
java.util.Map<String,String> pppppMap=new HashMap<String,String>();
pppppMap.put( "0","关闭");
pppppMap.put( "1","打开");

java.util.Map<String,String> settle_typeMap=new HashMap<String,String>();
settle_typeMap.put("CPS", "CPS");
settle_typeMap.put("CPA", "CPA");
settle_typeMap.put("CPA_R", "CPA_R");

//当处于编辑信息时，即修改某个游戏的信息时
if(id!=0){
    pageTitle="编辑信息ID:"+id;
    CooperationDao cooperationDao=new CooperationDao();
    Cooperation cooperation=(Cooperation)cooperationDao.getById(id);
    channel_name = cooperation.getChannelName();
    channel_no = cooperation.getChannelNo();
    app_name = cooperation.getAppName();
    
    business_no = cooperation.getBusinessNo();
    app_no = cooperation.getAppNo();
    pay_id = cooperation.getPayId();
    
    AppDao appDao=new AppDao();
    String sql = "select game_type from app where no='"+app_no+"'";
    app_type = (String)appDao.getValue(sql);
    
    packet_id = cooperation.getPacketId();
    settle_type = cooperation.getSettleType();
    settle_value = cooperation.getSettleValue();
    settle_ratio = cooperation.getSettleRatio();
    MM_APP_NO = cooperation.getMmAppNo();
    pay_mark = cooperation.getPayMark();
    if(pay_mark.compareTo("1")>=0){
    	pay_mark = "1";
    }
    pay_url = cooperation.getPayUrl();
    pay_id = cooperation.getPayId();
    pay_urlb = cooperation.getPayUrlb();
    b_del = cooperation.getBDel();
    fill_con = cooperation.getFillCon();
/*     vague = cooperation.getVague();
    vague_time = cooperation.getVagueTime();
    vague_addr = cooperation.getVagueAddr();
    alert = cooperation.getAlert();
    alert_time = cooperation.getAlertTime();
    alert_addr = cooperation.getAlertAddr();
    button = cooperation.getButton();
    button_time = cooperation.getButtonTime();
    button_addr = cooperation.getButtonAddr();
    ppppp = cooperation.getPpppp();
    ppppp_time = cooperation.getPppppTime();
    ppppp_addr = cooperation.getPppppAddr();
    ver = cooperation.getVer();
    cootype = cooperation.getCootype(); */
    mobile_vague = cooperation.getMobileVague();
    nuicom_vague = cooperation.getNuicomVague();
    telecom_vague = cooperation.getTelecomVague();
    default_vague = cooperation.getDefaultVague();
    
    mm_day_pay = cooperation.getMmDayPay();
    mm_month_pay = cooperation.getMmMonthPay();
    mm_channel_pay = cooperation.getMmChannelPay();
    filter_time=cooperation.getFilterTime();;//屏蔽时间
    filter_region=cooperation.getFilterRegion();;//屏蔽地区
    filter_begin_day=cooperation.getFilterBeginDay();;//屏蔽地区
    filter_end_day=cooperation.getFilterEndDay();;//屏蔽地区
    cpscount = cooperation.getCpscount();
    cpacount = cooperation.getCpacount();

    String tempstr=cooperation.getDiscontent();
    if(tempstr.length() > 0)
    {
    	discontent = tempstr;
    }
    
    tempstr = cooperation.getControlTime(); 
    if(tempstr.length() > 0)
    {
    	control_time = tempstr;
    }
    

    tempstr=cooperation.getDiscontent2();
    if(tempstr.length() > 0)
    {
    	discontent2 = tempstr;
    }
    
    tempstr=cooperation.getDisurl();
    if(tempstr.length() > 0)
    {
    	disurl = tempstr;
    }
    
    tempstr=cooperation.getNoturl();
    if(tempstr.length() > 0)
    {
    	noturl = tempstr;
    }
    
    exiturl = cooperation.getExiturl();
}
else
{
	
}
ChannelDao channelDao=new ChannelDao();
java.util.Map<String,String> channelMap=channelDao.getSelectMap("select no,concat(name,'(',no,')') from channel order by no");
//Map sidMap=cfgSpDao.getSelectMap("select sp_code,concat(sp_name,'(',sp_code,')') from cfg_sp");
AppDao appDao=new AppDao();
java.util.Map<String,String> appMap = new HashMap<String, String>();
if((role.equals(Userinfo.adminOnline))  || (role.equals(Userinfo.operationOnline)) ){//网游管理员角色
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
    
     document.mainForm.action="cfgCooperationAction.jsp?op=chngRitio&pageno=<%=pageno%>&id=<%=id%>";
     document.mainForm.submit();
}
function btnSave(){
    if(!CheckForm.checkForm("mainForm"))return;
    if('<%=id%>'=="0"){
        document.mainForm.action="cfgCooperationAction.jsp?op=add";
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="cfgCooperationAction.jsp?op=edit&pageno=<%=pageno%>&id=<%=id%>";
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
    <td width="33%" id="title_app_no">游戏名称</td>
    <td width="33%">
<select name="app_no" onchange=channelchg(this) id="app_no">
  <option value="">请选择</option>
</select>	</td>
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
    <td width="33%" id="title_settle_type">结算类型</td>
    <td width="33%">
 <select name="settle_type" id="settle_type">
</select>	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_cpscount">S比例</td>
    <td width="33%"><input name="cpscount" alt="cpscount" value="<%=cpscount%>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_cpacount">A比例</td>
    <td width="33%"><input name="cpacount" alt="cpacount" value="<%=cpacount%>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_settle_value">MM支付限额</td>
    <td width="33%"><input name="settle_value" alt="settle_value" value="<%=settle_value%>" maxlength="250"/></td>
<td>
<span id="warn_sp_code">元</span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_settle_ratio">显示比例</td>
    <td width="33%"><input name="settle_ratio" alt="settle_ratio" value="<%=settle_ratio%>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_business_no">所属商务 </td>
    <td width="33%">
<select name="business_no" id="business_no">
  <option value="">请选择</option>
</select>	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr>
</table>


<div id="wy">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_discontent">登录跑马灯内容</td>
    <td width="33%"><input name="discontent" alt="discontent_null" value="<%=discontent%>" maxlength="250"/></td>
<td>
<span id="warn_discontent_code">填no屏蔽游戏管理配置内容</span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_noturl">登录广告链接</td>
    <td width="33%"><input name="noturl" alt="noturl_null" value="<%=noturl%>" maxlength="250"/></td>
<td>
<span id="warn_disurl_code">填no屏蔽游戏管理配置内容</span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_discontent2">支付跑马灯内容</td>
    <td width="33%"><input name="discontent2" alt="discontent2_null" value="<%=discontent2%>" maxlength="250"/></td>
<td>
<span id="warn_discontent2_code">填no屏蔽游戏管理配置内容</span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_disurl">支付广告链接</td>
    <td width="33%"><input name="disurl" alt="disurl_null" value="<%=disurl%>" maxlength="250"/></td>
<td>
<span id="warn_disurl_code">填no屏蔽游戏管理配置内容</span></td>
</tr>
</table>
</div>



<div id="dj">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_pay_mark">黑白包开关</td>
    <td width="33%">
 <select name="pay_mark" id="pay_mark">
</select>	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr>

<tr align="center">
    <td width="33%" id="title_control_time">黑白包控制时间</td>
    <td width="33%"><input name="control_time" alt="control_time_null" value="<%=control_time%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id">如果有多个时间段，以";"隔开,例如19:00:00-24:00:00;00:00:00-07:00:00</span></td>
</tr>

<tr align="center">
    <td width="33%" id="title_filter_region">屏蔽地区</td>
    <td width="33%"><input name="filter_region" alt="filter_region_null" value="<%=filter_region%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id">如果有多个地区，以"_"隔开，例如广州_广东</span></td>
</tr>

<tr align="center">
    <td width="33%" id="title_filter_time">屏蔽时间段</td>
    <td width="33%"><input name="filter_time" alt="filter_time_null" value="<%=filter_time%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id">如果有多个时间段，以";隔开,例如19:00:00-24:00:00;00:00:00-07:00:00</span></td>
</tr>

<tr align="center">
    <td width="33%" id="title_filter_begin_day">屏蔽开始日期</td>
    <td width="33%"><input name="filter_begin_day" alt="filter_begin_day_null" value="<%=filter_begin_day%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id">例如2015-03-18</span></td>
</tr>

<tr align="center">
    <td width="33%" id="title_filter_end_day">屏蔽结束日期</td>
    <td width="33%"><input name="filter_end_day" alt="filter_end_day_null" value="<%=filter_end_day%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id">例如2015-03-18</span></td>
</tr>

<tr align="center">
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
</tr>


<tr align="center">
    <td width="33%" id="title_bDel_mark">短信拦截开关</td>
    <td width="33%">
 <select name="b_del" id="b_del">
</select>	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_fill_con">短信拦截关键字</td>
    <td width="33%"><input name="fill_con" alt="fill_con_null" value="<%=fill_con%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_mobile_vague">移动界面</td>
    <td width="33%"><input name="mobile_vague" alt="mobile_vague_null" value="<%=mobile_vague%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_nuicom_vague">联通界面</td>
    <td width="33%"><input name="nuicom_vague" alt="nuicom_vague_null" value="<%=nuicom_vague%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr><tr align="center">
    <td width="33%" id="title_telecom_vague">电信界面</td>
    <td width="33%"><input name="telecom_vague" alt="telecom_vague_null" value="<%=telecom_vague%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_default_vague">默认界面</td>
    <td width="33%"><input name="default_vague" alt="default_vague_null" value="<%=default_vague%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>
<%-- <tr align="center">
    <td width="33%" id="title_ver">游戏版本控制</td>
    <td width="33%"><input name="ver" alt="ver_null" value="<%=ver%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span>多个控制用#分开</td>
</tr>
<tr align="center">
    <td width="33%" id="title_cootype">线上线下控制</td>
    <td width="33%"><input name="cootype" alt="cootype_null" value="<%=cootype%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span>0:</td>
</tr>
<tr align="center">
    <td width="33%" id="title_alert"> <font color=red >弹出开关</font></td>
    <td width="33%"><input name="alert" alt="alert_null" value="<%=alert%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span>0：关闭.1：打开</td>
</tr>
<tr align="center">
    <td width="33%" id="title_alert_time">弹窗时间段</td>
    <td width="33%"><input name="alert_time" alt="alert_time_null" value="<%=alert_time%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_alert_addr">弹窗地区</td>
    <td width="33%"><input name="alert_addr" alt="alert_addr_null" value="<%=alert_addr%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>

<tr align="center">
    <td width="33%" id="title_vague"><font color=red >支付模糊开关</font></td>
    <td width="33%"><input name="vague" alt="vague_null" value="<%=vague%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span>0：关闭.1：打开</td>
</tr>
<tr align="center">
    <td width="33%" id="title_vague_time">模糊时间段</td>
    <td width="33%"><input name="vague_time" alt="vague_time_null" value="<%=vague_time%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_vague_addr">模糊地区</td>
    <td width="33%"><input name="vague_addr" alt="vague_addr_null" value="<%=vague_addr%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>


<tr align="center">
    <td width="33%" id="title_button"><font color=red >按钮处理</font></td>
    <td width="33%"><input name="button" alt="button_null" value="<%=button%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span>0：购买.1：确认.2：领取</td>
</tr>
<tr align="center">
    <td width="33%" id="title_button_time">模糊时间段</td>
    <td width="33%"><input name="button_time" alt="button_time_null" value="<%=button_time%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_button_addr">模糊地区</td>
    <td width="33%"><input name="button_addr" alt="button_addr_null" value="<%=button_addr%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>

<tr align="center">
    <td width="33%" id="title_ppppp"><font color=red >二次确认开关</font></td>
    <td width="33%"><input name="ppppp" alt="ppppp_null" value="<%=ppppp%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span>0：关闭.1：打开</td>
</tr>
<tr align="center">
    <td width="33%" id="title_ppppp_time">模糊时间段</td>
    <td width="33%"><input name="ppppp_time" alt="ppppp_time_null" value="<%=ppppp_time%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_ppppp_addr">模糊地区</td>
    <td width="33%"><input name="ppppp_addr" alt="ppppp_addr_null" value="<%=ppppp_addr%>" maxlength="250"/></td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>



<tr align="center">
    <td width="33%" id="title_pay_id">计费名称</td>
    <td width="33%">
 <select name="pay_id" id="pay_id">
 <option value="">请选择</option>
</select>	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_pay_url">计费地址</td>
    <td width="33%"><input name="pay_url" alt="pay_url_null" value="<%=pay_url%>" maxlength="250"/></td>
<td>
<span id="warn_disurl_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_pay_urlb">计费回调</td>
    <td width="33%"><input name="pay_urlb" alt="pay_url_null" value="<%=pay_urlb%>" maxlength="250"/></td>
<td>
<span id="warn_disurl_code"></span></td>
</tr>


</table>
</div>



<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_exiturl">游戏退出公告</td>
    <td width="33%"><input name="exiturl" alt="exiturl_null" value="<%=exiturl%>" maxlength="250"/></td>
<td>
<span id="warn_disurl_code">填no屏蔽游戏管理配置内容</span></td>
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
String str = azul.JspUtil.initSelectSortByValuePinyin("channel_no",channelMap,channel_no);
out.println(str);
out.println(azul.JspUtil.initSelectSortByValuePinyin("app_no",appMap,app_no));
out.println(azul.JspUtil.initSelect("business_no",businesserMap,business_no));
out.println(azul.JspUtil.initSelect("settle_type",settle_typeMap,settle_type));
out.println(azul.JspUtil.initSelect("pay_mark",pay_markMap,pay_mark));
out.println(azul.JspUtil.initSelect("pay_id",pay_idMap,pay_id));
out.println(azul.JspUtil.initSelect("b_del",b_delMap,b_del));
/* out.println(azul.JspUtil.initSelect("vague",vagueMap,vague));
out.println(azul.JspUtil.initSelect("alert",alertMap,alert));
out.println(azul.JspUtil.initSelect("button",buttonMap,button));
out.println(azul.JspUtil.initSelect("ppppp",pppppMap,ppppp)); */

%>
</script>
<script type="text/javascript">
		var choice = document.getElementsByTagName("select")[2];
	
		if('<%=app_type%>'=="on_line"||'<%=app_type%>'==""){
			document.getElementById("dj").style.display = "none";
			document.getElementById("wy").style.display = "block";}
			
		else if('<%=app_type%>'=="off_line"){
			document.getElementById("wy").style.display = "none";
			document.getElementById("dj").style.display = "block";}
		
			
</script>