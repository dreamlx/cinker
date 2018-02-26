<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/common.jsp" %>
<%@ page import="com.cinker.util.*"%>
<title>个人信息Profile</title>
<link href="${pageContext.request.contextPath}/css/pikaday-package.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/modernizr.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script src="${pageContext.request.contextPath}/js/cinker/profile.js"></script>
</head>
<body class="bg-2">
    <section class="container">
    	<div class='page-logo'>
			<img src='${pageContext.request.contextPath}/images/logo2.png'>
		</div>
		<form id="profleForm" action="${pageContext.request.contextPath}/usermember/profile">
			<input name="userSessionId" id="userSessionId" type="hidden" value="${userSessionId}">
			<input name="clubId" id="clubId" type="hidden" value="${clubId}">
			<input name="memberId" id="memberId" type="hidden" value="${memberId}">
			
    		<section class="register-main">
 			<div class="reg-input">
				<div class="ri_01">
					<h5>手机号码</h5>
					<p class="reg-txt">Phone #</p>
				</div>
 				<div class="ri_02"><input readonly="readonly" maxlength="11" type="text" class="reg-input01" value='${userMember.phone}'></div>
			</div>
			<div class="clear10"></div>
			
			<div class="reg-input left">
				<div class="ri_01">
					<h5>姓</h5>
					<p class="reg-txt">Last Name</p>
				</div>
				<div class="ri_03"><input maxlength="20" type="text" name="lastName" id='lastNameInput' class="reg-input01" value="${userMember.lastName}"></div>
			</div>
			
			<div class="reg-input right">
				<div class="ri_01">
					<h5>名</h5>
					<p class="reg-txt">First Name</p>
				</div>
				<div class="ri_03"><input maxlength="20" type="text" name="firstName" id='firstNameInput' class="reg-input01" value="${userMember.firstName}"></div>
			</div>
			<div class="clear10"></div>

			  
			<div class="reg-input">
				<div class="ri_01">
					<h5>邮箱</h5>
					<p class="reg-txt">E-mail</p>
				</div>
				<div class="ri_02"><input type="text" id="emailInput" name='email' class="reg-input01" value="${userMember.email}"></div>
			</div>
			<div class="clear10"></div>

			<div class="reg-input">
				<div class="ri_01">
					<h5>生日</h5>
					<p class="reg-txt">Date of Birth</p>
				</div>
				<c:choose>
					<c:when test="${userMember.birthday == NULL}">
						<div class="ri_02"><input type="date" name="birthday" id="birthday" class="reg-input01" value='${userMember.birthday}' placeholder="${userMember.birthday}"></div>
					</c:when>
					<c:otherwise>
						<div class="ri_02"><input id="birthday" class="reg-input01" value='${userMember.birthday}' readonly="readonly" ></div>	
    				</c:otherwise>
				</c:choose>
				
			</div>
			
			<div class="reg-input">
				<div class="ri_01">
					<h5>性别</h5>
					<p class="reg-txt">Gender</p>
				</div>
				<div class="ri_02">
					<select class="" id="sexSelect" name="sex" >
				<c:choose>
					<c:when test="${userMember.sex == '1'}">
						<option value="1" selected>男</option>
						<option value="2">女</option>	
					</c:when>
					<c:when test="${userMember.sex == '2'}">
						<option value="1">男</option>
						<option value="2" selected>女</option>	
					</c:when>
					<c:otherwise>
						<option value="1">男</option>
						<option value="2">女</option>	
    					</c:otherwise>
				</c:choose>
					</select>
				</div>
			</div>

        </section>
        <div class="clear10"></div>
        <button type='submit' class="sub-btn enfont">修改个人信息<br/>Save</button>
        <div class="clear10"></div>
        <a class="gray-btn enfont" href="javascript:history.go(-1)">返回<br/>Back</a>
        
		</form>
    </section>
</body>
<script src="${pageContext.request.contextPath}/js/pikaday-package.min.js"></script>
<script>
	var dateObject = pikadayResponsive(document.getElementById("date"),{
		outputFormat: "x",
		placeholder:"1900-01-01",	
	});
	$(document).ready(function(){
		$('#birthday').on("click",function(evt){
			if($(this).attr('readonly')=='readonly'){
				alert('修改生日，仅限线下凭有效证件更新。');
			}
			
		});
		$('#profleForm').submit(function(){
            var firstName = $('#firstNameInput').val();
			var lastName = $('#lastNameInput').val();
            var mail = $('#emailInput').val();
			var gender = $('#sexSelect').val();
            
			if(firstName == '' || lastName == ''){
				alert('请输入姓名');
				return false;
			}
			if(mail == '' || !(/^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/.test(mail))){	
				alert('请输入正确格式的邮箱');
				return false;
			}
			
			if(gender == '' || gender == undefined){
				alert('请选择性别');
				return false;
			}
        })

	})
</script>
</html>