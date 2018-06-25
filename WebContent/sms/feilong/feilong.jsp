<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://124.232.156.42:8080/sms/feilong/feilong.jsp?LinkID=123456&Action=3&Mobile=13751180111&MOContent=1*3&SPID=1066095953
//http://124.232.156.42:8080/sms/feilong/feilong.jsp?LinkID=123456&Action=4&Status=Y&MOContent=1*3&SPID=1066095953
String linkid=request.getParameter("LinkID")==null?"":request.getParameter("LinkID");
if(linkid.equals("")){
	System.out.println("feilong 1001 linkid is null");
	return;
}
String Action=request.getParameter("Action")==null?"":request.getParameter("Action");
//azul.JspUtil.p(request);
String sid="1001";
if("3".equals(Action)){
	System.out.println("feilong.mo 1001-------------------");
	String mobile=request.getParameter("Mobile")==null?"":request.getParameter("Mobile");
	String msg=request.getParameter("MOContent")==null?"":request.getParameter("MOContent");
	String SPID=request.getParameter("SPID")==null?"":request.getParameter("SPID");
	if(azul.SpTool.mo(linkid,sid,1,mobile,msg,SPID)){
		out.println("0");
	}
}
else if("4".equals(Action)){
	System.out.println("feilong.mr  1001-------------------");
	String Status=request.getParameter("Status")==null?"":request.getParameter("Status");
	String msg=request.getParameter("MOContent")==null?"":request.getParameter("MOContent");
	String SPID=request.getParameter("SPID")==null?"":request.getParameter("SPID");
	if(azul.SpTool.mr(sid,linkid,msg,SPID,Status,"Y")){
		out.println("0");
	}
}
%>