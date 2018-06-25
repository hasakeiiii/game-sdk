<%@ page contentType="text/html;charset=gb2312"%>
<%
String linkid=request.getParameter("LinkID")==null?"":request.getParameter("LinkID");
String Action=request.getParameter("Action")==null?"":request.getParameter("Action");
if("3".equals(Action)){
	System.out.println("zhangxun.mo 9999-------------------");
	String mobile=request.getParameter("Mobile")==null?"":request.getParameter("Mobile");
		if(azul.KeyTool.mo(linkid,"9999",2,mobile,"","")){
			out.println("0");
		}
		else{
			out.println("ERR");
		}
}
else if("4".equals(Action)){
	System.out.println("zhangxun.mr 9999-------------------");
	String Status=request.getParameter("Status")==null?"":request.getParameter("Status");
	if(azul.KeyTool.mr(linkid,Status,"Y")){
		out.println("0");
	}
	else{
		out.println("ERR");
	}
}
%>