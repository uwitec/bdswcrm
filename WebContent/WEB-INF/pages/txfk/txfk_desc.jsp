<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>付摊销付款</title>
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
		<td colspan="2">摊销计划</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td width="15%">序号</td>
		<td width="15%">编号</td>
		<td width="20%">摊销日期</td>
		<td width="20%">摊销金额</td>
		<td width="30%">备注</td>
	</tr>
	</thead>
	<ww:iterator value="%{txfkDescs}" status="li">
		<tr class="a1" onmousedown="trSelectChangeCss()">
			<td><ww:property value="#li.count" /></td>
			<td><ww:property value="%{id}" /></td>
			<td><ww:property value="%{txrq}" /></td>
			<td><ww:property value="%{getText('global.format.money',{je})}" /></td>
			<td><ww:property value="%{remark}" /></td>
		</tr>
	</ww:iterator>
</table>
</body>
</html>