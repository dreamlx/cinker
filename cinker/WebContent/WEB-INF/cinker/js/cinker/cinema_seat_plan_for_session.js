
	var _seatsInfoObj,_seatRows,_selectedSeatList;
	var _ColumnCount;
	var _rowCount;
	var _selectedSeats = [];
	
	var _seatMap = {};
	var _areaCategoryCode;
	var _useFreeTicketFlag = 0;

	var _paymentMethod='wechat';

	function getSeatsInfo(cinemaId,sessionID){
		$.ajax({
			method: "GET",
			url: '/cinker/film/getSetPlan/' + cinemaId + '/' + sessionID,
			dataType: 'json'
		}).done(function(data){
			if(data.ResponseCode!=0){
				if(data.ResponseCode == 67){
					alert('对不起该场次已售罄');
				}else{
					alert('error code: '+data.ResponseCode);
				}
				
				return
			}
			_seatsInfoObj = data;
			_ColumnCount = _seatsInfoObj.SeatLayoutData.Areas[0].ColumnCount;
			_rowCount = _seatsInfoObj.SeatLayoutData.Areas[0].RowCount;
			_seatRows = _seatsInfoObj.SeatLayoutData.Areas[0].Rows;
			_areaCategoryCode = _seatsInfoObj.SeatLayoutData.Areas[0].AreaCategoryCode;
			
			buildMap();
            buildSeatsHtml();
            
            $('.xzspan').click(function(evt){
                if($(this).hasClass('xzspan22')){
                    return;
                }
                
                var seatId = $(this).data('seatid');
                
                if(!$(this).hasClass('xzspan33')){
                	
                	if(_selectedSeats.length>(_totalLimit-1)){
                    	return;
                    }

                    //鍒ゆ柇鏄惁鏈夐噸澶嶉�搴�                    
                	var checked = false;
			        $.each(_selectedSeats, function(k,v){
			        	if(v.id == seatId){
			        		checked = true;
			        		return false;
			        	}
			        });
			        if(checked){
			        	return;
			        }   
                    addSeat(seatId, $(this));
             
                    
                }else{
                	cancelSeat(seatId, $(this));
                	
                }
                updatePrice();
            });
		});
	}
	
	// user add a seat
	function addSeat(seatId, $seatDom){
		var seatObj = _seatMap[seatId];
        
		$seatDom.addClass('xzspan33');
    	$('.selectedSeats').append('<section id="st'+seatId+'" class="pz">'+seatObj.PhysicalName+'排'+seatObj.Id+'座</section>');

    	_selectedSeats.push({
        	id:seatId,
        	obj:{
    			areaCategoryCode:_areaCategoryCode, 
    			areaNumber : seatObj.Position.AreaNumber,             			
    			rowIndex : parseInt(seatObj.Position.RowIndex), 
    			columnIndex : parseInt(seatObj.Position.ColumnIndex),
    			physicalName : isNaN(parseInt(seatObj.PhysicalName))?seatObj.PhysicalName:parseInt(seatObj.PhysicalName),
    			id:parseInt(seatObj.Id)
    		}
        });
        
	}

	// user cancel a seat
	function cancelSeat(seatId, $seatDom){
		$seatDom.removeClass('xzspan33');
        $('.selectedSeats #st'+seatId).remove();
        $.each(_selectedSeats, function(k,v){
        	if(_selectedSeats[k].id == seatId){
        		_selectedSeats.splice(k, 1);
        		return false;
        	}
        });
	}
	

	
	function buildMap(){
		for(physicslLine in _seatRows){
			var seats = _seatRows[physicslLine].Seats;
			for(seat in seats){
				// console.log(seats[seat].Id);
				var seatObj = seats[seat];
				seatObj.PhysicalName = _seatRows[physicslLine].PhysicalName;
				var columnIndex = seatObj.Position.ColumnIndex;
				var rowIndex = seatObj.Position.RowIndex;
				columnIndex = (columnIndex<10)?"0"+columnIndex:''+columnIndex;
				rowIndex = (rowIndex<10)?"0"+rowIndex:''+rowIndex;
				_seatMap[rowIndex+columnIndex] = seatObj;
			}
		}
		// var map = {}
	}
	

	function buildSeatsHtml(){
		var html = $('<div class="seats_wapper"></div>');
		for(var i=0;i<_rowCount;i++){
			var rowHtml = $('<section class="xz_line"></section>');
			if(_seatRows[i].PhysicalName == null){
				html.append(rowHtml);
				continue;
			}
			rowHtml.append('<section class="xznumber">'+_seatRows[i].PhysicalName+'</section>');
			for(var j=0;j<_ColumnCount;j++){
				var columnIndex = (j<10)?"0"+j:''+j;
				var rowIndex = (i<10)?"0"+i:''+i;
				var seatId = rowIndex+columnIndex;

				var columnHtml;

				if(_seatMap[seatId] != null){
					columnHtml = $('<section data-seatid="'+seatId+'" id="sd'+seatId+'" class="xzspan"><span>&nbsp;</span></section>');

					var status = _seatMap[seatId].Status;
					if(status==1){
						columnHtml.addClass('xzspan22');
					}else if(status == 5){
						columnHtml.addClass('xzspan22');
						//columnHtml.find('span').html('X');
					}
				}else{
					columnHtml = $('<section class="xzspan1">&nbsp;</section>');
				}

				rowHtml.append(columnHtml);

				if(j+1 == _ColumnCount){
					rowHtml.append('<section class="xznumber">'+_seatRows[i].PhysicalName+'</section>');
				}
			}
			html.append(rowHtml);
		}
		var divWidth = (_ColumnCount+2)*30;
		
		$("#seats").append($('<div style="width:'+divWidth+'px"></div>').append(html));
	}
	
	function updatePrice(){
		var price = 0;
		if(_paymentMethod=='wechat'){
			//price = _ogPriceInCents;
			price = _priceInCents;
		}else{
			price = _priceInCents;
		}
		var priceInYuan = price / 100;
		var totalPrice = priceInYuan * _selectedSeats.length;
		
		$('.moneybtn').html('&yen;'+totalPrice);
		$('#orderCount').html('¥'+priceInYuan+'x '+_selectedSeats.length);
		$('#orderTotalPrice').html(''+totalPrice);
	}

	
	function updateFreeTicketFlg(flag){
		if(flag){
			updatePayMethod('free');
		}
		if(flag){
			$('section.pay').removeClass('clkpay');
			$('section.freepay').removeClass('hidden');
			$('section.freepay').addClass('clkpay');
		}
	}
	
	function updatePayMethod(method){
		if(method=='wechat'){
			//wechat pay
			_useFreeTicketFlag = 0;
			_paymentMethod = 'wechat';
		}else if(method == 'member'){
			//member pay
			_useFreeTicketFlag = 0;
			_paymentMethod = 'member';
		}else{
			//free pay
			_useFreeTicketFlag = 1;
			_paymentMethod = 'free';
		}

	}
	
	// complete the step of choose seat and go to step two
	function seatComplate(){
		if(_selectedSeats.length>_totalLimit){
			return false;
		}
		// check the rules of choose
		var hasTiaoZuo = false;
		$.each(_selectedSeats, function(k,v){
			var seatObj = _selectedSeats[k].obj;
			$.each(_selectedSeats, function(i,o){
				if(seatObj.physicalName == o.obj.physicalName){
					var d = parseInt(seatObj.id) - parseInt(o.obj.id);
					if(Math.abs(d) == 2){
						var nextSeatId;			
						if(d>0){
							nextSeatId = parseInt(seatObj.id)-1;						
						}else{
							nextSeatId = parseInt(seatObj.id)+1;
						}
						var isSeatSelected = false;
						$.each(_selectedSeats, function(j,s){
							if(s.obj.id == nextSeatId){
								isSeatSelected = true;
							}
						});
						if(!isSeatSelected){
							hasTiaoZuo = true;
							return
						}
					}
				}
			});
        });
		if(hasTiaoZuo){
			alert('请不要选择分开的座位');
			return false;
		}
		
		_selectedSeatList = new Array();
		var selectedSeatStr ='';
		$.each(_selectedSeats, function(k,v){
			_selectedSeatList.push(_selectedSeats[k].obj);
			selectedSeatStr += _selectedSeats[k].obj.physicalName+"排"+_selectedSeats[k].obj.id+"座, ";
        });
		

		$('#seat_result').html(selectedSeatStr);
		$('#steptwo').show();
		$('#stepone').hide();
		
		//history.replaceState({}, "seat_plan", "/cinker/film/getSessionForSeat");
//		history.pushState({},'order','/cinker/film/order');
		//getFreeTickets();
	}
	
	function getFreeTickets(){
		var freeMemberItem = {
				userSessionId : _userSessionId,
	        	memberLevelId:_memberLevelId,
	        	cinemaType:_cinemaType
		};
		$.ajax({
			//url : "/cinker/film/addOrder",
			url : "/cinker/film/getMemberItems",
	        type : "POST",
	        contentType : "application/json;charset=UTF-8",
	        data : JSON.stringify(freeMemberItem),
	        success : function(result) {
	           console.log(result);
	           if(result.qtyAvailable>0){
	        	   updateFreeTicketFlg(true);
	        	   updatePrice();
	        	   
	           }
	        },
	        error:function(result){
	            alert("Sorry,get free ticket error!");
	        }
	    });
	}
	
	// update ticketTypeList
	function updateTicketTypes(){
		var price = 0;
		var typeCode = '';
		if(_paymentMethod=='wechat' && parseInt(_memberLevelId)<1){
			//only guest use the orginal price to pay
			price = _ogPriceInCents;
			typeCode = _ogTicketTypeCode;
		}else{
			price = _priceInCents;
			typeCode = _ticketTypeCode;
		}
		
		var count = _selectedSeats.length;
		return  [{
			ticketTypeCode: typeCode,
			qty: count, 
			priceInCents: price, 
			bookingFeeOverride:0 
		}];
	}
	
	function addOrder(){
		var filmOrder = {};
		filmOrder['freeRightFlg'] = _useFreeTicketFlag;
		filmOrder['cinemaType'] = _cinemaType;
		filmOrder['memberLevelId'] = _memberLevelId;
		filmOrder['scheduledFilmId'] = _scheduledFilmId ; 
		filmOrder['filmTitle'] = $('#filmTitle').val();
		filmOrder['cinemaId'] = _cinemaId ; 
		filmOrder['sessionId'] = _sessionId; 
		filmOrder['sessionName'] = $('#sessionName').val();
		filmOrder['areaCategoryCode'] = _areaCategoryCode; 
		filmOrder['showTime'] = $('#showTime').val(); 
		filmOrder['ticketTypes'] = updateTicketTypes();
		filmOrder['selectedSeats'] = _selectedSeatList;
		filmOrder['sessionShowTime'] = $('#sessionShowTime').val();
		filmOrder['orderNumber'] = _userSessionId;
		
		var payMethod = 'pay';
	    
		$.ajax({
			url : "/cinker/film/addOrder",			
	        type : "POST",
	        dataType : "json",
	        contentType : "application/json;charset=UTF-8",
	        data : JSON.stringify(filmOrder),
	        success : function(result) {
	           var resultObject = result.results;
	           if(resultObject.Result == '0'){
	        	   console.log(result);
	        	   if(_paymentMethod != 'wechat'){
	        		   payMethod = 'freePay';
	        	   }
	        	   window.location.href = '/cinker/wechat/'+payMethod+'?outTradeNo='+result.orderNumber+
	        	   	'&filmName='+result.filmTitle+
	        	   	'&sessionID='+result.sessionId+
	        	   	'&totalPrice='+result.totalValueCents;
	           }else{
	        	   alert('下单数据错误，错误原因'+ resultObject.ErrorDescription);
	        	   location.reload();
	           }
	           
	        },
	        error:function(result){
	            alert("Sorry,you are make a error!");
	        }
	    });
	}  