<%@page import="sdkReq.download"%>
<%@page import="util.ConstValue"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*"%>
<%@ page import="model.*"%>
<%@ page import="common.*"%>
<%@ page import="java.util.*"%>
 <jsp:include page="../check.jsp?check=cfgCPApkEdit.jsp" flush="true" /> 
<%
	String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id=azul.JspUtil.getInt(request.getParameter("id"),0);
int uid=azul.JspUtil.getInt(request.getParameter("uid"),0);	
// Userinfo sysUser=(Userinfo)session.getAttribute("sysUser");
Integer ID = uid;

String game_name = "";
String game_id="";//game名称
String file_name="";//包体
String version = "";
String time = "";
String packet_no = "";

AppDao appDao=new AppDao();
java.util.Map<String,String> gameMap = new HashMap<String, String>();
gameMap=appDao.getSelectMap("select no,name from app where cp_no in(select cp_no from cp_manage where login_user = "+ID+")");

if(id!=0){
    pageTitle="编辑信息ID:"+id;
    GamepackDao apkDao = new GamepackDao();
    Gamepack apk = (Gamepack)apkDao.getApk(id);
    game_id = apk.getGameId();
    file_name = apk.getFileName();
    version = apk.getVersion();
    time = apk.getTime();
    packet_no = apk.getPacketNo();
}
else
{
	
}
%>
<html>
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
        		document.mainForm.action="cfgCPApkAction.jsp?op=add&uid=<%=uid%>";
        		document.mainForm.submit();
    		}else{//编辑功能
        		document.mainForm.action="cfgCPApkAction.jsp?uid=<%=uid%>&op=edit&pageno=<%=pageno%>&id=<%=id%>"
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
				<select name="game_id" id="game_id">
						<option value="">请选择</option>
				</select></td>
				<td><span id="warn_sp_name"></span></td>
			</tr>
		
	
			<tr align="center">
				<td width="33%" id="title_version">版本号</td>
				<td width="33%"><input name="version" alt="version"
					value="<%=version%>" maxlength="250" /></td>
				<td><span id="warn_sp_url"></span></td>
			</tr>
			
			<tr align="center">
				<td width="33%" id="title_packet_no">包ID</td>
				<td width="33%"><input name="packet_no" alt="packet_no"
					value="<%=packet_no%>" maxlength="250" /></td>
				<td><span id="warn_sp_url"></span>命名规则：游戏id+包体序号；例：193001、193002</td>
			</tr>

			
		<%--     <tr align="center">
				<td width="33%">包体</td>
				<td width="33%"><input type = "file" id = "file_name" name = "file_name" value="<%=file_name%>" /></td>
				<td><span id="warn_sp_code"><input type = "button" id = "upload" name = "upload" value = "上传"/></span>
				</td>
			</tr>
			 --%>
	
           <tr align="center">
				<td width="33%">包体</td>
				<td width="33%"><input type="file" name="file_name" id="file_name"  multiple="multiple" onchange="fileSelected();" value="<%=file_name%>"/>
				  <div id="fileName"></div> <div id="fileSize"></div>  <div id="progressNumber"> </div>
				</td>
				<td><span id="warn_sp_code"><input type = "button" onclick="uploadFile()" value = "上传"/></span>
				</td>
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
        
        
        
              
            function fileSelected() {
           
            var file = document.getElementById('file_name').files[0];

            if (file) {

                var fileSize = 0;

                if (file.size > 1024 * 1024)

                    fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';

                else

                    fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';

 

                document.getElementById('fileName').innerHTML = 'Name: ' + file.name;

                document.getElementById('fileSize').innerHTML = 'Size: ' + fileSize;

                document.getElementById('fileType').innerHTML = 'Type: ' + file.type;

            }
           

        }

 

        function uploadFile() {
         var myfile = $("#file_name").val();
    	    if(myfile != ""){
    	    var strExtension = myfile.substr(myfile.lastIndexOf('.') + 1);  
            if (strExtension != 'apk'&& strExtension != 'zip') { 
                alert("请选择apk文件");  
                return;  
            }  

            var fd = new FormData();

            fd.append("file_name", document.getElementById('file_name').files[0]);
            
            var xhr;
			if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
  				xhr=new XMLHttpRequest();
  			} else {// code for IE6, IE5
  				xhr=new ActiveXObject("Microsoft.XMLHTTP");
  			}
	

            xhr.upload.addEventListener("progress", uploadProgress, false);

            xhr.addEventListener("load", uploadComplete, false);

            xhr.addEventListener("error", uploadFailed, false);

            xhr.addEventListener("abort", uploadCanceled, false);

            xhr.open("POST", "../servlet/Upload");

            xhr.send(fd);
            
            xhr.onreadystatechange=function()  {
			  if (xhr.readyState==4 && xhr.status==200) {
			    	alert("上传成功");
			    }
			  }
			   }else{
    			 alert("请选择要上传的文件！");  
    			 return false;  
    			}

        }

 

        function uploadProgress(evt) {

            if (evt.lengthComputable) {

                var percentComplete = Math.round(evt.loaded * 100 / evt.total);

                document.getElementById('progressNumber').innerHTML = percentComplete.toString() + '%';

            }

            else {

                document.getElementById('progressNumber').innerHTML = 'unable to compute';

            }

        }

 

        function uploadComplete(evt) {

            /* This event is raised when the server send back a response */

            //alert(evt.target.responseText);

        }

 

        function uploadFailed(evt) {

            alert("There was an error attempting to upload the file.");

        }

 

        function uploadCanceled(evt) {

            alert("The upload has been canceled by the user or the browser dropped the connection.");

        }
    </script>