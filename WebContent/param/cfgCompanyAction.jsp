<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<jsp:include page="../check.jsp?check_role=admin,cid" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
	CfgCompany cfgCompany=new CfgCompany();
//int cfg_company_id=azul.JspUtil.getInt(request.getParameter("cfg_company_id"));
//String name=azul.JspUtil.getStr(request.getParameter("name"));
//String cid=azul.JspUtil.getStr(request.getParameter("cid"));
//String cid_user=azul.JspUtil.getStr(request.getParameter("cid_user"));
//String cid_pass=azul.JspUtil.getStr(request.getParameter("cid_pass"));
//double rate=azul.JspUtil.getDouble(request.getParameter("rate"));
//String address=azul.JspUtil.getStr(request.getParameter("address"));
//String contact=azul.JspUtil.getStr(request.getParameter("contact"));
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
azul.JspUtil.populate(cfgCompany, request);
CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();

String act_flag="-1";
String msg="操作失败";
String toPage="cfgCompanyList.jsp?pageno="+pageno;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
SysUserDao sysUserDao=new SysUserDao();
//判断用户名是否被使用
boolean canDo=true;
SysUser sysUser=null;
if(cfgCompany.getCidUser()!=null && cfgCompany.getCidUser().toLowerCase().indexOf("admin")>-1){
	msg="登录名称不能含有admin字符";
	canDo=false;
}
if(canDo){
	if(op.equals("add")){ 
		int count=sysUserDao.getRecordCount("select * from sys_user where username='"+cfgCompany.getCidUser()+"'");
		if(count>0){
	msg="该登录名称已经存在";
	act_flag="-1";
		}
		else{
		    SysUser companySysUser=new SysUser();
		    common.MD5 md5=new common.MD5();
		    companySysUser.setUsername(cfgCompany.getCidUser());
		    companySysUser.setUserpass(md5.toMD5(cfgCompany.getCidPass()));
		    companySysUser.setRole("cid");
		    act_flag=sysUserDao.add(companySysUser); 
		    if("1".equals(act_flag)){
		    	int index=sysUserDao.getNextSort("select max(sys_user_id) from sys_user",0);
		    	cfgCompany.setCfgCompanyId(null);
		cfgCompany.setSysUserId(index);
		cfgCompany.setRate(0.0);
		cfgCompany.setScale(2);
		cfgCompany.setScaleAnchor(2);
	    act_flag=cfgCompanyDao.add(cfgCompany);
	    //添加默认的权限
	    SysRoleMenuDao sysRoleMenuDao=new SysRoleMenuDao();
	    String role=sysRoleMenuDao.getValue("select sys_menu_sub from sys_role_menu where sys_role='cid'").toString();
		if(!"".equals(role)){
			SysRoleDao sysRoleDao=new SysRoleDao();
			SysRole sysRole=new SysRole();
			sysRole.setSysUserId(cfgCompany.getSysUserId());
			sysRole.setSysMenuSub(role);
			act_flag=sysRoleDao.add(sysRole);
		}
		    }
		}
	}
	else if(op.equals("edit")){ 
		CfgCompany oldCfgCompany=(CfgCompany)cfgCompanyDao.getById(cfgCompany.getCfgCompanyId());
		int count=sysUserDao.getRecordCount("select * from sys_user where username='"+cfgCompany.getCidUser()+"' and sys_user_id<>"+oldCfgCompany.getSysUserId());
		if(count>0){
	msg="该登录名称已经存在";
	act_flag="-1";
		}
		else{
	if("".equals(cfgCompany.getCidPass())){
		cfgCompany.setCidPass(null);
	}
		    act_flag=cfgCompanyDao.edit(cfgCompany);
		    sysUser=(SysUser)sysUserDao.loadBySql("select sys_user.* from sys_user join cfg_company on sys_user.sys_user_id=cfg_company.sys_user_id where cfg_company_id="+cfgCompany.getCfgCompanyId());
		    if(act_flag.equals("1")){
		    	sysUser.setUsername(cfgCompany.getCidUser());
		    	if(cfgCompany.getCidPass()!=null){
		    		common.MD5 md5=new common.MD5();
		    		sysUser.setUserpass(md5.toMD5(cfgCompany.getCidPass()));
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
		CfgCompany oldCfgCompany=(CfgCompany)cfgCompanyDao.getById(cfgCompany.getCfgCompanyId());
		int count=sysUserDao.getRecordCount("select * from sys_user where username='"+cfgCompany.getCidUser()+"' and sys_user_id<>"+oldCfgCompany.getSysUserId());
		if(count>0){
	msg="该登录名称已经存在";
	act_flag="-1";
		}
		else{
	if("".equals(cfgCompany.getCidPass())){
		cfgCompany.setCidPass(null);
	}
	cfgCompany.setRate(null);
		    act_flag=cfgCompanyDao.edit(cfgCompany);
		    sysUser=(SysUser)sysUserDao.loadBySql("select sys_user.* from sys_user join cfg_company on sys_user.sys_user_id=cfg_company.sys_user_id where cfg_company_id="+cfgCompany.getCfgCompanyId());
		    if(act_flag.equals("1")){
		    	sysUser.setUsername(cfgCompany.getCidUser());
		    	if(cfgCompany.getCidPass()!=null){
		    		common.MD5 md5=new common.MD5();
		    		sysUser.setUserpass(md5.toMD5(cfgCompany.getCidPass()));
		    	}
		    	act_flag=sysUserDao.edit(sysUser);
		    }
		    if(act_flag.equals("1")){
		    	msg="修改信息成功";
	    act_flag="0";
		    }
		}
		toPage="cfgCompanyEditSelf.jsp";
	}
	else if(op.equals("editScale")){
		CfgCompany editCfgCompany=new CfgCompany();
		editCfgCompany.setCfgCompanyId(cfgCompany.getCfgCompanyId());
		editCfgCompany.setRate(cfgCompany.getRate());
		editCfgCompany.setGprs(cfgCompany.getGprs());
		editCfgCompany.setScale(cfgCompany.getScale());
		editCfgCompany.setScaleAnchor(cfgCompany.getScaleAnchor());
		editCfgCompany.setOpen(cfgCompany.getOpen());
		act_flag=cfgCompanyDao.edit(editCfgCompany);
		toPage="cfgCompanyScaleList.jsp";
		//对应更新扣费缓存中的数据
		//azul.CacheCompany.initCompany();
		//azul.CacheFee.initFee();
	}
	else if(op.equals("editcfgSellRole")){
		String role=azul.JspUtil.getStr(request.getParameter("role"),"");
		String sys_user_id=azul.JspUtil.getStr(request.getParameter("sys_user_id"),"");
		int flag=cfgCompanyDao.executeUpdate("update cfg_company set cfg_sell_id="+sys_user_id +" where cfg_company_id in ("+role+")");
	    if(flag>0){
	    	act_flag="1";
	    	msg="修改信息成功";
	    }
		toPage="cfgSellRoleList.jsp";
	}
	else{ 
	    System.err.println("cfgCompany op action is not right,op:"+op);
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