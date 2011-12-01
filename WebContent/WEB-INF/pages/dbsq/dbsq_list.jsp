<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("pageDbsq");

String creatdate1 = (String)VS.findValue("creatdate1");
String creatdate2 = (String)VS.findValue("creatdate2");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>调拨申请</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewDbsq.html?id="+id;
		var fea ='width=850,height=550,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-550)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			//location.href = "delDbsq.html?id=" + id;
			document.myform.action = "delDbsq.html?id=" + id;
			document.myform.submit();
		}
	}
	
	function add(){
		var destination = "addDbsq.html";
		var fea ='width=850,height=550,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-550)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'调拨申请',fea);	
	}	
	
	function clearAll(){
		document.myform.creatdate1.value = "";
		document.myform.creatdate2.value = "";
	}
	
	function edit(id){
		var destination = "editDbsq.html?id=" + id;
		var fea ='width=850,height=550,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-550)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'调拨申请',fea);		
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
		document.myform.action = "listDbsq.html";
		document.myform.submit();
	}			
</script>
</head>
<body >
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listDbsq.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>调拨申请</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			日期：<input type="text" name="creatdate1" value="<%=creatdate1 %>" class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;
			<input type="text" name="creatdate2" value="<%=creatdate2 %>" class="Wdate" onFocus="WdatePicker()">		
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td width="15%" onclick="doSort('id');">编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="15%" onclick="doSort('store_id');">库房名称<%if(orderName.equals("store_id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="15%" onclick="doSort('jsr');">经手人<%if(orderName.equals("jsr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="15%" onclick="doSort('creatdate');">日期<%if(orderName.equals("creatdate")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="10%" onclick="doSort('state');">状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td width="15%" onclick="doSort('czr');">操作员<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td width="15%">操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Dbsq dbsq = (Dbsq)it.next();
	%>
	<tr class="a1"  title="双击查看详情"  onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(dbsq.getId()) %>');" onDblClick="openWin('<%=StringUtils.nullToStr(dbsq.getId()) %>');">
		<td><%=StringUtils.nullToStr(dbsq.getId()) %></td>
		<td><%=StaticParamDo.getStoreNameById(StringUtils.nullToStr(dbsq.getStore_id())) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(dbsq.getJsr())) %></td>
		<td><%=StringUtils.nullToStr(dbsq.getCreatdate()) %></td>
		<td><%=StringUtils.nullToStr(dbsq.getState()) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(dbsq.getCzr())) %></td>
		<td>
		<%
		if(StringUtils.nullToStr(dbsq.getState()).equals("已提交")){
		%>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(dbsq.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>
		<%	
		}else{
		%>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(dbsq.getId()) %>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(dbsq.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(dbsq.getId()) %>');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>		
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

<form name="descForm" action="descDbsq.html" method="post" target="dbsqdesc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="dbsqdesc" name="dbsqdesc" width="100%" onload="dyniframesize('dbsqdesc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>
</div>
</body>
</html>
