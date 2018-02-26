function profile(){
	var userVipMember = {};
	var firstName = userVipMember['firstName'] = document.getElementById("firstNameInput").value;
	var lastName = userVipMember['lastName'] = document.getElementById("lastNameInput").value; 
	var email = userVipMember['email'] = document.getElementById("emailInput").value;
	var date = userVipMember['birthday'] = document.getElementById("birthday").value; 
	var sex = userVipMember['sex'] = document.getElementById("sex").value; 
	
	$.ajax({
        url : "/cinker/usermember/profile",
        type : "POST",
        contentType : "application/json;charset=UTF-8",
        data : JSON.stringify(userVipMember),
        success : function(result) {
           alert(result);
           window.location.href = '/cinker/usermember/getUserInfo';
        },
        error:function(result){
            alert("Sorry,update fail!");
        }
    });
}