<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
SysParam sysParam = new SysParam();
SysParamDao sysParamDao = new SysParamDao();
azul.JspUtil.populate(sysParam, request);//game_code
/*businesser.setId(id);
businesser.setName(request.getParameter("name"));
businesser.setNo(request.getParameter("no"));
businesser.setLoginUser(uerid);
BusinesserDao businesserDao=new BusinesserDao();
*/
DebuUtil.log("sysparam");
///////////////////////////////////////
//azul.JspUtil.populate(cfgSp, request);
String vague=azul.JspUtil.getStr(request.getParameter("vague"),"");
String act_flag="-1";
String msg="操作失败";
String toPage="SysParamEdit.jsp?id="+sysParam.getId();
if(vague.equals("1")){
	toPage="VagueSysParam.jsp?id="+sysParam.getId();
}
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
if(sysParam.getId() == 0){ 	
	act_flag = sysParamDao.addRecord(sysParam);	
        //DebuUtil.log("新增act_flag＝"+act_flag);
}
else { 
        //DebuUtil.log("编辑sysParam.getId()＝"+sysParam.getId());
	act_flag = sysParamDao.editRecord(sysParam);
	
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