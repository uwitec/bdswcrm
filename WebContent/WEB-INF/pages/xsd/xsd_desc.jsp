<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List results = (List)VS.findValue("xsdProducts");

%>

<html>
<head>
<title>销售单明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}		
</script>
</head>
<body oncontextmenu="return false;">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">销售单产品明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td width="35%">产品名称</td>
		<td width="35%">规格</td>
		<td width="10%">价格</td>
		<td width="10%">数量</td>
		<td width="10%">小计</td>
	</tr>
	</thead>
	<%
	Iterator it = results.iterator();
	
	while(it.hasNext()){
		XsdProduct xsdProduct = (XsdProduct)it.next();
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()">
		<td align="left"><%=StringUtils.nullToStr(xsdProduct.getProduct_name()) %></td>
		<td align="left"><%=StringUtils.nullToStr(xsdProduct.getProduct_xh()) %></td>
		<td align="right"><%=JMath.round(xsdProduct.getPrice(),2) %>&nbsp;</td>
		<td><%=StringUtils.nullToStr(xsdProduct.getNums()) %></td>
		<td align="right"><%=JMath.round(xsdProduct.getXj(),2) %>&nbsp;</td>
	</tr>
	
	<%
	}
	%>
</table>
</body>
</html>
