<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
		<td class="a2" width="35%"><ww:property value="%{cxd.id}"/>
		</td>
		<td class="a1" width="15%">单据日期</td>
		<td class="a2" width="35%"><ww:property value="%{cxd.cdate}"/>
		</td>				
	</tr>
	<tr>
		<td class="a1" width="15%">商品名称</td>
		<td class="a2" width="35%"><ww:property value="%{cxd.product_name}"/>
		</td>	
		<td class="a1" width="15%">商品编号</td>
		<td class="a2" width="35%"><ww:property value="%{cxd.product_id}"/>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">商品规格</td>
		<td class="a2" width="35%"><ww:property value="%{cxd.product_xh}"/>
		</td>
		<td class="a1" width="15%">单位</td>
		<td class="a2" width="35%"><ww:property value="%{cxd.product_dw}"/>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">单价</td>
		<td class="a2" width="35%"><ww:property value="%{getText('global.format.double',{cxd.price})}"/>
		</td>
		<td class="a1" width="15%">数量</td>
		<td class="a2"><ww:property value="%{cxd.nums}"/>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">金额</td>
		<td class="a2" width="35%"><ww:property value="%{getText('global.format.double',{cxd.hjje})}"/>
		</td>
		<td class="a1" width="15%">经手人</td>
		<td class="a2"><ww:property value="%{getUserRealName(cxd.jsr)}"/>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">所在库房</td>
		<td class="a2"><ww:property value="%{getStoreName(cxd.store_id)}"/>
		</td>	
		<td class="a1" width="15%">状态</td>
		<td class="a2"><ww:property value="%{cxd.state}"/>
		</td>				
	</tr>
	<tr>
		<td class="a1" width="15%">序列号</td>
		<td class="a2" colspan="3"><ww:property value="%{cxd.serial_nums}"/>
		</td>	
	</tr>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" colspan="3"><ww:property value="%{cxd.remark}"/>
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
		<td width="30%">商品名称</td>
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
		<td class="a2"><ww:property value="%{product_name}"/></td>
		<td class="a2"><ww:property value="%{product_xh}"/></td>
		<td class="a2"><ww:property value="%{product_dw}"/></td>
		<td class="a2"><ww:property value="%{getText('global.format.double',{price})}"/></td>
		<td class="a2"><ww:property value="%{nums}"/></td>
		<td class="a2"><ww:property value="%{getText('global.format.double',{hj})}"/></td>
		<td class="a2"><ww:property value="%{qz_serial_num}"/></td>
		<td class="a2"><ww:property value="%{remark}"/></td>
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