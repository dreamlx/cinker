function saveCinema(){	
	var cinema = {};
	cinema['id'] = document.getElementById("id").value;
	var cinemaId = cinema['cinemaId'] = document.getElementById("cinemaId").value;
	var name = cinema['name'] = document.getElementById("name").value; 
	var address = cinema['address'] = document.getElementById("address").value;
	var city = cinema['city'] = document.getElementById("city").value; 
	var type = cinema['type'] = document.getElementById("type").value; 
	if(cinemaId == ""){
		alert("影院ID不能为空");
		return false;
	}
	if(name == ""){
		alert("影院名称不能为空");
		return false;
	}
	if(address == ""){
		alert("影院地址不能为空");
		return false;
	}
	if(city == ""){
		alert("影院所属城市不能为空");
		return false;
	}
	if(type == ""){
		alert("影院类型不能为空");
		return false;
	}
    
	$.ajax({
        url : "/cinker/cinkerMaintain/saveCinema",
        type : "POST",
        contentType : "application/json;charset=UTF-8",
        data : JSON.stringify(cinema),
        success : function(result) {
           alert("save success!");
           window.location.href = '/cinker/cinkerMaintain/getCinemaList';
        },
        error:function(result){
            alert("Sorry,save fail!");
        }
    });
}

function insertCinema(){	
	var cinema = {};
	cinema['id'] = document.getElementById("id").value;
	var cinemaId = cinema['cinemaId'] = document.getElementById("cinemaId").value;
	var name = cinema['name'] = document.getElementById("name").value; 
	var address = cinema['address'] = document.getElementById("address").value;
	var city = cinema['city'] = document.getElementById("city").value; 
	var type = cinema['type'] = document.getElementById("type").value; 
	if(cinemaId == ""){
		alert("影院ID不能为空");
		return false;
	}
	if(name == ""){
		alert("影院名称不能为空");
		return false;
	}
	if(address == ""){
		alert("影院地址不能为空");
		return false;
	}
	if(city == ""){
		alert("影院所属城市不能为空");
		return false;
	}
	if(type == ""){
		alert("影院类型不能为空");
		return false;
	}
    
	$.ajax({
        url : "/cinker/cinkerMaintain/insertCinema",
        type : "POST",
        contentType : "application/json;charset=UTF-8",
        data : JSON.stringify(cinema),
        success : function(result) {
           alert("save success!");
           window.location.href = '/cinker/cinkerMaintain/getCinemaList';
        },
        error:function(result){
            alert("Sorry,save fail!");
        }
    });
}