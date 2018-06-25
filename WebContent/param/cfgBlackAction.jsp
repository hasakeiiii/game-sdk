<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<jsp:include page="../check.jsp?check_role=admin" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
	CfgBlack cfgBlack=new CfgBlack();
//int cfg_black_id=azul.JspUtil.getInt(request.getParameter("cfg_black_id"));
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
azul.JspUtil.populate(cfgBlack, request);
CfgBlackDao cfgBlackDao=new CfgBlackDao();

String act_flag="-1";
String msg="操作失败";
String toPage="cfgBlackList.jsp?pageno="+pageno;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
if(op.equals("add")){ 
    act_flag=cfgBlackDao.add(cfgBlack);
}
else if(op.equals("edit")){ 
    act_flag=cfgBlackDao.edit(cfgBlack);
}
else if(op.equals("delete")){ 
    act_flag=cfgBlackDao.delete(cfgBlack.getCfgBlackId());
}
else{ 
    System.err.println("cfgBlack op action is not right,op:"+op);
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