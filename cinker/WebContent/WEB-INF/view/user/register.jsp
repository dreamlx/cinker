<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/common.jsp" %>
<link href="${pageContext.request.contextPath}/css/pikaday-package.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/modernizr.js"></script>
<script src="${pageContext.request.contextPath}/js/cinker/util.js"></script>
<title>注册Registration</title>
</head>
<body class="bg-2">    
    <section class="container">
		<div class='page-logo'>
			<img src='${pageContext.request.contextPath}/images/logo2.png'>
		</div>
        <form id="regForm" action="${pageContext.request.contextPath}/wechat/login/register">
		<input type="hidden" name="username" class="reg-input01" placeholder=>
		<input type="hidden" name="password" class="reg-input01" value='123456' >
		<input type="hidden" value="Cinker" name="firstName" class="reg-input01" placeholder="姓氏" >
		<input type="hidden" value="Member" name="lastName" class="reg-input01" placeholder="名字" >
		<input type="hidden" name="email"  class="reg-input01" >
		<!-- <input type="text" name="date" id="date" class="reg-input01" placeholder="1900-01-01"> -->
    	<section class="register-main">
        	
            
        	<div class="reg-input">
            	<div class="ri_01">
				<div class="reg-field">手机<span>*</span></div>
				<div class="reg-txt enfont">Phone #</div>
			</div>
            	<div class="ri_02">
					<input type="text" id="phone" name="phone" class="reg-input02" placeholder="请输入您手机号码" >
					<span class='getPhoneVaildBtn' onclick="getRandomNumber();">获取验证码</span>
            		<span class='waitPhoneVaild'><i></i>秒后重试</span>
				</div>
            </div>
            
            <div class="reg-input">
            		<div class="ri_01">
					<div class="reg-field">验证码<span>*</span></div>
					<div class="reg-txt enfont">Code</div>
				</div>
            		<div class="ri_02"><input type="text" id="randomNumber" name="randomNumber" class="reg-input02" placeholder="请输入验证码" ></div>
            </div>	
			
			<div class="city-select-field">
            		<div class="">城市</div>
            		<div class="enfont">CITY</div>
            </div>	
			
			<div class="city-select">
					<select tabindex="2" class="" id="areaNumber" name="areaNumber" >
						<option value="0100">北京</option>
						<option value="0210">上海</option>	
					</select>
				</div>
            
			</div>
			<br/><br/>
			<button class="sub-btn enfont">
        		提交 <br>
        		REGISTER
        	</button>
     		<div class="sk_agree">
     			<p>
     				点击提交，即代表您认可三克会员协议
     			</p>
				<p class='enfont'>By Clicking Register ,I agree to your Term</p>
			</div>
            
            <div class="clear25px"></div>
        </section>
        
        <div class="clear10px"></div>
        </form>
    </section>
</body>
<script>

$(document).ready(function(){
	//$('#date-input').attr('disable','true')
	//$('#date-input').on('click',function(){
		//$('body,html').scrollTop(170);
	//})
	$('form#regForm').bind('submit', function(){
		var phoneValue = $('#regForm input[name=phone]').val();
		if(phoneValue==''){
            alert('请输入正确手机号');
            return false;
        }
        if(!(/^1(3|4|5|7|8)\d{9}$/.test(phoneValue))){
            alert("手机号码有误，请重填"); 
            return false;
        }
		$('#regForm input[name=username]').val(phoneValue);
		$('#regForm input[name=email]').val(phoneValue+'@cinker.cn');
	})
})
</script>
</html>
