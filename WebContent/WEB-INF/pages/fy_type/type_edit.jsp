<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>编辑费用类别</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script type="text/javascript">
	//更新
	function saveInfo(){
		if(!InputValid(document.getElementById("name"),1,"string",1,1,50,"名称")){	 return; }
		if(!InputValid(document.getElementById("ms"),0,"string",0,1,100,"描述")){	 return; }
		document.myform.submit();
	}
</script>
</head>
<body>
<form name="myform" action="updateFyType.html" method="post">
<ww:hidden name="fyType.id" id="id" value="%{fyType.id}" theme="simple"/>
<ww:hidden name="fyType.parent_id" id="parent_id" value="%{fyType.parent_id}" theme="simple"/>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">费用类别</td>
	</tr>
	</thead>
	<ww:if test="msg != ''">
	<tr>
		<td class="a2" colspan="2"><FONT color="red"><ww:property value="%{msg}"/></FONT></td>
	</tr>	
	</ww:if>
	<tr>
		<td class="a1" width="25%">名称</td>
		<td class="a2" width="75%">
			<ww:textfield name="fyType.name" id="name" value="%{fyType.name}" theme="simple"/>
			<span id="msg" style="color:red"></span>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="25%">描述</td>
		<td class="a2" width="75%">
			<ww:textarea name="fyType.ms" id="ms" value="%{fyType.ms}" theme="simple" cssStyle="width:90%"></ww:textarea>
		</td>
	</tr>
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提交" class="css_button" onclick="saveInfo();">&nbsp;
			<input type="button" name="button1" value="关闭" class="css_button" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
