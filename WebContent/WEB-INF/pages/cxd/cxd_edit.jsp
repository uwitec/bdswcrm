<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>商品拆卸单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">

	var strCount = '<ww:property value="%{cxdProducts.size}"/>';
	if(strCount == '0') strCount = '1';
	var allCount = parseInt(strCount) - 1;

	function saveInfo(vl){
		if(vl == "1"){
			document.getElementById("state").value = "已保存";
		}else{
			document.getElementById("state").value = "已提交";
		}
		if(!InputValid(document.getElementById("cdate"),1,"string",1,1,20,"单据日期")){return; }
		if(!InputValid(document.getElementById("product_name"),1,"string",1,1,100,"商品名称")){return; }
		if(!InputValid(document.getElementById("nums"),1,"int",1,1,9999,"数量")){return; }	
		
		if(document.getElementById("fzr").value == ""){
			alert("经手人不能为空，请选择");
			return;
		}
		if(document.getElementById("store_id").value == ""){
			alert("所在库房不能为空，请选择");
			return;
		}
		if(document.getElementById("qz_flag").value == "是" && document.getElementById("serial_nums").value == ""){
			alert("待拆卸商品为强制序列号商品，请输入序列号！");
			return;
		}

		//判断明细中应该输序列号的是否正确输入
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

		if(parseFloat(document.getElementById("hjje").value) != parseFloat(document.getElementById("hj_je").value)){
			alert("拆卸明细金额合计必须等于，拆卸商品金额!");
			return;
		}

		if(document.getElementById("state").value == "已提交"){
			if(window.confirm("确认要提交吗，提交后将不可修改！")){
				document.cxdForm.submit();
			}	
		}else{
			document.cxdForm.submit();
		}
		
	}
		
    function addTr(){
        var otr = document.getElementById("cxdTable").insertRow(-1);

        var curId = allCount + 1;   //curId一直加下去，防止重复
        allCount = allCount + 1;

        var otd0=document.createElement("td");
        otd0.className = "a2";
        otd0.innerHTML = '<input type="checkbox" name="proc_id" id="proc_id" value="' + curId + '">';
        
        var otd1 = document.createElement("td");
        otd1.className = "a2";
        otd1.innerHTML = '<input type="text" name="cxdProducts[' + curId + '].product_name" value="" readonly="readonly" id="product_name_' + curId + '" style="width:90%"/><input type="hidden" name="cxdProducts[' + curId + '].product_id" value="" id="product_id_' + curId + '"/>';
       
        var otd2 = document.createElement("td");
        otd2.className = "a2";
        otd2.innerHTML = '<input type="text" name="cxdProducts[' + curId + '].product_xh" value="" readonly="readonly" id="product_xh_' + curId + '" style="width:90%"/>';
        
        
        var otd3 = document.createElement("td");
        otd3.className = "a2";
        otd3.innerHTML = '<input type="text" name="cxdProducts[' + curId + '].product_dw" value="" readonly="readonly" id="product_dw_' + curId + '" style="width:90%"/>';    
        
        var otd4 = document.createElement("td");
        otd4.className = "a2";
        otd4.innerHTML = '<input type="text" name="cxdProducts[' + curId + '].price" value="0.00" onblur="hj();" id="price_' + curId + '" style="width:90%"/>';               
        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" name="cxdProducts[' + curId + '].nums" value="0" onblur="hj();" id="nums_' + curId + '" style="width:90%"/>';                       

        var otd6 = document.createElement("td");
        otd6.className = "a2";
        otd6.innerHTML = '<input type="text" name="cxdProducts[' + curId + '].hj" value="0.00" readonly="readonly" id="hj_' + curId + '" style="width:90%"/>';    
        
        var otd7 = document.createElement("td");
        otd7.className = "a2";
        otd7.innerHTML = '<input type="text" name="cxdProducts[' + curId + '].qz_serial_num" value="" id="qz_serial_num_' + curId + '" style="width:80%"/><input type="hidden" id="qz_flag_' + curId + '" name="cxdProducts[' + curId + '].qz_flag" value=""><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin(' + curId + ');"><b>...</b></a>';            

        var otd8 = document.createElement("td");
        otd8.className = "a2";
        otd8.innerHTML = '<input type="text" name="cxdProducts[' + curId + '].remark" value="" id="remark_' + curId + '" style="width:90%"/>';
        
        otr.appendChild(otd0); 
        otr.appendChild(otd1); 
        otr.appendChild(otd2);
        otr.appendChild(otd3);
        otr.appendChild(otd4);
        otr.appendChild(otd5);
        otr.appendChild(otd6);
        otr.appendChild(otd7);
        otr.appendChild(otd8);
    }		
	
	function selProduct(){
		var destination = "selCxProduct.html";
		var fea ='width=700,height=500,left=' + (screen.availWidth-700)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择商品',fea);		
	}

	function selProductMx(){
		var destination = "selCxProductMx.html";
		var fea ='width=700,height=500,left=' + (screen.availWidth-700)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择商品',fea);		
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


	function importSerialNums(){
		var pn = document.getElementById("product_id").value;
		var nm = document.getElementById("nums").value;
		
		if(pn == ""){
			alert("请选择商品，再输入序列号！");
			return;
		}
		if(nm == "" || nm == "0"){
			alert("请设置商品数量，再输入序列号！");
			return;
		}
		
		var qzserialnum = document.getElementById("serial_nums").value;
		
		var url = "importSerialNums.html?nums=" + nm + "&serialNum=" + qzserialnum + "&product_id=" + pn;
		var fea ='width=300,height=200,left=' + (screen.availWidth-300)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(url,'详细信息',fea);	
	}	


	function hjf(){
		var obj_price = document.getElementById("price");
		var obj_nums = document.getElementById("nums");
		var obj_hjje = document.getElementById("hjje");

		obj_hjje.value = (parseFloat(obj_price.value) * parseFloat(obj_nums.value)).toFixed(2);	
	}		

	
	function hj(){		
		
		var mx_hjje = 0;  //明细合计金额
		var mx_hjnums = 0; //明细合计数量 
		for(var i=0;i<=allCount;i++){			
			var obj_mx_price = document.getElementById("price_" + i);

			if(obj_mx_price == null){
				continue;
			}
			
			if(obj_mx_price != null){
				if(!InputValid(obj_mx_price,0,"float",0,1,99999999,"拆卸明细--单价")){
					obj_mx_price.focus();
					return;
				}
			}
			obj_mx_price.value = parseFloat(obj_mx_price.value).toFixed(2);
			
			var obj_mx_nums = document.getElementById("nums_" + i);
			if(obj_mx_nums != null){
				if(!InputValid(obj_mx_nums,0,"int",0,1,99999999,"拆卸明细--数量")){
					obj_mx_nums.focus();
					return;
				}
			}				
			
			var xj = document.getElementById("hj_" + i);			
			
			if(xj != null){
				xj.value = (parseFloat(obj_mx_price.value) * parseFloat(obj_mx_nums.value)).toFixed(2);	
							
				mx_hjje = parseFloat(mx_hjje) + parseFloat(xj.value);
				mx_hjnums = parseInt(mx_hjnums) + parseInt(obj_mx_nums.value);;
			}
		}

		document.getElementById("hj_nums").value = mx_hjnums;
		document.getElementById("hj_je").value = parseFloat(mx_hjje).toFixed(2);
		
	}	

	function delDesc(){
		var k = 0;
		var sel = "0"; 

		var objChk = document.getElementsByName("proc_id");
		for(var i=0;i<objChk.length;i++){
			var o = document.cxdForm.proc_id[i];
			if(o.checked){
				k = k + 1;
				sel = document.cxdForm.proc_id[i].value;
			}
		}
		if(k != 1){
			alert("请选择商品明细，且只能选择一条信息！");
			return;
		}

		alert(sel);
		
		document.getElementById("product_name_" + sel).value = "";
		document.getElementById("product_id_" + sel).value = "";
		document.getElementById("product_xh_" + sel).value = "";
		document.getElementById("product_dw_" + sel).value = "";
		document.getElementById("price_" + sel).value = "0.00";
		document.getElementById("nums_" + sel).value = "0";
		document.getElementById("hj_" + sel).value = "0.00";
		document.getElementById("remark_" + sel).value = "";
	}			
	
</script>
</head>
<body onload="initFzrTip();hj();">
<form name="cxdForm" action="updateCxd.html" method="post">
<ww:hidden name="cxd.state" id="state" value=""  theme="simple"></ww:hidden>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">商品拆卸单</td>
	</tr>
	</thead>
	<ww:if test="%{msg != ''}">
	<tr>
		<td colspan="4" class="a2"><font color="red"><ww:property value="%{msg}"/></font></td>
	</tr>
	</ww:if>
	<tr>
		<td class="a1" width="15%">单据编号</td>
		<td class="a2" width="35%">
			<ww:textfield name="cxd.id" id="id" value="%{cxd.id}" theme="simple" cssStyle="width:232px" readonly="true"/><span style="color:red">*</span>
		</td>
		<td class="a1" width="15%">单据日期</td>
		<td class="a2" width="35%">
			<input type="text" name="cxd.cdate" id="cdate" style="width:232px" value="<ww:property value="%{cxd.cdate}"/>" class="Wdate" onFocus="WdatePicker()"/>&nbsp;	
			<span style="color:red">*</span>
		</td>				
	</tr>
	<tr>
		<td class="a1" width="15%">商品名称</td>
		<td class="a2" width="35%">
			<ww:textfield name="cxd.product_name" id="product_name" cssStyle="width:232px" value="%{cxd.product_name}" onclick="selProduct();" theme="simple" maxLength="100" readonly="true"/>
			<img src="images/select.gif" align="absmiddle" title="选择商品" border="0" onclick="selProduct();" style="cursor:hand"><font color="red">*</font>
		</td>	
		<td class="a1" width="15%">商品编号</td>
		<td class="a2" width="35%">
			<ww:textfield name="cxd.product_id" id="product_id" cssStyle="width:232px" value="%{cxd.product_id}" theme="simple"  readonly="true"/>
			<ww:hidden name="cxd.qz_flag" id="qz_flag" value="%{cxd.qz_flag}" theme="simple"/>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">商品规格</td>
		<td class="a2" width="35%">
			<ww:textfield name="cxd.product_xh" id="product_xh" cssStyle="width:232px" value="%{cxd.product_xh}" theme="simple" readonly="true"/>
		</td>
		<td class="a1" width="15%">单位</td>
		<td class="a2" width="35%">
			<ww:textfield name="cxd.product_dw" id="product_dw" cssStyle="width:232px" value="%{cxd.product_dw}" theme="simple" readonly="true"/>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">单价</td>
		<td class="a2" width="35%">
			<ww:textfield id="price" name="cxd.price" cssStyle="width:232px" value="%{getText('global.format.double',{cxd.price})}" theme="simple" readonly="true"/>
		</td>
		<td class="a1" width="15%">数量</td>
		<td class="a2">
			<ww:textfield id="nums"  name="cxd.nums" cssStyle="width:232px" value="%{cxd.nums}" theme="simple" onblur="hjf();"/><span style="color:red">*</span>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%">
			<ww:textfield id="hjje" name="cxd.hjje" cssStyle="width:232px" value="%{getText('global.format.double',{cxd.hjje})}" theme="simple" readonly="true"/>
		</td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2">
			<ww:textfield name="brand" id="brand" onblur="setValue()" cssStyle="width:232px" value="%{getUserRealName(cxd.jsr)}" theme="simple"></ww:textfield>
            <div id="brandTip" style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <ww:hidden name="cxd.jsr" id="fzr" value="%{cxd.jsr}" theme="simple"></ww:hidden><font color="red">*</font>	
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">所在库房</td>
		<td class="a2">
			<ww:select name="cxd.store_id" id="store_id" cssStyle="width:232px" theme="simple" list="%{storeList}" listValue="name" listKey="id" emptyOption="true"></ww:select><span style="color:red">*</span>
		</td>			
		<td class="a1" width="15%">序列号</td>
		<td class="a2">
			<ww:textfield name="cxd.serial_nums" id="serial_nums" cssStyle="width:232px" value="%{cxd.serial_nums}" theme="simple" readonly="true" onclick="importSerialNums();"/>
			<a style="cursor:hand" title="左键点击输入输列号" onclick="importSerialNums();"><b>...</b></a>
		</td>	
	</tr>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" colspan="3">
			<ww:textfield name="cxd.remark"  id="remark" value="%{cxd.remark}" theme="simple" cssStyle="width:85%"/>
		</td>	
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">拆卸明细</td>
	</tr>
	</thead>
</table>	
<table width="100%"  align="center" id="cxdTable" class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="5%">选择</td>
		<td width="20%">商品名称</td>
		<td width="15%">规格</td>
		<td width="8%">单位</td>
		<td width="10%">单价</td>
		<td width="8%">数量</td>
		<td width="10%">金额</td>
		<td width="15%">序列号</td>
		<td width="9%">备注</td>
	</tr>
	</thead>
	
	<ww:if test="cxdProducts.size>0">
	<ww:iterator value="%{cxdProducts}" status="li">
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<ww:property value="%{#li.count-1}"/>"></td>
		<td class="a2">
			<ww:textfield name='cxdProducts[%{#li.count-1}].product_name' id='product_name_%{#li.count-1}' value="%{product_name}" theme="simple" cssStyle="width:90%" readonly="true"/>
			<ww:hidden name='cxdProducts[%{#li.count-1}].product_id' id='product_id_%{#li.count-1}' value="%{product_id}" theme="simple"/>
		</td>
		<td class="a2"><ww:textfield name='cxdProducts[%{#li.count-1}].product_xh' id='product_xh_%{#li.count-1}' value="%{product_xh}" theme="simple" cssStyle="width:90%" readonly="true"/></td>
		<td class="a2"><ww:textfield name='cxdProducts[%{#li.count-1}].product_dw' id='product_dw_%{#li.count-1}' value="%{product_dw}" theme="simple" cssStyle="width:90%" readonly="true"/></td>
		<td class="a2"><ww:textfield name='cxdProducts[%{#li.count-1}].price' id='price_%{#li.count-1}' value="%{getText('global.format.double',{price})}" onblur="hj();" theme="simple" cssStyle="width:90%"/></td>
		<td class="a2"><ww:textfield name='cxdProducts[%{#li.count-1}].nums' id='nums_%{#li.count-1}' value="%{nums}" onblur="hj();" theme="simple" cssStyle="width:90%"/></td>
		<td class="a2"><ww:textfield name='cxdProducts[%{#li.count-1}].hj' id='hj_%{#li.count-1}' value="%{getText('global.format.double',{hj})}" theme="simple" cssStyle="width:90%" readonly="true"/></td>
		<td class="a2">
			<ww:textfield name='cxdProducts[%{#li.count-1}].qz_serial_num' id='qz_serial_num_%{#li.count-1}' value="%{qz_serial_num}" theme="simple" cssStyle="width:80%" readonly="true"/>
			<ww:hidden id="qz_flag_%{#li.count-1}" name="cxdProducts[%{#li.count-1}].qz_flag" value="%{qz_flag}"/><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<ww:property value="%{#li.count-1}"/>');"><b>...</b></a>
		</td>
		<td class="a2"><ww:textfield name='cxdProducts[%{#li.count-1}].remark' id='remark_%{#li.count-1}' value="%{remark}" theme="simple" cssStyle="width:90%"/></td>
	</tr>	
	</ww:iterator>
	</ww:if>	
	<ww:else>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="0"></td>
		<td class="a2">
			<ww:textfield name='cxdProducts[0].product_name' id='product_name_0' value="" theme="simple" cssStyle="width:90%" readonly="true"/>
			<ww:hidden name='cxdProducts[0].product_id' id='product_id_0' value="" theme="simple"/>
		</td>
		<td class="a2"><ww:textfield name='cxdProducts[0].product_xh' id='product_xh_0' value="" theme="simple" cssStyle="width:90%" readonly="true"/></td>
		<td class="a2"><ww:textfield name='cxdProducts[0].product_dw' id='product_dw_0' value="" theme="simple" cssStyle="width:90%" readonly="true"/></td>
		<td class="a2"><ww:textfield name='cxdProducts[0].price' id='price_0' value="0.00" onblur="hj();" theme="simple" cssStyle="width:90%"/></td>
		<td class="a2"><ww:textfield name='cxdProducts[0].nums' id='nums_0' value="0" onblur="hj();" theme="simple" cssStyle="width:90%"/></td>
		<td class="a2"><ww:textfield name='cxdProducts[0].hj' id='hj_0' value="0.00" theme="simple" cssStyle="width:90%" readonly="true"/></td>
		<td class="a2">
			<ww:textfield name='cxdProducts[0].qz_serial_num' id='qz_serial_num_0' value="" theme="simple" cssStyle="width:80%"/>
			<input type="hidden" id="qz_flag_0" name="cxdProducts[0].qz_flag" value=""><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('0');"><b>...</b></a>
		</td>
		<td class="a2"><ww:textfield name='cxdProducts[0].remark' id='remark_0' value="" theme="simple" cssStyle="width:90%"/></td>
	</tr>	
	</ww:else>	
</table>
<table width="100%"  align="center" class="chart_list" cellpadding="0" cellspacing="0">	
	<tr>
		<td class="a2"  width="5%"><B>合计</B></td>
		<td class="a2"  width="20%">&nbsp;</td>
		<td class="a2"  width="15%">&nbsp;</td>
		<td class="a2"  width="8%">&nbsp;</td>
		<td class="a2"  width="10%">&nbsp;</td>
		<td class="a2"  width="8%"><input type="text" name="hj_nums" id="hj_nums" value="0" readonly style="width:90%"></td>
		<td class="a2"  width="10%"><input type="text" name="hj_je" id="hj_je" value="0.00" readonly style="width:90%"></td>
		<td class="a2"  width="15%">&nbsp;</td>
		<td class="a2"  width="9%">&nbsp;</td>
	</tr>
	<tr height="35">
		<td class="a2" colspan="9" style="text-align: left">&nbsp;
			<input type="button" name="button1" value="添加拆卸明细" class="css_button3" onclick="selProductMx();">
			<input type="button" name="button1" value="删除拆卸明细" class="css_button3" onclick="delDesc();">
		</td>
	</tr>			
	<tr height="35">
		<td class="a1" colspan="9">
			<input type="button" name="btnSave" value="草 稿" class="css_button2" onclick="saveInfo('1');">&nbsp;
			<input type="button" name="btnSub" value="提 交" class="css_button2" onclick="saveInfo('2');">&nbsp;
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>