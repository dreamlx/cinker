<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/common.jsp"%> 
<!DOCTYPE html>
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>500 服务器内部错误</title>  
</head>  
<body>  
 <div class="ui-alert-panel">  
        <h1 id='error_title'>对不起，系统出错</h1>  
        <div id='error' style="display:none">
        	<% Exception ex = (Exception)request.getAttribute("exception"); %>
    		<H2>Exception: <%= ex.getMessage()%></H2>
    		<P/>
    		<% ex.printStackTrace(new java.io.PrintWriter(out)); %>
        </div>  
    </div>  

</body> 
<script type="text/javascript">
    $(document).ready(function(){
    	$("#error_title").click(function(){
    		$("#error").toggle();
    	})
    });
	
</script> 
</html> 

