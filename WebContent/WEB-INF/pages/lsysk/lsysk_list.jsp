<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("lsyskPage");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");

String id = (String)VS.findValue("id");
String client_name = (String)VS.findValue("client_name");
String ys_date1 = (String)VS.findValue("ys_date1");
String ys_date2 = (String)VS.findValue("ys_date2");

%>

<html>
<head>
<title>零售预收款</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewLsysk.html?id="+id;
		var fea ='width=750,height=500,left=' + (screen.availWidth-750)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delLsysk.html?id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.id.value = "";
		document.myform.client_name.value = "";
		document.myform.ys_date1.value = "";
		document.myform.ys_date2.value = "";
	}	
	
	function add(){
		var destination = "addLsysk.html";
		var fea ='width=750,height=500,left=' + (screen.availWidth-750)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function edit(id){
		var destination = "editLsysk.html?id=" + id;
		var fea ='width=750,height=500,left=' + (screen.availWidth-750)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}
	
	function print(id){
		var destination = "printLsysk.html?id=" + id;
		var fea ='width=700,height=600,left=' + (screen.availWidth-700)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);				
	}
	
	
	function trSelectChangeCss(){
		if (event.srcElement.tagName=='TD'){
			for(i=0;i<selTable.rows.length;i++){
				selTable.rows[i].className="a1";
			}
			event.srcElement.parentElement.className='a2';
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
	
	function refreshPage(){
		document.myform.action = "listLsysk.html";
		document.myform.submit();
	}		
	
</script>
</head>
<body>
<form name="myform" action="listLsysk.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>零售预收款</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#"  onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#"  onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			编号：<input type="text" name="id" value="<%=id %>">&nbsp;&nbsp;
			客户名称：<input type="text" name="client_name" value="<%=client_name %>">&nbsp;&nbsp;
			预收时间：<input type="text" name="ys_date1" value="<%=ys_date1 %>" size="10" readonly>
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.ys_date1); return false;">
			&nbsp;至&nbsp;
			<input type="text" name="ys_date2" value="<%=ys_date2 %>" size="10" readonly>
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.ys_date2); return false;">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>				
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('id');">编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('client_name');">客户名称<%if(orderName.equals("client_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('jsr');">经手人<%if(orderName.equals("jsr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('ys_date');">预收日期<%if(orderName.equals("ys_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('ysje');">预收金额<%if(orderName.equals("ysje")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('skzh');">收款账号<%if(orderName.equals("skzh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('type');">预收款状态<%if(orderName.equals("type")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('czr');">操作员<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	Iterator its = (results.getResults()).iterator();
	
	while(its.hasNext()){
		Lsysk lsysk = (Lsysk)its.next();
		
	%>
	<tr class="a1"  title="双击查看详情"  onmousedown="trSelectChangeCss()"  onDblClick="openWin('<%=StringUtils.nullToStr(lsysk.getId()) %>');">
		<td><%=StringUtils.nullToStr(lsysk.getId()) %></td>
		<td><%=StringUtils.nullToStr(lsysk.getClient_name()) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(lsysk.getJsr())) %></td>
		<td><%=StringUtils.nullToStr(lsysk.getYs_date()) %></td>
		<td align="right"><%=JMath.round(lsysk.getYsje(),2) %>&nbsp;&nbsp;</td>
		<td><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(lsysk.getSkzh())) %></td>
		<td><%=StringUtils.nullToStr(lsysk.getType()) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(lsysk.getCzr())) %></td>
		<td>
		<%
		if(StringUtils.nullToStr(lsysk.getState()).equals("已提交")){
		%>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(lsysk.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="print('<%=StringUtils.nullToStr(lsysk.getId()) %>');"><img src="images/print.png" align="absmiddle" title="打印收据" border="0" style="cursor:hand"></a>
		<%
		}else{
		%>	
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(lsysk.getId()) %>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(lsysk.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;			
			<a href="#" onclick="del('<%=StringUtils.nullToStr(lsysk.getId()) %>');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;	
			<a href="#" onclick="print('<%=StringUtils.nullToStr(lsysk.getId()) %>');"><img src="images/print.png" align="absmiddle" title="打印收据" border="0" style="cursor:hand"></a>	
		<%
		}
		%>

		</td>
	</tr>
	
	<%
	}
	%>
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="page"><%=results.getPageScript() %></td>
	</tr>
</table>
</form>
</body>
</html>
