<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Hykda hykda = (Hykda)VS.findValue("hykda");
%>
<html>
<head>
<title>会员卡档案</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">会员卡档案</td>
	</tr>
	</thead>	
	<tr>
	    <td class="a1" width="15%">会员卡号</td>
	    <td class="a2" width="35%"><%=StringUtils.nullToStr(hykda.getHykh()) %></td>
		<td class="a1" width="15%">发卡日期</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(hykda.getFkrq()) %></td>	   
	</tr>
	<tr>
		<td class="a1">经手人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(hykda.getFkjsr()) %></td>
		<td class="a1">会员名称</td>
		<td class="a2"><%=StaticParamDo.getClientNameById(hykda.getHymc()) %></td>		
	</tr>
	<tr>	
		<td class="a1">会员联系人</td>
		<td class="a2"><%=StringUtils.nullToStr(hykda.getLxrname()) %></td>
		<td class="a1">证件号码</td>
		<td class="a2"><%=StringUtils.nullToStr(hykda.getSfzh()) %></td>		
	</tr>
	<tr>
		<td class="a1">性别</td>
		<td class="a2"><%=StringUtils.nullToStr(hykda.getSex()) %></td>					
		<td class="a1">出生日期</td>
		<td class="a2"><%=StringUtils.nullToStr(hykda.getBirth()) %></td>		
	</tr>
	<tr>
		<td class="a1">联系电话</td>
		<td class="a2"><%=StringUtils.nullToStr(hykda.getLxdh()) %></td>
		<td class="a1">手机</td>
		<td class="a2"><%=StringUtils.nullToStr(hykda.getMobile()) %></td>
	</tr>
	<tr>
		<td class="a1">E-Mail</td>
		<td class="a2"><%=StringUtils.nullToStr(hykda.getMail()) %></td>
		<td class="a1">单位名称</td>
		<td class="a2"><%=StringUtils.nullToStr(hykda.getGzdw()) %></td>
	</tr>
	<tr>
		<td class="a1">地址</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(hykda.getAddress()) %></td>
	</tr>	
	<tr>
		<td class="a1">备注</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(hykda.getFkbz()) %></td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>