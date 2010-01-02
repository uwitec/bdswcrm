<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<html>
<head>
<title>选择商品</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	function clearAll(){
		document.myform.product_con.value = "";
	}

	function selPro(product_id,product_name,product_xh,product_dw,price,qz_serial_num){
		window.opener.document.getElementById("product_id").value = product_id;
		window.opener.document.getElementById("product_name").value = product_name;
		window.opener.document.getElementById("product_xh").value = product_xh;
		window.opener.document.getElementById("product_dw").value = product_dw;
		window.opener.document.getElementById("price").value = price;
		window.opener.document.getElementById("qz_flag").value = qz_serial_num;
		window.close();
	}
</script>
</head>
<body>
<form name="myform" action="selCxProduct.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>选择商品</b></td>	
	</tr>
	<tr>
		<td class="search" align="left">&nbsp;&nbsp;
			商品信息：<input type="text" name="product_con" value="<ww:property value="product_con"/>" size="30">
			&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">			
		</td>				
	</tr>		
</table>
<table width="100%" align="center" border="1" class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td width="15%">商品编号</td>
		<td width="35%">商品名称</td>	
		<td width="30%">商品规格</td>
		<td width="10%">单位</td>
		<td width="10%">成本</td>
	</tr>
	</thead>
	<ww:iterator value="%{pageProduct.results}">
		<tr class="a1" onmouseover="this.className='a2';" title="双击鼠标左键选择" onmouseout="this.className='a1';" onDblClick="selPro('<ww:property value="productId"/>','<ww:property value="productName"/>','<ww:property value="productXh"/>','<ww:property value="dw"/>','<ww:property value="%{getText('global.format.double',{price})}"/>','<ww:property value="qz_serial_num"/>');";>
			<td><ww:property value="%{productId}" /></td>
			<td align="left"><ww:property value="%{productName}" /></td>
			<td align="left"><ww:property value="%{productXh}" /></td>
			<td><ww:property value="%{dw}" /></td>
			<td align="right"><ww:property value="%{getText('global.format.money',{price})}" /></td>
		</tr>
	</ww:iterator>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page">
		<crm:page value="%{pageProduct}" formname="myform"/>
		</td>
	</tr>
</table><BR>
注：双击鼠标左键选择
</form>
</body>
</html>