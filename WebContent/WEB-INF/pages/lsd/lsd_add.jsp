<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires",0);

OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List storeList = (List)VS.findValue("storeList");

List posTypeList = (List)VS.findValue("posTypeList");
String[] ysfsArry = (String[])VS.findValue("ysfs");

Lsd lsd = (Lsd)VS.findValue("lsd");
List lsdProducts = (List)VS.findValue("lsdProducts");

int counts = 2;
if(lsdProducts != null && lsdProducts.size()>0){
	counts = lsdProducts.size() - 1;
}

//是否完成初始标志
//0：未完成；1：已完成
String iscs_flag = StringUtils.nullToStr(VS.findValue("iscs_flag"));

String msg = StringUtils.nullToStr(VS.findValue("msg"));

String flag = StringUtils.nullToStr(VS.findValue("flag"));
%>
<html>
<head>
<title>零售单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type='text/javascript' src="js/selJsr.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">

	var allCount = <%=counts %>;
	var iscs_flag = '<%=iscs_flag %>';
	var flag = "<%=flag %>";
	
	function saveInfo(vl){ 

		if(vl == '1'){
			document.getElementById("state").value = "已保存";
		}else{
			document.getElementById("state").value = "已提交";
		}

		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择！");
			return;
		}	
		if(document.getElementById("store_id").value == ""){
			alert("出货库房不能为空，请选择！");
			return;
		}			
		if(document.getElementById("client_name").value == ""){
			alert("客户姓名不能为空，请填写！");
			return;
		}
		if(document.getElementById("has_yushk").value=="是"){
			if(document.getElementById("yushk_id").value == ""){
				alert("预收款编号不能为空，请选择！");
				return;
			}
		}
		
		if(document.getElementById("fkfs").value == "刷卡"){
			if(document.getElementById("pos_id").value == ""){
				alert("请选择刷卡POS机！");
				return;
			}
		}
				
		if(!InputValid(document.getElementById("skzh"),1,"string",0,1,100,"收款账户")){return;}	
		
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
		if(document.getElementById("state").value == "已提交"){
			if(window.confirm("确认要提交零售单吗，提交后将无法修改！")){
				document.lsdForm.submit();		
			}else{
				return;
			}
		}else{
			document.lsdForm.submit();	
		}
		document.lsdForm.btnSave.disabled = true;
		document.lsdForm.btnSub.disabled = true;
	}

    function addTr(){
        var otr = document.getElementById("lsdtable").insertRow(-1);

        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        var otd=document.createElement("td");
		otd.className = "a2";
		otd.innerHTML = '<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="' + curId + '"></td>';
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="lsdProducts['+curId+'].product_name" style="width:100%" readonly><input type="hidden" id="product_id_'+curId+'" name="lsdProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="lsdProducts['+curId+'].product_xh" size="15" style="width:100%" readonly>';

        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" id="price_'+curId+'" name="lsdProducts['+curId+'].price" value="0.00" size="7" style="width:100%" onblur="hj();"><input type="hidden" id="cbj_'+curId+'" name="lsdProducts['+curId+'].cbj" value="0.00"><input type="hidden" id="kh_cbj_'+curId+'" name="lsdProducts['+curId+'].kh_cbj" value="0.00"><input type="hidden" id="gf_'+curId+'" name="lsdProducts['+curId+'].gf" value="0">';      
        
        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" id="nums_'+curId+'" name="lsdProducts['+curId+'].nums" value="0" size="5" style="width:100%" onblur="hj();">';
        
        var otd8 = document.createElement("td");
        otd8.className = "a2";
        otd8.innerHTML = '<input type="text" id="xj_'+curId+'" name="lsdProducts['+curId+'].xj" value="0.00" size="7" style="width:100%" readonly>';        
        
        var otd9 = document.createElement("td");
        otd9.className = "a2";
        otd9.innerHTML = '<input type="text" id="qz_serial_num_'+curId+'" name="lsdProducts['+curId+'].qz_serial_num" size="15" readonly><input type="hidden" id="qz_flag_'+curId+'" name="lsdProducts['+curId+'].qz_flag"><a style="cursor:hand" title="点击输入序列号" onclick="openSerialWin('+ curId +');"><b>...</b></a>&nbsp;';   
        
	    otr.appendChild(otd); 
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd3); 
        otr.appendChild(otd5);
        otr.appendChild(otd8); 
        otr.appendChild(otd9);
     }	
     
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
	}     
	
	
	function openWin(id){
		var destination = "selLsProcCkd.html?openerId="+id;
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	

	function openAccount(){
		var destination = "selSkAccount.html";
		var fea ='width=400,height=300,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择账户',fea);		
	}	
	
	function openYushk(){
		var destination = "selLsysk.html";
		var fea ='width=850,height=500,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择零售预收款',fea);		
	}		
	
	function hj(){
		var length = (document.getElementById('lsdtable').rows.length-2);
		
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
				xj.value = (parseFloat(price.value) * parseFloat(nums.value)).toFixed(2);				
				hjz = parseFloat(hjz) + parseFloat(xj.value);
				
				cbjhj = parseFloat(cbjhj) + parseFloat(cbjz.value) * parseFloat(nums.value);
				khcbjhj = parseFloat(khcbjhj) + parseFloat(khcbjz.value) * parseFloat(nums.value)
			}
		}	
		var yhje = document.getElementById("yhje");
		if(!InputValid(yhje,0,"float",0,1,99999999,"优惠金额")){
			yhje.focus();
			return;
		}		
		
		var lsdje = document.getElementById("lsdje");
		var lsdcbj = document.getElementById("lsdcbj");
		var lsdkhcbj = document.getElementById("lsdkhcb");
		
		var skje = document.getElementById("skje");

		var yushkje = document.getElementById("yushkje");

		lsdje.value = (parseFloat(hjz) - parseFloat(yhje.value)).toFixed(2);

		lsdcbj.value = cbjhj.toFixed(2);
		lsdkhcbj.value = khcbjhj.toFixed(2);
		skje.value = (parseFloat(hjz) - parseFloat(yhje.value) - parseFloat(yushkje.value)).toFixed(2);

		
	}
	
	function chgSel(vl){
		if(vl == "是"){
			document.getElementById("ysStyle").style.display = "";
		}else{
			document.getElementById("ysStyle").style.display = "none";
			document.getElementById("yushk_id").value = "";
			document.getElementById("yushkje").value = "0.00";
		}
		hj();
	}
	
	function openSerialWin(vl){
		var pn = document.getElementById("product_id_" + vl).value;
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
		
		var url = "importSerial.html?openerId=" + vl + "&nums=" + nm + "&serialNum=" + qzserialnum + "&product_id=" + pn;
		var fea ='width=300,height=200,left=' + (screen.availWidth-300)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
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
	
	//处理返回商品对象
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
							alert("商品列表中已存在该序列号，请检查！");
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
						dwr.util.setValue("gf_" + i,product.gf);
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
	
	function chgKpTyle(vD){

		if(vD == "") vD = "普通发票";
		
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

		var obj_fpxx1 = document.getElementById("fpxx1");
		var obj_fpxx2 = document.getElementById("fpxx2");
		
		if(vD == "出库单"){
			obj_mc1.style.display = "none";
			obj_mc2.style.display = "none";
			
			obj_dz1.style.display = "none";
			obj_dz2.style.display = "none";
			
			obj_dh1.style.display = "none";
			obj_dh2.style.display = "none";
			
			obj_zh1.style.display = "none";
			obj_zh2.style.display = "none";
			
			obj_sh1.style.display = "none";
			obj_sh2.style.display = "none";		

			obj_fpxx1.style.display = "none";	
			obj_fpxx2.style.display = "none";	
				
		}else if(vD == "普通发票"){
			obj_mc1.style.display = "";
			obj_mc2.style.display = "";
			
			obj_dz1.style.display = "";
			obj_dz2.style.display = "";
			
			obj_dh1.style.display = "";
			obj_dh2.style.display = "";

			obj_fpxx1.style.display = "";	
			obj_fpxx2.style.display = "";			
			
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

			obj_fpxx1.style.display = "";	
			obj_fpxx2.style.display = "";			
		}
		
		if(document.getElementById("fkfs").value == "刷卡"){
			document.getElementById("pos_id").style.display = "";
			document.getElementById("pos_id").value = "<%=StringUtils.nullToStr(lsd.getPos_id()) %>";
		}else{
			document.getElementById("pos_id").style.display = "none";
		}		
	}
	
	function selFkfs(vD){
		if(vD == "刷卡"){
			document.getElementById("pos_id").style.display = "";
		}else{
			document.getElementById("pos_id").style.display = "none";
			document.getElementById("pos_id").value = "";
		}
	}
	
	function openWin(){
		var destination = "selLsProcCkd.html";
		var fea ='width=800,height=550,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-550)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}	
	
	
	function delDesc(){
		var k = 0;
		var sel = "0"; 
		for(var i=0;i<document.lsdForm.proc_id.length;i++){
			var o = document.lsdForm.proc_id[i];
			if(o.checked){
				k = k + 1;
				sel = document.lsdForm.proc_id[i].value;
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
		document.getElementById("cbj_" + sel).value = "0.00";
		document.getElementById("kh_cbj_" + sel).value = "0.00";
		document.getElementById("gf_" + sel).value = "0.00";
		document.getElementById("nums_" + sel).value = "0";
		document.getElementById("xj_" + sel).value = "0.00";
		document.getElementById("remark_" + sel).value = "";
	}

	//判断审批标志
	function submitSp(){
		if(flag == "") return;
		if(window.confirm("销售商品低于零售限价，需要提交审批，确定提交审批吗？")){
			document.lsdForm.action = "updateLsd.html";
			document.getElementById("state").value = "已提交";
			document.lsdForm.submit();
		}else{
			window.close();
			opener.document.myform.submit();
		}
	}		
</script>

</head>
<body onload="initFzrTip();chgKpTyle('<%=StringUtils.nullToStr(lsd.getFplx()) %>');submitSp();">
<form name="lsdForm" action="saveLsd.html" method="post">
<input type="hidden" name="lsd.state" id="state" value="">
<input type="hidden" name="flag" id="state" value="<%=flag %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">零售单信息</td>
	</tr>
	</thead>
	<%if(!msg.equals("")){%><tr><td colspan="4" class="a2"><font color="red"><%=msg %></font></td></tr><%}%>	
	<tr>
		<td class="a1" width="15%">编  号</td>
		<td class="a2" width="35%"><input type="text" name="lsd.id" id="id" value="<%=StringUtils.nullToStr(lsd.getId()) %>" size="40" readonly></td>	
		<%
		String rq = StringUtils.nullToStr(lsd.getCreatdate());
		if(rq.equals("")){
			rq = DateComFunc.getToday();
		}
		%>
		<td class="a1">销售日期</td>
		<td class="a2"><input type="text" name="lsd.creatdate" id="creatdate" value="<%=rq %>" size="40" readonly>
		</td>		
	</tr>
	<tr>
		
		<td class="a1" width="15%">出货库房</td>
		<td class="a2">
			<select name="lsd.store_id" id="store_id" style="width:232px">
				<option value=""></option>
			<%
			if(storeList != null){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse storeHouse = (StoreHouse)it.next();
			%>
				<option value="<%=StringUtils.nullToStr(storeHouse.getId()) %>" <%if(StringUtils.nullToStr(storeHouse.getId()).equals(StringUtils.nullToStr(lsd.getStore_id()))) out.print("selected"); %>><%=StringUtils.nullToStr(storeHouse.getName()) %></option>
			<%
				}
			}
			%>
			</select><font color="red">*</font>
		</td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2">         
			<input id="brand" type="text" mzxlength="20" size="40" onblur="setValue()" value="<%=StaticParamDo.getRealNameById(lsd.getXsry()) %>"/> 
			<div id="brandTip"  style="height:12px;position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
			<input type="hidden" name="lsd.xsry" id="fzr" value="<%=StringUtils.nullToStr(lsd.getXsry()) %>"/><font color="red">*</font>	          
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
	<tr height="35">
		<td class="a2" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;输入序列号：<input type="text" name="s_nums" value="" onkeypress="javascript:f_enter()">
			注：输入商品序列号回车，可自动提取商品信息到商品列表中
		</td>
	</tr>	
</table>
<table width="100%"  align="center" id="lsdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="5%">选择</td>
		<td width="30%">商品名称</td>
		<td width="25%">规格</td>
		<td width="10%">销售价格</td>
		<td width="5%">数量</td>
		<td width="10%">小计</td>
		<td width="15%">强制序列号</td>
	</tr>
	</thead>
<%
if(lsdProducts != null && lsdProducts.size()>0){
	for(int i=0;i<lsdProducts.size();i++){
		LsdProduct lsdProduct = (LsdProduct)lsdProducts.get(i);
		String flag2 = "否";
		if(!StringUtils.nullToStr(lsdProduct.getQz_serial_num()).equals("")){
			flag2 = "是";
		}
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="lsdProducts[<%=i %>].product_name" value="<%=StringUtils.nullToStr(lsdProduct.getProduct_name()) %>" style="width:100%" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="lsdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(lsdProduct.getProduct_id()) %>">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="lsdProducts[<%=i %>].product_xh" size="15" value="<%=StringUtils.nullToStr(lsdProduct.getProduct_xh()) %>" style="width:100%" readonly></td>	
		<td class="a2">
			<input type="text" id="price_<%=i %>" name="lsdProducts[<%=i %>].price" value="<%=JMath.round(lsdProduct.getPrice()) %>" size="7" style="width:100%" onblur="hj();">
			<input type="hidden" id="cbj_<%=i %>" name="lsdProducts[<%=i %>].cbj" value="<%=JMath.round(lsdProduct.getCbj()) %>">
			<input type="hidden" id="kh_cbj_<%=i %>" name="lsdProducts[<%=i %>].kh_cbj" value="<%=JMath.round(lsdProduct.getKh_cbj()) %>">
			<input type="hidden" id="gf_<%=i %>" name="lsdProducts[<%=i %>].gf" value="<%=lsdProduct.getGf() %>">
		</td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="lsdProducts[<%=i %>].nums" value="<%=lsdProduct.getNums() %>" size="5" style="width:100%" onblur="hj();"></td>
		<td class="a2"><input type="text" id="xj_<%=i %>" name="lsdProducts[<%=i %>].xj" value="<%=JMath.round(lsdProduct.getXj()) %>" size="7" style="width:100%" readonly></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="lsdProducts[<%=i %>].qz_serial_num" value="<%=StringUtils.nullToStr(lsdProduct.getQz_serial_num()) %>" size="15" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="lsdProducts[<%=i %>].qz_flag" value="<%=flag2 %>"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>		
	</tr>
<%
	}
}else{
	for(int i=0;i<3;i++){
		%>
			<tr>
				<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
				<td class="a2">
					<input type="text" id="product_name_<%=i %>" name="lsdProducts[<%=i %>].product_name" style="width:100%" readonly>
					<input type="hidden" id="product_id_<%=i %>" name="lsdProducts[<%=i %>].product_id">
				</td>
				<td class="a2"><input type="text" id="product_xh_<%=i %>" name="lsdProducts[<%=i %>].product_xh" size="15" style="width:100%" readonly></td>	
				<td class="a2">
					<input type="text" id="price_<%=i %>" name="lsdProducts[<%=i %>].price" value="0.00" size="7" style="width:100%" onblur="hj();">
					<input type="hidden" id="cbj_<%=i %>" name="lsdProducts[<%=i %>].cbj" value="0.00">
					<input type="hidden" id="kh_cbj_<%=i %>" name="lsdProducts[<%=i %>].kh_cbj" value="0.00">
					<input type="hidden" id="gf_<%=i %>" name="lsdProducts[<%=i %>].gf" value="0">
				</td>
				<td class="a2"><input type="text" id="nums_<%=i %>" name="lsdProducts[<%=i %>].nums" value="0" size="5" style="width:100%" onblur="hj();"></td>
				<td class="a2"><input type="text" id="xj_<%=i %>" name="lsdProducts[<%=i %>].xj" value="0.00" size="7" style="width:100%" readonly></td>
				<td class="a2">
					<input type="text" id="qz_serial_num_<%=i %>" name="lsdProducts[<%=i %>].qz_serial_num" size="15" readonly>
					<input type="hidden" id="qz_flag_<%=i %>" name="lsdProducts[<%=i %>].qz_flag"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
				</td>				
			</tr>
		<%
	}
}
%>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" colspan="4" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="添加商品" class="css_button3" onclick="openWin();">
			<input type="button" name="button8" value="清除商品" class="css_button3" onclick="delDesc();">
		</td>
	</tr>
	<tr height="35">	
		<td class="a1">合计金额</td>
		<td class="a2" colspan="3">
			<input type="text" name="lsd.lsdje" id="lsdje" value="<%=JMath.round(lsd.getLsdje()) %>" size="40" readonly>
			<input type="hidden" name="lsd.lsdcbj" id="lsdcbj" value="<%=JMath.round(lsd.getLsdcbj()) %>" readonly>
			<input type="hidden" name="lsd.lsdkhcb" id="lsdkhcb" value="<%=JMath.round(lsd.getLsdkhcb()) %>" readonly>
			<input type="hidden" name="lsd.yhje" id="yhje" value="0.00">
		</td>
	</tr>
	<%
	String cssStyle = "display:none";
	if(StringUtils.nullToStr(lsd.getHas_yushk()).equals("是")){
		cssStyle = "";
	}
	%>
	<tr height="35">
		<td class="a1" width="15%">本次实收金额</td>
		<td class="a2" width="35%">
			<input type="text" name="lsd.skje" id="skje" value="<%=JMath.round(lsd.getSkje()) %>" size="40" readonly>
		</td>	
		<td class="a1" width="15%">是否存在预收款</td>
		<td class="a2" width="35%">
			<select name="lsd.has_yushk" id="has_yushk" onchange="chgSel(this.value);" style="width:232px">
				<option value="否" <%if(StringUtils.nullToStr(lsd.getHas_yushk()).equals("否")) out.print("selected"); %>>否</option>
				<option value="是" <%if(StringUtils.nullToStr(lsd.getHas_yushk()).equals("是")) out.print("selected"); %>>是</option>
			</select>
		</td>	
	</tr>
	<tr height="35" id="ysStyle" style="<%=cssStyle %>">	
		<td class="a1">预收款编号</td>
		<td class="a2">
			<input type="text" name="lsd.yushk_id" id="yushk_id" value="<%=StringUtils.nullToStr(lsd.getYushk_id()) %>" size="40" onclick="openYushk();" readonly>
			<input type="button" name="btnSel" value="选择" class="css_button" onclick="openYushk();">
		</td>
		<td class="a1">预收金额</td>
		<td class="a2">
			<input type="text" name="lsd.yushkje" id="yushkje" value="<%=JMath.round(lsd.getYushkje()) %>" size="40" readonly>
		</td>		
	</tr>		
	<tr>
		<td class="a1" widht="20%">客户付款方式</td>
		<td class="a2">
			<select name="lsd.fkfs" id="fkfs" onchange="selFkfs(this.value);">
			<%
			if(ysfsArry != null && ysfsArry.length > 0){
				for(int i =0;i<ysfsArry.length;i++){
			%>
				<option value="<%=ysfsArry[i] %>" <%if(StringUtils.nullToStr(lsd.getFkfs()).equals(ysfsArry[i])) out.print("selected"); %>><%=ysfsArry[i] %></option>
			<%
				}
			}
			%>
				
			</select>	
			
			<select name="lsd.pos_id" id="pos_id" style="display:none;">
				<option value=""></option>
			<%
			if(posTypeList != null && posTypeList.size() > 0){
				for(int i =0;i<posTypeList.size();i++){
					PosType posType = (PosType)posTypeList.get(i);
			%>
				<option value="<%=posType.getId() %>" <%if(StringUtils.nullToStr(lsd.getFkfs()).equals(posType.getId())) out.print("selected"); %>><%=posType.getName() %></option>
			<%
				}
			}
			%>
				
			</select>							
		</td>	
		<td class="a1">收款账户</td>
		<td class="a2"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(lsd.getSkzh())) %>" onclick="openAccount();" readonly size="40">
		<input type="hidden" id="skzh"  name="lsd.skzh" value="<%=StringUtils.nullToStr(lsd.getSkzh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand"><font color="red">*</font>
		</td>
	</tr>	
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">客户信息</td>
	</tr>
	</thead>			
	<tr>
		<td class="a1" width="15%">客户名称</td>
		<td class="a2" width="35%"><input type="text" name="lsd.client_name" id="client_name" value="<%=StringUtils.nullToStr(lsd.getClient_name()) %>" size="40" maxlength="50"><font color="red">*</font>
		</td>
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><input type="text" name="lsd.lxr" id="lxr" value="<%=StringUtils.nullToStr(lsd.getLxr()) %>" maxleng="20" size="40"></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2"><input type="text" name="lsd.lxdh" id="lxdh" value="<%=StringUtils.nullToStr(lsd.getLxdh()) %>" maxlength="20" size="40"></td>	
		<td class="a1" width="15%">手机</td>
		<td class="a2"><input type="text" name="lsd.mobile" id="mobile" value="<%=StringUtils.nullToStr(lsd.getMobile()) %>" maxlength="20" size="40"></td>			
	</tr>
	<tr>
		<td class="a1" width="15%">地址</td>
		<td class="a2"><input type="text" name="lsd.address" id="address" value="<%=StringUtils.nullToStr(lsd.getAddress()) %>" size="40" maxlength="100"></td>			
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2"><input type="text" name="lsd.mail" id="mail" value="<%=StringUtils.nullToStr(lsd.getMail()) %>" maxlength="50" size="40"></td>		
	</tr>	
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="4">开票信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">发票类型</td>
		<td class="a2">
			<select name="lsd.fplx" id="fplx" onchange="chgKpTyle(this.value);" style="width:232px">
				<option value="普通发票" <%if(StringUtils.nullToStr(lsd.getFplx()).equals("普通发票")) out.print("selected"); %>>普通发票</option>
				<option value="出库单" <%if(StringUtils.nullToStr(lsd.getFplx()).equals("出库单")) out.print("selected"); %>>出库单</option>				
				<option value="增值发票" <%if(StringUtils.nullToStr(lsd.getFplx()).equals("增值发票")) out.print("selected"); %>>增值发票</option>
			</select>
		</td>
		<td class="a1" width="15%" id="mc1">名称</td>
		<td class="a2" id="mc2"><input type="text" name="lsd.kp_mc" id="kp_mc" value="<%=StringUtils.nullToStr(lsd.getKp_mc()) %>" maxlength="50" size="40"></td>				
	</tr>									
	<tr>
		<td class="a1" width="15%" id="dz1" style="display:none">地址</td>
		<td class="a2" id="dz2" style="display:none"><input type="text" name="lsd.kp_address" id="kp_address" value="<%=StringUtils.nullToStr(lsd.getKp_address()) %>" size="40" maxlength="50"></td>	
		<td class="a1" width="15%" id="dh1" style="display:none">电话</td>
		<td class="a2" id="dh2" style="display:none"><input type="text" name="lsd.kp_dh" id="kp_dh" value="<%=StringUtils.nullToStr(lsd.getKp_dh()) %>" maxlength="20" size="40"></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%" id="zh1" style="display:none">开户行账号</td>
		<td class="a2"  id="zh2" style="display:none"><input type="text" name="lsd.khhzh" id="khhzh" value="<%=StringUtils.nullToStr(lsd.getKhhzh()) %>" maxlength="50" size="40"></td>	
		<td class="a1" width="15%" id="sh1" style="display:none">税号</td>
		<td class="a2" id="sh2" style="display:none"><input type="text" name="lsd.sh" id="sh" value="<%=StringUtils.nullToStr(lsd.getSh()) %>" maxlength="50" size="40"></td>		
	</tr>	
	
	<tr>
		<td class="a1" width="15%" id="fpxx1">发票信息摘要</td>
		<td class="a2" colspan="3" id="fpxx2">
			<input type="text" name="lsd.fpxx" id="fpxx" value="<%=StringUtils.nullToStr(lsd.getFpxx()) %>" style="width:75%" maxlength="100">
		</td>	
				
	</tr>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%" colspan="3">
			<input type="text" name="lsd.ms" id="ms" value="<%=StringUtils.nullToStr(lsd.getMs()) %>" style="width:75%" maxlength="100">
		</td>
	</tr>			
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSave" value="草 稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="btnSub" value="提 交" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.opener.document.myform.submit();window.close();">
		</td>
	</tr>
</table>
<BR>
</form>
</BODY>
</HTML>