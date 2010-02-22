<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Shkc shkc = (Shkc)VS.findValue("shkc");

%>
<html>
<head>
<title>售后库存</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
</head>
<body>
<form name="serialNumForm" action="updateSerialNum.html" method="post">
 
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">售后库存管理</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="25%">序列号</td>
		<td class="a2" width="75%"><%=StringUtils.nullToStr(shkc.getQz_serial_num())%></td>		
	</tr>
	<tr>
		<td class="a1" width="25%">产品名称</td>
		<td class="a2" width="75%"><%=StringUtils.nullToStr(shkc.getProduct_name())%></td>
	</tr>
	<tr>
		<td class="a1" width="25%">产品规格</td>
		<td class="a2" width="75%"><%=StringUtils.nullToStr(shkc.getProduct_xh())%></td>
	</tr> 
	<tr>
		<td class="a1" width="25%">状态</td>
		<td class="a2" width="75%"><%String str=StringUtils.nullToStr(shkc.getState()); if(str.equals("1"))out.println("坏件库");if(str.equals("2"))out.println("在外库");if(str.equals(""))out.println("好件库");%></td>		
	</tr>
	
	<tr>
		<td class="a1" width="25%">客户名称</td>
		<td class="a2" width="75%"><%=StringUtils.nullToStr(StaticParamDo.getClientNameById(shkc.getClient_name()))%></td>		
	</tr>
	<tr>
		<td class="a1" width="25%">联系人</td>
		<td class="a2" width="75%"><%=StringUtils.nullToStr(shkc.getLinkman())%></td>		
	</tr>
	<tr>
		<td class="a1" width="25%">联系电话</td>
		<td class="a2" width="75%"><%=StringUtils.nullToStr(shkc.getLxdh())%></td>		
	</tr>	
	<tr>
		<td class="a1" width="25%">手机</td>
		<td class="a2" width="75%"><%=StringUtils.nullToStr(shkc.getMobile())%></td>		
	</tr>
	<tr>
		<td class="a1" width="25%">备注</td>
		<td class="a2" width="75%"><%=StringUtils.nullToStr(shkc.getRemark())%></td>		
	</tr>					
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="关 闭" class="css_button" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
