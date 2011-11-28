<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户经理应收汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>.selectTip{background-color:#009;color:#fff;}</style>
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
</script>
</head>
<body onload="initFzrTip();initClientTip();">
<form name="reportForm" action="getYshzKhjlResult.html" method="post">
<table width="100%" align="center" class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">客户经理应收汇总</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="0" id="selTable">
	<tr>
		<td class="a1" width="15%">起始日期</td>
		<td class="a4" width="35%">
			<input type="text" name="start_date" id="start_date" value="<%=DateComFunc.getToday() %>" class="Wdate" style="width:200px" onFocus="WdatePicker()"></td>
		<td class="a1" width="15%">结束日期</td>
		<td class="a4" width="35%">
			<input type="text" name="end_date" id="end_date" value="<%=DateComFunc.getToday() %>" class="Wdate" style="width:200px" onFocus="WdatePicker()"></td>				
	</tr>
	<tr>
		<td class="a1">客户名称</td>
		<td class="a4">
		<input type="text" name="cl" id="client_name" value="" onblur="setClientValue();" style="width:200px" maxlength="50">
		<input type="hidden" name="client_name" id="client_id" value="">
		<div id="clientsTip" style="position:absolute;width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		</td>	
		<td class="a1">客户经理</td>
		<td class="a4">
		    <input id="brand" type="text" maxlength="20" style="width:200px" onblur="setValue()"  /> 
            <div id="brandTip" style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		    <input type="hidden" name="khjl" id="fzr"  /> 
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
