

//--------------------------------------------信息滚动-----------------------
var scrollElem;
var stopscroll;
var stoptime;
var preTop;
var leftElem;
var currentTop;
var marqueesHeight;

function marque(width,height,marqueName,marqueCName)
{
  		marqueesHeight = height;
  		stopscroll     = false;
  		scrollElem = document.getElementById(marqueName);
  		with(scrollElem){
			style.width     = width;
			style.height    = marqueesHeight-1;
			style.overflow  = 'hidden';
			noWrap          = true;
		}	
  		scrollElem.onmouseover = new Function('stopscroll = true');
		scrollElem.onmouseout  = new Function('stopscroll = false');
		preTop     = 0; 
		currentTop = 0; 
		stoptime   = 0;

		leftElem = document.getElementById(marqueCName);
		scrollElem.appendChild(leftElem.cloneNode(true));
  
  		init_srolltext();
  		

}

function init_srolltext()
{
  scrollElem.scrollTop = 0;
  setInterval('scrollUp()', 25);
}

function scrollUp()
{
  if(stopscroll) return;
  currentTop += 1;

  if(currentTop == marqueesHeight+1) 
  {		
    stoptime += 1;
    currentTop -= 1;
  	if(stoptime >= (marqueesHeight)*5) {//停顿时间
  		   
      		currentTop = 0;
      		stoptime = 0;     
      		scrollElem.scrollTop += 1; 		
  	}
  }
  else
  {
    preTop = scrollElem.scrollTop;
    scrollElem.scrollTop += 1;
    if(preTop == scrollElem.scrollTop)
    {
      scrollElem.scrollTop = marqueesHeight;
      scrollElem.scrollTop += 1;
      currentTop = 0;
    }
  }
}
//******************************************************************************************



//--------------------------------------------常见问题信息滚动2-----------------------
var scrollElem_A;
var stopscroll_A;
var stoptime_A;
var preTop_A;
var leftElem_A;
var currentTop_A;
var marqueesHeight_A;

function marque_A(width,height,marqueName,marqueCName)
{
  		marqueesHeight_A = height;
  		stopscroll_A     = false;
  		scrollElem_A = document.getElementById(marqueName);
  		with(scrollElem_A){
			style.width     = width;
			style.height    = marqueesHeight_A;
			style.overflow  = 'hidden';
			noWrap          = true;
		}	
  		scrollElem_A.onmouseover = new Function('stopscroll_A = true');
		scrollElem_A.onmouseout  = new Function('stopscroll_A = false');
		preTop_A     = 0; 
		currentTop_A = 0; 
		stoptime_A   = 0;

		leftElem_A = document.getElementById(marqueCName);
		scrollElem_A.appendChild(leftElem_A.cloneNode(true));
  
  		init_srolltext_A();
  		

}

function init_srolltext_A()
{
  scrollElem_A.scrollTop = 0;
  setInterval('scrollUp_A()', 50);
}

function scrollUp_A()
{
  if(stopscroll_A) return;
  currentTop_A += 1;
	 
  if(currentTop_A >= marqueesHeight_A+1) 
  {		
    stoptime_A += 1;
    currentTop_A -= 1;    
  	if(stoptime_A >= 1) {//停顿时间
      				
      		currentTop_A = 0;
      		stoptime_A = 0;
  	}
  }
  else
  {
    preTop_A = scrollElem_A.scrollTop;
    scrollElem_A.scrollTop += 1;
    if(preTop_A == scrollElem_A.scrollTop)
    {
      scrollElem_A.scrollTop = marqueesHeight_A;
      scrollElem_A.scrollTop += 1;
     
    }
  }
}
//******************************************************************************************
