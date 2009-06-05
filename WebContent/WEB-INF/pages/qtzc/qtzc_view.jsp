<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Qtzc qtzc = (Qtzc)VS.findValue("qtzc");
List userList = (List)VS.findValue("userList");

%>

<html>
<head>
<title>一般费用</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body oncontextmenu="return false;" >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">一般费用</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(qtzc.getId()) %></td>
		<td class="a1" width="15%">支出日期</td>
		<td class="a2"><%=StringUtils.nullToStr(qtzc.getZc_date()) %></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">费用类型</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(qtzc.getType()) %></td>
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%"><%=JMath.round(qtzc.getZcje(),2) %></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">支出账号</td>
		<td class="a2" width="35%"><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(qtzc.getZczh())) %></td>	
		<td class="a1" width="15%">付款方式</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(qtzc.getFklx()) %></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">出纳</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(qtzc.getJsr())) %></td>	
		<td class="a1" width="15%">业务员</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(qtzc.getYwy())) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">相关客户</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(qtzc.getZcxm()) %></td>	
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(qtzc.getState()) %></td>		
	</tr>	
	<tr>
		<td class="a1" width="15%">审批人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(qtzc.getSpr())) %></td>
		<td class="a1" width="15%">审批时间</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(qtzc.getSp_date()) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">费用申请单编号</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(qtzc.getFysq_id()) %></td>
	</tr>	
	<tr height="50">
		<td class="a1">详细说明</td>
		<td class="a2" colspan="3">
			<textarea rows="2" cols="50" name="qtzc.remark" id="remark" style="width:80%" maxlength="500" readonly><%=StringUtils.nullToStr(qtzc.getRemark()) %>
			</textarea>
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
