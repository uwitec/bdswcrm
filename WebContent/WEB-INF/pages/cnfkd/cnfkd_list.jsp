<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<%@taglib uri="/WEB-INF/crm-taglib.tld" prefix="crm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>出纳付款单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	function edit(id){
		var destination = "editCnfkd.html?id=" + id;
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'出纳付款单',fea);		
	}
	
	function view(id){
		var destination = "viewCnfkd.html?id=" + id;
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		window.open(destination,'出纳付款单',fea);		
	}	
	
	function clearAll(){
		document.myform.start_date.value = "";
		document.myform.end_date.value = "";
		document.myform.state.value = "";
		document.myform.client_name.value = "";
	}

	function del(id){
		if(window.confirm("确认要退回吗？")){
			//location.href = "delCnfkd.html?id=" + id;
			document.myform.action = "delCnfkd.html?id=" + id;
			document.myform.submit();
		}
	}

	function refreshPage(){
		document.myform.action = "listCnfkd.html";
		document.myform.submit();
	}	

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
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listCnfkd.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="80%">&nbsp;&nbsp;&nbsp;&nbsp;<b>出纳付款单</b></td>	
		<td class="csstitle" width="20%" align="center">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>
		</td>				
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			创建时间：<input type="text" name="start_date" value="<ww:property value="%{start_date}"/>" size="15"  class="Wdate" onFocus="WdatePicker()">&nbsp;至&nbsp;
			<input type="text" name="end_date" value="<ww:property value="%{end_date}"/>" size="15"  class="Wdate" onFocus="WdatePicker()">
			&nbsp;
			客户名称：<ww:textfield name="client_name" id="client_name" value="%{client_name}" theme="simple"/>
			状态：<ww:select name="state" id="state" theme="simple" list="#{'待支付':'待支付','已支付':'已支付'}"  emptyOption="true" ></ww:select>
			&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">			
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  border="1"   class="chart_list" cellpadding="0" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td width="10%">编号</td>
		<td width="15%">单位名称</td>
		<td width="8%">付款类型</td>	
		<td width="9%">付款金额</td>
		<td width="13%">付款账户</td>
		<td width="8%">申请人</td>
		<td width="8%">出纳</td>
		<td width="8%">付款日期</td>
		<td width="8%">状态</td>
		<td width="12%">操作</td>
	</tr>
	</thead>
	<ww:iterator value="%{cnfkdPage.results}">
		<tr class="a1" onmousedown="trSelectChangeCss()" onDblClick="view('<ww:property value="%{id}" />');";>
			<td><ww:property value="%{id}" /></td>
			<td align="left"><ww:property value="%{getClientName(client_name)}" /></td>
			<td><ww:property value="%{fklx}" /></td>
			<td align="right"><ww:property value="%{getText('global.format.money',{fkje})}" />&nbsp;</td>
			<td><ww:property value="%{getAccountName(fkzh)}" /></td>
			<td><ww:property value="%{getUserRealName(fksqr)}" /></td>
			<td><ww:property value="%{getUserRealName(jsr)}" /></td>			
			<td><ww:property value="%{fk_date}" /></td>
			<td><ww:property value="%{state}" /></td>	
			<td nowrap="nowrap">
				<ww:if test="%{state=='待支付'}">
				<a href="#" onclick="edit('<ww:property value="%{id}" />');">付款</a>&nbsp;&nbsp;
				<a href="#" onclick="del('<ww:property value="%{id}" />');">退回</a>&nbsp;&nbsp;
				</ww:if>			
				<a href="#" onclick="view('<ww:property value="%{id}" />');">查看</a>		
			</td>
		</tr>
	</ww:iterator>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page">
		<crm:page value="%{cnfkdPage}" formname="myform"/>
		</td>
	</tr>
</table>
</form>
</div>
</body>
</html>