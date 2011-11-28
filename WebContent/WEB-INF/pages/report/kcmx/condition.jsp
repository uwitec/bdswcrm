<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List store_list = (List)VS.findValue("store_list");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>出入库汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">

	function subForm(){
		document.reportForm.submit();
	}
	
	function openWin(){
		var destination = "selKind.html";
		var fea ='width=400,height=500,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择商品类别',fea);
	}	
	
</script>
</head>
<body>
<form name="reportForm" action="getKcMxResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">出入库汇总</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="0" id="selTable">
	<tr>
		<td class="a1">开始日期</td>
		<td class="a4">
			<input type="text" name="start_date" id="start_date" value="<%=DateComFunc.getToday() %>"  style="width:232px" class="Wdate" onFocus="WdatePicker()"></td>
		<td class="a1">结束日期</td>
		<td class="a4">
			<input type="text" name="end_date" id="end_date" value="<%=DateComFunc.getToday() %>"  style="width:232px" class="Wdate" onFocus="WdatePicker()"></td>
	</tr>
	<tr>
		<td class="a1" width="15%">商品类别</td>
		<td class="a4" width="35%">
			<input type="text" name="kind_name" id="kind_name" value="" size="30" onclick="openWin();" style="width:232px" readonly>
			<input type="hidden" name="product_kind" id="product_kind" value="">
			<img src="images/select.gif" align="absmiddle" title="点击选择类别" border="0" onclick="openWin();" style="cursor:hand">
		</td>				
		<td class="a1">商品名称</td>
		<td class="a4">
			<input type="text" name="product_name" id="product_name" value="" style="width:232px">
		</td>					
	</tr>
	<tr>
		<td class="a1">按分仓库存统计</td>
		<td class="a4">
			<select name="store_id" id="store_id" style="width:232px">
				<option value=""></option>
				<%
				if(store_list != null && store_list.size()>0){
					for(int i=0;i<store_list.size();i++){
						StoreHouse store = (StoreHouse)store_list.get(i);
				%>
				<option value="<%=StringUtils.nullToStr(store.getId()) %>"><%=StringUtils.nullToStr(store.getName()) %></option>
				<%
					}
				}
				%>
			</select>
		</td>	
		<td class="a1">是否显示0库存商品</td>
		<td class="a4">
			<select name="isKc0" style="width:232px">
				<option value="否">否</option>
				<option value="是">是</option>
			</select>
		</td>			
	</tr>	
	<tr>
		<td class="a1">是否显示0发生额商品</td>
		<td class="a4" colspan="3">
			<select name="isFse0" style="width:232px">
				<option value="否">否</option>
				<option value="是" selected>是</option>
			</select>
		</td>			
	</tr>		
	<tr height="35">
		<td class="a1" colspan="4" height="35">
			<input type="button" name="button1" value="提 交" onclick="subForm();" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">
		</td>
	</tr>
</table>
</form>
</body>
</html>
