<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>游戏运营管理系统</title>
	<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>

<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
	<table width="100%" class="table_noborder">
		<tr>
			<td width="8%">游戏类型&nbsp;&nbsp;
				<select name="game_type" onchange="chgGameType(this)" id="game_type">
				  	<option value="">请选择</option>
				 	<option value="off_line">单机</option>
				 	<option value="on_line">网游</option>
				 	<option value="mm_on_line">MM网游</option>
				</select>&nbsp;
			</td>

			<td width="15%">游戏&nbsp;&nbsp;
				<select name="game_no" onchange=chgGame(this) id="game_no">
					<option value="">请选择</option>
				</select>&nbsp;
			</td>

			<td width="10%">所属商务&nbsp;&nbsp;
				<select name="business_no" id="business_no">
					<option value="">请选择</option>
				</select>&nbsp;
			</td>

			<td width="10%">渠道&nbsp;&nbsp;
				<select name="channel_no" id="channel_no" onchange = "chgChannel()">
					<option value="">请选择</option>
				</select>&nbsp;
			</td>
		<td width="10%">APK包号&nbsp;&nbsp;
					<select name="apk_no" id="apk_no">
						<option value="">请选择</option>
					</select>&nbsp;
				</td>
			<td width="15%">时间
				<select name="selYear" id="selYear">
				</select>&nbsp;年
			
				<select name="selMonth" onchange = "chgMonth()" id="selMonth">
				</select>&nbsp;月
			
				<select name="selDay" id="selDay">
				</select>&nbsp;日
			</td>


			<td width="10%">
				<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_ok.gif" border="0" alt="确定" align="absmiddle"/></a>
			</td>
		</tr>
	</table>
</form>
	
<table id="TableColor" width="100%" border="0">
	<tr>
		<td>日期</td>
		<td>省份</td>
		<td>移动新增用户</td>
		<td>移动新增付费</td>
		<td>移动付费</td>
		<td>联通新增用户</td>
		<td>联通新增付费</td>
		<td>联通付费</td>
		<td>电信新增用户数</td>
		<td>电信新增付费</td>
		<td>电信付费</td>
		<td>其他新增用户</td>
		<td>新增总数</td>
		<td>新增付费</td>
		<td>总付费</td>
	</tr>
</table>
<!-- 分页容器 -->
<div id = "pageContainer" align="center"></div>

<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/commonSelectOnload.js"></script>
<script type="text/javascript" src="../_js/common.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script>
var gameNo,bussinessNo,channelNo,apkNo,year,month,day,payType;

var curPage = 1;
var pageSize = 30;
var totalPage,totalCount;

function goSearch(){
   gameNo = $("#game_no :selected").val();
   businessNo = $("#business_no :selected").val(); 
   channelNo = $("#channel_no :selected").val();
   payType = $("#pay_type :selected").val();
   apkNo = $("#apk_no :selected").val();
   year = $("#selYear :selected").text();
   month = $("#selMonth :selected").text();
   day = $("#selDay :selected").text();
   
   $.post(
   		"../servlet/ThreeProvinceAction",
   		{
   			gameNo : gameNo,
   			businessNo : businessNo,
   			channelNo : channelNo,
   			apkNo : apkNo,
   			year : year,
   			month : month,
   			day : day,
   			curPage : curPage,
   			pageSize : pageSize
   		},
   		function(data){
   			var list = data.map.list;
   			var list3 = data.map.list3;
   			totalPage = data.map.totalPage;
   			totalCount = data.map.totalCount;
   			
   			var info = data.info;
   			if(info != ""){
   				alert(info);
   				return;
   			}
   			
   			$("#TableColor").find("tr").each(function(i){
    			if(i != 0)
    				$(this).remove();
    		});

 
    		$.each(list,function(i,provinceData){
    			console.info(provinceData);
    			//var sum = item[0]*1 +item[1]*1 +item[2]*1+item[3]*1+item[4]*1+item[5]*1+item[6]*1+item[7]*1+item[8]*1;
    			var sumacount = provinceData.mobileNewAcount*1 + provinceData.unicomNewAcount*1 + provinceData.telecomNewAcount*1;
    			var sumnewpay = provinceData.mobileNewPay*1 +provinceData.unicomNewPay*1 + provinceData.telecomNewPay*1;
    			var sumpay  = provinceData.mobilePay*1 + provinceData.unicomPay*1 +provinceData.telecomPay*1;
    			var table_tr = "<tr align='center'>";
    			table_tr += "<td>" + provinceData.date + "</td>";
    			table_tr += "<td>" + provinceData.province + "</td>";
    			table_tr += "<td>" + provinceData.mobileNewAcount + "</td>";      
    			table_tr += "<td>" + provinceData.mobileNewPay/100 + "</td>";      
    			table_tr += "<td>" + provinceData.mobilePay/100 + "</td>"; 
    			table_tr += "<td>" + provinceData.unicomNewAcount + "</td>"; 
    			table_tr += "<td>" + provinceData.unicomNewPay/100 +"</td>"; 
    			table_tr += "<td>" + provinceData.unicomPay/100 +"</td>"; 
    			table_tr += "<td>" + provinceData.telecomNewAcount +"</td>"; 
    			table_tr += "<td>" + provinceData.telecomNewPay/100 +"</td>"; 
    			table_tr += "<td>" + provinceData.telecomPay/100 +"</td>"; 
    			table_tr += "<td>" + provinceData.otherNewAcount +"</td>"; //其他付费数量
    			table_tr += "<td>" + sumacount +"</td>"; 
    			table_tr += "<td>" + sumnewpay/100 +"</td>"; 
    			table_tr += "<td>" + sumpay/100 +"</td>"; 

    			$("#TableColor").append(table_tr);     
    		});

    		//数据查询完成之后添加分页效果
    		fillPage(totalPage,totalCount,curPage,pageSize);
    		//解除绑定函数 
			$("#firstPage").unbind();
			$("#prePage").unbind();
			$("nextPage").unbind();
			$("#lastPage").unbind();
			$("#elemToPage").unbind();
			
			//绑定函数
			$("#firstPage").bind('click',function(){
				curPage = 1;
				goSearch();
			});
			$("#prePage").bind('click',function(){
				curPage = curPage - 1;
				goSearch();
			});
			$("#nextPage").bind('click',function(){
				curPage = curPage + 1;
				goSearch();
			});
			$("#lastPage").bind('click',function(){
				curPage = totalPage;
				goSearch();
			});
    		$("#elemToPage").bind('change',function(){
    			curPage = $("#elemToPage :selected").val() * 1;
    			goSearch();
    		});
    		
    		if(curPage == 1){
				$("#firstPage").unbind();
				$("#prePage").unbind();
			}
			if(curPage == totalPage){
				$("#nextPage").unbind();
				$("#lastPage").unbind();
			}
   		},"json"
   );
}

</script>
</body>
</html>
