<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("jhdPage");

String gysbh = (String)VS.findValue("gysbh");
String state = (String)VS.findValue("state");
String cg_date = (String)VS.findValue("cg_date");
String cg_date2 = (String)VS.findValue("cg_date2");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>采购订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" /><script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewJhd.html?id="+id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delJhd.html?id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.gysbh.value = "";
		document.myform.state.value = "";
		document.myform.cg_date.value = "";
		document.myform.cg_date2.value = "";		
	}
	
	function add(){
		var destination = "addJhd.html";
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加进货单',fea);	
	}
	
	function edit(id){
		var destination = "editJhd.html?id=" + id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改进货单',fea);		
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
		document.myform.action = "listJhd.html";
		document.myform.submit();
	}
	
	function print(id){
		var destination = "printJhd.html?id=" + id;
		var fea ='width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'销售订单打印',fea);				
	}		
</script>
</head>
<body>
<form name="myform" action="listJhd.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>采购订单</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			供货单位：<input type="text" name="gysbh" value="<%=gysbh %>">
			&nbsp;&nbsp;&nbsp;&nbsp;
			进货单状态：
			<select name="state">
				<option value=""></option>
				<option value="已保存" <%if(state.equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(state.equals("已提交")) out.print("selected"); %>>已提交</option>
				<option value="已入库" <%if(state.equals("已入库")) out.print("selected"); %>>已入库</option>
			</select>&nbsp;&nbsp;&nbsp;&nbsp;
			采购日期：<input type="text" name="cg_date" value="<%=cg_date %>" size="15"  class="Wdate" onFocus="WdatePicker()">	&nbsp;至&nbsp;
			<input type="text" name="cg_date2" value="<%=cg_date2 %>" size="15"  class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">&nbsp;&nbsp;
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('id');">编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('gysbh');">供货单位<%if(orderName.equals("gysbh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('state');">状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('fzr');">负责人<%if(orderName.equals("fzr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('total');">金额<%if(orderName.equals("total")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('fklx');">付款类型<%if(orderName.equals("fklx")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('cg_date');">采购时间<%if(orderName.equals("cg_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('czr');">操作员<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Jhd jhd = (Jhd)it.next();
	%>
	<tr class="a1" title="双击查看详情" <%if(StringUtils.nullToStr(jhd.getTh_flag()).equals("1")){ %>style="color:red"<%} %>   onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(jhd.getId()) %>');" onDblClick="openWin('<%=StringUtils.nullToStr(jhd.getId()) %>');">
		<td><%=StringUtils.nullToStr(jhd.getId()) %></td>
		<td align="left"><%=StringUtils.nullToStr(jhd.getGysmc()) %></td>
		<td><%=StringUtils.nullToStr(jhd.getState()) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(jhd.getFzr())) %></td>
		<td align="right"><%=JMath.round(jhd.getTotal(),2) %>&nbsp;</td>
		<td><%=StringUtils.nullToStr(jhd.getFklx()) %></td>
		<td><%=StringUtils.nullToStr(jhd.getCg_date()) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(jhd.getCzr())) %></td>
		<td>
		<%if(!StringUtils.nullToStr(jhd.getState()).equals("已保存")){ %>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(jhd.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看订单" border="0" style="cursor:hand"></a>
		<%}else{ %>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(jhd.getId()) %>');"><img src="images/modify.gif" align="absmiddle" title="修改订单" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(jhd.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看订单" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(jhd.getId()) %>');"><img src="images/del.gif" align="absmiddle" title="删除订单" border="0" style="cursor:hand"></a>
		<%} %>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="#" onclick="print('<%=StringUtils.nullToStr(jhd.getId()) %>');"><img src="images/print.png" align="absmiddle" title="打印订单" border="0" style="cursor:hand"></a>			
		</td>
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

<form name="descForm" action="descJhd.html" method="post" target="desc">
	<input type="hidden" name="id" value="">
</form>
说明：红色字体标示该订单为库房退回订单，订单修改后可再次提交
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="desc" name="desc" width="100%" onload="dyniframesize('desc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>

</body>
</html>
