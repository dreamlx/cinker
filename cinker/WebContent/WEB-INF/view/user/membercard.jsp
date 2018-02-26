<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/common.jsp" %>
<%@ page import="com.cinker.util.*"%>
<title>会员卡</title>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body class="bg-3 member-card">

		<c:if test="${regsuc== 'true'}">
			<section id="success_tips">
				<div class="tipsCN">
					<p>注册成功，恭喜您成为</p> 
					<h1>${memberNameCN}</h1>
				</div>
				<div class="tipsEN enfont">
					<p>CONGRATULATIONS FOR BECOMING</p> 
					<h1>${memberNameEN}</h1>
				</div>		
		    </section>
		</c:if>
	

	<section id="member-card-frame" class="cinkermember-${memberLevelId}">
		<div class='member-card-frame-left'>
			<div class="member-card-logo">
				<c:choose>
					<c:when test="${memberLevelId == '2' || memberLevelId == '4' }">
						<img src='${pageContext.request.contextPath}/images/logo3.png' />
					</c:when>
					<c:otherwise>
						<img src='${pageContext.request.contextPath}/images/logo2.png' />
					</c:otherwise>
				</c:choose>
				
			</div>
			<h1 class='enboldfont'>${memberNameEN}</h1>
			<h2>${memberNameCN}</h2>  
			<p class='enfont'>No. ${userMember.vistaMemberCardNumber}</p>
		</div>
		<div class='member-card-frame-right'>
			<img alt="" src="${pageContext.request.contextPath}/wechat/showQRcode?content=${userMember.vistaMemberCardNumber}&onColor=0x6e6e6e">
		</div>
    </section>
    
    <c:choose>
			<c:when test="${memberLevelId == '3' || memberLevelId == '6'}">
				<p style="margin:0 auto;width:90%;color:#666" class="membershipExpiryDate"></p>
			</c:when>
	</c:choose>
    
    
    <div class="main-txt recharge-header">
    	<h4 class="f-12">您可享受</h4>
    	<p class='en-space enfont'>U CAN ENJOY</p>
  	</div>
	
	<div class="member-desc-img">
		<c:choose>
			<c:when test="${memberLevelId == '1'}">
				<img src="${pageContext.request.contextPath}/images/upgrade_wechat_desc.jpg" />
			</c:when>
			<c:when test="${memberLevelId == '3'}">
				<img src="${pageContext.request.contextPath}/images/upgrade_morden_desc.jpg" />
			</c:when>
			<c:when test="${memberLevelId == '6'}">
				<img src="${pageContext.request.contextPath}/images/upgrade_supermorden_desc.jpg" />
			</c:when>
			<c:when test="${memberLevelId == '2'}">
				<img src="${pageContext.request.contextPath}/images/upgrade_classic_desc.jpg" />
			</c:when>
			<c:when test="${memberLevelId == '4'}">
				<img src="${pageContext.request.contextPath}/images/upgrade_superclassic_desc.jpg" />
			</c:when>
		</c:choose>
		
	</div>
	<p style="margin:0 auto;width:90%;color:#999;margin-top:25px">注：免费观影券仅限线下门店使用，具体请咨询微信客服或门店工作人员</p>
   	<script>
   	var expiryDateStr = "${expiryDate}";
   	var expirtyDate = expiryDateStr.substring(6,expiryDateStr.length - 2);
   	var expDate = new Date();
   	expDate.setTime(expirtyDate);
   	$(document).ready(function() {
   		$('.membershipExpiryDate').html('会员期限：'+expDate.pattern("yyyy-MM-dd"));
   	});
   	</script>
</body>
</html>