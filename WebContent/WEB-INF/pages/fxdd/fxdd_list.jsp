<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("pageFxdd");


String creatdate1 = StringUtils.nullToStr(VS.findValue("creatdate1"));
String creatdate2 = StringUtils.nullToStr(VS.findValue("creatdate2"));
String state = StringUtils.nullToStr(VS.findValue("state"));

//物流状态
//包括：待审批、待出库、已出库
String wlzt = StringUtils.nullToStr(VS.findValue("wlzt"));

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>采购订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewFxdd.html?fxdd_id="+id;
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delFxdd.html?fxdd_id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.creatdate1.value = "";
		document.myform.creatdate2.value = "";
		document.myform.state.value = "";
		document.myform.wlzt.value = "";
	}
	
	function add(){
		var destination = "editFxdd.html";
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加销售单',fea);	
	}
	
	function edit(id){
		var destination = "editFxdd.html?fxdd_id=" + id;
		var fea ='width=800,height=600,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';		
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
	
	function desc(id){
		parent.desc.location.href='descXsd.html?id=' + id;
	}
	
	function refreshPage(){
		document.myform.action = "listFxdd.html";
		document.myform.submit();
	}			
</script>
</head>
<body >
<form name="myform" action="listFxdd.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>采购订单</b></td>
		<td class="csstitle" width="25%" align="right">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;
			订单日期：<input type="text" name="creatdate1" value="<%=creatdate1 %>" size="8" readonly>	
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.creatdate1); return false;">
			&nbsp;至&nbsp;
			<input type="text" name="creatdate2" value="<%=creatdate2 %>" size="8" readonly>	
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.creatdate2); return false;">
			&nbsp;
			订单状态：<select name="state">
				<option value=""></option>
				<option value="已保存" <%if(state.equals("已保存")) out.print("selected"); %>>保存</option>
				<option value="已提交" <%if(state.equals("已提交")) out.print("selected"); %>>提交</option>
			</select>
			&nbsp;
			物流状态：<select name="wlzt">
				<option value=""></option>
				<option value="待出库" <%if(wlzt.equals("待出库")) out.print("selected"); %>>待出库</option>
				<option value="已出库" <%if(wlzt.equals("已出库")) out.print("selected"); %>>已出库</option>
			</select>
			&nbsp;			
			<input type="submit" name="buttonCx" value="查询" class="css_button"><input type="button" name="buttonQk" value="清空" class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('fxdd_id');">订单编号<%if(orderName.equals("fxdd_id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('hjje');">订单金额<%if(orderName.equals("hjje")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('creatdate');">创建日期<%if(orderName.equals("creatdate")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('state');">订单状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('wlzt');">物流状态<%if(orderName.equals("wlzt")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Fxdd fxdd = (Fxdd)it.next();
	%>
	<tr class="a1" title="双击查看详情" onmouseover="this.className='a2';" onmouseout="this.className='a1';" onDblClick="openWin('<%=StringUtils.nullToStr(fxdd.getFxdd_id()) %>');">
		<td><%=StringUtils.nullToStr(fxdd.getFxdd_id()) %></td>
		<td align="right"><%=JMath.round(fxdd.getHjje(),2) %>&nbsp;&nbsp;</td>
		<td><%=StringUtils.nullToStr(fxdd.getCreatdate()) %></td>
		<td><%=StringUtils.nullToStr(fxdd.getState()) %></td>
		<td><%=StringUtils.nullToStr(fxdd.getWlzt()) %></td>
		<td>
		<%
		if(!StringUtils.nullToStr(fxdd.getState()).equals("已保存")){
		%>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(fxdd.getFxdd_id()) %>');"><img src="images/view.gif" align="absmiddle" title="查看订单信息" border="0" style="cursor:hand"></a>
		<%	
		}else{
		%>
			<a href="javascript:edit('<%=StringUtils.nullToStr(fxdd.getFxdd_id()) %>');"><img src="images/modify.gif" align="absmiddle" title="修改订单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:openWin('<%=StringUtils.nullToStr(fxdd.getFxdd_id()) %>');"><img src="images/view.gif" align="absmiddle" title="查看订单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:del('<%=StringUtils.nullToStr(fxdd.getFxdd_id()) %>');"><img src="images/del.gif" align="absmiddle" title="删除订单" border="0" style="cursor:hand"></a>		
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
