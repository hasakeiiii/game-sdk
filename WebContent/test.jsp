<%@ page contentType="text/html; charset=utf-8"%>
<%
String cid="";
String msg="";
String sid="";
double rate=0;
String url="";

%>
<form name="mainForm" action="test.jsp">
<p><br>
  sid<input name="sid" value="<%=sid%>" />
</p>
<p>
  msg<input name="msg" value="<%=msg%>" />
</p>
<p><input type="submit" value="提交"/></p><br>
cid=<%=cid%><br>
rate=<%=rate%><br>
url=<%=url%><br>
<% 
azul.JspUtil.p(azul.CacheSp.getParam("1012","9MZC4A","24"));
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