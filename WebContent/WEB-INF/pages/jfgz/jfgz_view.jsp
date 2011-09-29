<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Jfgz jfgz = (Jfgz)VS.findValue("jfgz");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>积分规则</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="2">积分规则</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="25%">积分编号</td>
		<td class="a2" width="75%"><%=StringUtils.nullToStr(jfgz.getId()) %></td>
	</tr>
	
	<tr>
		<td class="a1">积分方法</td>
		<td class="a2"><%=StringUtils.nullToStr(jfgz.getJfff()) %></td>
	</tr>
	
	<tr>
		<td class="a1">消费金额</td>
		<td class="a2"><%=StringUtils.nullToStr(jfgz.getXfje()) %></td>
	</tr>
	<tr>
		<td class="a1">对应积分</td>
		<td class="a2"><%=StringUtils.nullToStr(jfgz.getDyjf()) %></td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="2">			
			<input type="button" name="button3" value="关 闭" class="css_button" onclick="window.close();">
		</td>
	</tr>
</table>

</body>
</html>