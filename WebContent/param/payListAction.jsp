<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
AppPay appPay=new AppPay();
MmPayData mmpaydata = new MmPayData();
MmPayDataDao mmpaydatadao = new MmPayDataDao();
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
String gameNo = request.getParameter("gameNo");
int moneycount = Integer.parseInt(request.getParameter("moneycount"));
int priority = Integer.parseInt(request.getParameter("priority"));

//azul.JspUtil.populate(cfgSp, request);//game_code
//app.setPayId(request.getParameter("payId"));
appPay.setName(request.getParameter("payName"));
appPay.setAppPayId(request.getParameter("appPayId"));
appPay.setNo(gameNo);
appPay.setCompany(request.getParameter("company"));
appPay.setProvince(request.getParameter("province"));
appPay.setProvinceMoney(request.getParameter("provinceMoney"));
appPay.setLimitMoney(Integer.parseInt(request.getParameter("limitMoney")));
appPay.setLimitUserMoney(Integer.parseInt(request.getParameter("limitUserMoney")));
appPay.setShieldRegion(request.getParameter("shieldRegion"));
appPay.setShieldTime(request.getParameter("shieldTime"));
appPay.setMoneycount(moneycount);
appPay.setPriority(priority);

AppPayDao appPayDao=new AppPayDao();

///////////////////////////////////////
//azul.JspUtil.populate(cfgSp, request);
mmpaydatadao.getMmPayDataByAppPay(gameNo,moneycount,priority);

String act_flag="-1";
String msg="操作失败";
String toPage="payListList.jsp?pageno="+pageno;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");


 if(op.equals("add")){ 
	
	
		int ret=appPayDao.addAppPay(appPay);
		if(ret == ConstValue.OK)
		{
		   act_flag="1";
		}
}
else  if(op.equals("edit")){ 
	
	int id = azul.JspUtil.getInt(request.getParameter("id"),0);
	AppPay oldAppPay = (AppPay)appPayDao.getById(id);
	appPay.setId(id);
	act_flag=appPayDao.edit(appPay);
}
else{ 
    System.err.println("error,op:"+op);
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