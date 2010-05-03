<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("pageXssk");

String sk_date1 = (String)VS.findValue("sk_date1");
String sk_date2 = (String)VS.findValue("sk_date2");
String client_name = (String)VS.findValue("client_name");


String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
%>

<html>
<head>
<title>销售收款</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewXssk.html?id="+id;
		var fea = 'width=850,height=700,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-720)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delXssk.html?id=" + id;
		}
	}
	
	function add(){
		var destination = "addXssk.html";
		var fea = 'width=900,height=700,left=' + (screen.availWidth-900)/2 + ',top=' + (screen.availHeight-720)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'添加采购退货单',fea);	
	}
	
	function edit(id){
		var destination = "editXssk.html?id=" + id;
		var fea ='width=900,height=700,left=' + (screen.availWidth-900)/2 + ',top=' + (screen.availHeight-720)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'修改采购退货单',fea);		
	}	
	
	function clearAll(){
		document.myform.client_name.value = "";
		document.myform.sk_date1.value = "";
		document.myform.sk_date2.value = "";
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
		document.myform.action = "listXssk.html";
		document.myform.submit();
	}	
</script>
</head>
<body>
<form name="myform" action="listXssk.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>销售收款</b></td>
		<td class="csstitle" width="25%">
			<img src="images/create.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="add();" class="xxlb"> 添 加 </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>	
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			往来单位：<input type="text" name="client_name" value="<%=client_name %>">&nbsp;&nbsp;&nbsp;&nbsp;
			销售收款日期：<input type="text" name="sk_date1" value="<%=sk_date1 %>" size="15"  class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;
			<input type="text" name="sk_date2" value="<%=sk_date2 %>" size="15"  class="Wdate" onFocus="WdatePicker()">	
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;			
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" border="1" cellspacing="0" id="selTable">
	<thead>
	<tr>
		<td onclick="doSort('id');">编号<%if(orderName.equals("id")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('client_name');">往来单位<%if(orderName.equals("client_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('skje');">收款金额<%if(orderName.equals("skje")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('jsr');">经手人<%if(orderName.equals("jsr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('czr');">操作员<%if(orderName.equals("czr")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('sk_date');">收款日期<%if(orderName.equals("sk_date")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>是否预收款</td>
		<td>操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	
	//超期应收款用红色字体标注
	while(it.hasNext()){
		Map map = (Map)it.next();
		
		double skje = map.get("skje")==null?0:((Double)map.get("skje")).doubleValue();

	%>
	<tr class="a1"  title="双击查看详情" onmousedown="trSelectChangeCss()"  onDblClick="openWin('<%=StringUtils.nullToStr(map.get("id")) %>');">
		<td><%=StringUtils.nullToStr(map.get("id")) %></td>
		<td><%=StaticParamDo.getClientNameById(StringUtils.nullToStr(map.get("client_name"))) %></td>
		<td align="right"><%=JMath.round(skje,2) %>&nbsp;&nbsp;</td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("jsr"))) %></td>
		<td><%=StaticParamDo.getRealNameById(StringUtils.nullToStr(map.get("czr"))) %></td>
		<td><%=StringUtils.nullToStr(map.get("sk_date")) %></td>
		<td><%=StringUtils.nullToStr(map.get("is_ysk")) %></td>
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
