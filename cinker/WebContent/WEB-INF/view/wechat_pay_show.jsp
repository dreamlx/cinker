<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport"
	content="initial-scale=1.0,user-scalable=no,minimum-scale=1.0,maximum-scale=1.0" />
<meta name="description" content="">
<meta name="author" content="">
<title>微信支付</title>
<script type="text/javascript">
	$(document).ready(function() {
		setInterval("ajaxstatus()",3000);
	});
	function ajaxstatus() {
		if ($("#out_trade_no").val() != 0) {
			$.ajax({
				url : "${pageContext.request.contextPath}/wechat/queryOrder?tradeno=" + $("#out_trade_no").val(),
				type :"GET",
				dataType:"json",
				data:"",
				success : function(data) {
					if (data == 1) { //订单状态为1表示支付成功
						window.location.href = "${pageContext.request.contextPath}/wechat/orderPaySuccess?tradeno=" +$("#out_trade_no").val();
						return;
					}
				},
				error : function() {
					
				}
			});
		}
	}
</script><style type="text/css">
	html,body {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
        }
    
        body {
        	text-align:center;
        }
</style>
</head>
<body class="bg-1">
	<p class="wechat_pay_logo">
		<img width="56px" src="${pageContext.request.contextPath}/images/pay_page_logo.png">
	</p>
	<div class="qrcode_block">
		<h3 style="margin-bottom:12px;font-size:18px">长按识别二维码付款</h3>
		<h6 class='enfont'>Press To Pay</h6>
		<p>
			<img class="qrcode" alt="" src="${pageContext.request.contextPath}/wechat/showQRcode?content=${content}">
			<input type="hidden" value="${tradeNo}" id="out_trade_no"/>
		</p>
		<p>如已支付完成，请查看订单</p>
	</div>
	
</body>
</html>
