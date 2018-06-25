if (typeof CheckForm != 'object'){
	alert('CheckForm object doesn\'t exists.');
}
var TableEdit = new Object;
TableEdit.isIE = window.ActiveXObject ? true : false;
var temp = location.href.lastIndexOf("?") == -1 ? location.href.substring((location.href.lastIndexOf("/")) + 1) : location.href.substring((location.href.lastIndexOf("/")) + 1, location.href.lastIndexOf("?"));
TableEdit.actionPage=temp.replace("List","Action");
TableEdit.actionPage=TableEdit.actionPage.replace("#","");
/**
 * 创建一个可编辑区,如果内容发生变化就提交修改
 * obj rule:检查机制 key:主键值 field:需要修改的属性
 * <td id="info_<%=arr[j]%>" onclick="TableEdit.edit(this,'_5-70')"><%=info%></td>
 * _blank 代表用户可以输入空，没有该属性的话用户就不能将已经有的值设置为空了
 */
 
TableEdit.edit = function(obj,rule,key,value,field){
    var paramNum=arguments.length;
    /* 保存原始的内容 */
	var org = "";
	var val = "";
    var elem=obj.firstChild;
    //如果当前没有原件就不检查合法性
    if(elem!=null){
    	var tag = obj.firstChild.tagName;
		if (typeof(tag) != "undefined" && tag.toLowerCase() == "input"){
			return;
		}
		org=obj.innerHTML;
		val=org;
    }
	else{
		val = TableEdit.isIE ? obj.innerText : obj.textContent;
        elem=obj.firstChild;
	}
	
	/* 创建一个输入框 */
	var txt = document.createElement("INPUT");
	txt.value = (val == 'N/A') ? '' : Check.trimHtml(val);
	txt.style.width = (obj.offsetWidth + 20) + "px" ;
	
	/* 隐藏对象中的内容，并将输入框加入到对象中 */
	obj.innerHTML = "";
	obj.appendChild(txt);
	txt.focus();
	txt.select();
	/* 编辑区失去焦点的处理函数 */
	txt.onblur = function(e){
	    var tempTxtValue=Check.trim(txt.value);
	    //用户输入与原来一样
		if(tempTxtValue==Check.trim(org)){
			obj.innerHTML = org;
			return;
		}
		//用户不输入，需要检查是否允许空，如果不允许就用原值填充，否则不改变
		else if(tempTxtValue==""){
			if(rule.indexOf("_blank")>-1){
			    obj.innerHTML = "";
			}
			else{
				obj.innerHTML = org;
			}
			return;
		}
		else{
		   //是否需要检查
		   if(rule!="" && rule.indexOf("_")==0){
			    rule="_"+rule;
			}
			else{
				obj.innerHTML = "<span style=\"color:#FF0000\">"+tempTxtValue+"</span>";
			    return;
			}
			//检查用户输入是否合法
		    var success=CheckForm.js_begCheck(TableEdit.getTitle(obj),tempTxtValue,rule);
			if(!success){
				obj.innerHTML = org;
			}
			else{
				if(paramNum<3){
			     	obj.innerHTML = "<span style=\"color:#FF0000\">"+tempTxtValue+"</span>";
			    }
			    else{
			        //检查无刷新提交所使用的ajax.js是否存在
			   		if (typeof ajax != 'object'){
						alert('ajax object doesn\'t exists.');
						return;
					}
					objAjax.URL=TableEdit.actionPage;
					objAjax.QueryString="op=edit&"+key+"="+value+"&"+field+"="+tempTxtValue;
					//alert(objAjax.URL+"?"+objAjax.QueryString);
					var pageContent=objAjax.RemoteOperate();
					//去除页面中的js代码和html代码
					pageContent=Check.trimJs(pageContent);
					pageContent=CheckForm.trimCode(pageContent);
					if(pageContent!=1){
						alert("修改错误");
						obj.innerHTML = org;
					}
					else{
						obj.innerHTML = tempTxtValue;
					}	
				}
			}
		}
	};
};
TableEdit.getTitle=function(obj){
    var index=obj.cellIndex;
    return obj.parentNode.parentNode.parentNode.rows[0].cells[index].innerHTML;
};