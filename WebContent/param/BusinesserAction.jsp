<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
Businesser businesser=new Businesser();
Userinfo userinfo = new Userinfo();
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);

int id=azul.JspUtil.getInt(request.getParameter("id"),0);
int uerid = azul.JspUtil.getInt(request.getParameter("uerid"),0);


String username=azul.JspUtil.getStr(request.getParameter("username"),"");//登录名
String userpass=azul.JspUtil.getStr(request.getParameter("userpass"),"");//密码


//azul.JspUtil.populate(cfgSp, request);//game_code
businesser.setId(id);
businesser.setName(request.getParameter("name"));
businesser.setNo(request.getParameter("no"));
businesser.setLoginUser(uerid);
BusinesserDao businesserDao=new BusinesserDao();

///////////////////////////////////////
//azul.JspUtil.populate(cfgSp, request);

userinfo.setId(uerid);
userinfo.setUsername(username);
userinfo.setPass(userpass);
userinfo.setRole("BSS");

String act_flag="-1";
String msg="操作失败";
String toPage="BusinesserList.jsp?pageno="+pageno;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
if(op.equals("add")){ 
	
	DebuUtil.log("username="+username);
	
	Businesser chn = businesserDao.getRecord(businesser.no);
	 
	if((chn == null) && (username.length() > 0))
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
			businesser.setLoginUser(userinfo.getId());
		}
	}
	
	if(act_flag != "-1")
	{
	   act_flag=businesserDao.add(businesser);
	}
	
}
else if(op.equals("edit")){ 
	
	UserinfoDao userinfoDao=new UserinfoDao();
	
	if(userinfo.getId() == 0)
	{
		if(username.length() > 0)
		{
			DebuUtil.log("增加用户名");
			int ret=userinfoDao.addUserinfo(userinfo);
			if(ret == ConstValue.OK)
			{
			   act_flag="1";
			}
			userinfo = userinfoDao.getUserinfo(userinfo.getUsername());
			if(userinfo != null)
			{
				businesser.setLoginUser(userinfo.getId());
			}
		}
	}
	else if(userinfo.getId() != 0)
	{
		DebuUtil.log("修改用户名");
		int ret=userinfoDao.editUserinfo(userinfo);
	    if(ret == ConstValue.OK)
		{
		   act_flag="1";
		}
	}
	
	
	//Channel oldchannel=(Channel)operaterDao.getById(id);
	act_flag=businesserDao.edit(businesser);
	
	/*if(oldchannel.getName().equals(channel.getName()))
	{
	}
	else
	{
		DebuUtil.log("渠道名字已修改");
	    CooperationDao cooperationDao = new CooperationDao();
	    cooperationDao.updateChannelName(channel.getNo(), channel.getName());
	}*/
	
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