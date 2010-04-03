<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");
request.removeAttribute("webwork.valueStack");

Object obj = (Object)VS.findValue("product");
Product product = new Product();
if(obj != null){
	product = (Product)obj;
}
%>

<html>
<head>
<title>商品信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">商品信息</td>
	</tr>
	</thead>	
	<tr>
		<td class="a1" width="15%">商品名称</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(product.getProductName()) %></td>
		<td class="a1" width="15%">规格</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(product.getProductXh()) %></td>
	</tr>
	
	<tr>
		<td class="a1" width="15%">计量单位</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(product.getDw())%></td>
		<td class="a1" width="15%">属性</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(product.getProp())%></td>
	</tr>
	<tr>
		<td class="a1" width="15%">移动加权价</td>
		<td class="a2" width="35%"><%=JMath.round(product.getPrice(),2) %></td>
		<td class="a1" width="15%">考核成本价</td>
		<td class="a2" width="35%"><%=JMath.round(product.getKhcbj(),2) %></td>		

	</tr>	
	<tr>	
		<td class="a1" width="15%">零售报价</td>
		<td class="a2" width="35%"><%=JMath.round(product.getLsbj(),2) %></td>	
		<td class="a1" width="15%">分销报价</td>
		<td class="a2" width="35%"><%=JMath.round(product.getFxbj(),2) %></td>					
	</tr>
	<tr>
		<td class="a1" width="15%">零售限价</td>
		<td class="a2" width="35%"><%=JMath.round(product.getLsxj(),2) %></td>	
		<td class="a1" width="15%">分销限价</td>
		<td class="a2" width="35%"><%=JMath.round(product.getFxxj(),2) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">比例点杀</td>
		<td class="a2" width="35%"><%=JMath.round(product.getGf()) %>%</td>
		<td class="a1" width="15%">金额点杀</td>
		<td class="a2" width="35%"><%=JMath.round(product.getDss(),2) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">预估成本价</td>
		<td class="a2" width="35%"><%=JMath.round(product.getYgcbj(),2) %></td>	
		<td class="a1" width="15%">库存下限</td>
		<td class="a2" width="35%"><%=product.getKcxx() %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">状态</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(product.getState()) %></td>
		<td class="a1" width="15%">强制序列号</td>
		<td class="a2" width="35%"><%=StringUtils.nullToStr(product.getQz_serial_num()) %></td>
	</tr>
	<tr>
		<td class="a1" width="15%">商品条形码</td>
		<td class="a2" colspan="3"><%=StringUtils.nullToStr(product.getSp_txm()) %></td>
	</tr>
	<tr height="50">
		<td class="a1">商品描述</td>
		<td class="a2" colspan="3">
			<textarea rows="3" cols="50" name="ms" style="width:80%" readonly><%=StringUtils.nullToStr(product.getMs()) %></textarea>
		</td>
	</tr>
	
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</body>
</html>
