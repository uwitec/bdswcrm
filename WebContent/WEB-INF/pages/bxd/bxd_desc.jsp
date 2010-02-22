<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

List results = (List)VS.findValue("bxdProducts");

%>

<html>
<head>
<title>报修单明细</title>
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
		<td colspan="2">报修单产品明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>	
	    <td width="20%">产品名称</td>	
		<td width="15%">规格</td>
		<td width="3%">数量</td>	
		<td width="5%">仓库</td>
		<td width="15%">序列号</td>
		<td width="7%">送修天数</td>
		<td width="20%">产品附件</td>
		<td width="15%">备注</td>
	</tr>
	</thead>
	 
	<%
	Iterator it = results.iterator();
	
	while(it.hasNext())
	{
		BxdProduct bxdProduct = (BxdProduct)it.next();
	%> 
	<tr class="a1" onmousedown="trSelectChangeCss()">
	    <td align="left">&nbsp;<%=StringUtils.nullToStr(bxdProduct.getProduct_name()) %></td>
		<td align="left">&nbsp;<%=StringUtils.nullToStr(bxdProduct.getProduct_xh()) %></td>	
		<td align="right">&nbsp;<%=StringUtils.nullToStr(bxdProduct.getNums()) %></td>		 
		<td align="left">&nbsp;<%="坏件库" %>	</td>
		<td align="left">&nbsp;<%=StringUtils.nullToStr(bxdProduct.getQz_serial_num()) %></td>
		<td align="right">&nbsp;<%=StringUtils.nullToStr(bxdProduct.getSxts()) %></td>
		<td align="left">&nbsp;<%=StringUtils.nullToStr(bxdProduct.getCpfj()) %></td>				
		<td align="left">&nbsp;<%=StringUtils.nullToStr(bxdProduct.getProduct_remark()) %></td>	
	</tr>
	<%
	}
	%>
</table>
</body>
</html>
