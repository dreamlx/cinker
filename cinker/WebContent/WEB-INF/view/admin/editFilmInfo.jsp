<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"  autoFlush="false"  buffer="300kb"%>
<%@ include file="temp.jsp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<script src="${pageContext.request.contextPath}/js/cinker/filmInfo.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/WdatePicker.js"></script>

<title>影片信息管理</title>
</head>
<body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cinker/filmInfo.js"></script>
	<form action="${pageContext.request.contextPath}/cinkerMaintain/insertFilmInfo" enctype="multipart/form-data" method="post" onsubmit = "return add();">
	
	<div class="main-container container-fluid">
		<!-- Page Container -->
		<div class="page-container">
			
			<jsp:include page="menu.jsp"></jsp:include>
			<div class="page-content">

				<div class="widget-header">                 
                   <span class="widget-caption"><strong>影片信息修改</strong></span>
                </div>

				<div class="page-body">
					<div class="col-lg-20 col-md-20 col-sm-20 col-xs-20">
						<div class="well">
							<div class="form-horizontal form-bordered">
							<c:choose>
								<c:when test="${empty filmInfo.filmId}">
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right padding-bottom-9">影院编码</label>
									<div class="col-sm-10">
										<input type="text"  class="form-control" id = "filmId" name = "filmId" value="${filmInfo.filmId}" />
									</div>
								</div>	
								</c:when>
								<c:otherwise>
									<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right padding-bottom-9">影院编码</label>
									<div class="col-sm-10">
										<input type="text" readonly="readonly" class="form-control" id = "filmId" name = "filmId" value="${filmInfo.filmId}" />
									</div>
								</div>	
								
								</c:otherwise>
							</c:choose>							
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影片中文名</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "chineseName" name = "chineseName" value="${filmInfo.chineseName}"/>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">影片英文名</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "englishName" name = "englishName" value="${filmInfo.englishName}"/>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">海报图片</label>
									<div class="col-sm-10">
										<input type="file" class="form-control" id="surfaceImage" name = "surfaceImage" onchange="preview(this)"/>
										<div id="preview"></div>
										<input type="text" class="form-control" readonly="readonly" id="surfaceImageUrl" name = "surfaceImageUrl" value="${filmInfo.imageUrl}" />
									</div>
								</div>								
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">详情图片上传</label>
									<div class="col-sm-10">
										<input class="form-control" type="file" id="graphTheories" name="graphTheories" multiple="multiple" onchange="javascript:setImagePreviews();"/>					
										<div id="prevview"></div>
										<input type="text" class="form-control" readonly="readonly" id = "imageUrl" name = "imageUrl" value="${filmContentImage.imageUrl}" />
									</div>
								</div>							
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">导演</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "director" name = "director" value="${filmInfo.director}"/>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">编剧</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "scriptWriter" name = "scriptWriter" value="${filmInfo.scriptWriter}"/>
									</div>
								</div>
								
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">上映时间</label>
									<div class="col-sm-10">
										<input  class="form-control"s onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" type="text" placeholder="请输入上映时间" id="showTime" name="showTime" value="${filmInfo.showTime}">
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">播放时长(分钟)</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "runTime" name="runTime"  value="${filmInfo.runTime}"></input>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">演员</label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id = "starringActor" name = "starringActor" value="${filmInfo.starringActor}"/>
									</div>
								</div>
								<div class="form-group">
											<label
												class="col-sm-3 control-label no-padding-right padding-bottom-9">影片类型</label>
											<div class="col-sm-10">
												<select  tabindex="2" class="form-control" id = "filmType" name="filmType"  >
													<option value="2D">2D</option>
													<option value="3D">3D</option>	
												</select>
												<script type="text/javascript">
													for(var i=0;i<document.getElementById('filmType').options.length;i++){ 	
														if(document.getElementById('filmType').options[i].value==='${filmInfo.filmType}'){														
															document.getElementById('filmType').options[i].selected=true; 
														} 
													} 				
												</script>
											</div>
										</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">简介</label>
									<div class="col-sm-10">
										<textarea class="form-control" id = "language" name="language" >${filmInfo.language}</textarea>
									</div>
								</div>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">三客影评</label>
									<div class="col-sm-10">
										<textarea class="form-control" id = "cinecism" name="cinecism" >${filmInfo.cinecism}</textarea>
									</div>
								</div>
								
								<script type="text/javascript">
						            jQuery.fn.extend({
						                autoHeight: function(){
						                    return this.each(function(){
						                        var $this = jQuery(this);
						                        if( !$this.attr('_initAdjustHeight') ){
						                            $this.attr('_initAdjustHeight', $this.outerHeight());
						                        }
						                        _adjustH(this).on('input', function(){
						                            _adjustH(this);
						                        });
						                    });
						                    /**
						                     * 重置高度 
						                     * @param {Object} elem
						                     */
						                    function _adjustH(elem){
						                        var $obj = jQuery(elem);
						                        return $obj.css({height: $obj.attr('_initAdjustHeight'), 'overflow-y': 'hidden'})
						                                .height( elem.scrollHeight );
						                    }
						                }
						            });
						            // 使用
						            $(function(){
						                $('textarea').autoHeight('200');
						            });
						        </script>
								<div class="form-group">
									<label
										class="col-sm-3 control-label no-padding-right padding-bottom-9">错误信息</label>
									<div class="col-sm-10">
									
										<input type="text" style="color: red" class="form-control" readonly="readonly" value="${message}" ></input>
									</div>
								</div>

								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<c:choose>
											<c:when test= "${empty filmInfo.filmId}">
												<input class="btn btn-azure" name="submit1" value="保 存" type="submit">
											</c:when>
											<c:otherwise>
												<input class="btn btn-azure" name="submit2" value="更新" type="submit">
											</c:otherwise>
										</c:choose>								
											<a  class="btn btn-azure" href="javascript:history.go(-1)"> 返回 </a>
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