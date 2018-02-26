function saveScreen(){	
	var screen = {};
	screen['id'] = document.getElementById("id").value;
	var screenName = screen['screenName'] = document.getElementById("screenName").value; 
	var screenNumber = screen['screenNumber'] = document.getElementById("screenNumber").value;
	var cinemaId = screen['cinemaId'] = document.getElementById("cinemaId").value;
	if(cinemaId == ""){
		alert("影院ID为空");
		return false;
	}
	if(screenName == ""){
		alert("影厅名称为空");
		return false;
	}
	if(screenNumber == ""){
		alert("影厅号码为空");
		return false;
	}
    
	$.ajax({
        url : "/cinker/cinkerMaintain/saveScreen",
        type : "POST",
        contentType : "application/json;charset=UTF-8",
        data : JSON.stringify(screen),
        success : function(result) {
           alert("save success!");
           window.location.href = '/cinker/cinkerMaintain/getScreenList';
        },
        error:function(result){
            alert("Sorry,save fail!");
        }
    });
}

function insertScreen(){	
	var screen = {};
	screen['id'] = document.getElementById("id").value;
	var screenName = screen['screenName'] = document.getElementById("screenName").value; 
	var screenNumber = screen['screenNumber'] = document.getElementById("screenNumber").value;
	var cinemaId = screen['cinemaId'] = document.getElementById("cinemaId").value;
	if(cinemaId == ""){
		alert("影院ID为空");
		return false;
	}
	if(screenName == ""){
		alert("影厅名称为空");
		return false;
	}
	if(screenNumber == ""){
		alert("影厅号码为空");
		return false;
	}
    
	$.ajax({
        url : "/cinker/cinkerMaintain/insertScreen",
        type : "POST",
        contentType : "application/json;charset=UTF-8",
        data : JSON.stringify(screen),
        success : function(result) {
           alert("save success!");
           window.location.href = '/cinker/cinkerMaintain/getScreenList';
        },
        error:function(result){
            alert("Sorry,save fail!");
        }
    });
}