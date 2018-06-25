<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ page import="java.util.*" %>
<%
CfgSpInfoDao cfgSpInfoDao=new CfgSpInfoDao();
List list=cfgSpInfoDao.getList("select * from cfg_sp_info order by sid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<span class="tdRed">请注意此处命令并不是真正发送命令，具体发送命令由技术人员负责分配。如有疑问请找技术人员核对。</span>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table id="TableColor" width="100%" border="0">
<tr>
<td width="9%">地址</td>
<td width="8%">命令</td>
<td width="5%">运营商</td>
<td width="4%">价格</td>
<td width="11%">省份</td>
<td width="14%">屏蔽地区</td>
<td width="29%">下发语</td>
<td width="6%">回复</td>
<td width="7%">上限</td>
</tr>
<%
for(int i=0;i<list.size();i++){
    CfgSpInfo cfgSpInfo=(CfgSpInfo)list.get(i);
    String trone=cfgSpInfo.getTrone();
    String command=cfgSpInfo.getCommand();
    String oper=cfgSpInfo.getOperator()==1?"移动":"联通";
    int price=cfgSpInfo.getPrice();
    String province=azul.KeyTool.getAreaByCode(cfgSpInfo.getProvince());
    String city=azul.KeyTool.getCityByCode(cfgSpInfo.getCity());
    String info=cfgSpInfo.getInfo();
    String replay=cfgSpInfo.getReplay();
    String max=cfgSpInfo.getMax();
%>
<tr>
<td><%=trone%></td>
<td><%=command%></td>
<td><%=oper%></td>
<td><%=price%></td>
<td><%=province%></td>
<td><%=city%></td>
<td><%=info%></td>
<td><%=replay%></td>
<td><%=max%></td>
</tr>
<%
}
%>
</table>
</form>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
</body>
</html>