<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<html>
<head>
<title>选择</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function sel(khLxr,khTel){
		var objKhLxr = window.opener.document.getElementById("kh_lxr");
		var objKhTel = window.opener.document.getElementById("tel");
		
		if(objKhLxr != null) objKhLxr.value = khLxr;
		if(objKhTel != null) objKhTel.value = khTel;
		
		window.close();	
	}
</script>
</head>
<body>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td width="5%" nowrap="nowrap">序号</td>
		<td width="35%">姓名</td>
		<td width="30%">联系电话</td>
		<td width="30%">手机</td>
	</tr>
	</thead>
	<ww:iterator value="%{descClientLinkman}" status="li">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" onclick="sel('<ww:property value="%{name}" />','<ww:property value="%{gzdh}" />/<ww:property value="%{yddh}" />');">
			<td><ww:property value="%{#li.count}" /></td>
			<td><ww:property value="%{name}" /></td>
			<td><ww:property value="%{gzdh}" /></td>
			<td><ww:property value="%{yddh}" /></td>
		</tr>
	</ww:iterator>
</table>
</body>
</html>