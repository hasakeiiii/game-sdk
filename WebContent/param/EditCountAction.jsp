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
String packet_id=azul.JspUtil.getStr(request.getParameter("packet_id"),"");
String datestr=azul.JspUtil.getStr(request.getParameter("datestr"),"");
String ratio=azul.JspUtil.getStr(request.getParameter("ratio"),"");

String act_flag="-1";
String msg="操作失败";
String toPage="EditCount.jsp";
DebuUtil.log("packet_id="+packet_id+"datestr="+datestr+"ratio="+ratio);

if(ratio.equals("check"))
{
  GamePayDataGen.checkAll();
}
else if(ratio.equals("requry"))
{
  GamePayDataGen.requryAll();
}
else if(ratio.equals("settle"))
{
  GamePayDataGen.settleAll();
}
else
{
CooperationDao cooperationDao= new CooperationDao();
cooperationDao.chngRitio(packet_id, datestr,Integer.valueOf(ratio));
}
act_flag="1";

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