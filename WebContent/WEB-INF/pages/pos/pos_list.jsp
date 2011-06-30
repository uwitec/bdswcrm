<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>刷卡POS机设定</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function edit(id){
		var destination = "editPos.html?id=" + id;
		var fea ='width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'摊销付款',fea);		
	}	
	function del(id){
		if(window.confirm("确认要删除该行数据吗？")){
			location.href = "delPos.html?id=" + id;
			return;
		}
	}	
	function refreshPage(){
		document.myform.action = "listPos.html";
		document.myform.submit();
	}	
</script>
</head>
<body>
<form name="myform" action="listPos.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="80%">&nbsp;&nbsp;&nbsp;&nbsp;<b>刷卡POS机设定</b></td>	
		<td class="csstitle" width="20%" align="center"><img src="images/create.gif" align="absmiddle" border="0">&nbsp;
			<a href="javascript:void(0);" onclick="edit('');" class="xxlb"> 添 加 </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>
		</td>				
	</tr>	
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td>序号</td>
		<td>名称</td>	
		<td>类型</td>
		<td>每笔费用</td>
		<td>费率</td>
		<td>封顶费用</td>
		<td>相应账户</td>
		<td>操作</td>
	</tr>
	</thead>
	<ww:iterator value="%{posTypeList}" status="li">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';">
			<td><ww:property value="%{#li.count}" /></td>
			<td><ww:property value="%{name}" /></td>
			<td>
				<ww:if test="type==1">固定金额</ww:if>
				<ww:elseif test="type==2">固定扣款率</ww:elseif>
				<ww:elseif test="type==3">固定扣款率(封顶)</ww:elseif>
			</td>
			<td align="right"><ww:property value="%{getText('global.format.money',{mbfy})}" />&nbsp;</td>
			<td align="right"><ww:property value="%{getText('global.format.money1',{fl})}" />&nbsp;</td>
			<td align="right"><ww:property value="%{getText('global.format.money',{fdfy})}" />&nbsp;</td>
			<td><ww:property value="%{getAccountName(account_id)}" /></td>	
			<td>
				<a href="#" onclick="edit('<ww:property value="%{id}" />');"><img src="images/modify.gif" align="absmiddle" title="编辑" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;	
				<a href="#" onclick="del('<ww:property value="%{id}" />');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>		
			</td>
		</tr>
	</ww:iterator>
</table>
</form>
</body>
</html>