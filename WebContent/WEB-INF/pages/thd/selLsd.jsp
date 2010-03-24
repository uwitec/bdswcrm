<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("pageLsd");

String creatdate = (String)VS.findValue("creatdate");
String creatdate2 = (String)VS.findValue("creatdate2");
String client_name = (String)VS.findValue("client_name");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
%>

<html>
<head>
<title>关联零售单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewLsd.html?id="+id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delLsd.html?id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.creatdate.value = "";
		document.myform.creatdate2.value = "";
		document.myform.client_name.value = "";
	}
	
	function add(){
		var destination = "addLsd.html";
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加销售单',fea);	
	}
	
	function edit(id){
		var destination = "editLsd.html?id=" + id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-750)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改销售单',fea);		
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
		document.myform.action = "selLsd.html";
		document.myform.submit();
	}
			
</script>
</head>
<body>
<form name="myform" action="selLsd.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>零售单</b></td>
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			客户名称：<input type="text" name="client_name" value="<%=client_name %>">&nbsp;&nbsp;
			时间：<input type="text" name="creatdate" value="<%=creatdate %>" size="15" class="Wdate" onFocus="WdatePicker()">&nbsp;至&nbsp;
			<input type="text" name="creatdate2" value="<%=creatdate2 %>" size="15" class="Wdate" onFocus="WdatePicker()">	
			&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('id');">零售单编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('client_name');">客户姓名<%if(orderName.equals("client_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('lxr');">联系人<%if(orderName.equals("lxr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('lxdh');">联系电话<%if(orderName.equals("lxdh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('lsdje');">金额<%if(orderName.equals("lsdje")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('creatdate');">时间<%if(orderName.equals("creatdate")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('xsry');">销售人员<%if(orderName.equals("xsry")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map lsd = (Map)it.next();
		double lsdje = lsd.get("lsdje")==null?0:((Double)lsd.get("lsdje")).doubleValue();
	%>
	<tr class="a1" title="双击查看详情"  onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(lsd.get("id")) %>');" onDblClick="openWin('<%=StringUtils.nullToStr(lsd.get("id")) %>');">
		<td><%=StringUtils.nullToStr(lsd.get("id")) %></td>
		<td><%=StringUtils.nullToStr(lsd.get("client_name")) %></td>
		<td><%=StringUtils.nullToStr(lsd.get("lxr")) %></td>
		<td><%=StringUtils.nullToStr(lsd.get("lxdh")) %></td>
		<td align="right"><%=JMath.round(lsdje,2) %>&nbsp;</td>
		<td><%=StringUtils.nullToStr(lsd.get("creatdate")) %></td>
		<td><%=StringUtils.nullToStr(lsd.get("xsry")) %></td>
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
<form name="descForm" action="descSelLsd.html" method="post" target="desc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="desc" name="desc" width="100%" onload="dyniframesize('desc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>
</body>
</html>
