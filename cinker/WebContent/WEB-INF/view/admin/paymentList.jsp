<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ include file="temp.jsp"%>
<title>订单一览</title>

</head>
<body>
<form action="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=${pagee}" method="get">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/WdatePicker.js"></script>
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
										<div class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">会员昵称</div>
							    		<div class="col-lg-12 input-group">
											<input value="${userNickName}" type="text" class="form-control" placeholder="请输入昵称" name="userNickName">
										</div>
									</div>
									<div class=" col-lg-6 col-md-6 no-padding  ">
										<div class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">支付对应的订单号</div>
							    		<div class="col-lg-12 input-group">
											<input value="${orderNumber}" type="text" class="form-control" placeholder="请输入订单号" name="orderNumber">
										</div>
									</div>
									
									<div class=" col-lg-6 col-md-6 no-padding  ">
										<div class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">支付编号</div>
							    		<div class="col-lg-12 input-group">
											<input value="${paymentID}" type="text" class="form-control" placeholder="请输入支付编号" name="paymentID">
											
										</div>
									</div>
									<br /><br />
									<div class=" col-lg-6 col-md-6 no-padding  ">
										<div class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">开始时间</div>
							    		<div class="col-lg-12 input-group">
											<input value="${beginTime}" name="beginTime" class="form-control" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" type="text" placeholder="请输入开始时间" >
										</div>
									</div>
									<div class=" col-lg-6 col-md-6 no-padding  ">
										<div class="col-lg-8 margin-top-10 text-align-right no-padding padding-right-5">结束时间</div>
							    		<div class="col-lg-12 input-group">
											<input value="${endTime}" name="endTime" class="form-control" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" type="text" placeholder="请输入结束时间" >	
										</div>
									</div>
								</div>
								</div>
							
						
							<div class="col-lg-10">
								<input class="btn btn-blue pull-right margin-left-10  margin-top-10" name="submit2" type="submit" value="导出Excel">
								<input class="btn btn-blue pull-right margin-left-10  margin-top-10" name="submit1" type="submit" value="查询">
								<input value="${submit1}" name="submit1" type="hidden" >
							</div>
							
						</div>
                	</div>
                	<div class="row">                                           
                        <div class="col-lg-20 col-sm-20 col-xs-20">
                            <div class="widget">
                                <div class="widget-body" style="display: block;">
                                    <table class="table table-bordered table-striped table-hover">
	                                	<colgroup class="row">
	                                		<col class="col-xs-3">
											<col class="col-xs-3">
											<col class="col-xs-3">
											<col class="col-xs-2">
											<col class="col-xs-3">
											<col class="col-xs-3">
											<col class="col-xs-3">
										</colgroup>
	                                    <thead>
	                                        <tr>	                                        	
	                                            <th>会员昵称</th>
	                                            <th>类型</th>
	                                            <th>订单号</th>
	                                            <th>金额</th>
	                                            <th>完成时间</th>
	                                            <th>状态</th>
	                                            <th>流水号</th>
	                                        </tr>
	                                    </thead>	                                    
	                                    <tbody>
	                                    	<c:forEach items="${paymentList}" var="paymentList">
	                                        <tr>	                                        	
	                                            <td>${paymentList.userNickName}</td>
	                                        <c:choose>
												<c:when test="${paymentList.type==1}">
													<td>微信支付</td>
												</c:when>
												<c:when test="${paymentList.type==2}">
													<td>会员卡支付</td>
												</c:when>
											</c:choose>
	                                            <td><div class="over-text">${paymentList.orderNumber}</div></td>
	                                            <td>${paymentList.paymentPrice}</td>
	                                            <td>${paymentList.endTime}</td>
	                                        <c:choose>
												<c:when test="${paymentList.status==0}">
													<td>未支付</td>
												</c:when>
												<c:when test="${paymentList.status==1}">
													<td>支付成功</td>
												</c:when>
												<c:when test="${paymentList.status==2}">
													<td>支付失败</td>
												</c:when>
											</c:choose> 
												<td>${paymentList.transactionId}</td>                                           
	                                        </tr>
	                                       </c:forEach> 	                                   
	                                    </tbody>	                                  
	                                </table>
	                                <div align="center">
										<font size="3">共 ${pages.totalPage} 页</font> 
										<font size="3">第${pages.page}页</font> 
	                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=1" id="1" >首页</a>
	                                	<c:choose>
	                                		<c:when test="${pages.page - 1 >0 }">
	                                			<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=${pages.page-1}" id="${pages.page-1}">上一页</a>
	                                		</c:when>
	                                	</c:choose>
	  
	                                	<c:choose>
	                                		<c:when test="${pages.totalPage == 1}">
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=${pages.page}" id="1">${pages.page}</a>
		                                	</c:when>
		                                	<c:when test="${pcxcccccccccccccccccccccages.totalPage == 2}">
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=1" id="1">1</a>
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=2" id="2">2</a>
		                                	</c:when>
		                                	<c:when test="${pages.totalPage == 3}">
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=1" id="1">1</a>
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=2" id="2">2</a>
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=3" id="3">3</a>
		                                	</c:when>
		                                	<c:when test="${pages.page < 3}">
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=${pages.page}" id="${pages.page}">${pages.page}</a>
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=${pages.page+1}" id="${pages.page+1}">${pages.page+1}</a>
		                                		<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=${pages.page+2}" id="${pages.page+2}">${pages.page+2}</a>
		                                	</c:when>
		                                	<c:when test="${pages.page >= pages.totalPage}">
			                                	<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=${pages.page-2}" id="${pages.page-2}">${pages.page-2}</a>
			                                	<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=${pages.page-1}" id="${pages.page-1}">${pages.page-1}</a>
			                                	<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=${pages.page}" id="${pages.page}">${pages.page}</a>
		                                	</c:when>
		                                	<c:otherwise>
			                                	<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=${pages.page-1}" id="${pages.page-1}">${pages.page-1}</a>
			                                	<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=${pages.page}" id="${pages.page}">${pages.page}</a>
			                                	<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=${pages.page+1}" id="${pages.page+1}">${pages.page+1}</a>
		                                	</c:otherwise>
	                                	</c:choose>
	                                	<c:choose>
	                                		<c:when test="${pages.page+1 <= pages.totalPage}">
	                                			<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=${pages.page+1}" id="${pages.page+1}">下一页</a>
	                                		</c:when>
	                                	</c:choose>
	                                	<c:choose>
	                                		<c:when test="${pages.totalPage == 0}">
	                                			<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=${pages.page}" id="${pages.page}">尾页</a>
			                            	</c:when>
			                            	<c:otherwise>
			                            		<a href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=${userNickName}&orderNumber=${orderNumber}&paymentID=${paymentID}&beginTime=${beginTime}&endTime=${endTime}&submit1=${submit1}&page=${pages.totalPage}" id="${pages.totalPage}">尾页</a>
			                            	</c:otherwise>
			                            </c:choose> 
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