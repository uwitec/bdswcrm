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
		<td width="15%">产品名称</td>
		<td width="15%">规格</td>
		<td width="5%">价格</td>
		<td width="5%">价格调整</td>
		<td width="5%">数量</td>
		<td width="5%">小计</td>
		<td width="35%">序列号</td>
		<td width="15%">备注</td>
	</tr>
	</thead>
	<%
	Iterator it = results.iterator();
	
	while(it.hasNext()){
		XsdProduct xsdProduct = (XsdProduct)it.next();
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()">
		<td><%=StringUtils.nullToStr(xsdProduct.getProduct_name()) %></td>
		<td><%=StringUtils.nullToStr(xsdProduct.getProduct_xh()) %></td>
		<td><%=JMath.round(xsdProduct.getPrice(),2) %></td>
		<td><%=JMath.round(xsdProduct.getJgtz(),2) %></td>
		<td><%=StringUtils.nullToStr(xsdProduct.getNums()) %></td>
		<td><%=JMath.round(xsdProduct.getXj(),2) %></td>
		<td><%=StringUtils.nullToStr(xsdProduct.getQz_serial_num()) %></td>
		<td><%=StringUtils.nullToStr(xsdProduct.getRemark()) %></td>
	</tr>
	
	<%
	}
	%>
</table>
</body>
</html>
