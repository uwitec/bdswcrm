<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List results = (List)VS.findValue("rkdProducts");

%>

<html>
<head>
<title>入库单明细</title>
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
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">入库单产品明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td width="15%">产品名称</td>
		<td width="15%">规格</td>
		<td width="10%">价格</td>
		<td width="10%">数量</td>
		<td width="35%">序列号</td>
		<td width="15%">备注</td>
	</tr>
	</thead>
	<%
	Iterator it = results.iterator();
	
	while(it.hasNext()){
		Map rkdProduct = (Map)it.next();
		
		double price = rkdProduct.get("price")==null?0:((Double)rkdProduct.get("price")).doubleValue();
	%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(rkdProduct.get("product_name")) %></td>
		<td class="a2"><%=StringUtils.nullToStr(rkdProduct.get("product_xh")) %></td>
		<td class="a2"><%=JMath.round(price,2) %></td>
		<td class="a2"><%=StringUtils.nullToStr(rkdProduct.get("nums")) %></td>
		<td class="a2"><%=StringUtils.nullToStr(rkdProduct.get("qz_serial_num")) %></td>
		<td class="a2"><%=StringUtils.nullToStr(rkdProduct.get("remark")) %></td>
	</tr>
	
	<%
	}
	%>
</table>
</body>
</html>
