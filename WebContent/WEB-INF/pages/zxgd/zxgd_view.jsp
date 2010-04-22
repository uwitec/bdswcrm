<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Map zxgd=(Map)VS.findValue("zxgd");
 %>
<html>
<head>
<title>咨询工单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
</head>
<body>
<form name="zxgdForm" action="viewZxgd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" id="tables">
	<thead>
	<tr>
		<td colspan="4">咨询工单信息</td>
	</tr>
	</thead>
	<tr>
	   <td class="a1" width="15%">编号</td>
	   <td class="a2" width="35%"><%=StringUtils.nullToStr(zxgd.get("id")) %></td>
	   <td class="a1" width="15%">回复日期</td>
	   <td class="a2" width="35%"><%=StringUtils.nullToStr(zxgd.get("hf_date")) %></td> 
	</tr> 	
	<tr>
		<td class="a1" width="15%">回复人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById((String)zxgd.get("hfr")) %></td>
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(zxgd.get("state")) %></td>
	</tr>
	<tr>
	  <td class="a1" width="15%">回复内容</td>
	  <td class="a2" width="85%" colspan="3"><%=StringUtils.nullToStr(zxgd.get("content")) %></td> 
	</tr>
	<tr>
	  <td class="a1" width="15%">客户意见</td>
	  <td class="a2" width="85%" colspan="3"><%=StringUtils.nullToStr(zxgd.get("content")) %></td> 
	</tr> 
</table> 
 <br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">售服信息</td>
	</tr>
	</thead>	
	<tr>
		<td class="a1" width="15%">售服单编号</td>
		<td class="a2"><%=StringUtils.nullToStr(zxgd.get("sfd_id")) %></td>	
		 	
		<td class="a1">接待日期</td>
		<td class="a2"><%=StringUtils.nullToStr(zxgd.get("jx_date")) %></td>						
	</tr>
	<tr>			
		<td class="a1" width="15%">往来单位</td>
		<td class="a2"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(zxgd.get("client_name")))%></td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(zxgd.get("jxr"))) %></td>
	</tr>
	<tr>
	   <td class="a1" width="15%">联系人</td>
		<td class="a2"><%=StringUtils.nullToStr(zxgd.get("linkman")) %></td>
		<td class="a1" width="15%">电话</td>
		<td class="a2"><%=StringUtils.nullToStr(zxgd.get("mobile")) %></td>
			
	</tr>
	<tr>
	   <td class="a1" width="15%">地址</td>
		<td class="a2"  colspan="3"><%=StringUtils.nullToStr(zxgd.get("address")) %></td>
	</tr>
	<tr>
	 <td class="a1" width="15%">内容描述</td>
	 <td class="a2" width="85%" colspan="3"><%=StringUtils.nullToStr(zxgd.get("ms")) %></td> 
	</tr>			
</table> 
 <br> 
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="reset" name="button2" value="关 闭" class="css_button2" onclick="window.opener.document.myform.submit();window.close();">
		</td>
	</tr>
</table>
<BR>
</form>
</BODY>
</HTML>