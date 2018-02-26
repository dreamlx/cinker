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
<title>CINKER CINEMAS</title>

</head>
<body class="bg-3 cnm">    
	<section class="select-main">
        <section class="sm_left">
            <select class="sl01">
            	<option value="0200" id="beijing">北京</option>
            </select>
        </section>
        <section class="sm_right">
            <select class="sl02" id = "cinema" name="cinema" >
            	<c:forEach items="${cinemas}" var="cinema"> 
            	<option value="${cinema.cinemaId}" >${cinema.name}</option>
            	 </c:forEach>
            </select>            
        </section>
    </section>
	<!-- <div class="enfont">
	    <h5 class="specialTitle enfont">Special</h5>
	</div>
    <c:forEach items="${activityFilmList}" var="activityFilmList">
	<section class="containerNo">
		<section class="film_main">
			<c:choose>
				<c:when test="${empty activityFilmList.url}">
					<a href="${pageContext.request.contextPath}/activity/loginActivity?eventId=${activityFilmList.sessionId}">
						<c:choose>
							<c:when test="${ empty activityFilmList.surfaceImage }">
								<img src="${pageContext.request.contextPath}/images/ntevent/bannernv.jpg">
							</c:when>
							<c:when test="${not empty activityFilmList.surfaceImage }">
								<img src="${pageContext.request.contextPath}${activityFilmList.surfaceImage}">
							</c:when>
							</c:choose>
							<div class="fm_name">
								<h4 class="enfont">${activityFilmList.sessionTime}</h4>
								<h3>${activityFilmList.filmTitle}</h3>
								<h4 class="enfont">${activityFilmList.englishName}</h4>
							</div>
						<div class='fm_arrow'></div>
					</a>
				</c:when>
				<c:when test="${not empty activityFilmList.url}">
					<a href="${activityFilmList.url}">
						<c:choose>
							<c:when test="${ empty activityFilmList.surfaceImage }">
								<img src="${pageContext.request.contextPath}/images/ntevent/bannernv.jpg">
							</c:when>
							<c:when test="${not empty activityFilmList.surfaceImage }">
								<img src="${pageContext.request.contextPath}${activityFilmList.surfaceImage}">
							</c:when>
							</c:choose>
							<div class="fm_name">
								<h4 class="enfont">${activityFilmList.sessionTime}</h4>
								<h3>${activityFilmList.filmTitle}</h3>
								<h4 class="enfont">${activityFilmList.englishName}</h4>
							</div>
						<div class='fm_arrow'></div>
					</a>
				</c:when>
			</c:choose>
		</section>
	</section>
    </c:forEach> -->
    
    <section class="dailyFilms">
		
		
		<c:forEach items="${films}" var="map" varStatus="loop">
			<div class="cnm_film">
				
					<div class="cnm_film_poster">
						<a href="/cinker/film/getCinemaFilmDetail?scheduledFilmId=${map.scheduledFilmId}&cinemaId=${map.cinemaId}">
							<img src="${pageContext.request.contextPath}${map.surfaceImageUrl}" />
						</a>
					</div>
					<div class="cnm_film_info">
						<h2>
							<a href="/cinker/film/getCinemaFilmDetail?scheduledFilmId=${map.scheduledFilmId}&cinemaId=${map.cinemaId}">
								${map.chineseName} 
								<span>${map.filmDType}</span>
								<span>${map.sessions[0].description[0]}</span>
							</a>
						</h2>
						<div>
							<div class='cnm_film_detail'>
								<a href="/cinker/film/getCinemaFilmDetail?scheduledFilmId=${map.scheduledFilmId}&cinemaId=${map.cinemaId}">
									<p>${map.ratingDescription}</p>
									<p>${map.filmActors}</p>
								</a>
							</div>
							<div class="cnm_film_right">
								<a href="/cinker/film/getCinemaFilmSessions?scheduledFilmId=${map.scheduledFilmId}&cinemaId=${map.cinemaId}&dateTime=${map.dateTime}">
									购买
								</a>
							</div>
						</div>
					</div>
				
				
			</div>
		</c:forEach>
    </section>
    <section class="clear75px"></section>
    <section class="footer">
    	<ul>
        	<a href="${pageContext.request.contextPath}/film/getScheduledFilm"><li class="clk1"></li></a>
        	<a href="${pageContext.request.contextPath}/usermember/getUserInfo"><li class="clk3"></li></a>
        </ul>
    </section>
    <script type="text/javascript">
    for(var i=0;i<document.getElementById('cinema').options.length;i++){ 	
		if(document.getElementById('cinema').options[i].value==='${inCinemaId}'){														
			document.getElementById('cinema').options[i].selected=true; 
		} 
	} 		
	    $(document).ready(function(){
	    	//$films = $('section.containerNo');
	    	//$films.sort(function (a, b) {
                //var sort1 = a.getAttribute('data-sort');
                //var sort2 = b.getAttribute('data-sort');

                //return (new Date(sort1).getTime()- new Date(sort2).getTime());
            //});
	    	//$films.detach().appendTo("#filmList");
	    	$('.dailyDateTitle').each(function(index){
	    		var thisDate = $(this).find('h5').data('date');
	    		var thisDateArr = thisDate.split('/');
	    		var dateStr = thisDateArr.join('.')+'th';
	    		$(this).find('h5.enfont').html(dateStr);
	    		var myDate = new Date(2017, (parseInt(thisDateArr[0])-1),thisDateArr[1]);
	    		var myDay = myDate.getDay();
	    		var myDayEn;
	    		switch(myDay){
	    			case 1:
	    				myDayEn = 'monday';
	    				break;
	    			case 2:
	    				myDayEn = 'tuesday';
	    				break;
	    			case 3:
	    				myDayEn = 'wednesday';
	    				break;
	    			case 4:
	    				myDayEn = 'thursday';
	    				break;
	    			case 5:
	    				myDayEn = 'friday';
	    				break;
	    			case 6:
	    				myDayEn = 'saturday';
	    				break;
	    			default:
	    				myDayEn = 'sunday';
	    				break;
	    		}
	    		$(this).find('.dayEn').html(myDayEn);
	    		
	    	});
			
			var dailyDateTitle=$('.dailyDateTitle');
			for(var i=0;i<dailyDateTitle.length;i++){
				var id=dailyDateTitle.eq(i).attr('id');
				id=id.replace(/\//,"");
				dailyDateTitle.eq(i).attr('id',id);
			}
			
			$('.sl03').on('change',function(){
				var value=$(this).val();
				$("html,body").animate({scrollTop:$('#'+value).offset().top},1000)
			});
			
			$('.sl02').on('change',function(){
				var _cinemaId=$(this).val();
	        	self.location.href = "/cinker/film/getScheduledFilm?cinemaId=" + _cinemaId;
			});
	    });
    </script>
</body>
</html>
