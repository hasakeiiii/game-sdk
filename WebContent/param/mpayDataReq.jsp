<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*"%>
<%@ page import="model.*"%>
<%@ page import="common.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.*"%>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
	String packet_id="";//计费编号
	String acount_num="";//计费点个数
	String date="";//查询时间
	String pay_name="暂无";//支付名称
	int pay_amount=0;//支付金额
	String user_sum="暂无";//付费用户总数
	String consu_sum="";//付费次数
	String pay_code[] = new String[15];
	
	int num = 0;
	
	String sign = request.getParameter("opp");
	//DebuUtil.log("sign="+sign);
	if(sign == null)
	{
	    sign = "";
	}
	if (sign.equals("sch")){
	packet_id = request.getParameter("packet_id");
	acount_num = request.getParameter("acount_num");
	date = request.getParameter("date");
	DebuUtil.log("sign="+sign);
	//构造出pay_code计费点数组
	num = Integer.valueOf(acount_num);
	
	DebuUtil.log("packet_id="+packet_id+"\n acount_num="+acount_num+"\n date="+date);
	System.out.print("packet_id="+packet_id+"\n acount_num="+acount_num+"\n date="+date);
	
	for (int i = 0; i < num; i++) {
		int j = i + 1;
		if (j < 10){
			pay_code[i] = packet_id + "0" + j;
			}
		else if (j >= 10){
			pay_code[i] = packet_id + j;
			}
			DebuUtil.log("pay_code[i]:"+pay_code[i]);
		}
		
	}

	MmPayDao mmPayDao = new MmPayDao(); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css" />
</head>
<body>
	<form name="mainForm" method="post" style="margin:0;padding:0">
		<table width="100%" class="table_noborder">
			<tr>
				<td width="25%">计费编号&nbsp;&nbsp;<input name="packet_id"
					value="<%=packet_id%>" />
				</td>
				<td width="25%">计费点个数&nbsp;&nbsp;<input name="acount_num"
					value="<%=acount_num%>" />
				</td>
				<td width="25%">时间&nbsp;&nbsp;<input name="date"
					value="<%=date%>" />
				</td>
				<td width="25%" align="right"><a href="#" onclick="javascript:goSearch()"><img
						src="../_js/ico/btn_search.gif" border="0" alt="搜索"
						align="absmiddle" /> </a></td>
			</tr>
		</table>
		<table id="TableColor" width="100%" border="0">
			<tr>
				<td>计费编号</td>
				<td>计费名称</td>
				<td>付费金额</td>
				<td>付费用户</td>
				<td>付费次数</td>
				<td>付费时间</td>

				<%
					int i;
					for (i=0; i < num; i++) {
					    DebuUtil.log("i="+i);
						String sql1 = "select sum(amount) from mm_pay where app_code='"+pay_code[i]+"' and ret='1' and date='"+date+"'";
						
						DebuUtil.log("sql1:"+sql1);
						System.out.print("sql1:"+sql1);
						
						
						pay_amount = mmPayDao.getCount(sql1)/100;
						/* if(!list.isEmpty()){
							String temp = list.get(0).toString();
							DebuUtil.log("temp:"+temp);
							System.out.print("temp:"+temp);
							pay_amount = Integer.valueOf(temp);
							
							}else{
								pay_amount = 0;	
							} */
						DebuUtil.log("pay_amount:"+pay_amount);
						System.out.print("pay_amount:"+pay_amount+"----------------------");	
																	
						String sql2 = "select * from mm_pay where app_code='"+pay_code[i]+"' and ret='1' and date='"+date+"'";
						DebuUtil.log("aql2:"+sql2);
						System.out.print("sql2:"+sql2);
						
						consu_sum = mmPayDao.getRecordCount(sql2)+"";
						/* list = mmPayDao.getList(sql2);
						if(!list.isEmpty()){
							consu_sum = list.get(0).toString();	
							DebuUtil.log("consu_sum:"+consu_sum);
							System.out.print("consu_sum:"+consu_sum);
							}else{
								consu_sum = "0";	
							} */
							DebuUtil.log("consu_sum:"+consu_sum);
						System.out.print("consu_sum:"+consu_sum+"----------------------");	
				%>
				<tr>
					<td><%=pay_code[i] %></td>
					<td><%=pay_name%></td>
					<td><%=pay_amount%></td>
					<td><%=user_sum%></td>
					<td><%=consu_sum%></td>
					<td><%=date%></td>
				</tr>
				<!-- <tr>
					<td>总计</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr> -->
				<%
					}
				%>
				
				
			
		</table>
	</form>

	<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css"
		media="screen" />
	<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
	<script type="text/javascript">
		function goSearch() {
			if (document.mainForm.packet_id.value == "") {
				error("请输入计费点编号");
				document.mainForm.packet_id.focus();
				return;
			} else if (document.mainForm.acount_num.value == "") {
				error("请输入计费点个数");
				document.mainForm.acount_num.focus();
				return;
			} else if (document.mainForm.date.value == "") {
				error("请输入时间");
				document.mainForm.date.focus();
				return;
			}
			document.mainForm.action = "mpayDataReq.jsp?opp=sch";
			document.mainForm.submit();
		}
	</script>
	<script type="text/javascript" src="../_js/elemUtil.js"></script>
	<script type="text/javascript" src="../_js/TableColor.js"></script>
	<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
