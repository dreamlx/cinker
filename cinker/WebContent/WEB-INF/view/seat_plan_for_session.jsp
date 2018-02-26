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

<c:choose>
	<c:when test="${cinemaType == 'Cinema' }">
		<script src="${pageContext.request.contextPath}/js/cinker/cinema_seat_plan_for_session.js?ver=171106"></script>
	</c:when>
	<c:otherwise>
		<script src="${pageContext.request.contextPath}/js/cinker/seat_plan_for_session.js?var=171110"></script>
	</c:otherwise>
</c:choose>
 


<title>购票</title>
</head>
<body class="bg-1">    
    <section class="container" id="stepone">
   
    	<section class="check_cont">
        	<!-- <section class="cc_lf"><a href="">&lt;上一场</a></section>  -->
        	<section class="cc_lf"></section>
        	<section class="cc_md enfont">
            	<span id="sessionDate">2016-06-23</span><br/>
                <span id="sessionTime">11:30</span><br/>
                <span id="sessionRoom">${screenName}</span>
            </section>
        	<section class="cc_rt"></section>
        </section>  
        <section class="space">
        	<section class="xz1">可选</section>
        	<section class="xz2">已售</section>
        	<section class="xz3">已选</section>
        </section>
        
        <div id="seats"></div>
        <p class='room-screen-tips'>屏幕 SCREEN</p>
        <section class="top-line"></section>
        
        <div class="clear25px"></div>
        <div class="selectedSeats">
        </div>
        <div class="clear45px"></div>
        
        <form id='seatForm' method='POST' action="/cinker/film/getFilmPyment">
        	 
			<input name="scheduledFilmId" type="hidden" id="scheduledFilmId" value="${scheduledFilmId}"/>
   			<input name="filmTitle" type="hidden" id="filmTitle" value="${filmTitle}"/>
   			<input name="cinemaId" type="hidden" id="cinemaId" value="${cinemaId}"/>
   			<input name="sessionId" type="hidden" id="sessionId" value="${sessionId}"/>
   			<input name="sessionName" type="hidden" id="sessionName" value="${screenName}"/>
   			<input name="areaCategoryCode" type="hidden" id="areaCategoryCode" value=""/> 
   			<input name="showTime" type="hidden" id="showTime" value="${showTime}"/>
   			<input name="ticketTypes" type="hidden" id="ticketTypes" value=""/>
   			<input name="selectedSeats" type="hidden" id="selectedSeats" value=""/>
			<input name="sessionShowTime" type="hidden" id="sessionShowTime" value="${sessionShowTime}"/>
			<input name="orderNumber" type="hidden" id="userSessionId" value="${userSessionId}"/>
			
			<input name="orderNumber" type="hidden" id="filmEnglishName" value="${filmEnglishName}"/>
			<input name="orderNumber" type="hidden" id="cinemaName" value="${cinemaName}"/>
			
        	<a href="javascript:seatComplate();" class="moneybtn enfont">&yen; 0 </a>
        </form>
        
        
    </section>
    
    <section class="container" id="steptwo" style="display:none">
    	<section class="money_cont">
            <h1>${filmTitle}</h1>
            <p>${filmEnglishName}</p>
            <div class="clear45px"></div>
            <p>${cinemaName}</p>
            <p>${screenName}</p>
            <h2 class='enboldfont'>${sessionShowTime}<br>${showTime}</h2>
            <p id="seat_result"></p>   
            <div class="clear45px"></div>
            <p>应付金额</p>
            <p class='enboldfont'><span id='orderCount'></span></p>
            <h3 class='enboldfont'>¥<span id="orderTotalPrice"></span></h3>
            <div class="clear75px"></div>
        </section>
    	<section class="pay wxpay">
        	微信支付
        </section>
    	<section class="pay hypay hidden">
        	余额支付
        </section>
        <section class="pay freepay hidden">
        	使用免费票
        </section>
        <div class="clear45px"></div>
        <!-- <a href="${pageContext.request.contextPath}/wechat/pay?outTradeNo=${filmOrder.orderNumber}&filmName=${filmOrder.filmTitle}&sessionID=${filmOrder.sessionId}&totalPrice=${filmOrder.totalValueCents}" class="gobuy"> -->
    	<a class="gobuy" href="javascript:addOrder();">
    		<span>支付</span>
    		<br>
    		<span class='enfont'>CHECK OUT</span>
    	</a>    	
    </section>
    
    <div class="alert_confirm">
    	<div class="confirm_frame">
    		<h3>请先注册再购票！</h3>
    		<div class="buttons">
    			<a class="button_yes" href="/cinker/usermember/vipRegister">注册</a>
    			<a class="button_no" href="javascript:void(0);">取消</a>
    		</div>
    	</div>
    </div>
    
    <script>
  		var _scheduledFilmId; //影片内部编码
  		var _cinemaId,_sessionId;
		var _HOGroupingCode = 'HOTK000001';//
		var _ticketTypeCode = '${ticketTypeCode}';
		var _priceInCents = '${priceInCents}';
		var _ogTicketTypeCode = '${ticketTypeCodeNoDiscount}';
		var _ogPriceInCents = '${priceNoDiscount}';
		var _userSessionId = '${userSessionId}';
		var _memberLevelId = '${memberLevelId}';
		var _cinemaType = '${cinemaType}';
		var _screenName = '${screenName}';

		var _totalLimit = ${restTicketsCount};
		
        $(document).ready(function() {
        	_scheduledFilmId = document.getElementById("scheduledFilmId").value;
        	_cinemaId = document.getElementById("cinemaId").value;
        	_sessionId = document.getElementById("sessionId").value;
			
			if(_scheduledFilmId.substr(2,2)=='nt'){
				_HOGroupingCode = 'HOTK000005';
			}
        	
        	//getTicketSessions(_cinemaId,_sessionId);
            getSeatsInfo(_cinemaId,_sessionId);
            
            $("#sessionDate").html($("#sessionShowTime").val());
            $("#sessionTime").html($("#showTime").val());
            $(document).attr("title",$("#filmTitle").val());
            
            // only classic member can use charge value pay
            if(parseInt(_memberLevelId)==2 || parseInt(_memberLevelId)==4){
            	$('section.hypay').addClass('clkpay');
            	$('section.hypay').removeClass('hidden');
            	updatePayMethod('member');
            }else if(parseInt(_memberLevelId)==6 && _cinemaType=='Pictures'){
				//_totalLimit = 1;
				$('section.freepay').addClass('clkpay');
            		$('section.freepay').removeClass('hidden');
				updatePayMethod('free');
			}else{
            	$('section.wxpay').addClass('clkpay');
            	updatePayMethod('wechat');
            }
            $('section.pay').bind('click', function(evt){
            	$('section.pay').removeClass('clkpay');
            	$(evt.currentTarget).addClass('clkpay');
          
            	if($(evt.currentTarget).hasClass('freepay')){
            		updatePayMethod('free');
            	}else if($(evt.currentTarget).hasClass('hypay')){
            		updatePayMethod('member');
            	}else{
            		updatePayMethod('wechat');
            	}
            	updatePrice();
            });
            
            $('.button_no').bind('click', function(evt){
            	$(".alert_confirm").hide();
            });
            
        });
        
        
    </script>
</body>
</html>