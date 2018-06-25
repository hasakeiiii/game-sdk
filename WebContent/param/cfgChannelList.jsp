<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
Userinfo sysUser=(Userinfo)session.getAttribute("sysUser");
Integer UserId = sysUser.getId();
DebuUtil.log("channellist");
ChannelDao channelDao=new ChannelDao();
String keyWord=azul.JspUtil.getStr(request.getParameter("keyWord"),"");
String pageSql="SELECT * from channel WHERE no IN(SELECT channel_no from cooperation WHERE business_no IN(SELECT no from businesser WHERE login_user = "+UserId+"))";
if(!keyWord.equals("")){
	   pageSql+=" and name like '%"+keyWord+"%'";
	   DebuUtil.log("pageSql="+pageSql);
}
pageSql+=" order by id desc";
int pagesize=30;//每页记录数
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int recordcount=channelDao.getRecordCount(pageSql);//得到记录总数
List<Channel> list=channelDao.getList(pageno,pagesize,pageSql);
DebuUtil.log("list"+list.size());
UserinfoDao userinfoDao = new UserinfoDao();
String strSQL = "select * from userinfo";
ArrayList userlist = userinfoDao.getList(strSQL);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<td width="90%">查询关键字&nbsp;&nbsp;<input name="keyWord" value="<%=keyWord%>" />&nbsp;&nbsp;<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a></td><td width="10%" align="right"><a href="#" onclick="addAct()"><img src="../_js/ico/btn_add.gif" border="0" alt="增加" align="absmiddle"/></a></td></tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td>公司名称</td>
<td>公司代号</td>
<td>登录用户名</td>
<td>登录密码</td>
<td>角色</td>
<td>公司地址</td>
<td>联系人</td>
<td>操作</td></tr>
<%
for(int i=0;i<list.size();i++){
    Channel channel=(Channel)list.get(i);
    Userinfo userinfor = new Userinfo();
    userinfor.setId(0);
    userinfor.setUsername("");
    userinfor.setPass("");
    if(channel.login_user != 0)
    {
	    for(int j = 0; j < userlist.size(); j ++)
	    {
	    	Userinfo tUser=(Userinfo)userlist.get(j);
	    	
	    	 int id1 = tUser.id;
		   	 int id2 = channel.login_user;
		     if(id1 == id2)
	    	 {	    		
	    		userinfor = tUser;
	    		break;
	    	 }
	    }
    }
%>
<tr>
<td><%=channel.getId()%></td>
<td><%=channel.getName()%></td>
<td><%=channel.getNo()%></td>
<td><%=userinfor.getUsername()%></td>
<td><%=userinfor.getPass()%></td>
<td><%=userinfor.getRole()%></td>
<td><%=channel.getAddr()%></td>
<td><%=channel.getContact()%></td>
<td><a href="#" onclick="editAct('<%=channel.getId()%>','<%=userinfor.getId()%>')"><img src="../_js/ico/btn_edit.gif" border="0" alt="修改"/></a></td>
</tr>
<%
}
%>
</table>
</form>
<%
String linkParam="";
out.print(azul.JspUtil.getPageCode(pageno,pagesize,recordcount,request,linkParam));
%>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
function addAct(){
    window.location="cfgChannelEdit.jsp";
}
function editAct(id,uerid){
    window.location="cfgChannelEdit.jsp?pageno=<%=pageno%>&id="+id+"&uerid="+uerid;
}
function infoAct(id){
    //window.location="cfgSpInfo.jsp?id="+id;
}
function goSearch(){
    if(document.mainForm.keyWord.value==""){
	     error("请输入查询关键字");
		 document.mainForm.keyWord.focus();
		 return;
	}
	document.mainForm.action="cfgChannelList.jsp";
	document.mainForm.submit();
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
