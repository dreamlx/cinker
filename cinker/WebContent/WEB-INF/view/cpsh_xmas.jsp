<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.cinker.util.*"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0,viewport-fit=cover">
    <title>预定</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cpsh/weui.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cpsh/newyear2018.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cpsh/example.css"/>
</head>
<body ontouchstart>
	<div class="container" id="container"></div>

	<script type="text/html" id="tpl_home">
<div class="page">
    <div class="page__hd">
    	<div class="weui-flex">
            <div class="weui-flex__item">
	            <img src="${pageContext.request.contextPath}/images/cpsh/homepage_1.png" alt="WeUI" width="100%" />	
            </div>
        </div>

        <div class="weui-flex">
            <div class="weui-flex__item">
	            <img src="${pageContext.request.contextPath}/images/cpsh/homepage.jpg" alt="WeUI" width="100%" />	
            </div>
        </div>

        <div class="weui-flex">
            <div class="weui-flex__item">
	            <img src="${pageContext.request.contextPath}/images/cpsh/homepage_2.png" alt="WeUI" width="100%" />	
            </div>
        </div>

        <div class="weui-flex">
            <div class="weui-flex__item">
	            <a class="js_item homepage_btn" id="dinner_btn" data-id="dinner" href="javascript:;">
		        </a>
            </div>
        </div>

        <div class="weui-flex">
            <div class="weui-flex__item">
	            <a class="js_item homepage_btn" id="champagne_btn" data-id="champagne" href="javascript:;">
		        </a>
            </div>
        </div>
        
    </div>
</div>
<script type="text/javascript">
    $(function(){
		
        $('.js_item').on('click', function(){
            var id = $(this).data('id');
            window.pageManager.go(id);
        });
    });
</script>
</script>


<script type="text/html" id="tpl_dinner">
	<div class="page">
	    <div class="page__hd">
	        <div class="weui-flex" style="margin-top:15%">
	            <div class="weui-flex__item">
	            	<img src="${pageContext.request.contextPath}/images/cpsh/menu_title_1.png" width="100%">
	            </div>
	        </div>
	        <div class="weui-flex" style="margin-top:2%">
	            <div class="weui-flex__item">
	            	<img src="${pageContext.request.contextPath}/images/cpsh/menu_1.png" width="100%">
	            </div>
	        </div>
	        <div class="weui-flex" style="margin-top:15%">
	            <div class="weui-flex__item">
		            <a class="js_item homepage_btn" id="book_dinner_btn" data-id="book1" href="javascript:;">
			        </a>
	            </div>
	        </div>
	    </div>
	</div>
	<script>
		$(function(){
			$('.js_item').on('click', function(){
	            var id = $(this).data('id');
	            window.pageManager.go(id);
	        });
		});
	</script>
</script>

<script type="text/html" id="tpl_champagne">
	<div class="page">
	    <div class="page__hd">
	        <div class="weui-flex" style="margin-top:15%">
	            <div class="weui-flex__item">
	            	<img src="${pageContext.request.contextPath}/images/cpsh/menu_title_2.png" width="100%">
	            </div>
	        </div>
	        <div class="weui-flex" style="margin-top:20%">
	            <div class="weui-flex__item">
	            	<img src="${pageContext.request.contextPath}/images/cpsh/menu_2.png" width="100%">
	            </div>
	        </div>
	        <div class="weui-flex" style="margin-top:15%">
	            <div class="weui-flex__item">
		            <a class="js_item homepage_btn" id="book_champagne_btn" data-id="book2" href="javascript:;">
			        </a>
	            </div>
	        </div>
	    </div>
	</div>
	<script>
		$(function(){
			$('.js_item').on('click', function(){
	            var id = $(this).data('id');
	            window.pageManager.go(id);
	        });
		});
	</script>
</script>

<script type="text/html" id="tpl_book1">
	<div class="page">
	    <div class="page__hd">
	        <div class="weui-flex" style="margin-top:15%">
	            <div class="weui-flex__item">
	            	<img src="${pageContext.request.contextPath}/images/cpsh/book_title_1.png" width="100%">
	            </div>
	        </div>
	        <div class="weui-flex" style="margin-top:1%">
	            <div class="weui-flex__item">
	            	<img src="${pageContext.request.contextPath}/images/cpsh/book_2.png" width="100%">
	            </div>
	        </div>
	        <div class="weui-flex" style="margin-top:5%">
	            <div class="weui-flex__item" >
	            	<form action="${pageContext.request.contextPath}/activity/activityPayBegin" id="myForm1">
      					<input name="orderId" value="${orderId}" type="hidden">
						<input id="eventId" name="eventId" value="xmas3001" type="hidden">
						<input name="userNumber" value="${userNumber}" type="hidden">
		            	<div class="form_line" id="book_form_name">
		            		<input type="text" id="formName" name="name" class="book_form_input" maxlength='20'>
		            	</div>

		            	<div class="form_line" id="book_form_tel">
		            		<input type="text" id="formPhone" name="phone" class="book_form_input"  maxlength='11'>
		            	</div>

		            	<div class="form_line" id="book_form_num">
							<input name="quaty" id="form_num" class="book_form_input" value="1">
		            	</div>

		            	<div class="form_line enfont" id="book_form_date">
		            		12/31 <span>18</span>:00
		            	</div>

		            	<div class="form_line enfont" id="book_form_price_1">
		            		RMB 300
		            	</div>
		            </form>
	            </div>
	        </div>
	        <div class="weui-flex" style="margin-top:3%">
	            <div class="weui-flex__item" style="text-align: center">
	            	<img src="${pageContext.request.contextPath}/images/cpsh/book_info.png" width="90%">
	            </div>
	        </div>
	        <div class="weui-flex" style="margin-top:8%">
	            <div class="weui-flex__item">
		            <a class="book_pay_btn" id="book_pay_btn" href="javascript:;"></a>
	            </div>
	        </div>
	    </div>
	</div>
	<script>
		$(function(){
			$('#book_pay_btn').on('click', function(){
	            var name = $('#formName').val();
            	var phone = $('#formPhone').val();
            	if(name=='' || phone == ''){
                	alert('请输入您的姓名和手机号');
                	return false;
            	}
            	if(!(/^1(3|4|5|7|8)\d{9}$/.test(phone))){
               		alert("手机号码有误，请重填"); 
                	return false;
            	}
	            $('#myForm1').submit();
	        });

	        $('#book_form_num').on('click', function () {
		        weui.picker([{
		            label: '1人',
		            value: 1
		        }, {
		            label: '2人',
		            value: 2
		        }, {
		            label: '3人',
		            value: 3
		        },{
		            label: '4人',
		            value: 4
		        }, {
		            label: '5人',
		            value: 5
		        }], {
		            onConfirm: function (result) {
		                $('#form_num').val(result[0]);
		                // console.log(result);
		                var np = result[0]*300;
		                $('#book_form_price_1').html('RMB '+np);
		            }	
		        });
		    });


	        $('#book_form_date').on('click', function () {
		        weui.picker([{
		            label: '18:00',
		            value: 18
		        }, {
		            label: '20:00',
		            value: 20
		        }], {
		            onConfirm: function (result) {
		                $('#book_form_date>span').html(result[0]);
		                if(result[0]=='18'){
		                	$('#eventId').val('xmas3001')
		                }else{
		                	$('#eventId').val('xmas3002')
		                }
		            }	
		        });
		    });
		});

	</script>
</script>

<script type="text/html" id="tpl_book2">
	<div class="page">
	    <div class="page__hd">
	        <div class="weui-flex" style="margin-top:15%">
	            <div class="weui-flex__item">
	            	<img src="${pageContext.request.contextPath}/images/cpsh/book_title_2.png" width="100%">
	            </div>
	        </div>
	        <div class="weui-flex" style="margin-top:1%">
	            <div class="weui-flex__item">
	            	<img src="${pageContext.request.contextPath}/images/cpsh/book_2.png" width="100%">
	            </div>
	        </div>
	        <div class="weui-flex" style="margin-top:5%">
	            <div class="weui-flex__item" >
	            	<form action="${pageContext.request.contextPath}/activity/activityPayBegin" id="myForm2">
      					<input name="orderId" value="${orderId}" type="hidden">
						<input id="eventId" name="eventId" value="xmas500" type="hidden">
						<input name="userNumber" value="${userNumber}" type="hidden">
		            	<div class="form_line" id="book_form_name">
		            		<input type="text" id="formName2" name="name" class="book_form_input" maxlength='20'>
		            	</div>

		            	<div class="form_line" id="book_form_tel">
		            		<input type="text" id="formPhone2" name="phone" class="book_form_input"  maxlength='11'>
		            	</div>

		            	<div class="form_line" id="book_form_num2">
							<input name="quaty" id="form_num2" class="book_form_input" value="1">
		            	</div>

		            	<div class="form_line enfont" id="book_form_date">
							12/31
						</div>

		            	<div class="form_line enfont" id="book_form_price_2">
		            		RMB 500
		            	</div>
		            </form>
	            </div>
	        </div>
	        <div class="weui-flex" style="margin-top:3%">
	            <div class="weui-flex__item" style="text-align:center">
	            	<img src="${pageContext.request.contextPath}/images/cpsh/book_info.png" width="90%">
	            </div>
	        </div>
	        <div class="weui-flex" style="margin-top:8%">
	            <div class="weui-flex__item">
		            <a class="book_pay_btn" id="book_pay_btn_2" href="javascript:;">
			        </a>
	            </div>
	        </div>
	    </div>
	</div>
	<script>
		$(function(){
			$('#book_pay_btn_2').on('click', function(){
				var name = $('#formName2').val();
            	var phone = $('#formPhone2').val();
            	if(name=='' || phone == ''){
                	alert('请输入您的姓名和手机号');
                	return false;
            	}
            	if(!(/^1(3|4|5|7|8)\d{9}$/.test(phone))){
               		alert("手机号码有误，请重填"); 
                	return false;
            	}
	            $('#myForm2').submit();
	        });

	        $('#book_form_num2').on('click', function () {
		        weui.picker([{
		            label: '1人',
		            value: 1
		        }, {
		            label: '2人',
		            value: 2
		        }, {
		            label: '3人',
		            value: 3
		        },{
		            label: '4人',
		            value: 4
		        }, {
		            label: '5人',
		            value: 5
		        }], {
		            onConfirm: function (result) {
		                $('#form_num2').val(result[0]);
		            }
		        });
		    });
		});

	</script>
</script>


<script type="text/html" id="tpl_success">
	<div class="page">
	    <div class="page__hd">
	        <div class="weui-flex" style="margin-top:15%">
	            <div class="weui-flex__item">
	            	<img src="${pageContext.request.contextPath}/images/cpsh/book_success.jpg" width="100%">
	            </div>
	        </div>
	    </div>
	</div>
</script>



	<script src="${pageContext.request.contextPath}/js/cpsh/zepto.min.js"></script>
    <script src="https://res.wx.qq.com/open/libs/weuijs/1.0.0/weui.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cpsh/example.js"></script>
</body>