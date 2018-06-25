<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>

<%
String app_id=azul.JspUtil.getStr(request.getParameter("app_id"),"");
String app_code=azul.JspUtil.getStr(request.getParameter("app_code"),"");
String order_no=azul.JspUtil.getStr(request.getParameter("order_no"),"");
String trade_no=azul.JspUtil.getStr(request.getParameter("trade_no"),"");
String amount=azul.JspUtil.getStr(request.getParameter("amount"),"");
String channel=azul.JspUtil.getStr(request.getParameter("channel"),"");
String state=azul.JspUtil.getStr(request.getParameter("state"),"");
String userdata=azul.JspUtil.getStr(request.getParameter("userdata"),"");

if(ConstValue.DEBUG == 1)
{
    DebuUtil.log("req="+request.getQueryString());
}

model.MmPay obj = new model.MmPay();
obj.app_id = app_id;
obj.app_code = app_code;
obj.order_no = order_no;
obj.trade_no = trade_no;
obj.amount = Integer.valueOf(amount);
obj.channel = channel;
obj.ret = Integer.valueOf(state);
obj.ExData = userdata;
obj.app_key = userdata;

model.Activate m_activate = MmpayRsq.handleExData(userdata,obj);
MmpayRsq.handle(m_activate, "", obj);

String rsq="success";
out.println(rsq);
%>

