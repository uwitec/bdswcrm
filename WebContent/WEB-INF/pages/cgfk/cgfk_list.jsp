<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("cgfkPage");

String gysmc = (String)VS.findValue("gysmc");
String fk_date1 = (String)VS.findValue("fk_date1");
String fk_date2 = (String)VS.findValue("fk_date2");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
%>

<html>
<head>
<title>采购付款列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewCgfk.html?id="+id;
		var fea = 'width=850,height=700,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-720)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delCgfk.html?id=" + id;
		}
	}
	
	function add(){
		var destination = "addCgfk.html";
		var fea = 'width=850,height=700,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-720)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'采购付款',fea);	
	}
	
	function edit(id){
		var destination = "editCgfk.html?id=" + id;
		var fea ='width=850,height=700,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-720)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'采购付款',fea);		
	}		
	
	function clearAll(){
		document.myform.gysmc.value = "";
		document.myform.fk_date1.value = "";
		document.myform.fk_date2.value = "";
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
	
	function refreshPage(){
		document.myform.action = "listCgfk.html";
		document.myform.submit();
	}	
</script>
</head>
<body >
<form name="myform" action="listCgfk.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>采购付款</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			单位名称：<input type="text" name="gysmc" value="<%=gysmc %>">&nbsp;&nbsp;&nbsp;&nbsp;
			付款日期：<input type="text" name="fk_date1" value="<%=fk_date1 %>" size="15"  class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;至&nbsp;&nbsp;
			<input type="text" name="fk_date2" value="<%=fk_date2 %>" size="15"  class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('id');">编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('gysbh');">供货单位<%if(orderName.equals("gysbh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('fkje');">付款金额<%if(orderName.equals("fkje")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td onclick="doSort('fkzh');">付款账户<%if(orderName.equals("fkzh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('jsr');">经手人<%if(orderName.equals("jsr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('state');">状态<%if(orderName.equals("state")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>是否预付款</td>
		<td onclick="doSort('czr');">操作员<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		
		String fkje = JMath.round((map.get("fkje")==null?0:((Double)map.get("fkje")).doubleValue()),2);
	%>
	<tr class="a1" title="双击查看详情"  onmousedown="trSelectChangeCss()" onDblClick="openWin('<%=StringUtils.nullToStr(map.get("id")) %>');">
		<td><%=StringUtils.nullToStr(map.get("id")) %></td>
		<td><%=StringUtils.nullToStr(map.get("gysmc")) %></td>
		<td align="right"><%=fkje %>&nbsp;&nbsp;</td>
		<td><%=StaticParamDo.getAccountNameById(StringUtils.nullToStr(map.get("fkzh"))) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("jsr"))) %></td>
		<td><%=StringUtils.nullToStr(map.get("state")) %></td>
		<td><%=StringUtils.nullToStr(map.get("is_yfk")) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("czr"))) %></td>
		<td>
		<%
		if(StringUtils.nullToStr(map.get("state")).equals("已提交")){
		%>
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(map.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>
		<%	
		}else{
		%>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(map.get("id")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(map.get("id")) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="del('<%=StringUtils.nullToStr(map.get("id")) %>');"><img src="images/del.gif" align="absmiddle" title="删除" border="0" style="cursor:hand"></a>
		
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
