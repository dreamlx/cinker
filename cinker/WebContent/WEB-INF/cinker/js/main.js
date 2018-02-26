//瀵懓鍤仦锟介崗鐓庮啇閻忣偆瀚勯妴涓8  
function showMask(){  
	$("#mask").css("height",$(document).height());  
	$("#mask").css("width",$(document).width());  
	$("#mask").show();  
}  
function letDivCenter(divName){   
	var top = ($(window).height() - $(divName).height())/2-50;   
	var left = ($(window).width() - $(divName).width())/2;   
	var scrollTop = $(document).scrollTop();   
	var scrollLeft = $(document).scrollLeft();   
	$(divName).css( { position : 'absolute', 'top' : top + scrollTop, left : left + scrollLeft } ).show();  
}  
function showAll(divName){  
	showMask();  
	letDivCenter(divName);  
}  
//閸忔娊妫寸仦锟�
$("#mask").click(function(){
	$("#cpzs-cont").hide();	
	$("#dtgz-cont").hide();	
	$("#dtgz-cont").hide();	
	$("#ans-cont1").hide();	
	$("#ans-cont2").hide();	
	$("#ans-cont3").hide();	
	$("#qrxx-cont").hide();
	$("#dtgz-cont").hide();	
	$("#xbtc-cont").hide();	
	$(this).hide();	
})  
$("#sklm").click(function(){
	var cls = $(this).attr('class');
	if(cls == 'fnav_li'){		
		$(".tg-div").show();
		$("#mask").show();
		$(this).addClass('checked');
	}else{
		$("#mask").hide();
		$(".tg-div").hide();
		$(this).removeClass('checked');
	}
})
$("#getcode").click(function(){
	$(this).text('');
})



function closed(){
	$("#cpzs-cont").hide();	
	$("#dtgz-cont").hide();	
	$("#ans-cont1").hide();	
	$("#ans-cont2").hide();	
	$("#ans-cont3").hide();	
	$("#qrxx-cont").hide();	
	$("#dtgz-cont").hide();	
	$("#xbtc-cont").hide();	
	$("#mask").hide();	
}


//闁銆嶉崡锛勬暏閹磋渹鑵戣箛锟�
$(".md_title li").click(function(){
	 var _idx = $(this).index();
	 $(this).addClass("clk").siblings().removeClass("clk");
	 $(".md_main > div").eq(_idx).show().siblings().hide();
})

$(".tm_title").find("span").click(function(){
	cls = $(this).attr('class');
	if(cls=='clk'){
		$(this).removeClass('clk');			
	}else{
		$(this).addClass('clk');		
	}	
})

	function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //鏋勯�涓�釜鍚湁鐩爣鍙傛暟鐨勬鍒欒〃杈惧紡瀵硅薄
        var r = window.location.search.substr(1).match(reg);  //鍖归厤鐩爣鍙傛暟
        if (r != null) return decodeURI(r[2]); return null; //杩斿洖鍙傛暟鍊�
    }

	Date.prototype.pattern=function(fmt) {         
	    var o = {         
	    "M+" : this.getMonth()+1, //鏈堜唤         
	    "d+" : this.getDate(), //鏃�        
	    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //灏忔椂         
	    "H+" : this.getHours(), //灏忔椂         
	    "m+" : this.getMinutes(), //鍒�        
	    "s+" : this.getSeconds(), //绉�        
	    "q+" : Math.floor((this.getMonth()+3)/3), //瀛ｅ害         
	    "S" : this.getMilliseconds() //姣         
	    };         
	    var week = {         
	    "0" : "/u65e5",         
	    "1" : "/u4e00",         
	    "2" : "/u4e8c",         
	    "3" : "/u4e09",         
	    "4" : "/u56db",         
	    "5" : "/u4e94",         
	    "6" : "/u516d"        
	    };         
	    if(/(y+)/.test(fmt)){         
	        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
	    }         
	    if(/(E+)/.test(fmt)){         
	        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);         
	    }         
	    for(var k in o){         
	        if(new RegExp("("+ k +")").test(fmt)){         
	            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
	        }         
	    }         
	    return fmt;         
	}     
