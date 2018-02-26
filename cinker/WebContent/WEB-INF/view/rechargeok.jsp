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
<title>会员充值</title>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body class="bg-1">
	<section class="msg-box">
		<img src="${pageContext.request.contextPath}/images/logo2.png" alt="" class="logo">
		<div class="msg-txt">
		  <p>储值成功</p>
		  <p>Recharge Success</p>
	  <!--     <p>您现在已经成为了</p>
		  <p>CINKER CINEMAS观影权益会员</p>  -->
		</div>
	</section>
	<footer style="height:120px">
		<a class="black-btn" href="${pageContext.request.contextPath}/usermember/getUserInfo">
		  <p>返回</p>
		  <p>Back</p>
		</a>
	</footer>
 <!--    <div class="member-date">
      <p>会员有效期至</p>
      <p>2018-04-01</p>
    </div>
  
    -->
</body>
</html>