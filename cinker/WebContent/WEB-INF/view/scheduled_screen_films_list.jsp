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
<title>CINKER PICTURES</title>

</head>
<body class="bg-3">    
	<section class="select-main">
        <section class="sm_left">
            <select class="sl01" id = "cinema" name="cinema" >
            	<c:forEach items="${cinemas}" var="cinema"> 
            		<option value="${cinema.cinemaId}" >${cinema.name}</option>
            	 </c:forEach>
            </select>
        </section>
        <section class="sm_right">
            <select class="sl02" id = "screen" name="screen" >
            	<option value="0" >ALL EVENTS</option>
            	<c:forEach items="${screens}" var="screen"> 
            	<option value="${screen.screenNumber}" >${screen.screenName}</option>
            	 </c:forEach>
            </select>            
        </section>
		<section class="sm_left">
            <select class="sl03">
            	<c:forEach items="${map}" var="map" varStatus="loop">
	            	<option value="${fn:replace(map.key,'/','')}">${map.key}</option>
            	</c:forEach>
            </select>
         </section>
    </section>
    <c:choose>
    	<c:when test="${not empty memberFilmMap}">
			<div class="enfont">
	    		<h5 class="specialTitle enfont">会员活动 MEMBERS</h5>
			</div>
		</c:when>
	</c:choose>
	
	<c:forEach items="${memberFilmMap}" var="memberFilmMap" varStatus="loop">
	    <section class="containerNo special">
	    	<section class="film_main">
	    		<c:forEach items="${memberFilmMap.value}" var="v" varStatus="loop1">
	    			<c:choose>
	    				<c:when test="${loop1.first}">
	    					<c:choose>
								<c:when test="${ empty v.surfaceImage }">
									<img src="${pageContext.request.contextPath}/images/ntevent/bannernv.jpg">
								</c:when>
								<c:otherwise>
									<img src="${pageContext.request.contextPath}${v.surfaceImage}">
								</c:otherwise>
							</c:choose>
							<div class="fm_name">
								<h4 class="enfont">${v.sessionName}</h4>
								<h3>${v.filmTitle}</h3>
								<h4 class="enfont">${v.englishName}</h4>
								<div class="special_sessions">
	    				</c:when>
	    			</c:choose>
	    			<c:choose>
	    				<c:when test="${empty v.url}">
								<a href="${pageContext.request.contextPath}/activity/loginActivity?eventId=${v.sessionId}">
									${fn:substring(v.sessionTime, 5, 16)}
									
								</a>
						</c:when>
						<c:otherwise>
							<div class="special_sessions">
								<a href="${v.url}">
									${fn:substring(v.sessionTime, 5, 16)}
								</a>
						</c:otherwise>
					</c:choose>
	    		</c:forEach>
					</div>
				</div>
	    	</section>
	    </section>
	</c:forEach>
	
    <c:choose>
    	<c:when test="${not empty activityFilmMap}">
			<div class="enfont">
	    		<h5 class="specialTitle enfont">本周经典 CLASSIC</h5>
			</div>
		</c:when>
	</c:choose>

	<c:forEach items="${activityFilmMap}" var="activityFilmMap" varStatus="loop">
	    <section class="containerNo special">
	    	<section class="film_main">
	    		<c:forEach items="${activityFilmMap.value}" var="v" varStatus="loop1">
	    			<c:choose>
	    				<c:when test="${loop1.first}">
	    					<c:choose>
								<c:when test="${ empty v.surfaceImage }">
									<img src="${pageContext.request.contextPath}/images/ntevent/bannernv.jpg">
								</c:when>
								<c:otherwise>
									<img src="${pageContext.request.contextPath}${v.surfaceImage}">
								</c:otherwise>
							</c:choose>
							<div class="fm_name">
								<h4 class="enfont">${v.sessionName}</h4>
								<h3>${v.filmTitle}</h3>
								<h4 class="enfont">${v.englishName}</h4>
								<div class="special_sessions">
	    				</c:when>
	    			</c:choose>
	    			<c:choose>
	    				<c:when test="${empty v.url}">
								<a href="${pageContext.request.contextPath}/activity/loginActivity?eventId=${v.sessionId}">
									${fn:substring(v.sessionTime, 5, 16)}
									
								</a>
						</c:when>
						<c:otherwise>
							<div class="special_sessions">
								<a href="${v.url}">
									${fn:substring(v.sessionTime, 5, 16)}
								</a>
						</c:otherwise>
					</c:choose>
	    		</c:forEach>
					</div>
				</div>
	    	</section>
	    </section>
	</c:forEach>

    <section class="dailyFilms">
		<c:choose>
			<c:when test="${empty map}">
				<div class="enfont">
	   				<h5 class="specialTitle enfont">该影厅本日没有排片</h5>
				</div>
			</c:when>
		    <c:otherwise>
	   			<c:forEach items="${map}" var="map" varStatus="loop">
	    			<div class="dailyDateTitle enfont" id="${map.key}">
	    				<h5 class="enfont" data-date="${map.key}"></h5>
	   			 		<span class="enblodfont dayEn"></span>
	    			</div>
	    			<c:forEach items="${map.value}" var="v">
						<section class="containerNo">
							<section class="film_main">
								<a href="/cinker/film/getFilmDetail?scheduledFilmId=${v.scheduledFilmId}&cinemaId=${v.cinemaId}&dateTime=${v.dateTime}#calendar">
									<img src="${pageContext.request.contextPath}${v.surfaceImageUrl }">
									<div class="fm_name">
										<h4 class="enfont">${v.startTime}</h4>
										<h3>${v.chineseName}</h3>
										<h4 class="enfont">${v.englishName}</h4>
									</div>
									<div class="fm_arrow"></div>
								</a>
							</section>
						</section>
					</c:forEach>
				</c:forEach>
			</c:otherwise>
		</c:choose> 
    </section>
    
    <section class="clear75px"></section>
    <section class="footer">
    	<ul>
        	<a href="${pageContext.request.contextPath}/film/getScheduledFilmByScreen"><li class="clk1"></li></a>
        	<a href="${pageContext.request.contextPath}/usermember/getUserInfo"><li class="clk3"></li></a>
        </ul>
    </section>
    <script type="text/javascript">
	    for(var i=0;i<document.getElementById('cinema').options.length;i++){ 	
			if(document.getElementById('cinema').options[i].value==='${cinemaId}'){														
				document.getElementById('cinema').options[i].selected=true; 
			} 
		} 	
	    
	    for(var i=0;i<document.getElementById('screen').options.length;i++){ 	
			if(document.getElementById('screen').options[i].value==='${inScreenNumber}'){														
				document.getElementById('screen').options[i].selected=true; 
			} 
		} 	
	    $(document).ready(function(){

	    	$('.dailyDateTitle').each(function(index){
	    		var thisDate = $(this).find('h5').data('date');
	    		var thisDateArr = thisDate.split('/');
	    		var mmddDateArr = thisDateArr.slice(1);
	    		var yyyyStr = thisDateArr[0];
	    		var dateStr = mmddDateArr.join('.')+'th';
	    		$(this).find('h5.enfont').html(dateStr);
	    		var myDate = new Date(parseInt(yyyyStr), (parseInt(mmddDateArr[0])-1),mmddDateArr[1]);
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
				id=id.replace(/\//g,"");
				dailyDateTitle.eq(i).attr('id',id);
			}
			
			$('.sl01').on('change',function(){
				var _cinemaId=$(this).val();
				
	        	self.location.href = "/cinker/film/getScheduledFilmByScreen?cinemaId=" + _cinemaId;
				
			});		
			
			$('.sl03').on('change',function(){
				var value=$(this).val();
				$("html,body").animate({scrollTop:$('#'+value).offset().top},1000)
			});
			
			$('.sl02').on('change',function(){
				var _screenNumber=$(this).val();
				var _cinemaId = document.getElementById('cinema').options[document.getElementById('cinema').selectedIndex].value;
				
	        	self.location.href = "/cinker/film/getScheduledFilmByScreen?cinemaId=" + _cinemaId + "&screenNumber=" + _screenNumber;
				
			});	
			$(".dailyFilms .film_main>a").each(function(){
				var dateTimeURL = $(this).attr('href');
				var newURL = dateTimeURL.replace(/2017\/|2018\//g,"");


				$(this).attr('href',newURL);
			})
	    });
    </script>
</body>
</html>