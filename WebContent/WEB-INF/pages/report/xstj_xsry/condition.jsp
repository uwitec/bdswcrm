<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.sw.cms.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>业务员销售汇总</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<style>
	.selectTip{background-color:#009;color:#fff;}
</style>
<script language='JavaScript' src="js/selJsr.js"></script>
<script language='JavaScript' src="js/selClient.js"></script>
<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type="text/javascript">

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
<script language="JavaScript" type="text/javascript" src="datepicker/WdatePicker.js"></script>
</head>
<body onload="initFzrTip();initClientTip();">
<form name="reportForm" action="getXstjXsryResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">业务员销售汇总</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="0" id="selTable">
	<tr>
		<td class="a1" width="15%">开始日期</td>
		<td class="a4" width="35%">
			<input type="text" name="start_date" id="start_date" value="<%=DateComFunc.getToday() %>"  class="Wdate" style="width:232px" onFocus="WdatePicker()"></td>
		<td class="a1" width="15%">结束日期</td>
		<td class="a4" width="35%">
			<input type="text" name="end_date" id="end_date" value="<%=DateComFunc.getToday() %>"  class="Wdate" style="width:232px" onFocus="WdatePicker()"></td>
	</tr>
	<tr>
		<td class="a1">客户名称</td>
		<td class="a4">
			<input type="text" name="client_name" id="client_name" value=""  onblur="setClientValue();" style="width:232px" maxlength="50">
			<input type="hidden" name="clientId" id="client_id" value="">
			<div id="clientsTip" style="position:absolute;width:300px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" ></div>			
		</td>	
		<td class="a1">显示商品明细</td>
		<td class="a4">
			<input type="radio" name="flag" id="flag" value="1"> 是
			<input type="radio" name="flag" id="flag" value="0" checked> 否
		</td>		
	</tr>
	<tr>
		<td class="a1">销售人员</td>
		<td class="a4">
		    <input  id="brand" type="text"   length="20" style="width:232px" onblur="setValue()"  /> 
            <div   id="brandTip"  style="position:absolute;width:132px;border:1px solid #CCCCCC;background-Color:#fff;display:none;" >
            </div>
		    <input type="hidden" name="xsry_id" id="fzr"  /> 
		</td>	
		<td class="a1">业务类型</td>
		<td class="a4">
			<input type="checkbox" name="xwType" id="xwType" value="1" checked> 销售订单
			<input type="checkbox" name="xwType" id="xwType" value="2" checked> 零售单
			<input type="checkbox" name="xwType" id="xwType" value="3" checked> 退货单
		</td>				
	</tr>	
	<tr>
		<td class="a1">单据编号</td>
		<td class="a4">
			<input type="text" name="dj_id" id="dj_id" value="" style="width:232px">
		</td>
		<td class="a1">显示销量为0业务员</td>
		<td class="a4">
			<select name="isShowZ" style="width:232px">
				<option value="否">否</option>
				<option value="是">是</option>
			</select>
		</td>			
	</tr>		

	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交"  onclick="submits()" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">
		</td>
	</tr>
</table>
</form>
</body>
</html>