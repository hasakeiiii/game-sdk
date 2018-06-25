window.alert = function(txt){    
	var shield = document.createElement("DIV");    
	shield.id = "shield";    
	shield.style.position = "absolute";    
	shield.style.left = "0px";    
	shield.style.top = "0px";    
	shield.style.width = "100%";    
	shield.style.height = "100%";    
	shield.style.background = "#333";    
	shield.style.textAlign = "center";    
	shield.style.zIndex = "10000";    
	shield.style.filter = "alpha(opacity=0)";    
	var alertFram = document.createElement("DIV");    
	alertFram.id="alertFram";    
	alertFram.style.position = "absolute";    
	alertFram.style.left = "50%";    
	alertFram.style.top = "50%";    
	alertFram.style.marginLeft = "-225px";    
	alertFram.style.marginTop = "-75px";    
	alertFram.style.width = "450px";    
	alertFram.style.height = "150px";    
	alertFram.style.background = "#ccc";    
	alertFram.style.textAlign = "center";    
	alertFram.style.lineHeight = "150px";    
	alertFram.style.zIndex = "10001";  
	var bgColor1="#0099CC";
	var bgColor2="#fff";
	var bgColor3="#D7F2FF";
	var borderColor="#D7F2FF";
	strHtml = "<ul style=\"list-style:none;margin:0px;padding:0px;width:100%\">\n";    
	strHtml += " <li style=\"background:"+bgColor1+";text-align:left;padding-left:20px;font-size:14px;font-weight:bold;height:25px;line-height:25px;border:1px solid "+borderColor+";\">[系统消息]</li>\n";    
	strHtml += " <li style=\"background:"+bgColor2+";text-align:center;font-size:12px;height:120px;line-height:120px;border-left:1px solid "+borderColor+";\";border-right:1px solid "+borderColor+";\">"+txt+"</li>\n";    
	strHtml += " <li style=\"background:"+bgColor3+";text-align:center;font-weight:bold;height:25px;line-height:25px; border:1px solid "+borderColor+";\"><a href=\"#\" onclick=\"doOk()\"><img src=\"../_js/ico/btn_ok.jpg\" border=\"0\"/></a></li>\n";    
	strHtml += "</ul>\n";    
	alertFram.innerHTML = strHtml;    
	document.body.appendChild(alertFram);    
	document.body.appendChild(shield);    
	var c = 10;    
	this.doAlpha = function(){    
	if (c > 50){clearInterval(ad);return 0;} 
        c=c+5;   
	shield.style.filter = "alpha(opacity="+c+");";    
	}    
	var ad = setInterval("doAlpha()",10);    
	this.doOk = function(){    
	alertFram.style.display = "none";    
	shield.style.display = "none";    
	}    
	alertFram.focus();    
	document.body.onselectstart = function(){return false;};    
}