<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Pz pz = (Pz)VS.findValue("pz");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>往来调账</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body oncontextmenu="return false;" >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">往来调账</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(pz.getId()) %></td>
		<td class="a1" width="15%">日期</td>
		<td class="a2"><%=StringUtils.nullToStr(pz.getPz_date()) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">类型</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(pz.getType()) %></td>
		<td class="a1" width="15%">调账项目</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(pz.getPzxm()) %></td>		

	</tr>	
	<tr>
		<td class="a1" width="15%">经手人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(pz.getJsr())) %></td>		
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="35%"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(pz.getClient_name())) %></td>			
	</tr>
	<tr>
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%" colspan="3"><%=JMath.round(pz.getPzje(),2) %></td>	
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
			<textarea rows="3" cols="50" name="pz.remark" id="remark" style="width:80%" maxlength="500" readonly><%=StringUtils.nullToStr(pz.getRemark()) %></textarea>
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
