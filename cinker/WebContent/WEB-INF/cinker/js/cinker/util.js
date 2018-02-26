//for register page, get mobile phone vaild code
function getRandomNumber(){
	var phone = document.getElementById("phone").value;
	
	if(phone==''){
        alert('请输入手机号');
        return false;
    }
    if(!(/^1(3|4|5|7|8)\d{9}$/.test(phone))){
        alert("手机号码有误，请重填"); 
        return false;
    }
	var getBtn = $('.getPhoneVaildBtn');
	getBtn.hide();
	var waitBtn = $('.waitPhoneVaild');
	waitBtn.show();
	var secondInput = waitBtn.find('i');
	$.ajax({
        url : "/cinker/wechat/login/getRandomNumber",
        type : "POST",
        contentType : "application/json;charset=UTF-8",
        data : {"phone":phone},
        success : function(result) {
           alert(result);
		   var cdNum = 60;
		   secondInput.html(cdNum);
		   var cd = setInterval(function(){
			   cdNum--;
			   if(cdNum==0){
				   clearInterval(cd);
				   getBtn.show();
				   waitBtn.hide();
				   secondInput.html('');
			   }
			   secondInput.html(cdNum);
		   }, 1000);
		   
        },
        error:function(result){
            alert("对不起,获取验证码失败!");
			getBtn.show();
        }
    });
}
