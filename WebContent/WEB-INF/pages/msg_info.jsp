<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>系统提示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body oncontextmenu="return false;" style="margin-top:80px;">
<table width="400" height="30"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td><B>系统提示</B></td>
	</tr>
	</thead>
</table>
<table width="400" height="200"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="170">
		<td class="a1">
			<ww:property value="%{msg}"/><BR><BR><BR>			
			<input type="button" name="button" value=" 返回 " class="css_button2" onclick="history.go(-1);">
		</td>
	</tr>
</table>
</body>
</html>
