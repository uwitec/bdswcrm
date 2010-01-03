<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
String id = (String)VS.findValue("id");
%>

<html>
<head>
<title>采购订单</title>
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
	
	var allCount = 2;
	
	function saveInfo(){
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

		if(document.getElementById("fkfs").value == ""){
			alert("付款方式不能为空，请选择！");
			return;
		}else if(document.getElementById("fkfs").value == "账期"){
			if(!InputValid(document.getElementById("zq"),1,"int",1,1,999,"账期限天数")){
				return;
			}
		}
		
		if(!InputValid(document.getElementById("fkje"),0,"float",0,1,99999999,"本次付款金额")){
			return;
		}
		
		if(document.getElementById("fkfs").value == "现金"){
			if(parseFloat(document.getElementById("fkje").value) != parseFloat(document.getElementById("total").value)){
				alert("付款金额与订单金额不符，请检查！");
				return;
			}	
		
			if(document.getElementById("fkzh").value == ""){
				alert("付款账户不能为空！");
				return;
			}
		}else{
			if(parseFloat(document.getElementById("fkje").value) > parseFloat(document.getElementById("total").value)){
				alert("本次付款金额大于订单金额，请检查!");
				return;
			}
		}
		
		hj();
		
		document.jhdForm.submit();
	}

      	
    function addTr(){
        var otr = document.getElementById("jhtable").insertRow(-1);

        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;
        
        var otd=document.createElement("td");
        otd.className = "a2";
        otd.innerHTML = '<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="' + curId + '"></td>';
        
        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="text" id="product_name_'+curId+'" name="jhdProducts['+curId+'].product_name" style="width:100%" readonly><input type="hidden" id="product_id_'+curId+'" name="jhdProducts['+curId+'].product_id">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" size="10"  id="product_xh_'+curId+'"  name="jhdProducts['+curId+'].product_xh" style="width:100%" readonly>';
        
        var otd2 = document.createElement("td");
        otd2.className = "a2";
        otd2.innerHTML = '<input type="text" size="10"  id="price_'+curId+'" name="jhdProducts['+curId+'].price" value="0.00" style="width:100%" onblur="hj();">';
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" size="5"  id="nums_'+curId+'" name="jhdProducts['+curId+'].nums" value="0" style="width:100%" onblur="hj();">';

        
        var otd4 = document.createElement("td");
        otd4.className = "a2";
        otd4.innerHTML = '<input type="text" size="10"  id="xj_'+curId+'" name="jhdProducts['+curId+'].xj" value="0.00" style="width:100%" readonly>';  
        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" id="remark_'+curId+'" name="jhdProducts['+curId+'].remark" style="width:100%">';                       
		
		otr.appendChild(otd);
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd2); 
        otr.appendChild(otd3); 
        otr.appendChild(otd4); 
        otr.appendChild(otd5);              
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
		var length = (document.getElementById('jhtable').rows.length-2);
		
		var hjz = 0;
		var cbjhj = 0;
		
		for(var i=0;i<=allCount;i++){			
			var price = document.getElementById("price_" + i);
			
			if(price != null){
				if(!InputValid(price,0,"float",0,1,99999999,"采购价格")){
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
				xj.value = (parseFloat(price.value) * parseFloat(nums.value)).toFixed(2);				
				hjz = parseFloat(hjz) + parseFloat(xj.value);
			}
		}		
		
		var total = document.getElementById("total");
		
		total.value = hjz.toFixed(2);
		
	}	


	function chkFkfs(vl){
		
		var obj = document.getElementById("zq");

		var obj_bcfkje1 = document.getElementById("bcfkje1");
		var obj_bcfkje2 = document.getElementById("bcfkje2");
		var obj_bcfkzh1 = document.getElementById("bcfkzh1");
		var obj_bcfkzh2 = document.getElementById("bcfkzh2");
		
		if(vl == "账期"){
			obj.style.display = "";
			obj_bcfkje1.style.display = "none";
			obj_bcfkje2.style.display = "none";
			obj_bcfkzh1.style.display = "none";
			obj_bcfkzh2.style.display = "none";

			document.getElementById("fkje").value = "0.00";
			document.getElementById("zhname").value = "";
			document.getElementById("fkzh").value = "";
			
		}else{
			obj.style.display = "none";
			obj_bcfkje1.style.display = "";
			obj_bcfkje2.style.display = "";
			obj_bcfkzh1.style.display = "";
			obj_bcfkzh2.style.display = "";
			obj.value = "0";
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
		alert("请选择产品明细，且只能选择一条信息！");
		return;
	}
	
	document.getElementById("product_name_" + sel).value = "";
	document.getElementById("product_id_" + sel).value = "";
	document.getElementById("product_xh_" + sel).value = "";
	document.getElementById("price_" + sel).value = "0.00";
	document.getElementById("nums_" + sel).value = "0";
	document.getElementById("xj_" + sel).value = "0.00";
	document.getElementById("remark_" + sel).value = "";
}	
	
</script>
</head>
<body onload="initFzrTip();initClientTip();">
<form name="jhdForm" action="saveJhd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">采购订单信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%">
		<input type="text" name="jhd.id" id="id" value="<%=id %>" readonly>
		</td>	
		<td class="a1" width="15%">采购日期</td>
		<td class="a2" width="35%"><input type="text" name="jhd.cg_date" id="cg_date" value="<%=DateComFunc.getToday() %>"  class="Wdate" onFocus="WdatePicker()"><font color="red">*</font>	
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">供货单位</td>
		<td class="a2" width="35%">
		<input type="text" name="jhd.gysmc" id="client_name" value="" size="35"  onblur="setClientValue();">
		<input type="hidden" name="jhd.gysbh" id="client_id" value="">
		<div id="clientsTip" style="height:12px;position:absolute;left:150px; top:85px; width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		<font color="red">*</font>	
		</td>		
		<td class="a1" width="15%">采购负责人</td>
		<td class="a2" width="35%">
		    <input id="brand" type="text" length="20" onblur="setValue()"/> 
            <div id="brandTip" style="height:12px;position:absolute;left:612px; top:85px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
            </div>
		    <input type="hidden" name="jhd.fzr" id="fzr" /> <font color="red">*</font>	
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">付款方式</td>
		<td class="a2">
			<select name="jhd.fkfs" id="fkfs" onchange="chkFkfs(this.value);">
				<option value=""></option>
				<option value="现金">现金</option>
				<option value="账期">账期</option>
			</select>
			<input type="text" name="jhd.zq" id="zq" value="0" size="3" style="display:none" title="账期天数"> <font color="red">*</font>注：选择账期，请输入账期天数
		</td>	
		<td class="a1" width="15%">进货单状态</td>
		<td class="a2">
			<select name="jhd.state" id="state">
				<option value="已保存">保存</option>
				<option value="已提交">提交</option>
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
<table width="100%"  align="center" id="jhtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="5%">选择</td>
		<td width="25%">产品名称</td>
		<td width="25%">规格</td>
		<td width="10%">采购价格</td>
		<td width="10%">数量</td>
		<td width="10%">小计</td>
		<td width="15%">备注</td>
	</tr>
	</thead>
<%
for(int i=0;i<3;i++){
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text"  id="product_name_<%=i %>" name="jhdProducts[<%=i %>].product_name" style="width:100%" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="jhdProducts[<%=i %>].product_id">
		</td>
		<td class="a2"><input type="text" size="10" id="product_xh_<%=i %>" name="jhdProducts[<%=i %>].product_xh" style="width:100%" readonly></td>
		<td class="a2"><input type="text" size="10"  id="price_<%=i %>" name="jhdProducts[<%=i %>].price" value="0.00" style="width:100%" onblur="hj();"></td>
		<td class="a2"><input type="text" size="5"  id="nums_<%=i %>" name="jhdProducts[<%=i %>].nums" value="0" style="width:100%" onblur="hj();"></td>		
		<td class="a2"><input type="text" size="10"  id="xj_<%=i %>" name="jhdProducts[<%=i %>].xj" value="0.00" style="width:100%" readonly></td>
		<td class="a2"><input type="text" id="remark_<%=i %>" name="jhdProducts[<%=i %>].remark" style="width:100%"></td>
	</tr>
<%
}
%>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="添加产品" class="css_button3" onclick="openWin();">
			<input type="button" name="button8" value="清除产品" class="css_button3" onclick="delDesc();">
		</td>
	</tr>
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">	
	<tr>
		<td class="a1" width="15%">合计金额</td>
		<td class="a2" width="35%">
			<input type="text" id="total"  name="jhd.total" value="0.00" readonly>
			<input type="hidden" id="yfje"  name="jhd.yfje" value="0.00">	
		</td>
		<td class="a1" width="15%" id="bcfkje1">本次付款金额</td>
		<td class="a2" width="35%" id="bcfkje2"><input type="text" id="fkje"  name="jhd.fkje" value="0.00"></td>		
	</tr>
	<tr>
		<td class="a1" widht="15%" id="bcfkzh1">本次付款账户</td>
		<td class="a2" colspan="3" id="bcfkzh2"><input type="text" id="zhname"  name="zhname" value="" readonly>
		<input type="hidden" id="fkzh"  name="jhd.fkzh" value="">
		<img src="images/select.gif" align="absmiddle" title="选择账户" border="0" onclick="openAccount();" style="cursor:hand">
		</td>
	</tr>
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">		
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%">
			<textarea rows="2" name="jhd.ms" id="ms" style="width:75%"></textarea>
		</td>
	</tr>		
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>