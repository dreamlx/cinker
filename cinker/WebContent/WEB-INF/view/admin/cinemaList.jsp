<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ include file="temp.jsp"%>
<title>影院管理</title>

</head>
<body>
	<div class="main-container container-fluid">
		<!-- Page Container -->
		<div class="page-container">					
			<jsp:include page="menu.jsp" flush="true"/>
			<div class="page-content">
                <div class="page-body">
                <div class="well">
						<div class="row">
							<div class="col-lg-10">
								<a class="btn pull-right margin-left-10  margin-top-10" href="${pageContext.request.contextPath}/cinkerMaintain/editCinema/0">新增</a>
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
											<col class="col-xs-4">
											<col class="col-xs-3">
											<col class="col-xs-4">
											<col class="col-xs-3">
										</colgroup>
	                                    <thead>
	                                        <tr>	                                        	
	                                            <th>影城ID</th>
	                                            <th>影院名称</th>
	                                            <th>影城地址</th>
	                                            <th>影院所属城市</th>
	                                            <th>类型</th>
	                                            <th>操作</th>
	                                        </tr>
	                                    </thead>	                                    
	                                    <tbody>
	                                    	<c:forEach items="${cinemalist}" var="cinema">
	                                        <tr>	                                        	
	                                            <td>${cinema.cinemaId}</td>
	                                            <td>${cinema.name}</td>
	                                            <td><div class="over-text">${cinema.address}</div></td>
	                                            <td>${cinema.city}</td>
	                                            <td>${cinema.type}</td>
	                                            <td>
	                                            	<a class="btn btn-azure"  href="${pageContext.request.contextPath}/cinkerMaintain/editCinema/${cinema.id}">&nbsp; 编辑 &nbsp;</a>
                                    				<a class="btn btn-azure"  href="${pageContext.request.contextPath}/cinkerMaintain/deleteCinema/${cinema.id}" onclick='return confirm( "确定要删除吗? ")'>&nbsp; 删除 &nbsp;</a>
                                    			</td>	                                            
	                                        </tr>
	                                       </c:forEach> 	                                   
	                                    </tbody>	                                  
	                                </table>									
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