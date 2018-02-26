function saveFilmOrders(){	
	var filmOrders = {};
	filmOrders['id'] = document.getElementById("id").value;	
	filmOrders['scheduledFilmId'] = document.getElementById("scheduledFilmId").value;
	filmOrders['orderNumber'] = document.getElementById("orderNumber").value;
	filmOrders['filmTitle'] = document.getElementById("filmTitle").value;
	filmOrders['cinemaId'] = document.getElementById("cinemaId").value; 
	filmOrders['cinemaName'] = document.getElementById("cinemaName").value; 
	filmOrders['sessionId'] = document.getElementById("sessionId").value; 
	filmOrders['sessionName'] = document.getElementById("sessionName").value; 
	filmOrders['showTime'] = document.getElementById("showTime").value; 
	filmOrders['areaCategoryCode'] = document.getElementById("areaCategoryCode").value; 
	filmOrders['totalOrderCount'] = document.getElementById("totalOrderCount").value;
	filmOrders['totalValueCents'] = document.getElementById("totalValueCents").value; 
	filmOrders['status'] = document.getElementById("status").value; 
	filmOrders['startTime'] = document.getElementById("startTime").value; 
	filmOrders['endTime'] = document.getElementById("endTime").value; 
	filmOrders['userNumber'] = document.getElementById("userNumber").value; 
	filmOrders['paymentID'] = document.getElementById("paymentID").value; 
	filmOrders['bookingNumber'] = document.getElementById("bookingNumber").value; 
	filmOrders['bookingID'] = document.getElementById("bookingID").value;
	filmOrders['seats'] = document.getElementById("seats").value;
	
    
	$.ajax({
        url : "/cinker/cinkerMaintain/saveFilmOrders",
        type : "POST",
        contentType : "application/json;charset=UTF-8",
        data : JSON.stringify(filmOrders),
        success : function(result) {
           alert("save success!");
           window.location.href = '/cinker/cinkerMaintain/getFilmOrder';
        },
        error:function(result){
            alert("Sorry,save fail!");
        }
    });
}

function insertFilmOrders(){	
	var filmOrders = {};
	filmOrders['id'] = document.getElementById("id").value;
	filmOrders['scheduledFilmId'] = document.getElementById("scheduledFilmId").value;
	filmOrders['orderNumber'] = document.getElementById("orderNumber").value;
	filmOrders['filmTitle'] = document.getElementById("filmTitle").value;
	filmOrders['cinemaId'] = document.getElementById("cinemaId").value; 
	filmOrders['cinemaName'] = document.getElementById("cinemaName").value; 
	filmOrders['sessionId'] = document.getElementById("sessionId").value; 
	filmOrders['sessionName'] = document.getElementById("sessionName").value; 
	filmOrders['showTime'] = document.getElementById("showTime").value; 
	filmOrders['areaCategoryCode'] = document.getElementById("areaCategoryCode").value; 
	filmOrders['totalOrderCount'] = document.getElementById("totalOrderCount").value;
	filmOrders['totalValueCents'] = document.getElementById("totalValueCents").value; 
	filmOrders['status'] = document.getElementById("status").value; 
	filmOrders['startTime'] = document.getElementById("startTime").value; 
	filmOrders['endTime'] = document.getElementById("endTime").value; 
	filmOrders['userNumber'] = document.getElementById("userNumber").value; 
	filmOrders['paymentID'] = document.getElementById("paymentID").value; 
	filmOrders['bookingNumber'] = document.getElementById("bookingNumber").value; 
	filmOrders['bookingID'] = document.getElementById("bookingID").value;
	filmOrders['seats'] = document.getElementById("seats").value;
	
    
	$.ajax({
        url : "/cinker/cinkerMaintain/insertFilmOrders",
        type : "POST",
        contentType : "application/json;charset=UTF-8",
        data : JSON.stringify(filmOrders),
        success : function(result) {
           alert("save success!");
           window.location.href = '/cinker/cinkerMaintain/getFilmOrder';
        },
        error:function(result){
            alert("Sorry,save fail!");
        }
    });
}