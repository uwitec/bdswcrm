<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>出纳付款单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">出纳付款单</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%"><ww:property value="%{cnfkd.id}"/></td>
		<td class="a1" width="15%">日期</td>
		<td class="a2" width="35%"><ww:property value="%{cnfkd.fk_date}"/></td>				
	</tr>
	<tr>
		<td class="a1">往来单位</td>
		<td class="a2"><ww:property value="%{getClientName(cnfkd.client_name)}"/></td>
		<td class="a1">联系人</td>
		<td class="a2"><ww:property value="%{cnfkd.lxr}"/></td>							
	</tr>
	<tr>
		<td class="a1">电话</td>
		<td class="a2"><ww:property value="%{cnfkd.tel}"/></td>
		<td class="a1">传真</td>
		<td class="a2"><ww:property value="%{cnfkd.fax}"/></td>							
	</tr>	
	<tr>
		<td class="a1">单位全称</td>
		<td class="a2"><ww:property value="%{cnfkd.client_all_name}"/></td>
		<td class="a1">开户行及账号</td>
		<td class="a2"><ww:property value="%{cnfkd.bank_no}"/></td>
	</tr>
	<tr>
		<td class="a1">付款类型</td>
		<td class="a2" colspan="3"><ww:property value="%{cnfkd.fklx}"/></td>			
	</tr>
</table>
<BR>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">付款信息</td>
	</tr>
	</thead>	
	<tr>
		<td class="a1" width="15%">付款方式</td>
		<td class="a2" width="35%"><ww:property value="%{cnfkd.fkfs}"/></td>		
		<td class="a1" width="15%">付款金额</td>
		<td class="a2" width="35%"><ww:property value="%{getText('global.format.money',{cnfkd.fkje})}"/></td>				
	</tr>
	<tr>
		<td class="a1">付款账户</td>
		<td class="a2"><ww:property value="%{getAccountName(cnfkd.fkzh)}"/></td>				
		<td class="a1">出纳</td>
		<td class="a2"><ww:property value="%{getUserRealName(cnfkd.jsr)}"/></td>
	</tr>				
	<tr>
		<td class="a1">备注</td>
		<td class="a2" colspan="3"><ww:property value="%{cnfkd.remark}"/></td>	
	</tr>			
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>