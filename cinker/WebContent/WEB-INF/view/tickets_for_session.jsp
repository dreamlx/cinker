<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<title>场次页</title>

</head>
<body>    
	<section class="banner1">
	
        <section class="content">
            <p>${filmTitle}</p>
        </section>
    	<c:choose>
    		<c:when test="${not empty film.surfaceImageUrl}">
        		<img src="${pageContext.request.contextPath}${film.surfaceImageUrl}">
        	</c:when>
        	<c:otherwise>
        		<img src="${pageContext.request.contextPath}/images/banner1.jpg">
        	</c:otherwise>
        </c:choose>
    </section>
   
    <section class="tab-ul">
        <ul id="dateTab">
        	<li><a id="tabToday" href="#">今天<span class='enfont'></span></a></li>
        	<li><a id="tabTmrw" href="#">明天<span class='enfont'></span></a></li>
        	<li><a id="tabDayAftTmrw" href="#">后天<span class='enfont'></span></a></li>
        </ul>
    </section>

 <c:forEach items="${ticketsInfoList}" var="ticketsInfoList"> 
    <section class="container">
        <a href="/cinker/seat_plan_for_session.jsp?priceInCents=${ticketsInfoList.priceInCents}&scheduledFilmId=${ticketsInfoList.scheduledFilmId}&filmTitle=${filmTitle}&cinemaId=${ticketsInfoList.cinemaId}&sessionId=${ticketsInfoList.sessionId}&showTime=${ticketsInfoList.startTime}&screenName=${ticketsInfoList.screenName}&sessionShowTime=<fmt:formatDate value='${ticketsInfoList.sessionShowTime}' pattern="yyyy-MM-dd"/>">
    
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
                		特惠<br>
                		<span class='enfont'>&yen; ${ticketsInfoList.priceInCents}</span>
                	</p>
                </li>
            </ul>
          
        </a>
      
    </section>
   </c:forEach>
    <section class="clear75px"></section>
   
    <section class="footer">
    	<ul>
        	<a href="${pageContext.request.contextPath}/film/getScheduledFilm"><li class="clk1">选座</li></a>
        	<a href="${pageContext.request.contextPath}/usermember/getUserInfo"><li class="clk3">我的</li></a>
        </ul>
    </section>
    
    <script>
    $(document).ready(function() {
    	var pageDate = getUrlParam('dateTime');
    	
    	var myDate = new Date();
        var todayStr = myDate.pattern("MM-dd");
        $('#tabToday>span').html(todayStr);
        
        myDate.setDate(parseInt(myDate.getDate())+1);
        var TommrowStr = myDate.pattern("MM-dd");
        myDate.setDate(myDate.getDate()+1);
        var DayAfterStr = myDate.pattern("MM-dd");
        
        var liDom;
        switch(pageDate){
        	case '':
        	case todayStr:
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
        
        var href="${pageContext.request.contextPath}/film/getFilmTicket?scheduledFilmId=${film.scheduledFilmId}&cinemaId=${film.cinemaId}&dateTime="
        $('#tabToday').attr('href', href);
        
        $('#tabTmrw>span').html(TommrowStr);
        $('#tabTmrw').attr('href', href+TommrowStr);
        
        $('#tabDayAftTmrw > span').html(DayAfterStr);
        $('#tabDayAftTmrw').attr('href', href+DayAfterStr);
    });
    </script>
</body>
</html>
