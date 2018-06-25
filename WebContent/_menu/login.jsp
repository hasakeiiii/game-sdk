<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<title>游戏运营管理系统</title>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
	<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="../_js/highcharts.js"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">
function fixHeight() { 
    document.getElementById("leftIfame").height=window.screen.height;
    document.getElementById("rightIfame").height= document.getElementById("leftIfame").height;
    //document.getElementById("leftIfame").height= document.body.offsetHeight - document.getElementById("leftIfame").offsetHeight -document.getElementById("leftIfame").offsetTop+150; 
    //document.getElementById("rightIfame").height= document.getElementById("leftIfame").height; alert(document.body.offsetHeight);alert(document.getElementById("leftIfame").offsetHeight );alert(document.getElementById("leftIfame").offsetTop);alert(document.getElementById("leftIfame").height);
} 
</SCRIPT>
 <BODY onLoad="fixHeight()">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <!--tr>
    <td colspan="3" height="80">
<iframe width="100%" height="80" scrolling="no" frameborder="0" marginheight="0" marginwidth="0" src="frame_top.jsp"></iframe>	</td>
  </tr-->
  <tr>
    <td id="leftTd" width="150" height="100%">
<iframe width="100%" id="leftIfame" name="leftIfame" scrolling="auto" frameborder="0" marginheight="0" marginwidth="0" src="frame_left.jsp"></iframe>	</td>
    <td width="9" align="right" valign="middle" bgcolor="#CCE4EC"><a href="javascript:showMenu()"><img id="menuBar" src="../_js/ico/arrow_left.jpg" border="0"/></a></td>
    <td valign="top" id="rightTd"><iframe width="100%" name="rightIfame" id="rightIfame" frameborder="0" marginheight="0" marginwidth="0" src="frame_right.jsp"></iframe></td>
  </tr>
</table>
<script>
var menuFlag=0;
function showMenu(){
   var elem=document.getElementById("menuBar");
   if(menuFlag==1){
       menuFlag=0;
       elem.src="../_js/ico/arrow_left.jpg";
	   document.getElementById("leftTd").width="158";
       //document.getElementById("rightTd").width="89%";
   }
   else{
       menuFlag=1;
       elem.src="../_js/ico/arrow_right.jpg";
	   document.getElementById("leftTd").width="1";
	   //document.getElementById("rightTd").width="99%";
   }
}
</script>
</body>
</html>