<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>支付结果</title>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="js/ltie9/html5shiv.min.js"></script>
  <script src="js/ltie9/respond.min.js"></script>
<![endif]-->
</head>
<body class="bg-1">    
    <section class="container">
    	<section class="result_cont">
            <h1>${filmOrder.filmTitle}</h1>
            <h6>${filmOrder.englishName}</h6>
            <div class="screen">
                <h3>${filmOrder.sessionName} </h3>
            </div>
            <div class="date_seat">
                <div class="left_date_time">
                    <div class="session_date">
                        <p>
                            ${filmOrder.sessionShowTime}
                        </p>
                        
                    </div>
                    <div class="session_time enboldfont">
                       ${filmOrder.showTime}
                    </div>
                </div>
                <div class="right_seat">
                    <p>${filmOrder.seat}</p>
                </div>
            </div>
            <div class="qrcode">
                <div class="left_address">
                    <h6>
                        请使用报名的手机号<br>
						在现场核对
                    </h6>
                    
                </div>
                <div class="right_qrcode">
                    <p>
						${cinema.address} <br/>
						Tel ：${cinema.tel}
					</p>
                </div>
            </div>
            
        </section> 
        <div class="frame_bottom"></div>
    </section>
</body>
</html>