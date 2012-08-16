<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>积分查询</title>
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
		if(document.getElementById("con_vl").value == ""){
			alert("会员卡号/手机号码 不能为空，请填写。");
			return;
		}
	   document.reportForm.submit();
	}
</script>
</head>
<body>
<form name="reportForm" action="getJfcxResult.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<thead>
	<tr>
		<td colspan="2">会员积分查询</td>
	</tr>
	</thead>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0" border="0" id="selTable">
	<tr>
		<td class="a1" width="20%">会员卡号/手机号码</td>
		<td class="a4" width="80%">
			<input type="text" name="con_vl" id="con_vl" style="width:232px" value="" /> <font color="red">*</font>
		</td>		
	</tr>
	<tr>
		<td class="a1">积分周期</td>
		<td class="a4">
			<input type="text" name="start_date" id="start_date" value=""  class="Wdate"  onFocus="WdatePicker()" /> 至 
			<input type="text" name="end_date" id="end_date" value=""  class="Wdate"  onFocus="WdatePicker()" /></td>
	</tr>
	<tr>
		<td class="a1"  colspan="2">
			<input type="button" name="button1" value="提 交" onclick="submits()" class="css_button2" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2" />
		</td>
	</tr>
</table>
</form>
</body>
</html>