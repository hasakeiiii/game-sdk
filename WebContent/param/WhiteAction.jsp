<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
WhiteList whiteList=new WhiteList();
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);


//azul.JspUtil.populate(cfgSp, request);//game_code
whiteList.setImei(request.getParameter("imei"));
whiteList.setNo(request.getParameter("no"));
WhiteListDao whiteListDao=new WhiteListDao();

///////////////////////////////////////
//azul.JspUtil.populate(cfgSp, request);


String act_flag="-1";
String msg="操作失败";
String toPage="WhiteList.jsp?pageno="+pageno;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
if(op.equals("add")){ 
	
	
		int ret=whiteListDao.addWhiteList(whiteList);
		if(ret == ConstValue.OK)
		{
		   act_flag="1";
		}
}
else if(op.equals("edit")){ 
	
	int id = azul.JspUtil.getInt(request.getParameter("id"),0);
	WhiteList oldWhite = (WhiteList)whiteListDao.getById(id);
	whiteList.setId(id);
	act_flag=whiteListDao.edit(whiteList);
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