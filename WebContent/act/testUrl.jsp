<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<title>后台管理系统</title>
</head>
<body>
<form name="mainForm" method="post">
<p><br>
  mo
    <input name="mo" id="mo" value="http://119.147.23.178:8080/sms/beiqingbao/beiqingbao.jsp?linkid=" size="70" /> 
  </p>
<p>mr
  <input name="mr" id="mr" value="http://119.147.23.178:8080/sms/beiqingbao/beiqingbao.jsp?linkid=" size="70" />
</p>
<p>linkid
    <input name="linkid" id="linkid" value="12031112799136270105" />
</p>
<p>phone
  <input name="phone" id="phone" value=15173034460 />
  </p>
<p>message
  <input name="message" id="message" value="xx" />
  </p>
<p>spnum
  <input name="spnum" id="spnum" value="1066889999" />
</p>
<p><input type="button" value="提交" onClick="ok()"/></p><br>
</form>
<p>
  <script>
function ok(){
   mainForm.action="reSend.jsp?send=1";
   mainForm.submit();
}
</script>
  <%
  System.out.println("aaaaaaaaaaaaa");
  common.tool.ImportTxt.insertDb();
  System.out.println("bbbbbbbbbbbbbb");
String send=azul.JspUtil.getStr(request.getParameter("send"),"");
if(!"1".equals(send)){
	return;
}
/*
String mo=azul.JspUtil.getStr(request.getParameter("mo"),"");
String mr=azul.JspUtil.getStr(request.getParameter("mr"),"");
String linkid=azul.JspUtil.getStr(request.getParameter("linkid"),"");
String phone=azul.JspUtil.getStr(request.getParameter("phone"),"");
String message=azul.JspUtil.getStr(request.getParameter("message"),"");
String spnum=azul.JspUtil.getStr(request.getParameter("spnum"),"");

StringBuffer sbMo=new StringBuffer();
sbMo.append(mo).append(linkid).append("&destaddr=&phone=").append(phone)
.append("&message=").append(message).append("&spnum=").append(spnum);
out.println(sbMo.toString());
azul.KeyTool.send(sbMo.toString());
//暂停几秒
java.util.Random random=new java.util.Random();
Thread.sleep (random.nextInt(5)*random.nextInt(5)*1000+2000) ;
StringBuffer sbMr=new StringBuffer();
sbMr.append(mr).append(linkid).append("&serviceid=delivrd&destaddr=0003&phone=").append(phone)
.append("&message=").append(message).append("&spnum=").append(spnum);
out.println(sbMr.toString());
azul.KeyTool.send(sbMr.toString());

*/
%>
http://119.147.23.178:8080/<br>
http://localhost:8080/sp/<br>
  高谷<br>
  <input name="message2" value="sms/gaogu/gaoguMo.jsp?LinkId=12345678911-Phone=13751180190-Msg=8" size="120" /><input type="button" value="提交" onClick="ok()"/>
  <input name="message23" value="sms/gaogu/gaoguMr.jsp?LinkId=12345678911-Phone=13751180190-SpNumber=8-Status=DelivRD" size="120" /><input type="button" value="提交" onClick="ok()"/>
</p>
<p>龙运通<br>
  <input name="message22" value="sms/longyuntong/longyuntongMo.jsp?linkid=12345678922-phone=13590379554-desttermid=1062600882-cmdword=CKLYtw-content=1GG" size="120" />
  <input type="button" value="提交" onClick="ok()"/>
  <input name="message222" value="sms/longyuntong/longyuntongMr.jsp?linkid=12345678922-status=0&content=1GG-cmdword=CKLYtw-desttermid=1062600882" size="120" />
  <input type="button" value="提交" onClick="ok()"/>
</p>
</body>
</html>