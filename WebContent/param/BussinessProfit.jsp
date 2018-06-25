<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>

<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />

<%
String bussiness = request.getParameter("BSS_NO");
%>

<script type="text/javascript">
	var businessNo = '<%=bussiness%>';
</script>

<html>
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

			<td width="10%">游戏&nbsp;&nbsp;
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
		<td>移动付费</td>
		<td>联通付费</td>
		<td>电信付费</td>
		<td>付费次数</td>
		<td>CPS结算比例</td>
		<td>CPA单价</td>
		<td>新增用户</td>
		<td>安装数</td>
		<td>总金额</td>
		<td>利润</td>
	</tr>
</table>
<!-- 分页容器 -->
<div id = "pageContainer" align="center"></div>

<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script type="text/javascript" src="../_js/common.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/commonSelectOnload.js"></script>
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
var pageSize = 30;
var totalPage,totalCount;

function goSearch(){
	
   businessNo = $("#business_no :selected").val(); 
	
   gameNo = $("#game_no :selected").val();
   channelNo = $("#channel_no :selected").val();
   payType = $("#pay_type :selected").val();
   apkNo = $("#apk_no :selected").val();
   year = $("#selYear :selected").text();
   month = $("#selMonth :selected").text();
   op = "";
  // day = $("#selDay :selected").text();
   //alert("开始查询");
   ajaxLoading();
   
   
    $.ajax({
           type: "post",
           url: "../servlet/BussinessProfitAction",
           data: {
      			gameNo : gameNo,
       			businessNo : businessNo,
       			channelNo : channelNo,
       			apkNo : apkNo,
       			year : year,
       			month : month,
       		//	day : day,
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

    	 
    	   			$.each(list,function(i,bp){
    	    			console.info(bp);
    	    			var table_tr = "<tr align='center'>";
    	    			table_tr += "<td>" + bp.date + "</td>";
    	    			table_tr += "<td>" + bp.mobilePay + "</td>";
    	    			table_tr += "<td>" + bp.unicomPay + "</td>";
    	    			table_tr += "<td>" + bp.telePay + "</td>";
    	    			table_tr += "<td>" + bp.payNum + "</td>";
    	    			table_tr += "<td>" + bp.cpsCount + "%</td>";
    	    			table_tr += "<td>" + bp.cpaCount + "</td>";
    	    			table_tr += "<td>" + bp.activate + "</td>";
    	    			table_tr += "<td>" + bp.install + "</td>";
    	    			table_tr += "<td>" + bp.sumPay + "</td>";
    	    			table_tr += "<td>" + bp.profit + "</td>";
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
    				alert(data.map.msg);
      			
      			
      			
      		},
      		error : function(XMLHttpRequest, textStatus, errorThrown){
      			alert("查询出错或者无数据");
      			ajaxLoadEnd();
      			$("#TableColor").find("tr").each(function(i,value){
      				if(i != 0)
      					$(value).remove();
      			});
      			
      			$("#pageContainer").empty();
      		}
       	   
  	});
  
   
}

</script>
</body>
</html>
