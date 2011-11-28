<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.sw.cms.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>货品销售执行汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}
	
	function openWin(){
		var destination = "selKind.html";
		var fea ='width=400,height=500,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择商品类别',fea);
	}
	
	function subForm(){
	  document.reportForm.submit();
	}		
</script>
</head>
<body>
<form name="reportForm" action="getHpxsHzCondition.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">货品销售执行汇总</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="0" id="selTable">
	<tr>
		<td class="a1">开始日期</td>
		<td class="a2">
			<input type="text" name="start_date" size="25" id="start_date" value="<%=DateComFunc.getToday() %>" style="width:232px"  class="Wdate" onFocus="WdatePicker()"></td>
		<td class="a1">结束日期</td>
		<td class="a2">
			<input type="text" name="end_date" size="25" id="end_date" value="<%=DateComFunc.getToday() %>" style="width:232px"  class="Wdate" onFocus="WdatePicker()"></td>
	</tr>
	<tr>
		<td class="a1" width="15%">商品类别</td>
		<td class="a2" width="35%">
			<input type="text" name="kind_name" id="kind_name" value="" size="25" style="width:232px" onclick="openWin();" readonly>
			<input type="hidden" name="productKind" id="product_kind" value="">
			<img src="images/select.gif" align="absmiddle" title="点击选择类别" border="0" onclick="openWin();" style="cursor:hand">
		</td>	
		<td class="a1" width="15%">商品名称</td>
		<td class="a2" width="35%">
			<input type="text" name="product_name" id="product_name" value="" style="width:232px" size="25">
		</td>		
	</tr>
	<tr>		
		<td class="a1">商品规格</td>
		<td class="a2" colspan="3">
			<input type="text" name="product_xh" id="product_xh" value="" style="width:232px" size="25">	
		</td>
	</tr>		
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1"  onclick="subForm();" value="提 交" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">
		</td>
	</tr>
</table>
</form>
</body>
</html>