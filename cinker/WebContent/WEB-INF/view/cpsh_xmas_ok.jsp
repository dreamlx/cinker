<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.cinker.util.*"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0,viewport-fit=cover">
    <title>预定成功</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cpsh/weui.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cpsh/newyear2018.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cpsh/example.css"/>
</head>
<body ontouchstart>
	<div class="container" id="container"></div>

	<script type="text/html" id="tpl_home">
	<div class="page">
	    <div class="page__hd">
	        <div class="weui-flex" style="margin-top:15%">
	            <div class="weui-flex__item">
	            	<img src="${pageContext.request.contextPath}/images/cpsh/book_success.jpg" width="100%">
	            </div>
	        </div>
	    </div>
	</div>
	</script>



	<script src="${pageContext.request.contextPath}/js/cpsh/zepto.min.js"></script>
    <script src="https://res.wx.qq.com/open/libs/weuijs/1.0.0/weui.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cpsh/example.js"></script>
</body>