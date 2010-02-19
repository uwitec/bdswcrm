<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<html>
<head>
<title>组装单明细</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
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
		<td colspan="2">组装单明细</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td width="8%">商品编号</td>
		<td width="20%">商品名称</td>
		<td width="20%">商品规格</td>
		<td width="8%">单位</td>
		<td width="8%">单价</td>
		<td width="8%">数量</td>
		<td width="8%">金额</td>
		<td width="10%">序列号</td>
		<td width="10%">备注</td>
	</tr>
	</thead>
	<ww:iterator value="%{zzdProducts}" status="li">
		<tr class="a1" onmousedown="trSelectChangeCss()">
			<td><ww:property value="%{product_id}" /></td>
			<td align="left"><ww:property value="%{product_name}" /></td>
			<td align="left"><ww:property value="%{product_xh}" /></td>
			<td><ww:property value="%{product_dw}" /></td>
			<td align="right"><ww:property value="%{getText('global.format.money',{price})}" /></td>
			<td><ww:property value="%{nums}" /></td>
			<td align="right"><ww:property value="%{getText('global.format.money',{hj})}" /></td>
			<td align="left"><ww:property value="%{qz_serial_num}" /></td>
			<td align="left"><ww:property value="%{remark}" /></td>
		</tr>
	</ww:iterator>
</table>
</body>
</html>