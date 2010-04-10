<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="com.sw.cms.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
ClientsLinkman linkman=(ClientsLinkman)VS.findValue("linkman");
 %>
<html>
<head>
<title>联系人视图 </title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form name="myform" action="viewLxr.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">联系人</td>
	</tr>
	</thead>
	<tr>
	   <td class="a1" width="15%">往来单位</td>
	   <td class="a2"  width="35%"><%=StringUtils.nullToStr(linkman.getClients_id()) %></td>
		<td class="a1" width="15%">姓名</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(linkman.getName()) %></td>
	</tr>
	<tr> 		
		<td class="a1" width="15%">称呼</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(linkman.getCh()) %></td>		
	    <td class="a1" width="15%">部门</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(linkman.getDept()) %></td>
	</tr>
	<tr> 		
		<td class="a1" width="15%">职务</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(linkman.getZw())%></td>
		<td class="a1" width="15%">办公电话</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(linkman.getGzdh())%></td>	
	</tr>
	<tr> 		
		<td class="a1" width="15%">移动电话</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(linkman.getYddh())%></td>			
		<td class="a1" width="15%">E-Mail</td>
		<td class="a2"  colspan="3"><%=StringUtils.nullToStr(linkman.getMail())%></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">QQ</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(linkman.getQq())%></td>	
		<td class="a1" width="15%">MSN</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(linkman.getMsn())%></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">性别</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(linkman.getSex())%></td>
		<td class="a1" width="15%">年龄段</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(linkman.getNld())%></td>		
	</tr>
	<tr>
		<td class="a1" width="15%">类型</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(linkman.getLx()) %></td>	
	</tr>		
	
	 
	<tr height="50">
		<td class="a1">备注</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(linkman.getRemark()) %></td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>			
</table>
</form>
</body>
</html>
