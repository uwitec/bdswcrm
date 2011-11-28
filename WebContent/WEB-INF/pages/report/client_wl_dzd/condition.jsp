<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
 
List clientsList=(List)VS.findValue("clientsList");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户往来对账单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type="text/javascript">
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}
	
	function sbForm(){
		if(document.getElementById("start_date").value==""){
			alert("开始时间不能为空，请选择！");
			return;
		}
		if(document.getElementById("end_date").value==""){
			alert("结束时间不能为空，请选择！");
			return;
		}			
		if(document.getElementById("client_id").value==""){
			alert("客户名称不能为空，请选择！");
			return;
		}
		document.reportForm.submit();
	}
</script>
</head>
<body onload="initClientTip();">
<form name="reportForm" action="getClientWlDzdResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">客户往来对账单</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="0" id="selTable">
	<tr>
		<td class="a1">开始日期</td>
		<td class="a4">
			<input type="text" name="start_date" id="start_date" value="<%=DateComFunc.getToday() %>" style="width:232px" class="Wdate" onFocus="WdatePicker()"></td>
		<td class="a1">结束日期</td>
		<td class="a4">
			<input type="text" name="end_date" id="end_date" value="<%=DateComFunc.getToday() %>" style="width:232px" class="Wdate" onFocus="WdatePicker()"></td>
	</tr>
	<tr>
		<td class="a1">客户名称</td>
		<td class="a4">
		<input type="text" name="clientId" id="client_name" value="" onblur="setClientValue();" style="width:232px"  size="30" maxlength="50">
		<input type="hidden" name="clientName" id="client_id" value="">
			<div id="clientsTip" style="position:absolute;width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		</td>	
		<td class="a1">显示明细</td>
		<td class="a4">
			<select name="flag" style="width:232px">
				<option value="否">否</option>	
				<option value="是">是</option>							
			</select>
		</td>								
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="sbForm();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">
		</td>
	</tr>
</table>
</form>
</body>
</html>