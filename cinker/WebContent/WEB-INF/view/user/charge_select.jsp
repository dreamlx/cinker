<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/common.jsp" %>
<%@ page import="com.cinker.util.*"%>
<title>会员升级</title>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="js/ltie9/html5shiv.min.js"></script>
  <script src="js/ltie9/respond.min.js"></script>
<![endif]-->
</head>
<body class="bg-2">
	<div class="main-txt recharge-header">
    	<h4 class="f-12">会员升级，享受更多权益</h4>
    	<p class='en-space enfont'>MORE PROFITS</p>
  	</div>
    <div class="recharge-class">
    	<a href="${pageContext.request.contextPath}/recharge/confirmRecharge?&orderId=${orderId}&userSessionId=${userSessionId}&headOfficeItemCode=HY0000000000001&recognitionId=5&priceInCents=28800">
    		<div class='member-upgrade-title'>
    			<h4 class="en-space enboldfont">MORDERN</h4>
    			<h4 class="recharge-money enboldfont">¥288</h4>
    		</div>
    		<div class='member-upgrade-info'>
    			<p class=''>三克摩登会员</p>
    			<p class="text-right">全年超值VIP观影体验 ></p>
    		</div>
    	</a>
    </div>

    <div class="recharge-class">
    	<a href="${pageContext.request.contextPath}/recharge/confirmRecharge?&orderId=${orderId}&userSessionId=${userSessionId}&headOfficeItemCode=HY0000000000004&recognitionId=24&priceInCents=298800">
      		<div class='member-upgrade-title'>
    			<h4 class="en-space enboldfont">MODERN SUPER</h4>
    			<h4 class="recharge-money enboldfont">¥2988</h4>
    		</div>
    		<div class='member-upgrade-info'>
    			<p class=''>三克超级摩登会员</p>
    			<p class="text-right">三克映画全年无限次观影 ></p>
    		</div>
      	</a>
    </div>

    <div class="recharge-class">
    	<a href="${pageContext.request.contextPath}/recharge/confirmRecharge?&orderId=${orderId}&userSessionId=${userSessionId}&headOfficeItemCode=HY0000000000002&recognitionId=25&priceInCents=300000">
      		<div class='member-upgrade-title'>
    			<h4 class="en-space enboldfont">CLASSIC</h4>
    			<h4 class="recharge-money enboldfont">¥3000</h4>
    		</div>
    		<div class='member-upgrade-info'>
    			<p class=''>三克经典会员</p>
    			<p class="text-right">电影与美食经典共享 ></p>
    		</div>
      	</a>
    </div>

    <div class="recharge-class">
    	<a href="${pageContext.request.contextPath}/recharge/confirmRecharge?&orderId=${orderId}&userSessionId=${userSessionId}&headOfficeItemCode=HY0000000000003&recognitionId=26&priceInCents=3000000">
      		<div class='member-upgrade-title'>
    			<h4 class="en-space enboldfont">CLASSIC SUPER</h4>
    			<h4 class="recharge-money enboldfont">¥30000</h4>
    		</div>
    		<div class='member-upgrade-info'>
    			<p class=''>三克超级经典会员</p>
    			<p class="text-right">电影与美食的超级礼遇 ></p>
    		</div>
      	<a href="#">
    </div>

</body>

</html>
