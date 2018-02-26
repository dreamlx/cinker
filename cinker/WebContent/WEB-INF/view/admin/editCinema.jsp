<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="temp.jsp"%>
	<title>影院管理</title>
	<script src="${pageContext.request.contextPath}/js/cinker/cinemaInfo.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
</head>
<body>
	<div class="main-container container-fluid">
		<!-- Page Container -->
		<div class="page-container">
			
			<jsp:include page="menu.jsp"></jsp:include>
			<div class="page-content">

				<div class="widget-header">                 
                   <span class="widget-caption"><strong>影院信息Edit</strong></span>
                </div>

				<div class="page-body">
					<div class="col-lg-20 col-md-20 col-sm-20 col-xs-20">
						<div class="well">
							<div class="form-horizontal form-bordered">
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right padding-bottom-9">影院ID</label>

									<input type="hidden" class="form-control" id = "id" name = "id" value="${cinema.id}" />
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "cinemaId" name = "cinemaId" value="${cinema.cinemaId}"/>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影院名称</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "name" name = "name" value="${cinema.name}"/>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影院地址</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "address" name="address" value="${cinema.address}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影院电话</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "tel" name="tel" value="${cinema.tel}" ></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影院所属城市</label>
									<div class="col-sm-10">
										<select   tabindex="3" class="form-control" id = "city" name="city" >
												<option value="北京">北京</option>
												<option value="上海">上海</option>	
										</select>
										<script type="text/javascript">
													for(var i=0;i<document.getElementById('city').options.length;i++){ 	
														if(document.getElementById('city').options[i].value==='${cinema.city}'){														
															document.getElementById('city').options[i].selected=true; 
														} 
													} 				
									</script>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影院类型</label>
									<div class="col-sm-10">
										<select   tabindex="3" class="form-control" id = "type" name="type" >
												<option value="${cinema.type}">${cinema.type}</option>
												<option value="Pictures">Pictures</option>
												<option value="Screening">Screening</option>	
										</select>
										
										<script type="text/javascript">
													for(var i=0;i<document.getElementById('type').options.length;i++){ 	
														if(document.getElementById('type').options[i].value==='${cinema.type}'){														
															document.getElementById('type').options[i].selected=true; 
														} 
													} 				
									</script>
									</div>
								</div>

								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<c:choose>
											<c:when test= "${empty cinema}">
												<input class="btn btn-azure" value="保 存" type="button" onclick="insertCinema();">
											</c:when>
											<c:otherwise>
												<input class="btn btn-azure" value="更 新" type="button" onclick="saveCinema();">
											</c:otherwise>
										</c:choose>
																																					
										<a  class="btn btn-azure" href="${pageContext.request.contextPath}/cinkerMaintain/getCinemaList"> 返回 </a>
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