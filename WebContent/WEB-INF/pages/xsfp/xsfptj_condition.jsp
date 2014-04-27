<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>销售发票统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	function query(){
		document.reportForm.submit();	
	}
</script>
</head>
<body  >
<form name="reportForm" action="xsfptjResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">销售发票统计</td>
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
		<td class="a1">发票类型</td>
		<td class="a4" >
			<select name="fplx" style="width:232px">
				<option value=""></option>
				<option value="普通发票">普通发票</option>
				<option value="增值发票">增值发票</option>
			</select>
		</td>
		<td class="a1">显示明细</td>
		<td class="a4" >
			<select name="flag" style="width:232px">
				<option value="是">是</option>
				<option value="否">否</option>
			</select>
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
