<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
Olrecords olrecords = new Olrecords();
String toPage="SendOnly.jsp";
String username = request.getParameter("username");
olrecords.setUsername(username);
int a = Integer.valueOf(request.getParameter("amount"));
int off = Integer.valueOf(request.getParameter("onlyoff"));
int amount = a*100/off;
olrecords.setAmount(a);
olrecords.setSendAmount(amount-a);
String gameId = request.getParameter("gameno");
olrecords.setGameId(gameId);
olrecords.setOff(off);
String payno = request.getParameter("tpayno");
olrecords.setPayNo(payno);
olrecords.setOperator(request.getParameter("operator"));
olrecords.setGsNo(request.getParameter("gsno"));
String date = DateUtil.getDate();
String time = DateUtil.getTime();
olrecords.setDate(date);
olrecords.setTime(time);
olrecords.setStatus("0");

OlrecordsDao olDao = new OlrecordsDao();
String ret = "-1";
String act_flag="-1";
String msg="操作失败";
 
 Olrecords recoder = olDao.getRecoderByPayNo(payno);
 if(recoder!=null&&recoder.getStatus().equals("1")){
 	act_flag = "-2";
 	msg = "订单已处理";
 }else{
 	ret = olDao.add(olrecords);
 	recoder = olDao.getRecoderByPayNo(payno);
	if(!ret.equals("-1")){
		System.err.println("添加到下发记录olrecord成功:");
		OnlybalanceDao onlydao = new OnlybalanceDao();
		Onlybalance onlybalance = onlydao.getRecord(username, gameId);
		if(onlybalance==null){
			onlybalance = onlydao.addNew(username, gameId);
			System.err.println("创建onlybalance结果:"+onlybalance.toString());
		}
		int ol = onlybalance.getAccountLeft()+olrecords.getSendAmount();
		onlybalance.setAccountLeft(ol);
		act_flag = onlydao.edit(onlybalance);
		System.err.println("修改onlybalance结果:"+act_flag);
	if(act_flag.equals("1")){
		recoder.setStatus("1");
		System.err.println("修改olrecords状态:"+recoder.toString());
		String mret = olDao.edit(recoder);
		System.err.println("修改记录状态结果:"+mret);
		}
	}
 }
 


%>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
if("<%=act_flag%>"=="-1"){
    error("<%=msg%>",callback);
}else if("<%=act_flag%>"=="-2"){
    error("<%=msg%>",callback);
}else{
	ok("操作成功",callback);
}
function callback(){
	location="<%=toPage%>";
}

</script>
</body>
</html>