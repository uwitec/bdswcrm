$(document).ready(function(){
	if(document.getElementById("divContent") != null){
		document.getElementById("divContent").style.height = $(window).height() + "px";
	}
});
$(window).resize(function(){
	if(document.getElementById("divContent") != null){
		document.getElementById("divContent").style.height = $(window).height() + "px";
	}
});