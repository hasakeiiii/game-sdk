/*
 * 界面下拉列表加载公用js
 * 要求 : 界面id命名规范必须一致
 * 参考模版界面 : cfgPayInfo.jsp 
 */

$(document).ready(function(){
	validRole();
	fillBusiness();
	fillDate();
	fillChannel();
});

/**
 * 判断权限
 */
function validRole(){
	var adminOffline = "admin_offline";//超级管理员单机
	var adminOnline = "admin_online";//超级管理员网游
	var operationOnline = "operation_online";//网游运营
	var operationOffline = "operation_offline";//单机运营
	if($("#game_type").length > 0){
		$.post(
				"../servlet/CommonOperationAction",
				{
					cmd : "validRole"
				},function(data){
					var role = data.role;
					if(role == adminOffline || role == operationOffline){
						$("#game_type").find("option[value='on_line']").remove();
					}else if(role == adminOnline || role == operationOnline){
						$("#game_type").find("option[value='mm_on_line']").remove();
						$("#game_type").find("option[value='off_line']").remove();
					}
				},"json"
		);
	}
	
}



function chgGameType(obj){	
	var operatedType = $(obj).val();
	$.post(
		"../servlet/CommonOperationAction",
		{
			cmd : "chgGameType",
			operatedType : operatedType 
		},
		function(data){
			var info = data.info;
			var list = data.list;
			
			if(info == "error"){
				alert("数据出错或者无数据");
			}else{
				$("#game_no").empty();
				$("#game_no").append("<option value=''>请选择</option>");
				$.each(list,function(i,value){
					$("#game_no").append("<option value='"+ value.no + "'>" + value.name + "</option>");
				});
			}
			
		},"json"
		
	);
}



function chgGame(obj){
	var operatedType = $(obj).val();
	
	$.post(
		"../servlet/CommonOperationAction",
		{
			cmd : "chgGame",
			operatedType : operatedType,
			businessNo : (typeof businessNo != 'undefined' && businessNo != undefined && businessNo != '') ? businessNo : "" 
		},
		function(data){
			var info = data.info;
			var list = data.list;
			
			if(info == "error"){
				alert("数据出错或者无数据");
			}else{
				if($("#channel_no").length > 0){
					$("#channel_no").empty();
					$("#channel_no").append("<option value=''>请选择</option>");
					$.each(list,function(i,value){
						$("#channel_no").append("<option value='"+ value.no + "'>" + value.name + "</option>");
					});
				}
			}
			
		},"json"
	);
	fillPacketId();
}

/**
 * 为了实现任意选够三项确定packetId
 */
function chgBusiness(){
	fillPacketId();
}

/*
 * 选择渠道,如果游戏没选,查出对应渠道所有游戏
 */
function chgChannel(){
		var channelNo = $("#channel_no :selected").val();
		if($("#game_no").length > 0 && $("#game_no").val() == ""){
			$.post(
					"../servlet/CommonOperationAction",
					{
						cmd : "chgChannel",
						channelNo : channelNo
					},
					function(data){
						
							
						var gameList = data.gameList;
						var info = data.info;
						if(info = "success"){
							$("#game_no").empty();
							$("#game_no").append("<option value=''>请选择</option>");
							$.each(gameList,function(i,value){
								$("#game_no").append("<option value='"+ value.no + "'>" + value.name + "</option>");
							});
						}
						
					},"json"
			);
		}
		fillPacketId();
}

/**
 * 三个值确定一个packetId
 */
function fillPacketId(){
	
	if($("#game_no :selected").val() != "" && $("#business_no :selected").val() != "" && $("#channel_no :selected").val() != ""){
		var gameNo = $("#game_no :selected").val();
		var businessNo = $("#business_no :selected").val();
		var channelNo = $("#channel_no :selected").val();
		if($("#apk_no").length > 0){
			$.post(
					"../servlet/CommonOperationAction",
					{
						cmd : "ensurePacketId",
						gameNo : gameNo,
						businessNo : businessNo,
						channelNo : channelNo
					},
					function(data){
						var list = data.list;
						var info = data.info;
						if(info = "success"){
							$.each(list,function(i,value){
								$("#apk_no").empty();
								$("#apk_no").append("<option value=''>请选择</option>");
								$.each(list,function(i,value){
									$("#apk_no").append("<option value='"+ value + "'>" + value + "</option>");
								});
							});
						}
					},"json"
			);
		}
	}
}

function fillBusiness(){
	//如果要选取的对象存在
	if($("#business_no").length > 0 ){
		$.post(
			"../servlet/CommonOperationAction",
			{
				cmd : "getAllBusiness",
				operatedType : ""
			},
			function(data){
				var info = data.info;
				var list = data.list;
				
				if(info == "error"){
					alert("数据出错或者无数据");
				}else{
					$("#business_no").empty();
					//如果是商务登录系统
					if( typeof businessNo != 'undefined' && businessNo != undefined && businessNo != ''){
						$.each(list,function(i,value){
							if(value.no == businessNo * 1)
								$("#business_no").append("<option value='"+ value.no + "'>" + value.name + "</option>");
						});
					}else{
						$("#business_no").append("<option value=''>请选择</option>");
						$.each(list,function(i,value){
							$("#business_no").append("<option value='"+ value.no + "'>" + value.name + "</option>");
						});
					}
					
					
					
				}
			},
			"json"
		);
	}
}

function fillDate(){
	if($("#selYear").length > 0){
		var myDate = new Date();
		var curYear = myDate.getFullYear();
		
		for(var i = 2014;i <= curYear;i++){
			$("#selYear").append("<option value='" + i + "'>" + i + "</option>");
		}
		
		$("#selYear").find("option[value='" + curYear + "']").attr("selected",true);
		
		if($("#selMonth").length > 0){
			for(var j = 1 ; j < 13 ; j++){
				$("#selMonth").append("<option value='" + j +"'>" + j + "</option>");
			}
			var curMonth = myDate.getMonth() + 1;
			
			$("#selMonth").find("option[value='" + curMonth + "']").attr("selected",true);  
			
			if($("#selDay").length > 0){
				chgMonth();
				var curDay = myDate.getDate();
				$("#selDay").find("option[value='" + curDay + "']").attr("selected",true);
			}
		}
		
	}
	
	
	if($("#selEndYear").length > 0){
		var myDate = new Date();
		var curYear = myDate.getFullYear();
		
		for(var i = 2014;i <= curYear;i++){
			$("#selEndYear").append("<option value='" + i + "'>" + i + "</option>");
		}
		
		$("#selEndYear").find("option[value='" + curYear + "']").attr("selected",true);
		
		if($("#selEndMonth").length > 0){
			for(var j = 1 ; j < 13 ; j++){
				$("#selEndMonth").append("<option value='" + j +"'>" + j + "</option>");
			}
			var curMonth = myDate.getMonth() + 1;
			
			$("#selEndMonth").find("option[value='" + curMonth + "']").attr("selected",true);  
			
			if($("#selEndDay").length > 0){
				chgEndMonth();
				var curDay = myDate.getDate();
				$("#selEndDay").find("option[value='" + curDay + "']").attr("selected",true);
			}
		}
		
	}
	
	
}

function chgMonth(){
	var year = $("#selYear option:selected").val() * 1;
	var month = $("#selMonth option:selected").val() * 1;
	
	switch(month){
		case 1 :	
		case 3 :
		case 5 :
		case 7 :
		case 8 :
		case 10 :
		case 12 : fill(1,"#selDay"); break;
		default : fill(2,"#selDay");
	}
	
	if(year % 100 == 0) year/=100;
	
	if(month == 2){
		if(year % 4 == 0){
			fill(4,"#selDay");
		}else{
			fill(3,"#selDay");
		}
	}
}

function chgEndMonth(){
	var year = $("#selEndYear option:selected").val() * 1;
	var month = $("#selEndMonth option:selected").val() * 1;
	
	switch(month){
		case 1 :	
		case 3 :
		case 5 :
		case 7 :
		case 8 :
		case 10 :
		case 12 : fill(1,"#selEndDay"); break;
		default : fill(2,"#selEndDay");
	}
	
	if(year % 100 == 0) year/=100;
	
	if(month == 2){
		if(year % 4 == 0){
			fill(4,"#selEndDay");
		}else{
			fill(3,"#selEndDay");
		}
	}
}

function fill(flag,name){
	
		$(name).empty();
		$(name).append("<option value = ''>请选择</option>");
		if(flag == 1){
			for(var i = 1 ; i < 32;i++){
				var str = "<option value = '" + i + "'>" + i + "</option>";
				$(name).append(str);
			}
		}else if(flag == 2){
			for(var i = 1 ; i < 31;i++){
				var str = "<option value = '" + i + "'>" + i + "</option>";
				$(name).append(str);
			}
		}else if(flag == 3){
			for(var i = 1 ; i < 29;i++){
				var str = "<option value = '" + i + "'>" + i + "</option>";
				$(name).append(str);
			}
		}else{
			for(var i = 1 ; i < 30;i++){
				var str = "<option value = '" + i + "'>" + i + "</option>";
				$(name).append(str);
			}
		}
	
	
	
}

function fillChannel(){
	if($("#channel_no").length > 0){
		game_no = $("#game_no option:selected").val();
		
		$.post(
			"../servlet/CommonOperationAction",
			{
				cmd : "getAllChannel",
				operatedType : ""
			},
			function(data){
				var info = data.info;
				var list = data.list;
				
				if(info == "error"){
					alert("数据出错或者无数据");
				}else{
					$("#channel_no").empty();
					$("#channel_no").append("<option value=''>请选择</option>");
					$.each(list,function(i,value){
						$("#channel_no").append("<option value='"+ value.no + "'>" + value.name + "</option>");
					});
				}
			},"json"
		);
	}
}

 

