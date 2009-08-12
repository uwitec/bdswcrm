//在body的onload事件中调用 initSqrTip();
//searchLikeSqr  查询相近的申请人
//showSrqTip 展示
//sqrMove 上下事件
//sqrDown 鼠标按下事件
//setSqrValue 鼠标离开事件
//显示框的ID为 sqr_text
//隐藏框的ID为 sqr
//在显示框后添加：<div d="sqr_tips"  style="height:12px;position:absolute;left:610px; top:110px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>


var SqrTip = "";
function searchLikeSqr(){    
	var url = 'queryJsrTipInfo.html';
	var params = "paramValue=" + $F('sqr_text');
	var myAjax = new Ajax.Request(
	url,
	{
		method:'post',
		parameters: params,
		onComplete: showSrqTip,
		asynchronous:true
	});
}

function searchAllSqr(){    
	var url = 'queryAllJsrTipInfo.html';
	var params = "paramValue=" + $F('sqr_text');
	var myAjax = new Ajax.Request(
	url,
	{
		method:'post',
		parameters: params,
		onComplete: showInitAllSqr,
		asynchronous:true
	});
}

var sqrlists = new Array();

function showSrqTip(originalRequest){   
	if(originalRequest.responseText.trim() == ""){
		var bt = $("sqr_tips");
		bt.innerHTML = "";
		Element.hide('sqr_tips');	
		return false;
	}
	var brandList = originalRequest.responseText.split("%");
	if (brandList != null && brandList.length > 0){
		var bt = $("sqr_tips");
		var s="";
		var flog=0;
		for(var i = 0 ; i <  brandList.length; i++){
		   if(flog==30) {
		     break;
		   }
		   var curBrand = brandList[i].split("$");
		   sqrlists[curBrand[1].trim()] = curBrand[0].trim();
		   
		   s += "<div onmouseover=\"this.className='selectTip';style.cursor='default'\"  onmouseout=\"this.className=null; style.cursor='default'\">" + curBrand[1].trim() + "</div>";
		   flog++;
		}
		bt.innerHTML=s;
		 
		if(SqrTip != $("sqr_text").value) {
			var pos = GetObjPos(document.getElementById("sqr_text"))
			document.getElementById("sqr_tips").style.left = pos.x;
			document.getElementById("sqr_tips").style.top = pos.y + 22;		
			Element.show('sqr_tips');
		}
	} else {
		var bt = $("sqr_tips");
		bt.innerHTML = "";
		Element.hide('sqr_tips');
	}
}

function showInitAllSqr(originalRequest){   
	if(originalRequest.responseText.trim() == ""){
		return false;
	}
	var brandList = originalRequest.responseText.split("%");
	if (brandList != null && brandList.length > 0){
		for(var i = 0 ; i <  brandList.length; i++){
		   var curBrand = brandList[i].split("$");
		   sqrlists[curBrand[1].trim()] = curBrand[0].trim();
		   
		}
	}
}

function sqrMove(event) {
	 var srcEl = Event.element(event);
	 var tipEl = $("sqr_tips");
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
					SqrTip = srcEl.value = bList[i].innerHTML;				 
					Element.hide(tipEl);
					return ;
				}
			}
		}
	}
}
function  sqrDown(event)
{
      var srcEl = Event.element(event);
	  var tipEl = $("sqr_tips");
      var bList = tipEl.childNodes;
			for (var i = 0 ; i < bList.length ; i ++) {   
				if (bList[i].className == "selectTip") {
					SqrTip = srcEl.value = bList[i].innerHTML;	
					document.getElementById("sqr_text").value=bList[i].innerHTML;					
					Element.hide(tipEl);
					return;
				}
			}
}

function setSqrValue(){
	if(document.getElementById("sqr_text").value!="") {
	    var brand =document.getElementById("sqr_text").value;
	    brand = brand.trim();
	    if(brand in sqrlists) {
      		document.getElementById("sqr").value=sqrlists[brand];
    	} else {
			//alert("您所输入的申请人不在列表里!");
			document.getElementById("sqr_text").value="";
			document.getElementById("sqr").value="";
			document.getElementById("sqr_text").focus();
		}
	}
	
	if(document.getElementById("sqr_text").value.length==0){
		document.getElementById("sqr").value="";
	} 
	Element.hide('sqr_tips');
}


function initSqrTip(){ 
	new Form.Element.Observer("sqr_text",1, searchLikeSqr);
	Event.observe("sqr_text", "keydown", sqrMove, false);
	Event.observe("sqr_tips","mousedown",sqrDown,true);
	
	//初始提示层的位置
	var pos = GetObjPos(document.getElementById("sqr_text"))
	document.getElementById("sqr_tips").style.left = pos.x;
	document.getElementById("sqr_tips").style.top = pos.y + 22;
	
	//初始申请人列表
	searchAllSqr();
}