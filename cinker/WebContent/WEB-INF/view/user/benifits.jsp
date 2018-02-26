<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/common/common.jsp" %>
<%@ page import="com.cinker.util.*"%>
<title>会员权益 Benifits</title>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body class="bg-3 memberBenifits">    
		<div class="cinkerlogo">
			<img src="/cinker/images/logo2.png">
		</div>
		<section class="memberDesc">
			<h4 class="memberCardSolgan">三克一刻，精彩时刻</h4>
			<div class="memberTitle">
				<h2 class="memberTitleEng enboldfont colorLY">
					CINKER MEMBER
				</h2>
				<h3 class="memberTitleChn colorLY">
					三克会员
				</h3>
				<div class="getBenifitsWay">
					<p class="fontCHN colorLY">
						关注“三克电影空间”官方微信并完成注册，即可轻松成为三克会员
					</p>
					<p class="fontENG colorLY enfont">
						Becoming a member is easy! Simply follow our official WeChat account: cinker-cinema-space	to enjoy the following benefits
					</p>
				</div>
				<div class="arrow"></div>
			</div>
			
			<div class="benifitsList">
				<div class="benifitCHN">
					<p>
						1. 在CINKER CINEMAS三克影院观影任选影片 65 折优惠 
					</p>
					<p>
						2. 在CINKER CINEMAS三克影院观影套餐 8 折优惠 
					</p>
					<p>
						3. 生日当天到店消费获赠惊喜礼品
					</p>
					<p class="benifitWarning">* 须在注册会员过程中提交个人信息资料</p>
				</div>
				
				<div class="benifitENG">
					<p>
						1. 35% off on all movie tickets at CINKER CINEMAS
					</p>
					<p>
						2. 20% off on all movie packages at CINKER CINEMAS
					</p>
					<p>
						3. Surprise gift with purchase on the day of your birthday
					</p>
					<p class="benifitWarning">* must provide personal detail during the registration process</p>
				</div>
				
			</div>			
		</section>


		<section class="memberDesc sale">
			<h4 class="memberCardSolgan">三克影院专享优惠</h4>
			<div class="memberTitle">
				<h2 class="memberTitleEng enboldfont colorLY">
					CINKER MORDEN MEMBER
				</h2>
				<h3 class="memberTitleChn colorLY">
					三克摩登会员<span style="font-size:14px">（仅限北京）</span>
				</h3>
				<div class="arrow"></div>
			</div>
			<div class="benifitsList">
				<div class="benifitCHN">
					<p>
						1. 在CINKER CINEMAS三克影院观影 5 折优惠
					</p>
					<p>
						2. 在CINKER CINEMAS三克影院观影套餐 8 折优惠 
					</p>
					<p>
						3. 获赠 2 张 CINKER PICTURES / CINKER CINEMAS通用免费观影券
					</p>
					<p class="benifitWarning">
						观影当日同场次可为本人及同行友人以同等优惠价格在CINKER CINEMAS三克影院购买多至 4 张观影券
					</p>
				</div>
				<div class="benifitENG">
					<p>
						1. 50% off on all movie tickets at CINKER CINEMAS
					</p>
					<p>
						2. 20% off on all movie packages at CINKER CINEMAS
					</p>
					<p>
						3. 2 free movie tickets to use at CINKER PICTURES/CINKER CINEMAS
					</p>
					<p class="benifitWarning">
						Members can purchase a total of 4 tickets per screening at discounted price at CINKER CINEMAS. 
					</p>
				</div>
				<a class="buy-btn enboldfont" href="${pageContext.request.contextPath}/recharge/confirmRecharge?&orderId=${orderId}&userSessionId=${userSessionId}&headOfficeItemCode=HY0000000000001&recognitionId=5&priceInCents=28800">¥288 <span>/年</span></a>
			</div>
		</section>

		<section class="memberDesc sale supermorden">
			<h4 class="memberCardSolgan">三克映画无限观影体验</h4>
			<div class="memberTitle">
				<h2 class="memberTitleEng enboldfont colorLY">
					CINKER MORDEN SUPER
				</h2>
				<h3 class="memberTitleChn colorLY">
					三克超级摩登会员
				</h3>
				<p class="colorLY">
					2017年度CINKER MODERN SUPER 三克超级摩登会员仅限100席
				</p>
				<p class="colorLY">
					A limit of 100 CINKER Modern Super membership is available for 2017 
				</p>
				<div class="arrow"></div>
			</div>
			
			<div class="benifitsList">
				<div class="benifitCHN">
					<p>1. 在CINKER CINEMAS三克影院观影 5 折优惠</p>
					<p>2. 在CINKER CINEMAS三克影观影套餐  7 折优惠</p>
					<p>3. 在CINKER PICTURES三克映画享受会员本人全年无限次免费观影优惠</p>
					<p>4. 获赠 4 张 CINKER PICTURES / CINKER CINEMAS 通用免费观影券</p>
					<p class="benifitWarning">
						*观影当日同场次可为本人及同行友人以同等优惠价格在CINKER CINEMAS三克影院购买多至 4 张观影券
					</p>
					<p class="benifitWarning">
						*CINKER PICTURES三克映画 免费观影仅限本人使用。
					</p>
				</div>
				<div class="benifitCHN">
					<p>
						1. 4 free movie tickets to use at CINKER PICTURES/CINKER CINEMAS
					</p>
					<p>2. 50% off on all movie tickets at CINKER CINEMAS</p>
					<p>3. 30% off on all movie packages at CINKER CINEMAS</p>
					<p>4. Enjoy free screening all year round, exclusively for CINKER Modern Super member ONLY</p>
					<p class="benifitWarning">
						*Members can purchase a total of 4 tickets per screening at discounted price at CINKER CINEMAS. 
					</p>
					<p class="benifitWarning">
						*Free screening at CINKER PICTURES exclusively for CINKER MODERN SUPER member ONLY
					</p>
					
				</div>
				<a class="buy-btn enboldfont" href="${pageContext.request.contextPath}/recharge/confirmRecharge?&orderId=${orderId}&userSessionId=${userSessionId}&headOfficeItemCode=HY0000000000004&recognitionId=24&priceInCents=298800">¥2988 <span>/年</span></a>
			</div>
		</section>

		<section class="memberDesc sale">
			<h4 class="memberCardSolgan">共享电影与美食</h4>
			<div class="memberTitle">
				<h2 class="memberTitleEng enfont colorLY">
					CINKER CLASSIC MEMBER
				</h2>
				<h3 class="memberTitleChn colorLY">
					三克经典会员
				</h3>
				<div class="arrow"></div>
			</div>
			
			<div class="benifitsList">
				<div class="benifitCHN">
					<p>1. 在CINKER CINEMAS三克影院观影 5 折优惠</p>
					<p>2. 在CINKER CINEMAS三克影院观影套餐 7 折优惠 </p>
					<p>3. 在CINKER CINEMAS三克影院之 PIC CAFE 用餐 9 折优惠</p>
					<p>4. 在CINKER PICTURES三克映画观影套餐 7  折优惠</p>
					<p>5. 获赠  4 张 CINKER PICTURES / CINKER CINEMAS通用免费观影券</p>
					<p class="benifitWarning">
						*观影当日同场次可为本人及同行友人以同等优惠价格在CINKER CINEMAS三克影院或CINKER PICTURES三克映画购买多至 4 张观影券
					</p>
				</div>
				
				<div class="benifitENG">
					<p>
						1. 50% off on all movie tickets at CINKER CINEMAS
					</p>
					<p>
						2. 40% off on all movie packages at CINKER CINEMAS
					</p>
					<p>
						3. 10% off all food and beverage items at PIC CAFÉ
					</p>
					<p>
						4. 30% off on all movie packages at CINKER PICTURES
					</p>
					<p>
						5. 4 free movie tickets to use at CINKER PICTURES/CINKER CINEMAS
					</p>
					<p class="benifitWarning">
						Members can purchase a total of 4 tickets per screening at discounted price<br>
						at CINKER CINEMAS and CINKER PICTURES. 
					</p>
				</div>
				<a class="buy-btn enboldfont" href="${pageContext.request.contextPath}/recharge/confirmRecharge?&orderId=${orderId}&userSessionId=${userSessionId}&headOfficeItemCode=HY0000000000002&recognitionId=25&priceInCents=300000">¥3000</a>
			</div>
		</section>

		<section class="memberDesc sale">
			<h4 class="memberCardSolgan">电影与美食的超级礼遇</h4>
			<div class="memberTitle">
				<h2 class="memberTitleEng enfont colorLY">
					CINKER CLASSIC SUPER
				</h2>
				<h3 class="memberTitleChn colorLY">
					三克超级经典会员
				</h3>
				<div class="arrow"></div>
			</div>
			
			<div class="benifitsList">
			
				<div class="benifitCHN">
					<p>1. 在CINKER CINEMAS三克影院观影 4 折优惠</p>
					<p>2. 在CINKER CINEMAS三克影院观影套餐 6 折优惠  </p>
					<p>3. PIC CAFE 用餐 88  折优惠</p>
					<p>4. CINKER PICTURES三克映画 观影套餐5折优惠</p>
					<p>5. 在CINKER PICTURES三克映画 用餐 88 折优惠</p>						
					<p>6. 获赠 8 张CINKER PICTURES / CINKER CINEMAS通用免费观影券</p>
					<p class="benifitWarning">
						*观影当日同场次可为本人及同行友人以同等优惠价格在CINKER CINEMAS三克影院或CINKER PICTURES三克映画购买多至 6 张观影券
					</p>
				</div>
				
				<div class="benifitENG">
					<p>
						1. 60% off on all movie tickets at CINKER CINEMAS
					</p>
					<p>
						2. 40% off on all movie packages at CINKER CINEMAS
					</p>
					<p>
						3. 12% off all food and beverage items at PIC CAFÉ
					</p>
					<p>
						4. 50% off on all movie packages at CINKER PICTURES
					</p>
						
					<p>
						5. 12% off all food and beverage items at CINKER PICTURES
					</p>
					<p>
						6. 8 free movie tickets to use at CINKER PICTURES/CINKER CINEMAS
					</p>
					<p class="benifitWarning">
						*Members can purchase a total of 6 tickets per screening at discounted price	at CINKER CINEMAS and CINKER PICTURES. 
					</p>
				</div>
				<a class="buy-btn enboldfont" href="${pageContext.request.contextPath}/recharge/confirmRecharge?&orderId=${orderId}&userSessionId=${userSessionId}&headOfficeItemCode=HY0000000000003&recognitionId=26&priceInCents=3000000">¥30000</a>
			</div>
		</section>

		<div class="benifitsTerms colorLG">
			<h6 class="termsTitle colorLG enfont">
				Terms and Conditions
			</h6>
			<h6 class="termsTitleCHN colorLG">
				注意事项
			</h6>
			<p class="fontCHN">
				1  上述会员权益仅限本人使用，不可转让他人
			</p>
			<p class="fontCHN">
				2  上述会员政策权益适用于CINKER三克®品牌旗下CINKER PICTURES三克映画、
GARDEN by CINKER PICTURES、CINKER CINEMAS三克影院、PIC CAFE映画咖啡
			</p>
			<p class="fontCHN">
				3  CINKER PICTURES / CINKER CINEMAS通用免费观影券有效期限一年，自办卡之日起有效
			</p>
			<p class="fontCHN">
				4  上述会员权益及优惠政策不适用于门店或线上特殊活动，并不得与门店或线上其它优惠政策同享
			</p>
			<div class="clear25px"></div>
			<p class="fontENG enfont">
				1 Benefits to be enjoyed by the member, membership is non-transferrable;
			</p>
			<p class="fontENG enfont">
				2 The benefits can be used throughout all CINKER venues: CINKER PICTURES, 
Garden by CINKER PICTURES, CINKER CINEMAS, PIC CAFÉ
			</p>
			<p class="fontENG enfont">
				3 Complimentary movie tickets are valid for one year, valid from the time of purchase
			</p>
			<p class="fontENG enfont">
				4 Benefit cannot be used in conjunction with other online or in store promotions
			</p>
		</div>
		<div class="clear85px"></div>
	</body>
	<script>

		$(document).ready(function(){
			$('.memberTitle').click(function(evt){
				$(this).next().toggle();
			});
		});
</script>
</html>