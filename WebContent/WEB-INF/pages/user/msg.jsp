<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%
String msg = (String)session.getAttribute("MSG");
session.removeAttribute("MSG");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>消息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body oncontextmenu="return false;" style="margin-top:80px;">
<table width="400" height="30"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td><B>系统消息</B></td>
	</tr>
	</thead>
</table>
<table width="400" height="200"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="170">
		<td class="a1">
			<%=msg %><BR><BR><BR>		
			<input type="button" name="button" value="返回" class="css_button2" onclick="location.href='changePass.html';">
		</td>
	</tr>
</table>
</body>
</html>
