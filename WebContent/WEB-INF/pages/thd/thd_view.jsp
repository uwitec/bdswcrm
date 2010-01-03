<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Thd thd = (Thd)VS.findValue("thd");
List thdProducts = (List)VS.findValue("thdProducts");

%>

<html>
<head>
<title>退货单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body >
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">退货单信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">退货单编号</td>
		<td class="a2"><%=StringUtils.nullToStr(thd.getThd_id()) %></td>	
		<td class="a1">退货日期</td>
		<td class="a2"><%=StringUtils.nullToStr(thd.getTh_date()) %></td>	
	</tr>
	<tr>			
		<td class="a1" width="15%">客户名称</td>
		<td class="a2"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(thd.getClient_name())) %></td>	
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(thd.getTh_fzr())) %></td>
	</tr>
	<tr>
		<td class="a1">退款方式</td>
		<td class="a2"><%=StringUtils.nullToStr(thd.getType()) %></td>	
		<td class="a1">状态</td>
		<td class="a2"><%=StringUtils.nullToStr(thd.getState()) %></td>			
	</tr>
	<tr>
		<td class="a1" widht="20%">退款账户</td>
		<td class="a2"><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(thd.getTkzh())) %></td>
		<td class="a1">合计退款金额</td>
		<td class="a2"><%=JMath.round(thd.getThdje(),2) %></td>
	</tr>
	<tr>
		<td class="a1">入库库房</td>
		<td class="a2" colspan="3"><%=StaticParamDo.getStoreNameById(thd.getStore_id()) %></td>			
	</tr>
	<tr>
		<td class="a1" width="15%">退货原因</td>
		<td class="a2"colspan="3"><%=StringUtils.nullToStr(thd.getRemark()) %></td>
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
<table width="100%"  align="center" id="thtable"  class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td>产品名称</td>
		<td>规格</td>
		<td>退货价格</td>
		<td>数量</td>
		<td>小计</td>
		<td>备注</td>
	</tr>
	</thead>
<%
if(thdProducts!=null && thdProducts.size()>0){
	for(int i=0;i<thdProducts.size();i++){
		ThdProduct thdProduct = (ThdProduct)thdProducts.get(i);
%>
	<tr>
		<td class="a2"><%=StringUtils.nullToStr(thdProduct.getProduct_name()) %></td>
		<td class="a2"><%=StringUtils.nullToStr(thdProduct.getProduct_xh()) %></td>
		<td class="a2"><%=JMath.round(thdProduct.getTh_price(),2) %></td>
		<td class="a2"><%=StringUtils.nullToStr(thdProduct.getNums()) %></td>
		<td class="a2"><%=JMath.round(thdProduct.getXj(),2) %></td>
		<td class="a2"><%=StringUtils.nullToStr(thdProduct.getRemark()) %></td>
	</tr>
<%
	}
}
%>	
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button3" value="关闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
