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
<title>${film.chineseName}</title>
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
    </section>

    <section class="clear85px"></section>
   	<a class="buy" href="/cinker/film/getCinemaFilmSessions?scheduledFilmId=${film.scheduledFilmId}&cinemaId=${cinemaId}&dateTime=${film.dateTime}">
    	<h6>立即购票</h6>
    	<h5 class="enfont">Calender</h5>
    </a>
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
        
    });
</script>
</body>
</html>
