<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/common.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport" content="initial-scale=1.0,user-scalable=no,minimum-scale=1.0,maximum-scale=1.0"/>
<meta name="description" content="">
<meta name="author" content="">
<title>详情页</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.theme.default.min.css" />
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/cinker/owl.carousel.min.js"></script>
<style>
	.movie-title{
	    position: absolute;
	    width: 100%;
	    z-index: 5;
	    bottom: 60px;
    }
	.movie-title h1{
		text-align:center;
		font-size:18px;
		color:white;
		margin:5px;
	}
	.movie-title h2{
		text-align:center;
		font-size:18px;
		color:white;
		margin:3px;
	}
</style>
</head>
<body class="bg-2">    
	<section class="detail-main">		
		<div class="owl-carousel">
			<c:choose>
	        	<c:when test="${not empty filmContentImage}">
	        		<c:forEach var="fct" items="${filmContentImage}">
	        			<img width="100%" src="${pageContext.request.contextPath}${fct.imageUrl}">
	        		</c:forEach>
	        	</c:when>
	       		<c:otherwise>
	       			<img width="100%" src="${pageContext.request.contextPath}/images/banner.jpg">
	       		</c:otherwise>
	 		</c:choose>
	 	</div>
    </section>
    <section class="container">
    	<section class="det_cont">
    		<h5 class="enboldfont">${film.englishName}</h5>
    		<h6>${film.chineseName}</h6> 		
           	
           	${film.synopsis}
           	影片时长: ${film.runTime} 分钟 <br/> 
        </section>
    	<section class="det_conta">
        	<h4>电影简介</h4>
			${film.ratingDescription}
        </section>
    	<section class="det_conta">
        	<h4>三克观点</h4>
        	${film.cinecism}
        	
        </section>
    </section>
    
    
    <section class="tab-ul" id='calendar'>
        <ul id="dateTab">
        	<li><a id="tabToday" href="#"><span class='enfont'>${filmDateTime}</span></a></li>
        	<li><a id="tabTmrw" href="#"><span class='enfont'></span></a></li>
        	<li><a id="tabDayAftTmrw" href="#"><span class='enfont'></span></a></li>
        </ul>
    </section>
   	<c:choose>
		<c:when test="${ticketsInfoList!=null && fn:length(ticketsInfoList) > 0}">
		
			 <c:forEach items="${ticketsInfoList}" var="ticketsInfoList"> 
			    <section class="container">
			       <!--  <a href="/cinker/seat_plan_for_session.jsp?userSessionId=${orderId}&scheduledFilmId=${ticketsInfoList.scheduledFilmId}&filmTitle=${filmTitle}&cinemaId=${ticketsInfoList.cinemaId}&sessionId=${ticketsInfoList.sessionId}&showTime=${ticketsInfoList.startTime}&screenName=${ticketsInfoList.screenName}&sessionShowTime=<fmt:formatDate value='${ticketsInfoList.sessionShowTime}' pattern='yyyy-MM-dd'/>">  -->
			         <a href="${pageContext.request.contextPath}/film/getSessionForSeat?userSessionId=${orderId}&scheduledFilmId=${ticketsInfoList.scheduledFilmId}&filmTitle=${filmTitle}&cinemaId=${ticketsInfoList.cinemaId}&sessionId=${ticketsInfoList.sessionId}&showTime=${ticketsInfoList.startTime}&screenName=${ticketsInfoList.screenName}&sessionShowTime=<fmt:formatDate value='${ticketsInfoList.sessionShowTime}' pattern='yyyy-MM-dd'/>&ticketTypeCodeNoDiscount=${ticketsInfoList.ticketTypeCodeNoDiscount}&priceNoDiscount=${ticketsInfoList.priceNoDiscount}&ticketTypeCode=${ticketsInfoList.ticketTypeCode}&priceInCents=${ticketsInfoList.priceInCents}&memberLevelId=${ticketsInfoList.memberLevelId}&cinemaType=${ticketsInfoList.cinemaType}">   
			            <ul class="c-ul">
			                <li class="clk1"><h3 class='enfont'>${ticketsInfoList.startTime}</h3><p></p><p>${ticketsInfoList.endTime}结束</p></li>
			                <li class="clk2">
			                	<p>
			                		<span class='film_type_tag'>
			                			${ticketsInfoList.description}
			                		</span>
			                		<br/>
			                		${ticketsInfoList.screenName}
			                	</p>
			                </li>
			                <li class="clk3">
			                	<p>
			                	<c:choose>
			                		<c:when test="${ticketsInfoList.priceInCents != ticketsInfoList.priceNoDiscount}">
			                			会员特惠
			                		</c:when>	
			                		<c:when test="${ticketsInfoList.priceInCents == ticketsInfoList.priceNoDiscount}">
			                			原价
			                		</c:when>		                		
			                	</c:choose>
			                		 <br>
			                		<span class='enfont'>&yen; ${ticketsInfoList.priceInCents}</span>
			                		<code>${ticketsInfoList.priceNoDiscount}</code>
			                	</p>
			                </li>
			                <li class="clk4">
			                	<div class='buybtn'>
			                		购买<br>
			                		<span class="enfont">BUY</span>
			                	</div>
			                </li>
			            </ul>
			        </a>
			      
			    </section>
			   </c:forEach>
		</c:when>
		<c:otherwise>
			<ul class="c-ul" style="text-align:center">
				当日没有排片                
			</ul>
		</c:otherwise>
	</c:choose>
    <section class="clear85px"></section>
   
	<script type="text/javascript">
    $(document).ready(function(){
        $(".owl-carousel").owlCarousel({
			items:1,
            center: false,
            loop:true,
            nav:false,
            dots:true,
            autoplay:true,
            autoplayTimeout:3000
            
        });
        
		var pageDate = getUrlParam('dateTime');
		var firstDate = '${filmDateTime}';
		
		var thisMonth = parseInt(firstDate.substr(0,2));
		var thisDay = parseInt(firstDate.substr(3,2));
    	var myDate = new Date();
    	myDate.setMonth(thisMonth-1, thisDay);
    	//myDate.setDate(thisDay);
		
    	myDate.setDate(parseInt(myDate.getDate())+1);
        var TommrowStr = myDate.pattern("MM/dd");
        myDate.setDate(myDate.getDate()+1);
        var DayAfterStr = myDate.pattern("MM/dd");
        
        var liDom;
        switch(pageDate){
        	case '':
        	case firstDate:
        		liDom = $('#dateTab>li').get(0);
        		break;
        	case TommrowStr:
        		liDom = $('#dateTab>li').get(1);
        		break;
        	case DayAfterStr:
        		liDom = $('#dateTab>li').get(2);
        		break;
        }
        $(liDom).addClass('clk');
        
        var href="${pageContext.request.contextPath}/film/getFilmDetail?scheduledFilmId=${film.scheduledFilmId}&cinemaId=${film.cinemaId}&dateTime="
        $('#tabToday').attr('href', href+'#calendar');
        
        $('#tabTmrw>span').html(TommrowStr);
        $('#tabTmrw').attr('href', href+TommrowStr+'#calendar');
        
        $('#tabDayAftTmrw > span').html(DayAfterStr);
        $('#tabDayAftTmrw').attr('href', href+DayAfterStr+'#calendar');
    });
</script>
</body>
</html>
