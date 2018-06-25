<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check=mmPayDataEdit.jsp" flush="true" />
<%
	String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id=azul.JspUtil.getInt(request.getParameter("id"),0);

java.util.Map<String,String> conpany_nameMap=new HashMap<String,String>();
conpany_nameMap.put("1","美林互动");
conpany_nameMap.put( "2","公司二");
conpany_nameMap.put( "3","李洁安");
conpany_nameMap.put( "4","自研");
conpany_nameMap.put( "5","掌中");
//移动
String payName="";//支付名称
String payId="";//支付代号
String payType = "";
String payAmount = "";
String conpany_name = "";//公司名
String payKey = "";//计费密匙
String publicKey = "";//计费公匙
String proId = "";//程序ID
String md5 = "";//md5
String md5dex = "";//md5dex
String verno = "1.2.3";//verno
String packageName="";//包名
String mainActivity="";//主界面名
String payCodeList="";
//移动奇葩页游
String webPartner = "1040";
String webKey1 = "f51a5f5770a39ad7c459667c24f8a7fb";
String webKey2 = "5f8ae0ba3467b28452e1697fd368e19f";
String webPid = "";
String webList = "";
String webUrl = "http://mbp.yiyugame.com/third/fpft.do";
String getNum="";
//联通
String unicomId="9008";
String unicomUrl="http://api.slxz.com.cn/charge-platform/client/client.php";
String unBkUrl="http://sa.91muzhi.com:8080/sdk/WoplusRsq";
String unKey="suhESjvk";
String unPayName="";
String unPayList="";
String unGoodId="";
String unName="";

String appPayId = "";//计费游戏

//电信
String tclId="OPEN_PARTNER_MUZHIYOUWAN";
String tclUrl="http://www.gomzone.com:8080/external/tySpaceltOpen_generateMultiOrder.action";
String tclBkUrl="http://sa.91muzhi.com:8080/sdk/Open189Rsq";
String tclKey="5438e99521e747e98a67450f3972e992";
String tclPayList="";
String tclName="";
//饭盒
String boxPayId = "";
String boxPayList = "";
String boxPayName = "";

String type = "";
int moneycount = 80;
int priority = 0;

int limitMoney = 20000;
int limitUserMoney = 200;
//动力
String powerId = "";
String powerPayCode = "";
String powerList = "";
String content = "";
String contentTwo = "";
String contentNum = "1065987701";
if(id!=0){
	//id!=0说明为数据编辑：先要获取原始数据显示
    pageTitle="编辑信息ID:"+id;
    MmPayDataDao mmPayDataDao=new MmPayDataDao();
    MmPayData mmPayData=(MmPayData)mmPayDataDao.getById(id);
    
    payName = mmPayData.getPayName();
    payId = mmPayData.getPayId();
    payType = mmPayData.getPayType();
    payAmount = mmPayData.getPayAmount();
    payKey = mmPayData.getPayKey();
    conpany_name = mmPayData.getConpanyName();
    publicKey = mmPayData.getPublicKey();
    proId = mmPayData.getProId();
    md5 = mmPayData.getMd5();
    md5dex = mmPayData.getMd5dex();
    verno = mmPayData.getVerno();
    packageName = mmPayData.getPackageName();
    mainActivity = mmPayData.getMainActivity();
    payCodeList = mmPayData.getPayCodeList();
    
    webPartner = mmPayData.getWebPartner();
    webKey1 = mmPayData.getWebKey1();
    webKey2 = mmPayData.getWebKey2();
    webPid = mmPayData.getWebPid();
    webList = mmPayData.getWebList();
    webUrl = mmPayData.getWebUrl();
    getNum = mmPayData.getGetNum();
    
    unicomId = mmPayData.getUnicomId();
    unicomUrl = mmPayData.getUnicomUrl();
    unBkUrl = mmPayData.getUnBkUrl();
    unKey = mmPayData.getUnKey();
    unPayName = mmPayData.getUnPayName();
    unPayList = mmPayData.getUnPayList();
    unGoodId = mmPayData.getUnGoodId();
    unName = mmPayData.getUnName();
    
    tclId= mmPayData.getTclId();
    tclUrl=mmPayData.getTclUrl();
    tclBkUrl=mmPayData.getTclBkUrl();
    tclKey=mmPayData.getTclKey();
    tclPayList=mmPayData.getTclPayList();
    tclName=mmPayData.getTclName();
    
    boxPayId = mmPayData.getBoxPayId();
    boxPayList = mmPayData.getBoxPayList();
    boxPayName = mmPayData.getBoxPayName();
    
    appPayId  = mmPayData.getAppPayId();
    
     limitMoney = mmPayData.getLimitMoney();
     limitUserMoney = mmPayData.getLimitUserMoney();
     type = mmPayData.getType();
     moneycount = mmPayData.getMoneycount();
     priority = mmPayData.getPriority();
     powerId = mmPayData.getPowerId();
     content = mmPayData.getContent();
     contentNum = mmPayData.getContentNum();
     powerList = mmPayData.getPowerList();
     powerPayCode = mmPayData.getPowerPayCode();
     contentTwo = mmPayData.getContentTwo();
}
else
{
	//id=0说明为添加新数据项，
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>mm支付数据管理</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script type="text/javascript" src="../_js/Check.js"></script>
<script type="text/javascript" src="../_js/CheckForm.js"></script>
<script type="text/javascript">
function selChange(){
	var opt = document.getElementById("type").options[document.getElementById("type").options.selectedIndex].text;
	document.getElementById("mm2").style.display = (opt==="移动掌中"|| opt==="安安")?'':'none';
	document.getElementById("mm3").style.display = (opt==="移动掌中"|| opt==="安安")?'':'none';
	document.getElementById("mm4").style.display = (opt==="移动掌中"|| opt==="安安"|| opt==="发送短信")?'':'none';
	document.getElementById("mm5").style.display = (opt==="移动掌中"|| opt==="安安")?'':'none';
	document.getElementById("mm6").style.display = (opt==="移动掌中"|| opt==="安安")?'':'none';
	document.getElementById("mm7").style.display = (opt==="移动掌中"|| opt==="安安")?'':'none';
	document.getElementById("mm8").style.display = (opt==="移动掌中"|| opt==="安安")?'':'none';
	document.getElementById("mm9").style.display = (opt==="移动掌中"|| opt==="安安")?'':'none';
	document.getElementById("mm10").style.display = (opt==="移动掌中"|| opt==="安安")?'':'none';
	document.getElementById("mm11").style.display = (opt==="移动掌中"|| opt==="安安")?'':'none';
	document.getElementById("mm12").style.display = (opt==="移动掌中"|| opt==="安安")?'':'none';
	document.getElementById("mm20").style.display = (opt==="移动掌中"|| opt==="安安")?'':'none';
	document.getElementById("mm16").style.display = opt==="移动奇葩页游"?'':'none';
	document.getElementById("mm17").style.display = opt==="移动奇葩页游"?'':'none';
	document.getElementById("mm19").style.display = opt==="移动奇葩页游"?'':'none';
	document.getElementById("un4").style.display = (opt==="联通十分科技" || opt==="电信全网")?'':'none';
	document.getElementById("un5").style.display = (opt==="联通十分科技" || opt==="电信全网")?'':'none';
	document.getElementById("un6").style.display = (opt==="联通十分科技" || opt==="电信全网")?'':'none';
	document.getElementById("un7").style.display = (opt==="联通十分科技" || opt==="电信全网")?'':'none';
	document.getElementById("uni8").style.display = opt==="联通饭盒支付"?'':'none';
	document.getElementById("uni9").style.display = opt==="联通饭盒支付"?'':'none';
	document.getElementById("uni10").style.display = opt==="联通饭盒支付"?'':'none';
	document.getElementById("tcl4").style.display = (opt==="电信十分科技" || opt==="十分科技天翼空间")?'':'none';
	document.getElementById("tcl5").style.display = (opt==="电信十分科技" || opt==="十分科技天翼空间")?'':'none';
	document.getElementById("tcl3").style.display = (opt==="电信十分科技" || opt==="十分科技天翼空间")?'':'none';
	document.getElementById("tcl").style.display = (opt==="电信十分科技" || opt==="十分科技天翼空间")?'':'none';
	document.getElementById("all1").style.display = opt==="动力"?'':'none';
	document.getElementById("all0").style.display = opt==="动力"?'':'none';
	document.getElementById("all4").style.display = opt==="动力"?'':'none';
	document.getElementById("all2").style.display = opt==="发送短信"?'':'none';
//	document.getElementById("all3").style.display = opt==="发送短信"?'':'none';
	document.getElementById("all5").style.display = opt==="发送短信"?'':'none';

}

</script>
<script language="javascript">
window.onload = function() {
	CheckForm.formBind("mainForm");
}
function btnSave(){
    if(!CheckForm.checkForm("mainForm"))return;
    if('<%=id%>'=="0"){
        document.mainForm.action="mmPayDataAction.jsp?op=add";
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="mmPayDataAction.jsp?op=edit&pageno=<%=pageno%>&id=<%=id%>";
        document.mainForm.submit();
    }
}
</script>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_payName">计费名称</td>
    <td width="33%"><input name="payName" alt="payName" value="<%=payName%>"  maxlength="250"/></td>
<td>
<span id="warn_sp_name"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_payId">计费编号</td>
    <td width="33%"><input name="payId" alt="payId" value="<%=payId%>"  maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_appPayId">计费游戏编号</td>
    <td width="33%"><input name="appPayId" alt="appPayId_null" value="<%=appPayId%>"  maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_limitMoney">计费点限额</td>
    <td width="33%"><input name="limitMoney" alt="limitMoney_null" value="<%=limitMoney%>"  maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_limitUserMoney">计费点单用户限额</td>
    <td width="33%"><input name="limitUserMoney" alt="limitUserMoney_null" value="<%=limitUserMoney%>"  maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center"style="display: none">
    <td width="33%" id="title_moneycount">百分比调整</td>
    <td width="33%"><input name="moneycount" alt="moneycount_null" value="<%=moneycount%>"  maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center"style="display: none">
    <td width="33%" id="title_priority">优先级</td>
    <td width="33%"><input name="priority" alt="priority_null" value="<%=priority%>"  maxlength="250"/></td>
<td>
<span id="warn_sp_code">0最高，1其次，</span></td>
</tr>

<tr align="center">
          <td width="125" height="22" align="center">计费点类型:</td>
          <td width="291" height="22"><select name="type" id="type" onChange="selChange()">
            <option selected><%=type %></option>
            <option value="5">移动掌中</option>
            <option value="6">联通十分科技</option>
            <option value="7">电信十分科技</option>
            <option value="8">游戏基地</option>
            <option value="9">电信全网</option>
            <option value="11">移动奇葩页游</option>
            <option value="13">联通饭盒支付</option>
            <option value="12">乐动</option>
            <option value="4">安安</option>
            <option value="17">动力</option>
            <option value="18">发送短信</option>
            <option value="7">十分科技天翼空间</option>
          </select></td>
              <td>
	<span id="warn_sp_url"></span></td>
        </tr>

<tr align="center" id = "mm2"style="display: none">
    <td width="33%" id="title_payKey">计费密匙</td>
    <td width="33%"><input name="payKey" alt="payKey_null" value="<%=payKey%>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
<tr align="center" id= "mm3"style="display: none">
    <td width="33%" id="title_payType">渠道号</td>
    <td width="33%"><input name="payType" alt="payType_null" value="<%=payType%>" maxlength="250"/></td>
<td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "mm4"style="display: none">
    <td width="33%" id="title_payAmount">金额列表</td>
    <td width="33%"><input name="payAmount" alt="payAmount_null" value="<%=payAmount%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "mm20"style="display: none">
    <td width="33%" id="title_payCodeList">计费点列表</td>
    <td width="33%"><input name="payCodeList" alt="payCodeList_null" value="<%=payCodeList%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>

<tr align="center" id= "mm5"style="display: none">
    <td width="33%" id="title_publicKey">应用公匙</td>
    <td width="33%"><input name="publicKey" alt="publicKey_null" value="<%=publicKey%>" maxlength="1024"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>

<tr align="center" id= "mm6"style="display: none">
    <td width="33%" id="title_proId">程序编号</td>
    <td width="33%"><input name="proId" alt="proId_null" value="<%=proId%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>

<tr align="center" id= "mm7"style="display: none">
    <td width="33%" id="title_md5">md5</td>
    <td width="33%"><input name="md5" alt="md5_null" value="<%=md5%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>

<tr align="center" id= "mm8"style="display: none">
    <td width="33%" id="title_md5dex">md5dex</td>
    <td width="33%"><input name="md5dex" alt="md5dex_null" value="<%=md5dex%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>

<tr align="center" id= "mm9"style="display: none">
    <td width="33%" id="title_verno">verno</td>
    <td width="33%"><input name="verno" alt="verno_null" value="<%=verno%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>

<tr align="center" id= "mm10"style="display: none">
    <td width="33%" id="title_packageName">包名</td>
    <td width="33%"><input name="packageName" alt="packageName_null" value="<%=packageName%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>

<tr align="center" id= "mm11"style="display: none">
    <td width="33%" id="title_mainActivity">主界面名</td>
    <td width="33%"><input name="mainActivity" alt="mainActivity_null" value="<%=mainActivity%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>

<tr align="center" id= "mm12" style="display: none">
				<td width="33%" id="title_conpany_name">破解公司</td>
				<td width="33%"><select name="conpany_name" id="conpany_name">

				</select></td>
				<td><span id="warn_conpany_name"></span>
				</td>
			</tr>
			
<tr align="center" id= "mm13"style="display: none">
    <td width="33%" id="title_webPartner">合作方标示</td>
    <td width="33%"><input name="webPartner" alt="webPartner_null" value="<%=webPartner%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "mm14"style="display: none">
    <td width="33%" id="title_webKey1">key1</td>
    <td width="33%"><input name="webKey1" alt="webKey1_null" value="<%=webKey1%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "mm15"style="display: none">
    <td width="33%" id="title_webKey2">key2</td>
    <td width="33%"><input name="webKey2" alt="webKey2_null" value="<%=webKey2%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "mm16"style="display: none">
    <td width="33%" id="title_webPid">产品编号</td>
    <td width="33%"><input name="webPid" alt="webPid_null" value="<%=webPid%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "mm17"style="display: none">
    <td width="33%" id="title_webList">计费列表</td>
    <td width="33%"><input name="webList" alt="webList_null" value="<%=webList%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "mm18"style="display: none">
    <td width="33%" id="title_webUrl">页游支付Url</td>
    <td width="33%"><input name="webUrl" alt="webUrl_null" value="<%=webUrl%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "mm19"style="display: none">
    <td width="33%" id="title_getNum">获取手机号上行</td>
    <td width="33%"><input name="getNum" alt="getNum_null" value="<%=getNum%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>


<tr align="center" id= "un"style="display: none">
    <td width="33%" id="title_unicomId">ID</td>
    <td width="33%"><input name="unicomId" alt="unicomId_null" value="<%=unicomId%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "un1"style="display: none">
    <td width="33%" id="title_unicomUrl">URL</td>
    <td width="33%"><input name="unicomUrl" alt="unicomUrl_null" value="<%=unicomUrl%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "un2"style="display: none">
    <td width="33%" id="title_unBkUrl">回调URL</td>
    <td width="33%"><input name="unBkUrl" alt="unBkUrl_null" value="<%=unBkUrl%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "un3"style="display: none">
    <td width="33%" id="title_unKey">密钥</td>
    <td width="33%"><input name="unKey" alt="unKey_null" value="<%=unKey%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "un4"style="display: none">
    <td width="33%" id="title_unPayName">计费名称</td>
    <td width="33%"><input name="unPayName" alt="unPayName_null" value="<%=unPayName%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "un5"style="display: none">
    <td width="33%" id="title_unPayList">计费列表</td>
    <td width="33%"><input name="unPayList" alt="unPayList_null" value="<%=unPayList%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "un6"style="display: none">
    <td width="33%" id="title_unGoodId">GoodId</td>
    <td width="33%"><input name="unGoodId" alt="unGoodId_null" value="<%=unGoodId%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "un7"style="display: none">
    <td width="33%" id="title_unName">游戏名称</td>
    <td width="33%"><input name="unName" alt="unName_null" value="<%=unName%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>

<tr align="center" id= "tcl"style="display: none">
    <td width="33%" id="title_tclId">（电信）电信ID</td>
    <td width="33%"><input name="tclId" alt="tclId_null" value="<%=tclId%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span>天翼空间选择第二套：7894f87e51f249f7ae957bdabd5d4a24</td>
</tr>
<tr align="center" id= "tcl1"style="display: none">
    <td width="33%" id="title_tclUrl">（电信）电信Url</td>
    <td width="33%"><input name="tclUrl" alt="tclUrl_null" value="<%=tclUrl%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "tcl2"style="display: none">
    <td width="33%" id="title_tclBkUrl">（电信）回调Url</td>
    <td width="33%"><input name="tclBkUrl" alt="tclBkUrl_null" value="<%=tclBkUrl%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "tcl3"style="display: none">
    <td width="33%" id="title_tclKey">（电信）密钥</td>
    <td width="33%"><input name="tclKey" alt="tclKey_null" value="<%=tclKey%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span>天翼空间选择第二套：15435e7714764838ac2d035da7e9f656</td>
</tr>
<tr align="center" id= "tcl4"style="display: none">
    <td width="33%" id="title_tclPayList">（电信）计费列表</td>
    <td width="33%"><input name="tclPayList" alt="tclPayList_null" value="<%=tclPayList%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "tcl5"style="display: none">
    <td width="33%" id="title_tclName">（电信）游戏名称</td>
    <td width="33%"><input name="tclName" alt="tclName_null" value="<%=tclName%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "uni8"style="display: none">
    <td width="33%" id="title_boxPayId">（联通饭盒）计费点列表</td>
    <td width="33%"><input name="boxPayId" alt="boxPayId_null" value="<%=boxPayId%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "uni9"style="display: none">
    <td width="33%" id="title_boxPayList">（联通饭盒）金额列表</td>
    <td width="33%"><input name="boxPayList" alt="boxPayList_null" value="<%=boxPayList%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "uni10"style="display: none">
    <td width="33%" id="title_boxPayName">（联通饭盒）计费点名称</td>
    <td width="33%"><input name="boxPayName" alt="boxPayName_null" value="<%=boxPayName%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "all1"style="display: none">
    <td width="33%" id="title_powerId">动力appId</td>
    <td width="33%"><input name="powerId" alt="powerId_null" value="<%=powerId%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "all4"style="display: none">
    <td width="33%" id="title_powerPayCode">动力goodId</td>
    <td width="33%"><input name="powerPayCode" alt="powerPayCode_null" value="<%=powerPayCode%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "all0"style="display: none">
    <td width="33%" id="title_powerList">计费列表</td>
    <td width="33%"><input name="powerList" alt="powerList_null" value="<%=powerList%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "all2"style="display: none">
    <td width="33%" id="title_content">短信内容#号前</td>
    <td width="33%"><input name="content" alt="content_null" value="<%=content%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "all5"style="display: none">
    <td width="33%" id="title_contentTwo">短信内容#号后</td>
    <td width="33%"><input name="contentTwo" alt="contentTwo_null" value="<%=contentTwo%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>
<tr align="center" id= "all3"style="display: none">
    <td width="33%" id="title_contentNum">服务号码</td>
    <td width="33%"><input name="contentNum" alt="contentNum_null" value="<%=contentNum%>" maxlength="250"/></td>
    <td>
<span id="warn_sp_url"></span></td>
</tr>

<tr align=center>
    <td colspan=3>
        <a href="#" onclick="btnSave()"><img src="../_js/ico/btn_save.gif" border="0" alt="保存" align="absmiddle"/></a>
        &nbsp;&nbsp;&nbsp;
        <a href="#" onclick="javascript:history.back()"><img src="../_js/ico/btn_back.gif" border="0" alt="返回" align="absmiddle"/></a>
    </td>
    
</tr>
</table>
</form>
</body>
</html>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript">
<%out.println(azul.JspUtil.initSelect("conpany_name",conpany_nameMap,conpany_name));%>
</script>
