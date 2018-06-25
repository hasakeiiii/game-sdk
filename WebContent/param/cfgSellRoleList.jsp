<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check_role=admin" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/dtree/dtree.js"></script>
</head>
<%
int cfg_sell_id=azul.JspUtil.getInt(request.getParameter("cfg_sell_id"),-1);
String role=azul.JspUtil.getStr(request.getParameter("role"),"");
CfgSellDao cfgSellDao=new CfgSellDao();
List list=cfgSellDao.getList("select * from cfg_sell");

CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
List listMain=cfgCompanyDao.getList("select * from cfg_company order by cid");System.out.println("BBB:"+listMain.size());

String Privilege=cfgCompanyDao.getValueStr("select cfg_company_id from cfg_company where cfg_sell_id="+cfg_sell_id,",");
%>
<body>
<h1>系统权限</h1>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" border="0" class="table_noborder">
  <tr>
    <td width="35%" align="center" valign="top">
	<table id="TableColor" width="100%" border="0">
      <tr>
        <td>序号</td>
        <td>用户名称</td>
		</tr>
      <%
	for(int i=0;i<list.size();i++){
		CfgSell cfgSell=(CfgSell)list.get(i);
		String username=cfgSell.getName();
	%>
      <tr>
        <td><%=i+1%><input type="radio" name="user" value="<%=cfgSell.getCfgSellId()%>" onclick="javascript:location='cfgSellRoleList.jsp?cfg_sell_id=<%=cfgSell.getCfgSellId()%>'"/></td>
        <td><%=username%></td>
		</tr>
    <%
	}
	%>
    </table>
	</td>
	<td width="3%">&nbsp;</td>
    <td width="62%" valign="top" id="radioList">
	<a href="#" onclick="d.openAll()">展开</a>&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="d.closeAll()">合并</a>&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="allSelect('radioList')">全选</a>&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="oppSelect('radioList')">反选</a>&nbsp;&nbsp;&nbsp;
	<input type="button" name="textfield22" value="修改权限" onclick="saveCfgSellRole()"/>
	<script>
    var d = new dTree('d');
    d.add(0,-1,'');  //目录
	d.add(1,0,"<strong>公司列表</strong>");
    <%
    for(int i=0;i<listMain.size();i++){
	    CfgCompany cfgCompany=(CfgCompany)listMain.get(i);System.out.println("AAA");
	    String checked="";
		if((","+Privilege+",").indexOf(","+cfgCompany.getCfgCompanyId()+",")>-1){
			checked="checked";
		}
		%>
		d.add(<%=i + 100%>,1,"<input type='checkbox' name='chkValue' <%=checked%> value='<%=cfgCompany.getCfgCompanyId()%>'/>&nbsp;<%=cfgCompany.getName()%>&nbsp;","javascript:void(0)");
		<%
   }
   %>
   document.write(d);
    </script>
	</td>
  </tr>
</table>
</form>
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script>
function saveCfgSellRole(){
    var key=getRadio("user");
	if(key==""){
	    error("请选择需要修改权限的用户");
		return;
	}
	var role=getCheck("chkValue");
	if(role==""){
	    error("请选择用户权限");
		return;
	}
	document.mainForm.action="cfgCompanyAction.jsp?op=editcfgSellRole&sys_user_id="+key+"&role="+role;
    document.mainForm.submit();	
}
//初始化当前选择用户权限
initElem("user","<%=cfg_sell_id%>");
initElem("chkValue","<%=Privilege%>");
</script>
</body>
</html>
