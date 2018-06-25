<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>银行支付结果通知页面加载中</title>
    <style type="text/css">
        #lodingImg{
            text-align:center;
            position:absolute;
            top:29%;
            left:42%;
        }
    </style>
</head>

<body>
<div><h7>恭喜你支付成功了，反馈结果如下：</h7></div>
<form id="postForm" method=post>
    功能码：<input type=text name="funcode" value="${funcode}"/>
    应用ID：<input type=text name="appId" value="${appId}"/>
    商户订单号：<input type=text name="mhtOrderNo" value="${mhtOrderNo}"/>
    字符集：<input type=text name="mhtCharset" value="${mhtCharset}"/>
    结果状态：<input type=text name="transStatus" value="${transStatus}"/>
    商户保留域：<input type=text name="mhtReserved" value="${mhtReserved}"/>
    签名类型：<input type=text name="mhtSignType" value="${mhtSignType}"/>
    数字签名：<input type=text name="mhtSignature" value="${mhtSignature}"/>
</form>
</body>
</html>