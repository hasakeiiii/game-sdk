<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>

<%
String packet_id=azul.JspUtil.getStr(request.getParameter("zzdata"),"");


ChannelDataDao channelDataDao = new ChannelDataDao();
ChannelData channelData = null;
channelData=channelDataDao.getRecord(packet_id,date);
if(channelData != null)
{
String rsq="";
rsq+="packet_id="+channelData.getPacketId();
rsq+="&date="+channelData.getDate();
rsq+="&mmpay="+channelData.getMmPay();
rsq+="&msmNum="+channelData.getMsmNum();
rsq+="&feeNum="+channelData.getFeeNum();
rsq+="&reqNum="+channelData.getReqNum();
rsq+="&reqOkNum="+channelData.getReqOkNum();
out.println(rsq);
}   
else
{
out.println("null");
}  
%>

