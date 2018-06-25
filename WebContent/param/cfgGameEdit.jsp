<%@page import="util.DebuUtil"%>
<%@page import="util.ConstValue"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check=cfgGameEdit.jsp" flush="true" />
<%
String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id=azul.JspUtil.getInt(request.getParameter("id"),0);

String game_name="";//game名称
String game_code="";//game代号
String game_type="网游";//game类型
String game_url = "";
int onlyoff = 0;//专属币折扣
String payways = "";//支付方式
int yeediscount = 80;

char[] cpayways=new char[]{};//传入js中去设置支付方式的状态

String game_mykey = "";
String discontent=ConstValue.discontent;
String discontent2=ConstValue.discontent2;
String disurl="";
String noturl = "";
String exiturl = "";
String pay_mark = "";//计费开关
String pay_url = "";//计费URL
String pay_urlb = "";//计费回调
String mm_company = "";//所属公司
String cp_name = "";//所属公司(网游)
int cpcount = 100;

java.util.Map<String,String> pay_markMap=new HashMap<String,String>();
pay_markMap.put("5","掌中破解");
pay_markMap.put("4","安安破解");
pay_markMap.put("3","联网破解");
pay_markMap.put("2","切换计费");
pay_markMap.put("1","本地破解");
pay_markMap.put( "0","关");

java.util.Map<String,String> gameTypeMap=new HashMap<String,String>();
gameTypeMap.put(App.OFF_LINE, "单机");
gameTypeMap.put(App.MM_ON_LINE, "MM网游");
gameTypeMap.put(App.ON_LINE, "网游");

java.util.Map<String,String> mm_companyMap=new HashMap<String,String>();
mm_companyMap.put(App.MZYW,"拇指游玩");
mm_companyMap.put(App.MZHY,"拇指互娱");
mm_companyMap.put(App.ZTY,"中泰源");
mm_companyMap.put(App.JY,"竟游");
mm_companyMap.put(App.CQ,"创趣");
mm_companyMap.put(App.GY,"光游");
mm_companyMap.put(App.HLX,"宏乐欣");



if(id!=0){
    pageTitle="编辑信息ID:"+id;
    AppDao cfgSpDao=new AppDao();
    App cfgSp=(App)cfgSpDao.getById(id);
    game_name = cfgSp.getName();
    //新增游戏类型
    game_type = cfgSp.getGameType();
    //可以不写，因为id直接决定是网游还是单机
    game_code=cfgSp.getNo();
    game_url = cfgSp.getUrl();
    game_mykey = cfgSp.getMykey();
    exiturl = cfgSp.getExiturl();
    pay_mark = cfgSp.getPayMark();
    pay_url = cfgSp.getPayUrl();
    pay_urlb = cfgSp.getPayUrlb();
    mm_company = cfgSp.getMmCompany();
    cp_name = cfgSp.getCpNo();
  	onlyoff = cfgSp.getOff();
    payways = cfgSp.getPaywaysign();
    yeediscount = cfgSp.getYeediscount();
    cpcount = cfgSp.getCpcount();
    
	if(payways!=null&&!payways.equals(""))
		cpayways = payways.toCharArray();
	System.out.println("this is"+Arrays.toString(cpayways));
    String tempstr=cfgSp.getDiscontent();
    if(tempstr.length() > 0)
    {
    	discontent = tempstr;
    }
    
    tempstr=cfgSp.getDiscontent2();
    if(tempstr.length() > 0)
    {
    	discontent2 = tempstr;
    }
    
    tempstr=cfgSp.getDisurl();
    if(tempstr.length() > 0)
    {
    	disurl = tempstr;
    }
    
    tempstr=cfgSp.getNoturl();
    if(tempstr.length() > 0)
    {
    	noturl = tempstr;
    }
}
else
{
	AppDao appDao=new AppDao();
	ArrayList<Object> list=appDao.getList("select * from app order by id desc limit 1");
	if(list.size() > 0)
	{
		App app=(App)list.get(0);
		int ivalue = Integer.valueOf(app.getNo());
		ivalue ++;
		game_code = String.valueOf(ivalue);
	}
}

CpManageDao cpmanageDao = new CpManageDao();
java.util.Map<String,String> cpmanageMap=cpmanageDao.getSelectMap("select cp_no,name from cp_manage order by cp_no");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>

<style>
	#dj{
		display:none;
	}
	</style>
	
</head>
<body>
	<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css"
		media="screen" />
	<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
	<script type="text/javascript" src="../_js/Check.js"></script>
	<script type="text/javascript" src="../_js/CheckForm.js"></script>
<script language="javascript">


window.onload = function() {
	//CheckForm.formBind("mainForm");
	console.log("进入了00");
	initcheck();
}

function btnSave(){
    if(!CheckForm.checkForm("mainForm"))return;
    if(<%=id%>==0){
        document.mainForm.action="cfgGameAction.jsp?op=add&paywaysign="+paywaysign;
        console.log("post的时候paywaysign为："+paywaysign);
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="cfgGameAction.jsp?op=edit&paywaysign="+paywaysign+"&pageno=<%=pageno%>&id=<%=id%>";
        console.log("post的时候paywaysign为："+paywaysign);
        document.mainForm.submit();
    }
}

function initcheck(){
	console.log("进入了2");
	if(<%=id%>!=0){
		paywaysign = '<%=payways%>';
		console.log("进入了2,paywaysign="+paywaysign);
		<%for(int i = 0; i< cpayways.length;i++){%>
			str[<%=i%>]=<%=cpayways[i]%>;
		<%}%>
	}else{
		paywaysign = "111111111";
		str = ['1','1','1','1','1','1','1','1','1'];
	}
	
	var field = document.getElementsByName("cb");
	console.log(field.length);
	var length = str.length;
	console.log("进入了3,length="+length);
	for(var i=0;i < length;i++){
		if(str[i] == '1'){
			field[i].checked = true;
		}else{
			field[i].checked = false;
		}
	}
}



function getcheckresult(field,v) {
	console.log("没有改变前");	
	console.log(str);

	console.log("第"+v+"个选择框被点击");
	if(field[v-1].checked == true){
			str[v-1]='1';
			console.log("改变后");	
			console.log(str);
		
		}else{
			str[v-1]='0';
			console.log("改变后");
			console.log(str);
		
			}
		paywaysign=getsignstr(str);
		console.log("现在得paywaysign为："+paywaysign);
	}

function getsignstr(str){
	var re="";
	for(var i=0;i < str.length;i++){
		re +=str[i]; }
	
	return re;
	}


var paywaysign;
var str=new Array();


</script>
	<h1><%=pageTitle%></h1>
	<form name="mainForm" id="mainForm" method="post"
		style="margin:0;padding:0">
		<table width="100%" border="0">
			<tr align="center">
				<td width="33%" id="title_game_name">游戏名称</td>
				<td width="33%"><input name="game_name" alt="game_name"
					value="<%=game_name%>" maxlength="250" />
				</td>
				<td><span id="warn_sp_name"></span>
				</td>
			</tr>
			<tr align="center">
				<td width="33%" id="title_game_code">游戏代号</td>
				<td width="33%"><input name="game_code" alt="game_code"
					value="<%=game_code%>" maxlength="250" />
				</td>
				<td><span id="warn_sp_code"></span>
				</td>
			</tr>

			<tr align="center">
				<td width="33%" id="title_game_type">游戏类型</td>
				<td width="33%">
					<select name="game_type" id="game_type">
					</select>
				</td>
				<td><span id="warn_game_type"></span>
				</td>
			</tr>
				<tr align="center">
					<td width="33%" id="title_company_name">CP公司</td>
					<td width="33%">
						<select name="company_name" id="company_name">
							<option value="">请选择</option>
						</select>
					</td>
					<td><span id="warn_sp_url"></span>
					</td>
				</tr>
			<tr align="center">
				<td width="33%" id="title_cpcount">CP显示比例</td>
				<td width="33%"><input name="cpcount" alt="cpcount"
					value="<%=cpcount%>" maxlength="250" />
				</td>
				<td><span id="warn_cpcount"></span>
				</td>
			</tr>
		</table>
		<div id="wy">
			<table width="100%" border="0">
				
				 <!-- 所属公司 -->
			
			
				<tr align="center">
					<td width="33%" id="title_game_url">充值回调</td>
					<td width="33%"><input name="game_url" alt="game_url_null"
						value="<%=game_url%>" maxlength="250" />
					</td>
					<td><span id="warn_sp_url"></span>
					</td>
				</tr>
				<tr align="center">
					<td width="33%" id="title_game_mykey">密匙</td>
					<td width="33%"><input name="game_mykey" alt="game_mykey_null"
						value="<%=game_mykey%>" maxlength="250" />
					</td>
					<td><span id="warn_sp_mykey"></span>
					</td>
				</tr>
				<tr align="center">
					<td width="33%" id="title_discontent">登录跑马灯内容</td>
					<td width="33%"><input name="discontent" alt="discontent_null"
						value="<%=discontent%>" maxlength="250" />
					</td>
					<td><span id="warn_discontent_code"></span>
					</td>
				</tr>
				<tr align="center">
					<td width="33%" id="title_noturl">登录广告链接</td>
					<td width="33%"><input name="noturl" alt="noturl_null"
						value="<%=noturl%>" maxlength="250" />
					</td>
					<td><span id="warn_disurl_code"></span>
					</td>
				</tr>
				<tr align="center">
					<td width="33%" id="title_discontent2">支付跑马灯内容</td>
					<td width="33%"><input name="discontent2"
						alt="discontent2_null" value="<%=discontent2%>" maxlength="250" />
					</td>
					<td><span id="warn_discontent2_code"></span>
					</td>
				</tr>
				<tr align="center">
					<td width="33%" id="title_disurl">支付广告链接</td>
					<td width="33%"><input name="disurl" alt="disurl_null"
						value="<%=disurl%>" maxlength="250" />
					</td>
					<td><span id="warn_disurl_code"></span>
					</td>
				</tr>
	
				<tr align="center">
					<td width="33%" id="title_payways">支付方式</td>
					<td width="33%">
					<input type="checkbox" name="cb" value="1" onClick="getcheckresult(this.form.cb,value)" >支付宝
					<input type="checkbox" name="cb" value="2" onClick="getcheckresult(this.form.cb,value)" >财付通
					<input type="checkbox" name="cb" value="3" onClick="getcheckresult(this.form.cb,value)" >银联<br>
					<input type="checkbox" name="cb" value="4" onClick="getcheckresult(this.form.cb,value)" >移动（易宝）
					<input type="checkbox" name="cb" value="5" onClick="getcheckresult(this.form.cb,value)" >联通（易宝）
					<input type="checkbox" name="cb" value="6" onClick="getcheckresult(this.form.cb,value)" >电信（易宝）<br>
					<input type="checkbox" name="cb" value="7" onClick="getcheckresult(this.form.cb,value)" >骏网一卡通（易宝）
					<input type="checkbox" name="cb" value="8" onClick="getcheckresult(this.form.cb,value)" >纵游一卡通（易宝）
					<input type="checkbox" name="cb" value="9" onClick="getcheckresult(this.form.cb,value)" >32卡（易宝）<br>
					</td>
					<td><span id="warn_pay_ways"></span>
					</td>
				</tr>
				<tr>
				<td width="33%" id="title_onlyoff" align="center">专属币折扣</td>
					<td width="33%" align="center"><input name="onlyoff" alt="onlyoff_null"
						value="<%=onlyoff%>" maxlength="250" />
					</td>
					<td><span id="warn_only_off"></span>
					</td>
				</tr>
				<tr>
				<td width="33%" id="title_yeediscount" align="center">易宝折扣</td>
					<td width="33%" align="center"><input name="yeediscount" alt="yeediscount_null"
						value="<%=yeediscount%>" maxlength="250" />
					</td>
					<td><span id="warn_yee_discount"></span>
					</td>
				</tr>
			</table>
		</div>

		<!-- 这里是第二个单机游戏的表单 -->
		<div id="dj">
			<table width="100%" border="0">
				<tr align="center">
					<td width="33%" id="title_pay_mark">单机计费开关</td>
					<td width="33%"><select name="pay_mark" id="pay_mark">
					</select></td>
					<td><span id="warn_sell_id"></span>
					</td>
				</tr>
				<tr align="center">
					<td width="33%" id="title_pay_url">计费开关参数</td>
					<td width="33%"><input name="pay_url" alt="pay_url_null"
						value="<%=pay_url%>" maxlength="250" />
					</td>
					<td><span id="warn_disurl_code"></span>
					</td>
				</tr>
				<tr align="center">
					<td width="33%" id="title_pay_urlb">计费回调</td>
					<td width="33%"><input name="pay_urlb" alt="pay_url_null"
						value="<%=pay_urlb%>" maxlength="250" />
					</td>
					<td><span id="warn_disurl_code"></span>
					</td>
				</tr>
                               <tr align="center">
				<td width="33%" id="title_mm_company">所属公司</td>
				<td width="33%"><select name="mm_company" id="mm_company">
				</select></td>
				<td><span id="warn_mm_company"></span>
				</td>
			        </tr>

			</table>
		</div>
		<!-- 这里是最后一个按键的表单 -->
		<table width="100%" border="0">
			<tr align="center">
				<td width="33%" id="title_exiturl">游戏退出公告</td>
				<td width="33%"><input name="exiturl" alt="exiturl_null"
					value="<%=exiturl%>" maxlength="250" />
				</td>
				<td><span id="warn_disurl_code"></span>
				</td>
			</tr>
			<tr align=center>
				<td colspan=3><a href="#" onclick="btnSave()"><img
						src="../_js/ico/btn_save.gif" border="0" alt="保存"
						align="absmiddle" />
				</a>&nbsp;&nbsp;&nbsp; <a href="#" onclick="javascript:history.back()"><img
						src="../_js/ico/btn_back.gif" border="0" alt="返回"
						align="absmiddle" />
				</a></td>
			</tr>
		</table>

</form>
</body>
</html>

<script type="text/javascript" src="../_js/elemUtil.js"></script>

<script type="text/javascript">
<%out.println(azul.JspUtil.initSelect("pay_mark",pay_markMap,pay_mark));%>
<%out.println(azul.JspUtil.initSelect("game_type",gameTypeMap,game_type));%>
<%out.println(azul.JspUtil.initSelect("mm_company",mm_companyMap,mm_company));%>
<%out.println(azul.JspUtil.initSelect("company_name",cpmanageMap,cp_name));%>
</script>
<script type="text/javascript">
		var choice = document.getElementsByTagName("select")[0];
	
		if('<%=game_type%>' == "on_line") {
		document.getElementById("dj").style.display = "none";
		document.getElementById("wy").style.display = "block";
	}

	else {
		document.getElementById("wy").style.display = "none";
		document.getElementById("dj").style.display = "block";
	}

	choice.onchange = function(event) {
		document.getElementById("wy").style.display = "none";
		document.getElementById("dj").style.display = "none";
		var option = choice.value;
		switch (option) {
		case "on_line":
			document.getElementById("wy").style.display = "block";
			break;
		case "off_line":
			document.getElementById("dj").style.display = "block";
			break;
		}
	}
	
	
</script>
