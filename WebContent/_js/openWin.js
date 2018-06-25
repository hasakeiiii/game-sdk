/*
onClick="javascript:openwin('pic.jsp','',320,150);"
弹出窗口调用父窗口函数时可以使用window.opener.xxx();
也可以使用window.opener.text1.value="xxx";来设置父窗口中属性
使用可以刷新父window.opener.reFresh();页面

　　window.open 弹出新窗口的命令； 
　　'page.html' 弹出窗口的文件名； 
　　'newwindow' 弹出窗口的名字（不是文件名），非必须，可用空''代替； 
　　height=100 窗口高度； 
　　width=400 窗口宽度； 
　　top=0 窗口距离屏幕上方的象素值； 
　　left=0 窗口距离屏幕左侧的象素值； 
　　toolbar=no 是否显示工具栏，yes为显示； 
　　menubar，scrollbars 表示菜单栏和滚动栏。 
　　resizable=no 是否允许改变窗口大小，yes为允许； 
　　location=no 是否显示地址栏，yes为允许； 
　　status=no 是否显示状态栏内的信息（通常是文件已经打开），yes为允许；
另外两种弹出窗口方法
2. confirm, prompt
if(window.confirm("确定删除此记录?")){   只有一个提示语句
if(prompt("请输入名字","无标题")==false){    弹出一个输入框接受输入
 
3. showModalDialog window.showModalDialog("child.html",window,"dialogWidth:335px;status:no;dialogHeight:300px")
status:是否显示状态栏内的信息

*/
function openWin(url,winName,width,height) {
	xposition=0; yposition=0;
	if ((parseInt(navigator.appVersion) >= 4 ))	{
		xposition = (screen.width - width) / 2;
		yposition = (screen.height - height) / 2;
	}
	theproperty= "width=" + width + ","
	+ "height=" + height + ","
	+ "location=0,"
	+ "menubar=0,"
	+ "resizable=1,"
	+ "scrollbars=0,"
	+ "status=0,"
	+ "titlebar=0,"
	+ "toolbar=0,"
	+ "hotkeys=0,"
	+ "screenx=" + xposition + "," //????????Netscape
	+ "screeny=" + yposition + "," //????????Netscape
	+ "left=" + xposition + "," //IE
	+ "top=" + yposition; //IE
	newwin=window.open('','',theproperty );
	url=url;//????  
	newwin.location.href =url;
}

//带有滚动条的弹出窗口
function openWinBar(url,winName,width,height){
	xposition=0; yposition=0;
	if ((parseInt(navigator.appVersion) >= 4 ))	{
		xposition = (screen.width - width) / 2;
		yposition = (screen.height - height) / 2;
	}
	theproperty= "width=" + width + ","
	+ "height=" + height + ","
	+ "location=0,"
	+ "menubar=0,"
	+ "resizable=1,"
	+ "scrollbars=1,"
	+ "status=0,"
	+ "titlebar=0,"
	+ "toolbar=0,"
	+ "hotkeys=0,"
	+ "screenx=" + xposition + "," //????????Netscape
	+ "screeny=" + yposition + "," //????????Netscape
	+ "left=" + xposition + "," //IE
	+ "top=" + yposition; //IE
	newwin=window.open('','',theproperty );
	url=url;//????  
	newwin.location.href =url;
}