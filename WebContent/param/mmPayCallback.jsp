<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="sdkReq.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>

<%
String app_id=azul.JspUtil.getStr(request.getParameter("app_id"),"");
String app_code=azul.JspUtil.getStr(request.getParameter("app_code"),"");
String trade_no=azul.JspUtil.getStr(request.getParameter("trade_no"),"");
String order_no=azul.JspUtil.getStr(request.getParameter("order_no"),"");
String amount=azul.JspUtil.getStr(request.getParameter("amount"),"");
String userdata=azul.JspUtil.getStr(request.getParameter("userdata"),"");
String state=azul.JspUtil.getStr(request.getParameter("state"),"");
String channel=azul.JspUtil.getStr(request.getParameter("channel"),"");
String num=azul.JspUtil.getStr(request.getParameter("num"),"");

 model.MmPay obj = new model.MmPay();

obj.date = DateUtil.getDate();
obj.time = DateUtil.getTime();
obj.app_id=app_id;    
obj.app_code=app_code;
obj.order_no= order_no;
obj.trade_no=trade_no;  
obj.amount=Integer.valueOf(amount); 
obj.MSISDN=num;
obj.channel=channel;
obj.ExData = userdata;
obj.app_key = userdata;
obj.ret=Integer.valueOf(state);
        
String rsq =MmpayRsq.notisty(obj,"");///////////////////
if(!StringUtil.is_nullString(rsq))
{
   obj.setNotisfy(1);
}

 MmPayDao dao = new MmPayDao();
 int ret = dao.mmPay(obj);
    	            
if(ret != ConstValue.Fail)
{
   MmpayRsq.handleSA(obj);
}
out.println("success");
%>

