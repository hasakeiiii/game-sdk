<%@ page contentType="text/html; charset=utf-8"%>
<%
String cid="";
String msg="";
String sid="";
String area="";
String rate="";
String url="";
%>
<form name="mainForm" action="test.jsp?ok=1">
<p><br>
  sid<input name="sid" value="<%=sid%>" />
</p>
<p>
  msg<input name="msg" value="<%=msg%>" />
</p>
<p>
  msg<input name="area" value="<%=area%>" />
</p>
<p><input type="submit" value="提交"/></p><br>
cid=<%=cid%><br>
rate=<%=rate%><br>
url=<%=url%><br>
<% 
String ok=azul.JspUtil.getStr(request.getParameter("ok"),"");
if("1".equals(ok)){
	azul.JspUtil.p(azul.CacheSp.getParam("1012","9MZC4A","24"));
}

//out.println(common.DebugUtil.p(azul.CacheSp.cidMap));
//out.println("=========================");
//out.println(common.DebugUtil.p(azul.CacheSp.cidMap));
//out.println("=========================");
//out.println(common.DebugUtil.p(azul.CacheSp.cidMap));
//azul.JspUtil.p(azul.CacheSp.paramMap);
//azul.JspUtil.p("---------------");
//azul.JspUtil.p(azul.CacheSp.codeMap);
%>
</form>
<script>
</script>