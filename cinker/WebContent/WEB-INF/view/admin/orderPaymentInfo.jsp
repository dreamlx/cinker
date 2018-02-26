<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" autoFlush="false" buffer="300kb"%>
<%@ include file="temp.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="${pageContext.request.contextPath}/js/cinker/filmInfo.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>

<title>Order支付信息管理</title>
</head>
<body>
	<div class="main-container container-fluid">
		<!-- Page Container -->
		<div class="page-container">

			<jsp:include page="menu.jsp"></jsp:include>
			<div class="page-content">

				<div class="widget-header">
					<span class="widget-caption"><strong>Order支付信息</strong> </span>
				</div>
				
				<div class="page-body">
					<div class="col-lg-20 col-md-20 col-sm-20 col-xs-20">
						<div class="well">
							<div class="form-horizontal form-bordered">
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">支付用户</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly"
											id="userNickName" name="userNickName"
											value="${payment.userNickName}" />
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">支付ID</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly"
											id="paymentID" name="paymentID"
											value="${payment.paymentID}" />
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">支付类型</label>
									<div class="col-sm-10">
										<c:choose>
											<c:when test="${payment.type==1}">
												<input type="text" class="form-control" readonly="readonly"
													id="type" name="type" value="微信支付" />
											</c:when>
											<c:when test="${payment.type==2}">
												<input type="text" class="form-control" readonly="readonly"
													id="type" name="type" value="会员卡支付" />
											</c:when>
										</c:choose>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">支付状态</label>

									<div class="col-sm-10">
										<c:choose>
											<c:when test="${payment.status==0}">
												<input type="text" class="form-control" readonly="readonly"
													id="status" name="status" value="未支付" />
											</c:when>
											<c:when test="${payment.status==1}">
												<input type="text" class="form-control" readonly="readonly"
													id="status" name="status" value="支付成功" />
											</c:when>
											<c:when test="${payment.status==2}">
												<input type="text" class="form-control" readonly="readonly"
													id="status" name="status" value="支付失败" />
											</c:when>
										</c:choose>
									</div>

								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">支付金额</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly"
											id="paymentPrice" name="paymentPrice"
											value="${payment.paymentPrice}" />
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">支付生成时间:</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly"
											id="startTime" name="startTime" value="${payment.startTime}" />
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">支付完成时间</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly"
											id="endTime" name="endTime" value="${payment.endTime}" />
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">支付对应的订单号</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly"
											id="orderNumber" name="orderNumber"
											value="${payment.orderNumber}"></input>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<a class="btn btn-azure"
											href="javascript:history.go(-1)">
											返回 </a>
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