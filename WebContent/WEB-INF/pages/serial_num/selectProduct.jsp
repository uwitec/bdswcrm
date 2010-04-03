<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<html>
<head>
<title>选择商品</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function sel(product_id,product_name,product_xh){
		var obj_id = window.opener.document.getElementById("product_id");
		var obj_name = window.opener.document.getElementById("product_name");
		var obj_xh = window.opener.document.getElementById("product_xh");
		
		obj_id.value = product_id;
		obj_name.value = product_name;
		obj_xh.value = product_xh;
		
		window.close();
	}
	
	function clearAll(){
		document.myform.product_xh.value = "";
		document.myform.product_name.value = "";
	}
</script>
</head>
<body>
<form name="myform" action="getProducts.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>选择商品</b></td>	
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			商品名称：
			<ww:textfield theme="simple" name="product_name" id="product_name" value="%{product_name}"/> 
			&nbsp;&nbsp;
			商品规格：
			<ww:textfield theme="simple" name="product_xh" id="product_xh" value="%{product_xh}"/> 
			&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">			
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>商品编号</td>
		<td>商品名称</td>	
		<td>商品规格</td>
		<td>单位</td>
	</tr>
	</thead>
	<ww:iterator value="%{pageProduct.results}">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" title="左键双击选择" onDblClick="sel('<ww:property value="%{productId}" />','<ww:property value="%{productName}" />','<ww:property value="%{productXh}" />');">
			<td><ww:property value="%{productId}" /></td>
			<td><ww:property value="%{productName}" /></td>
			<td><ww:property value="%{productXh}" /></td>
			<td><ww:property value="%{dw}" /></td>
		</tr>
	</ww:iterator>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page"><crm:page value="%{pageProduct}" formname="myform"/></td>
	</tr>
</table>
</form>
</body>
</html>