<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ page import="java.util.*" %>
<%
String pageTitle="编辑信息";
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
int cfg_stat_id=JspUtil.getInt(request.getParameter("cfg_stat_id"),0);
String sid="";//
String cid="";//
String province="";//
String msg="";//
int max_num=0;//
if(cfg_stat_id!=0){
    pageTitle="编辑信息ID:"+cfg_stat_id;
    CfgStatDao cfgStatDao=new CfgStatDao();
    CfgStat cfgStat=(CfgStat)cfgStatDao.getById(cfg_stat_id);
    sid=cfgStat.getSid();
    cid=cfgStat.getCid();
    province=cfgStat.getProvince();
    if(!"".equals(province)){
    	province=azul.JspUtil.decode(province);
    }
    msg=cfgStat.getMsg();
    max_num=cfgStat.getMaxNum();
}

CfgSpDao cfgSpDao=new CfgSpDao();
Map sidMap=cfgSpDao.getSelectMap("select sp_code,concat(sp_name,'(',sp_code,')') from cfg_sp");

CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
java.util.Map cidMap=cfgCompanyDao.getSelectMap("select cid,concat(name,'(',cid,')') from cfg_company order by name");

CfgSpCodeDao cfgSpCodeDao=new CfgSpCodeDao();
java.util.Map msgMap=cfgSpCodeDao.getSelectMap("select concat(sid,'_',cid,'_',sp_code),sp_code from cfg_sp_code");
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
    <td width="33%" id="title_sid">SID</td>
    <td width="33%">
<select name="sid" id="sid">
  <option value="">请选择</option>
</select></td>
<td>
<span id="warn_sid"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_cid">CID</td>
    <td width="33%">
	<select name="cid" id="cid" onchange="chargeMsg()">
  <option value="">请选择</option>
</select>	</td>
<td>
<span id="warn_cid"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_province">省份</td>
    <td width="33%">
	<table width="750" height="155" border="1" id="cmcTable">
	<tr align="left">
      <%
      for(int i=1;i<common.Constant.AREA.length;i++){
    	  if(i%5==1 && i!=1){
    		  out.println("</tr>");
    		  out.println("<tr align=\"left\">");
    	  }
      %>
        <td><input type="radio" name="province" value="<%=azul.JspUtil.decode(common.Constant.AREA[i])%>"/><%=common.Constant.AREA[i]%></td>
      <%
      }
      %>
      </tr>
    </table>
	</td>
<td>
<span id="warn_province"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_msg">上行命令</td>
    <td width="33%" id="msgDiv" align="left"></td>
<td>
<span id="warn_msg"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_max_num">上限数量</td>
    <td width="33%" align="left"><input name="max_num" alt="" value="<%=max_num%>"/></td>
<td>
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
<script type="text/javascript" src="../_js/Check.js"></script>
<script type="text/javascript" src="../_js/CheckForm.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script language="javascript">
var arrA=new Array();
var arrB=new Array();
<%
Iterator<java.util.Map.Entry<String, String>> itmsg = msgMap.entrySet().iterator();
int i=0;
while (itmsg.hasNext()) {
	java.util.Map.Entry<String,String> entry = itmsg.next();  
	String key=entry.getKey();
	String value=entry.getValue();			
%>
    arrA[<%=i%>]="<%=key%>";
	arrB[<%=i%>]="<%=value%>";
<%
    i++;
}
%>
function chargeMsg(){
    var sid=getElem("sid");
	if(sid==""){
	    alert("请选择要配置的SID");
		return;
	}
	var cid=getElem("cid");
	if(cid==""){
	    alert("请选择要配置的CID");
		return;
	}
	var info="";
	for(var i=0;i<arrA.length;i++){
	    if(arrA[i].indexOf(sid+"_"+cid+"_")>-1){
		   info+="<input type='radio' name='msg' value='"+arrB[i]+"'/>"+arrB[i];
		}
	}
	//alert(info);
	if(info!=""){
	    document.getElementById("msgDiv").innerHTML=info;
	}
	else{
		document.getElementById("msgDiv").innerHTML="";
	}
}
window.onload = function() {
	CheckForm.formBind("mainForm");
	CheckForm.alertType="";
}
function btnSave(){
    if(!CheckForm.checkForm("mainForm"))return;
    var msg=getElem("msg");
	if(msg==""){
	    alert("请选择要配置的上行命令");
		return;
	}
	var province=getElem("province");
	if(province==""){
	    alert("请选择要配置的省份");
		return;
	}
	if(document.mainForm.max_num.value==""){
	    alert("请输入上限数量");
		return;
	}
	if(Number(document.mainForm.max_num.value)<1){
	    alert("上限数量必须大于0");
		return;
	}
    var paramKey="&msg="+msg;
    if("<%=cfg_stat_id%>"=="0"){
        document.mainForm.action="cfgStatAction.jsp?op=add"+paramKey;
        document.mainForm.submit();
    }
    else{
        document.mainForm.action="cfgStatAction.jsp?op=edit&cfg_stat_id=<%=cfg_stat_id%>"+paramKey;
        document.mainForm.submit();
    }
}
<%
out.println(JspUtil.initSelect("sid",sidMap,sid));
out.println(JspUtil.initSelect("cid",cidMap,cid));
%>
initRadio("province","<%=province%>");
initRadio("msg","<%=msg%>");
chargeMsg();
</script>