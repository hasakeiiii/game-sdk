var ajax = function(){	
	var URL="";
	var QueryString="";
	this.killErrors=function(){
	    return true;
	};
	//window.onerror =this.killErrors;
	document.write("<input id=txtTempInfo style='display:none' value=0>");
	this.RemoteOperate=function()
	{
	       var objXML=this.XMLHTTP();
		   var strPage=this.URL;
		   var strQueryString=this.QueryString;
		   var strURL=strPage;
		   
	       objXML.open("post",strURL,false);
	       objXML.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	       objXML.send(strQueryString);
		   switch ( objXML.status ){
				case 0:break;
				case 200:break;
				case 400:
				alert(strPage+"?"+strQueryString+",page has error");
				break;
				case 404:
				alert(strPage+"?"+strQueryString+",page has not find");
				break;
				case 500:
				alert(strPage+"?"+strQueryString+",java function has error");
				break;
				default:
				alert("XmlHttpRequest status: [" + xhr.status + "] Unknow status.");
		   }
	       //document.getElementById("txtTempInfo").value=objXML.responseText;
	       //var strReturn=document.getElementById("txtTempInfo").value;
	       //if(strReturn.indexOf("Exception report")>-1){
	       //    alert("page error\n"+"url:"+strPage+"\nQueryString:"+strQueryString);
	       //}
		   //alert("AAA"+objXML.responseText+"BBB");
	       return this.trim(objXML.responseText);
	}
	this.handleParm=function(parm){
	    return escape(parm.replace(/'/gi,"\'").replace(/=/gi,"\=").replace(/&/gi,""));
	}
	
	this.buildQueryString=function(theFormName) 
	{
	  theForm = document.forms[theFormName];
	  if(theForm=='undefined' || theForm==null){
		   alert("there is not form named:"+theFormName);
	  }
	  var aParams=new Array();
	  for(var i=0;i<theForm.elements.length;i++){
	     var sParam=theForm.elements[i].name;
		 sParam+="=";
		 sParam+=encodeURIComponent(theForm.elements[i].value);
		 //sParam+=theForm.elements[i].value;
		 
		 aParams.push(sParam);
	  }
	  return aParams.join("&");
	};
	this.XMLHTTP=function()
	{
	      var xmlhttp=false;
	      if(window.XMLHttpRequest)
	      {
	            xmlhttp = new XMLHttpRequest();
	            if (xmlhttp.overrideMimeType)
	            {
	                  xmlhttp.overrideMimeType("text/xml");
	            }
	      }
	      else if (window.ActiveXObject)
	      {
	            try
	            {
	                  xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
	            }
	            catch (e)
	            {
	                  try
	                  {
	                        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	                  }
	                  catch (e)
	                  {
	                        alert('error');
	                  }
	            }
	      }
	   return xmlhttp;
	};
	
	this.ltrim=function(s){ 
		return s.replace( /^\s*/, ""); 
	} 
	this.rtrim=function(s){ 
		return s.replace( /\s*$/, ""); 
	} 
	this.trim=function (s){ 
		return this.rtrim(this.ltrim(s)); 
	};
}
var objAjax=new ajax();