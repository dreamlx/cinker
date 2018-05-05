<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="temp.jsp"%>
<title>活动报名管理</title>
</head>
<body>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/WdatePicker.js">
	</script>
	<form
		action="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=${page}"
		method="get" id="myform"
		onkeydown="if(event.keyCode==13){return false;}">
		<!--  <script type="text/javascript">
	var pagee = "${pagee}";
	if(pagee != null){
		var from = document.getElementById("myform");
		from.action = "${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?page=${pagee}";
	}else{
		from.action = "${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?page=${pages.page}";
	}
	</script> -->
		<div class="main-container container-fluid">
			<!-- Page Container -->
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
												class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">活动ID</div>
											<div class="col-lg-12 input-group">
												<input value="${activityId}" type="text"
													class="form-control" placeholder="请输入活动ID"
													name="activityId">
											</div>
										</div>
										<div class=" col-lg-8 col-md-6 no-padding  ">
											<div
												class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">活动时间</div>
											<div class="col-lg-12 input-group">
												<input value="${sessionTime}" name="sessionTime"
													class="form-control"
													onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" type="text"
													placeholder="请输入活动时间">
											</div>
										</div>
									</div>
								</div>
								<br /> <br />
								<div class="col-lg-24">
									<div
										class="form-group nav-bar col-lg-20 col-md-20 no-margin-bottom">
										<div class=" col-lg-8 col-md-6 no-padding  ">
											<div
												class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">姓名</div>
											<div class="col-lg-12 input-group">
												<input value="${name}" name="name"
													class="form-control" type="text"
													placeholder="请输入姓名">
											</div>
										</div>
										<div class=" col-lg-8 col-md-6 no-padding  ">
											<div
												class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">电话</div>
											<div class="col-lg-12 input-group">
												<input value="${phone}" name="phone"
													class="form-control" type="text"
													placeholder="请输入电话">
											</div>
										</div>
									</div>
								</div>
								<br /> <br />
								<div class="col-lg-24">
									<div
										class="form-group nav-bar col-lg-20 col-md-20 no-margin-bottom">
										<div class=" col-lg-8 col-md-6 no-padding  ">
											<div
												class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">影片名称</div>
											<div class="col-lg-12 input-group">
												<input value="${filmTitle}" name="filmTitle"
													class="form-control" type="text"
													placeholder="请输入影片名称">
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
									</div>
								</div>
								<script type="text/javascript">
									for (var i = 0; i < document
											.getElementById('cinemaId').options.length; i++) {
										if (document.getElementById('cinemaId').options[i].value === "${inCinemaId}") {
											document.getElementById('cinemaId').options[i].selected = true;
										}
									}
								</script>
								<div class="col-lg-10">
									<input
										class="btn btn-blue pull-right margin-left-10  margin-top-10"
										name="submit1" type="submit" value="导出Excel"> <input
										class="btn btn-blue pull-right margin-left-10  margin-top-10"
										name="submit2" type="submit" value="查询"> <input
										name="submit2" type="hidden" value="${submit2}">
								</div>

							</div>
						</div>
						<div class="row">
							<div class="col-lg-20 col-sm-20 col-xs-20">

								<div class="widget">

									<div class="widget-body" style="display: block;">
										<table class="table table-bordered table-striped table-hover">
											<colgroup class="row">
												<col class="col-xs-2">
												<col class="col-xs-3">
												<col class="col-xs-3">
												<col class="col-xs-3">
												<col class="col-xs-3">
												<col class="col-xs-2">
												<col class="col-xs-2">
											</colgroup>
											<thead>
												<tr>
													<th>姓名</th>
													<th>电话</th>
													<th>影片名称</th>
													<th>影片场次时间</th>
													<th>订单完成时间</th>
													<th>影院</th>
													<th>购票数</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${apFilmList}" var="apFilmList">
													<tr>
														<td>${apFilmList.name}</td>
														<td>${apFilmList.phone}</td>
														<td><div class="over-text">${apFilmList.filmTitle}</div></td>
														<td>${apFilmList.sessionTime}</td>
														<td>${apFilmList.dateTime}</td>
														<td>${apFilmList.cinemaName}</td>
														<td>${apFilmList.quaty}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<div align="center">
											<font size="3">共 ${pages.totalPage} 页</font> <font size="3">第${pages.page}页</font>
											<a
												href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=1"
												id="1">首页</a>
											<c:choose>
												<c:when test="${pages.page - 1 >0 }">
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=${pages.page-1}"
														id="${pages.page-1}">上一页</a>
												</c:when>
											</c:choose>

											<c:choose>
												<c:when test="${pages.totalPage == 1}">
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=${pages.page}"
														id="1">${pages.page}</a>
												</c:when>
												<c:when test="${pages.totalPage == 2}">
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=1"
														id="1">1</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=2"
														id="2">2</a>
												</c:when>
												<c:when test="${pages.totalPage == 3}">
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=1"
														id="1">1</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=2"
														id="2">2</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=3"
														id="3">3</a>
												</c:when>
												<c:when test="${pages.page < 3}">
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=${pages.page}"
														id="${pages.page}">${pages.page}</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=${pages.page+1}"
														id="${pages.page+1}">${pages.page+1}</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=${pages.page+2}"
														id="${pages.page+2}">${pages.page+2}</a>
												</c:when>
												<c:when test="${pages.page >= pages.totalPage}">
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=${pages.page-2}"
														id="${pages.page-2}">${pages.page-2}</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=${pages.page-1}"
														id="${pages.page-1}">${pages.page-1}</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=${pages.page}"
														id="${pages.page}">${pages.page}</a>
												</c:when>
												<c:otherwise>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=${pages.page-1}"
														id="${pages.page-1}">${pages.page-1}</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=${pages.page}"
														id="${pages.page}">${pages.page}</a>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=${pages.page+1}"
														id="${pages.page+1}">${pages.page+1}</a>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${pages.page+1 <= pages.totalPage}">
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=${pages.page+1}"
														id="${pages.page+1}">下一页</a>
												</c:when>
											</c:choose>
											<c:choose>
												<c:when test="${pages.totalPage == 0}">
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=${pages.page}"
														id="${pages.page}">尾页</a>
												</c:when>
												<c:otherwise>
													<a
														href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?activityId=${activityId}&sessionTime=${sessionTime}&name=${name}&phone=${phone}&filmTitle=${filmTitle}&cinemaId=${inCinemaId}&submit2=${submit2}&page=${pages.totalPage}"
														id="${pages.totalPage}">尾页</a>
												</c:otherwise>
											</c:choose>
											<br> 输入:<input name="pagee" id="pagee" type="text">
											<input value="跳转" type="submit">
										</div>


									</div>
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