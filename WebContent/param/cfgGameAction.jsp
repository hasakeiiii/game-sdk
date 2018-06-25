<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
App app=new App();
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
//azul.JspUtil.populate(cfgSp, request);//game_code
app.setName(request.getParameter("game_name"));
app.setNo(request.getParameter("game_code"));
app.setMykey(request.getParameter("game_mykey"));
app.setUrl(request.getParameter("game_url"));
app.setDiscontent(request.getParameter("discontent"));
app.setNoturl(request.getParameter("noturl"));
app.setDiscontent2(request.getParameter("discontent2"));
app.setDisurl(request.getParameter("disurl"));
app.setExiturl(request.getParameter("exiturl"));
app.setPayUrl(request.getParameter("pay_url"));
app.setPayMark(request.getParameter("pay_mark"));
app.setGameType(request.getParameter("game_type"));
app.setMmCompany(request.getParameter("mm_company"));
app.setOff(Integer.valueOf(request.getParameter("onlyoff")));
app.setPaywaysign(request.getParameter("paywaysign"));
app.setCpcount(Integer.parseInt(request.getParameter("cpcount")));

int distemp = Integer.parseInt(request.getParameter("yeediscount"));
app.setYeediscount(distemp);

app.setCpNo(request.getParameter("company_name"));

AppDao appDao=new AppDao();

String act_flag="-1";
String msg="操作失败";
String toPage="cfgGameList.jsp?pageno="+pageno;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
if(op.equals("add")){ 
	int ret=appDao.addApp(app);
	if(ret == ConstValue.OK)
	{
	   act_flag="1";
	}
}
else if(op.equals("edit")){ 
	int id = azul.JspUtil.getInt(request.getParameter("id"),0);
	App oldapp = (App)appDao.getById(id);
	app.setId(id);
	act_flag=appDao.edit(app);
	/*if(oldapp.getName().equals(app.getName()))
	{
		
	}
	else
	{
	    CooperationDao cooperationDao = new CooperationDao();
	    cooperationDao.updateAppName(app.getNo(), app.getName());
	}*/
}
else{ 
    System.err.println("app op action is not right,op:"+op);
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