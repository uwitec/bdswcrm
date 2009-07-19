function saveInfo(){
    
	if(document.getElementById("id").value == ""){
		alert("销售单编号不能为空，请填写！");
		return;
	}
	if(document.getElementById("client_name").value == ""){
		alert("客户名称不能为空，请选择！");
		return;
	}
	if(document.getElementById("fzr").value == ""){
		alert("经手人不能为空，请选择！");
		return;
	}		
	if(document.getElementById("sklx").value == ""){
		alert("客户付款类型不能为空，请选择！");
		return;
	}
	
	if(document.getElementById("sklx").value == "现结"){
		if(!InputValid(document.getElementById("xjd"),1,"float",1,0,99,"现金点")){
			document.getElementById("xjd").focus();
			return;
		}
		if(parseFloat(document.getElementById("skje").value) != parseFloat(document.getElementById("xsdje").value)){
			alert("本次收款金额与销售单金额不同，请检查！");
			return;
		}
		if(document.getElementById("skzh").value == ""){
			alert("收款账户不能为空，请选择！");
			return;
		}					
	}else if(document.getElementById("sklx").value == "账期"){
		if(!InputValid(document.getElementById("zq"),1,"int",1,1,99,"账期")){
			document.getElementById("zq").focus();
			return;
		}		
	}	
				
	hj();

		if(document.getElementById("skfs").value == "刷卡"){
		if(document.getElementById("pos_id").value == ""){
			alert("请选择刷卡POS机！");
			return;
		}
	}	
	
	document.xsdForm.btnSub.disabled = true;					
	document.xsdForm.submit();
}


function submitInfo(){
	if(document.getElementById("id").value == ""){
		alert("销售单编号不能为空，请填写！");
		return;
	}
	if(document.getElementById("client_name").value == ""){
		alert("客户名称不能为空，请选择！");
		return;
	}
	if(document.getElementById("fzr").value == ""){
		alert("经手人不能为空，请选择！");
		return;
	}		
	if(document.getElementById("sklx").value == ""){
		alert("客户付款类型不能为空，请选择！");
		return;
	}
	
	if(document.getElementById("sklx").value == "现结"){
		if(!InputValid(document.getElementById("xjd"),1,"float",1,0,99,"现金点")){
			document.getElementById("xjd").focus();
			return;
		}
		if(parseFloat(document.getElementById("skje").value) != parseFloat(document.getElementById("xsdje").value)){
			alert("本次收款金额与销售单金额不同，请检查！");
			return;
		}
		if(document.getElementById("skzh").value == ""){
			alert("收款账户不能为空，请选择！");
			return;
		}					
	}else if(document.getElementById("sklx").value == "账期"){
		if(!InputValid(document.getElementById("zq"),1,"int",1,1,99,"账期")){
			document.getElementById("zq").focus();
			return;
		}		
	}
		
	hj();
	
		if(document.getElementById("skfs").value == "刷卡"){
		if(document.getElementById("pos_id").value == ""){
			alert("请选择刷卡POS机！");
			return;
		}
	}
			
	document.xsdForm.action = "submitXsd.html";
	document.xsdForm.btnTjsp.disabled = true;
	
	document.xsdForm.submit();	
}

function addTr(){
	var otr = document.getElementById("xsdtable").insertRow(-1);
	
	var curId = allCount + 1;   //curId一直加下去，防止重复
	allCount = allCount + 1;
	
	
	var otd9=document.createElement("td");
	otd9.className = "a2";
	otd9.innerHTML = '<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="' + curId + '"></td>';
	
	var otd0=document.createElement("td");
	otd0.className = "a2";
	otd0.innerHTML = '<input type="text" id="product_name_'+curId+'"  style="width:100%" name="xsdProducts['+curId+'].product_name" readonly><input type="hidden" id="product_id_'+curId+'" name="xsdProducts['+curId+'].product_id">';
	
	var otd1 = document.createElement("td");
	otd1.className = "a2";
	otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  style="width:100%"  name="xsdProducts['+curId+'].product_xh" size="10" readonly>';
	
	var otd3 = document.createElement("td");
	otd3.className = "a2";
	otd3.innerHTML = '<input type="text" id="price_'+curId+'" name="xsdProducts['+curId+'].price" value="0.00" size="10" onblur="hj();"><input type="hidden" id="cbj_'+curId+'" name="xsdProducts['+curId+'].cbj" value="0.00"><input type="hidden" id="kh_cbj_'+curId+'" name="xsdProducts['+curId+'].kh_cbj" value="0.00"><input type="hidden" id="jgtz_'+curId+'" name="xsdProducts['+curId+'].jgtz" value="0.00" size="10" onblur="hj();">';
	
	
	var otd5 = document.createElement("td");
	otd5.className = "a2";
	otd5.innerHTML = '<input type="text" id="nums_'+curId+'" name="xsdProducts['+curId+'].nums" value="0" size="5" onblur="hj();">';
	
	var otd8 = document.createElement("td");
	otd8.className = "a2";
	otd8.innerHTML = '<input type="text" id="xj_'+curId+'" name="xsdProducts['+curId+'].xj" value="0.00" size="10" readonly><input type="hidden" id="qz_serial_num_'+curId+'" name="xsdProducts['+curId+'].qz_serial_num" size="15" readonly>';        
	
	var otd6 = document.createElement("td");
	otd6.className = "a2";
	otd6.innerHTML = '<input type="text" id="remark_'+curId+'" name="xsdProducts['+curId+'].remark">';                       

	otr.appendChild(otd9); 
	otr.appendChild(otd0); 
	otr.appendChild(otd1); 
	otr.appendChild(otd3);
	otr.appendChild(otd5);
	otr.appendChild(otd8); 
	otr.appendChild(otd6);  
               
}	
   

function openSerialWin(vl){
	var pn = document.getElementById("product_id_" + vl).value;
	var nm = document.getElementById("nums_" + vl).value;
	
	if(pn == ""){
		alert("请选择产品，再输入序列号！");
		return;
	}
	if(nm == "" || nm == "0"){
		alert("请设置产品数量，再输入序列号！");
		return;
	}
	
	var qzserialnum = document.getElementById("qz_serial_num_" + vl).value;
	
	var url = "importSerial.html?openerId=" + vl + "&nums=" + nm + "&serialNum=" + qzserialnum + "&product_id=" + pn;
	var fea ='width=300,height=200,left=' + (screen.availWidth-300)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
	
	window.open(url,'详细信息',fea);	
}    
    
    
function delTr(i){
		var tr = i.parentNode.parentNode;
		tr.removeNode(true);
		
}    


function openWin(){
	var destination = "selXsdProc.html";
	var fea ='width=800,height=550,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-550)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
	
	window.open(destination,'详细信息',fea);	
}	


function openClientWin(){
	var destination = "selectClient.html";
	var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
	
	window.open(destination,'详细信息',fea);		
}
function openywyWin()
{
   var destination = "selLsEmployee.html";
	var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
	
	window.open(destination,'选择经手人',fea);	
}	

function openAccount(){
	var destination = "selSkAccount.html";
	var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
	
	window.open(destination,'选择账户',fea);		
}

function chgState(vl){
	var obj = document.getElementById("store_id");
	var obj2 = document.getElementById("lb_store_id");
	
	if(vl == "已出库"){
		obj.style.display = "";
		obj2.style.display = "";
	}else{
		obj.style.display = "none";
		obj2.style.display = "none";
		obj.value = "";
	}
}

function hj(){
	var length = (document.getElementById('xsdtable').rows.length-2);
	
	var hjz = 0;
	var cbjhj = 0;
	var khcbjhj = 0;
	
	for(var i=0;i<=allCount;i++){			
		var price = document.getElementById("price_" + i);
		
		if(price != null){
			if(!InputValid(price,0,"float",0,1,99999999,"销售价格")){
				price.focus();
				return;
			}
		}
		
		var jgtz = document.getElementById("jgtz_" + i);
		if(jgtz != null){
			if(!InputValid(jgtz,0,"float",0,1,99999999,"调整价格")){
				jgtz.focus();
				return;
			}
		}	
		
		var nums = document.getElementById("nums_" + i);
		if(nums != null){
			if(!InputValid(nums,0,"int",0,1,99999999,"数量")){
				nums.focus();
				return;
			}
		}
		
		var cbjz = document.getElementById("cbj_" + i);
		var khcbjz = document.getElementById("kh_cbj_" + i);			
		var xj = document.getElementById("xj_" + i);			
		
		if(xj != null){
			xj.value = (parseFloat(price.value) + parseFloat(jgtz.value)) * parseFloat(nums.value);				
			hjz = parseFloat(hjz) + parseFloat(xj.value);
			
			cbjhj = parseFloat(cbjhj) + parseFloat(cbjz.value) * parseFloat(nums.value);
			khcbjhj = parseFloat(khcbjhj) + parseFloat(khcbjz.value) * parseFloat(nums.value)
		}
	}	
	
	var xsdje = document.getElementById("xsdje");
	var xsdcbj = document.getElementById("xsdcbj");
	var xsdkhcb = document.getElementById("xsdkhcb");
	
	xsdje.value = hjz;
	xsdcbj.value = cbjhj;
	xsdkhcb.value = khcbjhj;

}

function delDesc(){
	var k = 0;
	var sel = "0"; 
	for(var i=0;i<document.xsdForm.proc_id.length;i++){
		var o = document.xsdForm.proc_id[i];
		if(o.checked){
			k = k + 1;
			sel = document.xsdForm.proc_id[i].value;
		}
	}
	if(k != 1){
		alert("请选择产品明细，且只能选择一条信息！");
		return;
	}
	
	document.getElementById("product_name_" + sel).value = "";
	document.getElementById("product_id_" + sel).value = "";
	document.getElementById("product_xh_" + sel).value = "";
	document.getElementById("price_" + sel).value = "0.00";
	document.getElementById("cbj_" + sel).value = "0.00";
	document.getElementById("jgtz_" + sel).value = "0.00";
	document.getElementById("nums_" + sel).value = "0";
	document.getElementById("xj_" + sel).value = "0.00";
	document.getElementById("remark_" + sel).value = "";
}

function changeSklx(vl){
	//if(vl == "现结"){
	//	document.getElementById("lb_xjd").style.display = "";
	//	document.getElementById("tr_skxx").style.display = "";
	//	document.getElementById("xjd").style.display = "";
	//	document.getElementById("xjd").value = "0.00";
		
	//	document.getElementById("lb_zq").style.display = "none";
	//	document.getElementById("zq").style.display = "none";
	//	document.getElementById("zq").value = "0";		
	//}else 
	
	if(vl == "账期"){
		document.getElementById("lb_xjd").style.display = "none";
		document.getElementById("tr_skxx").style.display = "none";
		document.getElementById("xjd").style.display = "none";
		document.getElementById("xjd").value = "0.00";
		
		document.getElementById("lb_zq").style.display = "";
		document.getElementById("zq").style.display = "";
		document.getElementById("zq").value = "0";			
		
		document.getElementById("td_skzh_label").style.display = "none";
		document.getElementById("td_skzh_value").style.display = "none";
		document.getElementById("zhname").value = "";
		document.getElementById("skzh").value = "";
		
	}else{
		document.getElementById("tr_skxx").style.display = "";
		document.getElementById("lb_xjd").style.display = "none";
		document.getElementById("xjd").style.display = "none";
		document.getElementById("xjd").value = "0.00";
		
		document.getElementById("lb_zq").style.display = "none";
		document.getElementById("zq").style.display = "none";
		document.getElementById("zq").value = "0";	
		
		document.getElementById("td_skzh_label").style.display = "";
		document.getElementById("td_skzh_value").style.display = "";				
	}
}


function f_enter(){
    if (window.event.keyCode==13){
        sendSerialNum();
    }
}

//发送序列号
function sendSerialNum(){
	var serialNum = dwr.util.getValue("s_nums");
	if(serialNum == ""){
		return;
	}
	dwrService.getProductObjBySerialNum(serialNum,setProductInfo);		
}

//处理返回产品对象
function setProductInfo(product){
	if(product != null && product.productId != null){
		var flag = false;
		for(var i=0;i<=allCount;i++){
			var obj = document.getElementById("product_id_" + i);
							
			if(obj != null){
				if(obj.value == "" || obj.value==product.productId){
				
					var vl = dwr.util.getValue("qz_serial_num_" + i); //已有的序列号
					var vl2 = dwr.util.getValue("s_nums");    //输入的序列号
					if(vl.indexOf(vl2) != -1){
						alert("产品列表中已存在该序列号，请检查！");
						break;
					}
					
					if(vl == ""){
						vl = vl2;
					}else{
						vl += "," + vl2;
					}
					dwr.util.setValue("qz_serial_num_" + i,vl);
										
					dwr.util.setValue("product_id_" + i,product.productId);
					dwr.util.setValue("product_name_" + i,product.productName);
					dwr.util.setValue("product_xh_" + i,product.productXh);
					dwr.util.setValue("cbj_" + i,product.price);
					dwr.util.setValue("kh_cbj_" + i,product.khcbj);
					dwr.util.setValue("price_" + i,product.lsbj);
					
					var nums = dwr.util.getValue("nums_" + i);
					dwr.util.setValue("nums_" + i,parseInt(nums)+1);					
					
					dwr.util.setValue("qz_flag_" + i,product.qz_serial_num);
					
					dwr.util.setValue("s_nums","");
					break;
				}
				if(i==allCount){
					addTr();				
				}
			}
		}
		hj();
	}else{
		alert("该序列号不存在，请检查!");
	}
}


function selSkfs(vl){
	if(vl == "刷卡"){
		document.getElementById("pos_id").style.display = "";
	}else{
		document.getElementById("pos_id").style.display = "none";
		document.getElementById("pos_id").value = "";
	}
}
