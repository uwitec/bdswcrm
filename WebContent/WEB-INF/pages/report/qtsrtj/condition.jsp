<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

String[] srlx = (String[])VS.findValue("srlxs");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>其他收入统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
function submits(){
	document.reportForm.submit();
}	
</script>
</head>
<body>
<form name="reportForm" action="getQtsrtjResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">其他收入统计</td>
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
		<td class="a1">收入类型</td>
		<td class="a2" colspan="3">
			<select name="type" id="type" style="width:232px">
				<option value=""></option>
			<%
			if(srlx != null && srlx.length>0){
				for(int i=0;i<srlx.length;i++){
			%>
			<option value="<%=srlx[i] %>"><%=srlx[i] %></option>
			<%		
				}
			}
			%>
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