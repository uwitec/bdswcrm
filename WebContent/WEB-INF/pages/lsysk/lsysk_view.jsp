<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Lsysk lsysk = (Lsysk)VS.findValue("lsysk");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>零售预收款</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">零售预收款</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(lsysk.getId()) %></td>
		<td class="a1" width="15%">预收日期</td>
		<td class="a2"><%=StringUtils.nullToStr(lsysk.getYs_date()) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">客户名称</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(lsysk.getClient_name()) %></td>	
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(lsysk.getLxr()) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">联系电话</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(lsysk.getLxdh()) %></td>	
		<td class="a1" width="15%">手机</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(lsysk.getMobile()) %></td>		
	</tr>
	<tr>						
		<td class="a1" width="15%">经手人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(lsysk.getJsr())) %></td>	
		<td class="a1" width="15%">预收金额</td>
		<td class="a2" width="35%"><%=JMath.round(lsysk.getYsje()) %></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">收款账户</td>
		<td class="a2" width="35%"><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(lsysk.getSkzh())) %></td>
		<td class="a1">客户付款方式</td>
		<td class="a2"><%=StringUtils.nullToStr(lsysk.getFkfs()) %>&nbsp;<%=StaticParamDo.getPosNameById(lsysk.getPos_id()) %></td>
	</tr>
	<tr>				
		<td class="a1" width="15%">状态</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(lsysk.getState()) %></td>		
	</tr>
	<tr>
		<td class="a1">备注</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(lsysk.getRemark()) %></td>
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
