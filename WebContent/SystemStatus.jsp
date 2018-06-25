<%@ page contentType="text/html" %>
<%@page import="dombean.*,java.util.*"%>
<%
new JWCConfig();
Runtime runtime=Runtime.getRuntime();
String verb=request.getParameter("verb");
if("gc".equals(verb)) runtime.gc();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>dbconn</title>
</head>
<body>
<form method="post" name="frm">
<input type="hidden" name="verb" value="">
<h1>Server Status</h1>
<UL>
	<li><h2>DatabasePool Status</h2>
		<UL>
			<li><b>max</b>:&nbsp;</li>
			<li><b>opt</b>:&nbsp;</li>
			<li><b>used</b>:&nbsp;</li>
			<li><b>free</b>:&nbsp;</li>
			<li><b>timeout</b>:&nbsp;</li>
		</UL>
		<br>
	</li>
	<li><h2>
			Memory Status 
			<input type="button" value="Run Garbage Collection" onClick="document.frm.verb.value='gc';submit()">
		</h2>
		<UL>
			<li><b>processors</b>:&nbsp;<%=runtime.availableProcessors()%></li>
			<li><b>freeMemory</b>:&nbsp;<%=Math.round(runtime.freeMemory()*100/(1024*1024D))/100D%>M</li>
			<li><b>totalMemory</b>:&nbsp;<%=Math.round(runtime.totalMemory()*100/(1024*1024D))/100D%>M</li>
			<li><b>maxMemory</b>:&nbsp;<%=Math.round(runtime.maxMemory()*100/(1024*1024D))/100D%>M</li>
		</UL>
		<br>
	</li>
	<li>
		  <h2>Session Properties-----------------</h2>
		  <UL>
		<%
			Enumeration enums = session.getAttributeNames();
			while(enums.hasMoreElements())
			{
				String key=(String)enums.nextElement();
				out.println("<li><b>"+key+"</b>:&nbsp;"+session.getAttribute(key)+"</li>");
			}
		%>
		</UL>
		<br>
	</li>
	<li><h2>Servlet Context Status</h2>
		<UL>
			<li><h3>version:&nbsp;<%=application.getMajorVersion()+"."+application.getMinorVersion()%></h3></li>
			<li><h3>initial parameters</h3>
				<UL>
				<%
					enums=application.getInitParameterNames();
					while(enums.hasMoreElements())
					{
						String key=(String)enums.nextElement();
						out.println("<li><b>"+key+"</b>:&nbsp;"+application.getInitParameter(key)+"</li>");
					}
				%>
				</UL>
			</li>
			<li><h3>attributes</h3>
				<UL>
				<%
					enums=application.getAttributeNames();
					while(enums.hasMoreElements())
					{
						String key=(String)enums.nextElement();
						out.println("<li><b>"+key+"</b>:&nbsp;"+application.getAttribute(key).toString()+"</li>");
					}
				%>
				</UL>
			</li>
		</UL>
		<br>
	</li>
	<li><h2>System Properties</h2>
		<UL>
		<%
			enums=System.getProperties().keys();
			while(enums.hasMoreElements())
			{
				String key=(String)enums.nextElement();
				out.println("<li><b>"+key+"</b>:&nbsp;"+System.getProperty(key)+"</li>");
			}
		%>
		</UL>
		<br>
	</li>
</UL>
</form>
</body>
</html>