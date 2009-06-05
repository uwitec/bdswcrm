<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Nbzz nbzz = (Nbzz)VS.findValue("nbzz");

List userList = (List)VS.findValue("userList");

%>

<html>
<head>
<title>内部转账</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body oncontextmenu="return false;" >
<form name="nbzzForm" action="updateNbzz.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">内部转账</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(nbzz.getId()) %></td>
		<td class="a1" width="15%">转账日期</td>
		<td class="a2"><%=StringUtils.nullToStr(nbzz.getZz_date()) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">转出账户</td>
		<td class="a2" width="35%"><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(nbzz.getZczh())) %></td>
		<td class="a1" width="15%">转入账户</td>
		<td class="a2" width="35%"><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(nbzz.getZrzh())) %></td>
	</tr>	
	<tr>					
		<td class="a1" width="15%">转账金额</td>
		<td class="a2" width="35%"><%=JMath.round(nbzz.getZzje(),2) %></td>	
		<td class="a1" width="15%">经手人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(nbzz.getJsr())) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(nbzz.getState()) %></td>		
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
			<textarea rows="3" cols="50" name="nbzz.remark" id="remark" style="width:80%" maxlength="500" readonly><%=StringUtils.nullToStr(nbzz.getRemark()) %></textarea>
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
