<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<div class="navbar">
        <div class="navbar-inner">
            <div class="navbar-container">
                <div class="navbar-header pull-left">
                    <a href="#" class="navbar-brand">
                        <small>
                            <img src="${pageContext.request.contextPath}/images/logo.png" alt="" />
                        </small>
                    </a>
                </div>
                
                <div class="navbar-header pull-right">
                    <div class="navbar-account white">
                    	<a class=" pull-right btn margin-top-10 margin-right-10 btn-sm" href="${pageContext.request.contextPath}/cinkerMaintain/admin">
                    		退出登录 
                    	</a>
						<h5 class="pull-right margin-top-15 margin-right-20">
							<i class="iconfont icon-hi" style="font-size: 17px;"></i>
							<strong class="margin-left-10 margin-right-5">${username}</strong>
						</h5>
                      </div>
                </div>
            </div>
        </div>
    </div>
	
	<!-- 侧边导航 -->
			<div class="page-sidebar" id="sidebar">
                <ul class="nav sidebar-menu">
                    <!--首页-->
                    <li class="active">
                        <a href="Home.html">
                            <i class="menu-icon glyphicon glyphicon-home"></i>
                            <span class="menu-text">首页</span>
                        </a>
                    </li>
                    <li>
                        <a class="menu-dropdown" href="${pageContext.request.contextPath}/cinkerMaintain/getCinemaList">
                            <i class="menu-icon fa fa-university" style="font-size: 16px;"></i>
                            <span class="menu-text">影院管理</span>
                            <i class="menu-expand"></i>
                        </a>                        
                    </li>
                    <li>
                        <a class="menu-dropdown" href="${pageContext.request.contextPath}/cinkerMaintain/getScreenList">
                            <i class="menu-icon fa fa-video-camera" style="font-size: 16px;"></i>
                            <span class="menu-text">影厅管理</span>
                            <i class="menu-expand"></i>
                        </a>                        
                    </li>
                     <li>
                        <a class="menu-dropdown" href="${pageContext.request.contextPath}/cinkerMaintain/getSearch?page=1">
                            <i class="menu-icon fa fa-film" style="font-size: 16px;"></i>
                            <span class="menu-text">影讯管理</span>
                            <i class="menu-expand"></i>
                        </a>
                    </li>
                    <li>
                        <a class="menu-dropdown" href="${pageContext.request.contextPath}/cinkerMaintain/getSearchUserVipMember?page=1">
                            <i class="menu-icon fa fa-user" style="font-size: 16px;"></i>
                            <span class="menu-text">会员管理</span>
                            <i class="menu-expand"></i>
                        </a>
                    </li>
                    <li>
                        <a class="menu-dropdown" href="${pageContext.request.contextPath}/cinkerMaintain/getSearchFilmOrders?page=1">
                            <i class="menu-icon fa fa-ticket" style="font-size: 16px;"></i>
                            <span class="menu-text">订单一览</span>
                             <i class="menu-expand"></i>
                        </a>                       
                    </li>
                    <li>
                        <a class="menu-dropdown" href="${pageContext.request.contextPath}/cinkerMaintain/getSearchPayment?userNickName=&orderNumber=&paymentID=&beginTime=&endTime=&submit1=&page=1">
                            <i class="menu-icon fa fa-credit-card" style="font-size: 16px;"></i>
                            <span class="menu-text">支付一览</span>
                             <i class="menu-expand"></i>
                        </a>                        
                    </li> 
                     <li>
                        <a class="menu-dropdown" href="${pageContext.request.contextPath}/cinkerMaintain/getActivityFilmInfo?page=1">
                            <i class="menu-icon fa fa-bullhorn" style="font-size: 16px;"></i>
                            <span class="menu-text">活动一览</span>
                             <i class="menu-expand"></i>
                        </a>                        
                    </li> 
                     <li>
                        <a class="menu-dropdown" href="${pageContext.request.contextPath}/cinkerMaintain/getActivityPersonal?page=1">
                            <i class="menu-icon fa fa-check-square-o" style="font-size: 16px;"></i>
                            <span class="menu-text">活动报名一览</span>
                             <i class="menu-expand"></i>
                        </a>                        
                    </li>
                     <li>
                        <a class="menu-dropdown" href="${pageContext.request.contextPath}/cinkerMaintain/getStatements?fromDate=">
                            <i class="menu-icon fa fa-list-alt" style="font-size: 16px;"></i>
                            <span class="menu-text">下载对帐单</span>
                             <i class="menu-expand"></i>
                        </a>
                    </li>
				</ul>
            </div>
