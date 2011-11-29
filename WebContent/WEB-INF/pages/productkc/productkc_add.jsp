<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

int kc_nums = (Integer)VS.findValue("kc_nums");
String store_id = (String)VS.findValue("store_id");
Product product = (Product)VS.findValue("product");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>库存管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.productKcForm.kc_nums,1,"int",1,0,9999999,"库存数量")){	 
			document.productKcForm.kc_nums.focus();
			return; 
		}
		
		document.productKcForm.submit();
		//this.opener.document.myform.submit();
        //close();  
	}
</script>
</head>
<body >
<form name="productKcForm" action="saveProductKc.html" method="post">
<input type="hidden" name="product_id" value="<%=StringUtils.nullToStr(product.getProductId()) %>">
<input type="hidden" name="store_id" value="<%=store_id %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="2"><b>库存初始</b></td>
	</tr>
	</thead>
	<tr>
		<td class="a1">商品编号</td>
		<td class="a2"><%=StringUtils.nullToStr(product.getProductId()) %></td>	
	</tr>
	<tr>
		<td class="a1">商品名称</td>
		<td class="a2"><%=StringUtils.nullToStr(product.getProductName()) %></td>	
	</tr>
	<tr>
		<td class="a1">商品规格</td>
		<td class="a2"><%=StringUtils.nullToStr(product.getProductXh()) %></td>	
	</tr>
	<tr>
		<td class="a1">成本价</td>
		<td class="a2"><%=JMath.round(product.getPrice(),2) %></td>	
	</tr>		
	<tr>
		<td class="a1">库房名称</td>
		<td class="a2"><%=StaticParamDo.getStoreNameById(store_id) %></td>
	</tr>
	<tr>
		<td class="a1">账面库存</td>
		<td class="a2"><%=kc_nums %></td>	
	</tr>	
	<tr>
		<td class="a1">实际库存</td>
		<td class="a2"><input type="text" name="kc_nums" value="0" size="10"></td>	
	</tr>	
	
	<tr height="35">
		<td class="a1" colspan="3">
			<input type="button" name="button1" value="保存" class="css_button2" onclick="saveInfo();">
			<input type="button" name="button1" value="关闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
说明：在“实际库存”中输入实际库存数量后，点击“保存”按钮，系统将用您输入的实际库存数量去计算初始化数量。
</body>
</html>
