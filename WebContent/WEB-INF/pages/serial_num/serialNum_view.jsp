<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>序列号管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
</head>
<body>
<form name="serialNumForm" action="updateSerialNum.html" method="post">
<ww:hidden name="serialNumMng.product_id" id="product_id" value="%{serialNumMng.product_id}" theme="simple"/>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">序列号管理</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="25%">序列号</td>
		<td class="a2" width="75%"><ww:property value="%{serialNumMng.serial_num}" /></td>		
	</tr>
	<tr>
		<td class="a1" width="25%">商品名称</td>
		<td class="a2" width="75%"><ww:property value="%{serialNumMng.product_name}" /></td>
	</tr>
	<tr>
		<td class="a1" width="25%">商品规格</td>
		<td class="a2" width="75%"><ww:property value="%{serialNumMng.product_xh}" /></td>
	</tr>
	<tr>
		<td class="a1" width="25%">状态</td>
		<td class="a2" width="75%"><ww:property value="%{serialNumMng.state}" /></td>		
	</tr>
	<tr>
		<td class="a1" width="25%">所在库房</td>
		<td class="a2" width="75%"><ww:property value="%{serialNumMng.store_name}" /></td>		
	</tr>
	<tr>
		<td class="a1" width="25%">是否样机</td>
		<td class="a2" width="75%"><ww:if test="yj_flag==1">是</ww:if><ww:else>否</ww:else></td>		
	</tr>			
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="关 闭" class="css_button" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
