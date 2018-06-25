<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="model.*" %>
<%@ page import="dao.*" %>
<%@ page import="common.*" %>
<jsp:include page="../check.jsp?check_role=admin" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
Userinfo userinfo=new Userinfo();
int id =azul.JspUtil.getInt(request.getParameter("id"));
String role=azul.JspUtil.getStr(request.getParameter("role"));
String username=azul.JspUtil.getStr(request.getParameter("username"));
String userpass=azul.JspUtil.getStr(request.getParameter("userpass"));
azul.JspUtil.populate(userinfo, request);
UserinfoDao userinfoDao=new UserinfoDao();

String act_flag="-1";
String msg="操作失败";
String toPage="sysRoleList.jsp";

if(op.equals("add")){ 
   Userinfo us = new Userinfo();
   java.util.ArrayList nameList=userinfoDao.getValueList("select username from userinfo");
	if(nameList.contains(username)){
		   act_flag="-1";
		   msg="该用户名已经存在";
	}
	else{
            us.setUsername(username);
            us.setPass(userpass);
            us.setRole(role);
            userinfoDao.add(us);
            act_flag = "1";
      }
      toPage="sysRoleList.jsp";
}
else if(op.equals("edit")){ 	
	 Userinfo user = (Userinfo)userinfoDao.getById(id);
	 Userinfo user2=(Userinfo)userinfoDao.loadBySql("select * from userinfo where username='"+username+"'");
	 if(user2 != null){
	      if(user.getId()!= user2.getId()){
		   act_flag="-1";
		   msg="该用户名已经存在";
             	}else{
	       user.setUsername(username);
	       user.setPass(userpass);
	       user.setRole(role);
	       act_flag = userinfoDao.edit(user);
	                 }
	 }else{
	user.setUsername(username);
	user.setPass(userpass);
	user.setRole(role);
	act_flag = userinfoDao.edit(user);
	}
 toPage="sysRoleList.jsp";
}

else if(op.equals("delete")){ 
    act_flag=userinfoDao.delete(userinfo.getId());
    userinfoDao.executeUpdate("delete from userinfo where id="+userinfo.getId());
}


%>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
if("<%=act_flag%>"=="1"){
    ok("操作成功",callback);
}
else{
	error("<%=msg%>",callback);
}
function callback(){
	location="<%=toPage%>";
}
</script>
</body>
</html>
