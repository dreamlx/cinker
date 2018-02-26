<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<script src="${pageContext.request.contextPath}/js/cinker/seat_plan_for_session.js"></script>

<title>注册成功Registration</title>
</head>
<body class="bg-2">    
    <section class="container">
    	<div class="sucess-img"><img src="${pageContext.request.contextPath}/images/sucess_03.jpg"></div>
        <div class="sucess-sucess">
        	<p>注册成功！</p> 
        	<p class='enfont'>Congratulations! </p>
        	<p>恭喜你已成为了</p> 
        	<p class='enfont'>You are now a</p>
        	<p>三克会员</p>
        	<p class='enfont'>CINKER MEMBER</p>
        </div>
        <div class="sucess-id">会员ID</div>
        <div class="sucess-no">${vistaMemberCardNumber}</div>
        <div class="go-person">
        	<a class="enfont" href="${pageContext.request.contextPath}/usermember/getUserInfo">
        		返回个人中心
        		<br/>Return
        	</a>
        </div>
    </section>
</body>
</html>