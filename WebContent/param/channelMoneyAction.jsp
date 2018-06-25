<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>

<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
int id = azul.JspUtil.getInt(request.getParameter("id"),0);
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
CooperationMoneyDao cooperationMoneyDao=new CooperationMoneyDao();
String act_flag="-1";
String msg="操作失败";
String toPage="channelMoneyList.jsp?pageno="+pageno;
if(op.equals("delete")){
	 int delete = cooperationMoneyDao.deleteCooperationMoney(String.valueOf(id));
	 act_flag = "1";
}else{
String channelNo=request.getParameter("channel_no");
String packet_id=request.getParameter("packet_id");
String app_pay=request.getParameter("app_pay");
int limitMoney = Integer.parseInt(request.getParameter("limitMoney"));//计费编号

CooperationMoney cooperationMoney=new CooperationMoney();



/*ChannelDao channelDao=new ChannelDao();
java.util.Map channelMap=channelDao.getSelectMap("select no,name from channel");
channel_name = (String)channelMap.get(request.getParameter("channel_no"));*/
cooperationMoney.setChannelNo(channelNo);

/*AppDao appDao=new AppDao();
java.util.Map appMap=appDao.getSelectMap("select no,name from app");
app_name = (String)appMap.get(request.getParameter("app_no"));
DebuUtil.log("app_name="+app_name+"app_no="+request.getParameter("app_no"));*/
cooperationMoney.setPacketId(packet_id);
cooperationMoney.setLimitMoney(limitMoney);
cooperationMoney.setAppPay(app_pay);



if(op.equals("add")){ 
	int ret=cooperationMoneyDao.addCooperationMoney(cooperationMoney);
	if(ret == ConstValue.OK)
	{
	   act_flag="1";
	}
}
else if(op.equals("edit")){ 
	
	cooperationMoney = (CooperationMoney)cooperationMoneyDao.getById(id);
	cooperationMoney.setId(id);
	act_flag=cooperationMoneyDao.edit(cooperationMoney);
}

else{ 
    System.err.println("cooperation op action is not right,op:"+op);
}
}
%>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
if("<%=act_flag%>"=="-1"){
    error("<%=msg%>",callback);
}
else{
	ok("操作成功",callback);
}
function callback(){
	location="<%=toPage%>";
}
</script>
</body>
</html>