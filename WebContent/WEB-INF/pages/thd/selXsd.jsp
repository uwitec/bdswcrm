<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("pageXsd");

String client_name = (String)VS.findValue("client_name");
String creatdate1 = (String)VS.findValue("creatdate1");
String creatdate2 = (String)VS.findValue("creatdate2");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");

%>

<html>
<head>
<title>关联销售单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewXsd.html?id="+id;
		var fea ='width=900,height=700,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function clearAll(){
		document.myform.client_name.value = "";
		document.myform.creatdate1.value = "";
		document.myform.creatdate2.value = "";
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
	
	function desc(id){
		parent.desc.location.href='descSelXsd.html?id=' + id;
	}
</script>
</head>
<body oncontextmenu="return false;" >
<form name="myform" action="selXsd.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			客户名称：<input type="text" name="client_name" value="<%=client_name %>">&nbsp;&nbsp;&nbsp;
			日期：<input type="text" name="creatdate1" value="<%=creatdate1 %>" size="8" readonly>	
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.creatdate1); return false;">
			&nbsp;&nbsp;至&nbsp;&nbsp;
			<input type="text" name="creatdate2" value="<%=creatdate2 %>" size="8" readonly>	
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.creatdate2); return false;">
			&nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('id');">编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('client_name');">往来单位<%if(orderName.equals("client_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('state');">状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('fzr');">负责人<%if(orderName.equals("fzr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('sklx');">收款类型<%if(orderName.equals("sklx")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('creatdate');">时间<%if(orderName.equals("creatdate")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
	%>
	<tr class="a1" title="双击查看详情"  onmousedown="trSelectChangeCss()" onclick="desc('<%=StringUtils.nullToStr(map.get("id")) %>');" onDblClick="openWin('<%=StringUtils.nullToStr(map.get("id")) %>');">
		<td><%=StringUtils.nullToStr(map.get("id")) %></td>
		<td><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(map.get("client_name"))) %></td>
		<td><%=StringUtils.nullToStr(map.get("state")) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("fzr"))) %></td>
		<td><%=StringUtils.nullToStr(map.get("sklx")) %></td>
		<td><%=StringUtils.nullToStr(map.get("creatdate")) %></td>
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
</body>
</html>
