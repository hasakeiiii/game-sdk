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
		
		<tr align="center">
			<td width="33%">激活    /    请求指令</td>
			<td width="33%"><input type="text" id="reqNum" value="${cooRemind.reqNum}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
		
		<tr align="center">
			<td width="33%">付费金额</td>
			<td width="33%"><input type="text" id="mmPay" value="${cooRemind.mmPay }"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
		<tr align="center">
			<td width="33%">转化率</td>
			<td width="33%">
				<select id="operator1">
					<option value="">请选择</option>
					<option <c:if test="${cooRemind.operatorFirst == 1 }">selected</c:if> value="1">></option>
					<option <c:if test="${cooRemind.operatorFirst == 0 }">selected</c:if> value="0"><</option>
				</select>
				<input type="text" id="proportion" value = "${cooRemind.proportion }"/>%
			</td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
		<tr align="center">
			<td width="33%">付费转化率</td>
			<td width="33%">
				<select id="operator2">
					<option value="">请选择</option>
					<option <c:if test="${cooRemind.operatorSecond == 1 }">selected</c:if> value="1">></option>
					<option <c:if test="${cooRemind.operatorSecond == 0 }">selected</c:if> value="0"><</option>
				</select>
				<input type="text" id="payTransProportion" value="${cooRemind.payProportion }"/>%
			</td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
			<tr align="center">
			<td width="33%">（移动）单用户限额列表（元）</td>
			<td width="33%"><input type="text" id="mmDayPayProvince" value = "${cooRemind.mmDayPayProvince}"/></td>
			<td><span id="warn_sell_id"></span>10_15_20</td>
		</tr>
			<tr align="center">
			<td width="33%">单用户限额省份列表</td>
			<td width="33%"><input type="text" id="province"value = "${cooRemind.province}"/></td>
			<td><span id="warn_sell_id"></span>广东_海南_湖北</td>
		</tr>	<tr align="center">
			<td width="33%">渠道限额列表（元）</td>
			<td width="33%"><input type="text" id="mmDayChannelProvince"value = "${cooRemind.mmDayChannelProvince}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>	<tr align="center">
			<td width="33%">渠道限额省份列表</td>
			<td width="33%"><input type="text" id="channelProvince"value = "${cooRemind.channelProvince}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
		
			<tr align="center">
			<td width="33%">（联通）单用户限额列表（元）</td>
			<td width="33%"><input type="text" id="unDayPayProvince" value = "${cooRemind.unDayPayProvince}"/></td>
			<td><span id="warn_sell_id"></span>10_15_20</td>
		</tr>
			<tr align="center">
			<td width="33%">单用户限额省份列表</td>
			<td width="33%"><input type="text" id="unProvince"value = "${cooRemind.unProvince}"/></td>
			<td><span id="warn_sell_id"></span>广东_海南_湖北</td>
		</tr>	<tr align="center">
			<td width="33%">渠道限额列表（元）</td>
			<td width="33%"><input type="text" id="unDayChannelProvince"value = "${cooRemind.unDayChannelProvince}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>	<tr align="center">
			<td width="33%">渠道限额省份列表</td>
			<td width="33%"><input type="text" id="unChannelProvince"value = "${cooRemind.unChannelProvince}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
		
			<tr align="center">
			<td width="33%">（电信）单用户限额列表（元）</td>
			<td width="33%"><input type="text" id="telDayPayProvince" value = "${cooRemind.telDayPayProvince}"/></td>
			<td><span id="warn_sell_id"></span>10_15_20</td>
		</tr>
			<tr align="center">
			<td width="33%">单用户限额省份列表</td>
			<td width="33%"><input type="text" id="telProvince"value = "${cooRemind.telProvince}"/></td>
			<td><span id="warn_sell_id"></span>广东_海南_湖北</td>
		</tr>	<tr align="center">
			<td width="33%">渠道限额列表（元）</td>
			<td width="33%"><input type="text" id="telDayChannelProvince"value = "${cooRemind.telDayChannelProvince}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>	<tr align="center">
			<td width="33%">渠道限额省份列表</td>
			<td width="33%"><input type="text" id="telChannelProvince"value = "${cooRemind.telChannelProvince}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
					<tr align="center">
			<td width="33%">（移动）屏蔽地区</td>
			<td width="33%"><input type="text" id="mmAddr" value = "${cooRemind.mmAddr}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
					<tr align="center">
			<td width="33%">屏蔽时间</td>
			<td width="33%"><input type="text" id="mmAddrTime" value = "${cooRemind.mmAddrTime}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
					<tr align="center">
			<td width="33%">（联通）屏蔽地区</td>
			<td width="33%"><input type="text" id="unAddr" value = "${cooRemind.unAddr}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
					<tr align="center">
			<td width="33%">屏蔽时间</td>
			<td width="33%"><input type="text" id="unAddrTime" value = "${cooRemind.unAddrTime}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
					<tr align="center">
			<td width="33%">（电信）屏蔽地区</td>
			<td width="33%"><input type="text" id="telAddr" value = "${cooRemind.telAddr}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
					<tr align="center">
			<td width="33%">屏蔽时间</td>
			<td width="33%"><input type="text" id="telAddrTime" value = "${cooRemind.telAddrTime}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
		<tr align="center">
			<td width="33%"><a href="#" onclick="btnSave()"><img src="../_js/ico/btn_save.gif" border="0" alt="保存" align="absmiddle"/></a></td>
			<td width="33%"><input type="hidden" id = "coRemindId" value="${cooRemind.id }"/></td>
			<td></td>
		</tr>
		
	</table>

<script>
	function btnSave(){
		var reqNum,mmPay,operator1,proportion,operator2,payProportion;
		
		reqNum = $("#reqNum").val();
		mmPay = $("#mmPay").val();
		operator1 = $("#operator1").val();
		proportion = $("#proportion").val();
		operator2 = $("#operator2").val();
		payProportion = $("#payTransProportion").val();
		
		mmDayPayProvince = $("#mmDayPayProvince").val();
		province = $("#province").val();
		mmDayChannelProvince = $("#mmDayChannelProvince").val();
		channelProvince = $("#channelProvince").val();
		
		unDayPayProvince = $("#unDayPayProvince").val();
		unProvince = $("#unProvince").val();
		unDayChannelProvince = $("#unDayChannelProvince").val();
		unChannelProvince = $("#unChannelProvince").val();
		
		telDayPayProvince = $("#telDayPayProvince").val();
		telProvince = $("#telProvince").val();
		telDayChannelProvince = $("#telDayChannelProvince").val();
		telChannelProvince = $("#telChannelProvince").val();
		
		mmAddr = $("#mmAddr").val();
		mmAddrTime = $("#mmAddrTime").val();
		unAddr = $("#unAddr").val();
		unAddrTime = $("#unAddrTime").val();
		telAddr = $("#telAddr").val();
		telAddrTime = $("#telAddrTime").val();
		
		var channelNo = '${co.channelNo}';
		var appNo = '${co.appNo}';
		var mmChannel = '${mmChannel}';
		var packetId = '${co.packetId}'
		
		var op = "";
		
		if($("#coRemindId").val() != ''){
			op = "editRemind";
		}else{
			op = "saveRemind";
		}
		
		$.post(
			"../servlet/CooperationRemindAction",
			{
				cmd : "operateRemind",
				op : op,
				reqNum : reqNum,
				mmPay : mmPay,
				operator1 : operator1,
				proportion : proportion,
				operator2 : operator2,
				payProportion : payProportion,
				channelNo : channelNo,
				appNo : appNo,
				mmChannel : mmChannel,
				packetId : packetId,
				mmDayPayProvince : mmDayPayProvince,
				province : province,
				mmDayChannelProvince : mmDayChannelProvince,
				channelProvince : channelProvince,
				
				unDayPayProvince : unDayPayProvince,
				unProvince : unProvince,
				unDayChannelProvince : unDayChannelProvince,
				unChannelProvince : unChannelProvince,
				
				telDayPayProvince : telDayPayProvince,
				telProvince : telProvince,
				telDayChannelProvince : telDayChannelProvince,
				telChannelProvince : telChannelProvince,
				
				mmAddr : mmAddr,
				mmAddrTime : mmAddrTime,
				unAddr : unAddr,
				unAddrTime : unAddrTime,
				telAddr : telAddr,
				telAddrTime : telAddrTime
			},function(data){
				if("addOk" == data.info){
					alert("添加成功");
				}else if(data.info == "editOk"){
					alert("修改成功");
				}
				window.location.href = "../param/cfgCooperationList.jsp";
			},"json"
		);
	}
</script>
</body>

</html>

