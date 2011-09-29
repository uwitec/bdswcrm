<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List fxddProducts = (List)VS.findValue("fxddProducts");
Fxdd fxdd = (Fxdd)VS.findValue("fxdd");
Clients clients = (Clients)VS.findValue("clients");
String[] ysfsArry = (String[])VS.findValue("ysfs");

String fxdd_id = "";
String creatDate = "";  //创建时间
String ysfs = "";       //运输方式
String state = "";      //状态
String hjje = "";       //合计金额
String remark = "";     //备注

String client_name = StringUtils.nullToStr(clients.getName());  //客户名称
String client_id = StringUtils.nullToStr(clients.getId());
String address = StringUtils.nullToStr(clients.getAddress());
String lxr = StringUtils.nullToStr(clients.getLxr());
String lxdh = StringUtils.nullToStr(clients.getLxdh());


if(fxdd != null){
	fxdd_id = StringUtils.nullToStr(fxdd.getFxdd_id());
	creatDate = StringUtils.nullToStr(fxdd.getCreatdate());
	if(creatDate.equals("")){
		creatDate = DateComFunc.getToday();
	}
	
	ysfs = StringUtils.nullToStr(fxdd.getYsfs());	
	state = StringUtils.nullToStr(fxdd.getState());
	hjje = JMath.round(fxdd.getHjje());
	remark = StringUtils.nullToStr(fxdd.getRemark());
	
	address = StringUtils.nullToStr(fxdd.getAddress());
	lxr = StringUtils.nullToStr(fxdd.getLxr());
	lxdh = StringUtils.nullToStr(fxdd.getLxdh());
}

int allCount = 2;
if(fxddProducts != null && fxddProducts.size()>0){
	allCount = fxddProducts.size() -1;
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>采购订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript">

	var allCount = <%=allCount %>;
	
	function saveInfo(){
		if(document.getElementById("ysfs").value == ""){
			alert("运输方式不能为空，请选择！");
			return;
		}						
		hj();
		
		document.fxddForm.submit();
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
        otd3.innerHTML = '<input type="text" id="price_'+curId+'" name="xsdProducts['+curId+'].price" value="0.00" size="10" onblur="hj();" readonly>';
        
        var otd5 = document.createElement("td");
        otd5.className = "a2";
        otd5.innerHTML = '<input type="text" id="nums_'+curId+'" name="xsdProducts['+curId+'].nums" value="0" size="5" onblur="hj();">';
        
        var otd8 = document.createElement("td");
        otd8.className = "a2";
        otd8.innerHTML = '<input type="text" id="xj_'+curId+'" name="xsdProducts['+curId+'].xj" value="0.00" size="10" readonly>';        

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
	
	function openWin(){
		var destination = "selFxddProc.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function hj(){
		var length = ($('xsdtable').rows.length-2);
		
		var hjz = 0;
		var cbjhj = 0;
		
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
						
			var xj = document.getElementById("xj_" + i);			
			
			if(xj != null){
				xj.value = parseFloat(price.value) * parseFloat(nums.value);				
				hjz = parseFloat(hjz) + parseFloat(xj.value);
				
			}
		}	
		
		var hjje = document.getElementById("hjje");
		
		hjje.value = hjz;
	
	}
	
	function delDesc(){
		var k = 0;
		var sel = "0"; 
		for(var i=0;i<document.fxddForm.proc_id.length;i++){
			var o = document.fxddForm.proc_id[i];
			if(o.checked){
				k = k + 1;
				sel = document.fxddForm.proc_id[i].value;
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
		document.getElementById("jgtz_" + sel).value = "0.00";
		document.getElementById("nums_" + sel).value = "0";
		document.getElementById("xj_" + sel).value = "0.00";
		document.getElementById("remark_" + sel).value = "";
	}
</script>
</head>
<body >
<form name="fxddForm" action="updateFxdd.html" method="post">
<input type="hidden" name="fxdd.fxdd_id" value="<%=fxdd_id %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">采购订单</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">创建时间</td>
		<td class="a2" width="35%"><input type="text" name="fxdd.creatdate" id="creatdate" value="<%=creatDate %>" readonly>
		<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('creatdate')); return false;"><font color="red">*</font>
		</td>
		<td class="a1" width="15%">运输方式</td>
		<td class="a2" width="35%">
			<select name="fxdd.ysfs" id="ysfs">
				<option value=""></option>
			<%
			if(ysfsArry != null && ysfsArry.length > 0){
				for(int i =0;i<ysfsArry.length;i++){
			%>
				<option value="<%=ysfsArry[i] %>" <%if(ysfsArry[i].equals(ysfs)) out.print("selected"); %>><%=ysfsArry[i] %></option>
			<%
				}
			}
			%>
				
			</select>	<font color="red">*</font>	
		</td>					
	</tr>
	<tr>	
		<td class="a1" width="15%">单位名称</td>
		<td class="a2">
		<input type="text" name="fxdd.client_id" id="client_name" value="<%=client_name %>" size="30" maxlength="50" readonly>
		<input type="hidden" name="fxdd.client_name" id="client_id" value="<%=client_id %>">
		<font color="red">*</font>
		</td>
		<td class="a1">地址</td>
		<td class="a2"><input type="text" name="fxdd.kh_address" id="kh_address" value="<%=address %>"></td>	
	</tr>
	
	<tr>
		<td class="a1">联系人</td>
		<td class="a2"><input type="text" name="fxdd.kh_lxr" id="kh_lxr" value="<%=lxr %>"></td>	
		<td class="a1">联系电话</td>
		<td class="a2"><input type="text" name="fxdd.kh_lxdh" id="kh_lxdh" value="<%=lxdh %>"></td>
	</tr>	
	<tr>
		<td class="a1" width="15%">订单状态</td>
		<td class="a2" colspan="3">
			<select name="fxdd.state" id="state">
				<option value="已保存" <%if(state.equals("已保存")) out.print("selected"); %>>保存</option>
				<option value="已提交" <%if(state.equals("已提交")) out.print("selected"); %>>提交</option>
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
<table width="100%"  align="center" id="xsdtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>选择</td>
		<td>商品名称</td>
		<td>规格</td>
		<td>单价</td>
		<td>数量</td>
		<td>小计</td>
		<td>备注</td>
	</tr>
	</thead>
<%
if(fxddProducts!=null && fxddProducts.size()>0){
	for(int i=0;i<fxddProducts.size();i++){
		XsdProduct xsdProduct = (XsdProduct)fxddProducts.get(i);
%>
	<tr>
		<td class="a2"><input type="checkbox" name="proc_id" id="proc_id" value="<%=i %>"></td>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" name="xsdProducts[<%=i %>].product_name" value="<%=StringUtils.nullToStr(xsdProduct.getProduct_name()) %>" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="xsdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(xsdProduct.getProduct_id()) %>">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>" name="xsdProducts[<%=i %>].product_xh" size="10" value="<%=StringUtils.nullToStr(xsdProduct.getProduct_xh()) %>" readonly></td>
		<td class="a2">
			<input type="text" id="price_<%=i %>" name="xsdProducts[<%=i %>].price" value="<%=JMath.round(xsdProduct.getPrice()) %>" size="10" onblur="hj();" readonly>
		</td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="xsdProducts[<%=i %>].nums" value="<%=xsdProduct.getNums() %>" size="5" onblur="hj();"></td>
		<td class="a2"><input type="text" id="xj_<%=i %>" name="xsdProducts[<%=i %>].xj" value="<%=JMath.round(xsdProduct.getXj()) %>" size="10" readonly></td>		
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
			<input type="text" id="price_<%=i %>" name="xsdProducts[<%=i %>].price" value="0.00" size="10" onblur="hj();" readonly>
		</td>
		<td class="a2"><input type="text" id="nums_<%=i %>" name="xsdProducts[<%=i %>].nums" value="0" size="5" onblur="hj();"></td>
		<td class="a2"><input type="text" id="xj_<%=i %>" name="xsdProducts[<%=i %>].xj" value="0.00" size="10" readonly></td>		
		<td class="a2"><input type="text" id="remark_<%=i %>" name="xsdProducts[<%=i %>].remark"></td>
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a2" colspan="2" width="100%">&nbsp;
			<input type="button" name="button1" value="添加商品" class="css_button3" onclick="openWin();">
			<input type="button" name="button8" value="清除商品" class="css_button3" onclick="delDesc();">
		</td>
	</tr>
	<tr height="35">	
		<td class="a1">订单合计金额</td>
		<td class="a2">
			<input type="text" name="fxdd.hjje" id="hjje" value="<%=hjje %>" size="7" readonly>
		</td>	
	</tr>
	<tr>
		<td class="a1" width="15%">备&nbsp;&nbsp;注</td>
		<td class="a2" width="85%" colspan="2">
			<textarea rows="2" name="fxdd.remark" id="remark" style="width:75%"><%=remark %></textarea>
		</td>
	</tr>		
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="确 定" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();;">
		</td>
	</tr>
</table>
</form>
</body>
</html>
