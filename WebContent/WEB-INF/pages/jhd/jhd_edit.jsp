<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List storeList = (List)VS.findValue("storeList");
List jhdProducts = (List)VS.findValue("jhdProducts");
Jhd jhd = (Jhd)VS.findValue("Jhd"); 
int counts = 2;
if(jhdProducts != null && jhdProducts.size()>0){
	counts = jhdProducts.size() - 1;
}

List msg = (List)session.getAttribute("messages");
session.removeAttribute("messages");
%>

<html>
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
	
	var allCount = <%=counts %>;
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
        
		otr.appendChild(otd);
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd2); 
        otr.appendChild(otd3); 
        otr.appendChild(otd4); 
    }  
	
	
	function openWin(id){
		var destination = "selJhdProc.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
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
		document.getElementById("xj_" + sel).value = "0.00";
		document.getElementById("remark_" + sel).value = "";
	}	
	
</script>
</head>
<body onload="initFzrTip();initClientTip();">
<form name="jhdForm" action="updateJhd.html" method="post">
<input type="hidden" name="jhd.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">采购订单信息</td>
	</tr>
	</thead>
	<%
	if(msg != null && msg.size() > 0){
		for(int i=0;i<msg.size();i++){
	%>
	<tr>
		<td colspan="4" class="a2"><font color="red"><%=StringUtils.nullToStr(msg.get(i)) %></font></td>
	</tr>
	<%
		}
	}
	%>	
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%">
		<input type="text" name="jhd.id" id="id" value="<%=StringUtils.nullToStr(jhd.getId()) %>" size="45" readonly>	
		</td>
		<td class="a1" width="15%">采购日期</td>
		<td class="a2" width="35%"><input type="text" name="jhd.cg_date" id="cg_date" value="<%=StringUtils.nullToStr(jhd.getCg_date()) %>" size="45"  class="Wdate" onFocus="WdatePicker()"><font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">供货单位</td>
		<td class="a2" width="35%">
			<input type="text" name="jhd.gysmc" onblur="setClientRegInfo();" id="client_name" value="<%=StringUtils.nullToStr(jhd.getGysmc()) %>" size="45"  onblur="setClientValue();">
			<input type="hidden" name="jhd.gysbh" id="client_id" value="<%=StringUtils.nullToStr(jhd.getGysbh()) %>">
			<div id="clientsTip" style="height:12px;position:absolute;left:150px; top:85px; width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
			<font color="red">*</font>	
		</td>	
		<td class="a1">地址</td>
		<td class="a2"><input type="text" name="jhd.kh_address" id="kh_address" value="<%=StringUtils.nullToStr(jhd.getKh_address()) %>" maxlength="100" size="45"></td>	
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
		<td class="a2"><input type="text" name="jhd.kh_lxdh" id="kh_lxdh" value="<%=StringUtils.nullToStr(jhd.getKh_lxdh()) %>" maxlength="22" size="45"></td>
	</tr>
	<tr>		
		<td class="a1" width="15%">账期</td>
		<td class="a2">
			<input type="text" name="jhd.zq" id="zq" value="<%=StringUtils.nullToStr(jhd.getZq()) %>" size="45" title="账期" maxlength="5">天<font color="red">*</font>
			<input type="hidden" name="jhd.fkfs" id="fkfs" value="账期">
		</td>
		<td class="a1"  width="15%">发票类型</td>
	    <td class="a2" colspan="3">
	        <input type="radio"  name="jhd.ysws" value="未税" <%if(StringUtils.nullToStr(jhd.getYsws()).equals("未税")) out.print("checked"); %>>未税
	        <input type="radio"  name="jhd.ysws" value="含税" <%if(StringUtils.nullToStr(jhd.getYsws()).equals("含税")) out.print("checked"); %>>含税 <font color="red">*</font>
        </td>	
	</tr>
	<tr>			
		<td class="a1" width="15%">预计到货时间</td>
		<td class="a2">
		<input type="text" name="jhd.yjdhsj" id="yjdhsj" value="<%=StringUtils.nullToStr(jhd.getYjdhsj()) %>" size="45"  class="Wdate" onFocus="WdatePicker()">
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
				<option value="<%=StringUtils.nullToStr(storeHouse.getId()) %>" <%if(storeHouse.getId().equals(jhd.getStore_id())) out.print("selected"); %>><%=StringUtils.nullToStr(storeHouse.getName()) %></option>
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
			 <input id="brand" type="text" maxlength="20" onblur="setValue()" value="<%=StaticParamDo.getRealNameById(jhd.getFzr()) %>" size="45"/> 
	         <div id="brandTip" style="height:12px;position:absolute;left:612px; top:85px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		     <input type="hidden" name="jhd.fzr" id="fzr" value="<%=jhd.getFzr()%>"/> <font color="red">*</font>	
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
		<td width="5%">选择</td>
		<td width="30%">商品名称</td>
		<td width="30%">规格</td>
		<td width="12%">采购价格</td>
		<td width="10%">数量</td>
		<td width="13%">小计</td>
	</tr>
	</thead>
<%
if(jhdProducts != null && jhdProducts.size()>0){
	for(int i=0;i<jhdProducts.size();i++){
		JhdProduct jhdProduct = (JhdProduct)jhdProducts.get(i);
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="jhdProducts[<%=i %>].product_name" value="<%=StringUtils.nullToStr(jhdProduct.getProduct_name()) %>" style="width:100%" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="jhdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(jhdProduct.getProduct_id()) %>">
		</td>
		<td class="a2"><input type="text" size="10" id="product_xh_<%=i %>" name="jhdProducts[<%=i %>].product_xh" value="<%=StringUtils.nullToStr(jhdProduct.getProduct_xh()) %>" style="width:100%" readonly></td>
		<td class="a2"><input type="text" size="10" id="price_<%=i %>" name="jhdProducts[<%=i %>].price" value="<%=JMath.round(jhdProduct.getPrice()) %>" onblur="hj();" style="width:100%"></td>
		<td class="a2"><input type="text" size="5" id="nums_<%=i %>" name="jhdProducts[<%=i %>].nums"  value="<%=StringUtils.nullToStr(jhdProduct.getNums()) %>" onblur="hj();" style="width:100%"></td>
		<td class="a2"><input type="text" size="10" id="xj_<%=i %>" name="jhdProducts[<%=i %>].xj" value="<%=JMath.round(jhdProduct.getPrice() * jhdProduct.getNums()) %>" style="width:100%" readonly></td>
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center" id="jhtable"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="添加商品" class="css_button3" onclick="openWin();">
			<input type="button" name="button8" value="清除商品" class="css_button3" onclick="delDesc();">
		</td>
	</tr>
</table>
<table width="100%"  align="center" id="jhtable"  class="chart_info" cellpadding="0" cellspacing="0">	
	<tr>
		<td class="a1" widht="15%">合计金额</td>
		<td class="a2" widht="85%">
			<input type="text" id="total"  name="jhd.total" value="<%=JMath.round(jhd.getTotal()) %>" readonly>
			<input type="hidden" id="yfje"  name="jhd.yfje" value="0.00">
		</td>	
	</tr>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%"><input type="text" name="jhd.ms" id="ms" style="width:75%" value="<%=StringUtils.nullToStr(jhd.getMs()) %>">
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