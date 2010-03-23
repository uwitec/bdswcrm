<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("jhdPage");

String client_id = (String)VS.findValue("client_id");
String cg_date = (String)VS.findValue("cg_date");
String cg_date2 = (String)VS.findValue("cg_date2");
String id = (String)VS.findValue("id");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");

%>

<html>
<head>
<title>关联进货单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewJhd.html?id="+id;
		var fea ='width=900,height=700,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function clearAll(){
		document.myform.id.value = "";
		document.myform.cg_date.value = "";
		document.myform.cg_date2.value = "";
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
	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
		}
	}
	
	function descMx(id){
		document.descForm.id.value = id;
		document.descForm.submit();		
	}
</script>
</head>
<body>
<form name="myform" action="selJhd.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<input type="hidden" name="client_id" value="<%=client_id %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;
			采购订单编号：<input type="text" name="id" id="id" value="<%=id %>" maxlength="20">
			采购日期：<input type="text" name="cg_date" value="<%=cg_date %>" size="15"  class="Wdate" onFocus="WdatePicker()">&nbsp;&nbsp;至&nbsp;&nbsp;
			<input type="text" name="cg_date2" value="<%=cg_date2 %>" size="15"  class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('id');">编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('gysbh');">供应商<%if(orderName.equals("gysbh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('state');">状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('fzr');">负责人<%if(orderName.equals("fzr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('total');">金额<%if(orderName.equals("total")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('fklx');">付款类型<%if(orderName.equals("fklx")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('cg_date');">采购时间<%if(orderName.equals("cg_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Jhd jhd = (Jhd)it.next();
	%>
	<tr class="a1" title="双击查看详情"  onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(jhd.getId()) %>');" onDblClick="openWin('<%=StringUtils.nullToStr(jhd.getId()) %>');">
		<td><%=StringUtils.nullToStr(jhd.getId()) %></td>
		<td><%=StringUtils.nullToStr(jhd.getGysmc()) %></td>
		<td><%=StringUtils.nullToStr(jhd.getState()) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(jhd.getFzr())) %></td>
		<td><%=JMath.round(jhd.getTotal(),2) %></td>
		<td><%=StringUtils.nullToStr(jhd.getFklx()) %></td>
		<td><%=StringUtils.nullToStr(jhd.getCg_date()) %></td>
	</tr>
	
	<%
	}
	%>
</table>
<div id="pageDiv">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page"><%=results.getPageScript() %></td>
	</tr>
</table>
</div>
</form>
<form name="descForm" action="descSelJhd.html" method="post" target="desc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="desc" name="desc" width="100%" onload="dyniframesize('desc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>
</body>
</html>
