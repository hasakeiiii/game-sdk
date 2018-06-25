//兼容下拉框，单选框，多选框的得到所选值方法
function getElem(elemName){
	var result="";
     var elem=document.getElementsByName(elemName);
     if(elem==null){
		 alert("getElem error:not elem named:"+elemName);
	 }
	 //ie8 elem永不为空所以另加判断
	 if(!elem[0].type){
	 	return result;
	 }
	 if(elem[0].type=="select-one"){
		for(var i=0;i<elem[0].options.length;i++){
	         if(elem[0][i].selected){
	             return elem[0][i].value;
			 }
	     } 
	 }
	 else if(elem[0].type=="checkbox"){
		 for(var i=0;i<elem.length;i++){
				if(elem[i].checked){
					result+=elem[i].value;
					result+=",";
				}
			} 
			if(result!=""){
				result=result.substring(0,result.length-1);
			}
	 }
	 else if(elem[0].type=="radio"){
		 for(var i=0;i<elem.length;i++){
	         if(elem[i].checked){
	             result=elem[i].value;
			 }
     	} 
	 }
     return result;
}

//兼容下拉框，单选框，多选框的初始化方法
function initElem(elemName,key){
	var elem=document.getElementsByName(elemName);
	 if(elem==null){
		 alert("initElem error:not elem named:"+elemName);
	 }
	 if(elem[0].type=="select-one"){
		 for(var i=0;i<elem[0].options.length;i++){
	         if(elem[0][i].value==key){
	             elem[0][i].selected=true;
				 break;
			 }
	     } 
	 }
	 else if(elem[0].type=="checkbox"){
		 //多个值的初始化
		 if(key.indexOf(",")>-1){
		     var jsArr=key.split(",");
		     for(var m=0;m<elem.length;m++){
		      	 elem[m].checked=false;
				 for(var n=0;n<jsArr.length;n++){
					 if(elem[m].value==jsArr[n]){
						 elem[m].checked=true;
					 }
	             }
	         } 
		 }
		 //单个值的初始化
		 else{
		      for(var i=0;i<elem.length;i++){
				 if(key==elem[i].value){
					 elem[i].checked=true;
				 }
				 else{
					 elem[i].checked=false;
				 }
	         } 
		 }
	 }
	 else if(elem[0].type=="radio"){
		 for(var i=0;i<elem.length;i++){
	         if(elem[i].value==key){
	             elem[i].checked=true;
			 }
	     } 
	 }
}
/*====================================================================
下拉框部分属性，和方法
elem.options[elem.options.selectedIndex].value  下拉框选中的值
elem.options[elem.options.selectedIndex].text;  下拉框选中的标签  
清除下拉框的全部内容：myselect.length=0;
document.form1.select.options.selectedIndex             form1的选择索引
ondblclick="moveRight()"                        下拉框的双击事件
elem.remove (i);                                移除下拉框的某一项 
*/
/*
使用数据库数据填充下拉菜单
	var jsArrId=new Array();
	var jsArrName=new Array();

	int id=rs.getInt("ID");
	String name=rs.getString("title");
	%>
	jsArrId[<%=i%>]="<%=id%>";
	jsArrName[<%=i%>]="<%=name%>"; 
	<%
	i++;

	fillSelect("pageLink",jsArrId,jsArrName);
*/
function fillSelect(elemId,arrID,arrName,key){
     var elem=document.getElementById(elemId);
     var begIndex=elem.options.length;
     for(var i=begIndex;i<(arrID.length+begIndex);i++){
         elem.options[i]= new Option(arrName[i-begIndex],arrID[i-begIndex]); 
         if(elem.options[i].value==key){
             elem.options[i].selected=true;
		 }
     } 
}
/*
初始化下拉级联菜单objParent:父菜单id,objSon:子菜单id,arrParentID:父菜单id的数组,arrParentName:父菜单name的数组
onLoad="initSelectGroup('select1','select2',arrParentID,arrParentName)"
onChange="changeSelectGroup('select1','select2',arrLinkID,arrSonID,arrSonName)"

实际应用例子
* <%
String cid=azul.JspUtil.getStr(request.getParameter("cid"));
String mode=azul.JspUtil.getStr(request.getParameter("mode"));
CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
Map cidMap=cfgCompanyDao.getSelectMap("select cid,concat(cid,'(',name,')') from cfg_company");
CfgCompanyMobileDao CfgCompanyMobileDao=new CfgCompanyMobileDao();
java.util.List tidList=CfgCompanyMobileDao.getObjectList("select tid,concat(mode,'(',tid,')'),cid from cfg_company_mobile");
* %>
<script type="text/javascript" src="_js/elemUtil.js"></script>
<select id="cid" name="cid">
</select>
<select id="tid" name="tid">
</select>
<script>
<%
out.print(azul.JspUtil.initSelectGroup("cid","tid",cidMap,tidList,cid,tid));
%>
</script>
*/
function initSelectGroup(objParent,objSon,arrParentID,arrParentName){
   	var elemParent=document.getElementById(objParent);
	var elemSon=document.getElementById(objSon);
	elemParent.options[0]= new Option("请选择","");
	elemSon.options[0]= new Option("请选择","");
	for(var i=0;i<arrParentID.length;i++){
		elemParent.options[i+1]= new Option(arrParentName[i],arrParentID[i]);
	} 
}
function initSelectGroup(objParent,objSon,arrParentID,arrParentName,arrLinkID,arrSonID,arrSonName,parentKey,sonKey){
   	var elemParent=document.getElementById(objParent);
	var elemSon=document.getElementById(objSon);
	elemParent.options[0]= new Option("请选择","");
	elemSon.options[0]= new Option("请选择","");
	for(var i=0;i<arrParentID.length;i++){
		elemParent.options[i+1]= new Option(arrParentName[i],arrParentID[i]);
	} 
	initSelect(objParent,parentKey);
	changeSelectGroup(objParent,objSon,arrLinkID,arrSonID,arrSonName);
	initSelect(objSon,sonKey);
}
/*
级联菜单改变发生的事件objParent:父菜单id,objSon:子菜单id,arrLinkId:两者之间联系的id,arrSonID:子菜单id的数组,arrSonName:子菜单name的数组
*/
function changeSelectGroup(objParent,objSon,arrLinkID,arrSonID,arrSonName){
     var elemParent=document.getElementById(objParent);
	 var elemSon=document.getElementById(objSon);
     var selectId = elemParent.options[elemParent.selectedIndex].value;
     var k=1;
	 //清除以前加入下拉选项，注意使用for(var i=0;elemSon.length-1;i++)不成功,只能用for(i=elemSon.length-1; i>=1; i--)elemSon.remove (i);
	 elemSon.length=0;
	 elemSon.options[0]= new Option("请选择","");
	 //添加新的菜单项目
     for(var i=0;i<arrLinkID.length;i++){
          if(arrLinkID[i]==selectId){
              elemSon.options[k]= new Option(arrSonName[i],arrSonID[i]); 
              k++;
          }
     } 
     //如果子下拉列表为空，则有可能对应关联值不是value是key则重新添加一遍
     if(k<2){
     	 selectId = elemParent.options[elemParent.selectedIndex].key;
     	 for(var i=0;i<arrLinkID.length;i++){
          if(arrLinkID[i]==selectId){
              elemSon.options[k]= new Option(arrSonName[i],arrSonID[i]); 
              k++;
          }
        } 
     }
}
//初始化下拉框哪项被选中
function initSelect(elemId,key){
     var elem=document.getElementById(elemId);
	 if(elem==null){
		 alert("initSelect error:not elem named:"+elemId);
	 }
     for(var i=0;i<elem.options.length;i++){
         if(elem[i].value==key){
             elem[i].selected=true;
			 break;
		 }
     } 
}
/*====================================================================
checkbox部分属性，和方法 
同一组checkbox拥有相同的name，不同的value值，注意不要定义id值
使用name得到的值是选中第一个box的value值，后面被选择的将被忽略。如果没有选择是null
*/

//选择全部的多选框，输入第一个参数是table的id 全选id js_selectAll 单选id js_selectAll
//可以排除掉以'js_select'开头的checkbox
//<input type="checkbox" id="js_selectAll" onClick="allSelect('mainTable')">
var util_js_flag=0;
function allSelect(tableId){
   var checkBoxTable=document.getElementById(tableId);
   var objs =checkBoxTable.getElementsByTagName("input");
   //ie浏览器根据操作原件选择状态，其他浏览器使用util_js_flag变量
   if(getOs()!=1){
	  if(util_js_flag==0){
	     for (var i = 0; i < objs.length; i++){
			 if(objs[i].type=="checkbox" && objs[i].id.substring(0,9)!="js_select" ){
			     objs[i].checked=true; 
			 }
	     } 
	     util_js_flag=1;     
	  }
	  else{
	     for (var i = 0; i < objs.length; i++){
			 if(objs[i].type=="checkbox" && objs[i].id.substring(0,9)!="js_select" ){
			    objs[i].checked=false; 
			 }
	     }  
	     util_js_flag=0;      
	  }  	
   }
   else{
   	   var srcElem=window.event.srcElement.checked;
   	   for (var i = 0; i < objs.length; i++){
			 if(objs[i].type=="checkbox" && objs[i].id.substring(0,9)!="js_select" ){
			     objs[i].checked=srcElem; 
			}
	   } 
   }
}

function oppSelect(tableId){
   var checkBoxTable=document.getElementById(tableId);
   var objs =checkBoxTable.getElementsByTagName("input");
   for (var i = 0; i < objs.length; i++){
	   if(objs[i].type=="checkbox" && objs[i].id.substring(0,9)!="js_select"){
	      if(objs[i].checked){
	          objs[i].checked=false;
	      }
	      else{
	          objs[i].checked=true;
	      }
	   }
   }	
}

//只能且必须选择1个多选框，输入参数是table的id,必须选一个的警告，只能选一个的警告
function oneSelect(tableId){
    var checkBoxTable=document.getElementById(tableId);
    var objs =checkBoxTable.getElementsByTagName("input");
    var count=0;
    for (var i = 0; i < objs.length; i++){
	   //需要将不是checkbox类型的元素过滤，objs[i].id!="allSelectCheckbox" 是要排除全选框干扰
        if(objs[i].type=="checkbox" && objs[i].checked && objs[i].id.substring(0,9)!="js_select"){
	        count++;
        }
    }
    if(count==0){
  	   alert("请选择一条记录!");
 	   return false;
    }
    else if(count>1){
        alert("只能选择一条记录!");
	    return false;
    }
    return true;
}
//得到选择了多少个多选框
function getCheckNum(tableId){
   var checkBoxTable=document.getElementById(tableId);
   var objs =checkBoxTable.getElementsByTagName("input");
   var result=0;
   for (var i = 0; i < objs.length; i++){
       if(objs[i].type=="checkbox" && objs[i].checked && objs[i].id.substring(0,9)!="js_select"){
           result++;
	   }
   }
   return result;
}

function getIdStr(elemName){
	var result="";
	var elem=document.getElementsByName(elemName);
    if(elem==null){
		 alert("getElem error:not elem named:"+elemName);
	 }
	for(var i=0;i<elem.length;i++){
		if(elem[i].checked){
			result+=elem[i].id;
			result+=",";
		}
	} 
	if(result!=""){
		result=result.substring(0,result.length-1);
	}
	return result;
}

//得到CheckBox选择的值
function getCheck(elemName){
	var result="";
	var elem=document.getElementsByName(elemName);
	for(var i=0;i<elem.length;i++){
		if(elem[i].checked){
			result+=elem[i].value;
			result+=",";
		}
	} 
	if(result!=""){
		result=result.substring(0,result.length-1);
	}
	return result;
}
//初始化checkbox的方法,key可能是单个值，也可能是checkbox的value连接字符串
function initCheck(elemName,key){
     var elem=document.getElementsByName(elemName);
	 if(elem==null){
		 alert("initCheck error:not elem named:"+elemName);
	 }
	 //多个值的初始化
	 if(key.indexOf(",")>-1){
	     var jsArr=key.split(",");
	     for(var m=0;m<elem.length;m++){
	      	 elem[m].checked=false;
			 for(var n=0;n<jsArr.length;n++){
				 if(elem[m].value==jsArr[n]){
					 elem[m].checked=true;
				 }
             }
         } 
	 }
	 //单个值的初始化
	 else{
	      for(var i=0;i<elem.length;i++){
			 if(key==elem[i].value){
				 elem[i].checked=true;
			 }
			 else{
				 elem[i].checked=false;
			 }
         } 
	 }
}
/*====================================================================
radiobox部分属性，和方法。radiobox的name相同，value不同，尽量不要给它使用id属性
默认没有选择时按name得到的值是选中radio的value值，如果没有选择得到null
*/
//得到radio选择的值,注意radiobox和checkbox都要使用getElementsByName而不是getElementByNames(多了个s)
function getRadio(elemName){
	 var result="";
     var elem=document.getElementsByName(elemName);
     for(var i=0;i<elem.length;i++){
         if(elem[i].checked){
             result=elem[i].value;
		 }
     } 
	 return result;
}
//初始化radiobox的方法
function initRadio(elemName,key){
     var elem=document.getElementsByName(elemName);
	 if(elem==null){
		 alert("initRadio error:not elem named:"+elemName);
	 }
     for(var i=0;i<elem.length;i++){
         if(elem[i].value==key){
             elem[i].checked=true;
		 }
     } 
}
/*====================================================================
/*   四舍五入方法：
*    forDight(Dight,How):数值格式化函数，Dight要格式化的  数字，How要保留的小数位数。   
*/   
function  forDight(Dight,How){   
    Dight=Math.round(Dight*Math.pow(10,How))/Math.pow(10,How);   
    return  Dight;   
}  

//页面上的控件根据单选框选择情况显示,key是显示的控件,value是所有被
function displayDiv(key,value){
	document.getElementById(key).style.display="block";
	var arr=value.split(",");
	for(var i=0;i<arr.length;i++){
		if(key!=arr[i]){
			document.getElementById(arr[i]).style.display="none";
		}
	}
}

//得到table的行数和第一行的列数
function getRows(tableId){
	var table=document.getElementById(tableId);
	return checkBoxTable.getElementsByTagName("tr").length;
}
function getCols(tableId){
	var table=document.getElementById(tableId);
	var trObjs =checkBoxTable.getElementsByTagName("tr");
	return trObjs[0].getElementsByTagName("td").length;
}

//输入一个数组和数组对应的编号，返回数组内容，以特定符合分割
function getArrValue(arr,str,key){
	var jsArr=new Array();
	var tempArr=str.split(key);
	for(var i=0;i<arr.length;i++){
        jsArr[i]=arr[tempArr[i]];
	}
}

function getOs(){
   if(navigator.userAgent.indexOf("MSIE")>0)return 1;
   if(isFirefox=navigator.userAgent.indexOf("Firefox")>0)return 2;
   if(isSafari=navigator.userAgent.indexOf("Safari")>0)return 3;   
   if(isCamino=navigator.userAgent.indexOf("Camino")>0)return 4;
   if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0)return 5;
   return 0;
}

function _bar_begin(key,allNum,nowNum){
    document.write("<div style='width:100%;height:15px;background:#999999;'><div style='width:0px; height:15px;background:#00FF00;color:#0066FF;text-align:center;' id='_in_"+key+"'></div></div>");
	if(nowNum<allNum){
	    var temp=forDight(nowNum/allNum*100,2);
		document.getElementById("_in_"+key).style.width=temp+"%";
		document.getElementById("_in_"+key).innerHTML=temp+"%";
	}
	else{
		document.getElementById("_in_"+key).style.width="100%";
		document.getElementById("_in_"+key).innerHTML="100%";
	}
	document.close();
}
