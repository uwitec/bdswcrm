<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List userList = (List)VS.findValue("userList");
List storeList = (List)VS.findValue("storeList");
List clientsList=(List)VS.findValue("clientsList");
List posTypeList = (List)VS.findValue("posTypeList");

String[] ysfsArry = (String[])VS.findValue("ysfs");
String[] fkfsArry = (String[])VS.findValue("fkfs");

List xsdProducts = (List)VS.findValue("xsdProducts");
Xsd xsd = (Xsd)VS.findValue("xsd");
 

LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
String user_id = info.getUser_id();

if(!StringUtils.nullToStr(xsd.getFzr()).equals("")){
	user_id = StringUtils.nullToStr(xsd.getFzr());
}

//是否完成初始标志
//0：未完成；1：已完成
String iscs_flag = StringUtils.nullToStr(VS.findValue("iscs_flag"));

int allCount = 2;
if(xsdProducts != null && xsdProducts.size()>0){
	allCount = xsdProducts.size() -1;
}

List msg = (List)session.getAttribute("messages");
session.removeAttribute("messages");

String sp_state = StringUtils.nullToStr(xsd.getSp_state());

String sptg_flag = "";
if(sp_state.equals("3")){
	sptg_flag = "readonly";
}

String sp_type = StringUtils.nullToStr(xsd.getSp_type());

String spMsg = "";
if(sp_state.equals("1")){
	if(sp_type.equals("1")){
		spMsg = "客户存在超期应付款，需相关人员审批方可出库，提交审批请点击“提交审批”按钮。";
	}else if(sp_type.equals("2")){
		spMsg = "超出客户限额并且订单商品价格低于最低限价，需相关人员审批方可出库，提交审批请点击“提交审批”按钮。";	
	}else if(sp_type.equals("3")){
		spMsg = "超出客户限额，需相关人员审批方可出库，提交审批请点击“提交审批”按钮。";	
	}else if(sp_type.equals("4")){
		spMsg = "订单商品价格低于最低限价，需相关人员审批方可出库，提交审批请点击“提交审批”按钮，或修改价格后再次提交。";
	}
}
%>

<html>
<head>
<title>销售订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">

	var allCount = <%=allCount %>;
	var iscs_flag = '<%=iscs_flag %>';	
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
		
		//判断是否存在强制输入序列号的产品没有输入序列号
		if(document.getElementById("state").value == "已出库"){
			
			if(document.getElementById("store_id").value == ""){
				alert("请选择出货库房！");
				return;
			}

			//强制序列号校验
			for(var i=0;i<=allCount;i++){
				var qzflag = document.getElementById("qz_flag_" + i);            //标志是否强制输入
				var qzserialnum = document.getElementById("qz_serial_num_" + i); //序列号
				var pn = document.getElementById("product_name_" + i);           //产品名称
				
				if(qzflag != null){
					if(qzflag.value == "是"){
						if(qzserialnum.value == ""){
							//如果没有输入序列号提示用户输入序列号
							alert("产品" + pn.value + "强制序列号，请先输入序列号！");
							qzserialnum.focus();
							return;
						}else{
							//校验输入数量与产品数是否相同
							var serial = document.getElementById("qz_serial_num_" + i).value;
							var arrySerial = serial.split(",");
							
							var nms = document.getElementById("nums_" + i).value;
							
							if(parseInt(nms) != arrySerial.length){
								alert("产品" + pn.value + "输入序列号数量与产品数量不符，请检查！");
								qzserialnum.focus();
								return;
							}
						
						}
					}
				}
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
		
		//判断是否存在强制输入序列号的产品没有输入序列号
		if(document.getElementById("state").value == "已出库"){
			
			if(document.getElementById("store_id").value == ""){
				alert("请选择出货库房！");
				return;
			}

			//强制序列号校验
			for(var i=0;i<=allCount;i++){
				var qzflag = document.getElementById("qz_flag_" + i);            //标志是否强制输入
				var qzserialnum = document.getElementById("qz_serial_num_" + i); //序列号
				var pn = document.getElementById("product_name_" + i);           //产品名称
				
				if(qzflag != null){
					if(qzflag.value == "是"){
						if(qzserialnum.value == ""){
							//如果没有输入序列号提示用户输入序列号
							alert("产品" + pn.value + "强制序列号，请先输入序列号！");
							qzserialnum.focus();
							return;
						}else{
							//校验输入数量与产品数是否相同
							var serial = document.getElementById("qz_serial_num_" + i).value;
							var arrySerial = serial.split(",");
							
							var nms = document.getElementById("nums_" + i).value;
							
							if(parseInt(nms) != arrySerial.length){
								alert("产品" + pn.value + "输入序列号数量与产品数量不符，请检查！");
								qzserialnum.focus();
								return;
							}
						
						}
					}
				}
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
	
	function $(id) {return document.getElementById(id);}
	function $F(name){return document.getElementsByTagName(name);}
      	
    function addTr(){
        var otr = document.getElementById("xsdtable").insertRow(-1);
        
        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        
        var otd9=document.createElement("td");
        otd9.className = "a2";
        otd9.innerHTML = '<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="' + curId + '"></td>';
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="xsdProducts['+curId+'].product_name" readonly><input type="hidden" id="product_id_'+curId+'" name="xsdProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="xsdProducts['+curId+'].product_xh" size="10" readonly>';
       
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" id="price_'+curId+'" name="xsdProducts['+curId+'].price" value="0.00" size="10" onblur="hj();"><input type="hidden" id="cbj_'+curId+'" name="xsdProducts['+curId+'].cbj" value="0.00"><input type="hidden" id="kh_cbj_'+curId+'" name="xsdProducts['+curId+'].kh_cbj" value="0.00"><input type="hidden" id="jgtz_'+curId+'" name="xsdProducts['+curId+'].jgtz" value="0.00" size="10" onblur="hj();">';

        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" id="nums_'+curId+'" name="xsdProducts['+curId+'].nums" value="0" size="5" onblur="hj();">';
        
        var otd8 = document.createElement("td");
        otd8.className = "a2";
        otd8.innerHTML = '<input type="text" id="xj_'+curId+'" name="xsdProducts['+curId+'].xj" value="0.00" size="10" readonly>';        
        
        var otd11 = document.createElement("td");
        otd11.className = "a2";
        otd11.innerHTML = '<input type="text" id="qz_serial_num_'+curId+'" name="xsdProducts['+curId+'].qz_serial_num" size="15" readonly><input type="hidden" id="qz_flag_'+curId+'" name="xsdProducts['+curId+'].qz_flag"><a style="cursor:hand" title="点击输入序列号" onclick="openSerialWin('+ curId +');"><b>...</b></a>&nbsp;';   
        
        
        var otd6 = document.createElement("td");
        otd6.className = "a2";
        otd6.innerHTML = '<input type="text" id="remark_'+curId+'" name="xsdProducts['+curId+'].remark">';                       

		otr.appendChild(otd9); 
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd3);
        otr.appendChild(otd5);
        otr.appendChild(otd8); 
        otr.appendChild(otd11); 
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
		var length = ($('xsdtable').rows.length-2);
		
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
		if(vl == "现结"){
			document.getElementById("lb_xjd").style.display = "";
			document.getElementById("tr_skxx").style.display = "";
			document.getElementById("xjd").style.display = "";
			document.getElementById("xjd").value = "0.00";
			
			document.getElementById("lb_zq").style.display = "none";
			document.getElementById("zq").style.display = "none";
			document.getElementById("zq").value = "0";		
		}else if(vl == "账期"){
			document.getElementById("lb_xjd").style.display = "none";
			document.getElementById("tr_skxx").style.display = "none";
			document.getElementById("xjd").style.display = "none";
			document.getElementById("xjd").value = "0.00";
			
			document.getElementById("lb_zq").style.display = "";
			document.getElementById("zq").style.display = "";
			document.getElementById("zq").value = "0";			
		}else{
			document.getElementById("tr_skxx").style.display = "";
			document.getElementById("lb_xjd").style.display = "none";
			document.getElementById("xjd").style.display = "none";
			document.getElementById("xjd").value = "0.00";
			
			document.getElementById("lb_zq").style.display = "none";
			document.getElementById("zq").style.display = "none";
			document.getElementById("zq").value = "0";			
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
 
 
	

</script>
</head>
<body>
<form name="xsdForm" action="updateXsd.html" method="post">
<input type="hidden" name="xsd.sp_type" id="sp_type" value="<%=StringUtils.nullToStr(xsd.getSp_type()) %>">
<input type="hidden" name="xsd.sp_state" id="sp_state" value="<%=StringUtils.nullToStr(xsd.getSp_state()) %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">销售订单信息</td>
	</tr>
	</thead>
	<%
	if(msg != null && msg.size() > 0){
		for(int i=0;i<msg.size();i++){
	%>
	<tr>
		<td colspan="4" class="a1"><font color="red"><%=StringUtils.nullToStr(msg.get(i)) %></font></td>
	</tr>
	<%
		}
	}
	if(!spMsg.equals("")){
	%>
		<tr>
			<td colspan="4" class="a1"><font color="red"><%=spMsg %></font></td>
		</tr>
	<%		
	}
	%>
	<tr>
		<td class="a1" width="15%">销售订单编号</td>
		<td class="a2" width="35%"><input type="text" name="xsd.id" id="id" value="<%=StringUtils.nullToStr(xsd.getId()) %>" size="30" readonly><font color="red">*</font>
		</td>
			<td class="a1"  width="15%">经手人</td>
		<td class="a2">
            <!--修改 --------------------------------------------------------------------------------------  -->
		 <input  id="brand"    type="text"   length="20"  onblur="setValue()" value="<%StaticParamDo.getRealNameById(xsd.getFzr()); %>"/> 
         <img src="images/select.gif" align="absmiddle" title="选择经手人" border="0" onclick="openywyWin();" style="cursor:hand">
          <div   id="brandTip"  style="height:12px;position:absolute;left:610px; top:55px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
          </div>
		    <input type="hidden" name="xsd.fzr" id="fzr" value="<%=xsd.getFzr()%>"/> 
		<!--修改 --------------------------------------------------------------------------------------  --><font color="red">*</font>		
		</td>				 
	</tr>
	<tr>	
		<td class="a1" width="15%">客户名称</td>
		<td class="a2">
		<input type="text" name="xsd.client_id" onblur="setvalues()" id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(xsd.getClient_name())) %>" size="30" maxlength="50" >
		<input type="hidden" name="xsd.client_name" id="client_id" value="<%=StringUtils.nullToStr(xsd.getClient_name()) %>">
		<%
		if(!sp_state.equals("3")){
		%>
		<img src="images/select.gif" align="absmiddle" title="选择客户" border="0" onclick="openClientWin();" style="cursor:hand">
		<%
		}
		%>
		<div id="clientsTip" style="height:12px;position:absolute;left:150px; top:85px; width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		<font color="red">*</font>
		</td>
		<td class="a1">地址</td>
		<td class="a2"><input type="text" name="xsd.kh_address" id="kh_address" value="<%=StringUtils.nullToStr(xsd.getKh_address()) %>"></td>	
	</tr>
	
	<tr>
		<td class="a1">客户联系人</td>
		<td class="a2"><input type="text" name="xsd.kh_lxr" id="kh_lxr" value="<%=StringUtils.nullToStr(xsd.getKh_lxr()) %>"></td>	
		<td class="a1">联系电话</td>
		<td class="a2"><input type="text" name="xsd.kh_lxdh" id="kh_lxdh" value="<%=StringUtils.nullToStr(xsd.getKh_lxdh()) %>"></td>
	</tr>
	<tr>
		
		
		<td class="a1" width="15%">客户付款类型</td>
		<td class="a2">
			<select name="xsd.sklx" id="sklx" onchange="changeSklx(this.value);">
				<option value=""></option>
				<option value="现结" <%if(StringUtils.nullToStr(xsd.getSklx()).equals("现结")) out.print("selected"); %>>现结</option>
				<option value="账期" <%if(StringUtils.nullToStr(xsd.getSklx()).equals("账期")) out.print("selected"); %>>账期</option>
			</select>&nbsp;&nbsp;
			<b id="lb_zq" style="<%if(StringUtils.nullToStr(xsd.getSklx()).equals("账期")) out.print(""); else out.print("display:none"); %>">设置账期(天)：</b>
			<input type="text" name="xsd.zq" id="zq" value="<%=xsd.getZq() %>" size="5" style="<%if(StringUtils.nullToStr(xsd.getSklx()).equals("账期")) out.print(""); else out.print("display:none"); %>" <%=sptg_flag %>>
			<b id="lb_xjd" style="<%if(StringUtils.nullToStr(xsd.getSklx()).equals("现结")) out.print(""); else out.print("display:none"); %>">现金点：</b>
			<input type="text" name="xsd.xjd" id="xjd" value="<%=xsd.getXjd() %>" size="5" style="<%if(StringUtils.nullToStr(xsd.getSklx()).equals("现结")) out.print(""); else out.print("display:none"); %>">
			<font color="red">*</font>
		</td>
		<%
		String creatDate = StringUtils.nullToStr(xsd.getCreatdate());
		if(creatDate.equals("")){
			creatDate = DateComFunc.getToday();
		}
		%>
		<td class="a1">创建时间</td>
		<td class="a2"><input type="text" name="xsd.creatdate" id="creatdate" value="<%=creatDate %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('creatdate')); return false;"><font color="red">*</font>
		</td>
	
		
	</tr>
	<tr>
		<td class="a1" width="15%">运输方式</td>
		<td class="a2" width="35%"> 
			<%
		String[] ysfss = ysfsArry; 
		%>
			<select name="xsd.ysfs" id="ysfs">
				<option value=""></option>
			<%
			if(ysfss != null && ysfss.length > 0){
				for(int i =0;i<ysfss.length;i++){
			%>
				<option value="<%=ysfss[i] %>" <%if(ysfss[i].equals(StringUtils.nullToStr(xsd.getYsfs()))) out.print("selected"); %>><%=ysfss[i] %></option>
			<%
				}
			}
			%>
				
			</select>	
		</td>	
		
		<td class="a1" width="15%">订单状态</td>
		<td class="a2">
			<select name="xsd.state" id="state" onchange="chgState(this.value);">
				<option value="已保存" <%if(StringUtils.nullToStr(xsd.getState()).equals("已保存")) out.print("selected"); %>>保存</option>
				<option value="已提交" <%if(StringUtils.nullToStr(xsd.getState()).equals("已提交")) out.print("selected"); %>>提交</option>
				<option value="已出库" <%if(StringUtils.nullToStr(xsd.getState()).equals("已出库")) out.print("selected"); %>>出库</option>
			</select>
			
			<%
			String store_id = xsd.getStore_id();
			String diplayFlag = "display:none";
			if(StringUtils.nullToStr(xsd.getState()).equals("已出库")){
				diplayFlag = "";
			}
			%>
			<b id="lb_store_id" style="<%=diplayFlag %>">出货仓库：</b>
			<select name="xsd.store_id" id="store_id" style="<%=diplayFlag %>">
				<option value=""></option>
			<%
			if(storeList != null && storeList.size()>0){
				for(int i=0;i<storeList.size();i++){
					StoreHouse storeHouse = (StoreHouse)storeList.get(i);
			%>
				<option value="<%=storeHouse.getId() %>" <%if(storeHouse.getId().equals(store_id)) out.print("selected"); %>><%=storeHouse.getName() %></option>
			<%
				}
			}
			%>
			</select>
		</td>			
	</tr>
	
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">产品详细信息</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="xsdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>选择</td>
		<td>产品名称</td>
		<td>规格</td>
		<td>销售价格</td>
		<td>数量</td>
		<td>小计</td>
		<td>强制序列号</td>
		<td>备注</td>
	</tr>
	</thead>
<%
if(xsdProducts!=null && xsdProducts.size()>0){
	for(int i=0;i<xsdProducts.size();i++){
		XsdProduct xsdProduct = (XsdProduct)xsdProducts.get(i);
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="xsdProducts[<%=i %>].product_name" value="<%=StringUtils.nullToStr(xsdProduct.getProduct_name()) %>" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="xsdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(xsdProduct.getProduct_id()) %>">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="xsdProducts[<%=i %>].product_xh" size="10" value="<%=StringUtils.nullToStr(xsdProduct.getProduct_xh()) %>" readonly></td>
		<td class="a2">
			<input type="text" id="price_<%=i %>" name="xsdProducts[<%=i %>].price" value="<%=JMath.round(xsdProduct.getPrice()) %>" size="10" onblur="hj();" <%=sptg_flag %>>
			<input type="hidden" id="cbj_<%=i %>" name="xsdProducts[<%=i %>].cbj" value="<%=JMath.round(xsdProduct.getCbj()) %>">
			<input type="hidden" id="kh_cbj_<%=i %>" name="xsdProducts[<%=i %>].kh_cbj" value="<%=JMath.round(xsdProduct.getKh_cbj()) %>">
			<input type="hidden" id="jgtz_<%=i %>" name="xsdProducts[<%=i %>].jgtz" value="<%=JMath.round(xsdProduct.getJgtz()) %>" size="10" onblur="hj();">
		</td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="xsdProducts[<%=i %>].nums" value="<%=xsdProduct.getNums() %>" <%=sptg_flag %> size="5" onblur="hj();"></td>
		<td class="a2"><input type="text" id="xj_<%=i %>" name="xsdProducts[<%=i %>].xj" value="<%=JMath.round(xsdProduct.getXj()) %>" size="10" readonly></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="xsdProducts[<%=i %>].qz_serial_num" value="<%=StringUtils.nullToStr(xsdProduct.getQz_serial_num()) %>" size="15" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="xsdProducts[<%=i %>].qz_flag" value="<%=StringUtils.nullToStr(xsdProduct.getQz_flag()) %>"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>		
		<td class="a2"><input type="text" id="remark_<%=i %>" name="xsdProducts[<%=i %>].remark" value="<%=StringUtils.nullToStr(xsdProduct.getRemark()) %>"></td>
	</tr>
<%
	}
}else{
	for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="xsdProducts[<%=i %>].product_name" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="xsdProducts[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="xsdProducts[<%=i %>].product_xh" size="10" readonly></td>
		<td class="a2">
			<input type="text" id="price_<%=i %>" name="xsdProducts[<%=i %>].price" value="0.00" size="10" onblur="hj();">
			<input type="hidden" id="cbj_<%=i %>" name="xsdProducts[<%=i %>].cbj" value="0.00">
			<input type="hidden" id="kh_cbj_<%=i %>" name="xsdProducts[<%=i %>].kh_cbj" value="0.00">
			<input type="hidden" id="jgtz_<%=i %>" name="xsdProducts[<%=i %>].jgtz" value="0.00" size="10" onblur="hj();">
		</td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="xsdProducts[<%=i %>].nums" value="0" size="5" onblur="hj();"></td>
		<td class="a2"><input type="text" id="xj_<%=i %>" name="xsdProducts[<%=i %>].xj" value="0.00" size="10" readonly></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="xsdProducts[<%=i %>].qz_serial_num" value="" size="15" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="xsdProducts[<%=i %>].qz_flag" value=""><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>		
		<td class="a2"><input type="text" id="remark_<%=i %>" name="xsdProducts[<%=i %>].remark"></td>
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<%
	if(!sp_state.equals("3")){
	%>
	<tr height="35">
		<td class="a2" colspan="4" width="100%">&nbsp;
			<input type="button" name="button1" value="添加产品" class="css_button3" onclick="openWin();">
			<input type="button" name="button8" value="清除产品" class="css_button3" onclick="delDesc();">
			&nbsp;&nbsp;&nbsp;输入序列号：<input type="text" name="s_nums" value="" onkeypress="javascript:f_enter()">
			<font color="red">注：输入产品序列号回车，自动提取产品信息。</font>
		</td>
	</tr>
	<%
	}
	%>
	<tr height="35">	
		<td class="a1">订单合计金额</td>
		<td class="a2">
			<input type="text" name="xsd.xsdje" id="xsdje" value="<%=JMath.round(xsd.getXsdje()) %>" size="7" readonly>
			<input type="hidden" name="xsd.xsdcbj" id="xsdcbj" value="<%=JMath.round(xsd.getXsdcbj()) %>">
			<input type="hidden" name="xsd.xsdkhcb" id="xsdkhcb" value="<%=JMath.round(xsd.getXsdkhcb()) %>">
			<input type="hidden" name="xsd.yhje" id="yhje" value="0.00">
			<input type="hidden" id="ysje"  name="xsd.ysje" value="0.00">元
		</td>
		<td class="a1" widht="20%">收款账户</td>				
		<td class="a2"><input type="text" id="zhname"  name="zhname" value="<%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(xsd.getSkzh())) %>" readonly>
		<input type="hidden" id="skzh"  name="xsd.skzh" value="<%=StringUtils.nullToStr(xsd.getSkzh()) %>">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>		
	</tr>
	<%
	String skxxStyle = "";
	if(StringUtils.nullToStr(xsd.getSklx()).equals("账期")){
		skxxStyle = "display:none";
	}
	%>
	<tr id="tr_skxx" style="<%=skxxStyle %>">
		<td class="a1">本次收款金额</td>
		<td class="a2"><input type="text" id="skje"  name="xsd.skje" value="<%=JMath.round(xsd.getSkje()) %>" size="7">元</td>
		
		<td class="a1" widht="20%">收款方式</td>
		<td class="a2">
			<select name="xsd.skfs" id="skfs"  onchange="selSkfs(this.value);">
			<%
			if(fkfsArry != null && fkfsArry.length > 0){
				for(int i =0;i<fkfsArry.length;i++){
			%>
				<option value="<%=fkfsArry[i] %>" <%if(fkfsArry[i].equals(StringUtils.nullToStr(xsd.getSkfs()))) out.print("selected"); %>><%=fkfsArry[i] %></option>
			<%
				}
			}
			
			String khskfs = StringUtils.nullToStr(xsd.getSkfs());
			String cssStyle = "display:none";
			if(khskfs.equals("刷卡")){
				cssStyle = "";
			}
			%>
			</select>
			
			<select name="xsd.pos_id" id="pos_id" style="<%=cssStyle %>">
				<option value="">选择刷卡POS机</option>
			<%
			String pos_id = StringUtils.nullToStr(xsd.getPos_id());
			if(posTypeList != null && posTypeList.size() > 0){
				for(int i =0;i<posTypeList.size();i++){
					PosType posType = (PosType)posTypeList.get(i);
			%>
				<option value="<%=posType.getId() %>" <%if(pos_id.equals(posType.getId()+"")) out.print("selected"); %>><%=posType.getName() %></option>
			<%
				}
			}
			%>				
			</select><font color="red">*</font>				
		</td>		
	</tr>
	<tr>
		<td class="a1" width="15%">备&nbsp;&nbsp;注</td>
		<td class="a2" width="85%" colspan="3">
			<textarea rows="2" name="xsd.ms" id="ms" style="width:75%"><%=StringUtils.nullToStr(xsd.getMs()) %></textarea>
		</td>
	</tr>		
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="btnSub" value="确 定" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;
			<%
			if(!spMsg.equals("")){
			%>
			<input type="button" name="btnTjsp" value="提交审批" class="css_button3" onclick="submitInfo();">&nbsp;&nbsp;
			<%
			}
			%>
			<input type="reset" name="btnCz" value="重 置" class="css_button2">&nbsp;&nbsp;
			<input type="reset" name="btnClose" value="关 闭" class="css_button2" onclick="window.close();;">
		</td>
	</tr>
</table>
</form>
</body>
</html>
<!-- 修改 ----------------------------------------------------------------------------------------------------------------------->
<!-- searchBrand  查询相近的经手人-->
<!-- showResponse 展示-->
<!-- move 上下事件-->
<!-- down 鼠标按下事件-->
<!-- setValue 鼠标离开事件-->
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type="text/javascript">
var tip = "";
function searchBrand()
{
    
	var url = 'getBrands.do';
	var params = "prefix=" + $F('brand');
	var myAjax = new Ajax.Request(
	url,
	{
		method:'post',
		parameters: params,
		onComplete: showResponse,
		asynchronous:true
	});
}
var list;
function showResponse(originalRequest)
{   
	var brandLists = originalRequest.responseText.split("%");
	list=brandLists;
     
	var brandList=brandLists[0].split("$");
	 
	if ( brandList.length > 1)
	{
		var bt = $("brandTip");
		var s="";
		var flog=0;
		for(var i = 0 ; i <  brandList.length; i++)  
		{
		   if(flog==10)
		   {
		     break;
		   }
		    s += "<div onmouseover=\"this.className='selectTip';style.cursor='default'\"  onmouseout=\"this.className=null; style.cursor='default'\">" + brandList[i] + "</div>";
		   flog++;
		}
		 bt.innerHTML=s;
		 
		if( tip != $("brand").value)
		{
			Element.show('brandTip');
		}
	}
	else
	{
		var bt = $("brandTip");
		bt.innerHTML = "";
		Element.hide('brandTip');
	}
}
function move(event)
{
	 var srcEl = Event.element(event);
	 var tipEl = $(srcEl.id + "Tip");
     var a = tipEl.childNodes;
	 if (tipEl.style.display == "" )
	 {
		if(event.keyCode == 40 )
		{            
			if (tipEl.childNodes.length >= 1)
			{
				var bList = tipEl.childNodes;
				 
				if(tipEl.lastChild.className=="selectTip")
				{
				    tipEl.firstChild.className = "selectTip";
					tipEl.lastChild.className = "null";
					return ;
				}
				var s=0;
				for (var i = 0 ; i < bList.length; i++)
				{
					if (bList[i].className == "selectTip")
					{
					    s++;
						bList[i + 1].className = "selectTip";
						bList[i].className = "null";
						return ;
					}
					 
				}
				if(s==0)
				{
				  tipEl.firstChild.className = "selectTip";
				}
				 
			}

		}
		else if(event.keyCode == 38)
		{
		   
			if (tipEl.childNodes.length >= 1)
			{
			   
			   	if(tipEl.firstChild.className == "selectTip")
				{
					tipEl.lastChild.className = "selectTip";
					tipEl.firstChild.className = "null";
					return ;
				}
				var s=0;
				var bList = tipEl.childNodes;
				for (var i = 0 ; i < bList.length ; i ++)
				{
					if (bList[i].className == "selectTip")
					{
					   s++;
						bList[i - 1].className = "selectTip";
						bList[i].className = "null";
						return ;
					}
					 
				}
				if(s==0)
				{
				   tipEl.lastChild.className = "selectTip";
				}
			}
		}
		else if(event.keyCode == 13)
		{
			var bList = tipEl.childNodes;
			for (var i = 0 ; i < bList.length ; i ++)
			{
				if (bList[i].className == "selectTip")
				{
					tip = srcEl.value = bList[i].innerHTML;		
					//var useridlist=list[1].split("$");	
					//document.getElementById("xsry").value=useridlist[i];				 
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
			for (var i = 0 ; i < bList.length ; i ++)
			{   
				if (bList[i].className == "selectTip")
				{
					tip = srcEl.value = bList[i].innerHTML;	
					document.getElementById("brand").value=bList[i].innerHTML;				 
					//var useridlist=list[1].split("$");	
					//document.getElementById("xsry").value=useridlist[i];						
					 Element.hide(tipEl);
					 return;
				}
			}
}

var lists=new Array();
<%
  if(userList!=null)
  {
  for(int i=0;i<userList.size();i++)
  {   
     Map map=(Map)userList.get(i); 
%>
   lists["<%=map.get("real_name")%>"]="<%=map.get("user_id")%>";
<%}}%>
function setValue()
{
  if(document.getElementById("brand").value!="")
  {
    var brand =document.getElementById("brand").value;
    brand=brand.trim();
    if(brand in lists)
    {
      document.getElementById("fzr").value=lists[brand];
     
    }
    else
    {
      alert("您所输入的经手人不在列表里!");
      document.getElementById("brand").value="";
      document.getElementById("fzr").value="";
      document.getElementById("brand").focus();
    }
  }
  if(document.getElementById("brand").value.length==0)
  {
      document.getElementById("fzr").value="";
  }
 
  Element.hide('brandTip')
}
String.prototype.trim = function()
{
   return this.replace(/(^\s+)|\s+$/g,"");
}
 

new Form.Element.Observer("brand",1, searchBrand);
Event.observe("brand", "keydown", move, false);
Event.observe("brandTip","mousedown",down,true);


//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
</script>



<script type="text/javascript">
var tips = "";
function a()
{
    
	var url = 'getClients.do';
	var params = "clientsName=" + $F('client_name');
	var myAjax = new Ajax.Request(
	url,
	{
		method:'post',
		parameters: params,
		onComplete: showResponses,
		asynchronous:true
	});
}
var lista;
function showResponses(originalRequest)
{   
	var brandLists = originalRequest.responseText.split("%");
	lista=brandLists;
     
	var brandList=brandLists[0].split("$");
	 
	if ( brandList.length > 1)
	{
		var bt = $("clientsTip");
		var s="";
		var flog=0;
		for(var i = 0 ; i <  brandList.length; i++)  
		{
		   if(flog==10)
		   {
		     break;
		   }
		    s += "<div onmouseover=\"this.className='selectTip';style.cursor='default'\"  onmouseout=\"this.className=null; style.cursor='default'\">" + brandList[i] + "</div>";
		   flog++;
		}
		 bt.innerHTML=s;
		 
		if( tips != $("client_name").value)
		{
			Element.show('clientsTip');
		}
	}
	else
	{
		var bt = $("clientsTip");
		bt.innerHTML = "";
		Element.hide('clientsTip');
	}
}
function b(event)
{
	  var srcEl = Event.element(event);
	// var tipEl = $(srcEl.id + "Tip");
	  var tipEl = $('clientsTip');
     var a = tipEl.childNodes;
	 if (tipEl.style.display == "" )
	 {
		if(event.keyCode == 40 )
		{            
			if (tipEl.childNodes.length >= 1)
			{
				var bList = tipEl.childNodes;
				 
				if(tipEl.lastChild.className=="selectTip")
				{
				    tipEl.firstChild.className = "selectTip";
					tipEl.lastChild.className = "null";
					return ;
				}
				var s=0;
				for (var i = 0 ; i < bList.length; i++)
				{
					if (bList[i].className == "selectTip")
					{
					    s++;
						bList[i + 1].className = "selectTip";
						bList[i].className = "null";
						return ;
					}
					 
				}
				if(s==0)
				{
				  tipEl.firstChild.className = "selectTip";
				}
				 
			}

		}
		else if(event.keyCode == 38)
		{
		   
			if (tipEl.childNodes.length >= 1)
			{
			   
			   	if(tipEl.firstChild.className == "selectTip")
				{
					tipEl.lastChild.className = "selectTip";
					tipEl.firstChild.className = "null";
					return ;
				}
				var s=0;
				var bList = tipEl.childNodes;
				for (var i = 0 ; i < bList.length ; i ++)
				{
					if (bList[i].className == "selectTip")
					{
					   s++;
						bList[i - 1].className = "selectTip";
						bList[i].className = "null";
						return ;
					}
					 
				}
				if(s==0)
				{
				   tipEl.lastChild.className = "selectTip";
				}
			}
		}
		else if(event.keyCode == 13)
		{
			var bList = tipEl.childNodes;
			for (var i = 0 ; i < bList.length ; i ++)
			{
				if (bList[i].className == "selectTip")
				{
					tip = srcEl.value = bList[i].innerHTML;		
					//var useridlist=list[1].split("$");	
					//document.getElementById("xsry").value=useridlist[i];				 
					 Element.hide(tipEl);
					 return ;
				}
			}
		}
		
	}
}
function  c(event)
{
      var srcEl = Event.element(event);
	  var tipEl = $("clientsTip");
      var bList = tipEl.childNodes;
			for (var i = 0 ; i < bList.length ; i ++)
			{   
				if (bList[i].className == "selectTip")
				{
					tip = srcEl.value = bList[i].innerHTML;	
					document.getElementById("client_name").value=bList[i].innerHTML;				 
					//var useridlist=list[1].split("$");	
					//document.getElementById("xsry").value=useridlist[i];						
					 Element.hide(tipEl);
					 return;
				}
			}
}

var listss=new Array();
<%
   if(clientsList!=null)
   { 
  for(int i=0;i<clientsList.size();i++)
  {   
     Map map=(Map)clientsList.get(i); 
%>
   listss["<%=map.get("name")%>"]="<%=map.get("id")%>";
   
<%}}%>
function setvalues()
{
    
  if(document.getElementById("client_name").value!="")
  {
    var brand =document.getElementById("client_name").value;
    
    brand=brand.trims();
    if(brand in listss)
    {
      document.getElementById("client_id").value=listss[brand];     
    }
    else
    {
      alert("您所输入客户名称不在列表里!!");
      document.getElementById("client_name").value="";
      document.getElementById("client_id").value="";
      document.getElementById("client_name").focus();
    }
  }
  
  if(document.getElementById("client_name").value.length==0)
  {
      document.getElementById("client_id").value="";
  }
 Element.hide('clientsTip');
  
}
String.prototype.trims = function()
{
   return this.replace(/(^\s+)|\s+$/g,"");
}
new Form.Element.Observer("client_name",1, a);
Event.observe("client_name", "keydown", b, false);
Event.observe("clientsTip","mousedown",c,true);

</script>