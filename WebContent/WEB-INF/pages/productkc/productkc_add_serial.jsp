<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

int kc_nums = (Integer)VS.findValue("kc_nums");
String store_id = (String)VS.findValue("store_id");

Product product = (Product)VS.findValue("product");

%>

<html>
<head>
<title>库存管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript">	
	
	
	//触发点击回车事件
	function f_enter(){
	    if (window.event.keyCode==13){
	        sendSerialNum();
	    }
	}
	
	//发送序列号
	function sendSerialNum(){
		var serialNum = dwr.util.getValue("serial_num");
		if(serialNum == ""){
			alert("序列号不能为空，请输入！");
			return;
		}
		var product_id = dwr.util.getValue("product_id");
		var store_id = dwr.util.getValue("store_id");
		if(store_id == ""){
			alert("库房名称不能为空，请选择！");
			return;
		}
		
		saveSerialNumKc();
		
		dwr.util.setValue("kc_nums",parseInt(dwr.util.getValue("kc_nums"))+1);
		
		dwr.util.setValue("serial_num","");	
	}
	
	
	//更新库存，保存序列号状态
	function saveSerialNumKc(){
		var serialNum = dwr.util.getValue("serial_num");
		var product_id = dwr.util.getValue("product_id");
		var store_id = dwr.util.getValue("store_id");
		
		dwrService.updateProductKc(product_id,store_id,serialNum);
	}
	
	//查询库存值，发送条件到服务器端
	function setKcCondition(){
		var product_id = dwr.util.getValue("product_id");
		var store_id = dwr.util.getValue("store_id");
		
		if(store_id == null){
			return;
		}
		
		dwrService.getProudctKcNums(product_id,store_id,doReturnNums);
	}
	
	//处理返回库存值
	function doReturnNums(nums){
		if(nums == null || nums == ""){
			nums = "0";
		}
		dwr.util.setValue("zm_nums",nums);
	}
	
	//生成库存期初
	function doGenKcqc(){
		var productId = dwr.util.getValue("product_id");
		var storeId = dwr.util.getValue("store_id");
		var kcNums = dwr.util.getValue("kc_nums");
		
		if(kcNums == 0){
			if(!window.confirm("商品实际库存为0,确认吗？点击确认系统将生成期初，点击取消继续初始化！")){
				return;
			}
		}
		
		dwrService.genKcqc(productId,storeId,kcNums);
		
		alert("库存初始成功！");
		parent.opener.document.myform.submit();
		window.close();
	}
		
</script>
</head>
<body style="margin-top:0px;">
<form name="productKcForm" action="saveProductKc.html" method="post" target="_parent">
<input type="hidden" name="product_id" id="product_id" value="<%=StringUtils.nullToStr(product.getProductId()) %>">
<input type="hidden" name="store_id" id="store_id" value="<%=store_id %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td class="a1" colspan="2" width="100%"><B>库存初始</B></td>
	</tr>
	</thead>
	<tr>
		<td class="a1">产品编号</td>
		<td class="a2"><%=StringUtils.nullToStr(product.getProductId()) %></td>	
	</tr>
	<tr>
		<td class="a1">产品名称</td>
		<td class="a2"><%=StringUtils.nullToStr(product.getProductName()) %></td>	
	</tr>
	<tr>
		<td class="a1">产品规格</td>
		<td class="a2"><%=StringUtils.nullToStr(product.getProductXh()) %></td>	
	</tr>
	<tr>
		<td class="a1">成本价</td>
		<td class="a2"><%=JMath.round(product.getPrice(),2) %></td>	
	</tr>				
	<tr>
		<td class="a1">库房名称</td>
		<td class="a2"><%=StaticParamDo.getStoreNameById(store_id) %></td>
	</tr>
	<tr>
		<td class="a1">账面库存</td>
		<td class="a2"><%=kc_nums %></td>	
	</tr>	
	<tr>
		<td class="a1">实际库存</td>
		<td class="a2"><input type="text" name="kc_nums" value="0" size="5" readonly></td>	
	</tr>
	<tr>
		<td class="a1">序列号</td>
		<td class="a2">
			<input type="text" name="serial_num" id="serial_num" value="" size="15" onkeypress="javascript:f_enter()" title="输入序列号后回车，自动保存">
		</td>	
	</tr>	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="确定" class="css_button" onclick="doGenKcqc();">
		</td>
	</tr>
</table>
<BR>说明：<BR>
&nbsp;&nbsp;&nbsp;&nbsp;一、将光标定位于序列号输入框，用扫描枪扫描产品序列号，或输入序列号回车，系统自动保存产品序列号信息，并累加实际库存数。<BR>
&nbsp;&nbsp;&nbsp;&nbsp;二、所有库存序列号输入完成后，请点击“确定”按钮完成产品库存的初始，非法关闭窗口，产品初始化无效，要求一类产品的初始一次完成。
</form>
</body>
</html>
