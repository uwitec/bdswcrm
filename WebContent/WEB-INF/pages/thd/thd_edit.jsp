<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List storeList = (List)VS.findValue("storeList");

Thd thd = (Thd)VS.findValue("thd");
List thdProducts = (List)VS.findValue("thdProducts");

int counts = 2;
if(thdProducts != null && thdProducts.size() > 0){
	counts = thdProducts.size()-1;
}

String msg = StringUtils.nullToStr(VS.findValue("msg"));
%>

<html>
<head>
<title>销售退货单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">

	var allCount = <%=counts %>;
	
	function saveInfo(vl){

		if(vl == "1"){
			document.getElementById("state").value = "已保存";
		}else{
			document.getElementById("state").value = "已入库";
		}
		
		if(document.getElementById("thd_id").value == ""){
			alert("退货单编号不能为空！");
			return;
		}
		if(document.getElementById("th_date").value == ""){
			alert("退货日期不能为空！");
			return;
		}					
		if(document.getElementById("client_id").value == ""){
			alert("客户名称不能为空，请选择！");
			return;
		}
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}	
		if(document.getElementById("store_id").value == ""){
			alert("退货库房不能为空，请选择！");
			return;
		}
		if(document.getElementById("remark").value == ""){
			alert("退货原因不能为空！");
			return;
		}
		if(document.getElementById("yw_type").value == "1")
		{
		  if(document.getElementById("type").value == "现金"){
			 if(document.getElementById("skzh").value == ""){
				alert("退款账号不能为空，请选择！");
				return;
			 }
		   }
		 }			
		
		if(document.getElementById("yw_type").value == "2")
		{
		  if(document.getElementById("typeLs").value == "现金"){
			 if(document.getElementById("skzh").value == ""){
				alert("退款账号不能为空，请选择！");
				return;
			 }
		   }
		 }
		
		//判断是否存在强制输入序列号的商品没有输入序列号
		for(var i=0;i<allCount;i++){
			var qzflag = document.getElementById("qz_flag_" + i);            //标志是否强制输入
			var qzserialnum = document.getElementById("qz_serial_num_" + i); //序列号
			var pn = document.getElementById("product_name_" + i);           //商品名称
			
			if(qzflag != null){
				if(qzflag.value == "是"){
					if(qzserialnum.value == ""){
						//如果没有输入序列号提示用户输入序列号
						alert("商品" + pn.value + "强制序列号，请先输入序列号！");
						qzserialnum.focus();
						return;
					}else{
						//校验输入数量与商品数是否相同
						var serial = document.getElementById("qz_serial_num_" + i).value;
						var arrySerial = serial.split(",");
						
						var nms = document.getElementById("nums_" + i).value;
						
						if(parseInt(nms) != arrySerial.length){
							alert("商品" + pn.value + "输入序列号数量与商品数量不符，请检查！");
							qzserialnum.focus();
							return;
						}
					}
				}
			}
		}		
					
		hj();
		if(document.getElementById("state").value == "已入库"){
			if(window.confirm("确认要入库吗，入库后将无法修改！")){
				document.thdForm.submit();		
			}else{
				return;
			}		
		}else{
			document.thdForm.submit();	
		}
		
		document.thdForm.btnSave.disabled = true;
		document.thdForm.btnSub.disabled = true;
	}


    function addTr(){
        var otr = document.getElementById("thtable").insertRow(-1);

       // var curId = ($('xsdtable').rows.length-2);
        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="thdProducts['+curId+'].product_name" readonly style="width:150px"><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin('+curId+');"><input type="hidden" id="product_id_'+curId+'" name="thdProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="thdProducts['+curId+'].product_xh" style="width:150px" readonly>';
        
        var otd2 = document.createElement("td");
        otd2.className = "a2";
        otd2.innerHTML = '<input type="text" id="th_price_'+curId+'" name="thdProducts['+curId+'].th_price" style="width:80px" value="0.00" onblur="hj();"><input type="hidden" id="cbj_'+curId+'" name="thdProducts['+curId+'].cbj" value="0.00"><input type="hidden" id="kh_cbj_'+curId+'" name="thdProducts['+curId+'].kh_cbj" value="0.00"><input type="hidden" id="sd_'+curId+'" name="thdProducts['+curId+'].sd"  value="0.00"><input type="hidden" id="gf_'+curId+'" name="thdProducts['+curId+'].gf"  value="0.00"><input type="hidden" id="ds_'+curId+'" name="thdProducts['+curId+'].ds"  value="0.00"><input type="hidden" id="basic_ratio_'+curId+'" name="thdProducts['+curId+'].basic_ratio"  value="0.00"><input type="hidden" id="out_ratio_'+curId+'" name="thdProducts['+curId+'].out_ratio"  value="0.00"><input type="hidden" id="lsxj_'+curId+'" name="thdProducts['+curId+'].lsxj"  value="0.00"><input type="hidden" id="ygcbj_'+curId+'" name="thdProducts['+curId+'].ygcbj"  value="0.00"><input type="hidden" id="sfcytc_'+curId+'" name="thdProducts['+curId+'].sfcytc"  value="">';
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" id="nums_'+curId+'" name="thdProducts['+curId+'].nums" style="width:60px" value="0" onblur="hj();">';
        
		var otd7 = document.createElement("td");
		otd7.className = "a2";
		otd7.innerHTML = '<input type="text" id="qz_serial_num_'+curId+'" name="thdProducts['+curId+'].qz_serial_num" style="width:120px" readonly><input type="hidden" id="qz_flag_'+curId+'" name="thdProducts['+curId+'].qz_flag"><a style="cursor:hand" title="点击输入序列号" onclick="openSerialWin('+ curId +');"><b>...</b></a>&nbsp;';               
        
        var otd4 = document.createElement("td");
        otd4.className = "a2";
        otd4.innerHTML = '<input type="text" id="xj_'+curId+'" name="thdProducts['+curId+'].xj" style="width:80px" value="0.00" readonly>';  
        
		var otd6 = document.createElement("td");
		otd6.className = "a2";
		otd6.innerHTML = '<input type="button" name="delButton" value="删除" class="css_button" onclick="delTr(this);">';
		
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd2); 
        otr.appendChild(otd3); 
        otr.appendChild(otd7);
        otr.appendChild(otd4); 
        otr.appendChild(otd6);               
     }	
    
	function delTr(i){
		var tr = i.parentNode.parentNode;
		tr.removeNode(true);
	}   
		
	function openSerialWin(vl){
		var pn = document.getElementById("product_name_" + vl).value;
		var nm = document.getElementById("nums_" + vl).value;
		
		if(pn == ""){
			alert("请选择商品，再输入序列号！");
			return;
		}
		if(nm == "" || nm == "0"){
			alert("请设置商品数量，再输入序列号！");
			return;
		}
		
		var qzserialnum = document.getElementById("qz_serial_num_" + vl).value;
		
		var url = "importRkSerial.html?openerId=" + vl + "&nums=" + nm + "&serialNum=" + qzserialnum;
		var fea ='width=300,height=200,left=' + (screen.availWidth-300)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}	
	
	function openWin(id){
		var destination = "selThdProc.html?openerId="+id;
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function openAccount(){
		var destination = "selSkAccount.html";
		var fea ='width=400,height=200,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-200)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}		
	
	function hj(){
		var length = (document.getElementById('thtable').rows.length-2);
		
		var hjz = 0;
		for(var i=0;i<=allCount;i++){			
			var price = document.getElementById("th_price_" + i);
			
			if(price != null){
				if(!InputValid(price,0,"float",0,1,99999999,"退货价格")){
					price.focus();
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
			
			
			var xj = document.getElementById("xj_" + i);			
			
			if(xj != null){
				xj.value = (parseFloat(price.value)  * parseFloat(nums.value)).toFixed(2);				
				hjz = parseFloat(hjz) + parseFloat(xj.value);
			}
		}	
		
		var thdje = document.getElementById("thdje");
		thdje.value = hjz.toFixed(2);
		
	}	
	
	function chgType(vl){
		if(vl == "冲抵往来"){
			document.getElementById("td_zh_label").style.display = "none";
			document.getElementById("td_zh_value").style.display = "none";
			document.getElementById("zhname").value = ""
			document.getElementById("skzh").value = ""			
		}else{
			document.getElementById("td_zh_label").style.display = "";
			document.getElementById("td_zh_value").style.display = "";		
		}
	}
	
	
	function initVis(){
		if("<%=StringUtils.nullToStr(thd.getType()) %>" == "冲抵往来"){
			document.getElementById("td_zh_label").style.display = "none";
			document.getElementById("td_zh_value").style.display = "none";
			document.getElementById("zhname").value = ""
			document.getElementById("skzh").value = ""				
		}
	}	
	
	function chgKpTyle(vD){
		var obj_mc1 = document.getElementById("mc1");
		var obj_mc2 = document.getElementById("mc2");
		
		var obj_dz1 = document.getElementById("dz1");
		var obj_dz2 = document.getElementById("dz2");
		
		var obj_dh1 = document.getElementById("dh1");
		var obj_dh2 = document.getElementById("dh2");
		
		var obj_zh1 = document.getElementById("zh1");
		var obj_zh2 = document.getElementById("zh2");
		
		var obj_sh1 = document.getElementById("sh1");
		var obj_sh2 = document.getElementById("sh2");
		
		if(vD == "出库单"){
			obj_mc1.style.display = "";
			obj_mc2.style.display = "";
			
			obj_dz1.style.display = "none";
			obj_dz2.style.display = "none";
			
			obj_dh1.style.display = "none";
			obj_dh2.style.display = "none";
			
			obj_zh1.style.display = "none";
			obj_zh2.style.display = "none";
			
			obj_sh1.style.display = "none";
			obj_sh2.style.display = "none";			
		}else if(vD == "普通发票"){
			obj_mc1.style.display = "";
			obj_mc2.style.display = "";
			
			obj_dz1.style.display = "";
			obj_dz2.style.display = "";
			
			obj_dh1.style.display = "";
			obj_dh2.style.display = "";
			
			obj_zh1.style.display = "none";
			obj_zh2.style.display = "none";
			
			obj_sh1.style.display = "none";
			obj_sh2.style.display = "none";			
		}else if(vD == "增值发票"){
			obj_mc1.style.display = "";
			obj_mc2.style.display = "";
			
			obj_dz1.style.display = "";
			obj_dz2.style.display = "";
			
			obj_dh1.style.display = "";
			obj_dh2.style.display = "";
			
			obj_zh1.style.display = "";
			obj_zh2.style.display = "";
			
			obj_sh1.style.display = "";
			obj_sh2.style.display = "";			
		}	
	}	

	function chgYwType(vl){
		if(vl == "1"){
			document.getElementById("client_name").style.display = '';
			document.getElementById("client_id").style.display = 'none';
			document.getElementById("btnXsd").style.display = '';
			document.getElementById("btnLsd").style.display = 'none';
			document.getElementById("type").style.display = '';
			document.getElementById("typeLs").style.display = 'none';
		}else{
			document.getElementById("client_name").style.display = 'none';
			document.getElementById("client_id").style.display = '';
			document.getElementById("btnXsd").style.display = 'none';
			document.getElementById("btnLsd").style.display = '';
			document.getElementById("type").style.display = 'none';
			document.getElementById("typeLs").style.display = '';
		}
		document.getElementById("client_name").value = "";
		document.getElementById("client_id").value = "";
	}	
	
	function initChgYwType(vl){
		if(vl == "1"){
			document.getElementById("client_name").style.display = '';
			document.getElementById("client_id").style.display = 'none';
			document.getElementById("btnXsd").style.display = '';
			document.getElementById("btnLsd").style.display = 'none';
			document.getElementById("type").style.display = '';
			document.getElementById("typeLs").style.display = 'none';
		}else{
			document.getElementById("client_name").style.display = 'none';
			document.getElementById("client_id").style.display = '';
			document.getElementById("btnXsd").style.display = 'none';
			document.getElementById("btnLsd").style.display = '';
			document.getElementById("type").style.display = 'none';
			document.getElementById("typeLs").style.display = '';
		}
	}	

	function selXsd(){
		if(document.getElementById('client_id').value == ""){
			alert("请先选择客户！");
			return;
		}
		
		var destination = "selXsd.html?creatdate1=&creatdate2=&client_name=" + document.getElementById('client_id').value;
		var fea ='width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'关联销售单',fea);			
	}
	
	function selLsd(){
		var destination = "selLsd.html";
		var fea ='width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'关联销售单',fea);		
	}	
</script>
</head>
<body onload="initFzrTip();initClientTip();initVis();chgKpTyle('<%=StringUtils.nullToStr(thd.getFplx()) %>');initChgYwType('<%=StringUtils.nullToStr(thd.getYw_type()) %>');">
<form name="thdForm" action="updateThd.html" method="post">
<input type="hidden" name="thd.xsd_id" id="xsd_id" value="<%=StringUtils.nullToStr(thd.getXsd_id()) %>">
<input type="hidden" name="thd.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">销售退货单</td>
	</tr>
	</thead>
	<%if(!msg.equals("")){ %>
	<tr>
		<td class="a2" colspan="4"><font color="red"><%=msg %></font></td>		
	</tr>	
	<%} %>		
	<tr>
		<td class="a1" width="15%">退货单编号</td>
		<td class="a2">
		<input type="text" name="thd.thd_id" id="thd_id" style="width:232px" value="<%=StringUtils.nullToStr(thd.getThd_id()) %>" size="30" maxlength="50" readonly>
		</td>	
		<td class="a1">退货日期</td>
		<td class="a2"><input type="text" name="thd.th_date" style="width:232px" id="th_date" value="<%=StringUtils.nullToStr(thd.getTh_date()) %>"  class="Wdate" onFocus="WdatePicker()">
		</td>	
	</tr>
	<tr>			
		<td class="a1">退货类型</td>
		<td class="a2">
			<select name="thd.yw_type" id="yw_type" onchange="chgYwType(this.value);" style="width:232px">
				<option value="1" <%if(StringUtils.nullToStr(thd.getYw_type()).equals("1")) out.print("selected"); %>>销售订单</option>
				<option value="2" <%if(StringUtils.nullToStr(thd.getYw_type()).equals("2")) out.print("selected"); %>>零售单</option>
			</select> <span style="color:red">*</span>
		</td>		
		<td class="a1" width="15%">客户名称</td>
		<td class="a2">
		<input type="text" name="thd.client_id" id="client_name" style="width:232px" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(thd.getClient_name())) %>" size="30" maxlength="50" onblur="setClientValue();"><input type="text" name="thd.client_name" id="client_id" style="width:232px;display: none" value="<%=StringUtils.nullToStr(thd.getClient_name()) %>"> <font color="red">*</font>
		<div id="clientsTip" style="height:12px;position:absolute;left:147px; top:85px; width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		</td>	
	</tr>
	<tr>
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
		 <input  id="brand"  type="text" mxlength="20" style="width:232px"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(thd.getTh_fzr() )%>"/> <font color="red">*</font>
         <div id="brandTip" style=" height:12px; position:absolute;left:612px;top:85px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;"  ></div>
		    <input type="hidden" name="thd.th_fzr" id="fzr" value="<%=thd.getTh_fzr() %>"/>	
		</td>	
		<td class="a1">退款方式</td>
		<td class="a2">
			<select name="thd.type" id="type" onchange="chgType(this.value);" style="width:232px">
				<option value="现金" <%if(StringUtils.nullToStr(thd.getType()).equals("现金")) out.print("selected"); %>>现金</option>
				<option value="冲抵往来" <%if(StringUtils.nullToStr(thd.getType()).equals("冲抵往来")) out.print("selected"); %>>冲抵往来</option>
			</select>
			<select name="thd.typeLs" id="typeLs" onchange="chgType(this.value);" style="width:232px;display: none">
				<option value="现金" <%if(StringUtils.nullToStr(thd.getType()).equals("现金")) out.print("selected"); %>>现金</option>				
			</select>
			 <font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="a1">退货库房</td>
		<td class="a2">
			<select name="thd.store_id" id="store_id" style="width:232px">
				<option value=""></option>
			<%
			if(storeList != null && storeList.size()>0){
				for(int i=0;i<storeList.size();i++){
					StoreHouse storeHouse = (StoreHouse)storeList.get(i);
			%>
				<option value="<%=storeHouse.getId() %>" <%if(storeHouse.getId().equals(thd.getStore_id())) out.print("selected"); %>><%=storeHouse.getName() %></option>
			<%
				}
			}
			%>
			</select> <font color="red">*</font>	
		</td>	
		<td class="a1" width="15%">退货原因</td>
		<td class="a2">
			<input type="text" name="thd.remark" id="remark" value="<%=StringUtils.nullToStr(thd.getRemark()) %>" maxlength="100" style="width:232px"> <font color="red">*</font>
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
<table width="100%"  align="center" id="thtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="25%">商品名称</td>
		<td width="20%">规格</td>
		<td width="10%">退货价格</td>
		<td width="7%">数量</td>
		<td width="20%">强制序列号</td>
		<td width="10%">小计</td>
		<td width="8%"></td>
	</tr>
	</thead>
<%
if(thdProducts!=null && thdProducts.size()>0){
	for(int i=0;i<thdProducts.size();i++){
		ThdProduct thdProduct = (ThdProduct)thdProducts.get(i);
%>
	<tr>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="thdProducts[<%=i %>].product_name" style="width:150px" value="<%=StringUtils.nullToStr(thdProduct.getProduct_name()) %>" readonly>
			<input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin(<%=i %>);">
			<input type="hidden" id="product_id_<%=i %>" name="thdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(thdProduct.getProduct_id()) %>">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="thdProducts[<%=i %>].product_xh" style="width:150px" value="<%=StringUtils.nullToStr(thdProduct.getProduct_xh()) %>" readonly></td>
		<td class="a2">
			<input type="text" id="th_price_<%=i %>" name="thdProducts[<%=i %>].th_price" style="width:80px" value="<%=JMath.round(thdProduct.getTh_price()) %>" onblur="hj();">
			<input type="hidden" id="cbj_<%=i %>" name="thdProducts[<%=i %>].cbj" value="<%=JMath.round(thdProduct.getCbj()) %>">
			<input type="hidden" id="kh_cbj_<%=i %>" name="thdProducts[<%=i %>].kh_cbj" value="<%=JMath.round(thdProduct.getKh_cbj()) %>">
			
			<input type="hidden" id="sd_<%=i %>" name="thdProducts[<%=i %>].sd"  value="<%=JMath.round(thdProduct.getSd()) %>">
			<input type="hidden" id="gf_<%=i %>" name="thdProducts[<%=i %>].gf"  value="<%=JMath.round(thdProduct.getGf()) %>">
			<input type="hidden" id="ds_<%=i %>" name="thdProducts[<%=i %>].ds"  value="<%=JMath.round(thdProduct.getDs()) %>">
			<input type="hidden" id="basic_ratio_<%=i %>" name="thdProducts[<%=i %>].basic_ratio"  value="<%=JMath.round(thdProduct.getBasic_ratio()) %>">
			<input type="hidden" id="out_ratio_<%=i %>" name="thdProducts[<%=i %>].out_ratio"  value="<%=JMath.round(thdProduct.getOut_ratio()) %>">			
			<input type="hidden" id="lsxj_<%=i %>" name="thdProducts[<%=i %>].lsxj"  value="<%=JMath.round(thdProduct.getLsxj()) %>">
			<input type="hidden" id="ygcbj_<%=i %>" name="thdProducts[<%=i %>].ygcbj"  value="<%=JMath.round(thdProduct.getYgcbj()) %>">
			<input type="hidden" id="sfcytc_<%=i %>" name="thdProducts[<%=i %>].sfcytc"  value="<%=StringUtils.nullToStr(thdProduct.getSfcytc()) %>">				
		</td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="thdProducts[<%=i %>].nums" style="width:60px" value="<%=StringUtils.nullToStr(thdProduct.getNums()) %>" onblur="hj();"></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="thdProducts[<%=i %>].qz_serial_num" value="<%=StringUtils.nullToStr(thdProduct.getQz_serial_num()) %>" style="width:120px" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="thdProducts[<%=i %>].qz_flag" value="<%=StringUtils.nullToStr(thdProduct.getQz_flag()) %>"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>		
		<td class="a2"><input type="text" id="xj_<%=i %>" name="thdProducts[<%=i %>].xj" style="width:80px" value="<%=JMath.round(thdProduct.getXj()) %>" readonly></td>
		<%if (i>0){ %>		
		<td class="a2"><input type="button" name="delButton" value="删除" class="css_button" onclick="delTr(this);"></td>
		<%}else{ %>
		<td class="a2">&nbsp;</td>
		<%} %>
	</tr>
<%
	}
}else{
	for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="thdProducts[<%=i %>].product_name" style="width:150px" readonly><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin(<%=i %>);">
			<input type="hidden" id="product_id_<%=i %>" name="thdProducts[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="thdProducts[<%=i %>].product_xh" style="width:150px" size="10" readonly></td>
		<td class="a2">
			<input type="text" id="th_price_<%=i %>" name="thdProducts[<%=i %>].th_price" style="width:80px" value="0.00" onblur="hj();">
			<input type="hidden" id="cbj_<%=i %>" name="thdProducts[<%=i %>].cbj"  value="0.00">
			<input type="hidden" id="kh_cbj_<%=i %>" name="thdProducts[<%=i %>].kh_cbj"  value="0.00">
			
			<input type="hidden" id="sd_<%=i %>" name="thdProducts[<%=i %>].sd"  value="0.00">
			<input type="hidden" id="gf_<%=i %>" name="thdProducts[<%=i %>].gf"  value="0.00">
			<input type="hidden" id="ds_<%=i %>" name="thdProducts[<%=i %>].ds"  value="0.00">
			<input type="hidden" id="basic_ratio_<%=i %>" name="thdProducts[<%=i %>].basic_ratio"  value="0.00">
			<input type="hidden" id="out_ratio_<%=i %>" name="thdProducts[<%=i %>].out_ratio"  value="0.00">	
			<input type="hidden" id="lsxj_<%=i %>" name="thdProducts[<%=i %>].lsxj"  value="0.00">
			<input type="hidden" id="ygcbj_<%=i %>" name="thdProducts[<%=i %>].ygcbj"  value="0.00">
			<input type="hidden" id="sfcytc_<%=i %>" name="thdProducts[<%=i %>].sfcytc"  value="">				
		</td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="thdProducts[<%=i %>].nums" value="0" style="width:60px" onblur="hj();"></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="thdProducts[<%=i %>].qz_serial_num" style="width:120px" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="thdProducts[<%=i %>].qz_flag"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>			
		<td class="a2"><input type="text" id="xj_<%=i %>" name="thdProducts[<%=i %>].xj" value="0.00" style="width:80px" readonly></td>
		<%if (i>0){ %>		
		<td class="a2"><input type="button" name="delButton" value="删除" class="css_button" onclick="delTr(this);"></td>
		<%}else{ %>
		<td class="a2">&nbsp;</td>
		<%} %>
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="添加一行" class="css_button2" onclick="addTr();">
			<input type="button" name="btnXsd" id="btnXsd" value="关联销售订单" class="css_button3" onclick="selXsd();">
			<input type="button" name="btnLsd" id="btnLsd" value="关联零售单" style="display:none" class="css_button3" onclick="selLsd();">
		</td>
	</tr>
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr>
		<td class="a1" width="15%">合计退款金额</td>
		<td class="a2"><input type="text" id="thdje"  name="thd.thdje" value="<%=JMath.round(thd.getThdje()) %>" style="width:232px" readonly></td>	
		
		<td class="a1" id="td_zh_label" width="15%">退款账户</td>
		<td class="a2" id="td_zh_value" width="35%"><input type="text" id="zhname"  name="zhname" style="width:232px" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(thd.getTkzh())) %>" readonly>
		<input type="hidden" id="skzh"  name="thd.tkzh" value="<%=StringUtils.nullToStr(thd.getTkzh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>
	</tr>		
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">开票信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">发票类型</td>
		<td class="a2" width="35%">
			<select name="thd.fplx" id="fplx" onchange="chgKpTyle(this.value);" style="width:232px">
				<option value="出库单" <%if(StringUtils.nullToStr(thd.getFplx()).equals("出库单")) out.print("selected"); %>>出库单</option>
				<option value="普通发票" <%if(StringUtils.nullToStr(thd.getFplx()).equals("普通发票")) out.print("selected"); %>>普通发票</option>
				<option value="增值发票" <%if(StringUtils.nullToStr(thd.getFplx()).equals("增值发票")) out.print("selected"); %>>增值发票</option>
			</select>
		</td>
		<td class="a1" width="15%" id="mc1">名称</td>
		<td class="a2" id="mc2" width="35%"><input type="text" name="lsd.kp_mc" id="kp_mc" value="<%=StringUtils.nullToStr(thd.getKp_mc()) %>" style="width:232px" maxlength="50"></td>				
	</tr>									
	<tr>
		<td class="a1" width="15%" id="dz1" style="display:none">地址</td>
		<td class="a2" id="dz2" style="display:none"><input type="text" name="thd.kp_address" id="kp_address" value="<%=StringUtils.nullToStr(thd.getKp_address()) %>" style="width:232px" maxlength="50"></td>	
		<td class="a1" width="15%" id="dh1" style="display:none">电话</td>
		<td class="a2" id="dh2" style="display:none"><input type="text" name="thd.kp_dh" id="kp_dh" value="<%=StringUtils.nullToStr(thd.getKp_dh()) %>" style="width:232px" maxlength="20"></td>		
	</tr>
	<tr>
		<td class="a1" width="15%" id="zh1" style="display:none">开户行账号</td>
		<td class="a2"  id="zh2" style="display:none"><input type="text" name="thd.khhzh" id="khhzh" value="<%=StringUtils.nullToStr(thd.getKhhzh()) %>" style="width:232px" maxlength="50"></td>	
		<td class="a1" width="15%" id="sh1" style="display:none">税号</td>
		<td class="a2" id="sh2" style="display:none"><input type="text" name="thd.sh" id="sh" value="<%=StringUtils.nullToStr(thd.getSh()) %>" style="width:232px" maxlength="50"></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">发票信息摘要</td>
		<td class="a2" colspan="3">
			<input type="text" name="thd.fpxx" id="fpxx" value="<%=StringUtils.nullToStr(thd.getFpxx()) %>" maxlength="100" style="width:90%">
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSave" value="草 稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnSub" value="入 库" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>