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
<title>订单查看</title>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="js/ltie9/html5shiv.min.js"></script>
  <script src="js/ltie9/respond.min.js"></script>
<![endif]-->
</head>
<body class="bg-3">    
	<section class="select-main">
        <section class="sm_left">
            <select class="sl02" id = "cinema" name="cinema" >
            	<option value="" selected>ALL</option>
            	<c:forEach items="${cinemas}" var="cinema"> 
            	<option value="${cinema.cinemaId}" >${cinema.name}</option>
            	 </c:forEach>
            </select>  
        </section>		
    </section>
	<section class="clear25px"></section>
	<section class="container">
	<c:forEach items="${filmOrderList}" var="filmOrder"> 
    	<a href="${pageContext.request.contextPath}/usermember/getOrderDetail?orderNumber=${filmOrder.orderNumber}">
    	<c:choose>
            <c:when test="${filmOrder.paymentStatus==0}">	
            	<section class="ct_line ct_line1" data-showtime="${filmOrder.showTime}">
            </c:when>
            <c:when test="${filmOrder.paymentStatus==1}">	
            	<section class="ct_line ct_line2" data-showtime="${filmOrder.showTime}">
            </c:when>
            <c:otherwise>
            	<section class="ct_line ct_line3" data-showtime="${filmOrder.showTime}">
            </c:otherwise>			
        </c:choose>
    	
        	<section class="ctl_lf">
            	<section class="clf_lf">
                	<h1 class="orderMovieMon"></h1>
                	<h2 class="orderMovieDay"></h2>
                	<h3></h3>
                </section>
            	<section class="clf_rt">
                	<h1 class="orderMovieDate"></h1>
                	<h2 class="orderMovieTime"></h2>
                	<h6>${filmOrder.startTime}</h6>
                	<h3>${filmOrder.cinemaName} ${filmOrder.sessionName}</h3>
                </section>
                <h3>${filmOrder.filmTitle}</h3>
            </section>
        	<section class="ctl_rt">
            	<h2 class="ctl_color">¥${filmOrder.totalValueCents}</h2>
            	<h3 class="ctl_color">
            		<c:choose>
            			<c:when test="${filmOrder.paymentStatus==0}">	
            				未支付
            			</c:when>
            			<c:when test="${filmOrder.paymentStatus==1}">	
            				已支付
            			</c:when>
            			<c:when test="${filmOrder.paymentStatus==2}">
            				支付失败
            			</c:when>
            		</c:choose>
            	</h3>
            </section>
        </section>
        </a>
      </c:forEach>
    </section>
    
    <script>
    for(var i=0;i<document.getElementById('cinema').options.length;i++){ 	
		if(document.getElementById('cinema').options[i].value==='${inCinemaId}'){														
			document.getElementById('cinema').options[i].selected=true; 
		} 
	}
        $(document).ready(function() {
        	$('#cinema').change(function(evt){
        			window.location.href='/cinker/usermember/getOrderList?cinemaId='+$(this).val();
        	});
        	$('section.ct_line').each(function(){
        		var dateStr = $(this).data("showtime");
        		var date = dateStr.split(' ')[0];
        		var time = dateStr.split(' ')[1];
        		var month =toEn( date.substr(5,2));
        		var day = date.substr(8,2);
        		var dateArr = date.split('-');
        		date = dateArr[0]+"年"+dateArr[1]+"月"+dateArr[2]+"日";
        		
        		
        		$(this).find('.orderMovieMon').html(month);
        		$(this).find('.orderMovieDay').html(day);
        		$(this).find('.orderMovieDate').html(date);
        		$(this).find('.orderMovieTime').html(time);
        	});
        });
        
        function toEn(mon){
        	switch(mon){
        		case '01':
        			return "JAN";
        			break;
        		case '02':
        			return "FEB";
        			break;
        		case '03':
        			return "MAR";
        			break;
        		case '04':
        			return "APR";
        			break;
        		case '05':
        			return "MAY";
        			break;
        		case '06':
        			return "JUN";
        			break;
        		case '07':
        			return "JUL";
        			break;
        		case '08':
        			return "AUG";
        			break;
        		case '09':
        			return "SEP";
        			break;
        		case '10':
        			return "OCT";
        			break;
        		case '11':
        			return "NOV";
        			break;
        		case '12':
        			return "DEC";
        			break;
        	}
        }
        
    </script>
</body>
</html>