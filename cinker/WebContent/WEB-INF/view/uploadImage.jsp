<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="${pageContext.request.contextPath}/cinkerMaintain/filmUploadImage" enctype="multipart/form-data" method="post">
		<input class="filePrew" tabIndex="3" type="file" size="3" name="graphTheories" multiple/>
		<input type="submit" value="上传">
	</form>

</body>
</html>