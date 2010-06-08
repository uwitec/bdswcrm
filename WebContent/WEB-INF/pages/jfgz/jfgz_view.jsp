<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Jfgz jfgz = (Jfgz)VS.findValue("jfgz");
%>

<html>
<head>
<title>积分规则</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="xhEditor/jquery/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="xhEditor/xheditor.js"></script>
<script type="text/javascript">
</script>
</head>
<body >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">积分规则设置</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">积分编号</td>
		<td class="a2" width="80%" colspan="3">
		<%=StringUtils.nullToStr(jfgz.getId()) %></td>
	</tr>
	
	<tr>
		<td class="a1" width="15%">积分方法</td>
		<td class="a2" width="80%" colspan="3">
		<%=StringUtils.nullToStr(jfgz.getJfff()) %></td>
	</tr>
	
	<tr>
		<td class="a1" width="15%">消费金额</td>
		<td class="a2" colspan="3">
		<%=StringUtils.nullToStr(jfgz.getXfje()) %>
		</td>
	</tr>
	<tr>
		<td class="a1" width="15%">对应积分</td>
		<td class="a2" colspan="3">
		<%=StringUtils.nullToStr(jfgz.getDyjf()) %>
		</td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="4">			
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</body>
</html>