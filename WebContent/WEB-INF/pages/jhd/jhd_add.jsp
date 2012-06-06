<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
String id = (String)VS.findValue("id");
List storeList = (List)VS.findValue("storeList");
Jhd jhd = (Jhd)VS.findValue("jhd");
String cgsd = (String)VS.findValue("cgsd");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>采购订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<LINK href="css/ddcolortabs.css" type=text/css rel=stylesheet>
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
	
	var allCount = 2;
	var jhzq = 0;
	//客户对应联系人信息
	var arryLxrObj = new Array();
	var temp_client_id = "";
	//客户联系人对象
	function LinkMan(id,name,tel){
	    this.id = id;
	    this.name = name;
	    this.tel = tel;
	}	

	function onloadClientInfo(){
		if($F('client_id') == ""){
			return;
		}
		var url = 'queryClientsRegInfo.html';
		var params = "clients_id=" + $F('client_id');
		var myAjax = new Ajax.Request(
		url,
		{
			method:'post',
			parameters: params,
			onComplete: initJhzq,
			asynchronous:true
		});
	}

	function initJhzq(originalRequest){
		var resText = originalRequest.responseText.trim(); 
		if(resText == ""){
			return false;
		}
		var arryText = resText.split("%");

		//客户地址填充
		if(arryText != null && arryText.length>0){
			var arryClientInfo = arryText[0].split("#");			
			jhzq = arryClientInfo[6];
		}
	}
	
	
	//查询客户相关信息
	function setClientRegInfo(){		
		//填充值
		setClientValue(); 

		if(temp_client_id == $F('client_id')){
			return;
		}
		
		temp_client_id = $F('client_id');
		if($F('client_id') == ""){
			return;
		}
		
		//根据填充后的值选择客户地址联系人等信息
		var url = 'queryClientsRegInfo.html';
		var params = "clients_id=" + $F('client_id');
		var myAjax = new Ajax.Request(
		url,
		{
			method:'post',
			parameters: params,
			onComplete: fillClientRegInfo,
			asynchronous:true
		});
	}
	
	//处理客户地址联系人等信息
	function fillClientRegInfo(originalRequest){  

		var resText = originalRequest.responseText.trim(); 
		if(resText == ""){
			return false;
		}

		var arryText = resText.split("%");

		//客户地址填充
		if(arryText != null && arryText.length>0){
		
			var arryClientInfo = arryText[0].split("#");			
			document.getElementById("kh_address").value = arryClientInfo[0];
			if(document.getElementById("fkfs").value == "账期") {
				document.getElementById("zq").value = arryClientInfo[6];
			}else{
				document.getElementById("zq").value = '0';
			}
			jhzq = arryClientInfo[6];
		}
		
		if(arryText != null && arryText.length>1){
			var linkMantext = arryText[1];
			
			//联系人填充
			var objLxr = document.getElementById("kh_lxr"); 
			objLxr.options.length = 0;
			
			var arryLinkMan = linkMantext.split("$");
			if(arryLinkMan.length > 0){
				for(var i=0;i<arryLinkMan.length;i++){
					var arryInfo = arryLinkMan[i].split("#");		
					var manObj = new LinkMan(arryInfo[0],arryInfo[1],arryInfo[2]);
					arryLxrObj[i] = manObj;
					objLxr.add(new Option(arryInfo[1],arryInfo[1]));
				}
				if(arryLxrObj[0].tel != undefined)
					document.getElementById("kh_lxdh").value = arryLxrObj[0].tel;
			}		
		}
	}
	
	
	//联系人与电话联动
	function chgLxr(vl){
		if(vl == ""){
			return;
		}
		if(arryLxrObj != null && arryLxrObj.length > 0){
			for(var i=0;i<arryLxrObj.length;i++){
				if(vl == arryLxrObj[i].name){
					document.getElementById("kh_lxdh").value = arryLxrObj[i].tel;
					break;
				}
			}
		}
	}
	
	function saveInfo(vl){

		if(vl == "1"){
			document.getElementById("state").value = "已保存";
		}else{
			document.getElementById("state").value = "已提交";
		}
		
		if(document.getElementById("cg_date").value == ""){
			alert("采购日期不能为空，请选择！");
			return;
		}	
		if(document.getElementById("client_id").value == ""){
			alert("供货单位不能为空，请选择！");
			return;
		}

		if(document.getElementById("fzr").value == ""){
			alert("采购负责人不能为空，请选择！");
			return;
		}

		if(!InputValid(document.getElementById("zq"),1,"int",1,0,999,"账期限天数")){
			return;
		}
		
		 var rs=document.getElementsByName("jhd.ysws"); 
        if(rs[0].checked==false&&rs[1].checked==false) 
        { 
			alert("发票类型不能为空，请选择！");
			return;
		}
		
		hj();

		if(vl == "1"){
			document.jhdForm.submit();
		}else{
			if(window.confirm("确认提交吗？提交后将不可修改！")){
				document.jhdForm.submit();
			}else{
				return;
			}
		}
		
		document.jhdForm.btnSub.disabled = true;
		document.jhdForm.btnSave.disabled = true;
	}

      	
    function addTr(){
    	//alert(document.getElementById("jhtable").rows.length);
        var otr = document.getElementById("jhtable").insertRow(document.getElementById("jhtable").rows.length-1);

        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        var otd=document.createElement("td");
        otd.className = "a2";
        otd.innerHTML = '<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="' + curId + '"></td>';
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="jhdProducts['+curId+'].product_name" style="width:90%" readonly><input type="hidden" id="product_id_'+curId+'" name="jhdProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" size="10"  id="product_xh_'+curId+'"  name="jhdProducts['+curId+'].product_xh" style="width:90%" readonly>';
        
        var otd2 = document.createElement("td");
        otd2.className = "a2";
        otd2.innerHTML = '<input type="text" size="10"  id="price_'+curId+'" name="jhdProducts['+curId+'].price" value="0.00" style="width:90%;text-align: right;" onblur="hj();">';
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" size="5"  id="nums_'+curId+'" name="jhdProducts['+curId+'].nums" value="0" style="width:90%;text-align: center;" onblur="hj();">';

        
        var otd4 = document.createElement("td");
        otd4.className = "a2";
        otd4.innerHTML = '<input type="text" size="10"  id="hsje_'+curId+'" name="jhdProducts['+curId+'].hsje" value="0.00" style="width:90%;text-align: right;"  readonly="readonly">';  
        
        var hswfFlags = document.getElementsByName("jhd.ysws");
        var hswfFlag = "";
        for(var i=0;i<hswfFlags.length;i++){
        	if(hswfFlags[i].checked){
        		hswfFlag = hswfFlags[i].value;
        	}
        }
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.id = "tdHidden";
        if(hswfFlag == "未税"){
        	otd5.style.display = "none";
        	otd5.innerHTML = '<input type="text" size="5"  id="sd_'+curId+'" name="jhdProducts['+curId+'].sd" value="0.0" style="width:90%;text-align: right;" onblur="hj();">';
        }else{
        	otd5.style.display = "";
        	otd5.innerHTML = '<input type="text" size="5"  id="sd_'+curId+'" name="jhdProducts['+curId+'].sd" value="<%=cgsd %>" style="width:90%;text-align: right;" onblur="hj();">';
        }
          
        
        var otd6 = document.createElement("td");
        otd6.className = "a2";
        otd6.id = "tdHidden";
        otd6.innerHTML = '<input type="text" size="5"  id="sje_'+curId+'" name="jhdProducts['+curId+'].sje" value="0.00" style="width:90%;text-align: right;" onblur="hj();" readonly="readonly">';
        if(hswfFlag == "未税"){
        	otd6.style.display = "none";
        }else{
        	otd6.style.display = "";
        }
          
        
        var otd7 = document.createElement("td");
        otd7.className = "a2";
        otd7.id = "tdHidden";
        otd7.innerHTML = '<input type="text" size="5"  id="bhsje_'+curId+'" name="jhdProducts['+curId+'].bhsje" value="0.00" style="width:90%;text-align: right;" onblur="hj();" readonly="readonly">';
        if(hswfFlag == "未税"){
        	otd7.style.display = "none";
        }else{
        	otd7.style.display = "";
        }
        
		otr.appendChild(otd);
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd2); 
        otr.appendChild(otd3); 
        otr.appendChild(otd4);
        otr.appendChild(otd5); 
        otr.appendChild(otd6); 
        otr.appendChild(otd7); 
    }	
     
     
	function delTr(i){
		var tr = i.parentNode.parentNode;
		tr.removeNode(true);
			
	}     
	
	function openWin(id){
		var destination = "selJhdProc.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	
	function openAccount(){
		var destination = "selAccount.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}
	
	function hj(){
		var hjz = 0;
		var hj_sje = 0;
		var hj_bhsje = 0;
		var hj_counts = 0;
		
        var hswfFlags = document.getElementsByName("jhd.ysws");
        var hswfFlag = "";
        for(var i=0;i<hswfFlags.length;i++){
        	if(hswfFlags[i].checked){
        		hswfFlag = hswfFlags[i].value;
        	}
        }
		
		for(var i=0;i<=allCount;i++){			
			var price = document.getElementById("price_" + i);
			
			if(price != null){
				if(!InputValid(price,0,"float",0,1,99999999,"采购价格")){
					price.focus();
					return;
				}
			}
			
			var sd =  document.getElementById("sd_" + i);
			if(hswfFlag == "含税"){
				if(!InputValid(sd,0,"float",0,1,99999999,"税点")){
					sd.focus();
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
			hj_counts =  parseFloat(hj_counts) + parseFloat(nums.value);
			
			//含税金额
			var hsje = document.getElementById("hsje_" + i);	
			if(hsje != null){
				hsje.value = (parseFloat(price.value) * parseFloat(nums.value)).toFixed(2);
				hjz = parseFloat(hjz) + parseFloat(hsje.value);
			}
			
			if(hswfFlag == "含税"){
				//含税情况
				var bhsje = document.getElementById("bhsje_" + i);	  //不含税金额
				var sje = document.getElementById("sje_" + i);	  //税额
				if(bhsje != null){
					bhsje.value = ((parseFloat(price.value) * parseFloat(nums.value)) / (1 + parseFloat(document.getElementById("sd_" + i).value) / 100)).toFixed(2);
					sje.value = (parseFloat(hsje.value) - parseFloat(bhsje.value)).toFixed(2);
					
					hj_sje = parseFloat(hj_sje) + parseFloat(sje.value);
					hj_bhsje = parseFloat(hj_bhsje) + parseFloat(bhsje.value);
				}
			}else{
				//不含税情况
				document.getElementById("bhsje_" + i).value = hsje.value;
				document.getElementById("sd_" + i).value = "0.00";
				document.getElementById("sje_" + i).value = "0.00";
			}
		}		
		
		var total = document.getElementById("total");
		var hjsje = document.getElementById("hjsje");
		var hj_nums = document.getElementById("hj_nums");
		var hjbhsje = document.getElementById("hjbhsje");
		
		if(hswfFlag == "含税"){
			total.value = hjz.toFixed(2);
			hjsje.value = hj_sje.toFixed(2);
			hjbhsje.value = hj_bhsje.toFixed(2);
			hj_nums.value = hj_counts;
		}else{
			total.value = hjz.toFixed(2);
			hjsje.value = "0.00";
			hjbhsje.value =  hjz.toFixed(2);
			hj_nums.value = hj_counts;
		}
	}	
	
	function delDesc(){
		var k = 0;
		var sel = "0"; 
		for(var i=0;i<document.jhdForm.proc_id.length;i++){
			var o = document.jhdForm.proc_id[i];
			if(o.checked){
				k = k + 1;
				sel = document.jhdForm.proc_id[i].value;
			}
		}
		if(k != 1){
			alert("请选择商品明细，且只能选择一条信息！");
			return;
		}
		
		document.getElementById("product_name_" + sel).value = "";
		document.getElementById("product_id_" + sel).value = "";
		document.getElementById("product_xh_" + sel).value = "";
		document.getElementById("price_" + sel).value = "0.00";
		document.getElementById("nums_" + sel).value = "0";
		document.getElementById("sd_" + sel).value = "0.00";
		document.getElementById("hsje_" + sel).value = "0.00";
		document.getElementById("sje_" + sel).value = "0.00";
		document.getElementById("bhsje_" + sel).value = "0.00";
	}	

	//选择供货单位
	function selClient(){
		setClientValue();
		dwrService.getClientCgzq(dwr.util.getValue("client_id"),setCgzq);
	}

	//自动根据DWR返回值填充账期
	function setCgzq(cgzq){
		if(cgzq != null){
			dwr.util.setValue("zq",cgzq);
		}
	}
	
	//含税未税切换
	function changeHsws(vl){
		
		var tdObjs = document.getElementsByTagName("td");
		var inputObjs = document.getElementsByTagName("input");
		
		if(vl == "含税"){
			document.getElementById("tdHsje").innerHTML = "含税金额";
			
			if(tdObjs != null && tdObjs.length > 0){
				for(var i=0;i<tdObjs.length;i++){
					if(tdObjs[i].id == "tdHidden"){
						tdObjs[i].style.display = "";
					}
				}
			}
			
			if(inputObjs != null && inputObjs.length > 0){
				for(var i=0;i<inputObjs.length;i++){
					if(inputObjs[i].id.indexOf("sd_") != -1 ){
						inputObjs[i].value = "<%=cgsd %>";
					}
				}
			}
		} else {
			document.getElementById("tdHsje").innerHTML = "金额";
			if(tdObjs != null && tdObjs.length > 0){
				for(var i=0;i<tdObjs.length;i++){
					if(tdObjs[i].id == "tdHidden"){
						tdObjs[i].style.display = "none";
					}
				}
			}
			if(inputObjs != null && inputObjs.length > 0){
				for(var i=0;i<inputObjs.length;i++){
					if(inputObjs[i].id.indexOf("sd_") != -1 ){
						inputObjs[i].value = "0.0";
					}
				}
			}
		}
		hj();
	}
</script>
</head>
<body onload="initFzrTip();initClientTip();onloadClientInfo();">
<form name="jhdForm" action="saveJhd.html" method="post">
<input type="hidden" name="jhd.state" id="state" value=""/>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">采购订单信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%">
		<input type="text" name="jhd.id" id="id" value="<%=id %>" size="45" readonly>
		</td>	
		<td class="a1" width="15%">采购日期</td>
		<td class="a2" width="35%"><input type="text" name="jhd.cg_date" id="cg_date" value="<%=DateComFunc.getToday() %>" size="45"  class="Wdate" onFocus="WdatePicker()"><font color="red">*</font>	
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">供货单位</td>
		<td class="a2" width="35%">
		<input type="text" name="jhd.gysmc" onblur="setClientRegInfo();" id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(jhd.getGysbh())) %>" size="45" >
		<input type="hidden" name="jhd.gysbh" id="client_id" value="<%=StringUtils.nullToStr(jhd.getGysbh()) %>">
		<div id="clientsTip" style="position:absolute;width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		<font color="red">*</font>	
		</td>
		<td class="a1">地址</td>
		<td class="a2"><input type="text" name="jhd.kh_address" id="kh_address" value="<%=StringUtils.nullToStr(jhd.getKh_address()) %>" size="45" maxlength="100"></td>	
	</tr>
	
	<tr>
		<td class="a1">客户联系人</td>
		<td class="a2">
			<select name="jhd.kh_lxr" id="kh_lxr" onchange="chgLxr(this.value);" style="width:256px">
				<%
				if(!StringUtils.nullToStr(jhd.getKh_lxr()).equals("")){
				%>
					<option value="<%=StringUtils.nullToStr(jhd.getKh_lxr()) %>" selected><%=StringUtils.nullToStr(jhd.getKh_lxr()) %></option>
				<%
				} 
				%>
			</select>
		</td>	
		<td class="a1">联系电话</td>
		<td class="a2"><input type="text" name="jhd.kh_lxdh" id="kh_lxdh" size="45" maxlength="22"/></td>
	</tr>
	<tr>	
		<td class="a1" width="15%">账期</td>
		<td class="a2">
			<input type="text" name="jhd.zq" id="zq" value="0" size="45" title="账期"  maxlength="5"/>天<font color="red">*</font>
			<input type="hidden" name="jhd.fkfs" id="fkfs" value="账期"/>
		</td>
		<td class="a1"  width="15%">发票类型</td>
	    <td class="a2" colspan="3">
	        <input type="radio"  name="jhd.ysws" id="ysws" value="未税"  checked="checked"  onclick="changeHsws(this.value);"/>未税<input type="radio"  name="jhd.ysws" id="ysws" value="含税"  onclick="changeHsws(this.value);"/>含税
	         <font color="red">*</font>
        </td>
	</tr>
	<tr>			
		<td class="a1" width="15%">预计到货时间</td>
		<td class="a2">
		<input type="text" name="jhd.yjdhsj" id="yjdhsj" value="0" size="45" title="预计到货时间"/>天
		</td>		
	    <td class="a1" width="15%">到货库房</td>
		<td class="a2" width="35%">
			<select name="jhd.store_id" id="store_id" style="width:256px">
				<option value=""></option>
				<%
				if(storeList != null && storeList.size()>0){
					for(int i=0;i<storeList.size();i++){
						StoreHouse storeHouse = (StoreHouse)storeList.get(i);
				%>
				<option value="<%=StringUtils.nullToStr(storeHouse.getId()) %>"><%=StringUtils.nullToStr(storeHouse.getName()) %></option>
				<%
					}
				}
				%>
			</select>
		</td>	  
    </tr>
	<tr>
        <td class="a1" width="15%">采购负责人</td>
		<td class="a2" colspan="3">
		    <input id="brand" type="text" maxlength="20" size="45" onblur="setValue()" value=""> 
            <div id="brandTip" style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
            </div>
		    <input type="hidden" name="jhd.fzr" id="fzr" value=""> <font color="red">*</font>	
		</td>	
	</tr>	
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">商品详细信息</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="jhtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="4%">选择</td>
		<td width="25%">商品名称</td>
		<td width="20%">规格</td>
		<td width="8%">单价</td>
		<td width="8%">数量</td>
		<td width="8%" id="tdHsje" name="tdHsje">金额</td>
		<td id="tdHidden"  style="display: none" width="8%" >税点</td>
		<td id="tdHidden"  style="display: none">税额</td>
		<td id="tdHidden"  style="display: none">不含税金额</td>
	</tr>
	</thead>
<%
for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text"  id="product_name_<%=i %>" name="jhdProducts[<%=i %>].product_name" style="width:90%" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="jhdProducts[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" size="10" id="product_xh_<%=i %>" name="jhdProducts[<%=i %>].product_xh" style="width:90%" readonly></td>
		<td class="a2"><input type="text" size="10"  id="price_<%=i %>" name="jhdProducts[<%=i %>].price" value="0.00" style="width:90%;text-align: right;" onblur="hj();"></td>
		<td class="a2"><input type="text" size="5"  id="nums_<%=i %>" name="jhdProducts[<%=i %>].nums" value="0" style="width:90%;text-align: center;" onblur="hj();"></td>
		<td class="a2"><input type="text" size="5"  id="hsje_<%=i %>" name="jhdProducts[<%=i %>].hsje" value="0.00" style="width:90%;text-align: right;" readonly="readonly"></td>
		<td class="a2" id="tdHidden"   style="display: none"><input type="text" size="5"  id="sd_<%=i %>" name="jhdProducts[<%=i %>].sd" value=""  onblur="hj();" style="width:90%;text-align: right;"></td>
		<td class="a2" id="tdHidden"   style="display: none"><input type="text" size="5"  id="sje_<%=i %>" name="jhdProducts[<%=i %>].sje"  readonly="readonly" value="0.00" style="width:90%;text-align: right;"></td>
		<td class="a2" id="tdHidden"   style="display: none"><input type="text" size="5"  id="bhsje_<%=i %>" name="jhdProducts[<%=i %>].bhsje"  readonly="readonly" value="0.00" style="width:90%;text-align: right;"></td>
	</tr>
<%
}
%>	
	<tr>
		<td class="a2">合计</td>
		<td class="a2">&nbsp;</td>
		<td class="a2">&nbsp;</td>
		<td class="a2"></td>
		<td class="a2"><input type="text" size="5"  id="hj_nums" name="hj_nums"  value="0" style="width:90%;text-align: center;" onblur="hj();" readonly="readonly"/></td>
		<td class="a2"><input type="text" id="total"  name="jhd.total" value="0.00"  readonly="readonly" style="width:90%;text-align: right;"/></td>
		<td class="a2" id="tdHidden"  style="display: none">&nbsp;</td>
		<td class="a2" id="tdHidden"  style="display: none"><input type="text" id="hjsje"  name="jhd.hjsje"  style="width:90%;text-align: right;" value="0.00"  readonly="readonly"/></td>
		<td class="a2" id="tdHidden"  style="display: none"><input type="text" id="hjbhsje"  name="jhd.hjbhsje"   style="width:90%;text-align: right;" value="0.00"  readonly="readonly"/></td>
	</tr>
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="添加商品" class="css_button3" onclick="openWin();">
			<input type="button" name="button8" value="清除商品" class="css_button3" onclick="delDesc();">
		</td>
	</tr>
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">	
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%"><input type="text" name="jhd.ms" id="ms" style="width:80%">
		</td>
	</tr>		
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="btnSave" value="草 稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnSub" value="提 交" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
<BR>
</form>
</body>
</html>