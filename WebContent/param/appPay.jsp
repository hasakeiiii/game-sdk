<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />

<%
String op =  request.getParameter("op");
String game_id = request.getParameter("game_id");;
String pay_id = request.getParameter("pay_id");;
AppPayDao appPayDao = new AppPayDao();
MmPayDao mmPayDao = new MmPayDao();
Map<String,String> game_idMap = appPayDao.getSelectMap1("select no,name from app_pay ");
//if(!StringUtil.is_nullString(game_id))

	String sql = String.format("select pay_id,pay_name from mm_pay_data where app_pay_id ='%s' "
			                     ,game_id);
	Map<String,String>  pay_idMap = mmPayDao.getSelectMap1(sql);//
    



 //= mmPayDao.getSelectMap1("select pay_id,pay_name from mm_pay_data ");



%>
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
			<td width="15%">公司&nbsp;&nbsp;
				<select name="company"  id="company">
					<option value="">请选择</option>
					<option value="mzhy">拇指互娱</option>
					<option value="zty">中泰源</option>
					<option value="mzyw">拇指游玩</option>
					<option value="jy">竟游</option>
					<option value="cq">创趣</option>
				</select>&nbsp;
			</td>

			<td width="15%">计费游戏&nbsp;&nbsp;
				<select name="game_id"  id="game_id" onchange= "chgAppPay1(this)" >
					<option value="">请选择</option>
				</select>&nbsp;
			</td>
				<td width="15%">计费点信息&nbsp;&nbsp;
				<select name="pay_id"  id="pay_id">
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
		<td>计费点金额</td>

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
function chgAppPay1(obj)
{
	document.mainForm.action="appPay.jsp?op=selchannel";
	document.mainForm.submit();
}
var gameNo,bussinessNo,channelNo,apkNo,year,month,day,payType;

var curPage = 1;
var pageSize = 30;
var totalPage,totalCount;

function goSearch(){
	company = $("#company").val();
   gameId = $("#game_id").val();
   payId = $("#pay_id").val();
   year = $("#selYear :selected").text();
   month = $("#selMonth :selected").text();
   
  // day = $("#selDay :selected").text();
   
   $.post(
   		"../servlet/AppPayAction",
   		{
   			company : company,
   			gameId : gameId,
   			payId : payId,
   			year : year,
   			month : month,
   			curPage : curPage,
   			pageSize : pageSize
   		},
   		function(data){
/*    			var list = data.map.list;
   			var list3 = data.map.list3;
   			totalPage = data.map.totalPage;
   			totalCount = data.map.totalCount; */
   			var list = data.payMoney;
   			console.info(list);
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
	   			var table_tr = "<tr align='center'>";
				table_tr += "<td>" + item[0] +"</td>"; 
				table_tr += "<td>" + item[1]*1/100 +'元' +"</td>"; 
			$("#TableColor").append(table_tr);     
   			});


    		//数据查询完成之后添加分页效果
    		fillPage(1,1,1,1);
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
<%
out.println(azul.JspUtil.initSelect("game_id",game_idMap,game_id));
out.println(azul.JspUtil.initSelect("pay_id",pay_idMap,pay_id));
%>
</script>
</body>
</html>
