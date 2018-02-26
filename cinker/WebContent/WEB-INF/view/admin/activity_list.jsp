<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ include file="temp.jsp"%>

<title>活动管理</title>

</head>
<body>
<form action="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=${page}" method="get">
	<div class="main-container container-fluid">
		<!-- Page Container -->
		<div class="page-container">					
			<jsp:include page="menu.jsp" flush="true"/>
			<div class="page-content">
                <div class="page-body">
                <div class="well">
						<div class="row">
							<div class="col-lg-24">
								<div class="form-group nav-bar col-lg-20 col-md-20 no-margin-bottom">
							    	<div class=" col-lg-6 col-md-6 no-padding  ">
										<div class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">影片编码</div>
							    		<div class="col-lg-12 input-group">
											<input value="${filmId}" type="text" class="form-control" placeholder="请输入影片编码" name="filmId">
										</div>
									</div>
									<div class=" col-lg-6 col-md-6 no-padding  ">
										<div class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">影片名</div>
							    		<div class="col-lg-12 input-group">
											<input value="${filmTitle}" type="text" class="form-control" placeholder="请输入影片名" name="filmTitle">
										</div>
									</div>
								</div>
							</div>
							<div class="col-lg-10">
								<input class="btn btn-blue pull-right margin-left-10  margin-top-10" name="submit1" type="submit" value="查询">
								<a class="btn pull-right margin-left-10  margin-top-10" href="${pageContext.request.contextPath}/cinkerMaintain/editActivityFilm/0">新增</a>
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
											<col class="col-xs-2">
											<col class="col-xs-2">
											<col class="col-xs-2">
											<col class="col-xs-3">
											<col class="col-xs-2">
											<col class="col-xs-2">
											<col class="col-xs-2"> 
											<col class="col-xs-1">
											<col class="col-xs-3">
										</colgroup>
	                                    <thead>
	                                        <tr>	
	                                        	<th>活动场次ID</th>                                        	
	                                            <th>影讯ID</th>
	                                            <th>影院ID</th>
	                                            <th>活动开场时间</th>
	                                            <th>电影名称</th>
	                                            <th>票价</th>
	                                            <th>购买数量</th>
	                                            <th>剩余票数</th>
												<th>会员</th>
	                                            <th>操作</th>
	                                        </tr>
	                                    </thead>	                                    
	                                    <tbody>
	                                    	<c:forEach items="${activityFilm}" var="activityFilm">
	                                        <tr>	     
	                                        	<td><div class="over-text">${activityFilm.sessionId}</div></td>                                   	
	                                            <td>${activityFilm.filmId}</td>
	                                            <td>${activityFilm.cinemaId}</td>
	                                            
	                                            <td>${activityFilm.sessionTime}</td>
	                                            <td>${activityFilm.filmTitle}</td>
	                                            <td>${activityFilm.totalValueCents}</td>
	                                            <td>${activityFilm.quaty}</td>
	                                            <td>${activityFilm.activityTickets}</td>
	                                            <td>
	                                            	<c:if test="${activityFilm.isForUpperMember > 0}">
   													是
													</c:if>
	                                            </td>
	                                            <td>
	                                            	<a class="btn btn-azure"  href="${pageContext.request.contextPath}/cinkerMaintain/editActivityFilm/${activityFilm.id}">&nbsp; 编辑 &nbsp;</a>
                                    				<a class="btn btn-azure"  href="${pageContext.request.contextPath}/cinkerMaintain/deleteActivityFilm/${activityFilm.id}" onclick='return confirm( "确定要删除吗? ")'>&nbsp; 删除 &nbsp;</a>
                                    			</td>	                                            
	                                        </tr>
	                                       </c:forEach> 	                                   
	                                    </tbody>	                                  
	                                </table>
	                                <div align="center">
										<font size="3">共 ${pages.totalPage} 页</font>
										<c:if test="${pages.totalPage != 0}">
										<font size="3">第${pages.page}页</font> 
	                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=1" id="1">首页</a>
	                                	<c:choose>
	                                		<c:when test="${pages.page - 1 >0 }">
	                                			<a href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=${pages.page-1}" id="${pages.page-1}">上一页</a>
	                                		</c:when>
	                                	</c:choose>
	  
	                                	<c:choose>
	                                		<c:when test="${pages.totalPage == 1}">
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=${pages.page}" id="1">${pages.page}</a>
		                                	</c:when>
		                                	<c:when test="${pages.totalPage == 2}">
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=1" id="1">1</a>
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=2" id="2">2</a>
		                                	</c:when>
		                                	<c:when test="${pages.totalPage == 3}">
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearch?page=1" id="1">1</a>
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearch?page=2" id="2">2</a>
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearch?page=3" id="3">3</a>
		                                	</c:when>
		                                	<c:when test="${pages.page < 3}">
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=${pages.page}" id="${pages.page}">${pages.page}</a>
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=${pages.page+1}" id="${pages.page+1}">${pages.page+1}</a>
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=${pages.page+2}" id="${pages.page+2}">${pages.page+2}</a>
		                                	</c:when>
		                                	<c:when test="${pages.page >= pages.totalPage}">
			                                	<a href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=${pages.page-2}" id="${pages.page-2}">${pages.page-2}</a>
			                                	<a href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=${pages.page-1}" id="${pages.page-1}">${pages.page-1}</a>
			                                	<a href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=${pages.page}" id="${pages.page}">${pages.page}</a>
		                                	</c:when>
		                                	<c:otherwise>
			                                	<a href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=${pages.page-1}" id="${pages.page-1}">${pages.page-1}</a>
			                                	<a href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=${pages.page}" id="${pages.page}">${pages.page}</a>
			                                	<a href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=${pages.page+1}" id="${pages.page+1}">${pages.page+1}</a>
		                                	</c:otherwise>
	                                	</c:choose>
	                                	<c:choose>
	                                		<c:when test="${pages.page+1 <= pages.totalPage}">
	                                			<a href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=${pages.page+1}" id="${pages.page+1}">下一页</a>
	                                		</c:when>
	                                	</c:choose>
	                                	<c:choose>
	                                		<c:when test="${pages.totalPage == 0}">
	                                			<a href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=${pages.page}" id="${pages.page}">尾页</a>
			                            	</c:when>
			                            	<c:otherwise>
			                            		<a href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?filmId=${filmId}&filmTitle=${filmTitle}&submit1=${submit1}&page=${pages.totalPage}" id="${pages.totalPage}">尾页</a>
			                            	</c:otherwise>
			                            </c:choose> 
			                            </c:if>
			                            <br>输入:
			                            <c:choose>
			                            	<c:when test="${submit1 == '查询' }">
			                            		<input name="pagee" id="pagee" type="text">
		                                		<input name="page" id="page" type="hidden" value="1">	
	                                		</c:when>
	                                		<c:otherwise>
	                                			<input name="pagee" id="pagee" type="text">
	                                			<input name="page" id="page" type="hidden" value="1">
	                                		</c:otherwise>
	                                	</c:choose>
	                                		<input  value="跳转" type="submit">                         
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