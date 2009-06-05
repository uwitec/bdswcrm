/**
 * @author Administrator
 */

function MenusInit(){
	var menus = document.getElementById("menus").getElementsByTagName("li");
	var count = menus.length;
	for (var i = 0; i < count; i++) {
		menus[i].onmouseover = showCont;
		menus[i].onmouseout = hiddenCont;
	}
	
	var menus2 = document.getElementById("menus2").getElementsByTagName("li");
	var count2 = menus2.length;
	for (var k = 0; k < count2; k++) {
		menus2[k].onmouseover = showCont;
		menus2[k].onmouseout = hiddenCont;
	}
}

var hideDelayTimer = null;
var hideDelay = 1000;
var currentMenuItem = null;

function showCont()
{	
	
	this.className='ActiveMenuItem';
	if (currentMenuItem)
	{
		hiddenSetTime(currentMenuItem.id);
		window.clearTimeout(hideDelayTimer);
		currentMenuItem = null;
	}		
		
	var menuCont = document.getElementById(this.id+"_cont");
	menuCont.className = "show";
	
	try{		
		menuCont.style.left =this.offsetWidth;
	}
	finally{

		currentMenuItem = this;		
	}

}

function hiddenCont(){	
	  this.className='MenuItem';
	  hideDelayTimer = window.setTimeout("hiddenSetTime('"+this.id+"')",hideDelay);
}

function hiddenSetTime(objId){
	var menuCont = document.getElementById(objId + "_cont");
	menuCont.className = "hidden";

}

function hiddenMenuCont(e)
{
	e.className = "hidden";		
}

function showMenuCont(e)
{
	window.clearTimeout(hideDelayTimer);
	e.className = "show";
}