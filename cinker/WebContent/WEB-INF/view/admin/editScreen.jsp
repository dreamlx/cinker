<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="temp.jsp"%>

	<script src="${pageContext.request.contextPath}/js/cinker/screenInfo.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>

	<title>影厅管理</title>
</head>
<body>
	<div class="main-container container-fluid">
		<!-- Page Container -->
		<div class="page-container">
			
			<jsp:include page="menu.jsp"></jsp:include>
			<div class="page-content">

				<div class="widget-header">                 
                   <span class="widget-caption"><strong>影厅信息Edit</strong></span>
                </div>

				<div class="page-body">
					<div class="col-lg-20 col-md-20 col-sm-20 col-xs-20">
						<div class="well">
							<div class="form-horizontal form-bordered">
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right padding-bottom-9">影厅名称</label>

									<input type="hidden" class="form-control" id = "id" name = "id" value="${screen.id}" />
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "screenName" name = "screenName" value="${screen.screenName}"/>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影厅号码</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "screenNumber" name = "screenNumber" value="${screen.screenNumber}"/>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影院ID</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "cinemaId" name="cinemaId" value="${screen.cinemaId}" ></input>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<c:choose>
											<c:when test= "${empty screen}">
												<input class="btn btn-azure" value="保 存" type="button" onclick="insertScreen();">
											</c:when>
											<c:otherwise>
												<input class="btn btn-azure" value="更 新" type="button" onclick="saveScreen();">
											</c:otherwise>
										</c:choose>
																																					
										<a  class="btn btn-azure" href="${pageContext.request.contextPath}/cinkerMaintain/getScreenList"> 返回 </a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>