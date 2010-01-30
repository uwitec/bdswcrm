<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<html>
<head>
<title>选择</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function sel(clientName,bankNo){
		var objClientName = window.opener.document.getElementById("client_all_name");
		var objBankNo = window.opener.document.getElementById("bank_no");
		
		if(objClientName != null) objClientName.value = clientName;
		if(objBankNo != null) objBankNo.value = bankNo;
		
		window.close();	
	}
</script>
</head>
<body>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td width="5%" nowrap="nowrap">序号</td>
		<td width="50%">单位全称</td>
		<td width="45%">开户行账号</td>
	</tr>
	</thead>
	<ww:iterator value="%{clientsPayInfos}" status="li">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" onclick="sel('<ww:property value="%{client_all_name}" />','<ww:property value="%{bank_no}" />');">
			<td><ww:property value="%{#li.count}" /></td>
			<td align="left"><ww:property value="%{client_all_name}" /></td>
			<td align="left"><ww:property value="%{bank_no}" /></td>
		</tr>
	</ww:iterator>
</table>
</body>
</html>