<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>库存成本变化</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">

	function openWin(){
		var destination = "selKind.html";
		var fea ='width=400,height=500,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'选择商品类别',fea);
	}		

	function openProduct(){
		var destination = "selectProduct.html";
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'选择商品',fea);
	}

	function query(){
		if(document.getElementById("start_date").value == ""){
			alert("开始日期不能为空！");
			return;
		}
		if(document.getElementById("end_date").value == ""){
			alert("结束日期不能为空!");
			return;
		}
		if(document.getElementById("product_name_").value == ""){
			alert("商品名称不能为空!");
			return;
		}
		document.reportForm.submit();						
	}
</script>
</head>
<body  >
<form name="reportForm" action="getKcJeHzResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">库存成本变化</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="0" id="selTable">
	<tr>
		<td class="a1" width="15%">开始日期</td>
		<td class="a4" width="35%">
			<input type="text" name="start_date" id="start_date" value="<%=DateComFunc.getToday() %>" style="width:220px" class="Wdate" onFocus="WdatePicker()"></td>
		<td class="a1" width="15%">结束日期</td>
		<td class="a4" width="35%">
			<input type="text" name="end_date" id="end_date" value="<%=DateComFunc.getToday() %>"  style="width:220px" class="Wdate" onFocus="WdatePicker()"></td>
	</tr>
	<tr>
		<td class="a1">商品名称</td>
		<td class="a4" colspan="3">
			<input type="text" name="product_name" id="product_name_" onclick="openProduct();" style="width:220px" value="" readonly>
			<img src="images/select.gif" align="absmiddle" title="点击选择类别" border="0" onclick="openProduct();" style="cursor:hand">
		</td>
	</tr>		
	<tr height="35">
		<td class="a1" colspan="4" height="35">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="query();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">
		</td>
	</tr>
</table>
</form>
</body>
</html>
