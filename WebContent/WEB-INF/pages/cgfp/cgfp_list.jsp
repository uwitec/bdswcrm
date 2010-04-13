<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Page results = (Page)VS.findValue("cgfpPage");

String gysmc = (String)VS.findValue("gysmc");
String cg_date1 = (String)VS.findValue("cg_date1");
String cg_date2 = (String)VS.findValue("cg_date2");
String fpstate = (String)VS.findValue("fpstate");

String orderName = (String)VS.findValue("orderName");
String orderType = (String)VS.findValue("orderType");
%>

<html>
<head>
<title>采购发票</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	function openWin(id){
		var destination = "viewCgfp.html?id="+id;
		var fea = 'width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-720)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);	
	}
		
	function edit(id){
		var destination = "editCgfp.html?id=" + id;
		var fea ='width=850,height=600,left=' + (screen.availWidth-850)/2 + ',top=' + (screen.availHeight-720)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'采购发票',fea);		
	}		
	
	function clearAll(){
		document.myform.gysmc.value = "";
		document.myform.cg_date1.value = "";
		document.myform.cg_date2.value = "";
		document.myform.fpstate.value = "";
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
		document.myform.action = "listCgfp.html";
		document.myform.submit();
	}	
</script>
</head>
<body >
<form name="myform" action="listCgfp.html" method="post">
<input type="hidden" name="orderType" value="<%=orderType %>">
<input type="hidden" name="orderName" value="<%=orderName %>">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>采购发票</b></td>
		<td class="csstitle" width="25%">
		  <img src="images/import.gif" align="absmiddle" border="0">&nbsp;<a href="#" onclick="refreshPage();" class="xxlb"> 刷 新 </a>	</td>			
	</tr>
	<tr>
		<td class="search" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
			单位名称：<input type="text" name="gysmc" value="<%=gysmc %>">&nbsp;&nbsp;
			采购日期：<input type="text" name="cg_date1" value="<%=cg_date1 %>" size="15"  class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;至&nbsp;&nbsp;
			<input type="text" name="cg_date2" value="<%=cg_date2 %>" size="15"  class="Wdate" onFocus="WdatePicker()">
			&nbsp;&nbsp;状态：
			<select name="fpstate">
				<option value="未入库" <%if(fpstate.equals("未入库")) out.print("selected"); %>>未入库</option>
				<option value="已入库" <%if(fpstate.equals("已入库")) out.print("selected"); %>>已入库</option>
				<option value="" ></option>				
			</select>&nbsp;&nbsp;
			<input type="submit" name="buttonCx" value=" 查询 " class="css_button">	
			<input type="button" name="buttonQk" value=" 清空 " class="css_button" onclick="clearAll();">
		</td>				
	</tr>		
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<thead>
	<tr>		
		<td width="24%" nowrap="nowrap" onclick="doSort('gysbh');">供货单位<%if(orderName.equals("gysbh")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="8%" nowrap="nowrap" onclick="doSort('cgnums');">单据数<%if(orderName.equals("cgnums")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>		
		<td width="15%" nowrap="nowrap" onclick="doSort('cgmoney');">总金额<%if(orderName.equals("cgmoney")) out.print("<img src='images/" + orderType + ".gif'>"); %></td>
		<td width="10%" nowrap="nowrap">操作</td>
	</tr>
	</thead>
	<%
	List list = results.getResults();
	Iterator it = list.iterator();
	
	while(it.hasNext()){
		Map map = (Map)it.next();
		
		String cgmoney = JMath.round((map.get("cgmoney")==null?0:((Double)map.get("cgmoney")).doubleValue()),2);
	%>
	<tr class="a1" title="双击查看详情"  onmousedown="trSelectChangeCss()" onDblClick="openWin('<%=StringUtils.nullToStr(map.get("gysbh")) %>');">
		<td><%=StringUtils.nullToStr(map.get("gysmc")) %> </td>
		<td align="right" nowrap="nowrap"><%=map.get("cgnums") %>&nbsp;&nbsp;</td>
		<td align="right" nowrap="nowrap"><%=cgmoney %>&nbsp;&nbsp;</td>
					
		<td>
			<a href="#" onclick="edit('<%=StringUtils.nullToStr(map.get("gysbh")) %>');"><img src="images/modify.gif" align="absmiddle" title="修改" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onclick="openWin('<%=StringUtils.nullToStr(map.get("gysbh")) %>');"><img src="images/view.gif" align="absmiddle" title="查看" border="0" style="cursor:hand"></a>&nbsp;&nbsp;&nbsp;&nbsp;
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
