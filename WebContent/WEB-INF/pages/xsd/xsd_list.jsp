<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("pageXsd");

String client_name = StringUtils.nullToStr(VS.findValue("client_name"));
String creatdate1 = StringUtils.nullToStr(VS.findValue("creatdate1"));
String creatdate2 = StringUtils.nullToStr(VS.findValue("creatdate2"));
String fzr = StringUtils.nullToStr(VS.findValue("fzr"));
String skxs = StringUtils.nullToStr(VS.findValue("skxs"));
String state = StringUtils.nullToStr(VS.findValue("state"));

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");

%>

<html>
<head>
<title>销售出库单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewXsd.html?id="+id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delXsd.html?id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.client_name.value = "";
		document.myform.creatdate1.value = "";
		document.myform.creatdate2.value = "";
		document.myform.fzr.value = "";
		document.myform.state.value = "";
		document.myform.skxs.value = "";
	}
	
	function add(){
		var destination = "addXsd.html";
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加销售单',fea);	
	}
	
	function edit(id){
		var destination = "editXsd.html?flag=0&id=" + id;
		var fea ='width=950,height=700,left=' + (screen.availWidth-950)/2 + ',top=' + (screen.availHeight-700)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
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
		document.myform.action = "listXsd.html";
		document.myform.submit();
	}
</script>
</head>
<body>
<form name="myform" action="listXsd.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>销售订单</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;
			客户名称：<input type="text" name="client_name" value="<%=client_name %>" size="10">&nbsp;
			日期：<input type="text" name="creatdate1" value="<%=creatdate1 %>" size="8" readonly>	
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.creatdate1); return false;">
			&nbsp;至&nbsp;
			<input type="text" name="creatdate2" value="<%=creatdate2 %>" size="8" readonly>	
			<img src="images/data.gif" style="cursor:hand" width="16" height="16" border="0" onClick="return fPopUpCalendarDlg(document.myform.creatdate2); return false;">
			&nbsp;
			销售员：<input type="text" name="fzr" value="<%=fzr %>" size="10">&nbsp;&nbsp;
			订单状态：<select name="state">
				<option value=""></option>
				<option value="已保存" <%if(state.equals("已保存")) out.print("selected"); %>>已保存</option>
				<option value="已提交" <%if(state.equals("已提交")) out.print("selected"); %>>已提交</option>
				<option value="已出库" <%if(state.equals("已出库")) out.print("selected"); %>>已出库</option>
			</select>&nbsp;&nbsp;
			回款状态：<select name="skxs">
				<option value=""></option>
				<option value="未收" <%if(skxs.equals("未收")) out.print("selected"); %>>未收</option>
				<option value="部分已收" <%if(skxs.equals("部分已收")) out.print("selected"); %>>部分已收</option>
				<option value="已收" <%if(skxs.equals("已收")) out.print("selected"); %>>已收</option>
			</select>
			<input type="submit" name="buttonCx" value="查询" class="css_button"><input type="button" name="buttonQk" value="清空" class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%" align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('id');">销售单编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('client_name');">客户名称<%if(orderName.equals("client_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('state');">状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('xsdje');">销售单金额<%if(orderName.equals("xsdje")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('creatdate');">日期<%if(orderName.equals("creatdate")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('fzr');">销售员<%if(orderName.equals("fzr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('czr');">操作员<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('skxs');">回款类型<%if(orderName.equals("skxs")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('sp_state');">审批状态<%if(orderName.equals("sp_state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map xsd = (Map)it.next();
		double xsdje = xsd.get("xsdje")==null?0:((Double)xsd.get("xsdje")).doubleValue();
		
		String sp_state = StringUtils.nullToStr(xsd.get("sp_state"));
		
		String strSpState = "";
		if(sp_state.equals("0")){
			strSpState = "自动审批";
		}else if(sp_state.equals("1")){
			strSpState = "需审批";
		}else if(sp_state.equals("2")){
			strSpState = "待审批";
		}else if(sp_state.equals("3")){
			strSpState = "审批通过";
		}else if(sp_state.equals("4")){
			strSpState = "审批不通过";
		}
	%>
	<tr class="a1" title="双击查看详情"  <%if(StringUtils.nullToStr(xsd.get("th_flag")).equals("1")){ %>style="color:red"<%} %> onmousedown="trSelectChangeCss()" onclick="descMx('<%=StringUtils.nullToStr(xsd.get("id")) %>');" onDblClick="openWin('<%=StringUtils.nullToStr(xsd.get("id")) %>');">
		<td><%=StringUtils.nullToStr(xsd.get("id")) %></td>
		<td><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(xsd.get("client_name"))) %></td>
		<td><%=StringUtils.nullToStr(xsd.get("state")) %></td>
		<td align="right"><%=JMath.round(xsdje,2) %>&nbsp;&nbsp;</td>
		<td><%=StringUtils.nullToStr(xsd.get("creatdate")) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(xsd.get("fzr"))) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(xsd.get("czr"))) %></td>
		<td><%=StringUtils.nullToStr(xsd.get("skxs")) %></td>
		<td><%=strSpState %></td>
		<td>
		<%
		if(!StringUtils.nullToStr(xsd.get("state")).equals("已保存") || sp_state.equals("2")){
		%>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(xsd.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看销售单信息" border="0" style="cursor:hand"></a>
		<%	
		}else{
		%>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(xsd.get("id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改销售单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(xsd.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看销售单信息" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(xsd.get("id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除该销售单" border="0" style="cursor:hand"></a>		
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
说明：红色字体标示该订单为库房退回订单，订单修改后可再次提交
<form name="descForm" action="descXsd.html" method="post" target="desc">
	<input type="hidden" name="id" value="">
</form>
<table width="100%"  align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><iframe id="desc" name="desc" width="100%" onload="dyniframesize('desc');" border="0" frameborder="0" SCROLLING="no"  src=''/></td>
	</tr>
</table>

</body>
</html>
