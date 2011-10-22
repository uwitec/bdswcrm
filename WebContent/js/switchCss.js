$(document).ready(function(){
	if(document.getElementById("divContent") != null){
		if(parent.leftWinHeight != null){
			document.getElementById("divContent").style.height = parent.leftWinHeight;
		}else{
			document.getElementById("divContent").style.height = $(window).height() + "px";
		}
	}
	$(".stripe tbody tr").mouseover(function(){
		//如果鼠标移到class为stripe的表格的tr上时，执行函数
		$(this).addClass("over");}).mouseout(function(){
			//给这行添加class值为over，并且当鼠标一出该行时执行函数
			$(this).removeClass("over");}) //移除该行的class
});
$(window).resize(function(){
	if(document.getElementById("divContent") != null){
		if(parent.leftWinHeight != null){
			document.getElementById("divContent").style.height = parent.leftWinHeight;
		}else{
			document.getElementById("divContent").style.height = $(window).height() + "px";
		}
	}
});