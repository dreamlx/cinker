<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/common/common.jsp"%> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport" content="initial-scale=1.0,user-scalable=no,minimum-scale=1.0,maximum-scale=1.0"/>
<meta name="description" content="">
<meta name="author" content="">
<title>订单详情</title>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="js/ltie9/html5shiv.min.js"></script>
  <script src="js/ltie9/respond.min.js"></script>
<![endif]-->
</head>
<body class="bg-2">
    <section class="container">
    	<section class="paygoonmain">
    		<!-- 订单状态  0 未支付，1支付过的-->
			<c:choose>
				<c:when test="${filmOrder.status==0 && filmOrder.orderType == 1}">
						<!--<div class="pay-goon">您可以在<span> ${ filmOrder.startTime} </span>内</div>-->
	            		<div class="clear10px"></div>
	            		<div class="go-person">
	            			<a href="${pageContext.request.contextPath}/wechat/pay?outTradeNo=${filmOrder.orderNumber}&filmName=${filmOrder.filmTitle}&sessionID=${filmOrder.sessionId}&totalPrice=${filmOrder.totalValueCents}" class="gobuy">
								继续支付
							</a> 
	            		</div>
	            		<div class="clear45px"></div>
				</c:when>
				<c:when test="${filmOrder.status==0 && filmOrder.orderType == 2}">
						<!--<div class="pay-goon">您可以在<span> ${ filmOrder.startTime} </span>内</div>-->
	            		<div class="clear10px"></div>
	            		<div class="go-person">
	            			<a href="${pageContext.request.contextPath}/activity/getQRcode?orderId=${filmOrder.orderNumber}&filmName=${filmOrder.filmTitle}&sessionID=${filmOrder.sessionId}&totalPrice=${filmOrder.totalValueCents}&eventId=${filmOrder.sessionId}&quaty=${filmOrder.totalOrderCount}" class="gobuy">
								继续支付
							</a>
	            		</div>
	            		<div class="clear45px"></div>
				</c:when>
				<c:when test="${filmOrder.orderType == 1}">
					<div class="passtit">取票密码</div>
        			<div class="passimg">
        				<img alt="" src="${pageContext.request.contextPath}/wechat/showQRcode?content=${filmOrder.bookingID}">
					</div>
        			<div class="passmsg">${filmOrder.bookingID}</div>
				</c:when>
				<c:when test="${filmOrder.orderType == 2}">
					<div class="passtit">报名信息</div>
					<div>预约手机： <span class="passmsg" id="tel">${filmOrder.areaCategoryCode}</span></div>
					<div>预约人数： <span class="passmsg">${filmOrder.totalOrderCount}人</span></div>
				</c:when>
			</c:choose>
        	<script type="text/javascript">
			    var tel = document.getElementById('tel'),
			    telVal = tel.innerText;
			    var newTelVal = '';
			    if(telVal.length > 0){
			        for(var i = 0; i < telVal.length; i++){
			            if(i >= telVal.length-4){
			                newTelVal += telVal[i];
			            }else{
			                newTelVal += '*';
			            }
			        }
			    }
			    tel.innerText = newTelVal;
			</script>
        	<div class="pg-title">${filmOrder.filmTitle}<br/>${englishName}</div>
            <section class="pg-titla">
                <div class="pg_left">
                    <h3>影厅</h3> 
                    <p>SCREEN</p>
                </div>
                <div class="pg_right">
                    ${filmOrder.sessionName}
                </div>
            </section>
            <section class="pg-content">
            	<div class="pgc_lf">
                	<div class="pgcm-lf">
                    	<h2>日期</h2>
                        <p>Date</p>
                    </div>
                	<div class="pgcm-rt">
                    	<h2></h2>
                    	<h3>${filmOrder.showTime}</h3>
                    </div>
                	<div class="pgcm-lf">
                    	<h2>时间</h2>
                        <p>Time</p>
                    </div>
                	<div class="pgcm-rt">
                    	<h3></h3>
                    </div>
                </div>
            	<div class="pgc_rt">
                	<div class="pgcm-lf">
                    	<h2>座位</h2>
                        <p>Seat</p>
                    </div>
                	<div class="pgcm-rtr">
                    	<h3>${filmOrder.seat}</h3>
                    </div>
                </div>
            </section>
        </section>
    	<div class="clear10px"></div>
        <div class="order-div">
        	<p>订单号:${orderNumber}</p>
        	<p>实付金额:${totalValueCents}</p>
        	<c:choose>
        		<c:when test="${type == 1}">
        			<p>支付方式:微信支付</p>
        		</c:when>
        		<c:when test="${type == 2 }">
        			<p>支付方式:会员卡支付</p>
        		</c:when>
        	</c:choose>
        	<p>支付流水号:${transactionId}</p>
        </div>
    </section>
</body>
</html>