
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
CooperationMoneyDao cooperationMoneyDao=new CooperationMoneyDao();
String keyWord=azul.JspUtil.getStr(request.getParameter("keyWord"),"");
String pageSql="select * from cooperation_money where 1=1";
if(!keyWord.equals("")){
	   //pageSql+=" and channel_name like '%"+keyWord+"%'";
}
pageSql +=" order by id desc ";
int pagesize=30;//每页记录数
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int recordcount=cooperationMoneyDao.getRecordCount(pageSql);//得到记录总数
List<CooperationMoney> list=cooperationMoneyDao.getList(pageno,pagesize,pageSql);

AppDao appDao = new AppDao();
String strSQL = "select * from app";
ArrayList applist = appDao.getList(strSQL);
strSQL = "select * from businesser";

BusinesserDao businesseerDao = new BusinesserDao();
ArrayList businesserlist = businesseerDao.getList(strSQL);

java.util.Map pay_markMap=new HashMap<String,String>();
pay_markMap.put("开", "1");
pay_markMap.put("关", "0");

ChannelDao channelDao = new ChannelDao();
 strSQL = "select * from channel";
ArrayList channellist = channelDao.getList(strSQL);
AppPayDao appPayDao = new AppPayDao();
strSQL = "select * from app_pay ";
ArrayList appPayList = appPayDao.getList(strSQL);


if(!keyWord.equals("")){
  	strSQL+=" where name like '%"+keyWord+"%'";
  	channellist = channelDao.getList(strSQL);
  	list = (ArrayList)new ArrayList<Object>();
  	ArrayList tlist = new ArrayList<Object>();
  	for(int j=0;j<channellist.size();j++)
  	{
  		
  		Channel channel=(Channel)channellist.get(j);
  		pageSql="select * from cooperation where 1=1";
  		pageSql+=" and channel_no='"+channel.no+"'";
  		tlist = cooperationMoneyDao.getList(pageSql);
  		list.addAll(tlist);
  	}
  	pagesize = list.size();
  	pageno= 1;
  	recordcount = pagesize;
  	DebuUtil.log("recordcount:"+recordcount);
  }

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
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
<td>渠道名称</td>
<td>渠道ID</td>
<td>计费游戏</td>
<td>APK包号</td>
<td>渠道总限额/日</td>
<td>修改</td>
<td>删除</td>

</tr>
<%
for(int i=0;i<list.size();i++){
	CooperationMoney cooperationMoney=(CooperationMoney)list.get(i);
	String channel_name = "";
	String businesser_name = "";
	String appNo = "";
	String channelNo = "";
	String businessNo = "";
	String appPayName = "";
	
	for(int j=0;j<channellist.size();j++)
	{
		Channel channel=(Channel)channellist.get(j);
		if(channel.no.equals(cooperationMoney.channelNo))
		{
			channel_name = channel.name;
			channelNo = channel.no;
		}
	}
	for(int j=0;j<appPayList.size();j++)
	{
		AppPay appPay=(AppPay)appPayList.get(j);
		if(appPay.getNo().equals(cooperationMoney.appPay))
		{
			appPayName = appPay.getName();
		}
	}

	
%>
<tr>
<td><%=cooperationMoney.getId()%></td>
<td><%=channel_name%></td>
<td><%=channelNo%></td>
<td><%=appPayName%></td>
<td><%=cooperationMoney.getPacketId()%></td>
<td><%=cooperationMoney.getLimitMoney()%></td>
<td><a href="#" onclick="editAct('<%=cooperationMoney.getId()%>')"><img src="../_js/ico/btn_edit.gif" border="0" alt="修改"/></a></td>
<td><a href="#" onclick="deleteAct('<%=cooperationMoney.getId()%>')"><img src="../_js/ico/btn_edit.gif" border="0" alt="删除"/></a></td>
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
    window.location="channelMoneyEdit.jsp?op=edit";
}
function editAct(id){
    window.location="channelMoneyEdit.jsp?pageno=<%=pageno%>&op=edit&id="+id;
}
function infoAct(cfg_sp_id){
    //window.location="cfgSpInfo.jsp?cfg_sp_id="+cfg_sp_id;
}
function deleteAct(id){
    window.location="channelMoneyAction.jsp?op=delete&id="+id;
}
function goSearch(){
    if(document.mainForm.keyWord.value==""){
	     error("请输入查询关键字");
		 document.mainForm.keyWord.focus();
		 return;
	}
	document.mainForm.action="cfgCooperationList.jsp";
	document.mainForm.submit();
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
