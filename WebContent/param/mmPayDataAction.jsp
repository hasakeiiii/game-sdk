<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%
	MmPayData mmPayData=new MmPayData();
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
String payName = request.getParameter("payName");
String payAmount = request.getParameter("payAmount");
String payId = request.getParameter("payId");
String payType = request.getParameter("payType");
String conpanyName = request.getParameter("conpany_name");
String payKey = request.getParameter("payKey");
String publicKey = request.getParameter("publicKey");
String proId = request.getParameter("proId");
String md5 = request.getParameter("md5");
String md5dex = request.getParameter("md5dex");
String verno = request.getParameter("verno");
String packageName = request.getParameter("packageName");
String mainActivity = request.getParameter("mainActivity");
String payCodeList = request.getParameter("payCodeList");

String webPartner = request.getParameter("webPartner");
String webKey1 = request.getParameter("webKey1");
String webKey2 = request.getParameter("webKey2");
String webPid = request.getParameter("webPid");
String webList = request.getParameter("webList");
String webUrl = request.getParameter("webUrl");
String getNum = request.getParameter("getNum");

String unicomId = request.getParameter("unicomId");
String unicomUrl=request.getParameter("unicomUrl");
String unBkUrl=request.getParameter("unBkUrl");
String unKey=request.getParameter("unKey");
String unPayName=request.getParameter("unPayName");
String unPayList=request.getParameter("unPayList");
String unGoodId=request.getParameter("unGoodId");
String unName=request.getParameter("unName");

//电信
String tclId=request.getParameter("tclId");
String tclUrl=request.getParameter("tclUrl");
String tclBkUrl=request.getParameter("tclBkUrl");
String tclKey=request.getParameter("tclKey");
String tclPayList=request.getParameter("tclPayList");
String tclName=request.getParameter("tclName");

//饭盒
String boxPayId = request.getParameter("boxPayId");
String boxPayList = request.getParameter("boxPayList");
String boxPayName = request.getParameter("boxPayName");

String appPayId = request.getParameter("appPayId");
String type = request.getParameter("type");
int moneycount = Integer.parseInt(request.getParameter("moneycount"));
int priority = Integer.parseInt(request.getParameter("priority"));
int limitMoney = Integer.parseInt(request.getParameter("limitMoney"));
int limitUserMoney = Integer.parseInt(request.getParameter("limitUserMoney"));
String powerId = request.getParameter("powerId");
String content = request.getParameter("content");
String contentNum = request.getParameter("contentNum");
String powerList = request.getParameter("powerList");
String powerPayCode = request.getParameter("powerPayCode");
mmPayData.setPayName(payName);
mmPayData.setPayAmount(payAmount);
mmPayData.setPayId(payId);
mmPayData.setPayType(payType);
mmPayData.setPayKey(payKey);
mmPayData.setConpanyName(conpanyName);
mmPayData.setPublicKey(publicKey);
mmPayData.setProId(proId);
mmPayData.setMd5(md5);
mmPayData.setMd5dex(md5dex);
mmPayData.setVerno(verno);
mmPayData.setPackageName(packageName);
mmPayData.setMainActivity(mainActivity);
mmPayData.setUnicomId(unicomId);
mmPayData.setUnicomUrl(unicomUrl);
mmPayData.setUnBkUrl(unBkUrl);
mmPayData.setUnKey(unKey);
mmPayData.setUnPayName(unPayName);
mmPayData.setUnPayList(unPayList);
mmPayData.setUnGoodId(unGoodId);
mmPayData.setUnName(unName);
mmPayData.setTclId(tclId);
mmPayData.setTclUrl(tclUrl);
mmPayData.setTclBkUrl(tclBkUrl);
mmPayData.setTclKey(tclKey);
mmPayData.setTclPayList(tclPayList);
mmPayData.setTclName(tclName);
mmPayData.setAppPayId(appPayId);
mmPayData.setWebPartner(webPartner);
mmPayData.setWebKey1(webKey1);
mmPayData.setWebKey2(webKey2);
mmPayData.setWebPid(webPid);
mmPayData.setWebList(webList);
mmPayData.setWebUrl(webUrl);
mmPayData.setGetNum(getNum);
mmPayData.setLimitMoney(limitMoney);
mmPayData.setLimitUserMoney(limitUserMoney);
mmPayData.setPayCodeList(payCodeList);
mmPayData.setBoxPayId(boxPayId);
mmPayData.setBoxPayList(boxPayList);
mmPayData.setBoxPayName(boxPayName);
mmPayData.setType(type);
mmPayData.setMoneycount(moneycount);
mmPayData.setPriority(priority);
mmPayData.setPowerId(powerId);
mmPayData.setContent(content);
mmPayData.setContentNum(contentNum);
mmPayData.setPowerList(powerList);
mmPayData.setPowerPayCode(powerPayCode);

MmPayDataDao mmPayDataDao=new MmPayDataDao();

String act_flag="-1";
String msg="操作失败";
String toPage="mmPayDataList.jsp?pageno="+pageno;
String op=azul.JspUtil.getStr(request.getParameter("op"),"");
if(op.equals("add")){ 
	int ret=mmPayDataDao.addMmPayData(mmPayData);
	if(ret == ConstValue.OK)
	{
	   act_flag="1";
	}
}
else if(op.equals("edit")){ 
	int id = azul.JspUtil.getInt(request.getParameter("id"),0);
	mmPayData.setId(id);
	act_flag=mmPayDataDao.edit(mmPayData);
	
}
else{ 
    System.err.println("app op action is not right,op:"+op);
}
%>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
if("<%=act_flag%>"=="-1"){
    error("<%=msg%>",callback);
}
else{
	ok("操作成功",callback);
}
function callback(){
	location="<%=toPage%>";
}
</script>
</body>
</html>