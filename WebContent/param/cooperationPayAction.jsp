<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
CooperationPay cooperationPay=new CooperationPay();
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);


//azul.JspUtil.populate(cfgSp, request);//game_code
cooperationPay.setPacketId(request.getParameter("packet_id"));
cooperationPay.setPayId(request.getParameter("pay_id"));
cooperationPay.setPayData(request.getParameter("pay_data"));
cooperationPay.setPayDataProvince(request.getParameter("pay_data_province"));
CooperationPayDao cooperationPayDao=new CooperationPayDao();

///////////////////////////////////////
//azul.JspUtil.populate(cfgSp, request);


String act_flag="-1";
String msg="操作失败";
String toPage="cooperationPayList.jsp?pageno="+pageno;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
if(op.equals("add")){ 
	
	
		int ret=cooperationPayDao.addCooperationPay(cooperationPay);
		if(ret == ConstValue.OK)
		{
		   act_flag="1";
		}
}
else if(op.equals("edit")){ 
	
	int id = azul.JspUtil.getInt(request.getParameter("id"),0);
	CooperationPay oldCoo = (CooperationPay)cooperationPayDao.getById(id);
	cooperationPay.setId(id);
	act_flag=cooperationPayDao.edit(cooperationPay);
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