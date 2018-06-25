<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
DebuUtil.log("packetlist");
PacketDao pDao=new PacketDao();
String keyWord = azul.JspUtil.getStr(request.getParameter("keyWord"),"");

GamepackDao gpDao=new GamepackDao();
java.util.Map<String,String> gameMap = new HashMap<String, String>();
gameMap=gpDao.getSelectMap("select packet_no,concat(game_name,'(',packet_no,')') from gamepack where 1=1");

String pageSql="select packet.id,file_name,packet_id,gamepack.game_name,"+
				"pre_version,gamepack.version,d_url from packet,"+
				"gamepack where packet.packet_no=gamepack.packet_no";
int pagesize=30;//每页记录数
int pageno = 0;
int recordcount = 0;
List list = new ArrayList();
if(!keyWord.equals("")){
	pageSql+=" and packet.packet_no ='"+keyWord+"'";
	pageSql+=" and gamepack.sole = '1'";
	pageSql+=" order by id desc";


	pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
	recordcount=pDao.getRecordCount(pageSql);//得到记录总数
	DebuUtil.log("recordcount="+recordcount);
	if(recordcount>0)
		list = pDao.getList(pageno,pagesize,pageSql);
	DebuUtil.log("list="+list.size());
}

%>
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<td width="90%"><select name="keyWord" id="keyWord">
						<option value="">请选择</option>
				</select>&nbsp;&nbsp;
<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a></td>
<td width="10%" align="right"><a href="#" onclick="addAct()"><img src="../_js/ico/btn_add.gif" border="0" alt="增加" align="absmiddle"/></a></td>
</tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td><input type="checkbox" name="maincb" id="maincb" value="1" onchange="onmaincbchag(this)">全选</td>
<td>序号</td>
<td>渠道包号</td>
<td>渠道名</td>
<td>商务</td>
<td>游戏名</td>
<td>当前版本</td>
<td>最新版本</td>
<td>文件名</td>
<td>下载链接</td>
<td>操作</td></tr>
<%
	for(int i=0;i<list.size();i++){
           PacketMana packetMana=(PacketMana)list.get(i);
           packetMana.getName();
%>
<tr name="tr" id="tr">
<td><input type="checkbox" name="cb" id="cb"></td>
<td><%=packetMana.getId()%></td>
<td><%=packetMana.getPacketId()%></td>
<td><%=packetMana.getChannleName()%></td>
<td><%=packetMana.getBusinessName()%></td>
<td><%=packetMana.getGameName()%></td>
<td><%=packetMana.getPreVersion()%></td>
<td><%=packetMana.getNewVersion()%></td>
<td><%=packetMana.getFileName()%></td>
<td><a href="<%=packetMana.getDUrl()%>"><%=packetMana.getDUrl()%></a></td>
<td><a href="#" onclick="editAct('<%=packetMana.getId()%>')"><img src="../_js/ico/btn_edit.gif" border="0" alt="修改"/></a></td>
</tr>
<%
}
%>

</table>
</form>
<table>
<tr>
<td align="center" colspan="9"><button onclick="onPackConmmand(this)">打包</button></td>
</tr>
</table>
<%
String linkParam="";
out.print(azul.JspUtil.getPageCode(pageno,pagesize,recordcount,request,linkParam));
%>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>

function addAct(){
    window.location="packetEdit.jsp";
}
function editAct(id){
	window.location="packetEdit.jsp?pageno=<%=pageno%>&id="+id;
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
	document.mainForm.action="packetList.jsp";
	document.mainForm.submit();
}

function onmaincbchag(obj){
	
	var maincb = document.getElementById("maincb");
	var cbs = document.getElementsByName("cb");
	if(maincb.checked == true){
		for(var i=0;i<cbs.length;i++){
			cbs[i].checked=true;
		}
	}else{
		for(var i=0;i<cbs.length;i++){
			cbs[i].checked=false;
		}
	}
	
}



function onPackConmmand(obj){
	var role_key=getCheck("cb");
	if(role_key==""){
	    error("请选择包体");
		return;
	}
	var packetIds = "";
	var fileName = "";
	//var trs = $("tr[name='tr']");
	var trs = $("#TableColor").find("tr");
	var j = 0;
	for(var i = 1;i<trs.length;i++){
		if($(trs[i]).find("td").eq(0).find("input:checkbox").eq(0).attr("checked")=="checked"){
			j+=1;
			packetIds += ($(trs[i]).find("td").eq(2).html()+",");
			fileName = $(trs[i]).find("td").eq(8).html();
		}
	}
	console.info("packetIds:"+packetIds);
	console.info("fileName:"+fileName);

	alert("打包进行中，大约"+j*2+"分钟后完成。。。");
	$.ajaxSettings.async = false;
	
    $.post(
		"../PreRepackServlet", 
		{
			fileName : fileName,
			packetIds : packetIds
			
		}, function(data) {
			console.info(data);
			if(data.result=="success"){
				location.reload();
			}
		}, "json");  
	//var jsonStr = '{"packetId":"'+packetId+'",'+' "fileName":"'+fileName+'"}';
	//jsonStr = eval('(' + jsonStr + ')');
}


</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
<script type="text/javascript">
	
<%out.println(azul.JspUtil.initSelectSortByValuePinyin("keyWord",gameMap, keyWord));%>
	
</script>