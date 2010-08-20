<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("storePage");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");

%>

<html>
<head>
<title>仓库列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewStore.html?id="+id;
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delStore.html?id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.yfk_id.value = "";
		document.myform.fklx.value = "";
		document.myform.yfrq.value = "";
	}
	
	function add(){
		lcation.href = "addStore.html";
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
		document.myform.action = "listStore.html";
		document.myform.submit();
	}		
</script>
</head>
<body oncontextmenu="return false;" >
<form name="myform" action="listStore.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>仓库资料</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="addStore.html" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td onclick="doSort('id');">仓库编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('name');">名称<%if(orderName.equals("name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('address');">地址<%if(orderName.equals("address")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('lxr');">负责人<%if(orderName.equals("lxr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('lxdh');">联系电话<%if(orderName.equals("lxdh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('flag');">状态<%if(orderName.equals("flag")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
	%>
	<tr>
		<td class="a1"><%=StringUtils.nullToStr(map.get("id")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("name")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("address")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("lxr")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("lxdh")) %></td>
		<td class="a1"><%=StringUtils.nullToStr(map.get("flag")).equals("1")?"正常":"停用" %></td>
		<td class="a1">
			<a href="editStore.html?id=<%=StringUtils.nullToStr(map.get("id")) %>"><img src="images/modify.gif" align="absmiddle" title="修改仓库信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(map.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看仓库信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(map.get("id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除仓库信息" border="0" style="cursor:hand"></a>
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
