function add(){
	var chineseName = document.getElementById("chineseName").value; 
	var englishName = document.getElementById("englishName").value; 
	var surfaceImage = document.getElementById("surfaceImage").value;
	var surfaceImageUrl = document.getElementById("surfaceImageUrl").value;
	var graphTheories = document.getElementById("graphTheories").value;
	var imageUrl = document.getElementById("imageUrl").value;
	var director = document.getElementById("director").value; 
	var scriptWriter = document.getElementById("scriptWriter").value; 
	var showTime = document.getElementById("showTime").value;
	var runTime = document.getElementById("runTime").value; 
	var starringActor = document.getElementById("starringActor").value; 
	var filmType = document.getElementById("filmType").value; 
	var language = document.getElementById("language").value;
	var filmId = document.getElementById("filmId").value;

	if(filmId == ""){
		alert("影片编码不能为空");
		return false;
	}
	if(chineseName == ""){
		alert("影片中文名不能为空");
		return false;
	}
	
	if(englishName == ""){
		alert("影片英文名不能为空");
		return false;
	}
	
	if(surfaceImageUrl == "" & surfaceImage == ""){
		alert("海报图片不能为空");
		return false;
	}
	if(imageUrl == "" & graphTheories == ""){
		alert("详情图片不能为空");
		return false;
	}
	if(director == ""){
		alert("导演不能为空");
		return false;
	}
	if(scriptWriter == ""){
		alert("编剧不能为空");
		return false;
	}
	if(showTime == ""){
		alert("上映时间不能为空");
		return false;
	}
	if(runTime == ""){
		alert("播放时长不能为空");
		return false;
	}
	var regRunTime = new RegExp("^[0-9]*$");
	if(!regRunTime.test(runTime)){
		alert("播放时长只能输入数字");
		return false;
	}
	if(starringActor == ""){
		alert("演员不能为空");
		return false;
	}
	if(filmType == ""){
		alert("影片类型不能为空");
		return false;
	}
	if(language == ""){
		alert("简介不能为空");
		return false;
	}
}


function preview(file)  {  
	var prevDiv = document.getElementById('preview');  
	if (file.files && file.files[0])  {  
		var reader = new FileReader();  
		reader.onload = function(evt){  
			prevDiv.innerHTML = '<img src="' + evt.target.result + '" width="200"/>';  
			};   
			reader.readAsDataURL(file.files[0]);  
		}else{  
			prevDiv.innerHTML = '<div class="img" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src=\'' + file.value + '\'"></div>';  
		}  
	}  

function setImagePreviews(avalue) {
    var docObj = document.getElementById("graphTheories");
    var dd = document.getElementById("prevview");
    dd.innerHTML = "";
    var fileList = docObj.files;
    for (var i = 0; i < fileList.length; i++) {            
        dd.innerHTML += "<div style='float:left' > <img id='img" + i + "'  /> </div>";
        var imgObjPreview = document.getElementById("img"+i); 
        if (docObj.files && docObj.files[i]) {
            //火狐下，直接设img属性
            imgObjPreview.style.display = 'block';
            imgObjPreview.style.width = '150px';
            //imgObjPreview.src = docObj.files[0].getAsDataURL();
            //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
            imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]);
        }
        else {
            //IE下，使用滤镜
            docObj.select();
            var imgSrc = document.selection.createRange().text;
            var localImagId = document.getElementById("img" + i);
            //必须设置初始大小
            localImagId.style.width = "150px";
            //图片异常的捕捉，防止用户修改后缀来伪造图片
            try {
                localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
            }
            catch (e) {
                alert("您上传的图片格式不正确，请重新选择!");
                return false;
            }
            imgObjPreview.style.display = 'none';
            document.selection.empty();
        }
    }  
    return true;
}