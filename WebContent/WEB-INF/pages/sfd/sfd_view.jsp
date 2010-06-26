<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
Sfd sfd = (Sfd)VS.findValue("sfd");
LoginInfo info = (LoginInfo)session.getAttribute("LOGINUSER");
String user_id = info.getUser_id();
 
%>

<html>
<head>
<title>售后服务单查看</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<form name="sfdForm" action="saveSfd.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">售后服务单</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(sfd.getId())%></td>
		<td class="a1" width="15%">商品序列号</td>
		<td class="a2" width="35%"> <%=StringUtils.nullToStr(sfd.getQz_serial_num())%></td>	
	</tr>
	<tr>			
		<td class="a1" width="15%">客户名称</td>		
		<td class="a2" width="35%"><%=StringUtils.nullToStr(StaticParamDo.getClientNameById(sfd.getClient_name())) %> </td>  
		
		<td class="a1" width="15%">联系人</td>
		<td class="a2" width="35%"> <%=StringUtils.nullToStr(sfd.getLinkman())%></td>   		              		
	</tr>
	<tr>			
		<td class="a1" width="15%">联系电话</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(sfd.getMobile())%></td>	
				
		<td class="a1" width="15%">地址</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(sfd.getAddress())%></td>     
		        		
	</tr>
	<tr>
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%"> <%=StringUtils.nullToStr(StaticParamDo.getRealNameById(sfd.getJxr()))%> </td>	  
		            	    		
	    <td class="a1" width="15%">客户报修方式</td>
		<td class="a2" width="35%"> <%=StringUtils.nullToStr(sfd.getQzfs()) %> </td>
									
	</tr>
	<tr>
		<td class="a1">状态</td>
		<td class="a2"><%=StringUtils.nullToStr(sfd.getState())%></td> 	
		<td class="a1">维修状态</td>
		<td class="a2"> <%=StringUtils.nullToStr(sfd.getWx_state())%></td>	
	</tr>
	<tr>
	    <td class="a1" width="15%">接待日期</td>
		<td class="a2" width="35%"> <%=StringUtils.nullToStr(sfd.getJx_date())%></td>	
		<td class="a1" width="15%">结单日期</td>
		<td class="a2" width="35%"> <%=StringUtils.nullToStr(sfd.getJd_date())%> </td>			 				
	</tr>
	 
	<tr>
		<td class="a1" width="15%">商品信息</td>
		<td class="a2" colspan="3">
			<textarea rows="4"  style="width:75%" readonly="readonly" ><%=StringUtils.nullToStr(sfd.getMs())%></textarea>
		</td>
	</tr>	
	<tr>
		<td class="a1" width="15%">报修原因</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(sfd.getBxyy()) %></td>	
	</tr>
	<tr>	
		<td class="a1" width="15%">报修原因说明</td>
		<td class="a2" colspan="3"><textarea rows="4"  style="width:75%" readonly="readonly" ><%=StringUtils.nullToStr(sfd.getBxyy_ms())%></textarea></td>
   </tr>			
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>