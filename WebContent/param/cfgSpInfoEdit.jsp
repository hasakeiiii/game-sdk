<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ include file="../check.jsp"%>
<%
String pageTitle="编辑信息";
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
int cfg_sp_info_id=JspUtil.getInt(request.getParameter("cfg_sp_info_id"),0);
String sid="";
String trone="";//地址
String command="";//命令
int operator=0;//运营商
int price=1;//价格
String province="";//省份
String city="";//屏蔽地区
String info="";//下发语
String replay="";//回复
String max="";//上限
if(cfg_sp_info_id!=0){
    pageTitle="编辑信息ID:"+cfg_sp_info_id;
    CfgSpInfoDao cfgSpInfoDao=new CfgSpInfoDao();
    CfgSpInfo cfgSpInfo=(CfgSpInfo)cfgSpInfoDao.getById(cfg_sp_info_id);
    sid=cfgSpInfo.getSid();
    trone=cfgSpInfo.getTrone();
    command=cfgSpInfo.getCommand();
    operator=cfgSpInfo.getOperator();
    price=cfgSpInfo.getPrice();
    province=cfgSpInfo.getProvince();
    city=cfgSpInfo.getCity();
    info=cfgSpInfo.getInfo();
    replay=cfgSpInfo.getReplay();
    max=cfgSpInfo.getMax();
}
CfgSpDao cfgSpDao=new CfgSpDao();
java.util.Map sidMap=cfgSpDao.getSelectMap("select sp_code,concat(sp_name,'(',sp_code,')') from cfg_sp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_sid">通道</td>
    <td width="33%"><select name="sid" id="sid">
  <option value="">请选择</option>
</select></td>
<td>
<span id="warn_sid"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_trone">地址</td>
    <td width="33%"><input name="trone" alt="trone" value="<%=trone%>"/></td>
<td>
<span id="warn_trone"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_command">命令<span class="tdRed">*代表模糊指令</span></td>
    <td width="33%"><input name="command" alt="command" value="<%=command%>"/></td>
<td>
<span id="warn_command"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_operator">运营商</td>
    <td width="33%"><input name="operator" type="radio" value="1" checked/>
    移动
      <input name="operator" type="radio" value="2"/>
      联通</td>
    <td>
<span id="warn_operator"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_price">价格</td>
    <td width="33%"><input name="price" alt="price_int" value="<%=price%>"/></td>
<td>
<span id="warn_price"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_replay">回复情况</td>
    <td width="33%"><input name="replay" alt="replay" value="<%=replay%>"/></td>
<td>
<span id="warn_replay"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_max">上限</td>
    <td width="33%"><input name="max" alt="max" value="<%=max%>"/></td>
<td>
<span id="warn_max"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_info">下发语</td>
    <td width="33%"><textarea name="info" cols="40" rows="10"><%=info%></textarea></td>
<td>
<span id="warn_info"></span></td>
</tr>
</table>

<table width="100%" id="TableColorRowArea">
  <%
for(int i=0;i<common.Constant.AREA.length;i++){
%>
  <tr>
    <td width="8%"><input name="province" type="checkbox" id="province<%=common.Constant.AREA_CODE[i]%>" value="<%=common.Constant.AREA_CODE[i]%>" onclick="chargeProvince('<%=common.Constant.AREA_CODE[i]%>')"/>
        <%=common.Constant.AREA[i]%></td>
    <td align="left" id="city_div_<%=common.Constant.AREA_CODE[i]%>"></td>
    </tr>
  <%
}
%>
</table>
        <div align="center"><a href="#" onclick="btnSave()"><img src="../_js/ico/btn_save.gif" border="0" alt="保存" align="absmiddle"/></a>&nbsp;&nbsp;&nbsp;
            <a href="#" onclick="javascript:history.back()"><img src="../_js/ico/btn_back.gif" border="0" alt="返回" align="absmiddle"/></a>    
        </div>
</form>
</body>
</html>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/Check.js"></script>
<script type="text/javascript" src="../_js/CheckForm.js"></script>
<script language="javascript">
window.onload = function() {
	CheckForm.formBind("mainForm");
	CheckForm.alertType="";
}
function btnSave(){
    if(!CheckForm.checkForm("mainForm"))return;
	var province=getCheck("province");
    if(province==""){
         alert("请选择有效地区");
         return;
    }
	var city=getCheck("city");
    var paramKey="&province="+province+"&city="+city;
    if("<%=cfg_sp_info_id%>"=="0"){
        document.mainForm.action="cfgSpInfoAction.jsp?op=add"+paramKey;
        document.mainForm.submit();
    }
    else{
        document.mainForm.action="cfgSpInfoAction.jsp?op=edit&pageno=<%=pageno%>&cfg_sp_info_id=<%=cfg_sp_info_id%>"+paramKey;
        document.mainForm.submit();
    }
}
//选中一个地区时出现地市屏蔽信息
function chargeProvince(index){
	var key=document.getElementById("province"+index).value;
    if(document.getElementById("province"+index).checked){
    	var text="";
    	for(var i=0;i<province_code.length;i++){
    	     if(province_code[i]==key){
    	          for(var j=0;j<city_code[i].length;j++){
    	              text+="<input name=\"city\" type=\"checkbox\" id=\"city"+j+"\" value=\""+city_code[i][j]+"\"/>"+city[i][j];
    	          }
    	          break;
    	     }
    	}
    	document.getElementById("city_div_"+key).innerHTML=text;
    }
    else{
    	document.getElementById("city_div_"+key).innerHTML="";
    }
}
var province_code=new Array();
var city=new Array();
var city_code=new Array();
<%
for (int i = 0; i < common.Constant.CITY.length; i++) {
%>
province_code[<%=i%>]="<%=common.Constant.AREA_CODE[i]%>";
city[<%=i%>]=new Array();
city_code[<%=i%>]=new Array();
<%
for (int j = 0; j < common.Constant.CITY[i].length; j++) {
%>
city_code[<%=i%>][<%=j%>]="<%=common.Constant.CITY_CODE[i][j]%>";
city[<%=i%>][<%=j%>]="<%=common.Constant.CITY[i][j]%>";
<%
}
}
%>
//chargeProvince();
if("<%=province%>"!=""){
    initElem("province","<%=province%>");
    var tempArr="<%=province%>".split(",");
    for(var i=0;i<tempArr.length;i++){
        chargeProvince(tempArr[i]);
    }
    initElem("city","<%=city%>");
}
initElem("operator","<%=operator%>");

<%
out.println(JspUtil.initSelect("sid",sidMap,sid));
%>
</script>