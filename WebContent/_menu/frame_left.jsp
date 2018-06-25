<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="dao.*"%>
<%@ page import="model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.*"%>
<%@ include file="../check.jsp"%>
<%
Userinfo sysUser=(Userinfo)session.getAttribute("sysUser");
String role = sysUser.getRole();
String channel = (String)session.getAttribute("channel_no");
String BSS = (String)session.getAttribute(Userinfo.business);
int uid = 0;
if(sysUser.getId() != null){
  uid = sysUser.getId();
}

String CPPACKETServerUrl = "";
if(ConstValue.PAY_SERVER != 1)
{
    CPPACKETServerUrl = ConstValue.CPPACKETServerUrl;
}
SysMenuMainDao sysMenuMainDao=new SysMenuMainDao();
ArrayList menuList=sysMenuMainDao.getList("select * from sys_menu_main order by sort");

SysMenuSubDao sysMenuSubDao=new SysMenuSubDao();
ArrayList subList=sysMenuSubDao.getList("select * from sys_menu_sub order by sys_menu_main_id,sort");
String mainRoleStr="";
String subRoleStr="";
if(sysUser.getUsername().indexOf("admin")==-1){
    SysRoleMenuDao sysRoleMenuDao = new SysRoleMenuDao();
    SysRoleMenu sysRoleMenu=(SysRoleMenu)sysRoleMenuDao.loadBySql("select * from sys_role_menu where sys_role ='" + sysUser.getRole()+"'");
 	List<Object> sysMenuSub=sysMenuSubDao.getList("select * from sys_menu_sub where display= 0");
	if(sysRoleMenu!=null){
		subRoleStr = sysRoleMenu.getSysMenuSub();
	
		String[] sub2 = subRoleStr.split(",");
		List<String> list2 = new ArrayList<String>();
		
		for(String s : sub2){
			list2.add(s);
		}
		
		
		List<String> list1 = new ArrayList<String>();
		for(Object data : sysMenuSub){
			SysMenuSub d = (SysMenuSub)data;
			list1.add(d.getSysMenuSubId() + "");
		}
		String str = "";
		list2.removeAll(list1);
		for(int i= 0;i<list2.size();i++){	
		str+=(","+list2.get(i)+",");
		/* if(i!=list2.size()-1){
		    str+=(list2.get(i)+",");
			}else{
				str+=list2.get(i);
			} */
			
		}
		System.out.println("-----" +str);
		System.out.println("-----" + list1);
		subRoleStr = str;
		mainRoleStr=","+sysMenuMainDao.getValueStr("select distinct(sys_menu_main_id) from sys_menu_sub where sys_menu_sub_id in ("+sysRoleMenu.getSysMenuSub()+")",",")+",";
	}
}
System.out.println(mainRoleStr+"===="+subRoleStr+"-----");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
<link href="../_js/dtree/dtree.css" rel="stylesheet" type="text/css"/>
<link href="../_css/back.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="../_js/dtree/dtree.js"></script>
<script>
var d = new dTree('d');
d.add(0,-1,"<div style='text-align:center;vertical-align:middle; font-size:14px;'><strong>系统菜单</strong></div>");  //目录
<%
for(int i=0;i<menuList.size();i++){
	SysMenuMain sysMenuMain=(SysMenuMain)menuList.get(i);
	int sys_menu_main_id = sysMenuMain.getSysMenuMainId();
	if(sysUser.getUsername().indexOf("admin")>-1 || mainRoleStr.indexOf(","+sys_menu_main_id+",")>-1){
    %>
	d.add(<%=i+1%>,0,"<strong><%=sysMenuMain.getName()%></strong>");
	<%
	}
	for(int j=0;j<subList.size();j++){
		SysMenuSub sysMenuSub=(SysMenuSub)subList.get(j);
		int sys_menu_sub_id = sysMenuSub.getSysMenuSubId();
		if(sysMenuSub.getSysMenuMainId()!=sys_menu_main_id){
			continue;
		}
		if(sysUser.getUsername().indexOf("admin")>-1 || subRoleStr.indexOf(","+sys_menu_sub_id+",")>-1){
			String title=sysMenuSub.getTitle();
			int color=sysMenuSub.getColor();
			if(1==color){
				title="<span class='tdRed'>"+title+"</span>";
		    }
		    else if(2==color){
		    	title="<span class='tdOrgen'>"+title+"</span>";
		    }
		    else if(3==color){
		    	title="<span class='tdBlue'>"+title+"</span>";
		    }
		    else if(4==color){
		    	title="<span class='tdGreen'>"+title+"</span>";
		    }
			if(sysUser.getUsername().indexOf("admin")>-1 && sysMenuSub.getDisplay()==0){
				title="<span class='tdRed'>"+sysMenuSub.getTitle()+"</span>";
				%>
				d.add(<%=i+100+j%>,<%=i+1%>,"&nbsp;<%=title%>","");
				<%	
			}
			else{
			    if(role.equals(Userinfo.business) && (sysMenuSub.getLinks().contains("cfgGameDataReq") || sysMenuSub.getLinks().contains("DataQuery") || sysMenuSub.getLinks().contains("ChannelQuery") || sysMenuSub.getLinks().contains("DataReportFormReq") || sysMenuSub.getLinks().contains("cfgPayTypeList") || sysMenuSub.getLinks().contains("UserLevelList")))
			    {
				%>
				d.add(<%=i+100+j%>,<%=i+1%>,"&nbsp;<%=title%>","javascript:linkToPage('../<%=sysMenuSub.getLinks()%>?BSS_NO=<%=BSS%>')");
				<%	
				}
				else if(sysMenuSub.getLinks().contains("cfgCPSDataList_SA") || sysMenuSub.getLinks().contains("cfgCPSDataList_T") || sysMenuSub.getLinks().contains("packetDownload") || sysMenuSub.getLinks().contains("cfgCPADataList_T")){
				%>
				d.add(<%=i+100+j%>,<%=i+1%>,"&nbsp;<%=title%>","javascript:linkToPage('../<%=sysMenuSub.getLinks()%>?channel_no=<%=channel%>')");
				<%	
				}
				else if(sysMenuSub.getLinks().contains("cfgCPApkList")){
				%>
				d.add(<%=i+100+j%>,<%=i+1%>,"&nbsp;<%=title%>","javascript:linkToPage('../<%=sysMenuSub.getLinks()%>?uid=<%=uid%>')");
				<%	
				}
				else
				{
				%>
				d.add(<%=i+100+j%>,<%=i+1%>,"&nbsp;<%=title%>","javascript:linkToPage('../<%=sysMenuSub.getLinks()%>')");
				<%
				}
			}
		}
	}
}
if(sysUser.getUsername()!=null && sysUser.getUsername().indexOf("admin")>-1){
%>
d.add(9001,0,"<strong>系统管理</strong>");
<%
}
%>
d.add(10000,0,"&nbsp;&nbsp;<strong><a href='quit.jsp'>退出</a></strong>");



<%-- var index = 9002;
var groupindex = 902;
var CPUrl = "<%=CPPACKETServerUrl%>";

var jsrole = "<%=role%>";//'${sysUser.role}';
var admin="<%=Userinfo.admin%>";//'${sysUser.admin}';//"admin";//超级管理员权限
var adminOffline="<%=Userinfo.adminOffline%>";//'${sysUser.adminOffline}';//"admin_offline";//超级管理员单机
var adminOnline="<%=Userinfo.adminOnline%>";//'${sysUser.adminOnline}';//"admin_online";//超级管理员网游
var operationOnline="<%=Userinfo.operationOnline%>";//'${sysUser.operationOnline}';//"operation_online";//网游运营
var operationOffline="<%=Userinfo.operationOffline%>";//'${sysUser.operationOffline}';//"operation_offline";//单机运营
var business="<%=Userinfo.business%>";//'${sysUser.business}';//"BSS";//商务角色
var ceo="<%=Userinfo.ceo%>";//'${sysUser.ceo}';//"GUEST";//总经办角色
var customService = "<%=Userinfo.customService%>";//'${sysUser.customService}';//"C_S";//客服
var finance = "<%=Userinfo.finance%>";//'${sysUser.finance}';//财务部;



if((jsrole == admin) || (jsrole == adminOffline) || (jsrole == operationOffline))//运营和系统管理角色 
{	
d.add(groupindex,0,"<strong>管理中心</strong>");
d.add(index++,groupindex,"&nbsp;&nbsp;游戏管理","javascript:linkToPage('../param/cfgGameList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;渠道管理","javascript:linkToPage('../param/cfgChannelList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;商务管理","javascript:linkToPage('../param/BusinesserList.jsp')");



if(!(jsrole == operationOffline))
{	  
    d.add(index++,groupindex,"&nbsp;&nbsp;后台账号管理","javascript:linkToPage('../param/OperaterList.jsp')");
}	

groupindex ++;
d.add(groupindex,0,"<strong>综合数据</strong>");
d.add(index++,groupindex,"&nbsp;&nbsp;数据查询1111","javascript:linkToPage('../param/DataQuery.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;数据查询","javascript:linkToPage('../param/cfgGameDataReq.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;数据查询(新)","javascript:linkToPage('../param/DataQuery.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;渠道数据","javascript:linkToPage('../param/ChannelQuery.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;数据报表","javascript:linkToPage('../param/DataReportFormReq.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;充值类型明细","javascript:linkToPage('../param/cfgPayTypeList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;单机付费分析","javascript:linkToPage('../param/AnalysisReportForm.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;单机计费点数据","javascript:linkToPage('../param/feeDataReportForm.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;运营商计费占比","javascript:linkToPage('../param/ThreeProvinceMonth.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;单机渠道数据","javascript:linkToPage('../param/cfgGameDataReqCh.jsp')");

groupindex ++;
d.add(groupindex,0,"<strong>新增分析</strong>");
d.add(index++,groupindex,"&nbsp;&nbsp;单机周付费","javascript:linkToPage('../param/feeWeekAnalysis.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;单机新增付费","javascript:linkToPage('../param/newUserPayAnalysis.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;三网分省新增","javascript:linkToPage('../param/cfgThreeProvince.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;单机省份新增","javascript:linkToPage('../param/ProvinceDataList.jsp')");


groupindex ++;
d.add(groupindex,0,"<strong>运营配置</strong>");
d.add(index++,groupindex,"&nbsp;&nbsp;渠道合作管理","javascript:linkToPage('../param/cfgCooperationList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;mm计费点管理","javascript:linkToPage('../param/mmPayDataList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;调整数据","javascript:linkToPage('../param/EditCount.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;白名单设置","javascript:linkToPage('../param/WhiteList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;计费点限额","javascript:linkToPage('../param/cooperationPayList.jsp')");



groupindex ++;
d.add(groupindex,0,"<strong>其它查询</strong>");
d.add(index++,groupindex,"&nbsp;&nbsp;mm计费明细","javascript:linkToPage('../param/mpayDataReq.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;查询IMEI计费","javascript:linkToPage('../param/cfgIMEI.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;游戏充值详情","javascript:linkToPage('../param/cfgPayInfo.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;用户级别分析","javascript:linkToPage('../param/UserLevelList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;帐号充值查询","javascript:linkToPage('../param/cfgUserPayList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;找回密码","javascript:linkToPage('../param/FindPassword.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;MM订单查询","javascript:linkToPage('../param/MmpaySearch.jsp')");

if((jsrole == adminOffline) || (jsrole == admin))//
{
groupindex ++;
d.add(groupindex,0,"<strong>计费后台</strong>");
d.add(index++,groupindex,"&nbsp;&nbsp;计费游戏管理","javascript:linkToPage('../param/payListList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;渠道合作","javascript:linkToPage('../param/CooperationMoneyList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;渠道限额","javascript:linkToPage('../param/channelMoneyList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;单机计费配置","javascript:linkToPage('../param/SysParamEdit.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;计费点流水","javascript:linkToPage('../param/payListDataReq.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;计费点金额报表","javascript:linkToPage('../param/payDataReq.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;计费游戏金额","javascript:linkToPage('../param/appPay.jsp')");
}	
}


if( (jsrole == adminOnline)  || (jsrole == operationOnline) )//网游管理员角色
{	
d.add(groupindex,0,"<strong>管理中心</strong>");
d.add(index++,groupindex,"&nbsp;&nbsp;游戏管理","javascript:linkToPage('../param/cfgGameList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;渠道管理","javascript:linkToPage('../param/cfgChannelList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;商务管理","javascript:linkToPage('../param/BusinesserList.jsp')");
if(!(jsrole == operationOnline))
{	 
    d.add(index++,groupindex,"&nbsp;&nbsp;后台账号管理","javascript:linkToPage('../param/OperaterList.jsp')");
}	


d.add(index++,groupindex,"&nbsp;&nbsp;CP管理","javascript:linkToPage('../param/cfgCpManageList.jsp')");


groupindex ++;

d.add(groupindex,0,"<strong>运营配置</strong>");
d.add(index++,groupindex,"&nbsp;&nbsp;渠道合作管理","javascript:linkToPage('../param/cfgCooperationList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;下发专属币","javascript:linkToPage('../param/SendOnly.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;二级页面管理","javascript:linkToPage('../param/cfgLinkManageList.jsp')");
//d.add(index++,groupindex,"&nbsp;&nbsp;游戏母包管理","javascript:linkToPage('../param/gamePacManaList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;游戏母包审核","javascript:linkToPage('../param/cfgCPApkRunList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;渠道打包管理","javascript:linkToPage('../param/packetList.jsp')");



groupindex ++;
d.add(groupindex,0,"<strong>综合数据</strong>");
d.add(index++,groupindex,"&nbsp;&nbsp;数据查询","javascript:linkToPage('../param/cfgGameDataReq.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;数据报表","javascript:linkToPage('../param/DataReportFormReq.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;网游数据详情","javascript:linkToPage('../param/cfgGameDataDetails.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;充值类型明细","javascript:linkToPage('../param/cfgPayTypeList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;渠道充值详情","javascript:linkToPage('../param/cfgPayInfo.jsp')");

groupindex ++;
d.add(groupindex,0,"<strong>投放时间段分析</strong>");
d.add(index++,groupindex,"&nbsp;&nbsp;分渠道分时间段分析报表","javascript:linkToPage('../param/cfGameTime.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;在线时间","javascript:linkToPage('../param/cfgOnTime.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;用户等级","javascript:linkToPage('../param/cfgUserLevel.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;ltv报表","javascript:linkToPage('../param/cfgLtv.jsp')");

groupindex ++;
d.add(groupindex,0,"<strong>其它查询</strong>");
d.add(index++,groupindex,"&nbsp;&nbsp;用户级别分析","javascript:linkToPage('../param/UserLevelList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;帐号充值查询","javascript:linkToPage('../param/cfgUserPayList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;调整数据","javascript:linkToPage('../param/EditCount.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;找回密码","javascript:linkToPage('../param/FindPassword.jsp')");
}	

if(jsrole == business)//商务角色
{	
d.add(index++,0,"&nbsp;&nbsp;数据查询","javascript:linkToPage('../param/cfgGameDataReq.jsp?BSS_NO=<%=BSS%>')");
d.add(index++,0,"&nbsp;&nbsp;数据查询(新)","javascript:linkToPage('../param/DataQuery.jsp?BSS_NO=<%=BSS%>')");
d.add(index++,0,"&nbsp;&nbsp;渠道数据","javascript:linkToPage('../param/ChannelQuery.jsp?BSS_NO=<%=BSS%>')");
d.add(index++,0,"&nbsp;&nbsp;数据报表","javascript:linkToPage('../param/DataReportFormReq.jsp?BSS_NO=<%=BSS%>')");
d.add(index++,0,"&nbsp;&nbsp;充值类型明细","javascript:linkToPage('../param/cfgPayTypeList.jsp?BSS_NO=<%=BSS%>')");
d.add(index++,0,"&nbsp;&nbsp;用户级别分析","javascript:linkToPage('../param/UserLevelList.jsp?BSS_NO=<%=BSS%>')");
d.add(index++,0,"&nbsp;&nbsp;调整数据","javascript:linkToPage('../param/EditCount.jsp')");
d.add(index++,0,"&nbsp;&nbsp;二级页面管理","javascript:linkToPage('../param/cfgLinkManageList.jsp')");
}	

if((jsrole == ceo) || (jsrole == finance))//总经办财务角色
{	
d.add(index++,0,"&nbsp;&nbsp;数据查询","javascript:linkToPage('../param/cfgGameDataReq.jsp')");
d.add(index++,0,"&nbsp;&nbsp;数据报表","javascript:linkToPage('../param/DataReportFormReq.jsp')");
d.add(index++,0,"&nbsp;&nbsp;充值类型明细","javascript:linkToPage('../param/cfgPayTypeList.jsp')");
}	


if(jsrole == customService)//客服
{	
d.add(index++,0,"&nbsp;&nbsp;帐号充值查询","javascript:linkToPage('../param/cfgUserPayList.jsp')");
d.add(index++,0,"&nbsp;&nbsp;找回密码","javascript:linkToPage('../param/FindPassword.jsp')");

}	


if(jsrole == "CPS")
{	


              d.add(index++,0,"&nbsp;&nbsp;游戏数据查询","javascript:linkToPage('../param/cfgCPSDataList_SA.jsp?channel_no=<%=channel%>')");
               d.add(index++,0,"&nbsp;&nbsp;游戏数据查询","javascript:linkToPage('../param/cfgCPSDataList_T.jsp?channel_no=<%=channel%>')");
               d.add(index++,0,"&nbsp;&nbsp;游戏包下载","javascript:linkToPage('../param/packetDownload.jsp?channel_no=<%=channel%>')");
               
}	


if(jsrole == "CPA")
{	
d.add(index++,0,"&nbsp;&nbsp;游戏数据查询","javascript:linkToPage('../param/cfgCPADataList_T.jsp?channel_no=<%=channel%>')");
d.add(index++,0,"&nbsp;&nbsp;游戏包下载","javascript:linkToPage('../param/packetDownload.jsp?channel_no=<%=channel%>')");
}	


if(jsrole == "CPA_R")
{	
d.add(index++,0,"&nbsp;&nbsp;游戏数据查询","javascript:linkToPage('../param/cfgCPA_RDataList_T.jsp?channel_no=<%=channel%>')");
d.add(index++,0,"&nbsp;&nbsp;游戏包下载","javascript:linkToPage('../param/packetDownload.jsp?channel_no=<%=channel%>')");
}	


if(jsrole == "SA")
{	
d.add(index++,0,"&nbsp;&nbsp;数据查询","javascript:linkToPage('../param/cfgGameDataReq.jsp')");
d.add(index++,0, "&nbsp;&nbsp;mm计费明细","javascript:linkToPage('../param/mpayDataReq.jsp')");
}	


if(jsrole == "CP")

{	
d.add(index++,0,"&nbsp;&nbsp;游戏管理","javascript:linkToPage('../param/cfgCPGameList.jsp')");
//d.add(index++,0,"&nbsp;&nbsp;数据查询","javascript:linkToPage('../param/cfgCPGameDataReq.jsp')");
d.add(index++,0,"&nbsp;&nbsp;充值类型明细","javascript:linkToPage('../param/cfgCPPayTypeList.jsp')");
d.add(index++,0,"&nbsp;&nbsp;cp母包管理","javascript:linkToPage('"+CPUrl+"/param/cfgCPApkList.jsp?uid=<%=uid%>')");
//d.add(index++,0,"&nbsp;&nbsp;cp母包管理","javascript:linkToPage('http://119.29.15.79/sdk/param/cfgCPApkList.jsp?uid=542')");

}

groupindex ++;
d.add(groupindex,0,"<strong>系统管理</strong>");
d.add(index++,groupindex,"&nbsp;&nbsp;系统权限","javascript:linkToPage('../_menu/sysRoleList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;菜单管理","javascript:linkToPage('../_menu/sysMenuMainList.jsp')");
d.add(index++,groupindex,"&nbsp;&nbsp;角色菜单","javascript:linkToPage('../_menu/sysRoleMenuList.jsp')");

d.add(index++,0,"&nbsp;&nbsp;<strong><a href='quit.jsp'>退出</a></strong>"); --%>
</script>
</head>
<body bgcolor="#ddeef2" topmargin="0">
<script>document.write(d);</script>
<script>
var treeFlag=1;
function showTree(){
   var elem=document.getElementById("treeBar");
   if(treeFlag==0){
       treeFlag=1;
       elem.src="../_js/dtree/img/nolines_plus.gif";
	   d.closeAll();
   }
   else{
       treeFlag=0;
       elem.src="../_js/dtree/img/nolines_minus.gif";
	   d.openAll();   
   }
}
function linkToPage(url){
   top.rightIfame.location=url;
}
</script>
</body>
</html>