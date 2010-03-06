<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Qtzc qtzc = (Qtzc)VS.findValue("qtzc");
%>

<html>
<head>
<title>一般费用</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">费用申请信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">费用申请单编号</td>
		<td class="a2"><%=StringUtils.nullToStr(qtzc.getFysq_id()) %></td>
		<td class="a1" width="15%">费用类型</td>
		<td class="a2" width="35%"><%=StaticParamDo.getFyTypeNameById(qtzc.getType()) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">费用申请人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(qtzc.getSqr()) %></td>		
		<td class="a1" width="15%">费用使用人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(qtzc.getYwy()) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">费用使用部门</td>
		<td class="a2"><%=StaticParamDo.getDeptNameById(qtzc.getYwy_dept()) %></td>			
		<td class="a1" width="15%">对应客户</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(qtzc.getZcxm()) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">审批人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(qtzc.getSpr())) %></td>
		<td class="a1" width="15%">审批时间</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(qtzc.getSp_date()) %></td>
	</tr>
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">费用支出信息</td>
	</tr>
	</thead>					
	<tr>
		<td class="a1" width="15%">支出编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(qtzc.getId()) %></td>
		<td class="a1" width="15%">支出日期</td>
		<td class="a2"><%=StringUtils.nullToStr(qtzc.getZc_date()) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">出纳</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(qtzc.getJsr()) %></td>	
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%"><%=JMath.round(qtzc.getZcje()) %></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">支出账号</td>
		<td class="a2" width="35%"><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(qtzc.getZczh())) %></td>	
		<td class="a1" width="15%">付款方式</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(qtzc.getFklx()) %></td>	
	</tr>
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="2">备注</td>
	</tr>
	</thead>	
	<tr height="50">
		<td class="a1" width="15%">详细说明</td>
		<td class="a2" width="85%">
			<textarea rows="3" cols="50" name="qtzc.remark" id="remark" style="width:80%" maxlength="500" readonly><%=StringUtils.nullToStr(qtzc.getRemark()) %></textarea>
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