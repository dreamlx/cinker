<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/common.jsp" %>
<%@ page import="com.cinker.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>会员充值</title>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
 
                                    <table class="table table-bordered table-striped table-hover">
	                                	<colgroup class="row">
	                                		<col class="col-xs-3">
											<col class="col-xs-3">
										</colgroup>
	                                    <thead>
	                                        <tr>                                       	
	                                            <th>sessionTime</th>
	                                            <th>transactionDate</th>
	                                        </tr>
	                                    </thead>	                                    
	                                    <tbody>
	                                    	<c:forEach items="${memberTranslist}" var="memberTranslist">
	                                        <tr>	                                        	
	                                            <td>${memberTranslist.sessionTime}</td>
	                                            <td>${memberTranslist.transactionDate}</td>
	                                        </tr>
	                                       </c:forEach>                          
	                                    </tbody>                                
	                                </table>	
</body>

</html>