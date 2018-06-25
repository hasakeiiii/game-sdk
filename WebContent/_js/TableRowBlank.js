var TableRowBlank = new Object;
TableRowBlank.size=30;
TableRowBlank.init=function(){
	var elem=document.getElementById("TableColor");
	if(elem==null){
		alert("TableRowBlank can't find TableColor!");
	}
	if (typeof Pagenavs == 'object'){
		TableRowBlank.size=Pagenavs.pagesize;
	}
	var row=Number(elem.getElementsByTagName("tr").length)-1;
	var trObjs =elem.getElementsByTagName("tr");
	var colTh=trObjs[row].getElementsByTagName("th").length
	var colTd=trObjs[row].getElementsByTagName("td").length;
	var col=colTh+colTd;
	for(var jsI=0;jsI<(TableRowBlank.size-row);jsI++){ 
		var objTR=elem.insertRow(row+jsI+1);  
		for(var jsJ=0;jsJ<col;jsJ++){
			var objTD1=objTR.insertCell(jsJ);
			objTD1.height="25px";
			objTD1.innerHTML="&nbsp;";
		} 
	}
}
if (document.all){
	window.attachEvent('onload',TableRowBlank.init)
}
else{
	window.addEventListener('load',TableRowBlank.init,false);
}