<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>考核成本价调整</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript">
	//更新成本
	function saveInfo(){
		if(!InputValid(document.myform.kh_cbj,0,"float",1,0,9999999,"修改后考核成本")){return; }
		document.myform.submit();
	}
</script>
</head>
<body>
<form name="myform" action="updateKhcb.html" method="post">
<ww:hidden name="id" id="id" value="%{id}" theme="simple"/>
<ww:hidden name="dj_type" id="dj_type" value="%{dj_type}" theme="simple"/>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">考核成本价调整</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="25%">商品编号</td>
		<td class="a2" width="75%"><ww:property value="%{productMap.product_id}"/>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="25%">产品名称</td>
		<td class="a2" width="75%"><ww:property value="%{productMap.product_name}"/></td>
	</tr>
	<tr>
		<td class="a1" width="25%">产品规格</td>
		<td class="a2" width="75%"><ww:property value="%{productMap.product_xh}"/></td>
	</tr>
	<tr>
		<td class="a1" width="25%">原考核成本</td>
		<td class="a2" width="75%"><ww:property value="%{productMap.kh_cbj}"/></td>		
	</tr>
	<tr>
		<td class="a1" width="25%">修改后考核成本</td>
		<td class="a2" width="75%">
			<ww:textfield theme="simple" name="kh_cbj" id="kh_cbj" value="0.00"/> 
		</td>		
	</tr>
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提交" class="css_button" onclick="saveInfo();">&nbsp;
			<input type="reset" name="button2" value="重置" class="css_button">&nbsp;&nbsp;
			<input type="button" name="button1" value="关闭" class="css_button" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
