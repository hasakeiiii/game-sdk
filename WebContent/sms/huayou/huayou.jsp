<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/dingxue/sms/tom/tomMo.jsp?linkid=1234567&mobile=13751180191&msg=796SSXCXACXAA
//azul.JspUtil.p(request);

String linkid=request.getParameter("link_id")==null?"":request.getParameter("link_id");
if(linkid.equals("")){
	System.out.println("huayou.mo linkid is null");
	return;
}
String stat=request.getParameter("stat")==null?"":request.getParameter("stat");
if("".equals(stat)){
	System.out.println("huayou.mo 1002-------------------");
	String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
	if(azul.KeyTool.mo(linkid,"1002",2,mobile,"","")){
		out.println("OK");
	}
	else{
		out.println("ERR");
	}
}
else{
	System.out.println("huayou.mr 1002-------------------");
	if(azul.KeyTool.mr(linkid,stat,"DELIVRD")){
		//同步信息
		/*
		String mobile=request.getParameter("mobile");
		String url="http://192.168.0.123/sp.jsp?linkid="+linkid+"&mobile="+mobile;
		azul.KeyTool.send(url);
		*/
		out.println("OK");
	}
	else{
		out.println("ERR");
	}
}

%>