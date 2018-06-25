<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%


//Userinfo userinfo = new Userinfo();


int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id=azul.JspUtil.getInt(request.getParameter("id"),0);
String cp_name=azul.JspUtil.getStr(request.getParameter("cp_name"),"");
String cp_address=azul.JspUtil.getStr(request.getParameter("cp_address"),"");
String cp_web=azul.JspUtil.getStr(request.getParameter("cp_web"),"");//渠道地址
String cp_username=azul.JspUtil.getStr(request.getParameter("cp_username"),"");//联系人
String cp_password=azul.JspUtil.getStr(request.getParameter("cp_password"),"");//登录名
String remarks=azul.JspUtil.getStr(request.getParameter("remarks"),"");//角色
int cp_no=azul.JspUtil.getInt(request.getParameter("cp_no"),0);
int login_user = azul.JspUtil.getInt(request.getParameter("login_user"),0); 

 
 
 
 
 
 
 
CpManageDao cpmanageDao=new CpManageDao();
UserinfoDao userinfodao = new UserinfoDao();

String act_flag="-1";
String msg="操作失败";
String toPage="cfgCpManageList.jsp?pageno="+pageno;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");

Userinfo userinfo = new Userinfo();
userinfo.setUsername(cp_username);
userinfo.setPass(cp_password);
userinfo.setRole("CP");

CpManage cpmanage = new CpManage();
cpmanage.setName(cp_name);
cpmanage.setAddress(cp_address);
cpmanage.setWeb(cp_web);
cpmanage.setRemarks(remarks);
cpmanage.setCpNo(cp_no);
cpmanage.setLoginUser(login_user);

if(op.equals("add")){ 
	DebuUtil.log("username="+cp_username);
	CpManage cm = cpmanageDao.getRecord(cp_no);
	if((cm == null) && (cp_username.length() > 0))
	{
		DebuUtil.log("增加用户名");
		UserinfoDao userinfoDao=new UserinfoDao();
		int ret=userinfoDao.addUserinfo(userinfo);
		if(ret == ConstValue.OK)
		{
		   act_flag="1";
		}
		userinfo = userinfoDao.getUserinfo(userinfo.getUsername());
		if(userinfo != null)
		{
			cpmanage.setLoginUser(userinfo.getId());
		}
	}
	
	if(act_flag != "-1"){
		act_flag=cpmanageDao.add(cpmanage);
	}
}

else if(op.equals("edit")){ 
	cpmanage.setId(id);
	cpmanage.setName(cp_name);
	cpmanage.setAddress(cp_address);
	cpmanage.setWeb(cp_web);
	cpmanage.setRemarks(remarks);
	cpmanage.setCpNo(cp_no);
	cpmanage.setLoginUser(login_user);
	int ret1 = cpmanageDao.editCpManage(cpmanage);
	
	userinfo.setId(login_user);
	userinfo.setUsername(cp_username);
	userinfo.setPass(cp_password);
	userinfo.setRole("CP");
	int ret2 = userinfodao.editUserinfo(userinfo);
	if(ret1 == ConstValue.OK && ret2 == ConstValue.OK){
		act_flag = "1";
	}
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