var TableSort = new Object;
TableSort.mySort= function(sort_by,sort_order,param){
	this._css_table=document.getElementById("TableColor");
	this._css_td_arr=this._css_table.getElementsByTagName("td");
	var icoFlag="asc";
	var real_sort_order=sort_order;
	if(sort_order=="desc"){
		icoFlag="desc";
		real_sort_order="asc";
	}
	else{
		real_sort_order="desc";
	}
	for (i=1;i<this._css_td_arr.length;i++) {  
		if(this._css_td_arr[i].id.indexOf("js_sort")==0){
		    var tempTxt=this._css_td_arr[i].innerHTML;
			var elemId=this._css_td_arr[i].id.substring(8,this._css_td_arr[i].id.length);
			if(elemId==sort_by){
				this._css_td_arr[i].innerHTML="<a href=\"#\" onclick=\"TableSort.js_pageSort('"+elemId+"','"+real_sort_order+"','"+param+"')\">"+tempTxt+"<img src=\"../_js/ico/sort_"+icoFlag+".gif\"/></a>";
			}
			else{
				this._css_td_arr[i].innerHTML="<a href=\"#\" onclick=\"TableSort.js_pageSort('"+elemId+"','"+real_sort_order+"','"+param+"')\">"+tempTxt+"<img src=\"../_js/ico/sort_asc.gif\"/></a>";
			}
		}
	}
}
TableSort.js_pageSort=function(sort_by,sort_order,param){
    var tempLocation=location.href.substring(0,location.href.indexOf(".jsp")+4);
    //检查带来的参数里面是否有排序参数，如果有就清除掉
    var newParam="&";
    if(!param==""){
    	var arr=param.split("&");
    	for(var i=0;i<arr.length;i++){
    		if(arr[i].indexOf("sort_by=")>-1 || arr[i].indexOf("sort_order=")>-1 || arr[i]==""){
    			continue;
    		}
    		newParam+=arr[i];
    		if(i!=arr.length-1){
    			newParam+="&";
    		}
    	}
    }
    //alert(tempLocation+"?sort_by="+sort_by+"&sort_order="+sort_order+param);
    //alert(tempLocation+"?sort_by="+sort_by+"&sort_order="+sort_order+newParam);
    location=tempLocation+"?sort_by="+sort_by+"&sort_order="+sort_order+newParam;
}