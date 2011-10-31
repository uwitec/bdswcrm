//在body的onload事件中调用 initClientTip();
//searchClients  查询相近客户资料
//showClientsTip 展示
//clientTipMove 上下事件
//clientTipDown 鼠标按下事件
//setClientValue 鼠标离开事件
//显示框的ID为 client_name onblur="setClientValue()"
//隐藏框的ID为 client_id

var tips = "";
function searchClients(){
	var url = 'queryClientsTipInfo.html';
	var params = "clientsName=" + $F('client_name');
	var myAjax = new Ajax.Request(
	url,
	{
		method:'post',
		parameters: params,
		onComplete: showClientsTip,
		asynchronous:true
	});
}


function searchAllClients(){
	var url = 'queryAllClientsTipInfo.html';
	var myAjax = new Ajax.Request(
	url,
	{
		method:'post',
		parameters: '',
		onComplete: showInitAllClients,
		asynchronous:true
	});
}


var clientlists = new Array();

function showClientsTip(originalRequest){   
	if(originalRequest.responseText.trim() == ""){
		var bt = $("clientsTip");
		bt.innerHTML = "";
		Element.hide('clientsTip');	
		return false;
	}
	var brandList = originalRequest.responseText.split("%");
	if (brandList != null && brandList.length > 0){
		var bt = $("clientsTip");
		var s="";
		var flog=0;
		for(var i = 0 ; i <  brandList.length; i++) {
		   if(flog==30){
		     break;
		   }
		   var curBrand = brandList[i].split("$");
		   
		   clientlists[curBrand[1].trim()] = curBrand[0].trim();
		   
		   s += "<div onmouseover=\"this.className='selectTip';style.cursor='default'\"  onmouseout=\"this.className=null; style.cursor='default'\">" + curBrand[1].trim() + "</div>";
		   flog++;
		}
		bt.innerHTML=s;
		if( tips != $("client_name").value){
			var pos = GetObjPos(document.getElementById("client_name"))
			document.getElementById("clientsTip").style.left = pos.x;
			document.getElementById("clientsTip").style.top = pos.y + 22;		
			Element.show('clientsTip');
		}
	}else{
		var bt = $("clientsTip");
		bt.innerHTML = "";
		Element.hide('clientsTip');
	}
}



function showInitAllClients(originalRequest){   
	if(originalRequest.responseText.trim() == ""){	
		return false;
	}
	var brandList = originalRequest.responseText.split("%");
	if (brandList != null && brandList.length > 0){
		for(var i = 0 ; i <  brandList.length; i++) {
		   var curBrand = brandList[i].split("$");		   
		   clientlists[curBrand[1].trim()] = curBrand[0].trim();
		}
	}
}


function clientTipMove(event){
	  var srcEl = Event.element(event);
	  var tipEl = $('clientsTip');
     var a = tipEl.childNodes;
	 if (tipEl.style.display == "" ){
		if(event.keyCode == 40 ){            
			if (tipEl.childNodes.length >= 1){
				var bList = tipEl.childNodes;
				 
				if(tipEl.lastChild.className=="selectTip"){
				    tipEl.firstChild.className = "selectTip";
					tipEl.lastChild.className = "null";
					return ;
				}
				var s=0;
				for (var i = 0 ; i < bList.length; i++){
					if (bList[i].className == "selectTip"){
					    s++;
						bList[i + 1].className = "selectTip";
						bList[i].className = "null";
						return ;
					}
				}
				if(s==0){
				  tipEl.firstChild.className = "selectTip";
				}
			}
		}else if(event.keyCode == 38){
			if (tipEl.childNodes.length >= 1){
			   	if(tipEl.firstChild.className == "selectTip"){
					tipEl.lastChild.className = "selectTip";
					tipEl.firstChild.className = "null";
					return ;
				}
				var s=0;
				var bList = tipEl.childNodes;
				for (var i = 0 ; i < bList.length ; i ++){
					if (bList[i].className == "selectTip"){
					    s++;
						bList[i - 1].className = "selectTip";
						bList[i].className = "null";
						return ;
					}
				}
				if(s==0){
				   tipEl.lastChild.className = "selectTip";
				}
			}
		}else if(event.keyCode == 13){
			var bList = tipEl.childNodes;
			for (var i = 0 ; i < bList.length ; i ++){
				if (bList[i].className == "selectTip"){
					tips = srcEl.value = bList[i].innerHTML;					 
					Element.hide(tipEl);
					return ;
				}
			}
		}
	}
}
function  clientTipDown(event){
	var srcEl = Event.element(event);
	var tipEl = $("clientsTip");
	var bList = tipEl.childNodes;
	
	for (var i = 0 ; i < bList.length ; i ++){   
		if (bList[i].className == "selectTip"){
			tips = srcEl.value = bList[i].innerHTML;	
			document.getElementById("client_name").value=bList[i].innerHTML;		
			Element.hide(tipEl);
			return;
		}
	}
}


function setClientValue(){    
	if(document.getElementById("client_name").value!=""){
	    var brand =document.getElementById("client_name").value;
	    
	    brand=brand.trim();
	    if(brand in clientlists){
			document.getElementById("client_id").value=clientlists[brand];
			
	    }else{
			//alert("您所输入客户名称不在列表里，请检查!");
			document.getElementById("client_name").value="";
			document.getElementById("client_id").value="";
			document.getElementById("client_name").focus();
	    }
	}
  
	if(document.getElementById("client_name").value.length==0) {
      document.getElementById("client_id").value="";
	}
	
	Element.hide('clientsTip');
}

function initClientTip(){
	new Form.Element.Observer("client_name",1, searchClients);
	Event.observe("client_name", "keydown", clientTipMove, false);
	Event.observe("clientsTip","mousedown",clientTipDown,true);
	
	//初始提示层的位置
	var pos = GetObjPos(document.getElementById("client_name"));
	document.getElementById("clientsTip").style.left = pos.x;
	document.getElementById("clientsTip").style.top = pos.y + 22;
	
	//初始客户列表
	searchAllClients();
}