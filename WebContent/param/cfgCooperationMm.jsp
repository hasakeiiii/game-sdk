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
    <select id="mobile_pay_type" name="mobile_pay_type" class="" >
    <c:forEach items="${paytypemap}" var="entry">
    
     <option value='${entry.key }'  <c:if test="${ entry.key == cooFee.mobilePayType }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
 <%-- <tr align="center">
    <td width="33%" id="title_pay_id">移动主计费点</td>
    <td width="30%">
    <select id="mobile_pay_id" name="mobile_pay_id" class="" >
    <c:forEach items="${map}" var="entry">
     <option value='${entry.key }' <c:if test="${ entry.key == cooFee.mobliePayId }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
<tr align="center">
			<td width="33%">移动主计费点</td>
			<td width="33%"><input type="text" id="mobile_pay_id" value = "${cooFee.mobliePayId}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
<%-- <tr align="center">
    <td width="33%" id="title_pay_id">计费方式1</td>
    <td width="30%">
    <select id="mobile_pay1_type" name="mobile_pay1_type" class="" >
    <c:forEach items="${paytypemap}" var="entry">
        <c:choose>
                       <c:when test="${entry.key eq  cooFee.mobilePay1Type}">
                            <option value="${entry.key}"  selected>${entry.value }</option>
                       </c:when>
                       <c:otherwise>
                            <option value="${entry.key}">${entry.value }</option>
                       </c:otherwise>
                    </c:choose> 
      <option value='${entry.key }'  <c:if test="${ entry.key == cooFee.mobilePay1Type }">selected</c:if> >${entry.value }</option>
     <option value="${entry.key}"   <c:if  test="${entry.key  eq  '0' }"> selected="selected"</c:if>  >${entry.value }</option>
     <option value='${entry.key }' <c:if test="${ entry.key == cooFee.mobilePay1Type }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
<%-- 		 <tr align="center">
    <td width="33%" id="title_pay_id">移动副计费点1</td>
    <td width="30%">
    <select id="mobile_pay1_id" name="mobile_pay1_id" class="" >
    <c:forEach items="${map}" var="entry">
     <option value='${entry.key }'  <c:if test="${ entry.key == cooFee.mobliePay1Id }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
<tr align="center">
			<td width="33%">移动副计费点1</td>
			<td width="33%"><input type="text" id="mobile_pay1_id" value = "${cooFee.mobliePay1Id}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
<%-- 		<tr align="center">
			<td width="33%">省份列表</td>
			<td width="33%"><input type="text" id="mobile_pay1_param" value = "${cooFee.mobliePay1Param}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr> --%>
	<%-- 	 <tr align="center">
    <td width="33%" id="title_pay_id">移动副计费点2</td>
    <td width="30%">
    <select id="mobile_pay2_id" name="mobile_pay2_id" class="" >
    <c:forEach items="${map}" var="entry">
     <option value='${entry.key }' <c:if test="${ entry.key == cooFee.mobliePay2Id }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
<%-- 		<tr align="center">
    <td width="33%" id="title_pay_id">计费方式2</td>
    <td width="30%">
    <select id="mobile_pay2_type" name="mobile_pay2_type" class="" >
    <c:forEach items="${paytypemap}" var="entry">
     <option value='${entry.key }' <c:if test="${ entry.key == cooFee.mobilePay2Type }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
<tr align="center">
			<td width="33%">移动副计费点2</td>
			<td width="33%"><input type="text" id="mobile_pay2_id" value = "${cooFee.mobliePay2Id}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
<%-- 		<tr align="center">
			<td width="33%">省份列表</td>
			<td width="33%"><input type="text" id="mobile_pay2_param" value = "${cooFee.mobliePay2Param}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr> --%>
		<%-- <tr align="center">
    <td width="33%" id="title_pay_id">计费方式3</td>
    <td width="30%">
    <select id="mobile_pay3_type" name="mobile_pay3_type" class="" >
    <c:forEach items="${paytypemap}" var="entry">
     <option value='${entry.key }' <c:if test="${ entry.key == cooFee.mobilePay3Type }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
<%-- 		 <tr align="center">
    <td width="33%" id="title_pay_id">移动副计费点3</td>
    <td width="30%">
    <select id="mobile_pay3_id" name="mobile_pay3_id" class="" >
    <c:forEach items="${map}" var="entry">
     <option value='${entry.key }' <c:if test="${ entry.key == cooFee.mobliePay3Id }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
<tr align="center">
			<td width="33%">移动副计费点3</td>
			<td width="33%"><input type="text" id="mobile_pay3_id" value = "${cooFee.mobliePay3Id}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
		<%-- <tr align="center">
			<td width="33%">省份列表</td>
			<td width="33%"><input type="text" id="mobile_pay3_param" value = "${cooFee.mobliePay3Param}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
		
		<tr align="center">
    <td width="33%" id="title_pay_id">计费方式4</td>
    <td width="30%">
    <select id="mobile_pay4_type" name="mobile_pay4_type" class="" >
    <c:forEach items="${paytypemap}" var="entry">
     <option value='${entry.key }' <c:if test="${ entry.key == cooFee.mobilePay4Type }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
<tr align="center">
			<td width="33%">移动副计费点4</td>
			<td width="33%"><input type="text" id="mobile_pay4_id" value = "${cooFee.mobilePay4Id}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
	<%-- 	<tr align="center">
    <td width="33%" id="title_pay_id">计费方式5</td>
    <td width="30%">
    <select id="mobile_pay5_type" name="mobile_pay5_type" class="" >
    <c:forEach items="${paytypemap}" var="entry">
     <option value='${entry.key }' <c:if test="${ entry.key == cooFee.mobilePay5Type }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
<tr align="center">
			<td width="33%">移动副计费点5</td>
			<td width="33%"><input type="text" id="mobile_pay5_id" value = "${cooFee.mobilePay5Id}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
	<%-- 	<tr align="center">
    <td width="33%" id="title_pay_id">计费方式6</td>
    <td width="30%">
    <select id="mobile_pay6_type" name="mobile_pay6_type" class="" >
    <c:forEach items="${paytypemap}" var="entry">
     <option value='${entry.key }' <c:if test="${ entry.key == cooFee.mobilePay6Type }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
<tr align="center">
			<td width="33%">移动副计费点6</td>
			<td width="33%"><input type="text" id="mobile_pay6_id" value = "${cooFee.mobilePay6Id}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
	<%-- 	<tr align="center">
    <td width="33%" id="title_pay_id">计费方式7</td>
    <td width="30%">
    <select id="mobile_pay7_type" name="mobile_pay7_type" class="" >
    <c:forEach items="${paytypemap}" var="entry">
     <option value='${entry.key }' <c:if test="${ entry.key == cooFee.mobilePay7Type }">selected</c:if> >${entry.value }</option>
	</c:forEach>
	</select>
	</td>
<td>
<span id="warn_sell_id"></span></td>
</tr> --%>
<tr align="center">
			<td width="33%">移动副计费点7</td>
			<td width="33%"><input type="text" id="mobile_pay7_id" value = "${cooFee.mobilePay7Id}"/></td>
			<td><span id="warn_sell_id"></span></td>
		</tr>
			<tr align="center">
			<td width="33%"><a href="#" onclick="btnSave()"><img src="../_js/ico/btn_save.gif" border="0" alt="保存" align="absmiddle"/></a></td>
			<td width="33%"><input type="hidden" id = "coFeeId" value="${cooFee.id }"/></td>
			<td></td>
		</tr>
	</table>

<script>
	function btnSave(){
		var mobile_pay_type,moblie_pay_id,mobile_pay1_type,moblie_pay1_id,moblie_pay1_param,mobile_pay1_type;
		var mobile_pay_type = $("#mobile_pay_type").val();
		var mobile_pay1_type = $("#mobile_pay1_type").val();
		var mobile_pay2_type = $("#mobile_pay2_type").val();
		var mobile_pay3_type = $("#mobile_pay3_type").val();
		var mobile_pay_id = $("#mobile_pay_id").val();
		var mobile_pay1_id = $("#mobile_pay1_id").val();
		var mobile_pay2_id = $("#mobile_pay2_id").val();
		var mobile_pay3_id = $("#mobile_pay3_id").val();
		var mobile_pay1_param = $("#mobile_pay1_param").val();
		var mobile_pay2_param = $("#mobile_pay2_param").val();
		var mobile_pay3_param = $("#mobile_pay3_param").val();
		
		var mobile_pay4_type = $("#mobile_pay4_type").val();
		var mobile_pay5_type = $("#mobile_pay5_type").val();
		var mobile_pay6_type = $("#mobile_pay6_type").val();
		var mobile_pay7_type = $("#mobile_pay7_type").val();
		var mobile_pay4_id = $("#mobile_pay4_id").val();
		var mobile_pay5_id = $("#mobile_pay5_id").val();
		var mobile_pay6_id = $("#mobile_pay6_id").val();
		var mobile_pay7_id = $("#mobile_pay7_id").val();
		
		
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
			"../servlet/CooperationMmAction",
			{
				cmd : "operateRemind",
				op : op,
				channelNo : channelNo,
				appNo : appNo,
				mmChannel : mmChannel,
				packetId : packetId,
				mobile_pay_type : mobile_pay_type,
				mobile_pay1_type : mobile_pay1_type,
				mobile_pay2_type : mobile_pay2_type,
				mobile_pay3_type : mobile_pay3_type,
				mobile_pay_id : mobile_pay_id,
				mobile_pay1_id : mobile_pay1_id,
				mobile_pay2_id : mobile_pay2_id,
				mobile_pay3_id : mobile_pay3_id,
				mobile_pay1_param :mobile_pay1_param,
				mobile_pay2_param :mobile_pay2_param,
				mobile_pay3_param :mobile_pay3_param,
				mobile_pay4_type : mobile_pay4_type,
				mobile_pay5_type : mobile_pay5_type,
				mobile_pay6_type : mobile_pay6_type,
				mobile_pay7_type : mobile_pay7_type,
				mobile_pay4_id : mobile_pay4_id,
				mobile_pay5_id : mobile_pay5_id,
				mobile_pay6_id : mobile_pay6_id,
				mobile_pay7_id : mobile_pay7_id
				
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

