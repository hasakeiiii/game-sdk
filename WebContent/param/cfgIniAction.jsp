<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check_role=admin" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
	String toPage="cfgIni.jsp";
String act_flag="-1";
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
if(op.equals("feeCut")){ 
	String feeCut=azul.JspUtil.getStr(request.getParameter("feeCut"),"");
	boolean success=common.ConfigIni.setValue("feeCut","feeCut",feeCut);
	if(success){
		act_flag="1";
	}
}
else if(op.equals("setArea")){ 
	CfgAreaDao cfgAreaDao=new CfgAreaDao();
	cfgAreaDao.setArea("charge");
	act_flag="1";
}
else if(op.equals("setSmsArea")){ 
	//common.auto.SmsSell smsSell=new common.auto.SmsSell();
	//smsSell.setSmsArea();
	act_flag="1";
}
else if(op.equals("setSms")){ 
	//common.auto.SmsSell smsSell=new common.auto.SmsSell();
	//smsSell.updateFee();
	act_flag="1";
}
else if(op.equals("statCharge")){ 

}
else if(op.equals("feeCid")){ 
	//common.auto.FeeCid feeCid=new common.auto.FeeCid();
	//feeCid.updateFeeCid();
	act_flag="1";
}
else if(op.equals("getWeather")){ 
	//GetWeather getWeather=new GetWeather();
	//getWeather.updateWeather();
	//更新缓存中数据
	//azul.CacheAd.initWeather();
	act_flag="1";
}
else if(op.equals("setCid")){
	CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
	List companyList=cfgCompanyDao.getList();
	SysRoleMenuDao sysRoleMenuDao=new SysRoleMenuDao();
	String role=sysRoleMenuDao.getValue("select sys_menu_sub from sys_role_menu where sys_role='cid'").toString();
	if(!"".equals(role)){
		SysRoleDao sysRoleDao=new SysRoleDao();
		for(int i=0;i<companyList.size();i++){
	CfgCompany cfgCompany=(CfgCompany)companyList.get(i);
	SysRole sysRole=(SysRole)sysRoleDao.loadBySql("select * from sys_role where sys_user_id="+cfgCompany.getSysUserId());
	if(sysRole==null){
		sysRole=new SysRole();
		sysRole.setSysUserId(cfgCompany.getSysUserId());
		sysRole.setSysMenuSub(role);
		act_flag=sysRoleDao.add(sysRole);
	}
	else{
		act_flag=sysRoleDao.executeUpdate("update sys_role set sys_menu_sub='"+role+"' where sys_user_id="+cfgCompany.getSysUserId())+"";
	}
		}
	}
}
else if(op.equals("setSid")){ 
	CfgSellDao cfgSellDao=new CfgSellDao();
	List companyList=cfgSellDao.getList();
	SysRoleMenuDao sysRoleMenuDao=new SysRoleMenuDao();
	String role=sysRoleMenuDao.getValue("select sys_menu_sub from sys_role_menu where sys_role='cid'").toString();
	SysRoleDao sysRoleDao=new SysRoleDao();
	for(int i=0;i<companyList.size();i++){
		CfgCompany cfgCompany=(CfgCompany)companyList.get(i);
		SysRole sysRole=(SysRole)sysRoleDao.loadBySql("select * from sys_role where sys_user_id="+cfgCompany.getSysUserId());
		if(sysRole==null){
	sysRole.setSysUserId(cfgCompany.getSysUserId());
	sysRole.setSysMenuSub(role);
	act_flag=sysRoleDao.add(sysRole);
		}
		else{
	act_flag=sysRoleDao.executeUpdate("update sys_role set sys_menu_sub='"+role+"' where sys_user_id="+cfgCompany.getSysUserId())+"";
		}
	}
}
else if(op.equals("setSql")){ 
	if(azul.BaseDao.AUTO_PRINT){
		azul.BaseDao.AUTO_PRINT=false;
	}
	else{
		azul.BaseDao.AUTO_PRINT=true;
	}	
	act_flag="1";
}
else if(op.equals("noCidTrigger")){ 
	
	act_flag="1";
}
else if(op.equals("monthTable")){ 

}
else if(op.equals("clearCache")){ 
	String key=azul.JspUtil.getStr(request.getParameter("table_name"),"");
	Enumeration e = application.getAttributeNames();
	while (e.hasMoreElements()) {
		String name = e.nextElement().toString();
		if (name.indexOf(key) > -1) {
	System.out.println("clear name:" + name);
	application.setAttribute(name, null);
		}
	}
	act_flag="1";
}
//批量分配厂商收入功能
else if(op.equals("updateFee")){ 
	String updateDate=azul.JspUtil.getStr(request.getParameter("updateDate"),"");
	String fromCid=azul.JspUtil.getStr(request.getParameter("fromCid"),"");
	if("dexe".equals(fromCid)){
		fromCid="";
	}
	String toCid=azul.JspUtil.getStr(request.getParameter("toCid"),"");
	if("dexe".equals(toCid)){
		toCid="";
	}
	int num=azul.JspUtil.getInt(request.getParameter("num"),0);
	int updateType=azul.JspUtil.getInt(request.getParameter("updateType"),0);
	String fromFeeType="";
	String toFeeType="";
	if(updateType==0){
		fromFeeType="A";
		toFeeType="B";
	}
	else if(updateType==1){
		fromFeeType="A";
		toFeeType="C";
	}
	else if(updateType==2){
		fromFeeType="B";
		toFeeType="A";
	}
	else if(updateType==3){
		fromFeeType="B";
		toFeeType="C";
	}
	else if(updateType==4){
		fromFeeType="C";
		toFeeType="B";
	}
	else if(updateType==5){
		fromFeeType="C";
		toFeeType="A";
	}
	else if(updateType==6){
		fromFeeType="A";
		toFeeType="A";
	}
	//System.out.println(updateDate+"_"+fromCid+"_"+toCid+"_"+fromFeeType+"_"+toFeeType+"_"+num);
    //common.auto.MyAuto myAuto=new common.auto.MyAuto();
	//act_flag=myAuto.updateFee(updateDate,fromCid,toCid,fromFeeType,toFeeType,num);
	//toPage="../charge/chargeListPay.jsp?cid="+fromCid+"&toCid="+toCid+"&startDate="+updateDate;
}
else if(op.equals("delete")){ 
    
}
else{ 
    System.err.println("cfgIniAction op action is not right,op:"+op);
}
%>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
if("<%=act_flag%>"=="-1"){
    error("操作失败",callback);
}
else if("<%=act_flag%>"!="1"){
    error("<%=act_flag%>",callback);
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
