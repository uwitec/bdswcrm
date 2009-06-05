<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>付摊销付款</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">付摊销付款</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">编号</td>
		<td class="a2" width="35%">
			<ww:property value="%{txfk.id}"/>
		</td>
		<td class="a1" width="15%">付款日期</td>
		<td class="a2" width="35%">
			<ww:property value="%{txfk.fk_date}"/>
		</td>				
	</tr>
	<tr>
		<td class="a1" width="15%">相关客户</td>
		<td class="a2" width="35%">
			<ww:property value="%{txfk.client_name}"/>
		</td>	
		<td class="a1" width="15%">经手人</td>
		<td class="a2" width="35%">
			<ww:property value="%{getUserRealName(txfk.jsr)}"/>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">支付类型</td>
		<td class="a2" width="35%">
			<ww:property value="%{txfk.fklx}"/>
		</td>
		<td class="a1" width="15%">付款金额</td>
		<td class="a2" width="35%">
			<ww:text name="global.format.money"><ww:param value="txfk.fkje"/></ww:text>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">支付账户</td>
		<td class="a2" width="35%">
			<ww:property value="%{getAccountName(txfk.account_id)}"/>
		</td>
		<td class="a1" width="15%">状态</td>
		<td class="a2">
			<ww:property value="%{txfk.state}"/>
		</td>						
	</tr>
	<tr>
		<td class="a1" width="15%">操作人</td>
		<td class="a2" width="35%">
			<ww:property value="%{getUserRealName(txfk.czr)}"/>
		</td>
		<td class="a1" width="15%">操作时间</td>
		<td class="a2">
			<ww:property value="%{txfk.cz_date}"/>
		</td>						
	</tr>	
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">摊销计划</td>
	</tr>
	</thead>
</table>	
<table width="100%"  align="center" id="txfkTable" class="chart_list" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td width="15%">序号</td>
		<td width="15%">编号</td>
		<td width="15%">摊销日期</td>
		<td width="15%">摊销金额</td>
		<td width="40%">备注</td>
	</tr>
	</thead>
	
	<ww:iterator value="%{txfkDescs}" status="li">
	<tr>
		<td class="a2"><ww:property value="#li.count"/></td>
		<td class="a2"><ww:property value="%{id}"/></td>
		<td class="a2"><ww:property value="%{txrq}"/></td>
		<td class="a2"><ww:property value="%{getText('global.format.money',{je})}"/></td>
		<td class="a2"><ww:property value="%{remark}"/></td>
	</tr>	
	</ww:iterator>
	<tr>
		<td class="a1" width="15%">备注</td>
		<td class="a2" colspan="4">
			<ww:textarea name="txfk.remark" id="remark"  theme="simple" cssStyle="width:70%" readonly="true" />
		</td>
	</tr>				
	<tr height="35">
		<td class="a1" colspan="5">
			<input type="button" name="button1" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</body>
</html>
