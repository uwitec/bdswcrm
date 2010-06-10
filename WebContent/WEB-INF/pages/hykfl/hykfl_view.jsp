<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.model.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Hykfl hykfl = (Hykfl)VS.findValue("hykfl");
%>

<html>
<head>
<title>会员卡分类</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
</script>
</head>
<body >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">会员卡分类</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="80%" colspan="3"><%=StringUtils.nullToStr(hykfl.getId()) %></td>
	</tr>
	
	<tr>
		<td class="a1" width="15%">名称</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(hykfl.getName()) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">积分方式</td>
		<td class="a2" colspan="3"><%=StaticParamDo.getJfgzNameById(hykfl.getJffs()) %></td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">			
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</body>
</html>