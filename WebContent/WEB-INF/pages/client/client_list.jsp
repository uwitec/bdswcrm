<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("clientsPage");

String name = (String)VS.findValue("name");
String lxr = (String)VS.findValue("lxr");
String khjl = (String)VS.findValue("khjl");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewClient.html?id="+id;
		window.open(destination);	
	}
	
	function del(id){
		if(confirm("确定要删除该单位及联系人吗！")){
			$.ajax({
				cache: false,
				url:"checkClientCanDel.html",
				type: "POST",
				data:{id:id},
				success: function(result) {
					if(result == "false"){
						alert("已发生往来业务，客户信息不能删除！");
					}else{
						document.myform.action = "delClient.html?id=" + id;
						document.myform.submit();
					}
				}
			});	
		}
	}
	
	function add(){
		var destination = "addClient.html";
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
			
		window.open(destination,'添加单位',fea);	
	}
	
	function edit(id){
		var destination = "editClient.html?id=" + id;
		var fea = 'width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改单位',fea);		
	}		
	
	function clearAll(){
		document.myform.name.value = "";
		document.myform.lxr.value = "";
		document.myform.khjl.value = "";
	}
	
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
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
		document.myform.action = "listClient.html";
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
<form name="myform" action="listClient.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>往来单位</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			单位名称：<input type="text" name="name" value="<%=name %>" onkeypress="f_enter();">&nbsp;&nbsp;
			联系人：<input type="text" name="lxr" value="<%=lxr %>" onkeypress="f_enter();">&nbsp;&nbsp;
			客户经理：
		    <input type="text" name="khjl" id="khjl" value="<%=khjl%>" /> 
			<input type="button" name="buttonCx" value=" 查询 " onclick="submits()" class="css_button">&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" border="1" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td width="10%" onclick="doSort('id');">编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="25%" onclick="doSort('name');">单位名称<%if(orderName.equals("name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="25%" onclick="doSort('address');">地址<%if(orderName.equals("address")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="10%" onclick="doSort('gzdh');">固定电话<%if(orderName.equals("gzdh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="10%" onclick="doSort('khjl');">客户经理<%if(orderName.equals("khjl")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="10%" onclick="doSort('flag');">状态<%if(orderName.equals("flag")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="10%">操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Clients clients = (Clients)it.next();
	%>
	<tr class="a1" onmousedown="trSelectChangeCss()" title="双击查看详情" onDblClick="openWin('<%=StringUtils.nullToStr(clients.getId()) %>');">
		<td><%=StringUtils.nullToStr(clients.getId()) %></td>
		<td align="left"><%=StringUtils.nullToStr(clients.getName()) %></td>
		<td align="left"><%=StringUtils.nullToStr(clients.getAddress()) %></td>
		<td><%=StringUtils.nullToStr(clients.getGzdh()) %></td>
		<td><%=StaticParamDo.getRealNameById(clients.getKhjl()) %></td>
		<td><%=StringUtils.nullToStr(clients.getFlag()).equals("1")?"正常":"停用" %></td>
		<td>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(clients.getId()) %>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(clients.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(clients.getId()) %>');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>
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