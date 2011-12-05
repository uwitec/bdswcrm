<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("pageQtsr");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");


String sr_date1 = (String)VS.findValue("sr_date1");
String sr_date2 = (String)VS.findValue("sr_date2");
String state = StringUtils.nullToStr(VS.findValue("state"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>其他收入</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewQtsr.html?id="+id;
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			//location.href = "delQtsr.html?id=" + id;
			document.myform.action = "delQtsr.html?id=" + id;
			document.myform.submit();
		}
	}
	
	function clearAll(){
		document.myform.sr_date1.value = "";
		document.myform.sr_date2.value = "";
		document.myform.state.value = "";
	}	
	
	function add(){
		var destination = "addQtsr.html";
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'其他收入',fea);	
	}
	
	function edit(id){
		var destination = "editQtsr.html?id=" + id;
		var fea ='width=600,height=400,left=' + (screen.availWidth-600)/2 + ',top=' + (screen.availHeight-400)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'其他收入',fea);		
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
		document.myform.action = "listQtsr.html";
		document.myform.submit();
	}	
	
</script>
</head>
<body>
<div class="rightContentDiv" id="divContent">
<form name="myform" action="listQtsr.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>其它收入</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#"  onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb" onclick="refreshPage();"> 刷 新 </a>	</td>	
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			时间：<input type="text" name="sr_date1" value="<%=sr_date1 %>" size="15" class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;
			<input type="text" name="sr_date2" value="<%=sr_date2 %>" size="15" class="Wdate" onFocus="WdatePicker()">&nbsp;&nbsp;&nbsp;&nbsp;
			状态：
			<select name="state">
				<option value=""></option>
				<option value="已保存" <%if(state.equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(state.equals("已提交")) out.print("selected"); %>>已提交</option>
			</select>	
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>				
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td width="15%" onclick="doSort('id');">编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="15%" onclick="doSort('type');">类型<%if(orderName.equals("type")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="15%" onclick="doSort('skje');">收入金额<%if(orderName.equals("skje")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="15%" onclick="doSort('jsr');">经手人<%if(orderName.equals("jsr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="15%" onclick="doSort('sr_date');">日期<%if(orderName.equals("sr_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="10%" onclick="doSort('czr');">操作员<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="15%">操作</td>
	</tr>
	</thead>
	<%
	Iterator its = (results.getResults()).iterator();
	
	while(its.hasNext()){
		Qtsr qtsr = (Qtsr)its.next();
		
	%>
	<tr class="a1"  title="双击查看详情"  onmousedown="trSelectChangeCss()"  onDblClick="openWin('<%=StringUtils.nullToStr(qtsr.getId()) %>');">
		<td><%=StringUtils.nullToStr(qtsr.getId()) %></td>
		<td><%=StringUtils.nullToStr(qtsr.getType()) %></td>
		<td align="right"><%=JMath.round(qtsr.getSkje(),2) %>&nbsp;&nbsp;</td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(qtsr.getJsr())) %></td>
		<td><%=StringUtils.nullToStr(qtsr.getSr_date()) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(qtsr.getCzr())) %></td>
		<td>
		<%
		if(StringUtils.nullToStr(qtsr.getState()).equals("已提交")){
		%>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(qtsr.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>
		<%
		}else{
		%>	
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(qtsr.getId()) %>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(qtsr.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;			
			<a href="#" onclick="del('<%=StringUtils.nullToStr(qtsr.getId()) %>');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>		
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
</div>
</body>
</html>
