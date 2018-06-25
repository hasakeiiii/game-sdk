<%@page import="sdkReq.download"%>
<%@page import="util.ConstValue"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*"%>
<%@ page import="model.*"%>
<%@ page import="common.*"%>
<%@ page import="java.util.*"%>
<jsp:include page="../check.jsp?check=cfgCPApkRunEdit.jsp" flush="true" />
<%
	String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id=azul.JspUtil.getInt(request.getParameter("id"),0);
Userinfo sysUser=(Userinfo)session.getAttribute("sysUser");
Integer ID = sysUser.getId();

String game_name = "";
String game_id="";//game名称
String file_name="";//包体
String version = "";
String time = "";
String sole =" ";

AppDao appDao=new AppDao();
java.util.Map<String,String> gameMap = new HashMap<String, String>();
gameMap=appDao.getSelectMap("select no,name from app");

if(id!=0){
    pageTitle="编辑信息ID:"+id;
    GamepackDao apkDao = new GamepackDao();
    Gamepack apk = (Gamepack)apkDao.getApk(id);
    game_id = apk.getGameId()();
    file_name = apk.getFileName()();
    version = apk.getVersion()();
    time = apk.getTime();
    sole = apk.getSole().toString();
   
}
else
{
	
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css" />

<style>
#dj {
	display: none;
}
</style>

</head>
<body>
	<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css"
		media="screen" />
	<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
	<script type="text/javascript" src="../_js/Check.js"></script>
	<script type="text/javascript" src="../_js/CheckForm.js"></script>
	<script language="javascript">
		window.onload = function() {
			CheckForm.formBind("mainForm");
		}

		
		function btnSave() {
			if(!CheckForm.checkForm("mainForm"))return;
			
    		if('<%=id%>'=="0"){
        		document.mainForm.action="cfgCPApkRunAction.jsp?op=add";
        		document.mainForm.submit();
    		}else{//编辑功能
        		document.mainForm.action="cfgCPApkRunAction.jsp?op=edit&pageno=<%=pageno%>&id=<%=id%>"
				document.mainForm.submit();
			}
		}
		
	</script>
	<h1><%=pageTitle%></h1>
	<form name="mainForm" id="mainForm" method="post"
		style="margin:0;padding:0">
		<table width="100%" border="0">
			<tr align="center">
				<td width="33%" id="title_game_id">游戏名称</td>
				<td width="33%">
				<select name="game_id" id="game_id" disabled="false">
						<option value="">请选择</option>
				</select></td>
				<td><span id="warn_sp_name"></span></td>
			</tr>
		
	
			<tr align="center">
				<td width="33%" id="title_version">版本号</td>
				<td width="33%"><input name="version" alt="version"
					value="<%=version%>" maxlength="250" disabled="disabled"/></td>
				<td><span id="warn_sp_url"></span></td>
			</tr>

				<tr align="center">
				<td width="33%" id="title_sole">状态</td>
				<td width="33%"><input name="sole" alt="sole"
					value="<%=sole%>" maxlength="250" disabled="disabled"/></td>
				<td><span id="warn_sp_url"></span></td>
			</tr>
		

			<tr align=center>
				<td colspan=3>
				<a href="#" onclick="btnSave()"><img src="../_js/ico/btn_save.gif" border="0" alt="保存"
						align="absmiddle"/> </a>&nbsp;&nbsp;&nbsp; 
				<a href="#"onclick="javascript:history.back()"><img src="../_js/ico/btn_back.gif" border="0" alt="返回"
						align="absmiddle" /> </a>
				</td>
			</tr>
		</table>

	</form>
</body>
</html>

<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/ajaxfileupload.js"></script>

<script type="text/javascript">
<%out.println(azul.JspUtil.initSelect("game_id",gameMap, game_id));%>
</script>



 <script>
   
    	$("#upload").click(function(){
    	
    	var myfile = $("#file_name").val();
    	if(myfile != ""){
//     	ajaxLoading();
    	 var strExtension = myfile.substr(myfile.lastIndexOf('.') + 1);  
         //    if (strExtension != 'jpg' && strExtension != 'gif' && strExtension != 'png' && strExtension != 'txt') { 
            if (strExtension != 'apk'&& strExtension != 'zip') { 
                alert("请选择apk文件");  
                return;  
            }  
            
            $.ajaxFileUpload({
				url:"../servlet/Upload",
				type:"post",
				//对应上传控件的id属性
				fileElementId:"file_name",
				//注意这里返回的数据类型是一个字符串而不是json
				dataType:"text",
				//是否支持跨域传输
				//所谓跨域传输不限定在某个工程中可以上传到另外一个工程中
				secureuri:false,
				//回调函数
				success:function(data){
					alert("上传成功");
				}
			});
	
    	}else{
    			 alert("请选择要上传的文件！");  
    			 return false;  
    			}
    	
    		
    	});

        function back(){
        	window.history.back(-1);
        }
        
        
        
//         	function ajaxLoading(){   
//         	alert('ssssssssssss');
//     	    $.css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");   
//     	    $.html("正在上传，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});   
//     	} 
    </script>