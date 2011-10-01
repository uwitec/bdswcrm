<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="com.sw.cms.service.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("clientsPage");
ClientsService clientsService = (ClientsService)VS.findValue("clientsService");

String name = (String)VS.findValue("name");
String lxr = (String)VS.findValue("lxr");
String khjl = (String)VS.findValue("khjl");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
List userList = (List)VS.findValue("userList");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>往来单位资料</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewClientbb.html?id="+id;
		var fea ='width=950,height=750,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-800)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}
	
	function clearAll(){
		document.myform.name.value = "";
		document.myform.lxr.value = "";
		document.myform.khjl.value = "";
		document.myform.brand.value="";
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
		document.myform.action = "listClientbb.html";
		document.myform.submit();
	}	
	function submits()
	{
	   document.myform.submit();
	}	
	
	
	//触发点击回车事件
	function f_enter(){
	    if (window.event.keyCode==13){
	        submits();
	        event.returnValue = false;
	    }
	}
</script>
</head>
<body onload="initFzrTip();">
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listClientbb.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>往来单位资料</b></td>
		<td class="csstitle" width="25%">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			单位名称：<input type="text" name="name" value="<%=name %>" onkeypress="f_enter();">&nbsp;&nbsp;&nbsp;&nbsp;
			联系人：<input type="text" name="lxr" value="<%=lxr %>" onkeypress="f_enter();">&nbsp;&nbsp;&nbsp;&nbsp;
			客户经理：
		    <input  id="brand"    type="text"   length="20"  onblur="setValue()" value="<%=StaticParamDo.getRealNameById(khjl) %>" onkeypress="f_enter();"> 
            <div id="brandTip"  style="position:absolute;left:515px; top:60px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <input type="hidden" name="khjl" id="fzr" value="<%=khjl%>" /> 
			<input type="button" name="buttonCx" value=" 查询 " onclick="submits()" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td onclick="doSort('id');">编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('name');">单位名称<%if(orderName.equals("name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('address');">地址<%if(orderName.equals("address")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('lxr_name');">主联系人<%if(orderName.equals("lxr_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('lxr_yddh');">手机<%if(orderName.equals("lxr_yddh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('lxr_gzdh');">电话<%if(orderName.equals("lxr_gzdh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('lxr_mail');">Email<%if(orderName.equals("lxr_mail")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('khjl');">客户经理<%if(orderName.equals("khjl")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Clients clients = (Clients)it.next();
	%>
	<tr class="a1"  title="双击查看详情" onDblClick="openWin('<%=StringUtils.nullToStr(clients.getId()) %>');">
		<td class="a1"><%=StringUtils.nullToStr(clients.getId()) %></td>
		<td class="a1"><%=StringUtils.nullToStr(clients.getName()) %></td>
		<td class="a1"><%=StringUtils.nullToStr(clients.getAddress()) %></td>
	<% 
	String id=StringUtils.nullToStr(clients.getId());
	ClientsLinkman clientsLinkman=(ClientsLinkman)clientsService.getZClientsLinkman(id);
	%>
		<td class="a1"><%=StringUtils.nullToStr(clientsLinkman.getName()) %></td>
		<td class="a1"><%=StringUtils.nullToStr(clientsLinkman.getYddh()) %></td>
		<td class="a1"><%=StringUtils.nullToStr(clientsLinkman.getGzdh()) %></td>
		<td class="a1"><%=StringUtils.nullToStr(clientsLinkman.getMail()) %></td>
	
		<td class="a1"><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(clients.getKhjl())) %></td>
		<td class="a1">
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(clients.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
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
</div>
</body>
</html>