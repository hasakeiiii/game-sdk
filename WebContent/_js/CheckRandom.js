//页面验证码检查函数
//<img border="0" id="_js_radom_img"  width="65" height="20" onclick="_js_change_img()"/>
//<input id="_check_random_input" size="10" maxlength="4"/>
//使用动态地址时js得不到应用目录所以此处需要手动处理下
var _js_random_file_path="http://"+document.location.host+'/net/_js/CheckRandom.jsp';
function _js_check_random(){
	if (typeof objAjax != 'object'){
		alert("can't find ajax.js");
		return false;
	}
	if(document.getElementById("_check_random_input").value==""){
		 alert("验证码不能为空");
		 return false;
	}
	objAjax.URL=_js_random_file_path;
	objAjax.QueryString="nowNum="+document.getElementById("_check_random_input").value;
	var check_value=objAjax.RemoteOperate();
	if(check_value=="0"){
		 alert("验证码不正确");
		 return false;
	}
	return true;
}
function _js_change_img(){
   document.getElementById("_js_radom_img").src=_js_random_file_path+'?rand='+ Math.random();
}
_js_change_img();