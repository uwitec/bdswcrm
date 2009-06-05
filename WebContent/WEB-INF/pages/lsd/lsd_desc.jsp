<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List results = (List)VS.findValue("lsdProducts");

%>

<html>
<head>
<title>零售单明细</title>
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
		<td colspan="2">零售单产品明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td width="20%">产品名称</td>
		<td width="15%">规格</td>
		<td width="10%">价格</td>
		<td width="10%">数量</td>
		<td width="10%">小计</td>
		<td width="25%">序列号</td>
		<td width="10%">备注</td>
	</tr>
	</thead>
	<%
	Iterator it = results.iterator();
	
	while(it.hasNext()){
		LsdProduct lsdProduct = (LsdProduct)it.next();
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()">
		<td><%=StringUtils.nullToStr(lsdProduct.getProduct_name()) %></td>
		<td><%=StringUtils.nullToStr(lsdProduct.getProduct_xh()) %></td>
		<td><%=JMath.round(lsdProduct.getPrice(),2) %></td>
		<td><%=StringUtils.nullToStr(lsdProduct.getNums()) %></td>
		<td><%=JMath.round(lsdProduct.getXj(),2) %></td>
		<td><%=StringUtils.nullToStr(lsdProduct.getQz_serial_num()) %></td>
		<td><%=StringUtils.nullToStr(lsdProduct.getRemark()) %></td>
	</tr>
	
	<%
	}
	%>
</table>
</body>
</html>
