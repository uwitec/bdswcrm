<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("yufuToYingfuPage");

String client_name = StringUtils.nullToStr(VS.findValue("client_name"));
String create_date1 = StringUtils.nullToStr(VS.findValue("create_date1"));
String create_date2 = StringUtils.nullToStr(VS.findValue("create_date2"));
String jsr = StringUtils.nullToStr(VS.findValue("jsr"));

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>预付冲应付</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewYufuToYingfu.html?id="+id;
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'查看预付冲应付信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delYufuToYingfu.html?id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.client_name.value = "";
		document.myform.create_date1.value = "";
		document.myform.create_date2.value = "";
		document.myform.jsr.value = "";
	}
	
	function add(){
		var destination = "addYufuToYingfu.html";
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加预付冲应付信息',fea);	
	}
	
	function edit(id){
		var destination = "editYufuToYingfu.html?id=" + id;
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改预付冲应付信息',fea);		
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
	
	function refreshPage(){
		document.myform.action = "listYufuToYingfu.html";
		document.myform.submit();
	}
</script>
</head>
<body>
<form name="myform" action="listYufuToYingfu.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>预付冲应付</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;
			客户名称：<input type="text" name="client_name" value="<%=client_name %>" size="20">&nbsp;
			日期：<input type="text" name="create_date1" value="<%=create_date1 %>" size="15" class="Wdate" onFocus="WdatePicker()">	
			&nbsp;至&nbsp;
			<input type="text" name="create_date2" value="<%=create_date2 %>" size="15" class="Wdate" onFocus="WdatePicker()">
			&nbsp;
			经手人：<input type="text" name="jsr" value="<%=jsr %>" size="10">&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value="查询" class="css_button"><input type="button" name="buttonQk" value="清空" class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%" align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('a.id');">编号<%if(orderName.equals("a.id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('b.name');">客户名称<%if(orderName.equals("b.name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('a.state');">状态<%if(orderName.equals("a.state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('a.total');">本次结算金额<%if(orderName.equals("a.total")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('a.create_date');">日期<%if(orderName.equals("a.create_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('a.jsr');">经手人<%if(orderName.equals("a.jsr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('a.czr');">操作员<%if(orderName.equals("a.czr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		YufuToYingfu info = (YufuToYingfu)it.next();
	%>
	<tr class="a1" title="双击查看详情" onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(info.getId()) %>');" onDblClick="openWin('<%=StringUtils.nullToStr(info.getId()) %>');">
		<td><%=StringUtils.nullToStr(info.getId()) %></td>
		<td><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(info.getClient_name())) %></td>
		<td><%=StringUtils.nullToStr(info.getState()) %></td>
		<td align="right"><%=JMath.round(info.getTotal(),2) %>&nbsp;&nbsp;</td>
		<td><%=StringUtils.nullToStr(info.getCreate_date()) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(info.getJsr())) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(info.getCzr())) %></td>
		<td>
		<%
		if(!StringUtils.nullToStr(info.getState()).equals("已保存")){
		%>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(info.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>
		<%	
		}else{
		%>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(info.getId()) %>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(info.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(info.getId()) %>');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>		
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

<form name="descForm" action="descYufuToYingfu.html" method="post" target="desc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="desc" name="desc" width="100%" onload="dyniframesize('desc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>

</body>
</html>
