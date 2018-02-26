<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ include file="temp.jsp"%>
<title>下载对帐单</title>
   
</head>
<body>

	<form action="${pageContext.request.contextPath}/cinkerMaintain/getStatements?fromDate=${fromDate}" method="get" name="form1" onkeydown="if(event.keyCode==13){return false;}">
	
	<div class="main-container container-fluid">		
		<div class="page-container">
			<jsp:include page="menu.jsp" flush="true"/>
			<div class="page-content">                
                <div class="page-body">
                	<div class="well">
						<div class="row">
						
							<div class="col-lg-24">
								<div class="form-group nav-bar col-lg-20 col-md-20 no-margin-bottom">
							    	<div class=" col-lg-8 col-md-6 no-padding  ">
										<div class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">对账单日期</div>
							    		<div class="col-lg-12 input-group">
											<input value="${fromDate}" type="text" class="form-control" placeholder="请输入对账单日期(8位数字)" name="fromDate">
										</div>
									</div>
								</div>
							</div>
						
							<div class="col-lg-10">
								<input class="btn btn-blue pull-right margin-left-10  margin-top-10" name="submit1" type="submit" value="导出Excel">
							</div>
							
						</div>
					</div>
                     
                </div>
			</div>			
        </div>
	</div>
	</form>
	<c:choose>
		<c:when test="${status == '1'}">
			<p>该日期没有对帐单</p>
		</c:when>
	</c:choose>
</body>
</html>