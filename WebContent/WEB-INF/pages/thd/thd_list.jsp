<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("pageThd");

String client_name = (String)VS.findValue("client_name");
String th_date1 = (String)VS.findValue("th_date1");
String th_date2 = (String)VS.findValue("th_date2");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>销售退货单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewThd.html?thd_id="+id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-720)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function add(){
		var destination = "addThd.html";
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-720)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加出库单',fea);	
	}
	
	function edit(id){
		var destination = "editThd.html?thd_id=" + id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-720)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改出库单',fea);		
	}	
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delThd.html?thd_id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.client_name.value = "";
		document.myform.th_date1.value = "";
		document.myform.th_date2.value = "";
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
	
	function descMx(id){
		document.descForm.thd_id.value = id;
		document.descForm.submit();			
	}		
	
	function refreshPage(){
		document.myform.action = "listThd.html";
		document.myform.submit();
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
<form name="myform" action="listThd.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>销售退货单</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#"  onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#"  onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			客户名称：<input type="text" name="client_name" size="25" value="<%=client_name %>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			退货日期：<input type="text" name="th_date1" value="<%=th_date1 %>" size="15"  class="Wdate" onFocus="WdatePicker()">	&nbsp;&nbsp;至&nbsp;&nbsp;
			<input type="text" name="th_date2" value="<%=th_date2 %>" size="15"  class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value="查询" class="css_button">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value="清空" class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0"  border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('thd_id');">退货单编号<%if(orderName.equals("thd_id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('yw_type');">退货类型<%if(orderName.equals("yw_type")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('client_name');">客户名称<%if(orderName.equals("client_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('th_fzr');">退货负责人<%if(orderName.equals("th_fzr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('thdje');">退货单金额<%if(orderName.equals("thdje")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('th_date');">退货日期<%if(orderName.equals("th_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('state');">状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td onclick="doSort('czr');">操作员<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>	
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map thd = (Map)it.next();
		double thdje = thd.get("thdje")==null?0:((Double)thd.get("thdje")).doubleValue();
	%>
	<tr class="a1"  title="双击查看详情" <%if(StringUtils.nullToStr(thd.get("th_flag")).equals("1")){ %>style="color:red"<%} %>  onmousedown="trSelectChangeCss();"  onclick="descMx('<%=StringUtils.nullToStr(thd.get("thd_id")) %>');" onDblClick="openWin('<%=StringUtils.nullToStr(thd.get("thd_id")) %>');">
		<td><%=StringUtils.nullToStr(thd.get("thd_id")) %></td>
		<td><%=StringUtils.nullToStr(thd.get("yw_type")).equals("1")?"销售订单":"零售单" %></td>
		<td><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(thd.get("client_name"))) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(thd.get("th_fzr"))) %></td>
		<td align="right"><%=JMath.round(thdje,2) %>&nbsp;&nbsp;&nbsp;</td>
		<td><%=StringUtils.nullToStr(thd.get("th_date")) %></td>
		<td><%=StringUtils.nullToStr(thd.get("state")) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(thd.get("czr"))) %></td>
		<td>
		<%
		if(!StringUtils.nullToStr(thd.get("state")).equals("已保存")){
		%>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(thd.get("thd_id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>
		<%	
		}else{
		%>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(thd.get("thd_id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(thd.get("thd_id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(thd.get("thd_id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>		
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

<form name="descForm" action="descThd.html" method="post" target="desc">
	<input type="hidden" name="thd_id" value="">
</form>
说明：红色字体标示该订单为库房退回订单，订单修改后可再次提交
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="desc" name="desc" width="100%" onload="dyniframesize('desc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>

</body>
</html>
