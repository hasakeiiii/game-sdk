<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<jsp:include page="../check.jsp?check_role=admin,sell" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
CfgSell cfgSell=new CfgSell();
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
azul.JspUtil.populate(cfgSell, request);
CfgSellDao cfgSellDao=new CfgSellDao();

String act_flag="-1";
String msg="操作失败";
String toPage="cfgSellList.jsp?pageno="+pageno;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
SysUserDao sysUserDao=new SysUserDao();
//判断用户名是否被使用
boolean canDo=true;
SysUser sysUser=null;
if(cfgSell.getSellUser().toLowerCase().indexOf("admin")>-1){
	msg="登录名称不能含有admin字符";
	canDo=false;
}
if(canDo){
	if(op.equals("add")){ 
		int count=sysUserDao.getRecordCount("select * from sys_user where username='"+cfgSell.getSellUser()+"'");
		if(count>0){
			msg="该登录名称已经存在";
			act_flag="-1";
		}
		else{
		    SysUser companySysUser=new SysUser();
		    common.MD5 md5=new common.MD5();
		    companySysUser.setUsername(cfgSell.getSellUser());
		    companySysUser.setUserpass(md5.toMD5(cfgSell.getSellPass()));
		    companySysUser.setRole("sell");
		    act_flag=sysUserDao.add(companySysUser); 
		    if("1".equals(act_flag)){
		    	int index=sysUserDao.getNextSort("select max(sys_user_id) from sys_user",0);
		    	cfgSell.setCfgSellId(null);
				cfgSell.setSysUserId(index);
			    act_flag=cfgSellDao.add(cfgSell);
		    }
		}
	}
	else if(op.equals("edit")){ 
		CfgSell oldCfgSell=(CfgSell)cfgSellDao.getById(cfgSell.getCfgSellId());
		int count=sysUserDao.getRecordCount("select * from sys_user where username='"+cfgSell.getSellUser()+"' and sys_user_id<>"+oldCfgSell.getSysUserId());
		if(count>0){
			msg="该登录名称已经存在";
			act_flag="-1";
		}
		else{
			if("".equals(cfgSell.getSellPass())){
				cfgSell.setSellPass(null);
			}
		    act_flag=cfgSellDao.edit(cfgSell);
		    sysUser=(SysUser)sysUserDao.loadBySql("select sys_user.* from sys_user join cfg_sell on sys_user.sys_user_id=cfg_sell.sys_user_id where cfg_sell_id="+cfgSell.getCfgSellId());
		    if(act_flag.equals("1")){
		    	sysUser.setUsername(cfgSell.getSellUser());
		    	if(cfgSell.getSellPass()!=null){
		    		common.MD5 md5=new common.MD5();
		    		sysUser.setUserpass(md5.toMD5(cfgSell.getSellPass()));
		    	}
		    	act_flag=sysUserDao.edit(sysUser);
		    }
		    if(act_flag.equals("1")){
		    	msg="修改信息成功";
			    act_flag="0";
		    }
		}
	}
	else if(op.equals("editSelf")){ 
		CfgSell oldCfgSell=(CfgSell)cfgSellDao.getById(cfgSell.getCfgSellId());
		int count=sysUserDao.getRecordCount("select * from sys_user where username='"+cfgSell.getSellUser()+"' and sys_user_id<>"+oldCfgSell.getSysUserId());
		if(count>0){
			msg="该登录名称已经存在";
			act_flag="-1";
		}
		else{
			if("".equals(cfgSell.getSellPass())){
				cfgSell.setSellPass(null);
			}
		    act_flag=cfgSellDao.edit(cfgSell);
		    sysUser=(SysUser)sysUserDao.loadBySql("select sys_user.* from sys_user join cfg_sell on sys_user.sys_user_id=cfg_sell.sys_user_id where cfg_sell_id="+cfgSell.getCfgSellId());
		    if(act_flag.equals("1")){
		    	sysUser.setUsername(cfgSell.getSellUser());
		    	if(cfgSell.getSellPass()!=null){
		    		common.MD5 md5=new common.MD5();
		    		sysUser.setUserpass(md5.toMD5(cfgSell.getSellPass()));
		    	}
		    	act_flag=sysUserDao.edit(sysUser);
		    }
		    if(act_flag.equals("1")){
		    	msg="修改信息成功";
			    act_flag="0";
		    }
		}
		toPage="cfgSellEditSelf.jsp";
	}
	else{ 
	    System.err.println("cfgSell op action is not right,op:"+op);
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