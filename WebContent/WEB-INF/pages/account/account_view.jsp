<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Map map = (Map)VS.findValue("accountMap");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>账号资料</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body oncontextmenu="return false;" >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">账户资料</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">账户名称</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(map.get("name")) %></td>
		<td class="a1" width="15%">账户类型</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(map.get("type")) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">开户行</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(map.get("bank")) %></td>
		<td class="a1" width="15%">账号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(map.get("bank_count")) %></td>		
	</tr>		
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">备 注</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="20%">备注</td>
		<td class="a2" width="80%"><%=StringUtils.nullToStr(map.get("remark")) %></td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
