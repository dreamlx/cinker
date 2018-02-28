<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="temp.jsp"%>
<title>订单一览</title>
</head>
<body>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/WdatePicker.js"></script>
	<form
		action="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders"
		method="post" name="form1"
		onkeydown="if(event.keyCode==13){return false;}">

		<div class="main-container container-fluid">
			<div class="page-container">
				<jsp:include page="menu.jsp" flush="true" />
				<div class="page-content">
					<div class="page-body">
						<div class="well">
							<div class="row">

								<div class="col-lg-24">
									<div
										class="form-group nav-bar col-lg-20 col-md-20 no-margin-bottom">
										<div class=" col-lg-8 col-md-6 no-padding  ">
											<div
												class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">订单编号</div>
											<div class="col-lg-12 input-group">
												<input value="${orderNumber}" type="text"
													class="form-control" placeholder="请输入订单编号"
													name="orderNumber">
											</div>
										</div>
										<div class=" col-lg-8 col-md-6 no-padding  ">
											<div
												class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">影片名称</div>
											<div class="col-lg-12 input-group">
												<input value="${filmTitle}" type="text" class="form-control"
													placeholder="请输入影片名称" name="filmTitle">
											</div>
										</div>
										<br>
										<br>
										<div class=" col-lg-8 col-md-6 no-padding  ">
											<div
												class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">订单生成时间</div>
											<div class="col-lg-12 input-group">
												<input value="${beginTime}" name="beginTime"
													class="form-control"
													onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
													type="text" placeholder="请输入开始时间">
											</div>
										</div>
										<div class=" col-lg-8 col-md-6 no-padding  ">
											<div
												class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">订单结束时间</div>
											<div class="col-lg-12 input-group">
												<input value="${endTime}" name="endTime"
													class="form-control"
													onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
													type="text" placeholder="请输入结束时间">
											</div>
										</div>
										<br>
										<br>
										<div class=" col-lg-8 col-md-6 no-padding  ">
											<div
												class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">影片开始时间</div>
											<div class="col-lg-12 input-group">
												<input value="${beginShowTime}" name="beginShowTime"
													class="form-control"
													onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
													type="text" placeholder="请输入开始时间">
											</div>
										</div>
										<div class=" col-lg-8 col-md-6 no-padding  ">
											<div
												class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">影片结束时间</div>
											<div class="col-lg-12 input-group">
												<input value="${endShowTime}" name="endShowTime"
													class="form-control"
													onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
													type="text" placeholder="请输入结束时间">
											</div>
										</div>
										<br>
										<br>
										<div class=" col-lg-8 col-md-6 no-padding  ">
											<div
												class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">VISTA交易号</div>
											<div class="col-lg-12 input-group">
												<input type="text" class="form-control"
													placeholder="请输入VISTA交易号" name="vistaTransNumber"
													value="${vistaTransNumber}">
											</div>
										</div>
										<div class=" col-lg-8 col-md-6 no-padding  ">
											<div
												class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">影院名称</div>
											<section class="sm_right">
												<select class="sl01" id="cinemaId" name="cinemaId"
													onChange="this.form.cinemaId.value=this.options[this.selectedIndex].value">
													<option value="">ALL</option>
													<c:forEach items="${cinemas}" var="cinema">
														<option value="${cinema.cinemaId}">${cinema.name}</option>
													</c:forEach>
												</select>
											</section>
										</div>
										<br />
										<br />
										<div class="col-lg-8 col-md-6 no-padding">
											<div
												class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">支付状态</div>
											<section class="sm_right">
												<select class="sl01" id="status" name="status"
													onChange="this.form.status.value=this.options[this.selectedIndex].value">
													<option value="1">已支付</option>
													<option value="0">未支付</option>
												</select>
											</section>
										</div>
										<script type="text/javascript">
											for (var i = 0; i < document
													.getElementById('status').options.length; i++) {
												if (document
														.getElementById('status').options[i].value === "${orderStatus}") {
													document
															.getElementById('status').options[i].selected = true;
												}
											}
											for (var i = 0; i < document
													.getElementById('cinemaId').options.length; i++) {
												if (document
														.getElementById('cinemaId').options[i].value === "${inCinemaId}") {
													document
															.getElementById('cinemaId').options[i].selected = true;
												}
											}
										</script>
									</div>

								</div>

								<div class="col-lg-10">
									<input
										class="btn btn-blue pull-right margin-left-10  margin-top-10"
										name="submit1" type="submit" value="导出Excel"> <input
										class="btn btn-blue pull-right margin-left-10  margin-top-10"
										name="submit2" type="submit" value="查询">
									<%-- <input name="submit2" type="hidden" value="${submit2}"> --%>
								</div>

							</div>
						</div>
						<div class="row">
							<div class="col-lg-20 col-sm-20 col-xs-20">

								<div class="widget-body" style="display: block;">
									<table class="table table-bordered table-striped table-hover">
										<colgroup class="row">
											<col class="col-xs-3">
											<col class="col-xs-3">
											<col class="col-xs-3">
											<col class="col-xs-3">
											<col class="col-xs-3">
											<col class="col-xs-3">
											<col class="col-xs-3">
											<col class="col-xs-3">
											<col class="col-xs-2">
										</colgroup>
										<thead>
											<tr>
												<th>交易时间</th>
												<th>微信订单号</th>
												<th>影城</th>
												<th>VISTA交易号</th>
												<th>影片时间</th>
												<th>影片名称</th>
												<th>金额/张</th>
												<th>电话</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${orderList}" var="orderList">
												<tr>
													<td>${orderList.endTime}</td>
													<td>${orderList.orderNumber}</td>
													<td>${orderList.cinemaName}</td>
													<td>${orderList.vistaTransNumber}</td>
													<td>${orderList.showTime}</td>
													<td>${orderList.filmTitle}</td>
													<td>${orderList.unitPrice}</td>
													<td>${orderList.mobilePhone}</td>
													<td><a class="btn btn-azure"
														href="${pageContext.request.contextPath}/cinkerMaintain/findFilmOrders/${orderList.id}">&nbsp;
															查看 &nbsp;</a> <c:choose>
															<c:when test="${orderList.status==1}">
																<a class="btn btn-azure"
																	href="${pageContext.request.contextPath}/cinkerMaintain/cancleFilmOrder/${orderList.id}"
																	onclick='return confirm( "确定要退票吗? ")'>&nbsp; 退款
																	&nbsp;</a>
															</c:when>
														</c:choose></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<div align="center">
										<font size="3">共 ${pages.totalPage} 页</font>
										<c:if test="${pages.totalPage != 0}">
											<font size="3">第${pages.page}页</font>
											<a
												href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=1&cinemaId=${inCinemaId}"
												id="1">首页</a>
											<c:choose>
												<c:when test="${pages.page - 1 >0 }">
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=${pages.page-1}&cinemaId=${inCinemaId}"
														id="${pages.page-1}">上一页</a>
												</c:when>
											</c:choose>
											<c:choose>
												<c:when test="${pages.totalPage == 1}">
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=${pages.page}&cinemaId=${inCinemaId}"
														id="1">${pages.page}</a>
												</c:when>
												<c:when test="${pages.totalPage == 2}">
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=1&cinemaId=${inCinemaId}"
														id="1">1</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=2&cinemaId=${inCinemaId}"
														id="2">2</a>
												</c:when>
												<c:when test="${pages.totalPage == 3}">
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=1&cinemaId=${inCinemaId}"
														id="1">1</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=2&cinemaId=${inCinemaId}"
														id="2">2</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=3&cinemaId=${inCinemaId}"
														id="3">3</a>
												</c:when>
												<c:when test="${pages.page < 3}">
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=${pages.page}&cinemaId=${inCinemaId}"
														id="${pages.page}">${pages.page}</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=${pages.page+1}&cinemaId=${inCinemaId}"
														id="${pages.page+1}">${pages.page+1}</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=${pages.page+2}&cinemaId=${inCinemaId}"
														id="${pages.page+2}">${pages.page+2}</a>
												</c:when>
												<c:when test="${pages.page >= pages.totalPage}">
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=${pages.page-2}&cinemaId=${inCinemaId}"
														id="${pages.page-2}">${pages.page-2}</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=${pages.page-1}&cinemaId=${inCinemaId}"
														id="${pages.page-1}">${pages.page-1}</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=${pages.page}&cinemaId=${inCinemaId}"
														id="${pages.page}">${pages.page}</a>
												</c:when>
												<c:otherwise>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=${pages.page-1}&cinemaId=${inCinemaId}"
														id="${pages.page-1}">${pages.page-1}</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=${pages.page}&cinemaId=${inCinemaId}"
														id="${pages.page}">${pages.page}</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=${pages.page+1}&cinemaId=${inCinemaId}"
														id="${pages.page+1}">${pages.page+1}</a>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${pages.page+1 <= pages.totalPage}">
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=${pages.page+1}&cinemaId=${inCinemaId}"
														id="${pages.page+1}">下一页</a>
												</c:when>
											</c:choose>
											<c:choose>
												<c:when test="${pages.totalPage == 0}">
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=${pages.page}&cinemaId=${inCinemaId}"
														id="${pages.page}">尾页</a>
												</c:when>
												<c:otherwise>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?orderNumber=${orderNumber}&filmTitle=${filmTitle}&beginTime=${beginTime}&endTime=${endTime}&beginShowTime=${beginShowTime}&endShowTime=${endShowTime}&vistaTransNumber=${vistaTransNumber}&status=${orderStatus}&submit2=${submit2 }&page=${pages.totalPage}&cinemaId=${inCinemaId}"
														id="${pages.totalPage}">尾页</a>
												</c:otherwise>
											</c:choose>
										</c:if>
										<br>输入:
										<c:choose>
											<c:when test="${submit2 == '查询' }">
												<input name="pagee" id="pagee" type="text">
												<input name="page" id="page" type="hidden" value="1">
											</c:when>
											<c:otherwise>
												<input name="pagee" id="pagee" type="text">
												<input name="page" id="page" type="hidden" value="1">
											</c:otherwise>
										</c:choose>
										<input value="跳转" type="submit">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>