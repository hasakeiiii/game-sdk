function enterAsTab() {
	if (event.keyCode!=13) return true;
	var formElements=new Array();
	for(i=0;i<document.forms.length;i++){
		for(j=0;j<document.forms[i].elements.length;j++){
		    formElements[formElements.length]=document.forms[i].elements[j];
		}
	}
	for (i=0;i <= document.all.length - 1; i++){
        if (event.srcElement != formElements[i]) continue;
        if ((formElements[i].type=="button")||(formElements[i].type=="reset")||(formElements[i].type=="submit")){
        formElements[i].click();
            break;
        }
	
        if (formElements[i+1] == null || formElements.length<=(i+1)){break;}
        for(j = i+1; j < formElements.length; j++){
            if ((formElements[j].disabled == false) && (formElements[j].type != "hidden")){
                try{
                    formElements[j].focus();
                    if (formElements[j].type=="text" || formElements[j].type=="password"){
                        formElements[j].click();
                        formElements[j].select();
                    }
                    break;
                }catch(e){};
            }
        }
        break;
	}
	event.returnValue = false;
}
//------------------------------------------------回车键自动提交
/*
var elem=document.getElementById("m_userpwd");
if(elem.addEventListener){
	elem.addEventListener("keypress",mykeypdown,false);
}else{
	elem.attachEvent("onkeypress",mykeypdown);
}
function mykeypdown(evt){
	if(evt.keyCode==13){
	  
	}
}
*/
//================================================================================
//form表单的验证。如果控件有alt属性则被检查。找到相同"title_"加name控件的innerHTML然后提示
//离开控件立刻检测  onblur="CheckForm.js_checkElem(this)",新版本采用表单自动绑定验证时间,如果需要使用则调用CheckForm.formBind("formId");
/*
window.onload = function() {
	CheckForm.formBind("mainForm");
	CheckForm.alertType="";//表示页面使用页面不使用弹出框提示,默认使用弹出框提示
}
*/
//使用提交按钮时检测 if(!CheckForm.checkForm("mainForm"))return;
//验证规则绑定在alt属性上，不同规则使用"_"分隔
//radio和checkbox如果一组有多个元素，任意一个加alt都会对全组检查
//_key 不允许特殊字符，_6-16长度检查，_email 邮箱地址检查，
//_>
// _int 整型检查，_double double型检查，_(1-30)数字在1-30之间不包括1和30，_[1-30]数字在1-30之间包括1和30,暂时不支持无上限或无下限的数字
// 是否是金额数字_money 小数点后只能有两位
//_null可以为空有输入就检查，_method:xxx() 验证后回调方法， _span:不使用默认的提示span 使用公用的span进行提示，冒号后跟span的id，
//method中调用的方法必须要有返回true,false
//可以针对任意元素里面的元素进行验证,如table,div 必须使用它们的id属性
//javascript验证是使用alert形式还是innerHTML形式使用属性CheckForm.alertType控制
//CheckForm.trace属性用于调试,查看在那个元素被中断
//可以自己设置验证通过信息和提示图片
//特殊的验证可以调用js_checkPass和js_checkUnPass方法
//外部文件检查单个元素可以使用if(CheckForm.js_begCheck(elemInfoTitle,elemValue,alt,elem))return;方法
if (typeof Check != 'object'){
	alert('Check object doesn\'t exists.');
}
var CheckForm=new Object();
CheckForm.alertType="alert";
CheckForm.rigthIco="";
CheckForm.errorIco="";
CheckForm.trace=false;//是否弹出调试信息
CheckForm.js_right_hint="<img src='../_js/ico/icon_right.gif' align='absmiddle'/><span style='color:#009900'> 已通过验证</span>";
CheckForm.js_error_hint="<img src='../_js/ico/icon_error.gif' align='absmiddle'/>&nbsp;<span style='color:#FF0000'>";
CheckForm.js_error_hint_end="</span>"
CheckForm.js_tempPass;//验证两次密码是否相同使用的临时变量
CheckForm.js_tempElem;
CheckForm.js_tempAlert="";
CheckForm.checkForm=function(formId){
	var js_checkForm=document.getElementById(formId);
	if(js_checkForm==null){
		alert("there is no form id is:"+formId);
	}
	
	for (var i=0; i<js_checkForm.elements.length; i++){
		var elem=js_checkForm.elements[i];
		var elemType=elem.type;
		//alt不为空时才进行验证
		var alt=elem.getAttribute("alt");
		if(alt==null || alt==""){
			continue;
		}
		if(this.trace){
			alert("beg:"+elem.name+"   "+alt);
		}
		var elemTd=document.getElementById("title_"+elem.name);
		if(elemTd==null){
		   alert("no title element id is:"+"title_"+elem.name);
		   return false;
		}
	   var elemInfoTitle=this.trimCode(elemTd.innerHTML);
	   var elemValue=Check.trim(elem.value);
	   if(!this.js_checkElem(elemInfoTitle,elemValue,alt,elem))return false;
	   if(this.trace){
			alert("end:"+elem.name+"   "+alt);
		}
	}//end for
	return true;
}
//--------------------------------------------------------------------------------------------------------------------
//检查单个元素是否符合标准,用于一离开控件就开始检查
//页面调用单一控件时候使用if(!CheckForm.js_checkElemBlur(document.mainForm.my_int))return false;
CheckForm.js_checkElemBlur=function(elem){
    var elemInfoTitle="";
    var elemTd=document.getElementById("title_"+elem.name);
    if(elemTd!=null){
    	elemInfoTitle=this.trimCode(elemTd.innerHTML);
    }
    var alt=elem.getAttribute("alt");
    var elemValue=Check.trim(elem.value);
    //alert(elemInfoTitle+"--"+elemValue+"--"+alt+"---"+elem.name);
	if(!CheckForm.js_checkElem(elemInfoTitle,elemValue,alt,elem))return false;
}
CheckForm.js_checkElem=function(elemInfoTitle,elemValue,alt,elem){
	//参数是4个就说明是某个控件引发的检查
	if(arguments.length==4){
		var elemType=elem.type;
		if (elemType=="text" || elemType=="textarea" || elemType=="password"){
		   this.js_tempElem=elem;
	       if(!this.js_begCheck(elemInfoTitle,elemValue,alt,elem))return false;
		   if(elemType=="password"){
				if(this.js_tempPass==null){
					this.js_tempPass=elem;
				}
				else{
					if(this.js_tempPass.value!=elemValue){
						this.js_alertType("两次密码不相同!",elem.name);
						return false;
					}
				}
		   }
	    }
		else if(elemType=="radio" || elemType=="checkbox"){
			var hasSelect=false;
			var elemBox=document.getElementsByName(elem.name);
			for(var j=0;j<elemBox.length;j++){
				if(elemBox[j].checked){
					hasSelect=true;
					break; 
				}
			}
			if(!hasSelect){
				this.js_alertType("请选择"+elemInfoTitle+"!",elem.name);
				return false;
			}
	   }
	   else if(elemType=="select-one"){
		   if(elem.options[elem.options.selectedIndex].value=="-1" || elem.options[elem.options.selectedIndex].value==""){
			   this.js_alertType("请选择"+elemInfoTitle+"!",elem.name);
			   return false;
		   }
	   }
	  //如果不是使用公用的提示span,否则使用单独的成功提示span
   	  this.js_right_show(elem);
	}
	//不是控件引发的检查
	else{
		if(!this.js_begCheck(elemInfoTitle,elemValue,alt))return false;
	}
    /*
	//如果有特殊的验证需求就调用页面的一个验证方法进行验证
   if(elem.id.indexOf("_method:")>-1){
	   var temp=this.js_getMethod(elem.id);
	   var success=eval(temp);
	   if(!success){
		   return false;
	   }
   }
   * */
   return true;
}
//============================--------------------------------------
CheckForm.js_begCheck=function(elemInfoTitle,elemValue,alt,elem){
	var warnElem="";
	if(arguments.length==4){
		warnElem=elem.name;
	}
	//alt带有null就可以为空，否则必须有输入
    if(alt.indexOf("_null")==-1 && elemValue==""){
		this.js_alertType("请输入"+elemInfoTitle+"!",warnElem);
	    return false;
    }
	//输入长度验证
	if(alt.indexOf("-")>-1){
	   var textLength=this.js_getLength(alt);
	   //使用"-"split是因为在js_getLength函数中"-"已经被","替换
	   var tempLength=textLength.split(",");
	   var trueLength=Check.getTextLength(elemValue);
	   if(tempLength[0]==tempLength[1]){
	   	    if(trueLength!=tempLength[0]){
	   	    	this.js_alertType(elemInfoTitle+"必须是"+tempLength[0]+"个字符",warnElem);
	   	    	return false;
	   	    }
	   }
	   else if(trueLength<tempLength[0] || trueLength>tempLength[1]){
		   var text=elemInfoTitle+"长度必须为"+tempLength[0]+"-"+tempLength[1]+"个字符";
		   if(alt.indexOf("key")==-1){
			   text+="(汉字为两个)";
		   }
		   this.js_alertType(text,warnElem);
		   return false;
	   }
	}
	//不能包含特殊字符
	if(alt.indexOf("_key")>-1){
	   if(!Check.isNormal(elemValue)){
		   this.js_alertType(elemInfoTitle+"必须是数字或英文",warnElem);
		   return false;
	   }
	}
	else if(alt.indexOf("_int")>-1){
	   if(!Check.isPosNumber(elemValue)){
		   this.js_alertType(elemInfoTitle+"必须为数字型",warnElem);
		   return false;
	   }
	   //检查控件是否除了_int检查之外有别的约束条件
	   if(!this.getAct(elemInfoTitle,elemValue,alt,warnElem))return false;
	}
	else if(alt.indexOf("_double")>-1){
	   if(!Check.isMoney(elemValue)){
		   this.js_alertType(elemInfoTitle+"必须为数字型",warnElem);
		   return false;
	   }
	   //检查控件是否除了_double检查之外有别的约束条件
	   if(!this.getAct(elemInfoTitle,elemValue,alt,warnElem))return false;
	}
	else if(alt.indexOf("email")>-1){
	   if(!Check.isEmail(elemValue)){
		   this.js_alertType(elemInfoTitle+"不符合xxx@163.com格式",warnElem);
		   return false;
	   }
	}
   return true;
}
//-------------------------------错误提示调用方法------------------------------------------------------------
//如果只使用alert提示就输入需要提示的内容，使用控件提示就输入提示控件warn_的后缀
CheckForm.js_alertType=function(str,warnElem){
    //如果alert控件存在就使用alert控件，否则使用标准的alert
    if(typeof(error) == 'undefined'){
		alert(str);
	}else{
	    error(str);
	}
	if(warnElem!=""){
		//先找具体的提示span
		if(document.getElementById("warn_"+warnElem)!=null){
			document.getElementById("warn_"+warnElem).innerHTML=this.js_error_hint+str+this.js_error_hint_end;
		}
		//找不到然后查看是否有公用的提示sapn
		else if(document.getElementById("error_info")!=null){
			document.getElementById("error_info").style.display="block";
			document.getElementById("error_info").innerHTML=this.js_error_hint+str+this.js_error_hint_end;
		}
	}
	/*
	if(this.js_tempElem!=null){
		this.js_tempElem.value="";
		CheckForm.js_tempAlert=this.js_tempElem.name;
		this.js_tempElem.focus();
	}
	*/
}
//-------------------------------正确提示调用方法------------------------------------------------------------
CheckForm.js_right_show=function(elem){
	if(document.getElementById("warn_"+elem.name)!=null){
		document.getElementById("warn_"+elem.name).innerHTML=this.js_right_hint;
	}
	else if(document.getElementById("error_info")!=null){
		document.getElementById("error_info").style.display="none";
	}
}
//根据输入条件判断数字范围 elemInfoTitle:提示控件属性名称 alt:检查属性有效性关键字 elem:需要使用那个控件提示
CheckForm.getAct=function(elemInfoTitle,elemValue,alt,warnElem){
   var _otherCheck=alt.substring(alt.lastIndexOf("_int")+4,alt.length);
   if(""==_otherCheck){
		_otherCheck=alt.substring(alt.lastIndexOf("_double")+7,alt.length);
   }
   if(_otherCheck!=""){
        if(_otherCheck.indexOf("(")>-1){
             var _firstNum=_otherCheck.substring(_otherCheck.indexOf("(")+1,_otherCheck.indexOf("-"));
             if(Number(elemValue)<=Number(_firstNum)){
                  this.js_alertType(elemInfoTitle+"不能小于等于"+_firstNum,warnElem);
                  return false;
             }
        }
        else if(_otherCheck.indexOf("[")>-1){
        	var _firstNum=_otherCheck.substring(_otherCheck.indexOf("[")+1,_otherCheck.indexOf("-"));
        	if(Number(elemValue)<Number(_firstNum)){
                  this.js_alertType(elemInfoTitle+"不能小于"+_firstNum,warnElem);
                  return false;
             }
        }
        if(_otherCheck.indexOf(")")>-1){
        	var _lastNum=_otherCheck.substring(_otherCheck.indexOf("-")+1,_otherCheck.indexOf(")"));
        	if(Number(elemValue)>=Number(_lastNum)){
                  this.js_alertType(elemInfoTitle+"不能大于等于"+_lastNum,warnElem);
                  return false;
             }
        }
        else if(_otherCheck.indexOf("]")>-1){
        	var _lastNum=_otherCheck.substring(_otherCheck.indexOf("-")+1,_otherCheck.indexOf("]"));
        	if(Number(elemValue)>Number(_lastNum)){
                  this.js_alertType(elemInfoTitle+"不能大于"+_lastNum,warnElem);
                  return false;
             }
        }
   }
   return true;
}
//-----------------------------------------------------------------------------------------
CheckForm.js_getLength=function(s){
	 var temp=s.split("_");
	 for(var i=0;i<temp.length;i++){
		 if(temp[i].indexOf("-")>-1){
			 var tempValue=temp[i].split("-");
			 if(tempValue[0]=="" || tempValue[1]==""){
				  alert("get min length error:"+s);
			 }
			 return tempValue[0]+","+tempValue[1];
		 }
	 }
}
//-----------------------------------------------------------------------------------------
CheckForm.js_getMethod=function(s){
	 var temp=s.split("_");
	 for(var i=0;i<temp.length;i++){
		 if(temp[i].indexOf("method:")>-1){
			 var tempValue=temp[i].split(":");
			 if(tempValue[1]==""){
				  alert("get method error:"+s);
			 }
			 return tempValue[1];
		 }
	 }
}
//清除代码中的空格和html代码
CheckForm.trimCode=function(str){
   str=Check.trimHtml(str);
   str=str.replace("&nbsp;/g","");
   str=Check.replaceAll(str,"&nbsp;","");
   str=str.replace(":","");
   str=str.replace("：","");
   str=Check.trimAll(str);
   str=Check.trimAllGbk(str);
   return str;
}
//-----------------------------------------------------------------------------------------
CheckForm.js_checkPass=function(warnSpanId){
	if(document.getElementById(warnSpanId)!=null){
		document.getElementById(warnSpanId).innerHTML=this.js_right_hint;
	}
}
//-----------------------------------------------------------------------------------------
CheckForm.js_checkUnPass=function(str,warnSpanId){
    //如果alert控件存在就使用alert控件，否则使用标准的alert
    if(typeof(error) == 'undefined'){
		alert(str);
	}else{
	    error(str);
	}
    if(document.getElementById(warnSpanId)!=null){
		document.getElementById(warnSpanId).innerHTML=this.js_error_hint+str+this.js_error_hint_end;
	}
	else if(document.getElementById("error_info")!=null){
		document.getElementById("error_info").style.display="block";
		document.getElementById("error_info").innerHTML=this.js_error_hint+str+this.js_error_hint_end;
	}
}

//动态给页面绑定检查方法
CheckForm.formBind=function(formId){
	var js_checkForm=document.getElementById(formId);
	if(js_checkForm==null){
		alert("formBind error,there is no form id is:"+formId);
	}
	for (var i=0; i<js_checkForm.elements.length; i++){
		var elem=js_checkForm.elements[i];
		var alt=elem.getAttribute("alt");
		if(alt==null || alt==""){
			continue;
		}
		else{
		    elem.onblur = function(){
				CheckForm.js_checkElemBlur(this);
			}
		}
    }
}