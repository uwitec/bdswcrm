<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Cgfpd cgfpd=(Cgfpd)VS.findValue("cgfpd");
List cgfpDescs = (List)VS.findValue("cgfpDescs");
%>

<html>
<head>
<title>采购发票</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">采购发票</td>
	</tr>
	</thead>
	<tr>	
		<td class="a1" width="15%">往来单位</td>
		<td class="a2" width="85%"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(cgfpd.getGysbh())) %></td>			
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">采购发票明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="25%">发生日期</td>
		<td width="25%">进货单编号</td>
		<td width="25%">发生金额</td>
		<td width="25%">状态</td>		
	</tr>
	</thead>
<%

if(cgfpDescs != null && cgfpDescs.size()>0){
	for(int i=0;i<cgfpDescs.size();i++){
	Map map = (Map)cgfpDescs.get(i);	
	double total = map.get("total")==null?0:((Double)map.get("total")).doubleValue();	
%>
	<tr>		
		<td class="a2"><%=StringUtils.nullToStr(map.get("cg_date")) %></td>
		<td class="a2"><%=StringUtils.nullToStr(map.get("jhd_id")) %></td>
		<td class="a2"><%=JMath.round(total) %></td>   
		<td class="a2"><%=StringUtils.nullToStr(map.get("state")) %></td>		
	</tr>
<%		
	}
}
%>	
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
<BR>
</body>
</html>