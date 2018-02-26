x<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"  autoFlush="false"  buffer="300kb"%>
<%@ include file="temp.jsp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="${pageContext.request.contextPath}/js/cinker/orderInfo.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>

<title>订单一览详情页</title>
</head>
<body>
	<div class="main-container container-fluid">
		<!-- Page Container -->
		<div class="page-container">
			
			<jsp:include page="menu.jsp"></jsp:include>
			<div class="page-content">

				<div class="widget-header">                 
                   <span class="widget-caption"><strong>订单一览</strong></span>
                </div>

				<div class="page-body">
					<div class="col-lg-20 col-md-20 col-sm-20 col-xs-20">
						<div class="well">
							<div class="form-horizontal form-bordered">
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影片内部编码</label>
										
									<input type="hidden" class="form-control" id = "id" name = "id" value="${filmOrders.id}" />
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "scheduledFilmId" name = "scheduledFilmId" value="${filmOrders.scheduledFilmId}"/>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">微信名称</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "userNickName" name = "scheduledFilmId" value="${filmOrders.userNickName}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right padding-bottom-9">订单编号</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "orderNumber" name = "orderNumber" value="${filmOrders.orderNumber}"/>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影片名称</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "filmTitle" name = "filmTitle" value="${filmOrders.filmTitle}"/>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影城内部编码</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "cinemaId" name="cinemaId" value="${filmOrders.cinemaId}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影院名称</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "cinemaName" name="cinemaName" value="${filmOrders.cinemaName}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影片场次ID</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "sessionId" name="sessionId" value="${filmOrders.sessionId}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">场次名称</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "sessionName" name="sessionName" value="${filmOrders.sessionName}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">播放时间</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "showTime" name="showTime" value="${filmOrders.showTime}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">区域类别编码</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "areaCategoryCode" name="areaCategoryCode" value="${filmOrders.areaCategoryCode}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">订单数量</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "totalOrderCount" name="totalOrderCount" value="${filmOrders.totalOrderCount}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">订单购买总价</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "totalValueCents" name="totalValueCents" value="${filmOrders.totalValueCents}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right padding-bottom-9">订单状态</label>
									<div class="col-sm-10">
									<c:choose>
										<c:when test="${filmOrders.status==0}">
											<input type="text" class="form-control" readonly="readonly" id = "status" name="status" value="未支付" />
										</c:when>
										<c:when test="${filmOrders.status==1}">
											<input type="text" class="form-control" readonly="readonly" id = "status" name="status" value="支付成功" />
										</c:when>
										<c:when test="${filmOrders.status==2}">
											<input type="text" class="form-control" readonly="readonly" id = "status" name="status" value="支付失败" />
										</c:when>
									</c:choose>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">订单生成时间</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "startTime" name="startTime"  value="${filmOrders.startTime}"></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">订单完成时间</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "endTime" name="endTime" value="${filmOrders.endTime}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">会员号</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "userNumber" name="userNumber" value="${filmOrders.userNumber}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">支付编号</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "paymentID" name="paymentID" value="${filmOrders.paymentID}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">取票码</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "bookingNumber" name="bookingNumber" value="${filmOrders.bookingNumber}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">验证码</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "bookingID" name="bookingID" value="${filmOrders.bookingID}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">座位</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" readonly="readonly" id = "seats" name="seats" value="${filmOrders.seats}" ></input>
									</div>
								</div>

								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">																																				
										<a  class="btn btn-azure" href="javascript:history.go(-1)"> 返回 </a>
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