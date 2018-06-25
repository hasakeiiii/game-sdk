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
String channel_name="";//渠道名称
String app_name="";//游戏名称
String app_type="";//游戏类型
String pay_name = "";//计费编号
int id=azul.JspUtil.getInt(request.getParameter("id"),0);

Cooperation cooperation=new Cooperation();
pay_name = azul.JspUtil.getStr(request.getParameter("pay_id"), "");

DebuUtil.log("pay_name"+pay_name);

int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
azul.JspUtil.populate(cooperation, request);
CooperationDao cooperationDao=new CooperationDao();

/*ChannelDao channelDao=new ChannelDao();
java.util.Map channelMap=channelDao.getSelectMap("select no,name from channel");
channel_name = (String)channelMap.get(request.getParameter("channel_no"));*/
cooperation.setId(id);
cooperation.setChannelName(channel_name);

/*AppDao appDao=new AppDao();
java.util.Map appMap=appDao.getSelectMap("select no,name from app");
app_name = (String)appMap.get(request.getParameter("app_no"));
DebuUtil.log("app_name="+app_name+"app_no="+request.getParameter("app_no"));*/
cooperation.setAppName(app_name);
cooperation.setPayId(pay_name);


String act_flag="-1";
String msg="操作失败";
String toPage="CooperationMoneyList.jsp?pageno="+pageno;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");

if(op.equals("add")){ 
	act_flag=cooperationDao.addItem(cooperation);
}
else if(op.equals("edit")){ 
	act_flag=cooperationDao.EditItem(cooperation);
}
else if(op.equals("chngRitio")){ 
	act_flag=cooperationDao.chngRitio(cooperation);
}
else{ 
    System.err.println("cooperation op action is not right,op:"+op);
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