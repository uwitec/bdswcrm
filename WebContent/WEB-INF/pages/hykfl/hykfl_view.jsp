<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.model.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Hykfl hykfl = (Hykfl)VS.findValue("hykfl");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>会员卡分类</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="2">会员卡分类</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="25%">编号</td>
		<td class="a2" width="75%"><%=StringUtils.nullToStr(hykfl.getId()) %></td>
	</tr>
	
	<tr>
		<td class="a1">名称</td>
		<td class="a2"><%=StringUtils.nullToStr(hykfl.getName()) %></td>
	</tr>
	<tr>
		<td class="a1">积分方式</td>
		<td class="a2"><%=StaticParamDo.getJfgzNameById(hykfl.getJffs()) %></td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="2">			
			<input type="button" name="button3" value="关闭" class="css_button" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>