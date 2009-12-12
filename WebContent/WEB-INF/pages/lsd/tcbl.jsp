<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>提成比例设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script type="text/javascript">	
	//检查表单输入情况
	function subForm(){	
		if(!InputValid(document.getElementById("basic_ratio"),1,"float",1,0,99,"基本提成比例")){ return false; }	
		if(!InputValid(document.getElementById("out_ratio"),1,"float",1,0,99,"超限提成比例")){ return false; }	
		if(!InputValid(document.getElementById("ds_ratio"),1,"float",1,0,99,"低于零售限价时点杀比例")){ return false; }	
		document.lssdForm.submit();
	}
</script>
</head>
<body>
<form name="lssdForm" action="saveTcbl.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">提成比例设置</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">基本提成比例</td>
		<td class="a2" width="35%">
			<ww:textfield name="basic_ratio" id="basic_ratio" value="%{tcblMap.basic_ratio}" theme="simple" />%<span style="color:red">*</span>
		</td>			
	</tr>
	<tr>
		<td class="a1" width="15%">超限提成比例</td>
		<td class="a2" width="35%">
			<ww:textfield name="out_ratio" id="out_ratio" value="%{tcblMap.out_ratio}" theme="simple" />%<span style="color:red">*</span>
		</td>			
	</tr>
	<tr>
		<td class="a1" width="15%">低于零售限价时点杀比例</td>
		<td class="a2" width="35%">
			<ww:textfield name="ds_ratio" id="ds_ratio" value="%{tcblMap.ds_ratio}" theme="simple" />%<span style="color:red">*</span>
		</td>			
	</tr>					
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="btnTg" value="确 定" class="css_button2" onclick="subForm();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
