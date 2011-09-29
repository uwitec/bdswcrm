<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("ckdPage");

String ckd_id = StringUtils.nullToStr(VS.findValue("ckd_id"));
String creatdate = StringUtils.nullToStr(VS.findValue("creatdate"));
String creatdate2 = StringUtils.nullToStr(VS.findValue("creatdate2"));
String state = StringUtils.nullToStr(VS.findValue("state"));

String orderName = StringUtils.nullToStr(VS.findValue("orderName"));
String orderType = StringUtils.nullToStr(VS.findValue("orderType"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>出库单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewCkd.html?ckd_id="+id;
		var fea ='width=900,height=600,left=' + (screen.availWidth-900)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delCkd.html?ckd_id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.ckd_id.value = "";
		document.myform.creatdate.value = "";
		document.myform.creatdate2.value = "";
		document.myform.state.value = ""; 
	}
	
	function add(){
		var destination = "addCkd.html";
		var fea ='width=800,height=700,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加出库单',fea);	
	}
	
	function edit(id){
		var destination = "editCkd.html?ckd_id=" + id;
		var fea ='width=900,height=700,left=' + (screen.availWidth-900)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改出库单',fea);		
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
		document.descForm.ckd_id.value = id;
		document.descForm.submit();		
	}
	
	function refreshPage(){
		document.myform.action = "listCkd.html";
		document.myform.submit();
	}		

	function print(id){
		var destination = "printCkd.html?ckd_id=" + id;
		var fea ='width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'出库单打印',fea);				
	}		
		
</script>
</head>
<body>
<form name="myform" action="listCkd.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>出库单</b></td>
		<td class="csstitle" width="25%">
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;
			出库单编号：<input type="text" name="ckd_id" value="<%=ckd_id %>">&nbsp;&nbsp;
			创建时间：<input type="text" name="creatdate" value="<%=creatdate %>" size="15"  class="Wdate" onFocus="WdatePicker()">	&nbsp;至&nbsp;
			<input type="text" name="creatdate2" value="<%=creatdate2 %>" size="15"  class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;
			物流状态：<select name="state">
				<option value=""></option>
				<option value="待出库" <%if(state.equals("待出库")) out.print("selected"); %>>待出库</option>
				<option value="已出库" <%if(state.equals("已出库")) out.print("selected"); %>>已出库</option>
			</select>&nbsp;&nbsp;		
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('ckd_id');">出库单编号<%if(orderName.equals("ckd_id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('creatdate');">创建日期<%if(orderName.equals("creatdate")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('client_name');">客户名称<%if(orderName.equals("client_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('state');">物流状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('fzr');">经手人<%if(orderName.equals("fzr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td onclick="doSort('store_id');">仓库<%if(orderName.equals("store_id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('ck_date');">出库时间<%if(orderName.equals("ck_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('czr');">操作员<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator its = list.iterator();
	
	while(its.hasNext()){
		Map ckd = (Map)its.next();
		
		String ckd_state = StringUtils.nullToStr(ckd.get("state"));
	%>
	<tr class="a1"  title="双击查看详情"  onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(ckd.get("ckd_id")) %>');" onDblClick="openWin('<%=StringUtils.nullToStr(ckd.get("ckd_id")) %>');">
		<td><%=StringUtils.nullToStr(ckd.get("ckd_id")) %></td>
		<td><%=StringUtils.nullToStr(ckd.get("creatdate")) %></td>
		<td align="left"><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(ckd.get("client_name"))) %></td>
		<td><%=StringUtils.nullToStr(ckd.get("state")) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(ckd.get("fzr"))) %></td>
		<td><%=StaticParamDo.getStoreNameById(StringUtils.nullToStr(ckd.get("store_id"))) %></td>		
		<td><%=StringUtils.nullToStr(ckd.get("ck_date")) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(ckd.get("czr"))) %></td>
		<td>
			<%if(ckd_state.equals("已出库")){  //已入库的入库单不能修改%>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(ckd.get("ckd_id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看入库单信息" border="0" style="cursor:hand"></a>	
			<%}else{ %>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(ckd.get("ckd_id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改入库单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(ckd.get("ckd_id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看入库单信息" border="0" style="cursor:hand"></a>
			<%} %>
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="print('<%=StringUtils.nullToStr(ckd.get("ckd_id")) %>');"><img src="images/print.png" align="absmiddle" title="打印出库单" border="0" style="cursor:hand"></a>
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

<form name="descForm" action="descCkdProduct.html" method="post" target="desc">
	<input type="hidden" name="ckd_id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="desc" name="desc" width="100%" onload="dyniframesize('desc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>

</body>
</html>
