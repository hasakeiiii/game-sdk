<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*"%>
<%@ page import="model.*"%>
<%@ page import="common.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css" />
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css"
	media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script type="text/javascript" src="../_js/Check.js"></script>
<script type="text/javascript" src="../_js/CheckForm.js"></script>
</head>
<body>

	<table width="100%" border="0">
		<tr align="center">
			<td width="33%">渠道名称</td>
			<td width="33%">${channelName}</td>
			<td></td>
		</tr>
		
		<tr align="center">
			<td width="33%">公司</td>
			<td width="33%">${company }</td>
			<td></td>
		</tr>
		
		<tr align="center">
			<td width="33%" id="title_app_no">游戏名称</td>
			<td width="33%">${appName}</td>
			<td></td>
		</tr>
		
		<tr align="center">
			<td width="33%">MM渠道号</td>
			<td width="33%">${mmChannel }</td>
			<td><span id="warn_sp_code"></span></td>
		</tr>
		
		<tr align="center">
			<td width="33%">拇指渠道号</td>
			<td width="33%">${co.packetId }</td>
			<td><span id="warn_sp_code"></span></td>
		</tr>
<%-- <tr align="center">
    <td width="33%" id="title_pay_id">计费方式</td>
    <td width="30%">
    <select id="telcom_pay_type" name="telcom_pay_type" class="" >
    <c:forEach items="${paytypemap}" var="entry">
     <option value='${entry.key }'  <c:if test="${ entry.key == cooFee.telcomPayType }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
<%--  <tr align="center">
    <td width="33%" id="title_pay_id">电信主计费点</td>
    <td width="30%">
    <select id="telcom_pay_id" name="telcom_pay_id" class="" >
    <c:forEach items="${map}" var="entry">
     <option value='${entry.key }' <c:if test="${ entry.key == cooFee.telcomPayId }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
<tr align="center">
			<td width="33%">电信主计费点</td>
			<td width="33%"><input type="text" id="telcom_pay_id" value = "${cooFee.telcomPayId}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
<%-- <tr align="center">
    <td width="33%" id="title_pay_id">计费方式1</td>
    <td width="30%">
    <select id="telcom_pay1_type" name="telcom_pay1_type" class="" >
    <c:forEach items="${paytypemap}" var="entry">
     <option value='${entry.key }' <c:if test="${ entry.key == cooFee.telcomPay1Type }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
	<%-- 	 <tr align="center">
    <td width="33%" id="title_pay_id">电信副计费点1</td>
    <td width="30%">
    <select id="telcom_pay1_id" name="telcom_pay1_id" class="" >
    <c:forEach items="${map}" var="entry">
     <option value='${entry.key }'  <c:if test="${ entry.key == cooFee.telcomPay1Id }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
<tr align="center">
			<td width="33%">电信副计费点1</td>
			<td width="33%"><input type="text" id="telcom_pay1_id" value = "${cooFee.telcomPay1Id}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
		<%-- <tr align="center">
			<td width="33%">省份列表</td>
			<td width="33%"><input type="text" id="telcom_pay1_param" value = "${cooFee.telcomPay1Param}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
		<tr align="center">
    <td width="33%" id="title_pay_id">计费方式2</td>
    <td width="30%">
    <select id="telcom_pay2_type" name="telcom_pay2_type" class="" >
    <c:forEach items="${paytypemap}" var="entry">
     <option value='${entry.key }' <c:if test="${ entry.key == cooFee.telcomPay2Type }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
	<%-- 	 <tr align="center">
    <td width="33%" id="title_pay_id">电信副计费点2</td>
    <td width="30%">
    <select id="telcom_pay2_id" name="telcom_pay2_id" class="" >
    <c:forEach items="${map}" var="entry">
     <option value='${entry.key }' <c:if test="${ entry.key == cooFee.telcomPay2Id }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
<tr align="center">
			<td width="33%">电信副计费点2</td>
			<td width="33%"><input type="text" id="telcom_pay2_id" value = "${cooFee.telcomPay2Id}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
		<%-- <tr align="center">
			<td width="33%">省份列表</td>
			<td width="33%"><input type="text" id="telcom_pay2_param" value = "${cooFee.telcomPay2Param}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
		<tr align="center">
    <td width="33%" id="title_pay_id">计费方式3</td>
    <td width="30%">
    <select id="telcom_pay3_type" name="telcom_pay3_type" class="" >
    <c:forEach items="${paytypemap}" var="entry">
     <option value='${entry.key }' <c:if test="${ entry.key == cooFee.telcomPay3Type }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
<%-- 		 <tr align="center">
    <td width="33%" id="title_pay_id">电信副计费点3</td>
    <td width="30%">
    <select id="telcom_pay3_id" name="telcom_pay3_id" class="" >
    <c:forEach items="${map}" var="entry">
     <option value='${entry.key }' <c:if test="${ entry.key == cooFee.telcomPay3Id }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
<tr align="center">
			<td width="33%">电信副计费点3</td>
			<td width="33%"><input type="text" id="telcom_pay3_id" value = "${cooFee.telcomPay3Id}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
		<%-- <tr align="center">
			<td width="33%">省份列表</td>
			<td width="33%"><input type="text" id="telcom_pay3_param" value = "${cooFee.telcomPay3Param}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr> --%>
			<tr align="center">
			<td width="33%"><a href="#" onclick="btnSave()"><img src="../_js/ico/btn_save.gif" border="0" alt="保存" align="absmiddle"/></a></td>
			<td width="33%"><input type="hidden" id = "coFeeId" value="${cooFee.id }"/></td>
			<td></td>
		</tr>
	</table>

<script>
	function btnSave(){
		var telcom_pay_type,moblie_pay_id,telcom_pay1_type,moblie_pay1_id,moblie_pay1_param,telcom_pay1_type;
		var telcom_pay_type = $("#telcom_pay_type").val();
		var telcom_pay1_type = $("#telcom_pay1_type").val();
		var telcom_pay2_type = $("#telcom_pay2_type").val();
		var telcom_pay3_type = $("#telcom_pay3_type").val();
		var telcom_pay_id = $("#telcom_pay_id").val();
		var telcom_pay1_id = $("#telcom_pay1_id").val();
		var telcom_pay2_id = $("#telcom_pay2_id").val();
		var telcom_pay3_id = $("#telcom_pay3_id").val();
		var telcom_pay1_param = $("#telcom_pay1_param").val();
		var telcom_pay2_param = $("#telcom_pay2_param").val();
		var telcom_pay3_param = $("#telcom_pay3_param").val();
		
		
		var channelNo = '${co.channelNo}';
		var appNo = '${co.appNo}';
		var mmChannel = '${mmChannel}';
		var packetId = '${co.packetId}'
		var pay = '${co.channelNo}';
		var op = "";
		
		if($("#coFeeId").val() != ''){
			op = "editRemind";
		}else{
			op = "saveRemind";
		}
		
		$.post(
			"../servlet/CooperationTelAction",
			{
				cmd : "operateRemind",
				op : op,
				channelNo : channelNo,
				appNo : appNo,
				mmChannel : mmChannel,
				packetId : packetId,
				telcom_pay_type : telcom_pay_type,
				telcom_pay1_type : telcom_pay1_type,
				telcom_pay2_type : telcom_pay2_type,
				telcom_pay3_type : telcom_pay3_type,
				telcom_pay_id : telcom_pay_id,
				telcom_pay1_id : telcom_pay1_id,
				telcom_pay2_id : telcom_pay2_id,
				telcom_pay3_id : telcom_pay3_id,
				telcom_pay1_param :telcom_pay1_param,
				telcom_pay2_param :telcom_pay2_param,
				telcom_pay3_param :telcom_pay3_param
				
			},function(data){
				if("addOk" == data.info){
					alert("添加成功");
				}else if(data.info == "editOk"){
					alert("修改成功");
				}
				window.location.href = "../param/cfgCooperationList.jsp";
			},"json"
		);
		<%
	//	out.println(azul.JspUtil.initSelect("pay_id",pay_idMap,pay_id)); 
		%>
	}
</script>
</body>

</html>

