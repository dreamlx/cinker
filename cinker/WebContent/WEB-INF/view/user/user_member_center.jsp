<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/common.jsp"%> 

<title>会员中心 Membership Center</title>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="js/ltie9/html5shiv.min.js"></script>
  <script src="js/ltie9/respond.min.js"></script>
<![endif]-->
</head>
<body class="bg-3">    
	<section class="containera">
    	<section class="member">
    	<c:choose>
    	   <c:when test= "${empty cardNumber}">
	        	<section class="mb_membera"><img src="${userMember.userHeadImageUrl}"></section>
	            <section class="mb_line enfont">Hello！${userMember.userNickName}</section>
	            <section class="mb_line">
					<a id='mb_user_reg_btn' href="${pageContext.request.contextPath}/usermember/vipRegister">
						<p>注册成为会员，享受更多权益</p>
						<p class='enfont'>Register to become a member <br>
						 to enjoy more benefits.</p>
					</a>
	            </section>
	            <section class="mb_middle" style="margin-top:200px;">
					<a href="${pageContext.request.contextPath}/usermember/getOrderList">
						<img src="${pageContext.request.contextPath}/images/memberpic1.png" width="50%">
						<p class='enfont'>
							我的订单 <br/>
							My Order
						</p>
					</a>
				</section>
           </c:when>


           <c:when test="${memberLevelId > '0'}">
           		<div>
           			<div class='mb_left'>
           				<span class='mb_score'>${name_2}</span>
           				<br/>
           				${pointsRemaining_2}
           			</div>
           			<div class="mb_member"><img src="${userMember.userHeadImageUrl}"></div> 
           			<div class='mb_right'>
           				<span class='mb_score'>${name_1}</span>
           				<br/>
           			 	${pointsRemaining_1}
           			 </div>  
           		</div>
	                  
	           <section class="mb_line">
	           		<h5>你好！${userMember.userNickName}</h5>
	           		<p class='enfont'>
						${memberLevelName}
					</p>
	           	</section>
	           
	           <section class="memico">
					<section class="mb_50">
						<a href="${pageContext.request.contextPath}/usermember/getOrderList">
							<img src="${pageContext.request.contextPath}/images/n_memberpic1.png" width="38%">
							<p>我的订单</p>
							<p class='enfont'>
								MY ORDER
							</p>
						</a>
					</section>
					<section class="mb_50">
						<a href="${pageContext.request.contextPath}/usermember/vipPersonInformation?userSessionId=${userSessionId}&clubId=${clubId}&memberId=${memberId}">
							<img src="${pageContext.request.contextPath}/images/n_memberpic4.png" width="38%">
							<p>个人信息</p>
							<p class='enfont'>PROFILE</p>
						</a>
					</section>
					<section class="mb_50">
						<a href="${pageContext.request.contextPath}/usermember/benifits?userSessionId=${userSessionId}">
							<img src="${pageContext.request.contextPath}/images/n_memberpic5.png" width="38%">
							<p>会员权益</p>
							<p class='enfont'>BENEFITS</p>
						</a>
					</section>
					<section class="mb_50">
						<a href="${pageContext.request.contextPath}/usermember/getMemberCard?userSessionId=${userSessionId}">
							<img src="${pageContext.request.contextPath}/images/n_memberpic6.png" width="38%">
							<p>会员卡</p>
							<p class='enfont'>CARD</p>
						</a>
					</section>
<!--
					<section class="mb_50"><a href="#"><img src="${pageContext.request.contextPath}/images/n_memberpic3.png" width="38%"></a><p class='enfont'>积分余额<br>Points</p></section>
-->					<section class="mb_50">
						<a href="${pageContext.request.contextPath}/recharge/firstRecharge?userSessionId=${userSessionId}">
							<img src="${pageContext.request.contextPath}/images/n_memberpic2.png" width="38%">
							<p>会员升级</p>
							<p class='enfont'>UPGRADE</p>
						</a>
					</section>
					
					<section class="mb_50">
						<a href="${pageContext.request.contextPath}/usermember/qanda?userSessionId=${userSessionId}">
							<img src="${pageContext.request.contextPath}/images/n_memberpic7.png" width="38%">
							<p>常见问题</p>
							<p class='enfont'>Q&A</p>
						</a>
					</section>

					<div style="clear:both"></div>
	           </section>
           </c:when >
        </c:choose>
        </section>
    </section>
</body>
</html>