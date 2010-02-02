<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<html>
<head>
<title>待办工作</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	function doTask(url,id){
		var destination = url + id;
		var fea ='width=850,height=700,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'undo_work',fea);	
	}

	function refreshPage(){
		document.myform.action = "undoWork.html";
		document.myform.submit();
	}	
</script>
</head>
<body>
<form name="myform" action="undoWork.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="80%">&nbsp;&nbsp;&nbsp;&nbsp;<b>待办工作</b></td>	
		<td class="csstitle" width="20%" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>
		</td>				
	</tr>	
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td width="15%">编号</td>
		<td width="15%">工作类型</td>	
		<td width="50%">工作内容</td>
		<td width="10%">日期</td>
		<td width="10%">操作</td>
	</tr>
	</thead>
	<ww:iterator value="%{undoWorkPage.results}">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" onDblClick="javascript:doTask('<ww:property value="%{url}" />','<ww:property value="%{id}" />');" title="双击鼠标左键办理业务">
			<td><ww:property value="%{id}" /></td>
			<td><ww:property value="%{yw_type}" /></td>
			<td align="left">&nbsp;<ww:property value="%{yw_content}" /></td>
			<td><ww:property value="%{cdate}" /></td>
			<td>
				<a href="javascript:doTask('<ww:property value="%{url}" />','<ww:property value="%{id}" />');">办理</a>
			</td>
		</tr>
	</ww:iterator>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page">
		<crm:page value="%{undoWorkPage}" formname="myform"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>