<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

 
List clientsList=(List)VS.findValue("clientList");
%>
<html>
<head>
<title>客户应收汇总表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script type="text/javascript">
function doSubmit(){
	if(document.reportForm.start_date.value == ""){
		alert("开始日期不能为空，请选择！");
		return;
	}
	if(document.reportForm.end_date.value == ""){
		alert("结束日期不能为空，请选择！");
		return;
	}

	document.reportForm.submit();
}

function chgType(){

	var vl = "0";
	var obj = document.reportForm.tj_type;
	for(var i=0;i<obj.length;i++){
		if(obj[i].checked){
			vl = obj[i].value;
		}
	}

	var clients = document.getElementById("td_client");
	var xsrys = document.getElementById("td_xsry");
	if(vl == "0"){
		document.getElementById("td_client1").style.display = "";
		document.getElementById("td_client2").style.display = "";

		document.getElementById("td_xsry1").style.display = "none";
		document.getElementById("td_xsry2").style.display = "none";
	}else{
		document.getElementById("td_client1").style.display = "none";
		document.getElementById("td_client2").style.display = "none";

		document.getElementById("td_xsry1").style.display = "";
		document.getElementById("td_xsry2").style.display = "";
	}
}
</script>
</head>
<body onload="initClientTip();">
<form name="reportForm" action="getYsmxResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">客户应收汇总表</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<tr>
		<td class="a1" width="15%">开始日期</td>
		<td class="a4" width="35%">
			<input type="text" name="start_date" id="start_date" value="<%=DateComFunc.getToday() %>" class="Wdate" onFocus="WdatePicker()"></td>
		<td class="a1" width="15%">结束日期</td>
		<td class="a4" width="35%">
			<input type="text" name="end_date" id="end_date" value="<%=DateComFunc.getToday() %>" class="Wdate" onFocus="WdatePicker()"></td>
	</tr>
	<tr>
		<td class="a1">客户名称</td>
		<td class="a4">
		<input type="text" name="cl" id="client_name" value="" onblur="setClientValue();"  size="30"  maxlength="50">
		<input type="hidden" name="client_name" id="client_id" value="">
		<div id="clientsTip" style="height:12px;position:absolute;left:276px; top:85px; width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		</td>	
		<td class="a1">不显示0往来单位</td>
		<td class="a4">	
			<input type="radio" name="flag" id="flag" value="是"/>是&nbsp;&nbsp;
			<input type="radio" name="flag" id="flag" value="否" checked/>否			
		</td>					
	</tr>	
	<tr>
		<td class="a1">不显示余额为0往来单位</td>
		<td class="a4" colspan="3">
			<input type="radio" name="flag2" id="flag2" value="是" checked/>是&nbsp;&nbsp;
			<input type="radio" name="flag2" id="flag2" value="否"/>否				
		</td>	
	</tr>
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="doSubmit();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">
		</td>
	</tr>
</table>
</form>
</body>
</html>
