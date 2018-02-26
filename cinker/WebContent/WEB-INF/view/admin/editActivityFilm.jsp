<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="temp.jsp"%>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/skins.min.js"></script>
<script src="${pageContext.request.contextPath}/js/beyond.min.js"></script>

<script src="${pageContext.request.contextPath}/js/WdatePicker.js"></script>
<script src="${pageContext.request.contextPath}/js/cinker/activity.js?ver=171020"></script>
<title>活动管理 - 编辑活动</title>
</head>
<body>
	<div class="main-container container-fluid">
		<!-- Page Container -->
		<div class="page-container">
			
			<jsp:include page="menu.jsp"></jsp:include>
			<div class="page-content">

				<div class="widget-header">                 
                   <span class="widget-caption"><strong>影院信息管理</strong></span>
                </div>

				<div class="page-body">
					<div class="col-lg-20 col-md-20 col-sm-20 col-xs-20">
						<div class="well">
							<div class="form-horizontal form-bordered">
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">活动场次ID</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "sessionId" name="sessionId" value="${activityFilm.sessionId}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影片名称</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "filmTitle" name="filmTitle" value="${activityFilm.filmTitle}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right padding-bottom-9">影讯ID</label>

									<input type="hidden" class="form-control" id = "id" name = "id" value="${activityFilm.id}" />
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "filmId" name = "filmId" value="${activityFilm.filmId}"/>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影院ID</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "cinemaId" name = "cinemaId" value="${activityFilm.cinemaId}"/>
									</div>
								</div>
								
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影厅名称</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "sessionName" name = "sessionName" value="${activityFilm.sessionName}"/>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影片场次时间</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" id = "sessionTime" name = "sessionTime" value="${activityFilm.sessionTime}"/>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">开始置顶时间</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" id = "startSessionTime" name = "startSessionTime" value="${activityFilm.startSessionTime}"/>
									</div>
								</div>
								
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">活动影片类型</label>
									<div class="col-sm-10">
										<select tabindex="3" class="form-control" id = "activityFilmType" name="activityFilmType" >
												
												<option value="Pictures">Pictures</option>
												<option value="Screening">Screening</option>	
										</select>
										
										<script type="text/javascript">
													for(var i=0;i<document.getElementById('activityFilmType').options.length;i++){ 	
														if(document.getElementById('activityFilmType').options[i].value==='${activityFilm.activityFilmType}'){														
															document.getElementById('activityFilmType').options[i].selected=true; 
														} 
													} 				
										</script>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">会员活动</label>
									<div class="col-sm-10">
										<div class="checkbox">
											<select tabindex="4" class="form-control" id="isformember" name="isForUpperMember" >
												<option value="0">无限制</option>
												<option value="1">注册会员</option>	
												<option value="2">付费会员</option>	
											</select>
											<script type="text/javascript">
													for(var i=0;i<document.getElementById('isformember').options.length;i++){ 	
														if(document.getElementById('isformember').options[i].value==='${activityFilm.isForUpperMember}'){														
															document.getElementById('isformember').options[i].selected=true; 
														} 
													} 				
											</script>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">票价</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "totalValueCents" name = "totalValueCents" value="${activityFilm.totalValueCents}"/>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">总票数</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "total" name="total" value="${activityFilm.total}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">个人最多购买票数</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "quaty" name="quaty" value="${activityFilm.quaty}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">链接</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "url" name="url" value="${activityFilm.url}" ></input>
									</div>
								</div>

								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<c:choose>
											<c:when test= "${empty activityFilm}">
												<input class="btn btn-azure" value="保 存" type="button" onclick="insertActivityFilm();">
											</c:when>
											<c:otherwise>
												<input class="btn btn-azure" value="更 新" type="button" onclick="saveActivityFilm();">
											</c:otherwise>
										</c:choose>
																																					
										<a  class="btn btn-azure" href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?page=1"> 返回 </a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
   
	
	</script>
</body>
</html>