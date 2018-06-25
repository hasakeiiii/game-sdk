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
		<td>时间段</td>
		<td>激活</td>
		<td>注册用户</td>
		<td>有效用户</td>
		<td>注册有效率</td>
		<td>登陆用户</td>
		<td>激活注册率</td>
		<td>充值人数</td>
		<td>充值次数</td>
		<td>次日留存率</td>
		<td>周留存率</td>
		<td>月留存率</td>
		<td>第三方支付</td>
		<td>付费率</td>
		<td>ARPPU</td>
		<td>ARPU</td>
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
   		"../servlet/GameTimeAction",
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
   			var sum1 = 0;
   			var sum2 = 0;
   			var sum3 = 0;
   			var sum4 = 0;
   			var sum5 = 0;
   			var sum6 = 0;
   			var sum7 = 0;
   			var sum8 = 0;
   			var sum0 = 0;
    		$.each(list,function(i,item){
    			console.info(item);
    	//	var sum = item[0]*1 +item[1]*1 +item[2]*1+item[3]*1+item[4]*1+item[5]*1+item[6]*1+item[7]*1+item[8]*1;
    			var table_tr = "<tr align='center'>";
    			table_tr += "<td>"+ month+'-'+day+ "</td>";
    			table_tr += "<td>" + item[9] + "</td>";
    			table_tr += "<td>" + item[0] +"</td>";
    			table_tr += "<td>" + item[1] +"</td>";
    			table_tr += "<td>" + item[2] +"</td>";
    			table_tr += "<td>" + (item[2]*100/item[1]*1).toFixed(2) + '%' +"</td>";
    			table_tr += "<td>" + "" + "</td>";
    			table_tr += "<td>" + (item[1]*100/item[0]*1).toFixed(2) + '%' +"</td>";
    			table_tr += "<td>" + item[3] +"</td>";
    			table_tr += "<td>" + item[4] +"</td>";
    			table_tr += "<td>" + (item[5]*100/item[1]*1).toFixed(2) + '%' +"</td>";
    			table_tr += "<td>" + (item[6]*100/item[1]*1).toFixed(2) + '%' +"</td>";
    			table_tr += "<td>" + (item[7]*100/item[1]*1).toFixed(2) + '%' +"</td>";
    			table_tr += "<td>" + item[8]*1/100 +"</td>";
    			table_tr += "<td>" + (item[5]/item[1]).toFixed(2) +"</td>";
    			table_tr += "<td>" + (item[8]*1/100/item[5]).toFixed(2) +"</td>";
    			table_tr += "<td>" + (item[8]*1/100/item[2]).toFixed(2) +"</td>";
    		 	sum0 += item[0]*1;sum1 += item[1]*1;sum2 += item[2]*1;sum3 += item[3]*1;sum4 += item[4]*1;sum5 += item[5]*1;sum6 += item[6]*1;sum7 += item[7]*1;sum8 += item[8]*1;
    			$("#TableColor").append(table_tr);
    		});
    		console.info(sum1);
    		var table_trr = "<tr align='center'>";
    		table_trr += "<td>" + "总计" + "</td>";
    		table_trr += "<td>" + "" + "</td>";
    		table_trr += "<td>" + sum0 + "</td>";
    		table_trr += "<td>" + sum1 + "</td>";
    		table_trr += "<td>" + sum2 + "</td>";
    		table_trr += "<td>" + (sum2*100/sum1).toFixed(2) + '%' +"</td>";
    		table_trr += "<td>" + "" + "</td>";
    		table_trr += "<td>" + (sum1*100/sum0).toFixed(2) + '%' +"</td>";
    		table_trr += "<td>" + sum3 +"</td>";
			table_trr += "<td>" + sum4 +"</td>";
			table_trr += "<td>" + (sum5*100/sum1*1).toFixed(2) + '%' +"</td>";
			table_trr += "<td>" + (sum6*100/sum1*1).toFixed(2) + '%' +"</td>";
			table_trr += "<td>" + (sum7*100/sum1*1).toFixed(2) + '%' +"</td>";
			table_trr += "<td>" + sum8*1/100 +"</td>";
			table_trr += "<td>" + (sum5/sum1).toFixed(2) +"</td>";
			table_trr += "<td>" + (sum8*1/100/sum5).toFixed(2) +"</td>";
			table_trr += "<td>" + (sum8*1/100/sum2).toFixed(2) +"</td>";
			$("#TableColor").append(table_trr);
		//	(("<tr><td>总计</td><td> </td><td>")+sum1+("</td><td>")+sum2+("</td></tr>"))
		//	$("#TableColor").append(table_tr);
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
