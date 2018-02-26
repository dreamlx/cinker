function saveActivityFilm(){	
	var activityFilm = {};
	activityFilm['id'] = document.getElementById("id").value;
	var filmId = activityFilm['filmId'] = document.getElementById("filmId").value;
	var cinemaId = activityFilm['cinemaId'] = document.getElementById("cinemaId").value; 
	var sessionId = activityFilm['sessionId'] = document.getElementById("sessionId").value;
	var sessionName = activityFilm['sessionName'] = document.getElementById("sessionName").value; 
	var filmTitle = activityFilm['filmTitle'] = document.getElementById("filmTitle").value; 
	var totalValueCents = activityFilm['totalValueCents'] = document.getElementById("totalValueCents").value; 
	var total = activityFilm['total'] = document.getElementById("total").value;
	var quaty = activityFilm['quaty'] = document.getElementById("quaty").value;
	var sessionTime = activityFilm['sessionTime'] = document.getElementById("sessionTime").value;
	var startSessionTime = activityFilm['startSessionTime'] = document.getElementById("startSessionTime").value;
	var activityFilmType = activityFilm['activityFilmType'] = document.getElementById("activityFilmType").value;
	var url = activityFilm['url'] = document.getElementById("url").value;
	var isformember = activityFilm['isForUpperMember'] = document.getElementById("isformember").value;
	
	$.ajax({
        url : "/cinker/cinkerMaintain/updateActivityFilm",
        type : "POST",
        contentType : "application/json;charset=UTF-8",
        data : JSON.stringify(activityFilm),
        success : function(result) {
           alert("update success!");
           //window.location.href = '/cinker/cinkerMaintain/getActivityFilmInfo?page=1';
           window.location.reload();
        },
        error:function(result){
            alert("Sorry,update fail!");
        }
    });
}

function insertActivityFilm(){	
	var activityFilm = {};
	activityFilm['id'] = document.getElementById("id").value;
	var filmId = activityFilm['filmId'] = document.getElementById("filmId").value;
	var cinemaId = activityFilm['cinemaId'] = document.getElementById("cinemaId").value; 
	var sessionId = activityFilm['sessionId'] = document.getElementById("sessionId").value;
	var sessionName = activityFilm['sessionName'] = document.getElementById("sessionName").value; 
	var filmTitle = activityFilm['filmTitle'] = document.getElementById("filmTitle").value; 
	var totalValueCents = activityFilm['totalValueCents'] = document.getElementById("totalValueCents").value; 
	var total = activityFilm['total'] = document.getElementById("total").value;
	var quaty = activityFilm['quaty'] = document.getElementById("quaty").value;
	var sessionTime = activityFilm['sessionTime'] = document.getElementById("sessionTime").value;
	var startSessionTime = activityFilm['startSessionTime'] = document.getElementById("startSessionTime").value;
	var activityFilmType = activityFilm['activityFilmType'] = document.getElementById("activityFilmType").value;
	var url = activityFilm['url'] = document.getElementById("url").value;
	
	$.ajax({
        url : "/cinker/cinkerMaintain/insertActivityFilm",
        type : "POST",
        contentType : "application/json;charset=UTF-8",
        data : JSON.stringify(activityFilm),
        success : function(result) {
           alert("save success!");
           window.location.href = '/cinker/cinkerMaintain/getActivityFilmInfo?page=1';
        },
        error:function(result){
            alert("Sorry,save fail!");
        }
    });
}