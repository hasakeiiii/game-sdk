<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
DebuUtil.log("cfgUserPayList");
 

PayDao  payDao=new PayDao();
List<Pay> list = new ArrayList<Pay>();
int pagesize=30;//每页记录数
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int recordcount = 0;
String sch = azul.JspUtil.getStr(request.getParameter("sch"),"");
String status =azul.JspUtil.getStr(request.getParameter("status"),"");
String keyWord=azul.JspUtil.getStr(request.getParameter("keyWord"),"");

Map<String,String> statusMap=new HashMap<String,String>();
statusMap.put("suc", "成功");
statusMap.put("fail", "失败");
statusMap.put("all", "全部");


String pageSql="select * from pay where 1=1";
if(!keyWord.equals("")){
	   pageSql+=" and username ='"+keyWord+"'";
	   
	   recordcount = payDao.getRecordCount(pageSql);
	   pagesize = recordcount;
}
if(!status.isEmpty()){
	if(status.equals("suc"))
		pageSql +=" and state=1";
	if(status.equals("fail")) 
		pageSql +=" and state!=1";
	if(status.equals("all")) 
		;
}

if(sch.equals("sch")){
	list = payDao.getList(pageno,pagesize,pageSql);
}
DebuUtil.log("pageSql="+pageSql);//

//int recordcount=payDao.getRecordCount(pageSql);//得到记录总数
//List<Pay> list=payDao.getList(pageno,pagesize,pageSql);
DebuUtil.log("list"+list.size());
AppDao appDao = new AppDao();
String strSQL = "select * from app";
ArrayList applist = appDao.getList(strSQL);

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
<td width="50%">查询账号&nbsp;&nbsp;<input name="keyWord" value="<%=keyWord%>" />&nbsp;&nbsp;</td>
<td width="30%">订单状态&nbsp; 
				 <select name="status" onchange=statuschg(this) id="status">
				 <option value="">请选择</option>
				</select>&nbsp; 
<td><a href="#" onclick="goSearch()"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a></td>
<td width="10%" align="right"><br /><br /></td>
</tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td>CP订单号</td>
<td>订单号</td>
<td>帐号</td>
<td>手机IMEI</td>
<td>APK包号</td>
<td>游戏名称</td>
<td>付费方式</td>
<td>付费状态</td>
<td>付费金额</td>
<td>付费时间</td>
<td>CP通知</td>
<td>补发通知</td>
<%
for(int i=0;i<list.size();i++){
	Pay pay=(Pay)list.get(i);
    App app = new App();
    app.setName("");
    String payType = "";
    String payTime = "";
    String payAccount = "";
    String payState = "";
    String CPState = "";
    
    //if(pay.getState() == 1)
    {
	    for(int j = 0; j < applist.size(); j ++)
	    {
	    	App tApp=(App)applist.get(j);
	    	if(tApp.no.equals(pay.getGameId()))
	    	{
	    		app.setName(tApp.getName());
	    	}
	    }
	    if(pay.getPayType().equals(Pay.AliPayType))
	    {
	    	payType="支付宝";
	    }
	    else if(pay.getPayType().equals(Pay.YeePayType))
	    {
	    	payType="易宝";
	    }
	    else if(pay.getPayType().equals(Pay.MmPayType))
	    {
	    	payType="移动话费";
	    }
	    else if(pay.getPayType().equals(Pay.TenPayType))
	    {
	    	payType="财付通";
	    }
	    else if(pay.getPayType().equals(Pay.UnionPayType))
	    {
	    	payType="银联";
	    }
            else if(pay.getPayType().equals(Pay.MzPayType))
	    {
	    	payType="拇指币";
	    }
	    
	    if(pay.getState() == 1)
	    {
	    	payState="成功";
	    }
	    else
	    {
	    	payState="失败";
	    }
	    
	    if(pay.getNotisfy() == 1)
	    {
	    	CPState="发送";
	    }
	    else
	    {
	    	CPState="未发送";
	    }
	    
	    payTime = pay.getDate()+" "+pay.getTime();
	    payAccount = String.format("%d元", pay.getAmount()/100); 
%>
<tr>
<td><%=pay.getId()%></td>
<td><%= pay.getCpOrderId()%></td>
<td><%= pay.getPayNo()%></td>
<td><%=pay.getUsername()%></td>
<td><%=pay.getDeviceId()%></td>
<td><%=pay.getPacketId()%></td>
<td><%=app.getName()%></td>
<td><%=payType%></td>
<td><%=payState%></td>
<td><%=payAccount%></td>
<td><%=payTime%></td>
<td><%=CPState%></td>
<%
if((pay.getState() == 1) && (pay.getNotisfy() != 1))
{
%>
<td><a href="#" onclick="editAct('<%=pay.getId()%>')"><img src="../_js/ico/btn_edit.gif" border="0" alt="修改"/></a></td>
<%
}
%>
</tr>
<%
}
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
   
}
function editAct(id){
    //window.location="notisfyCPAction.jsp?id="+id;
	//PayDao  payDao=new PayDao();
	document.mainForm.action="notisfyCPAction.jsp?id="+id;
	document.mainForm.submit();
}
function statuschg(obj){
	document.mainForm.action="cfgUserPayList.jsp";
	document.mainForm.submit();
}
function goSearch(){
    if(document.mainForm.keyWord.value==""){
	     error("请输入查询关键字");
		 document.mainForm.keyWord.focus();
		 return;
	}
	document.mainForm.action="cfgUserPayList.jsp?sch=sch";
	document.mainForm.submit();
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
<script type="text/javascript">
	<%
		out.println(azul.JspUtil.initSelect("status",statusMap,status));
	%>
</script>
