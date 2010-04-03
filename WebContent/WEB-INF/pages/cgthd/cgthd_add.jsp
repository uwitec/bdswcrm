<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List storeList = (List)VS.findValue("storeList");
String id = (String)VS.findValue("id");

//是否完成初始标志
//0：未完成；1：已完成
String iscs_flag = StringUtils.nullToStr(VS.findValue("iscs_flag"));

%>

<html>
<head>
<title>采购退货单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">

	var allCount = 2;
	var iscs_flag = '<%=iscs_flag %>';
	
	function saveInfo(vl){

		if(vl == "1"){
			document.getElementById("state").value = "已保存";
		}else{
			document.getElementById("state").value = "已出库";
		}
		
		if(document.getElementById("id").value == ""){
			alert("编号不能为空！");
			return;
		}

		if(document.getElementById("th_date").value == ""){
			alert("退货日期不能为空！");
			return;
		}
					
		if(document.getElementById("client_id").value == ""){
			alert("供货单位不能为空，请选择！");
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
		
		if(document.getElementById("tkfs").value == "现金"){
			if(document.getElementById("skzh").value == ""){
				alert("账号不能为空，请选择！");
				return;
			}		
		}
		
		if(iscs_flag == "1"){  //初始化完成后再做强制序列号校验
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
		}
				
		hj();
		
		if(document.getElementById("state").value == "已出库"){
			if(window.confirm("确认要出库吗，出库后将无法修改！")){
				document.cgthdForm.submit();		
			}else{
				return;
			}			
		}else{
			document.cgthdForm.submit();
		}
		
		document.cgthdForm.btnSave.disabled = true;
		document.cgthdForm.btnSub.disabled = true;
	}
	
      	
    function addTr(){
        var otr = document.getElementById("thtable").insertRow(-1);

       // var curId = ($('xsdtable').rows.length-2);
        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="cgthdProducts['+curId+'].product_name" readonly><input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin('+curId+');"><input type="hidden" id="product_id_'+curId+'" name="cgthdProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" id="product_xh_'+curId+'"  name="cgthdProducts['+curId+'].product_xh" size="10" readonly>';
        
        var otd2 = document.createElement("td");
        otd2.className = "a2";
        otd2.innerHTML = '<input type="text" id="th_price_'+curId+'" name="cgthdProducts['+curId+'].th_price" size="10" value="0.00" onblur="hj();;">';
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" id="nums_'+curId+'" name="cgthdProducts['+curId+'].nums" size="5" value="0" onblur="hj();">';
        
		var otd7 = document.createElement("td");
		otd7.className = "a2";
		otd7.innerHTML = '<input type="text" id="qz_serial_num_'+curId+'" name="cgthdProducts['+curId+'].qz_serial_num" size="15" readonly><input type="hidden" id="qz_flag_'+curId+'" name="cgthdProducts['+curId+'].qz_flag"><a style="cursor:hand" title="点击输入序列号" onclick="openSerialWin('+ curId +');"><b>...</b></a>&nbsp;';           
		       
       
        var otd4 = document.createElement("td");
        otd4.className = "a2";
        otd4.innerHTML = '<input type="text" id="xj_'+curId+'" name="cgthdProducts['+curId+'].xj" size="10" value="0.00" readonly>';  
        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" id="remark_'+curId+'" name="cgthdProducts['+curId+'].remark"  maxlength="50">';                       

		var otd6 = document.createElement("td");
		otd6.className = "a2";
		otd6.innerHTML = '<input type="button" name="delButton" value="删除" class="css_button" onclick="delTr(this);">';
		
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd2); 
        otr.appendChild(otd3); 
        otr.appendChild(otd7); 
        otr.appendChild(otd4); 
        otr.appendChild(otd5);
        otr.appendChild(otd6);               
     }     
     
    //打开输入序列号窗口
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
     
	function delTr(i){
			var tr = i.parentNode.parentNode;
			tr.removeNode(true);
			
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
	
	function selJhd(){
		if(document.getElementById("client_id").value == ""){
			alert("请先选择供货单位！");
			return;
		}
		var destination = "selJhd.html?client_id=" + document.getElementById("client_id").value;
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'关联进货单',fea);		
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
		
		var tkzje = document.getElementById("tkzje");
		tkzje.value = hjz.toFixed(2);
		
	}
	

	function selTkfs(vl){
		if(vl == "冲抵往来"){
			document.getElementById("trSkzh").style.display = "none";	
			document.getElementById("zhname").value = "";	
			document.getElementById("skzh").value = "";
		}else{
			document.getElementById("trSkzh").style.display = "";	
		}
	}
	
</script>
</head>
<body onload="initFzrTip();initClientTip();">
<form name="cgthdForm" action="saveCgthd.html" method="post">
<input type="hidden" name="cgthd.jhd_id" id="jhd_id" value="">
<input type="hidden" name="cgthd.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">采购退货单信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">退货单编号</td>
		<td class="a2">
		<input type="text" name="cgthd.id" id="id" value="<%=id %>" size="30" maxlength="50" readonly>
		</td>	
		<td class="a1">退货日期</td>
		<td class="a2"><input type="text" name="cgthd.th_date" id="th_date" value="<%=DateComFunc.getToday() %>"  class="Wdate" onFocus="WdatePicker()"><font color="red">*</font>
		</td>	
	</tr>
	<tr>			
		<td class="a1" width="15%">供货单位</td>
		<td class="a2">
		<input type="text" name="cgthd.provider_id" id="client_name" value="" size="30" maxlength="50"  onblur="setClientValue();">
		<input type="hidden" name="cgthd.provider_name" id="client_id" value="">
		<div id="clientsTip" style="height:12px;position:absolute;left:150px; top:85px; width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		<font color="red">*</font>
		</td>	
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
		 <input id="brand" type="text" maxlength="20" onblur="setValue();" /> 
         <div id="brandTip" style="height:12px;position:absolute;left:610px; top:85px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		  <input type="hidden" name="cgthd.jsr" id="fzr"/> <font color="red">*</font>	
		</td>
	</tr>
	<tr>
		<td class="a1">退款方式</td>
		<td class="a2">
			<select name="cgthd.tkfs" id="tkfs" onchange="selTkfs(this.value);">
				<option value="现金">现金</option>
				<option value="冲抵往来">冲抵往来</option>
			</select>			
		</td>
		<td class="a1">退货库房</td>
		<td class="a2">
			<select name="cgthd.store_id" id="store_id">
				<option value=""></option>
			<%
			if(storeList != null){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse sh = (StoreHouse)it.next();
			%>
				<option value="<%=StringUtils.nullToStr(sh.getId()) %>"><%=StringUtils.nullToStr(sh.getName()) %></option>
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
		<td colspan="2">商品详细信息</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="thtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>商品名称</td>
		<td>规格</td>
		<td>退货价格</td>
		<td>数量</td>
		<td>强制序列号</td>
		<td>小计</td>
		<td>备注</td>
		<td></td>
	</tr>
	</thead>
<%
for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="cgthdProducts[<%=i %>].product_name" readonly>
			<input type="button" name="selectButton" value="选择" class="css_button" onclick="openWin(<%=i %>);">
			<input type="hidden" id="product_id_<%=i %>" name="cgthdProducts[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="cgthdProducts[<%=i %>].product_xh" size="10" readonly></td>
		<td class="a2"><input type="text" id="th_price_<%=i %>" name="cgthdProducts[<%=i %>].th_price" size="10" value="0.00" onblur="hj();"></td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="cgthdProducts[<%=i %>].nums" value="0" size="5" onblur="hj();"></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="cgthdProducts[<%=i %>].qz_serial_num" size="15" readonly>
			<input type="hidden" id="qz_flag_<%=i %>" name="cgthdProducts[<%=i %>].qz_flag"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>
		<td class="a2"><input type="text" id="xj_<%=i %>" name="cgthdProducts[<%=i %>].xj" value="0.00" size="10" readonly></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="cgthdProducts[<%=i %>].remark" maxlength="50"></td>
		<%if (i>0){ %>		
		<td class="a2"><input type="button" name="delButton" value="删除" class="css_button" onclick="delTr(this);"></td>
		<%}else{ %>
		<td class="a2">&nbsp;</td>
		<%} %>
	</tr>
<%
}
%>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="添加一行" class="css_button2" onclick="addTr();">
			<input type="button" name="button1" value="关联进货单" class="css_button3" onclick="selJhd();">
		</td>
	</tr>
	<tr>
		<td class="a1">合计金额</td>
		<td class="a2"><input type="text" id="tkzje"  name="cgthd.tkzje" value="0.00" readonly></td>
	</tr>
	<tr id="trSkzh">
		<td class="a1" widht="20%">账户名称</td>
		<td class="a2"><input type="text" id="zhname"  name="zhname" value="" readonly>
		<input type="hidden" id="skzh"  name="cgthd.zhmc" value="">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>
	</tr>			
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">备注</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%">
			<textarea rows="3" name="cgthd.remark" id="remark" style="width:75%" maxlength="500"></textarea>
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="btnSave" value="草 稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnSub" value="出 库" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>