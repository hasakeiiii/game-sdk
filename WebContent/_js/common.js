/**
 *数据加载时动态画面 
 */
function ajaxLoading(){   
    $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");   
    $("<div class=\"datagrid-mask-msg\"></div>").html("数据正在查询，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});   
} 

function ajaxLoadEnd(){   
    $(".datagrid-mask").remove();   
    $(".datagrid-mask-msg").remove();               
}
/**
 * 填充分页
 */
function fillPage(totalPage,totalCount,curPage,pageSize){
	if($("#pageContainer").length > 0){
		$("#pageContainer").empty();
		$("#pageContainer").append("<span id = 'firstPage'></span>&nbsp;&nbsp;");
		$("#pageContainer").append("<span id = 'prePage'></span>&nbsp;&nbsp;");
		$("#pageContainer").append("<span id = 'nextPage'></span>&nbsp;&nbsp;");
		$("#pageContainer").append("<span id = 'lastPage'></span>&nbsp;&nbsp;");
		$("#pageContainer").append("跳转到第");
		$("#pageContainer").append("<select id='elemToPage'>");
		for(var i = 1; i <= totalPage;i++){
			$("#elemToPage").append("<option value='" + i + "'>" + i + "</option>");
		}
		$("#elemToPage").find("option[value='" + curPage + "']").attr("selected",true);;
		
		$("#pageContainer").append("页&nbsp;&nbsp;共");
		$("#pageContainer").append("<font color='#FF0000'>" + totalCount + "</font>");
		$("#pageContainer").append("条记录&nbsp;&nbsp;本页为第");
		$("#pageContainer").append("<font color='#FF0000'>" + curPage + "</font>");
		$("#pageContainer").append("页&nbsp;&nbsp;共");
		$("#pageContainer").append("<font color='#FF0000'>" + totalPage + "</font>");
		$("#pageContainer").append("页");
		
		$("#firstPage").append("<a href='#'>首页</a>");
		$("#prePage").append("<a href='#'>上页</a>");
		$("#nextPage").append("<a href='#'>下页</a>");
		$("#lastPage").append("<a href='#'>尾页</a>");
		
		if(curPage == 1){
			$("#firstPage").empty();
			$("#prePage").empty();
			$("#firstPage").append("<font color='#999999'>首页</font>");
			$("#prePage").append("<font color='#999999'>上页</font>");
		}
		if(curPage == totalPage){
			$("#nextPage").empty();
			$("#lastPage").empty();
			$("#nextPage").append("<font color='#999999'>下页</font>");
			$("#lastPage").append("<font color='#999999'>尾页</font>");
		}
		
	}
}


