<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List deptList = (List)VS.findValue("deptList");
String[] fy_types = (String[])VS.findValue("fy_types");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>部门费用汇总表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
function submits(){
	document.reportForm.submit();
}	

function openFyType(){
	var destination = "selFyType.html";
	var fea ='width=400,height=400,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=yes,toolbar=no,scrollbars=yes,resizeable=no';
	
	window.open(destination,'费用类别',fea);	
}
</script>
</head>
<body>
<form name="reportForm" action="getDeptFytjResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">部门费用汇总表</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="0" id="selTable">
	<tr>
		<td class="a1">开始日期</td>
		<td class="a4">
			<input type="text" name="start_date" id="start_date" style="width:232px" value="<%=DateComFunc.getToday() %>" class="Wdate" onFocus="WdatePicker()"></td>
		<td class="a1">结束日期</td>
		<td class="a4">
			<input type="text" name="end_date" id="end_date" style="width:232px" value="<%=DateComFunc.getToday() %>" class="Wdate" onFocus="WdatePicker()"></td>
	</tr>
	<tr>
		<td class="a1">费用类型</td>
		<td class="a2">
			<input type="text" name="fy_type_show" style="width:232px" id="fy_type_show" value="" readonly onclick="openFyType();"/>
			<input type="hidden" name="fy_type" id="fy_type" value=""/>
			<img src="images/select.gif" align="absmiddle" title="选择费用类型" border="0" onclick="openFyType();" style="cursor:hand">
		</td>
		<td class="a1">部门等级</td>
		<td class="a4">
			<select name="dj" style="width:232px">
				<option value="1">第一级</option>
				<option value="2">第二级</option>
				<option value="3">第三级</option>
				<option value="4">第四级</option>
			</select>
		</td>		
	</tr>	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" onclick="submits()" name="button1" value="提 交" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">
		</td>
	</tr>
</table>
</form>
</body>
</html>