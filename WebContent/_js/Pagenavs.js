//Pagenavs.init("teacherDetailAjax.jsp","ajaxDiv","",param,5);
var Pagenavs = new Object;
Pagenavs.js_alert="";
Pagenavs.ajaxFile="";
Pagenavs.txtDiv="";
Pagenavs.pageDiv="";
Pagenavs.param="";
Pagenavs.pagesize=10;
Pagenavs.pagelist=10;
Pagenavs.getpagenum=1;
Pagenavs.recordcount=0;
Pagenavs.pagenojs=1;
Pagenavs.init = function(ajaxFile,txtDiv,pageDiv,param,pagesize){
	if (typeof objAjax != 'object'){
		alert("pagenavs objAjax doesn\'t exists.");
		reutrn;
	}
	if(document.getElementById(txtDiv)==null){
		alert("pagenavs.ajax_table_div is not exists");
		return;
	}
	if(pageDiv!="" && document.getElementById(pageDiv)==null){
		alert("pagenavs.pages_div is not exists");
		return;
	}

	Pagenavs.ajaxFile=ajaxFile;
	Pagenavs.txtDiv=txtDiv;
	Pagenavs.pageDiv=pageDiv;
	if(param.indexOf("&")!=0){
		this.param="&"+param;
	}
	else{
		this.param=param;
	}
	if (typeof pagesize == 'number'){
		this.pagesize=pagesize;	
	}

	objAjax.URL=this.ajaxFile;
	objAjax.QueryString="op=getRecordCount"+this.param;
	Pagenavs.recordcount=objAjax.RemoteOperate();
	if(isNaN(this.recordcount)){
		alert("pagenavs.getRecordcount is error:"+this.recordcount);
	}
	if(this.js_alert){
		alert("Pagenavs.recordcount:"+this.recordcount);
	}
	if(Pagenavs.recordcount<1){
		document.getElementById(this.txtDiv).innerHTML="";
		if(document.getElementById(this.pageDiv)!=null){
			document.getElementById(this.pageDiv).innerHTML="";
		}
	}
	else{
		this.pagefun(1);
	}
};
Pagenavs.pagefun=function(pageno){
	this.pagecount = parseInt((this.recordcount - 1) / this.pagesize + 1);
	if(isNaN(this.pagecount)|| this.pagecount<1){this.pagecount=1;}
	var pageinfhtml="";
	var pageno=parseInt(pageno);
	pageno=(pageno<1)?1:pageno;
	pageno=(pageno>this.pagecount)?this.pagecount:pageno;
	this.pagenojs=pageno;
	//alert(pageno+"    "+this.pagelist+"  "+this.pagesize+"   "+this.numberperline+"   "+this.recordcount+"   "+this.pagecount);
	if(this.pagecount>1){
		pageinfhtml+="总计<font color=\"#FF0000\">"+this.recordcount+"</font>个记录分为<font color=\"#FF0000\">"+this.pagecount+"</font>页   ";
		if(pageno>1){
			pageinfhtml+="<a href=\"javascript:void(0)\" onclick=\"javascript:Pagenavs.pagefun('1')\" title=\"第一页\" onfocus=\"this.blur()\">&nbsp;&lt;&lt;&nbsp;</a>";
			pageinfhtml+="<a href=\"javascript:void(0)\" onclick=\"javascript:Pagenavs.pagechange(-1)\" title=\"上页\" onfocus=\"this.blur()\">&lt</a>";
			
		}
		else{
			pageinfhtml+="<span class=\"disabled\">&nbsp;&lt;&lt;&nbsp;</span>";
			pageinfhtml+="<span class=\"disabled\">&lt</span>";	
		}	
		
		if(this.pagecount<=this.pagelist){
			for(i=0;i<this.pagecount;i++){
				temp=i+1;
				if(i+1==pageno){
					pageinfhtml+="<span class=\"current\">"+temp+"</span>";
				}
				else{
					pageinfhtml+="<a href=\"javascript:void(0)\" onclick=\"javascript:Pagenavs.pagefun('"+temp+"')\" onfocus=\"this.blur()\">&nbsp;"+temp+"&nbsp;</a>";
				}
			}
		}
		else{
			var pagenobegin=1;
			var pagenoend=10;
			var pagelisthalf=parseInt((this.pagelist+1)/2);
			if((pageno-pagelisthalf)<=0){
				pagenobegin=1;
				pagenoend=this.pagelist;
			}
			else if((pageno+pagelisthalf+this.getpagenum)>this.pagecount){
				pagenobegin=this.pagecount-this.pagelist+1;
				pagenoend=this.pagecount;		  
			}
			else{
				pagenobegin=pageno-pagelisthalf+this.getpagenum;
				pagenoend=pagenobegin+this.pagelist-1;		  
			}
			for(i=pagenobegin;i<pagenoend+1;i++){
				if(i>this.pagecount)break;
				if(i==pageno){
					pageinfhtml+="<span class=\"current\">"+i+"</span>";
				}
				else{
					pageinfhtml+="<a href=\"javascript:void(0)\" onclick=\"javascript:Pagenavs.pagefun('"+i+"')\" onfocus=\"this.blur()\">"+i+"</a>";
				}
			}
		}
		
		if(pageno<this.pagecount){
			pageinfhtml+="<a href=\"javascript:void(0)\" onclick=\"javascript:Pagenavs.pagechange(1)\" onfocus=\"this.blur()\" title=\"下页\">&gt;</a>";
			pageinfhtml+="<a href=\"javascript:void(0)\" onclick=\"javascript:Pagenavs.pagefun('"+this.pagecount+"')\" title=\"最后页\" onfocus=\"this.blur()\">&nbsp;&gt;&gt;&nbsp;</a>";
		}
		else{
			pageinfhtml+="<span class=\"disabled\">&gt;</span>";
			pageinfhtml+="<span class=\"disabled\">&nbsp;&gt;&gt;&nbsp;</span>";
		}
		if(this.pagecount>1){
			if(document.getElementById(this.pageDiv)!=null){
				document.getElementById(this.pageDiv).innerHTML=pageinfhtml;
				
			}
		}
		else{
			if(document.getElementById(this.pageDiv)!=null){
				document.getElementById(this.pageDiv).innerHTML="";
			}
		}
	}
	else{
		if(document.getElementById(this.pageDiv)!=null){
			document.getElementById(this.pageDiv).innerHTML="";
		}
	}
	if(this.recordcount>0){
		this.getPageContent(pageno);
	} 
};
Pagenavs.getPageContent=function(pagenoFromNav){
	objAjax.URL=this.ajaxFile;
	objAjax.QueryString="op=list&pageno="+pagenoFromNav+"&pagesize="+this.pagesize+"&recordcount="+this.recordcount+this.param;
	document.getElementById(this.txtDiv).innerHTML=objAjax.RemoteOperate();
	if (typeof TableColor == 'object'){
		TableColor.init();	
	}
};
Pagenavs.pagechange=function(step){
	step=parseInt(step);
	var pagenochange=this.pagenojs+step;	
	if(pagenochange<1){
		 alert("已经到第一页了！");
		 return;	
	}
		
	if(pagenochange>this.pagecount){
		alert("已经到最后页！");
		return;
	}
	this.pagefun(pagenochange);
};