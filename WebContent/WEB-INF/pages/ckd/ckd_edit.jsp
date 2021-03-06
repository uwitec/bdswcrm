<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List msg = (List)session.getAttribute("messages");
session.removeAttribute("messages");

Clients client = (Clients)VS.findValue("client");

List storeList = (List)VS.findValue("storeList");
Ckd ckd = (Ckd)VS.findValue("ckd");
List ckdProducts = (List)VS.findValue("ckdProducts");

String[] ysfsArry = (String[])VS.findValue("ysfs");

int count = 2;
if(ckdProducts!=null && ckdProducts.size()>0){
	count = ckdProducts.size();
}

String lxr = "",address = "";
if(client != null){
	lxr = StringUtils.nullToStr(client.getLxr());
	address = StringUtils.nullToStr(client.getAddress());
}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>出库单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<LINK href="css/ddcolortabs.css" type=text/css rel=stylesheet>
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/nums.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">

	var allCount = <%=count %>;
	
	function saveInfo(vl){

		if(vl == "1"){
			document.getElementById("state").value = "待出库";
		}else{
			document.getElementById("state").value = "已出库";
		}
			
		if(document.getElementById("ckd_id").value == ""){
			alert("出库单编号不能为空，请填写！");
			return;
		}
		if(document.getElementById("client_name").value == ""){
			alert("客户名称不能为空，请选择！");
			return;
		}

		if(document.getElementById("store_id").value == ""){
			alert("出货库房不能为空，请选择！");
			return;
		}	
		if(document.getElementById("brand").value == ""){
			alert("出库经手人不能为空，请选择！");
			return;
		}								
		
		if(document.getElementById("state").value == "已出库"){
			if(document.getElementById("ck_date").value == ""){
				alert("出库时间不能为空，请选择！");
				return;
			}
		}
		if(document.getElementById("xsd_id").value == ""){
			alert("销售单编号不能为空，请输入！");
			return;
		}
		if(document.getElementById("ysfs").value == ""){
			alert("运输方式不能为空，请选择！");
			return;
		}					

		//判断是否存在强制输入序列号的商品没有输入序列号
		for(var i=0;i<allCount;i++){
			var qzflag = document.getElementById("qz_flag_" + i);            //标志是否强制输入
			var qzserialnum = document.getElementById("qz_serial_num_" + i); //序列号
			var pn = document.getElementById("product_name_" + i);           //商品名称
			
			//判断输入数量与订单数量是否一致
			if(document.getElementById("ck_nums_" + i).value != document.getElementById("nums_" + i).value){
				alert("订单数量与出库数量不一致，无法出库，请检查！");
				return;
			}
			
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

		if(vl == "1"){
			document.ckdForm.submit();
		}else{
			if(window.confirm("确认出库吗？")){
				document.ckdForm.submit();
			}else{
				return;
			}
		}
		document.ckdForm.btnSave.disabled = true;
		document.ckdForm.btnSub.disabled = true;
		document.ckdForm.btnTh.disabled = true;
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
	
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}	

	
	//退回订单
	function doTh(){
		if(window.confirm("确认要退回订单吗？")){
			document.ckdForm.action = "doCkdTh.html";
			document.ckdForm.btnTh.disabled = true;
			document.ckdForm.submit();
		}
	}
	
	//点击回车后执行
	function f_enter(){
	    if (window.event.keyCode==13){
	        sendSerialNum();
	    }
	}	
	
	//发送序列号
	function sendSerialNum(){
		var serialNum = dwr.util.getValue("s_nums");
		var storeId = dwr.util.getValue("store_id");
		if(serialNum == ""){
			return;
		}
		if(storeId == ""){
			alert("出货库房不能为空，请选择！");
			return;
		}
		dwrService.getProductObjBySerialNumAndStoreId(serialNum,storeId,setProductInfo);	
	}
	
	//处理返回商品对象
	function setProductInfo(product){
		if(product != null && product.productId != null){
			var flag = false;
			for(var i=0;i<=allCount;i++){
				var obj = document.getElementById("product_id_" + i);
								
				if(obj != null){
					if(obj.value==product.productId){  //如果当前行商品与返回商品相同
					
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
						
						var nums = dwr.util.getValue("ck_nums_" + i);
						if(nums == "" || isNaN(nums)) nums="0"; 
						
						if((parseInt(nums)+1) > parseInt(dwr.util.getValue("nums_" + i))){
							alert("出库数量不能大于订单数量，请检查！");
							break;
						}
						
						dwr.util.setValue("ck_nums_" + i,parseInt(nums)+1);	
						
						dwr.util.setValue("qz_serial_num_" + i,vl);
						
						dwr.util.setValue("s_nums","");
						
						break;
					}
				}
			}
		}else{
			alert("当前库房中不存在该序列号或条形码不存在，请检查!");
		}
	}		
</script>
</head>
<body onload="initFzrTip();">
<form name="ckdForm" action="updateCkd.html" method="post">
<input type="hidden" name="ckd.state" id="state" value="">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">出库单基本信息</td>
	</tr>
	</thead>
<%
//如果有信息则显示
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
		<td class="a1" width="15%">出库单编号</td>
		<td class="a2" width="35%"><input type="text" name="ckd.ckd_id" id="ckd_id" value="<%=StringUtils.nullToStr(ckd.getCkd_id()) %>" readonly size="40">
		</td>
		<%
		String cjsj = StringUtils.nullToStr(ckd.getCreatdate());
		if(cjsj.equals("")){
			cjsj = DateComFunc.getToday();
		}
		%>
		<td class="a1">创建日期</td>
		<td class="a2"><input type="text" name="ckd.creatdate" id="creatdate" value="<%=cjsj %>"  size="40" readonly>
		</td>	
	</tr>
	<tr>		
		<td class="a1" width="15%">客户名称</td>
		<td class="a2">		
		<input type="text" name="ckd.client_id" id="client_name" value="<%=StaticParamDo.getClientNameById(StringUtils.nullToStr(ckd.getClient_name())) %>" readonly size="40" maxlength="50">
		<input type="hidden" name="ckd.client_name" id="client_id" value="<%=StringUtils.nullToStr(ckd.getClient_name()) %>">
		</td>
		<td class="a1">联系人</td>
		<td class="a2"><input type="text" name="ckd.client_lxr" id="client_lxr" size="40" value="<%=StringUtils.nullToStr(ckd.getClient_lxr()) %>" readonly>				
	</tr>
	<tr>
		<td class="a1">联系电话</td>
		<td class="a2"><input type="text" name="ckd.client_lxr_tel" id="client_lxr_tel" size="40" value="<%=StringUtils.nullToStr(ckd.getClient_lxr_tel()) %>" readonly>	
		<td class="a1">地址</td>
		<td class="a2"><input type="text" name="ckd.client_lxr_address" id="client_address" size="40" value="<%=StringUtils.nullToStr(ckd.getClient_lxr_address()) %>" readonly></td>			
	</tr>
	<tr>	
		<td class="a1" width="15%">销售订单编号</td>
		<td class="a2"><input type="text" name="ckd.xsd_id" id="xsd_id" value="<%=StringUtils.nullToStr(ckd.getXsd_id()) %>" size="40" readonly maxlength="20"></td>				
		<td class="a1" width="15%">销售负责人</td>
		<td class="a2">
			<input type="text" name="xsry_name" id="xsry_name" value="<%=StaticParamDo.getRealNameById(ckd.getXsry()) %>" size="40" readonly maxlength="20">
			<input type="hidden" name="ckd.xsry" id="xsry" value="<%=StringUtils.nullToStr(ckd.getXsry()) %>" readonly maxlength="20">	
			<input type="hidden" name="ckd.skzt" id="skzt" value="<%=StringUtils.nullToStr(ckd.getSkzt()) %>">	
		</td>			
	</tr>
	<tr>	
		<td class="a1" width="15%">运费支付</td>
		<td class="a2" colspan="3">
			<input type="text" name="ckd.yfzf_type" value="<%=StringUtils.nullToStr(ckd.getYfzf_type()) %>" size="40" readonly>
		</td>							
	</tr>		
</table>	
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">物流信息</td>
	</tr>
	</thead>
	<tr>	
		<td class="a1" width="15%">出货库房</td>
		<td class="a2" width="35%">
			<select name="ckd.store_id" id="store_id" style="width:232px">
				<option value=""></option>
			<%
			if(storeList != null){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse sh = (StoreHouse)it.next();
					String id = StringUtils.nullToStr(sh.getId());
					String name = StringUtils.nullToStr(sh.getName());
			%>
				<option value="<%=id %>" <%if(StringUtils.nullToStr(ckd.getStore_id()).equals(id)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select><font color="red">*</font>	
		</td>
		<td class="a1" width="15%">出库经手人</td>
		<td class="a2" width="35%">
			 <input  id="brand" type="text" maxlength="20" onblur="setValue()" value="<%=StaticParamDo.getRealNameById(ckd.getFzr()) %>" size="40"/> 
	         <div id="brandTip" style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <input type="hidden" name="ckd.fzr" id="fzr" value="<%=ckd.getFzr()%>"/><font color="red">*</font>	
		</td>						
	</tr>		
	<tr>
		<%
		String cksj = StringUtils.nullToStr(ckd.getCk_date());
		if(cksj.equals("")){
			cksj = DateComFunc.getToday();
		}
		%>	
		<td class="a1">出库日期</td>
		<td class="a2"><input type="text" name="ckd.ck_date" id="ck_date" value="<%=cksj %>"  class="Wdate" onFocus="WdatePicker()" size="40">
		</td>	
		<td class="a1" width="15%">运输方式</td>
		<td class="a2" width="35%">
			<select name="ckd.ysfs" id="ysfs" style="width:232px">
				<option value=""></option>
			<%
			if(ysfsArry != null && ysfsArry.length > 0){
				for(int i =0;i<ysfsArry.length;i++){
			%>
				<option value="<%=ysfsArry[i] %>" <%if(ysfsArry[i].equals(ckd.getYsfs())) out.print("selected"); %>><%=ysfsArry[i] %></option>
			<%
				}
			}
			%>
				
			</select>	<font color="red">*</font>	
		</td>			
	</tr>	
	<tr>	
		<td class="a1" width="15%">货单号</td>
		<td class="a2"><input type="text" name="ckd.job_no" id="job_no" value="<%=StringUtils.nullToStr(ckd.getJob_no()) %>" size="40"  maxlength="20"></td>
		<td class="a1" width="15%">查询电话</td>
		<td class="a2"><input type="text" name="ckd.cx_tel" id="cx_tel" value="<%=StringUtils.nullToStr(ckd.getCx_tel()) %>" size="40"  maxlength="20"></td>												
	</tr>
	<tr>	
		<td class="a1" width="15%">发货时间</td>
		<td class="a2" colspan="3"><input type="text" name="ckd.send_time" id="send_time" value="<%=StringUtils.nullToStr(ckd.getSend_time()) %>" size="40" maxlength="20"></td>										
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
		<td class="a2" colspan="2">&nbsp;&nbsp;&nbsp;输入序列号：<input type="text" size="30" name="s_nums" value="" onkeypress="javascript:f_enter()">
			注：输入商品序列号回车，可自动提取商品信息到列表。
		</td>
	</tr>
</table>
<table width="100%"  align="center" id="cktable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="35%">商品名称</td>
		<td width="35%">规格</td>
		<td width="7%">订单数量</td>
		<td width="7%">出库数量</td>
		<td width="16%">强制序列号</td>
	</tr>
	</thead>
<%
if(ckdProducts!=null && ckdProducts.size()>0){
	for(int i=0;i<ckdProducts.size();i++){
		Map ckdProduct = (Map)ckdProducts.get(i);
		
		String cssStyle = "";
		if(StringUtils.nullToStr(ckdProduct.get("qz_flag")).equals("是")){
			cssStyle = "color:red";
		}
		
		double price = ckdProduct.get("price")==null?0:((Double)ckdProduct.get("price")).doubleValue();
		double cbj =  ckdProduct.get("cbj")==null?0:((Double)ckdProduct.get("cbj")).doubleValue();
%>
	<tr>
		<td class="a2">
			<input type="text" id="product_name_<%=i %>" style="width:90%;<%=cssStyle %>" name="ckdProducts[<%=i %>].product_name" value="<%=StringUtils.nullToStr(ckdProduct.get("product_name")) %>" readonly>
			<input type="hidden" id="product_id_<%=i %>" name="ckdProducts[<%=i %>].product_id" value="<%=StringUtils.nullToStr(ckdProduct.get("product_id")) %>">
			
			<input type="hidden" id="price_<%=i %>" name="ckdProducts[<%=i %>].price" value="<%=price %>">
			<input type="hidden" id="cbj_<%=i %>" name="ckdProducts[<%=i %>].cbj" value="<%=cbj %>">
		</td>
		<td class="a2"><input type="text" id="product_xh_<%=i %>"  style="width:90%;<%=cssStyle %>" name="ckdProducts[<%=i %>].product_xh" size="10" value="<%=StringUtils.nullToStr(ckdProduct.get("product_xh")) %>" readonly></td>	
		<td class="a2"><input type="text" id="nums_<%=i %>" name="ckdProducts[<%=i %>].nums" value="<%=StringUtils.nullToStr(ckdProduct.get("nums")) %>" readonly  style="width:90%;text-align:center;<%=cssStyle %>"></td>
		<td class="a2"><input type="text" id="ck_nums_<%=i %>" name="ckdProducts[<%=i %>].ck_nums" value="<%=StringUtils.nullToStr(ckdProduct.get("ck_nums")) %>"  style="width:90%;text-align:center;<%=cssStyle %>"></td>
		<td class="a2">
			<input type="text" id="qz_serial_num_<%=i %>" name="ckdProducts[<%=i %>].qz_serial_num" value="<%=StringUtils.nullToStr(ckdProduct.get("qz_serial_num")) %>"  style="<%=cssStyle %>" size="13" readonly  onclick="openSerialWin('<%=i %>');">
			<input type="hidden" id="qz_flag_<%=i %>" name="ckdProducts[<%=i %>].qz_flag" value="<%=StringUtils.nullToStr(ckdProduct.get("qz_flag")) %>"><a style="cursor:hand" title="左键点击输入输列号" onclick="openSerialWin('<%=i %>');"><b>...</b></a>&nbsp;
		</td>
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<tr>
		<td class="a2" colspan="2">注：列表中红色字体显示商品为强制序列号商品，必须输入序列号。</td>
	</tr>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%"><input type="text" name="ckd.ms" id="ms" size="100" maxlength="100" value="<%=StringUtils.nullToStr(ckd.getMs()) %>"></input>
		</td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="btnSave" value="草 稿" class="css_button2" onclick="saveInfo('1');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnSub" value="出 库" class="css_button2" onclick="saveInfo('2');">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="btnTh" value="退回订单" class="css_button3" onclick="doTh();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button2" value="关 闭" class="css_button2" onclick="window.opener.document.myform.submit();window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>