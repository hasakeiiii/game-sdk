<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>

<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
String bussiness = request.getParameter("BSS_NO");
%>

<script type="text/javascript">
	var businessNo = '<%=bussiness%>';
</script>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>游戏运营管理系统</title>
	<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
	<link rel="stylesheet" href="../_css/common.css" type="text/css"/>
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
				<select name="business_no" id="business_no" onchange=chgbus()>
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
				<a href= "#" onclick="goSearch()"><img src="../_js/ico/btn_ok.gif" border="0" alt="确定" align="absmiddle"/></a>
				<input type="button" value="导出" onclick="goExcel()" ID="Button1" NAME="Button1">
			</td>
		</tr>
	</table>
</form>
	
<table id="TableColor" width="100%" border="0">
	<tr>
		<td>日期</td>
		<td>渠道ID</td>
		<td>渠道名称</td>
		<td>商务负责人</td>
		<td>推广方式</td>
		<td>新增用户</td>
		<td>新增移动用户</td>
		<td>移动用户占比</td>
		<td>新增用户付费金额</td>
		<td>当日新增ARPU</td>
		<td>活跃用户</td>
		<td>次日留存率</td>
		<td>付费用户</td>
		<td>付费次数</td>
		<td>付费金额</td>
		<td>活跃付费率</td>
		<td>付费成功率</td>
		<td>ARPPU</td>
		<td>激活ARPU</td>
		<td>人均会话时长</td>
		<td>移动付费金额</td>
		<td>联通付费金额</td>
		<td>电信付费金额</td>
		<td>显示比例</td>
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
<script type="text/javascript">
//加载默认选中单机并触发onchange事件
	var select = $("#game_type option[value='off_line']");
	$(select).attr("selected",true);
	$(select).trigger("change");
</script>
<script>
var gameNo,bussinessNo,channelNo,apkNo,year,month,day,payType;

var curPage = 1;
var pageSize = 100;
var totalPage,totalCount;
function chgbus(){
	if(<%=bussiness%> ==null){
		businessNo = $("#business_no :selected").val();
	}
}
function goSearch(){

   businessNo = $("#business_no :selected").val(); 

   gameNo = $("#game_no :selected").val();
 
   channelNo = $("#channel_no :selected").val();
   payType = $("#pay_type :selected").val();
   apkNo = $("#apk_no :selected").val();
   year = $("#selYear :selected").text();
   month = $("#selMonth :selected").text();
	day = $("#selDay :selected").text();
   op = "";
   
   ajaxLoading();
   
   $.ajax({
       type: "post",
       url: "../servlet/ChannelQueryAction",
       data: {
   			gameNo : gameNo,
   			businessNo : businessNo,
   			channelNo : channelNo,
   			apkNo : apkNo,
   			year : year,
   			month : month,
   			day : day,
   			curPage : curPage,
   			pageSize : pageSize,
   			op :op
   		},
        dataType: "json",
        success : function(data){
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

 
   			$.each(list,function(i,item){
   				
    			/* $.each(item,function(i,itemone){
    				console.info(itemone);
    				if(itemone == null){
    					itemone = 0;
    				}
    			}); */
    			
    			var table_tr = "<tr align='center'>";
    			table_tr += "<td>" + item[0] + "</td>";
    			table_tr += "<td>" + item[1] + "</td>";
    			table_tr += "<td>" + item[2] + "</td>";      
    			table_tr += "<td>" + item[3] + "</td>";      
    			table_tr += "<td>" + item[4] +"</td>"; 
    			table_tr += "<td>" + item[5] + "</td>"; 
    			table_tr += "<td>" + item[6] +"</td>"; 
    			if(item[7]==null){
    				table_tr += "<td>" + 0 +"</td>"; 
    			}else{
    				table_tr += "<td>" + item[7] +"%" +"</td>"; 
    			}
    			table_tr += "<td>" + item[8] +"</td>"; 
    			if(item[9]==null){
    				table_tr += "<td>" + 0 +"</td>"; 
    			}else{
    				table_tr += "<td>" + item[9] +"</td>"; 
    			}
    			
    			table_tr += "<td>" + item[10] +"</td>"; 
    			if(item[11]==null){
    				table_tr += "<td>" + 0 +"</td>"; 
    			}else{
    				table_tr += "<td>" + item[11] +"%" +"</td>"; 
    			}
    			
    			table_tr += "<td>" + item[12] +"</td>"; 
    			table_tr += "<td>" + item[13]  +"</td>"; 
    			table_tr += "<td>" + item[14] +"</td>"; 
    			table_tr += "<td>" + item[15] +"%" +"</td>"; 
    			if(item[16]==null){
    				table_tr += "<td>" + 0 +"</td>"; 
    			}else{
    				table_tr += "<td>" + item[16] +"%" +"</td>"; 
    			}
    			if(item[17]==null){
    				table_tr += "<td>" + 0 +"</td>"; 
    			}else{
    				table_tr += "<td>" + item[17] +"</td>"; 
    			}	
    			if(item[18]==null){
    				table_tr += "<td>" + 0 +"</td>"; 
    			}else{
    				table_tr += "<td>" + item[18] +"</td>"; 
    			}
    			if(item[19]==null){
    				table_tr += "<td>" + 0 +"</td>"; 
    			}else{
    				table_tr += "<td>" + item[19] +"</td>"; 
    			}
    			table_tr += "<td>" + item[20] +"</td>"; 
    			table_tr += "<td>" + item[21] +"</td>"; 
    			table_tr += "<td>" + item[22] +"</td>"; 
    			table_tr += "<td>" + item[23] +"</td>"; 
 
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
			
			ajaxLoadEnd();
		//	alert(data.map.msg);
   		},
   		error : function(XMLHttpRequest, textStatus, errorThrown){
  			alert("查询出错或者无数据");
  			ajaxLoadEnd();
  			$("#TableColor").find("tr").each(function(i,value){
  				if(i != 0)
  					$(value).remove();
  			});
  			
  			$("#pageContainer").empty();}
   		}
   );
}
function goExcel(){
	   gameNo = $("#game_no :selected").val();
	   businessNo = $("#business_no :selected").val(); 
	   channelNo = $("#channel_no :selected").val();
	   payType = $("#pay_type :selected").val();
	   apkNo = $("#apk_no :selected").val();
	   year = $("#selYear :selected").text();
	   month = $("#selMonth :selected").text();
		day = $("#selDay :selected").text();
		op = "excel";

		  document.mainForm.action="../servlet/ChannelQueryExcelAction?op=excel&gameNo="+gameNo+"&businessNo="+businessNo+"&channelNo="+channelNo+"&apkNo="+apkNo+"&year="+year+"&month="+month+"&day="+day+"";
	      document.mainForm.submit();
	
}
</script>
</body>
</html>
