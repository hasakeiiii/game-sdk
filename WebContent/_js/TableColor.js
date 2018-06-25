//使用时必须将<script language="JavaScript" src="TableColor.js"><-/script>放在页面尾部
//table上引用样式 id="TableColor" border="1" bordercolor="#daebff" style="border-collapse:collapse"
//为了防止javascript变量名冲突，公用组件尽量使用特殊定义	
//若想表格改变对齐方式在js尾部加<script>TableColor.js_tr_align="left";</script>
var TableColor = new Object;
TableColor.js_tr_align="center";
TableColor._color_line_title="#bbdde5";     /* Title行的背景色 */ 
TableColor._color_line_over="#ecfbd4";      /* 鼠标经过时的背景色 */
TableColor._color_line_odd="#FFFFFF";       /* 第一行的背景色 */ 
TableColor._color_line_even="#E2EBED";      /* 第二行的背景色 */
TableColor._color_line_click="#bce774";     /* 鼠标选择时的背景色 */ 
TableColor._css_table=new Object;
TableColor._css_tr_arr=new Object;

TableColor.init = function(){
	var hasTableColor=false;
	var _css_tableArr=document.getElementsByTagName("table");
	for(var z=0;z<_css_tableArr.length;z++){
		if(_css_tableArr[z].id.indexOf("TableColor")==-1 || _css_tableArr[z].id.indexOf("TableColorRow")>-1){
			continue;
		}
		hasTableColor=true;
		this._css_table=_css_tableArr[z];
		this._css_tr_arr=this._css_table.getElementsByTagName("tr");
		this._css_table.style.cursor="pointer";
		this._css_table.border="1";
		this._css_table.borderColor="#BBDDE5";
		this._css_table.style.borderCollapse="collapse";
		//设置标题行默认的样式
		this._css_tr_arr[0].style.backgroundColor =TableColor._color_line_title;
		this._css_tr_arr[0].style.color="#336633";
		this._css_tr_arr[0].style.fontWeight="bold";	
		this._css_tr_arr[0].style.height="25px";
		this._css_tr_arr[0].align=TableColor.js_tr_align;
		for (i=1;i<this._css_tr_arr.length;i++) {  
			this._css_tr_arr[i].style.backgroundColor = (i%2>0)?TableColor._color_line_odd:TableColor._color_line_even;  
			this._css_tr_arr[i].style.height="25px";
			this._css_tr_arr[i].align=TableColor.js_tr_align;
		} 
		var _select_tr=null;  
		var _tamp_color=null;
		for(var i=1;i<this._css_tr_arr.length;i++) { 
			this._css_tr_arr[i].onmouseover=function(){
				_tamp_color=this.style.backgroundColor;
				this.style.backgroundColor =TableColor._color_line_over;
				//如果已经存在选定的行,则每次把选定行设置背景色
				if(_select_tr!=null){
					_select_tr.style.backgroundColor =TableColor._color_line_click; 
				}
			}
			this._css_tr_arr[i].onmouseout=function(){
				this.style.backgroundColor=_tamp_color;
			} 
			this._css_tr_arr[i].onclick=function(){
				_tamp_color=this.style.backgroundColor; 
				_tr_color=this.style.backgroundColor; 
				//如果已经存在选定的行,则上次把选定行背景色设为空
				if(_select_tr!=null){
					//先得到当前事件时选择的行号，然后找出它应该被填充的颜色
					var _temp_index=_select_tr.sectionRowIndex;
					var _temp_color=(_temp_index%2>0)?TableColor._color_line_odd:TableColor._color_line_even;
					_select_tr.style.backgroundColor = _temp_color; 
				}          
				_select_tr=this;
				this.style.backgroundColor=TableColor._color_line_click; 
			} 
		}
	}
	if(!hasTableColor){
		alert("can't find TableColor!");
	}
};
//执行window.load。如果是IE则执行attachEvent，Mozilla/Firefox则执行addEventListener

if (document.all){
	window.attachEvent('onload',TableColor.init)
}
else{
	window.addEventListener('load',TableColor.init,false);
}
