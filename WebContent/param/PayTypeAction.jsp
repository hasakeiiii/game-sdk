<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
PayTypeCount payTypeCount=new PayTypeCount();
int id = azul.JspUtil.getInt(request.getParameter("id"),0);
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
azul.JspUtil.populate(payTypeCount, request);

//azul.JspUtil.populate(cfgSp, request);//game_code
PayTypeCountDao payTypeCountDao=new PayTypeCountDao();

///////////////////////////////////////
//azul.JspUtil.populate(cfgSp, request);


String act_flag="-1";
String msg="操作失败";
String toPage="PayTypeList.jsp?pageno="+pageno;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
if(op.equals("add")){ 
	int add =payTypeCountDao.addPayTypeCount(payTypeCount);
	if(add == 0){
		act_flag = "1";
	}
}
else if(op.equals("edit")){ 
	
	act_flag=payTypeCountDao.EditPayTypeCount(payTypeCount);
}
else{ 
    System.err.println("businesser op action is not right,op:"+op);
}
//////////////////////////////////////

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