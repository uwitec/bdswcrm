<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.sw.cms.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>对账单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	function sbForm(){
		if(document.getElementById("start_date").value==""){
			alert("开始时间不能为空，请选择！");
			return;
		}
		if(document.getElementById("end_date").value==""){
			alert("结束时间不能为空，请选择！");
			return;
		}
		document.reportForm.submit();
	}
</script>
</head>
<body  >
<form name="reportForm" action="getDlsDzdResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">对账单</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<tr>
		<td class="a1">开始日期</td>
		<td class="a4">
			<input type="text" name="start_date" id="start_date" value="<%=DateComFunc.getToday() %>" size="15"  class="Wdate" onFocus="WdatePicker()">
		</td>
		<td class="a1">结束日期</td>
		<td class="a4">
			<input type="text" name="end_date" id="end_date" value="<%=DateComFunc.getToday() %>" size="15"  class="Wdate" onFocus="WdatePicker()">
		</td>
	</tr>
	<tr>	
		<td class="a1">显示明细</td>
		<td class="a4">
			<select name="flag">
				<option value="否">否</option>	
				<option value="是">是</option>							
			</select>
		</td>
		<td class="a1"></td>
		<td class="a4"></td>										
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
