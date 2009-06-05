<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>


<html>
<head>
<title>应付汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
function doSubmit(){
	if(document.reportForm.start_date.value == ""){
		alert("开始日期不能为空，请选择！");
		return;
	}
	if(document.reportForm.end_date.value == ""){
		alert("结束日期不能为空，请选择！");
		return;
	}
	document.reportForm.submit();
}
</script>
</head>
<body  >
<form name="reportForm" action="getYfHzResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">应付汇总</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<tr>
		<td class="a1">开始日期</td>
		<td class="a4">
			<input type="text" name="start_date" id="start_date" value="<%=DateComFunc.getToday() %>" readonly>
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('start_date')); return false;"></td>
		<td class="a1">结束日期</td>
		<td class="a4">
			<input type="text" name="end_date" id="end_date" value="<%=DateComFunc.getToday() %>" readonly>
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.getElementById('end_date')); return false;"></td>
	</tr>
	<tr>
		<td class="a1" width="25%">客户名称</td>
		<td class="a4">
			<input type="text" name="client_name" id="client_name" value="" size="30">
		</td>
		<td class="a1">不显示0往来单位</td>
		<td class="a4">	
			<select name="flag" id="flag">
				<option value="否">否</option>
				<option value="是">是</option>				
			</select>
		</td>						
	</tr>	
	<tr>
		<td class="a1">不显示余额为0往来单位</td>
		<td class="a4" colspan="3">	
			<select name="flag2" id="flag2">
				<option value="是">是</option>
				<option value="否">否</option>
			</select>
		</td>						
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="doSubmit();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">
		</td>
	</tr>
</table>
</form>
</body>
</html>
