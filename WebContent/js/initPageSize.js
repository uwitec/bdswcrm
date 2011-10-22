$(document).ready(function(){
	if(document.getElementById("divContent") != null){
		if(parent.leftWinHeight != null){
			document.getElementById("divContent").style.height = parent.leftWinHeight;
		}else{
			document.getElementById("divContent").style.height = $(window).height() + "px";
		}
	}
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