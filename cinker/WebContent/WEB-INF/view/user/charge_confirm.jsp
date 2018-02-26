<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/common.jsp" %>
<%@ page import="com.cinker.util.*"%>
<title>会员充值</title>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body class='bg-2'>
	<section class="member-info">
		<c:choose>
			<c:when test="${headOfficeItemCode=='HY0000000000001'}">
    	 		<div class="member-classic">
    	 			<p>三克摩登会员</p>
    	 			<p class="enfont">CINKER MODREN</p>
 				</div>
    	 		<div class="main-txt recharge-header">
    				<h4 class="f-12">全年超值VIP观影体验</h4>
  				</div>
  				<div class="member_upgrade_desc_img">
    				<img src="${pageContext.request.contextPath}/images/upgrade_morden_desc.jpg" />
  				</div>
    	 	</c:when>
    	 
    	 	<c:when test="${headOfficeItemCode=='HY0000000000004'}">
    	 		<div class="member-classic">
    	 			<p>三克超级摩登会员</p>
    	 			<p class="enfont">CINKER MODREN</p>
    	 		</div>
    	 		<div class="main-txt recharge-header">
    				<h4 class="f-12">三克映画全年无限次观影</h4>
  				</div>
  				<div class="member_upgrade_desc_img">
    				<img src="${pageContext.request.contextPath}/images/upgrade_supermorden_desc.jpg" />
  				</div>
    	 	</c:when>
    	 
    	 	<c:when test="${headOfficeItemCode=='HY0000000000002'}">
    	 		<div class="member-classic">
    	 			<p>三克经典会员</p>
    	 			<p class="enfont">CINKER CLASSIC</p>
    	 		</div>
    	 		<div class="main-txt recharge-header">
    				<h4 class="f-12">电影与美食经典共享</h4>
  				</div>
  				<div class="member_upgrade_desc_img">
    				<img src="${pageContext.request.contextPath}/images/upgrade_classic_desc.jpg" />
  				</div>
    	 	</c:when>
    	 
    	 	<c:when test="${headOfficeItemCode=='HY0000000000003'}">
    	 		<div class="member-classic">
    	 			<p>三克超级经典会员</p>
    	 			<p class="enfont">CINKER SUPER CLASSIC</p>
    	 		</div>
    	 		<div class="main-txt recharge-header">
    				<h4 class="f-12">电影与美食的超级礼遇</h4>
  				</div>
  				<div class="member_upgrade_desc_img">
    				<img src="${pageContext.request.contextPath}/images/upgrade_superclassic_desc.jpg" />
  				</div>
    	 	</c:when>
	</c:choose>
  
  </section>
  <footer class="payment-btn-group">
    <form id='chargeForm' action="${pageContext.request.contextPath}/recharge/orderPayment?&orderId=${orderId}&userSessionId=${userSessionId}">
		<input type="hidden" name='orderId' value="${orderId}">
		<input type="hidden" name='userSessionId' value="${userSessionId}">
		<input type="hidden" name='headOfficeItemCode' value='${headOfficeItemCode}'>
		<input type="hidden" name='recognitionId' value='${recognitionId}'>
		<input type="hidden" name='priceInCents' value='${priceInYuan}'>
		<a class="sub-btn" id="submitForm">
			<p>¥${priceInYuan}元</p>
		</a>
	</form>
  </footer>
  <p class="member_upgrade_rules">有效期1年，详情请见<a href="#">《三克会员规则》</a></p>
  <div class="clear10px"></div>
</body>
<script>

$(document).ready(function(){
	
	$('#submitForm').bind('click', function(){
		
		$('form#chargeForm').submit();
	})
})
</script>
</html>