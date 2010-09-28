<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
String msg = StringUtils.nullToStr(VS.findValue("msg"));
if(msg.equals("")){
	msg = "系统出错，请与管理员联系！";
}
%>
<html>
<head>
<title>错误页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body style="margin-top:40px;">
<table width="400" height="30"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td><B>系统错误</B></td>
	</tr>
	</thead>
</table>
<table width="400" height="200"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="170">
		<td class="a1">
			<%=msg %><BR><BR><BR>
			<input type="button" name="button" value="重新登录" class="css_button2" onclick="window.top.location='login.jsp';">&nbsp;&nbsp;			
			<input type="button" name="button" value="返回" class="css_button2" onclick="history.go(-1);">
		</td>
	</tr>
</table>
</body>
</html>
