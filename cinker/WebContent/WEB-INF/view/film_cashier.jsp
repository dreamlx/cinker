<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/common.jsp" %>  
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport" content="initial-scale=1.0,user-scalable=no,minimum-scale=1.0,maximum-scale=1.0"/>
<meta name="description" content="">
<meta name="author" content="">
<title>收银台</title>

</head>
<body class="bg-1">    
    <section class="container">
    	<section class="money_cont">
            <h1>${filmOrder.filmTitle}</h1>
            <p>${filmOrder.englishName}</p>
            <div class="clear45px"></div>
            <p>${filmOrder.cinemaName}</p>
            <p>${filmOrder.sessionName}</p>
            <h2 class='enboldfont'>${filmOrder.sessionShowTime}<br>${filmOrder.showTime}</h2>
            <p>${filmOrder.seat}</p>   
            <div class="clear45px"></div>
            <p>应付金额</p>
            <p class='enboldfont'>¥${ticketType.priceInCents} x ${filmOrder.totalOrderCount}</p>
            <h3 class='enboldfont'>¥${filmOrder.totalValueCents}</h3>
            <div class="clear75px"></div>
        </section>
    	<section class="pay wxpay clkpay">
        	微信支付
        </section>
    	<section class="pay hypay">
        	会员卡支付
        </section>
        <div class="clear45px"></div>
    	<a href="${pageContext.request.contextPath}/wechat/pay?outTradeNo=${filmOrder.orderNumber}&filmName=${filmOrder.filmTitle}&sessionID=${filmOrder.sessionId}&totalPrice=${filmOrder.totalValueCents}" class="gobuy">
    		<span>支付</span>
    		<br>
    		<span class='enfont'>CHECK OUT</span>
    	</a>    	
    </section>
</body>
</html>