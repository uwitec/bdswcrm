<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.sw.cms.util.*" %>
<html>
<head>
<title>客户销售汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
<script language='JavaScript' src="js/selJsr.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<style>
	.selectTip{
		background-color:#009;
		 color:#fff;
	}
</style>
<script type="text/javascript">
	function submits(){
	   document.reportForm.submit();
	}
</script>
</head>
<body onload="initFzrTip();initClientTip();">
<form name="reportForm" action="getXstjClientResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">客户销售汇总</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="1" id="selTable">
	<tr>
		<td class="a1">开始日期</td>
		<td class="a4">
			<input type="text" name="start_date" id="start_date" value="<%=DateComFunc.getToday() %>"  class="Wdate" onFocus="WdatePicker()"></td>
		<td class="a1">结束日期</td>
		<td class="a4">
			<input type="text" name="end_date" id="end_date" value="<%=DateComFunc.getToday() %>"  class="Wdate" onFocus="WdatePicker()"></td>
	</tr>
	<tr>
		<td class="a1">客户名称</td>
		<td class="a4">
		<input type="text" name="client_name" id="client_name" value="" onblur="setClientValue();"  size="30"  maxlength="50">
		<input type="hidden" name="cl " id="client_id" value="">
		<div id="clientsTip" style="height:12px;position:absolute;left:103px; top:85px; width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>
		</td>	
		<td class="a1">销售人员</td>
		<td class="a4">
		    <input  id="brand" type="text"   length="20"  onblur="setValue()"  /> 
            <div   id="brandTip"  style="height:12px;position:absolute;left:760px; top:87px; width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
            </div>
		    <input type="hidden" name="xsry_id" id="fzr"  /> 
		</td>			
	</tr>
	<tr>
		<td class="a1">单据编号</td>
		<td class="a4">
			<input type="text" name="dj_id" id="dj_id" value="" size="20">
		</td>
		<td class="a1">是否显示销售额为零客户</td>
		<td class="a4">
			<select name="isShowZ">
				<option value="否">否</option>
				<option value="是">是</option>
			</select>
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