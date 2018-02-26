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
<title>会员充值Profile</title>
<link href="${pageContext.request.contextPath}/css/pikaday-package.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/modernizr.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script src="${pageContext.request.contextPath}/js/cinker/profile.js"></script>
</head>
<body class="bg-2">
    <section class="container">
		<form id="profleForm" action="${pageContext.request.contextPath}/usermember/profile">
			<input name="userSessionId" id="userSessionId" type="hidden" value="${userSessionId}">
			<input name="clubId" id="clubId" type="hidden" value="${clubId}">
			<input name="memberId" id="memberId" type="hidden" value="${memberId}">
			
    		<section class="register-main">
 			<div class="reg-input">
				<div class="ri_01">手机号码*</div>
 				<div class="ri_02"><input maxlength="11" type="text" class="reg-input01" value='${userMember.phone}'></div>
			</div>
			<div class="reg-txt">Phone #</div>
			
			<div class="reg-input">
				<div class="ri_01">姓<span>*</span></div>
				<div class="ri_03"><input maxlength="20" type="text" name="firstName" id='firstNameInput' class="reg-input01" value="${userMember.firstName}"></div>
				<div class="ri_01" style="text-align:center">名<span>*</span></div>
				<div class="ri_03"><input maxlength="20" type="text" name="lastName" id='lastNameInput' class="reg-input01" value="${userMember.lastName}"></div>
			</div>
			<div class="reg-txt enfont"><span class="cls-01">Last Name</span><span class="cls-02">First Name</span></div>
			  
			<div class="reg-input">
				<div class="ri_01">邮箱<span>*</span></div>
				<div class="ri_02"><input type="text" id="emailInput" name='email' class="reg-input01" value="${userMember.email}"></div>
			</div>
			<div class="reg-txt">E-mail</div>

			<div class="reg-input">
				<div class="ri_01">生日<span>*</span></div>
				<div class="ri_03"><input type="date" name="birthday" id="birthday" class="reg-input01" value='${userMember.birthday}' placeholder="${userMember.birthday}"></div>
				<div class="ri_01" style="text-align:center">性别<span>*</span></div>
				<div class="ri_03">
				<c:choose>
					<c:when test="${userMember.userSex == 1}">
						<input type="radio" name="sex"  id="sex" value="1" checked/> 男 &nbsp; 
						<input type="radio" name="sex"  id="sex" value="2"/> 女
					</c:when>
					<c:when test="${userMember.userSex == 2}">
						<input type="radio" name="sex"  id="sex" value="1" /> 男 &nbsp; 
						<input type="radio" name="sex"  id="sex" value="2" checked/> 女
					</c:when>
				</c:choose>
				</div>
			</div>
			<div class="reg-txt enfont"><span class="cls-01">Date of Birth</span><span class="cls-02">Gender</span></div>
        </section>
        <button type='submit' class="red-btn enfont">修改个人信息<br/>Save
        </button>
        <a class="red-btn enfont" href="javascript:history.go(-1)">返回<br/>Back</a>
        <div class="clear10px"></div>
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
		$('#profleForm').submit(function(){
            var firstName = $('#firstNameInput').val();
			var lastName = $('#lastNameInput').val();
            var mail = $('#emailInput').val();
			var birth = $('#date').val();
			var gender = $('input:radio[name=sex]:checked').val();
            
			if(firstName == '' || lastName == ''){
				alert('请输入姓名');
				return false;
			}
			if(mail == '' || !(/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(mail))){
				alert('请输入正确格式的邮箱');
				return false;
			}
			
			if(birth ==''){
				alert('请选择生日');
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