
var calendar=this;
var calendarTime;
this.calendarPad=null;
this.prevMonth=null;
this.nextMonth=null;
this.prevYear=null;
this.nextYear=null;
this.goToday=null;
this.calendarClose=null;
this.calendarAbout=null;
this.head=null;
this.body=null;
this.today=[];
this.currentDate=[];
this.sltDate;
this.target;
this.source;
this.time;
this.target2 = null;
this.isForward = false;
this.addCalendarPad=function()
{
    document.write("<div id='divCalendarpad' style='position:absolute;top:0;left:0;width:246;height:170;display:none;'>");
    document.write("<iframe frameborder='0' height='100%' width='100%'></iframe>");
    document.write("<div style='position:absolute;top:1;left:1;width:100%;height:100%;background-color:#336699;'></div>");
    document.write("</div>");
    calendar.calendarPad=document.all.divCalendarpad;
}
function DateIsTrue(asDate)
{
 var lsDate  = asDate + "";
 var loDate  = lsDate.split("-");
 if (loDate.length!=3) return false; 
 var liYear  = parseFloat(loDate[0]);
 var liMonth = parseFloat(loDate[1]);
 var liDay   = parseFloat(loDate[2]);
 if ((loDate[0].length>4)||(loDate[1].length>2)||(loDate[2].length>2)) return false;
 if (isNaN(liYear)||isNaN(liMonth)||isNaN(liDay)) return false;
 if ((liYear<1800)||(liYear>2500)) return false;
 if ((liMonth>12)||(liMonth<=0))   return false;
 if (GetThisDays(liYear,liMonth)<liDay) return false;
 return !isNaN(Date.UTC(liYear,liMonth,liDay));
}
this.addCalendarBoard=function()
{
    var BOARD=this;
    var divBoard=document.createElement("div");
    calendar.calendarPad.insertAdjacentElement("beforeEnd",divBoard);
    divBoard.style.cssText="position:absolute;top:0;left:0;width:100%;height:100%;border:1 outset;background-color:buttonface;";

    var tbBoard=document.createElement("table");
    divBoard.insertAdjacentElement("beforeEnd",tbBoard);
    tbBoard.style.cssText="position:absolute;top:0;left:0;width:100%;height:10;font-size:9pt;";
    tbBoard.cellPadding=0;
    tbBoard.cellSpacing=1;
    tbBoard.bgColor="#333333";
    trRow = tbBoard.insertRow(0);
    calendar.calendarAbout=calendar.insertTbCell(trRow,0,"-","center");
    calendar.calendarAbout.onclick=function()
    {
  calendar.about();
 }
    tbCell=trRow.insertCell(1);
    tbCell.colSpan=5;
    tbCell.bgColor="#99CCFF";
    tbCell.align="center";
    tbCell.style.cssText = "cursor:default;font-family: Tahoma;font-size: 12pt;";
    calendar.head = tbCell;
    tbCell=trRow.insertCell(2);
    calendar.calendarClose = calendar.insertTbCell(trRow,2,"x","center");
    calendar.calendarClose.title="关闭";
    calendar.calendarClose.onclick=function()
    {
        calendar.hide();
    }
    trRow = tbBoard.insertRow(1);
    calendar.prevYear = calendar.insertTbCell(trRow,0,"&lt;&lt;","center");
    calendar.prevYear.title="上一年";
    calendar.prevYear.onmousedown=function()
    {
        calendar.currentDate[0]--;
        calendar.showCalendar(calendar.target,calendar.currentDate[0]+"-"+calendar.currentDate[1]+"-"+calendar.currentDate[2],calendar.source);
    }
    calendar.prevMonth = calendar.insertTbCell(trRow,1,"&lt;","center");
    calendar.prevMonth.title="上一月";
    calendar.prevMonth.onmousedown=function()
    {
        calendar.currentDate[1]--;
        if(calendar.currentDate[1]==0)
        {
            calendar.currentDate[1]=12;
            calendar.currentDate[0]--;
        }
        calendar.showCalendar(calendar.target,calendar.currentDate[0]+"-"+calendar.currentDate[1]+"-"+calendar.currentDate[2],calendar.source);
    }
    calendar.goToday = calendar.insertTbCell(trRow,2,"今天","center",3);
    calendar.goToday.title="选择今天";
    calendar.goToday.onclick=function()
    {
        var x_today=new Date();
        calendar.currentDate[0]=x_today.getFullYear();
        calendar.currentDate[1]=x_today.getMonth()+1;
        calendar.currentDate[2]=x_today.getDate();
        calendar.sltDate=calendar.currentDate[0]+"-"+calendar.currentDate[1]+"-"+calendar.currentDate[2];
        calendar.target.value=calendar.sltDate + " " + calendarTime.getTime();;
        calendar.hide();
    }
    calendar.nextMonth = calendar.insertTbCell(trRow,3,"&gt;","center");
    calendar.nextMonth.title="下一月";
    calendar.nextMonth.onmousedown=function()
    {
        calendar.currentDate[1]++;
        if(calendar.currentDate[1]==13)
        {
            calendar.currentDate[1]=1;
            calendar.currentDate[0]++;
        }
        calendar.showCalendar(calendar.target,calendar.currentDate[0]+"-"+calendar.currentDate[1]+"-"+calendar.currentDate[2],calendar.source);
    }
    calendar.nextYear = calendar.insertTbCell(trRow,4,"&gt;&gt;","center");
    calendar.nextYear.title="下一年";
    calendar.nextYear.onmousedown=function()
    {
        calendar.currentDate[0]++;
        calendar.showCalendar(calendar.target,calendar.currentDate[0]+"-"+calendar.currentDate[1]+"-"+calendar.currentDate[2],calendar.source);
    }
    trRow = tbBoard.insertRow(2);
    var cnDateName = new Array("周日","周一","周二","周三","周四","周五","周六");
    for (var i = 0; i < 7; i++) 
    {
        tbCell=trRow.insertCell(i)
        tbCell.innerText=cnDateName[i];
        tbCell.align="center";
        tbCell.width=35;
        tbCell.style.cssText="cursor:default;border:1 solid #99CCCC;background-color:#99CCCC;";
    }
    trRow = tbBoard.insertRow(3);
    tbCell=trRow.insertCell(0);
    tbCell.colSpan=7;
    tbCell.height=83;
    tbCell.vAlign="top";
    tbCell.bgColor="#F0F0F0";
    var tbBody=document.createElement("table");
    tbCell.insertAdjacentElement("beforeEnd",tbBody);
    tbBody.style.cssText="position:relative;top:0;left:0;width:100%;height:100%;font-size:9pt;"
    tbBody.cellPadding=0;
    tbBody.cellSpacing=0;
    calendar.body=tbBody;
    trRow = tbBoard.insertRow(4);
    tbCell=trRow.insertCell(0);
    tbCell.colSpan=7;
    tbCell.height=20;
    tbCell.vAlign="top";
    tbCell.bgColor="#F0F0F0";
    var tbBodyTime=document.createElement("table");
    tbCell.insertAdjacentElement("beforeEnd",tbBodyTime);
    tbBodyTime.style.cssText="position:relative;top:0;left:0;width:100%;height:100%;font-size:9pt;"
    tbBodyTime.cellPadding=0;
    tbBodyTime.cellSpacing=0;
    calendar.time=tbBodyTime;
}
this.insertTbCell=function(trRow,cellIndex,TXT,trAlign,tbColSpan)
{
    var tbCell=trRow.insertCell(cellIndex);
    if(tbColSpan!=undefined) tbCell.colSpan=tbColSpan;

    var btnCell=document.createElement("button");
    tbCell.insertAdjacentElement("beforeEnd",btnCell);
    btnCell.value=TXT;
    btnCell.style.cssText="width:100%;border:1 outset;background-color:buttonface;";
    btnCell.onmouseover=function()
    {
        btnCell.style.cssText="width:100%;border:1 outset;background-color:#F0F0F0;";
    }
    btnCell.onmouseout=function()
    {
        btnCell.style.cssText="width:100%;border:1 outset;background-color:buttonface;";
    }
    btnCell.onmousedown=function()
    {
        btnCell.style.cssText="width:100%;border:1 inset;background-color:&H000000FF&;";
    }
    btnCell.onmouseup=function()
    {
        btnCell.style.cssText="width:100%;border:1 outset;background-color:#F0F0F0;";
    }
    btnCell.onclick=function()
    {
        btnCell.blur();
    }
    return btnCell;
}
this.setDefaultDate=function()
{
    var dftDate=new Date();
    calendar.today[0]=dftDate.getYear();
    calendar.today[1]=dftDate.getMonth()+1;
    calendar.today[2]=dftDate.getDate();
}
this.showCalendar=function(targetObject,defaultDate,sourceObject,targetObject2,isForward)
{
    if(targetObject==undefined) 
    {
        alert("未设置目标对像. \n方法: ATCALENDAR.showCalendar(obj 目标对像,string 默认日期,obj 点击对像);\n\n目标对像:接受日期返回值的对像.\n默认日期:格式为\"yyyy-mm-dd\",缺省为当日日期.\n点击对像:点击这个对像弹出calendar,默认为目标对像.\n");
        return false;
    }
    else    calendar.target=targetObject;
    if (targetObject2 !=undefined && targetObject2 != null)
  calendar.target2=targetObject2;
 if (isForward != undefined && isForward != null)
  calendar.isForward = isForward;
    if(sourceObject==undefined) calendar.source=calendar.target;
    else calendar.source=sourceObject;
 
    var firstDay;
    var Cells=new Array();
    if (targetObject.value.length > 0 && (defaultDate==undefined || defaultDate==""))
    {
  defaultDate = targetObject.value;
    }
    if(defaultDate==undefined || defaultDate=="")
    {
        var theDate=new Array();
        calendar.head.innerText = calendar.today[0]+"-"+calendar.today[1]+"-"+calendar.today[2];
        theDate[0]=calendar.today[0]; theDate[1]=calendar.today[1]; theDate[2]=calendar.today[2];
    }
    else
    {
        var theDateTime = defaultDate.split(" ");
        var theDate=theDateTime[0].split("-");
        calendar.head.innerText = theDateTime[0];
        defaultDate = theDateTime[0];
    }
    calendar.currentDate[0]=theDate[0];
    calendar.currentDate[1]=theDate[1];
    calendar.currentDate[2]=theDate[2];
    theFirstDay=calendar.getFirstDay(theDate[0],theDate[1]);
    theMonthLen=theFirstDay+calendar.getMonthLen(theDate[0],theDate[1]);
    calendar.calendarPad.style.display="";
    var theRows = Math.ceil((theMonthLen)/7);
    while (calendar.body.rows.length > 0) 
    {
        calendar.body.deleteRow(0)
    }
    var n=0;day=0;
    for(i=0;i<theRows;i++)
    {
        theRow=calendar.body.insertRow(i);
        for(j=0;j<7;j++)
        {
            n++;
            if(n>theFirstDay && n<=theMonthLen)
            {
                day=n-theFirstDay;
                calendar.insertBodyCell(theRow,j,day);
            }
            else
            {
                var theCell=theRow.insertCell(j);
                theCell.style.cssText="background-color:#F0F0F0;cursor:default;";
            }
        }
    }
    
    while (calendar.time.rows.length > 0) 
    {
        calendar.time.deleteRow(0)
    }
    theRow = calendar.time.insertRow(0);
    var theCell=theRow.insertCell(0);
    theCell.style.cssText="background-color:#F0F0F0;cursor:default;text-align: center;";
    calendarTime = null;
    calendarTime = new minute(calendar.source.id, calendar.source);
    theCell.innerHTML = calendarTime.toString();
    var offsetPos=calendar.getAbsolutePos(calendar.source);
    if((document.body.offsetHeight-(offsetPos.y+calendar.source.offsetHeight-document.body.scrollTop))<calendar.calendarPad.style.pixelHeight)
    {
        var calTop=offsetPos.y-calendar.calendarPad.style.pixelHeight;
    }
    else
    {
        var calTop=offsetPos.y+calendar.source.offsetHeight;
    }
    if((document.body.offsetWidth-(offsetPos.x+calendar.source.offsetWidth-document.body.scrollLeft))>calendar.calendarPad.style.pixelWidth)
    {
        var calLeft=offsetPos.x;
    }
    else
    {
        var calLeft=document.body.offsetWidth-calendar.calendarPad.style.pixelWidth-20;
    }
    calendar.calendarPad.style.pixelLeft=calLeft;
    calendar.calendarPad.style.pixelTop=calTop;
}
this.getAbsolutePos = function(el) 
{
    var r = { x: el.offsetLeft, y: el.offsetTop };
    if (el.offsetParent) 
    {
        var tmp = calendar.getAbsolutePos(el.offsetParent);
        r.x += tmp.x;
        r.y += tmp.y;
    }
    return r;
};
this.insertBodyCell=function(theRow,j,day,targetObject)
{
    var theCell=theRow.insertCell(j);
    if(j==0) var theBgColor="#FF9999";
    else var theBgColor="#FFFFFF";
    if(day==calendar.currentDate[2]) var theBgColor="#facdaa";
    if(day==calendar.today[2]) var theBgColor="#99FFCC";
    theCell.bgColor=theBgColor;
    theCell.innerText=day;
    theCell.align="center";
    theCell.width=35;
    theCell.style.cssText="border:1 solid #CCCCCC;cursor:hand;";
    theCell.onmouseover=function()
    { 
        theCell.bgColor="#FFFFCC"; 
        theCell.style.cssText="border:1 outset;cursor:hand;";
    }
    theCell.onmouseout=function()
    { 

        theCell.bgColor=theBgColor; 
        theCell.style.cssText="border:1 solid #CCCCCC;cursor:hand;";
    }
    theCell.onmousedown=function()
    { 
        theCell.bgColor="#FFFFCC"; 
        theCell.style.cssText="border:1 inset;cursor:hand;";
    }
    theCell.onclick=function()
    {
        if(calendar.currentDate[1].length<2) calendar.currentDate[1]="0"+calendar.currentDate[1];
        if(day.toString().length<2) day="0"+day;
        calendar.sltDate=calendar.currentDate[0]+"-"+calendar.currentDate[1]+"-"+day;
        calendar.target.value=calendar.sltDate + " " + calendarTime.getTime();
        calendar.hide();
    }
}
this.getFirstDay=function(theYear, theMonth)
{
    var firstDate = new Date(theYear,theMonth-1,1);
    return firstDate.getDay();
}
this.getMonthLen=function(theYear, theMonth) 
{
    theMonth--;
    var oneDay = 1000 * 60 * 60 * 24;
    var thisMonth = new Date(theYear, theMonth, 1);
    var nextMonth = new Date(theYear, theMonth + 1, 1);
    var len = Math.ceil((nextMonth.getTime() - thisMonth.getTime())/oneDay);
    return len;
}
this.hide=function()
{
    calendar.calendarPad.style.display="none";
}
this.setup=function(defaultDate)
{
    calendar.addCalendarPad();
    calendar.addCalendarBoard();
    calendar.setDefaultDate();
    
}
this.about=function()
{
}
calendar.setup();
this.minute = function(fName,obj)
{
 this.fName = fName;
 this.timer = null;
 this.fObj = null;
 this.toString = function()
 { 
  var objDate = new Date();
  if (obj.value.length > 0)
  { 
   var theDateTime = obj.value.split(" ");
   var theDate=theDateTime[0].split("-");
   var theTime = theDateTime[1].split(":");
   objDate.setFullYear(theDate[0]);
   objDate.setMonth(theDate[1]);
   objDate.setDate(theDate[2]);
   objDate.setHours(theTime[0]);
   objDate.setMinutes(theTime[1]);
   objDate.setSeconds(theTime[2]);   
  }
  var sMinute_Common = "style='width: 18px;height: 14px;border: 0px solid black;font-family: Tahoma;font-size: 9px;text-align: right;ime-mode:disabled' maxlength='2' id='txtcalendarTime' name='calendarTime' onfocus='calendarTime.setFocusObj(this)' onblur='calendarTime.setTime(this)' onkeyup='calendarTime.prevent(this)' onkeypress='if (!/[0-9]/.test(String.fromCharCode(event.keyCode)))event.keyCode=0' onpaste='return false' ondragenter='return false'";
  var sButton_Common = "style='width: 16px;height: 8px;font-family: Webdings;font-size: 7px;line-height: 2px;padding-left: 2px;cursor: default' onfocus='this.blur()' onmouseup='calendarTime.controlTime()' disabled"
  var str = "";
  str += "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
  str += " <tr>"
  str += "  <td>"
  str += "   <span style='vertical-align:middle;font-family: Arial;font-size: 10pt;'>时间：</span>" 
  str += "  </td>"
  str += "  <td>"
  str += "   <div style=\"border-left: 2px inset #D4D0C8;border-top: 2px inset #D4D0C8;border-right: 2px inset #FFFFFF;border-bottom: 2px inset #FFFFFF;width: 100px;height: 19px;background-color: #FFFFFF;overflow: hidden;text-align: right;font-family: Tahoma;font-size: 10px;\">"
  str += "    <input radix=\"24\" value=\""+this.formatTime(objDate.getHours())+"\" "+sMinute_Common+">:"
  str += "    <input radix=\"60\" value=\""+this.formatTime(objDate.getMinutes())+"\" "+sMinute_Common+">:"
  str += "    <input radix=\"60\" value=\""+this.formatTime(objDate.getSeconds())+"\" "+sMinute_Common+">"
  str += "   </div>"
  str += "  </td>"
  str += "  <td>"
  str += "  <table border=\"0\" cellspacing=\"2\" cellpadding=\"0\">"
  str += "   <tr><td><button id=\""+this.fName+"_up\" "+sButton_Common+">5</button></td></tr>"
  str += "   <tr><td><button id=\""+this.fName+"_down\" "+sButton_Common+">6</button></td></tr>"
  str += "  </table>"
  str += "  </td>"
  str += " </tr>"
  str += "</table>"
  return str;
 }
 this.play = function()
 {
  this.timer = setInterval("calendarTime.playback()",1000);
 }
 this.formatTime = function(sTime)
 {
  sTime = ("0"+sTime);
  return sTime.substr(sTime.length-2);
 }
 this.playback = function()
 {
  var objDate = new Date();
  var arrDate = [objDate.getHours(),objDate.getMinutes(),objDate.getSeconds()];
  var objMinute = document.getElementsByID(this.fName);
  for (var i=0;i<objMinute.length;i++)
  {
   objMinute[i].value = this.formatTime(arrDate[i])
  }
 }
 this.prevent = function(obj)
 {
  clearInterval(this.timer);
  this.setFocusObj(obj);
  var value = parseInt(obj.value,10);
  var radix = parseInt(obj.radix,10)-1;
  if (obj.value>radix||obj.value<0)
  {
   obj.value = obj.value.substr(0,1);
  }
 }
 this.controlTime = function(cmd)
 {
  event.cancelBubble = true;
  if (!this.fObj) return;
  clearInterval(this.timer);
  var cmd = event.srcElement.innerText=="5"?true:false;
  var i = parseInt(this.fObj.value,10);
  var radix = parseInt(this.fObj.radix,10)-1;
  if (i==radix&&cmd)
  {
   i = 0;
  }
  else if (i==0&&!cmd)
  {
   i = radix;
  }
  else
  {
   cmd?i++:i--;
  }
  this.fObj.value = this.formatTime(i);
  this.fObj.select();
 }
 this.setTime = function(obj)
 {
  obj.value = this.formatTime(obj.value);
 }
 this.setFocusObj = function(obj)
 {
  eval(this.fName+"_up").disabled = eval(this.fName+"_down").disabled = false;
  this.fObj = obj;
 }
 this.getTime = function()
 {
  var arrTime = new Array(2);
  for (var i=0;i<document.getElementsByName("calendarTime").length;i++)
  {
   arrTime[i] = document.getElementsByName("calendarTime")[i].value;
  }
  return arrTime.join(":");
 }
}

