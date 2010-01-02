<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>商品拆卸单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">商品拆卸单</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">单据编号</td>
		<td class="a2" width="35%">
			<ww:textfield name="cxd.id" id="id" value="%{cxd.id}" theme="simple" readonly="true"/>
		</td>
		<td class="a1" width="15%">单据日期</td>
		<td class="a2" width="35%">
			<ww:textfield name="cxd.cdate" id="cdate" value="%{cxd.cdate}" theme="simple" readonly="true"/><span style="color:red">*</span>
		</td>				
	</tr>
	<tr>
		<td class="a1" width="15%">商品名称</td>
		<td class="a2" width="35%">
			<ww:textfield name="cxd.product_name" id="product_name" value="%{cxd.product_name}" theme="simple" maxLength="100"  size="30" readonly="true"/>
		</td>	
		<td class="a1" width="15%">商品编号</td>
		<td class="a2" width="35%">
			<ww:textfield name="cxd.product_id" id="product_id" value="%{cxd.product_id}" theme="simple"  readonly="true"/>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">商品规格</td>
		<td class="a2" width="35%">
			<ww:textfield name="cxd.product_xh" id="product_xh" value="%{cxd.product_xh}" theme="simple"  size="30" readonly="true"/>
		</td>
		<td class="a1" width="15%">单位</td>
		<td class="a2" width="35%">
			<ww:textfield name="cxd.product_dw" id="product_dw" value="%{cxd.product_dw}" theme="simple" readonly="true"/>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">单价</td>
		<td class="a2" width="35%">
			<ww:textfield id="price" name="cxd.price" value="%{getText('global.format.double',{cxd.price})}" theme="simple" readonly="true"/>
		</td>
		<td class="a1" width="15%">数量</td>
		<td class="a2">
			<ww:textfield id="nums"  name="cxd.nums" value="%{cxd.nums}" theme="simple" readonly="true"/>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%">
			<ww:textfield id="hjje" name="cxd.hjje" value="%{getText('global.format.double',{cxd.hjje})}" theme="simple" readonly="true"/>
		</td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2">
			<ww:textfield name="brand" id="brand" onblur="setValue()" value="%{getUserRealName(cxd.jsr)}" theme="simple"></ww:textfield>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">所在库房</td>
		<td class="a2">
			<ww:select name="cxd.store_id" id="store_id" theme="simple" list="%{storeList}" listValue="name" listKey="id" emptyOption="true" disabled="true"></ww:select><span style="color:red">*</span>
		</td>	
		<td class="a1" width="15%">状态</td>
		<td class="a2">
			<ww:select name="cxd.state" id="state" theme="simple" list="#{'已保存':'已保存','已提交':'已提交'}"  emptyOption="false" disabled="true"></ww:select>
		</td>				
	</tr>
	<tr>
		<td class="a1" width="15%">序列号</td>
		<td class="a2" colspan="3">
			<ww:textfield name="cxd.serial_nums" id="serial_nums" value="%{cxd.serial_nums}" theme="simple" cssStyle="width:55%"  readonly="true"/>
		</td>	
	</tr>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" colspan="3">
			<ww:textfield name="cxd.remark"  id="remark" value="%{cxd.remark}" theme="simple" cssStyle="width:85%" readonly="true"/>
		</td>	
	</tr>
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">拆卸明细</td>
	</tr>
	</thead>
</table>	
<table width="100%"  align="center" id="cxdTable" class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="30%">产品名称</td>
		<td width="15%">规格</td>
		<td width="6%">单位</td>
		<td width="10%">单价</td>
		<td width="6%">数量</td>
		<td width="10%">金额</td>
		<td width="15%">序列号</td>
		<td width="8%">备注</td>
	</tr>
	</thead>
	
	<ww:iterator value="%{cxdProducts}" status="li">
	<tr>
		<td class="a2">
			<ww:textfield name='cxdProducts[%{#li.count-1}].product_name' id='product_name_%{#li.count-1}' value="%{product_name}" theme="simple" cssStyle="width:100%" readonly="true"/>
			<ww:hidden name='cxdProducts[%{#li.count-1}].product_id' id='product_id_%{#li.count-1}' value="%{product_id}" theme="simple"/>
		</td>
		<td class="a2"><ww:textfield name='cxdProducts[%{#li.count-1}].product_xh' id='product_xh_%{#li.count-1}' value="%{product_xh}" theme="simple" cssStyle="width:100%" readonly="true"/></td>
		<td class="a2"><ww:textfield name='cxdProducts[%{#li.count-1}].product_dw' id='product_dw_%{#li.count-1}' value="%{product_dw}" theme="simple" cssStyle="width:100%" readonly="true"/></td>
		<td class="a2"><ww:textfield name='cxdProducts[%{#li.count-1}].price' id='price_%{#li.count-1}' value="%{getText('global.format.double',{price})}" readonly="true" theme="simple" cssStyle="width:100%"/></td>
		<td class="a2"><ww:textfield name='cxdProducts[%{#li.count-1}].nums' id='nums_%{#li.count-1}' value="%{nums}" readonly="true" theme="simple" cssStyle="width:100%"/></td>
		<td class="a2"><ww:textfield name='cxdProducts[%{#li.count-1}].hj' id='hj_%{#li.count-1}' value="%{getText('global.format.double',{hj})}" theme="simple" cssStyle="width:100%" readonly="true"/></td>
		<td class="a2">
			<ww:textfield name='cxdProducts[%{#li.count-1}].qz_serial_num' id='qz_serial_num_%{#li.count-1}' value="%{qz_serial_num}" theme="simple" cssStyle="width:85%" readonly="true"/>
		</td>
		<td class="a2"><ww:textfield name='cxdProducts[%{#li.count-1}].remark' id='remark_%{#li.count-1}' value="%{remark}" theme="simple" cssStyle="width:100%" readonly="true"/></td>
	</tr>	
	</ww:iterator>
</table>
<table width="100%"  align="center" class="chart_info" cellpadding="0" cellspacing="0">		
	<tr height="35">
		<td class="a1" colspan="9">
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>