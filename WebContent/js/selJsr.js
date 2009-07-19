//在body的onload事件中调用 initFzrTip();
//earchBrand  查询相近的经手人
//showResponse 展示
//move 上下事件
//down 鼠标按下事件
//setValue 鼠标离开事件
//显示框的ID为 brand
//隐藏框的ID为 fzr

var tip = "";
function searchBrand(){    
	var url = 'queryJsrTipInfo.html';
	var params = "paramValue=" + $F('brand');
	var myAjax = new Ajax.Request(
	url,
	{
		method:'post',
		parameters: params,
		onComplete: showResponse,
		asynchronous:true
	});
}

var jsrlists = new Array();

function showResponse(originalRequest){   
	if(originalRequest.responseText.trim() == ""){
		var bt = $("brandTip");
		bt.innerHTML = "";
		Element.hide('brandTip');	
		return false;
	}
	var brandList = originalRequest.responseText.split("%");
	if (brandList != null && brandList.length > 0){
		var bt = $("brandTip");
		var s="";
		var flog=0;
		for(var i = 0 ; i <  brandList.length; i++){
		   if(flog==30) {
		     break;
		   }
		   var curBrand = brandList[i].split("$");
		   jsrlists[curBrand[1].trim()] = curBrand[0].trim();
		   
		   s += "<div onmouseover=\"this.className='selectTip';style.cursor='default'\"  onmouseout=\"this.className=null; style.cursor='default'\">" + curBrand[1].trim() + "</div>";
		   flog++;
		}
		bt.innerHTML=s;
		 
		if( tip != $("brand").value) {
			Element.show('brandTip');
		}
	} else {
		var bt = $("brandTip");
		bt.innerHTML = "";
		Element.hide('brandTip');
	}
}
function move(event) {
	 var srcEl = Event.element(event);
	 var tipEl = $(srcEl.id + "Tip");
     var a = tipEl.childNodes;
	 if (tipEl.style.display == "" ) {
		if(event.keyCode == 40 ) {            
			if (tipEl.childNodes.length >= 1) {
				var bList = tipEl.childNodes;
				 
				if(tipEl.lastChild.className=="selectTip") {
				    tipEl.firstChild.className = "selectTip";
					tipEl.lastChild.className = "null";
					return ;
				}
				var s=0;
				for (var i = 0 ; i < bList.length; i++) {
					if (bList[i].className == "selectTip") {
					    s++;
						bList[i + 1].className = "selectTip";
						bList[i].className = "null";
						return ;
					}
				}
				if(s==0) {
				  tipEl.firstChild.className = "selectTip";
				}
			}

		} else if(event.keyCode == 38) {
		   
			if (tipEl.childNodes.length >= 1) {
			   
			   	if(tipEl.firstChild.className == "selectTip") {
					tipEl.lastChild.className = "selectTip";
					tipEl.firstChild.className = "null";
					return ;
				}
				var s=0;
				var bList = tipEl.childNodes;
				for (var i = 0 ; i < bList.length ; i ++) {
					if (bList[i].className == "selectTip") {
					   s++;
						bList[i - 1].className = "selectTip";
						bList[i].className = "null";
						return ;
					}
					 
				}
				if(s==0) {
				   tipEl.lastChild.className = "selectTip";
				}
			}
		} else if(event.keyCode == 13) {
			var bList = tipEl.childNodes;
			for (var i = 0 ; i < bList.length ; i ++) {
				if (bList[i].className == "selectTip") {
					tip = srcEl.value = bList[i].innerHTML;				 
					Element.hide(tipEl);
					return ;
				}
			}
		}
	}
}
function  down(event)
{
      var srcEl = Event.element(event);
	  var tipEl = $("brandTip");
      var bList = tipEl.childNodes;
			for (var i = 0 ; i < bList.length ; i ++) {   
				if (bList[i].className == "selectTip") {
					tip = srcEl.value = bList[i].innerHTML;	
					document.getElementById("brand").value=bList[i].innerHTML;					
					Element.hide(tipEl);
					return;
				}
			}
}

function setValue(){
	if(document.getElementById("brand").value!="") {
	    var brand =document.getElementById("brand").value;
	    brand=brand.trim();
	    if(brand in jsrlists) {
      		document.getElementById("fzr").value=jsrlists[brand];
    	} else {
			alert("您所输入的经手人不在列表里!");
			document.getElementById("brand").value="";
			document.getElementById("fzr").value="";
			document.getElementById("brand").focus();
		}
	}
	
	if(document.getElementById("brand").value.length==0){
		document.getElementById("fzr").value="";
	} 
	Element.hide('brandTip')
}


function initFzrTip(){ 
	new Form.Element.Observer("brand",1, searchBrand);
	Event.observe("brand", "keydown", move, false);
	Event.observe("brandTip","mousedown",down,true);
	
	//初始提示层的位置
	var pos = GetObjPos(document.getElementById("brand"))
	document.getElementById("brandTip").style.left = pos.x;
	document.getElementById("brandTip").style.top = pos.y + 22;
}