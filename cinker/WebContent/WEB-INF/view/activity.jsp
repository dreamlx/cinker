<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/common.jsp" %>
<%@ page import="com.cinker.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>活动详情</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.theme.default.min.css" />
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script src="${pageContext.request.contextPath}/js/cinker/owl.carousel.min.js"></script>
<style>
	#myForm select{width:80%;color:#ccc;border: none;background: none;font-size:18px;margin-top: 3%;}
</style>
</head>
<body class="bg-2">
  <section class="detail-main">		
		<div class="owl-carousel">
			<c:choose>
	        	<c:when test="${not empty image}">
	        		<c:forEach var="image" items="${image}">
	        			<img width="100%" src="${pageContext.request.contextPath}${image.imageUrl}">
	        		</c:forEach>
	        	</c:when>
	       		<c:otherwise>
	       			<img width="100%" src="${pageContext.request.contextPath}/images/banner.jpg">
	       		</c:otherwise>
	 		</c:choose>
	 	</div>
    </section>
  <section class="main-txt" id="orderForm">
    <p>${activityFilm.englishName}</p>
    <p>${activityFilm.chineseName}</p>
    <div class="live-info marg enfont">
      <div>
        <p>${date}</p>
      </div>
      <div>
        <p>${time}</p>
      </div>
      <div>
        <p>${activityFilm.totalValueCents}</p>
        <p>Per Person</p>
      </div>
    </div>
    <div class="live-desc">
      ${activityFilm.language} 
     
    </div>
    <div class="live-signup bg-profile enfont">
      <h4>预约</h4>
      <p>SIGN UP</p>
      <form action="${pageContext.request.contextPath}/activity/getQRcode" id="myForm">
      <input name="orderId" value="${orderId}" type="hidden">
      <input id="eventId" name="eventId" value="${eventId}" type="hidden">
      <input name="userNumber" value="${userNumber}" type="hidden">
      
      <div class="reg-input">
        <div class="ri_01">姓名</div>
        <div class="ri_02"><input type="text" id="formName" name="name" class="reg-input01" maxlength='20' placeholder="姓名" ></div>
      </div>
      <div class="reg-txt">name</div>
      <div class="reg-input">
        <div class="ri_01">手机号码*</div>
        <div class="ri_02"><input type="text" id="formPhone" name="phone" class="reg-input01" maxlength='11' placeholder="您的手机号码" ></div>
      </div>
      <div class="reg-txt">Phone</div>
      <div class="reg-input">
        <div class="ri_01">人数</div>
        <div class="ri_02">
        	<select name="quaty">
        	<c:choose>
	        	<c:when test="${memberLevelId == '6'}">	
                	<option value="1" selected="selected">${restTicketsCount} person</option>
	        	</c:when>
	       		<c:otherwise>
	       			<option value="1" selected="selected"> 1 person</option>
                	<option value="2">2 persons</option>
                	<option value="3">3 persons</option>
                	<option value="4">4 persons</option>
                	<option value="5">5 persons</option>
                	<option value="6">6 persons</option>
                	<option value="7">7 persons</option>
                	<option value="8">8 persons</option>
                	<option value="9">9 persons</option>
                	<option value="10">10 persons</option>
	       		</c:otherwise>
	 		</c:choose>                
            </select>
		</div>
      </div>
      <div class="reg-txt">Total ${memberLevelLock}</div>
      <div>
      		<c:if test="${restTicketsCount > '0' }">
      		
      		
      			<c:choose>
	        		<c:when test="${memberCanBook}">	
                		<button class="f-red-btn" onclick="myfunction();" type="submit">
      						<p>立即预约</p>
      						<p>RESERVATION</p>
      					</button>  
	        		</c:when>
	        		<c:otherwise>
	        			<c:choose>
	        				<c:when test="${memberLevelLock == 1 }">
	        					<p>仅限注册会员及以上可预约</p>
	        					<p>Only available for CINKER members and above</p>
	        				</c:when>
	        				<c:when test="${memberLevelLock == 2 }">
	        					<p>仅限摩登及以上会员</p>
	        					<p>Only available for CINKER MODERN members and above</p>
	        				</c:when>
	        				<c:otherwise>
	        					<p>仅限注可预约</p>
	        				</c:otherwise>
	        			</c:choose>
	        			

	        		</c:otherwise>
	 			</c:choose>  
      		</c:if>
		</div>
    	</form>
    </div>
  </section>
  <script>
	  var msg = '${message}';
	  if(msg == '名额已满'){
	      alert('名额已满,请选择其他影片');
	      window.location.href = '/cinker/film/getScheduledFilm';
	  }
	  if(msg == '超出规定数量'){
	      alert('超出规定购票数量,请重新选择');
	      window.location.href = '/cinker/activity/loginActivity?eventId=${eventId}';
	  }
	$(document).ready(function(){
		if($('.owl-carousel').children().length>1){
			$(".owl-carousel").owlCarousel({
				items:1,
				center: false,
				loop:true,
				nav:false,
				dots:false,
				autoplay:true,
				autoplayTimeout:3000				
			});
		}else{
			$(".owl-carousel").show();
		}
		$('#myForm').submit(function(){
            var name = $('#formName').val();
            var phone = $('#formPhone').val();
            if(name=='' || phone == ''){
                alert('请输入您的姓名和手机号');
                return false;
            }
            if(!(/^1(3|4|5|7|8)\d{9}$/.test(phone))){
                alert("手机号码有误，请重填"); 
                return false;
            }
        })
	})
  	
  
  </script>
  <footer class="live-footer">
    
    <img src="${pageContext.request.contextPath}/images/fo-pic2.png" class="live-footer-pic">
  </footer>

</body>
</html>