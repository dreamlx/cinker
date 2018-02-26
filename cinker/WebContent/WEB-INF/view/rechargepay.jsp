<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>微信支付</title>
</head>
<body>
</body>
<script>
function onBridgeReady(){
	   WeixinJSBridge.invoke(
	       'getBrandWCPayRequest', {
	           "appId":"${appid}",     //公众号名称，由商户传入     
	           "timeStamp":"${timeStamp}",         //时间戳，自1970年以来的秒数     
	           "nonceStr":"${nonceStr}", //随机串     
	           "package":"${packageValue}",     
	           "signType":"MD5",         //微信签名方式：     
	           "paySign":"${paySign}" //微信签名
	       },
	       function(res){
	           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
	        	   console.log("支付成功!");
	        	   window.location.href = "${pageContext.request.contextPath}/recharge/orderPaySuccess?out_trade_no=${out_trade_no}&userSessionId=${userSessionId}&totalValueCents=${totalValueCents}&headOfficeItemCode=${headOfficeItemCode}&openid=${openid}";
	        	   return;
	        	   
	           }// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
	       }
	   ); 
	}
	if (typeof WeixinJSBridge == "undefined"){
	   if( document.addEventListener ){
	       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	   }else if (document.attachEvent){
	       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
	       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	   }
	}else{
	   onBridgeReady();
	}
</script>
</html>