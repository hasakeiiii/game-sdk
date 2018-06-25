<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check=payListEdit.jsp" flush="true" />
<%
String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id=azul.JspUtil.getInt(request.getParameter("id"),0);

String payName="";
String appPayId="";
String gameNo = "";
String company = "";//所属公司
int limitMoney= 50000;
int limitUserMoney= 100;
String provinceMoney = "";
String province = "";
String shieldRegion = "";
String shieldTime = "";
int moneycount= 80;
int priority = 0;

java.util.Map<String,String> companyMap=new HashMap<String,String>();
companyMap.put(AppPay.MZYW,"拇指游玩");
companyMap.put(AppPay.MZHY,"拇指互娱");
companyMap.put(AppPay.ZTY,"中泰源");
companyMap.put(AppPay.JY,"竟游");
companyMap.put(AppPay.CQ,"创趣");
companyMap.put(AppPay.GY,"光游");
companyMap.put(AppPay.HLX,"宏乐欣");
companyMap.put(AppPay.QT,"其他");
companyMap.put(AppPay.QSZD,"奇硕智达");
companyMap.put(AppPay.MYKJ,"妙悟科技");
companyMap.put(AppPay.LST,"联思泰");
companyMap.put(AppPay.KTX,"科特迅");
companyMap.put(AppPay.YSSJ,"原宿设计");
companyMap.put(AppPay.LB,"遛宝");
companyMap.put(AppPay.WG,"吴贵");
companyMap.put(AppPay.BXL,"冰雪狼");
companyMap.put(AppPay.YY,"页游");

if(id!=0){
    pageTitle="编辑信息ID:"+id;
    AppPayDao appPayDao = new AppPayDao();
    AppPay cfgSp = (AppPay)appPayDao.getById(id);
/*     MmPayDataDao mmPayDataDao=new MmPayDataDao();
    MmPayData cfgSp=(MmPayData)mmPayDataDao.getById(id); */
    id=cfgSp.getId();
    payName=cfgSp.getName();
    appPayId=cfgSp.getAppPayId();
    gameNo = cfgSp.getNo();
    company = cfgSp.getCompany();
    limitMoney = cfgSp.getLimitMoney();
    limitUserMoney = cfgSp.getLimitUserMoney();
    provinceMoney = cfgSp.getProvinceMoney();
    province = cfgSp.getProvince();
    shieldRegion = cfgSp.getShieldRegion();
    shieldTime = cfgSp.getShieldTime();
    moneycount = cfgSp.getMoneycount();
    priority = cfgSp.getPriority();
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
        document.mainForm.action="payListAction.jsp?op=add";
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="payListAction.jsp?op=edit&pageno=<%=pageno%>&id=<%=id%>";
        document.mainForm.submit();
    }
}
</script>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<%-- <tr align="center">
    <td width="33%" id="title_id">游戏ID</td>
    <td width="33%"><input name="id" alt="id" value="<%=id%>" maxlength="250" /></td>
<td>
<span id="warn_sp_name"></span></td>
</tr> --%>
<tr align="center">
    <td width="33%" id="title_payName">游戏名称</td>
    <td width="33%"><input name="payName" alt="payName" value="<%=payName %>" maxlength="250" /></td>
<td>
<span id="warn_sp_no"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_gameNo">游戏编号</td>
    <td width="33%"><input name="gameNo" alt="gameNo" value="<%=gameNo %>" maxlength="250" /></td>
<td>
<span id="warn_sp_no"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_appPayId">备注</td>
    <td width="33%"><input name="appPayId" alt="appPayId" value="<%=appPayId%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_limitMoney">计费游戏总限额</td>
    <td width="33%"><input name="limitMoney" alt="limitMoney" value="<%=limitMoney%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_limitUserMoney">单用户限额</td>
    <td width="33%"><input name="limitUserMoney" alt="limitUserMoney" value="<%=limitUserMoney%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_provinceMoney">分省限额</td>
    <td width="33%"><input name="provinceMoney" alt="provinceMoney_null" value="<%=provinceMoney%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_province">分省</td>
    <td width="33%"><input name="province" alt="province_null" value="<%=province%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_shieldRegion">屏蔽省份</td>
    <td width="33%"><input name="shieldRegion" alt="shieldRegion_null" value="<%=shieldRegion%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_shieldTime">屏蔽时间</td>
    <td width="33%"><input name="shieldTime" alt="shieldTime_null" value="<%=shieldTime%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_moneycount">金额百分比</td>
    <td width="33%"><input name="moneycount" alt="moneycount" value="<%=moneycount%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_priority">优先级</td>
    <td width="33%"><input name="priority" alt="priority" value="<%=priority%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span></td>
</tr>
 <tr align="center">
				<td width="33%" id="title_company">所属公司</td>
				<td width="33%"><select name="company" id="company">
				</select></td>
				<td><span id="warn_sp_no"></span>
				</td>
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
<%out.println(azul.JspUtil.initSelect("company",companyMap,company));%>
</script>