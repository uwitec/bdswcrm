<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<html>
<head>
<title>付摊销付款</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function edit(id){
		var destination = "editTxfk.html?id=" + id;
		var fea ='width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'摊销付款',fea);		
	}
	
	function view(id){
		var destination = "viewTxfk.html?id=" + id;
		var fea ='width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'序列号管理',fea);		
	}	
	
	function del(id){
		if(window.confirm("确认要删除该行数据吗？")){
			location.href = "delTxfk.html?id=" + id;
			return;
		}
	}
	
	function doSort(order_name){
		if(myform.orderType.value=='asc'){
			myform.orderType.value='desc';
		}else{
			myform.orderType.value='asc';	
		}
		myform.orderName.value = order_name;
	    myform.submit();		
	}	
	
	function clearAll(){
		document.myform.fk_date1.value = "";
		document.myform.fk_date2.value = "";
		document.myform.state.value = "";
	}
	
	function refreshPage(){
		document.myform.action = "listTxfk.html";
		document.myform.submit();
	}
	
	function descMx(id){
		document.descForm.id.value = id;
		document.descForm.submit();			
	}		
</script>
</head>
<body>
<form name="myform" action="listTxfk.html" method="post">
<ww:hidden name="orderName" id="orderName" theme="simple" value="%{orderName}" />
<ww:hidden name="orderType" id="orderType" theme="simple" value="%{orderType}" />
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="80%">&nbsp;&nbsp;&nbsp;&nbsp;<b>付摊销付款</b></td>	
		<td class="csstitle" width="20%" align="center"><img src="images/create.gif" align="absmiddle" border="0">&nbsp;
			<a href="javascript:void(0);" onclick="edit('');" class="xxlb"> 添 加 </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>
		</td>				
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			付款日期：
			<ww:textfield theme="simple" name="fk_date1" id="fk_date1" value="%{fk_date1}" size="10" readonly="true"/> 
			&nbsp;<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.fk_date1); return false;">&nbsp;
			&nbsp;&nbsp;至&nbsp;&nbsp;	
			<ww:textfield theme="simple" name="fk_date2" id="fk_date2" value="%{fk_date1}" size="10" readonly="true"/> 
			&nbsp;<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.fk_date2); return false;">&nbsp;	
			&nbsp;&nbsp;
			状态：
			<ww:select name="state" id="state" theme="simple" list="#{'已保存':'已保存','已提交':'已提交'}"  emptyOption="true" ></ww:select>
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">			
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td onclick="doSort('id');">编号<ww:if test="orderName=='id'"><img src='images/<ww:property value="%{orderType}" />.gif'></ww:if></td>
		<td onclick="doSort('fk_date');">付款日期<ww:if test="orderName=='fk_date'"><img src='images/<ww:property value="%{orderType}" />.gif'></ww:if></td>	
		<td onclick="doSort('client_name');">相关客户<ww:if test="orderName=='client_name'"><img src='images/<ww:property value="%{orderType}" />.gif'></ww:if></td>
		<td onclick="doSort('fklx');">支出类型<ww:if test="orderName=='fklx'"><img src='images/<ww:property value="%{orderType}" />.gif'></ww:if></td>
		<td onclick="doSort('fkje');">支出金额<ww:if test="orderName=='fkje'"><img src='images/<ww:property value="%{orderType}" />.gif'></ww:if></td>
		<td onclick="doSort('account_id');">支出账户<ww:if test="orderName=='account_id'"><img src='images/<ww:property value="%{orderType}" />.gif'></ww:if></td>
		<td onclick="doSort('jsr');">经手人<ww:if test="orderName=='jsr'"><img src='images/<ww:property value="%{orderType}" />.gif'></ww:if></td>
		<td>操作</td>
	</tr>
	</thead>
	<ww:iterator value="%{txfkPage.results}">
		<tr class="a1" onmouseover="this.className='a2';" onmouseout="this.className='a1';" onclick="descMx('<ww:property value="%{id}" />');" onDblClick="view('<ww:property value="%{id}" />');";>
			<td><ww:property value="%{id}" /></td>
			<td><ww:property value="%{fk_date}" /></td>
			<td><ww:property value="%{client_name}" /></td>
			<td><ww:property value="%{fklx}" /></td>
			<td align="right"><ww:text name="global.format.money"><ww:param value="fkje"/></ww:text>&nbsp;</td>
			<td><ww:property value="%{getAccountName(account_id)}" /></td>
			<td><ww:property value="%{getUserRealName(jsr)}" /></td>	
			<td>
				<ww:if test="state=='已保存'">
				<a href="#" onclick="edit('<ww:property value="%{id}" />');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;			
				<a href="#" onclick="view('<ww:property value="%{id}" />');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" onclick="del('<ww:property value="%{id}" />');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>				
				</ww:if>				
				<ww:else>
				<a href="#" onclick="view('<ww:property value="%{id}" />');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>
				</ww:else>
			</td>
		</tr>
	</ww:iterator>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page">
		<crm:page value="%{txfkPage}" formname="myform"/>
		</td>
	</tr>
</table>
</form>

<form name="descForm" action="descTxfk.html" method="post" target="desc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="desc" name="desc" width="100%" onload="dyniframesize('desc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>
</body>
</html>