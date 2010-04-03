<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List results = (List)VS.findValue("bfdProducts");

%>

<html>
<head>
<title>报废单明细</title>
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
		<td colspan="2">报废单商品明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>	
	    <td width="20%">商品名称</td>	
		<td width="15%">规格</td>
		<td width="3%">数量</td>			
		<td width="15%">序列号</td>		
		<td width="15%">备注</td>
	</tr>
	</thead>
	 
	<%
	Iterator it = results.iterator();
	
	while(it.hasNext())
	{
		BfdProduct bfdProduct = (BfdProduct)it.next();
	%> 
	<tr class="a1" onmousedown="trSelectChangeCss()">
	    <td align="left">&nbsp;<%=StringUtils.nullToStr(bfdProduct.getProduct_name()) %></td>
		<td align="left">&nbsp;<%=StringUtils.nullToStr(bfdProduct.getProduct_xh()) %></td>	
		<td align="right">&nbsp;<%=StringUtils.nullToStr(bfdProduct.getNums()) %></td>	
		<td align="left">&nbsp;<%=StringUtils.nullToStr(bfdProduct.getQz_serial_num()) %></td>			
		<td align="left">&nbsp;<%=StringUtils.nullToStr(bfdProduct.getProduct_remark()) %></td>	
	</tr>
	<%
	}
	%>
</table>
</body>
</html>
