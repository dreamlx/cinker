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
<title>个人信息Profile</title>
<link href="${pageContext.request.contextPath}/css/pikaday-package.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/modernizr.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script src="${pageContext.request.contextPath}/js/cinker/profile.js"></script>
</head>
<body class="bg-2">
    <section class="container">
		<form id="profleForm" action="${pageContext.request.contextPath}/usermember/profile">
    	<section class="register-main">
			<div class="reg-input">
				<div class="ri_01">手机号码*</div>
				<div class="ri_02"><input type="text" class="reg-input01" value='${userMember.phone}' readonly="readonly" ></div>
			</div>
			<div class="reg-txt">Phone #</div>
			
			<div class="reg-input">
				<div class="ri_01">姓<span>*</span></div>
				<div class="ri_03"><input type="text" name="firstName" id='firstNameInput' class="reg-input01" placeholder="姓氏" ></div>
				<div class="ri_01" style="text-align:center">名<span>*</span></div>
				<div class="ri_03"><input type="text" name="lastName" id='lastNameInput' class="reg-input01" placeholder="名字" ></div>
			</div>
			<div class="reg-txt enfont"><span class="cls-01">Last Name</span><span class="cls-02">First Name</span></div>
			  
			<div class="reg-input">
				<div class="ri_01">邮箱<span>*</span></div>
				<div class="ri_02"><input type="text" name="email" id="emailInput" class="reg-input01" placeholder="您的电子邮箱" ></div>
			</div>
			<div class="reg-txt">E-mail</div>

			<div class="reg-input">
				<div class="ri_01">生日<span>*</span></div>
				<div class="ri_03"><input type="date" name="birthday" id="birthday" class="reg-input01" value='1900-01-01' placeholder="1900-01-01"></div>
				<div class="ri_01" style="text-align:center">性别<span>*</span></div>
				<div class="ri_03">
					<input type="radio" name="sex"  id="sex" value="1"/> 男 &nbsp; 
					<input type="radio" name="sex"  id="sex" value="2"/> 女
				</div>
			</div>
			<div class="reg-txt enfont"><span class="cls-01">Date of Birth</span><span class="cls-02">Gender</span></div>
        </section>
        <input class="red-btn" type="submit" value="保存修改">
        <a class="red-btn" href="javascript:history.go(-1)">返回</a>
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