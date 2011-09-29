<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Djhpsz djhpsz = (Djhpsz)VS.findValue("djhpsz");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>兑奖货品设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
</script>
</head>
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">兑奖货品设置</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">产品编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(djhpsz.getProduct_id()) %></td>
	</tr>
	<tr>	
		<td class="a1">产品名称</td>
		<td class="a2"><%=StringUtils.nullToStr(djhpsz.getProduct_name()) %></td>
	</tr>
	<tr>	
		<td class="a1" width="15%">产品规格</td>
		<td class="a2"><%=StringUtils.nullToStr(djhpsz.getProduct_xh()) %></td>	
	</tr>
	<tr>	
		<td class="a1" width="15%">对应积分</td>
		<td class="a2"><%=StringUtils.nullToStr(djhpsz.getDwjf()) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">操作人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(djhpsz.getCzr())) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">操作时间</td>
		<td class="a2"><%=StringUtils.nullToStr(djhpsz.getCz_date()) %></td>			
	</tr>	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.close();;">
		</td>
	</tr>
</table>
</body>
</html>
