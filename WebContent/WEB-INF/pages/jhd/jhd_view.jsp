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
		<td class="a1" width="15%">地址</td>
		<td class="a2"><%=StringUtils.nullToStr(jhd.getKh_address()) %></td>	
	</tr>
	
	<tr>
		<td class="a1" width="15%">客户联系人</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(jhd.getKh_lxr()) %></td>	
		<td class="a1" width="15%">联系电话</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(jhd.getKh_lxdh()) %></td>
	</tr>
	<tr>		
	    <td class="a1" width="15%">账期</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(jhd.getZq()) %>天</td>	
		<td class="a1"  width="15%">发票类型</td>
	    <td class="a2" width="35%"><%=StringUtils.nullToStr(jhd.getYsws()) %></td>	
	</tr>
	<tr>		
		<td class="a1" width="15%">进货单状态</td>
		<td class="a2"><%=StringUtils.nullToStr(jhd.getState()) %></td>	
		<td class="a1" width="15%">预计到货时间</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(jhd.getYjdhsj()) %></td>	
	</tr>
	<tr>		
		<td class="a1" width="15%">到货库房</td>
		<td class="a2" width="35%"><%=StaticParamDo.getStoreNameById(jhd.getStore_id()) %></td>
		<td class="a1" width="15%">采购负责人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(jhd.getFzr())) %>	</td>    
	</tr>	
	<tr>
		<td class="a1">合计金额</td>
		<td class="a2" colspan="3"><%=JMath.round(jhd.getTotal(),2) %></td>	
	</tr>	
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">商品详细信息</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center" id="jhtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="35%">商品名称</td>
		<td width="30%">规格</td>
		<td width="10%">进货价格</td>
		<td width="10%">数量</td>
		<td width="15%">小计</td>	
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
		<td class="a2" align="right"><%=JMath.round(jhdProduct.getPrice(),2) %></td>
		<td class="a2"><%=StringUtils.nullToStr(jhdProduct.getNums()) %></td>
		<td class="a2" align="right"><%=JMath.round(jhdProduct.getPrice() * jhdProduct.getNums(),2) %></td>
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">	
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" width="85%"><%=StringUtils.nullToStr(jhd.getMs()) %></td>
	</tr>		
	<tr height="35">
		<td class="a1" colspan="6">
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
