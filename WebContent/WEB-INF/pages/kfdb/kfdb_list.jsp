<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("pageKfdb");
List storeList = (List)VS.findValue("storeList");
String ck_date1 = (String)VS.findValue("ck_date1");
String ck_date2 = (String)VS.findValue("ck_date2");
String dckf = (String)VS.findValue("dckf");
String drkf = (String)VS.findValue("drkf");

String state = (String)VS.findValue("state");
String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
%>

<html>
<head>
<title>库房调拨</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewKfdb.html?id="+id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delKfdb.html?id=" + id;
		}
	}
	
	function add(){
		var destination = "addKfdb.html";
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'调拨申请',fea);	
	}	
	
	function clearAll(){
		document.myform.ck_date1.value = "";
		document.myform.ck_date2.value = "";
		document.myform.state.value = "";
		document.myform.dckf.value = "";
		document.myform.drkf.value = "";
	}
	
	function edit(id){
		var destination = "editKfdb.html?id=" + id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'调拨申请',fea);		
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
		document.myform.action = "listKfdb.html";
		document.myform.submit();
	}		
	
	function print(id){
		var destination = "printKfdb.html?id=" + id;
		var fea ='width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-600)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'调拨单打印',fea);				
	}	
</script>
</head>
<body >
<form name="myform" action="listKfdb.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>库房调拨</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			日期：<input type="text" name="ck_date1" value="<%=ck_date1 %>" size="15"  class="Wdate" onFocus="WdatePicker()">	
			&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;
			<input type="text" name="ck_date2" value="<%=ck_date2 %>" size="15"  class="Wdate" onFocus="WdatePicker()">			
			&nbsp;&nbsp;&nbsp;&nbsp;
			状态：
			<select name="state">
				<option value=""></option>
				<option value="已保存" <%if(state.equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已出库" <%if(state.equals("已出库")) out.print("selected"); %>>已出库</option>
				<option value="已入库" <%if(state.equals("已入库")) out.print("selected"); %>>已入库</option>
				<option value="已退回" <%if(state.equals("已退回")) out.print("selected"); %>>已退回</option>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;
			调出仓库：<select name="dckf">
				<option value=""></option>
			<%
			if(storeList != null && storeList.size()>0){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse storeHose = (StoreHouse)it.next();
					String id = StringUtils.nullToStr(storeHose.getId());
					String name = StringUtils.nullToStr(storeHose.getName());
			%>
				<option value="<%=id %>" <%if(id.equals(dckf)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select>
			
			&nbsp;&nbsp;&nbsp;&nbsp;
			调入仓库：<select name="drkf">
				<option value=""></option>
			<%
			if(storeList != null && storeList.size()>0){
				Iterator it = storeList.iterator();
				while(it.hasNext()){
					StoreHouse storeHose = (StoreHouse)it.next();
					String id = StringUtils.nullToStr(storeHose.getId());
					String name = StringUtils.nullToStr(storeHose.getName());
			%>
				<option value="<%=id %>" <%if(id.equals(drkf)) out.print("selected"); %>><%=name %></option>
			<%
				}
			}
			%>
			</select>
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('id');">编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('ck_store_id');">调出库房<%if(orderName.equals("ck_store_id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('rk_store_id');">调入库房<%if(orderName.equals("rk_store_id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('jsr');">经手人<%if(orderName.equals("jsr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('ck_date');">日期<%if(orderName.equals("ck_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('state');">状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td onclick="doSort('czr');">操作员<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Kfdb kfdb = (Kfdb)it.next();
	%>
	<tr class="a1"  title="双击查看详情"  onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(kfdb.getId()) %>');" onDblClick="openWin('<%=StringUtils.nullToStr(kfdb.getId()) %>');">
		<td><%=StringUtils.nullToStr(kfdb.getId()) %></td>
		<td><%=StaticParamDo.getStoreNameById(StringUtils.nullToStr(kfdb.getCk_store_id())) %></td>
		<td><%=StaticParamDo.getStoreNameById(StringUtils.nullToStr(kfdb.getRk_store_id())) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(kfdb.getJsr())) %></td>
		<td><%=StringUtils.nullToStr(kfdb.getCk_date()) %></td>
		<td><%=StringUtils.nullToStr(kfdb.getState()) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(kfdb.getCzr())) %></td>
		<td>
		<%
		if(StringUtils.nullToStr(kfdb.getState()).equals("已保存") || StringUtils.nullToStr(kfdb.getState()).equals("已退回")){
		%>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(kfdb.getId()) %>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(kfdb.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(kfdb.getId()) %>');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>			
		<%	
		}else{
		%>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(kfdb.getId()) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>
		<%	
		}		
		%>
			&nbsp;&nbsp;&nbsp;&nbsp;	
			<a href="#" onclick="print('<%=StringUtils.nullToStr(kfdb.getId()) %>');"><img src="images/print.png" align="absmiddle" title="打印" border="0" style="cursor:hand"></a>	
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
<font color="red">状态说明，已保存：草稿状态；已出库：调拨单已出库，等待入库库房确认；已入库：调入库房确认入库；已退回：调入库房退回；</font>
<form name="descForm" action="descKfdb.html" method="post" target="desc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="desc" name="desc" width="100%" onload="dyniframesize('desc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>

</body>
</html>
