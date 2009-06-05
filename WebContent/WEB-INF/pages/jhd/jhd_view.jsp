<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.model.*" %>


<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
List providers = (List)VS.findValue("providers");
List userList = (List)VS.findValue("userList");

List jhdProducts = (List)VS.findValue("jhdProducts");
Jhd jhd = (Jhd)VS.findValue("Jhd"); 
%>

<html>
<head>
<title>采购订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">采购订单信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(jhd.getId()) %></td>	
		<td class="a1" width="15%">采购日期</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(jhd.getCg_date()) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">供货单位</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(jhd.getGysmc()) %></td>	
		<td class="a1" width="15%">采购负责人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(jhd.getFzr())) %>	</td>
	</tr>
	<tr>
		<td class="a1" width="15%">付款方式</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(jhd.getFkfs()) %></td>	
		<td class="a1" width="15%">进货单状态</td>
		<td class="a2"><%=StringUtils.nullToStr(jhd.getState()) %></td>	
	</tr>
	<tr>
		<td class="a1">合计金额</td>
		<td class="a2"><%=JMath.round(jhd.getTotal(),2) %></td>	
		<td class="a1">付款金额</td>
		<td class="a2"><%=JMath.round(jhd.getFkje(),2) %></td>		
	</tr>	
	<tr>
		<td class="a1">付款类型</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(jhd.getFklx()) %></td>
	</tr>		
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">产品详细信息</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="jhtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>规格</td>
		<td>进货价格</td>
		<td>数量</td>
		<td>小计</td>
		<td>强制序列号</td>		
		<td>备注</td>
	</tr>
	</thead>
<%
if(jhdProducts != null && jhdProducts.size()>0){
	for(int i=0;i<jhdProducts.size();i++){
		JhdProduct jhdProduct = (JhdProduct)jhdProducts.get(i);
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(jhdProduct.getProduct_name()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(jhdProduct.getProduct_xh()) %></td>
		<td class="a2"><%=JMath.round(jhdProduct.getPrice(),2) %></td>
		<td class="a2"><%=StringUtils.nullToStr(jhdProduct.getNums()) %></td>
		<td class="a2"><%=JMath.round(jhdProduct.getPrice() * jhdProduct.getNums(),2) %></td>
		<td class="a2"><%=StringUtils.nullToStr(jhdProduct.getQz_serial_num()) %></td>		
		<td class="a2"><%=StringUtils.nullToStr(jhdProduct.getRemark()) %></td>	
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">	
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%">
			<textarea rows="3" name="jhd.ms" id="ms" style="width:75%" readonly><%=StringUtils.nullToStr(jhd.getMs()) %></textarea>
		</td>
	</tr>		
	<tr height="35">
		<td class="a1" colspan="6">
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
