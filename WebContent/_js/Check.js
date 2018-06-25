var Check = new Object();
Check.checkTextLength = function(textId,maxLength,alertStr){
    var checkText=document.getElementById(textId);
	var str=checkText.value;
	if(str!=undefined) {
		var trueLength=0;
		for(i=0;i<str.length;i++) {
			if( str.charCodeAt(i)>0 && str.charCodeAt(i)<255 ){
				trueLength++;
			}else{
				// if char have chinese then length+2
				trueLength+=2;
			}
		}
		if(trueLength > maxLength) {
			window.alert(alertStr);
			checkText.focus();
			return false;
		}
		return true;
	}
	else {
		return false;
	}
}

//get str length,chinese word count 2
Check.getTextLength = function(str){	
	if(str!=undefined) {
		var trueLength=0;
		for(i=0;i<str.length;i++) {
			if( str.charCodeAt(i)>0 && str.charCodeAt(i)<255 ){
				trueLength++;
			}else{2
				trueLength+=2;
			}
		}
		return trueLength;
	}
	else {
		return 0;
	}
}
//验证时间的正确性，可以输入空值,根据返回值可以判断输入时间的大小，参数blanked是否可以为空,false表示不能为空，equalsed时间是否可以相等
//返回true表示开始时间小于结束时间（正常）,false表示开始时间大于结束时间
Check.checkDate = function(begEme,endEme,blanked,equalsed){
	//首先检验输入控件是否存在
    var beg;
    var end;
	var elemBeg=document.getElementById(begEme);
    var begDate="";
	if(elemBeg!=null){
	 
        begDate=Check.trim(elemBeg.value);
	}
	else{
        alert("起始时间控件不存在");
		return false;
	}
	var elemEnd=document.getElementById(endEme);
    var endDate="";
	if(elemEnd!=null){
        endDate=Check.trim(elemEnd.value);
	}
	else{
        alert("结束时间控件不存在");
		return false;
	}
    //检查输入为空条件是否成立
	if(!blanked){
         if(begDate==""){
		    alert("起始时间不能为空");
		    elemBeg.focus();
		    return false;
         }
         if(endDate==""){
			alert("结束时间不能为空");
			elemEnd.focus();
			return false;
         }
	}
    //检测输入的时间是否属于合法格式
    if(begDate!=""){
		if(!Check.isIllDate(begDate)){
			alert("起始时间格式错误");
			elemBeg.focus();
			return false;
		}
		//将输入时间格式化成统一的格式
		beg=Check.formatDate(begDate);
		//将转换后的格式替代原来的值
		elemBeg.value=beg;
		beg=beg.replace(new RegExp("-", 'g'), "");
		beg=beg.replace(new RegExp(" ", 'g'), "");
		beg=beg.replace(new RegExp(":", 'g'), "");
    }
    if(endDate!=""){
		if(!Check.isIllDate(endDate)){
			alert("结束时间格式错误");
			elemEnd.focus();
			return false;
		}
		end=Check.formatDate(endDate);
		elemEnd.value=end;
		end=end.replace(new RegExp("-", 'g'), "");
		end=end.replace(new RegExp(" ", 'g'), "");
		end=end.replace(new RegExp(":", 'g'), "");	
    }
    if(begDate=="" || endDate==""){
        return true;
    }
    //此时的数据全部不为空了
    //两个值是否可以相等
    if(!equalsed){
		if(beg==end){
		   alert("起始时间不能等于结束时间");
		   elemBeg.focus();
		   return false;
		}
    }
	if(beg>end){
		   alert("起始时间不能大于结束时间");
		   elemBeg.focus();
		   return false;
	}
    return true;
}

//将时间格式化成统一的形式2007-1-8,2007-1-8 12:23:45格式都可以格式
Check.formatDate = function(str){
    //如果长度符合规定长度就不做检测直接返回
    if(str.length==10 || str.length==14){
        return str;
    }
    var year=str.substring(0,str.indexOf("-"));
    var beg=str.indexOf("-");
    var end=str.lastIndexOf("-");
    var month=str.substring(beg+1,end);
	if(month.length<2){
		month="0"+month;
	}
    if(str.length<10){
        var day=str.substring(end+1,str.length);
		if(day.length<2){
			day="0"+day;
		}
        return year+"-"+month+"-"+day;
    }
    else{
        var day=str.substring(end+1,str.indexOf(" "));
		if(day.length<2){
			day="0"+day;
		}
        var time=str.substring(str.indexOf(" "));
        return year+"-"+month+"-"+day+time;
    } 
}

//自定义trim函数,去除字符串前面和后面的空字符，字符串中间的空字符不会被替换
Check.trim = function(str){
    return str.replace(/(^[\s]*)|([\s]*$)/g, "");
}

//自定义trim函数,去除字符串所有的空字符
Check.trimAll = function(str){
	return str.replace(/\s/g,"");
}
Check.trimAllGbk = function(str){
    var temp=str.split("　");
    var resultStr="";
    for(i = 0; i < temp.length; i++){
        resultStr+=temp[i];
    }
    return resultStr;
}
Check.trimJs = function(str){
	return str.replace(/<(script|%)[\s\S]*?\/\1>/ig,"");
}
//删除某个标签内部的js代码 ,可以是body,html
Check.trimJsType = function(str){
	return str.replace(/<(script|body|%)[\s\S]*?\/\1>/ig,"");
}

Check.trimHtml = function(str){
	return str.replace(/<[^>]*>/g,"")
}
// 替换全部
Check.replaceAll = function(str,key,value){   
	return str.replace(new RegExp(key,"g"),value);    
}
//判断输入框，文本框是否为空
Check.isBlank = function(inputId,altStr){
	var myInput=document.getElementById(inputId);
	if(myInput.value==""){
		alert(altStr);
		myInput.focus();
		return false;
	}
	return true;
}
Check.isEquals = function(inputId1,inputId2,altStr){
    var myInput1=document.getElementById(inputId1).text;
	var myInput2=document.getElementById(inputId2).text;
	if(myInput1!=myInput2){
        alert(altStr);
		return false;
	}
	return true;
}
Check.isNormal = function(str){
     var exp = /^\w+$/;
     var p = new RegExp(exp);
     if (p.test(str))return true;
     return false; 
}

//验证是正数数字类型
Check.isPosNumber = function(str){
	var patrn=/^[0-9]\d*$/ ;
	if(!patrn.exec(str)) return false;
	return true;
}

//验证是否是年龄型，不可以是负数，不能以0开头
Check.isAge = function(str){
	var patrn=/^[1-9][0-9]{0,1}$/ ;
	if(!patrn.exec(str)) return false;
	return true;
}

//验证是否是数字型，以0开头只能有一个0，可以是负数
Check.isNumber = function(str){
	var patrn=/^(\+|-)?(0|[1-9]\d*)(\.\d*[1-9])?$/ ;
	if(!patrn.exec(str)) return false;
	return true;
}

//判断是否属于金额类型，同时小数点后只能有两位，可以是负数
Check.isMoneyAll = function(str){
	var patrn=/^(-)?(0|[1-9]\d*)(\.\d{1,2})?$/;
	if(!patrn.exec(str)) return false;
	return true;
}

//判断是否属于金额类型，同时小数点后只能有两位，不可以是负数
Check.isMoney = function(str){
	var patrn=/^(0|[1-9]\d*)(\.\d{1,2})?$/;
	if(!patrn.exec(str)) return false;
	return true;
}

//判断email地址是否正确
Check.isEmail = function(str){ 
	var patrn=/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
	if(!patrn.exec(str)) return false;
	return true;
}

//判断时间格式是否正确
Check.isDate = function(str){
	//如果时间是长格式的
	if(str.length>10){
		var patrn=/^(\d{4})\-(\d{2})\-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/; 
	}
	else{
		var patrn=/^(\d{4})\-(\d{2})\-(\d{2})$/;
	}
	if(!patrn.exec(str)) return false;
	return true;
}

//判断时间格式是否含有不正常的正确时间，例如2007-1-4 12:23:34
Check.isIllDate = function(str){
    var patrn1;
    var patrn2;
    var patrn3;
    var result=false;
	//如果时间是长格式的
	if(str.length==19){
		patrn1=/^(\d{4})\-(\d{2})\-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/; 
	}
	else if(str.length>10){
		patrn1=/^(\d{4})\-(\d{1})\-(\d{1}) (\d{2}):(\d{2}):(\d{2})$/; 
        patrn2=/^(\d{4})\-(\d{1})\-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/; 
        patrn3=/^(\d{4})\-(\d{2})\-(\d{1}) (\d{2}):(\d{2}):(\d{2})$/; 
	}
	else if(str.length==10){
		patrn1=/^(\d{4})\-(\d{2})\-(\d{2})$/;
	}
	else if(str.length<10){
		patrn1=/^(\d{4})\-(\d{1})\-(\d{2})$/;
        patrn2=/^(\d{4})\-(\d{2})\-(\d{1})$/;
        patrn3=/^(\d{4})\-(\d{1})\-(\d{1})$/;
	}
    if(patrn1!=undefined){
        if(patrn1.exec(str)){
            result=true;
        }
    }
    if(patrn2!=undefined){
        if(patrn2.exec(str)){
            result=true;
        }
    }
    if(patrn3!=undefined){
        if(patrn3.exec(str)){
            result=true;
        }
    }	
	return result;
}

//判断手机格式是否正确
Check.isMobile = function(str){
	//不是完整的11位手机号或者正确的手机号前七位
	var patrn = /^1[3|5][0-9]\d{4,8}$/; 
	if(!patrn.exec(str)) return false;
	return true;
}

//判断颜色格式是否正确
Check.isColor = function(str){
	var patrn = /^#[0-9a-fA-F]{6}$/; 
	if(!patrn.exec(str)) return false;
	return true;
}

//判断字符穿是否是以关键字结尾的
Check.endWith = function(str,key){
	if(str.lastIndexOf(key)==-1){
        return false;
	}
    var index=str.lastIndexOf(key);
	if(str.length==index+key.length){
        return true;
	}
	return true;
}
//判断字符穿是否是以关键字开头的
Check.begWith = function(str,key){
	if(str.indexOf(key)==0){
		if(str.length==str.indexOf(key)+key.length){
			return true;
		}
	}
	return false;
}

//判断文件格式,验证格式"gif,jpg,bmp"
Check.isFile = function(str,key){
	var type=str.toLowerCase();
	var arr=key.split(",");
	var right=false;
	for(var i=0;i<arr.length;i++){
		if(Check.endWith(type,arr[i])){
	        right=true;
			break;
		}
	}
	if(!right){
	     alert("请选择"+key+"格式文件");
		 return false;
	}
	return true;
}