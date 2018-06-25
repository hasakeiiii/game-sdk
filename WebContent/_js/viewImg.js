/*
 兼容于ie,firefox,netscape的等比例图片本地预览的javascript实现
 author:semovy@gmail.com
 date:14:39 上午 2007-10-9
 @param:targetImg string id 待显示等比例调整过的目标元素的id字符串
 @param:imgSrc string src 等处理的图片源路径字符串
 @param:fitWidth int 等显示图片的最大宽度
 @param:fitHeight int 等显示图片的最大高度
*/
var targetImg;
var imgSrcValue;
var fitWidth=0;
var fitHeight=0;
function showImg(js_targetImg,js_imgSrc,js_fitWidth,js_fitHeight){
	targetImg=js_targetImg;
	imgSrcValue=js_imgSrc;
    fitWidth=js_fitWidth;
	fitHeight=js_fitHeight;
	setTimeout("changeImgSize()",500);
}

function changeImgSize(){
	var imgSrc = "file:///" + imgSrcValue.replace(/\\/g,"/");//本地路径c:\a.jpg,而ff,ns不支持,所以替换成file:///c:/a.jpg这种形式
	var img = document.getElementById(targetImg);//获取目标图片元素容器
	var tempImg = new Image();//建立临时图片对象
	tempImg.src = imgSrc; //给临时图片对象赋予图片源
	var scale=1.0; //图片度高比例因子.
	var width=0,height=0;
	//如果是ie
	if(document.all){
		if(tempImg.readyState=='complete'){
			width = tempImg.width;//获取源图片宽,高
			height = tempImg.height;
		}
	}
	else if(tempImg.complete){//fire fox ,netscape
		width = tempImg.width;
		height = tempImg.height;
	}
	if(width<5){
		width = tempImg.width;
		height = tempImg.height;
	}
	scale = width/height;//宽度比例因子
	if(width > fitWidth){//等比例调整
		width = fitWidth;
		height = width/scale;  
		if(height > fitHeight){
			height = fitHeight;
			width = height*scale;
		}
	}
	if(height > fitHeight){
		height = fitHeight;
		width = height*scale;
	}
	if(width==0 || height==0){
		width=fitWidth;
		height=fitHeight;
	}
	//alert(imgSrc);
	img.width = width;//调整后的宽,高
	img.height = height;
	img.src = imgSrc;
	img.style.display="";//显示图片
	//alert(width+"  "+height+"   "+imgSrc);
}
function imgFit(imgId,maxWidth,maxHeight){
	var imageArr=document.getElementById(imgId);
	var imageRate = imageArr.offsetWidth / imageArr.offsetHeight;    
    if(imageArr.offsetWidth > maxWidth){
        imageArr.style.width=maxWidth + "px";
        imageArr.style.Height=maxWidth / imageRate + "px";
    }
    if(imageArr.offsetHeight > maxHeight){
        imageArr.style.width = maxHeight * imageRate + "px";
        imageArr.style.Height = maxHeight + "px";
    }
}