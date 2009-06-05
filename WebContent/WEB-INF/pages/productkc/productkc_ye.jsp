<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("productKcPage");

String product_xh = (String)VS.findValue("product_xh");
String product_name = (String)VS.findValue("product_name");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
%>

<html>
<head>
<title>库存列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewKcye.html?product_id="+id;
		var fea ='width=400,height=300,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-300)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'设置库存数量',fea);	
	}
	
	function del(id){
		if(confirm("确定要删除该条记录吗！")){
			location.href = "delProductKc.html?kc_id=" + id;
		}
	}
	
	function clearAll(){
		document.myform.product_xh.value = "";
		document.myform.product_name.value = "";
	}
	
	function add(){
		lcation.href = "addJhd.html";
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
</script>
</head>
<body oncontextmenu="return false;" >
<form name="myform" action="listKcye.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>库存余额</b></td>
		<td class="csstitle" width="25%">&nbsp;&nbsp;&nbsp;
			<img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" class="xxlb"> 导出EXCEL </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			产品名称：<input type="text" name="product_name" value="<%=product_name %>">&nbsp;&nbsp;&nbsp;&nbsp;
			规格：<input type="text" name="product_xh" value="<%=product_xh %>">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button2" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>
		<td>序号</td>
		<td onclick="doSort('product_name');">产品名称<%if(orderName.equals("product_name")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('product_xh');">规格<%if(orderName.equals("product_xh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('gysmc');">供应商名称<%if(orderName.equals("gysmc")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('prop');">产品属性<%if(orderName.equals("prop")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('dw');">计量单位<%if(orderName.equals("dw")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('kc_nums');">库存数量<%if(orderName.equals("kc_nums")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td onclick="doSort('price');">成本价<%if(orderName.equals("price")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td>库存余额</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	int i = 0;
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		String state = StringUtils.nullToStr(map.get("flag"));
		if(state.equals("1")){
			state = "正常";
		}else{
			state = "停售";
		}
		i = i + 1 ;
		
		double price = map.get("price")==null?0:((Double)map.get("price")).doubleValue();
		String strNums = StringUtils.nullToStr(map.get("kc_nums"));
		if(strNums.equals("")){
			strNums =  "0";
		}
		double kcye = price * new Integer(strNums).intValue();
	%>
	<tr class="a1" title="双击查看产品库存"  onmousedown="trSelectChangeCss()" onDblClick="openWin('<%=StringUtils.nullToStr(map.get("product_id")) %>');">
		<td><%=i %></td>
		<td><%=StringUtils.nullToStr(map.get("product_name")) %></td>
		<td><%=StringUtils.nullToStr(map.get("product_xh")) %></td>
		<td><%=StringUtils.nullToStr(map.get("gysmc")) %></td>	
		<td><%=StringUtils.nullToStr(map.get("prop")) %></td>
		<td><%=StringUtils.nullToStr(map.get("dw")) %></td>
		<td><%=strNums %></td>
		<td><%=JMath.round(price,2) %></td>	
		<td><%=JMath.round(kcye,2) %></td>			
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
