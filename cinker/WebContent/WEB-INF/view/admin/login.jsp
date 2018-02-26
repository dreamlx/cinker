<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ include file="temp.jsp"%>

	<title>登录页面</title>
</head>
<body>
<form name="userForm" action="${pageContext.request.contextPath}/user/login" method="post">	
		<div class="login-container animated fadeInDown">
			<div class="loginbox bg-white">
				<div class="loginbox-title">三克影院登陆系统</div>
				<div class="loginbox-social">
					<div class="social-title ">请登录您的账号!</div>
				</div>
				<div class="loginbox-textbox">
					<h5>账号：</h5>
					<input type="text" class="form-control" name="username" />
				</div>
				<div class="loginbox-textbox">
					<h5>密码：</h5>
					<input type="password" class="form-control" name="password" />
				</div>
				<div class="loginbox-forgot loginbox-textbox"></div>
				<div class="loginbox-submit">
					<input type="submit" class="btn btn-azure btn-block" value="登 录"/>
				</div>
			</div>
		</div>
</form>
</body>
</html>