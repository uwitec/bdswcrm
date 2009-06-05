<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Qtsr qtsr = (Qtsr)VS.findValue("qtsr");

%>

<html>
<head>
<title>其它收入</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body oncontextmenu="return false;" >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">其它收入</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(qtsr.getId()) %></td>
		<td class="a1" width="15%">收入日期</td>
		<td class="a2"><%=StringUtils.nullToStr(qtsr.getSr_date()) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">类型</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(qtsr.getSr_date()) %></td>
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%"><%=JMath.round(qtsr.getSkje(),2) %></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">收款账号</td>
		<td class="a2" width="35%"><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(qtsr.getSkzh())) %></td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(qtsr.getJsr())) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(qtsr.getState()) %></td>		
	</tr>
	
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">备 注</td>
	</tr>
	</thead>
	<tr height="50">
		<td class="a1" width="20%">备注</td>
		<td class="a2" width="80%">
			<textarea rows="3" cols="50" name="qtsr.remark" id="remark" style="width:80%" maxlength="500" readonly><%=StringUtils.nullToStr(qtsr.getRemark()) %></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
