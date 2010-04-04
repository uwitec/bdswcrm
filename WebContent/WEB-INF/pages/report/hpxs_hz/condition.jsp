<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

String[] arryClientType = (String[])VS.findValue("arryClientType");
%>

<html>
<head>
<title>货品销售汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">
	function openClientWin(){
		var destination = "selectClient.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'详细信息',fea);		
	}
	
	function openWin(){
		var destination = "selKind.html";
		var fea ='width=400,height=500,left=' + (screen.availWidth-400)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择商品类别',fea);
	}
	function openywyWin()
	{
	   var destination = "selLsEmployee.html";
		var fea ='width=800,height=500,left=' + (screen.availWidth-800)/2 + ',top=' + (screen.availHeight-500)/2 + ',directories=no,localtion=no,menubar=no,status=no,toolbar=no,scrollbars=yes,resizeable=no';
		
		window.open(destination,'选择经手人',fea);	
	}
	function submits()
	{
	   document.reportForm.submit();
	}	
</script>
</head>
<body onload="initFzrTip();initClientTip();">
<form name="reportForm" action="getHpxsHzResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">货品销售汇总</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<tr>
		<td class="a1">开始日期</td>
		<td class="a4">
			<input type="text" name="start_date" id="start_date" value="<%=DateComFunc.getToday() %>"  class="Wdate" size="35" onFocus="WdatePicker()"></td>
		<td class="a1">结束日期</td>
		<td class="a4">
			<input type="text" name="end_date" id="end_date" value="<%=DateComFunc.getToday() %>"  class="Wdate" size="35" onFocus="WdatePicker()"></td>
	</tr>
	<tr>
		<td class="a1">客户名称</td>
		<td class="a4">
			<input type="text" name="clientId" id="client_name" value=""  size="35" onblur="setClientValue();" maxlength="50">
			<input type="hidden" name="clientName" id="client_id" value="">
			<div id="clientsTip" style="height:12px;position:absolute;left:170px; top:85px; width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		</td>
		<td class="a1">客户类型</td>
		<td class="a4">
			<select name="client_type">
				<option value=""></option>
				<%
				if(arryClientType != null && arryClientType.length > 0){
					for(int i=0;i<arryClientType.length;i++){
				%>
					<option value="<%=arryClientType[i] %>"><%=arryClientType[i] %></option>
				<%
					}
				}
				%>
			</select>
		</td>
	</tr>	
	<tr>
		<td class="a1">销售人员</td>
		<td class="a4">
		    <input  id="brand" type="text"   length="20"  onblur="setValue()"   size="35"/> 
            <div   id="brandTip"  style="height:12px;position:absolute;left:700px; top:85px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <input type="hidden" name="xsry_id" id="fzr"/>
		</td>	
		<td class="a1" width="15%">商品类别</td>
		<td class="a4" width="35%">
			<input type="text" name="kind_name" id="kind_name" value="" size="35" onclick="openWin();" readonly>
			<input type="hidden" name="product_kind" id="product_kind" value="">
			<img src="images/select.gif" align="absmiddle" title="点击选择类别" border="0" onclick="openWin();" style="cursor:hand">
		</td>					
	</tr>
	<tr>
		<td class="a1" width="15%">商品名称</td>
		<td class="a4" width="35%">
			<input type="text" name="product_name" id="product_name" value="" size="35">
		</td>		
		<td class="a1" width="15%">商品规格</td>
		<td class="a4">
			<input type="text" name="product_xh" id="product_xh" value="" size="35">
		</td>										
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交" onclick="submits()" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">
		</td>
	</tr>
</table>
</form>
</body>
</html>