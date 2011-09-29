<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.StringUtils" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加商品</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.myform.kindName,1,"string",1,1,25,"商品类别名称")){	 return; }
		if(!InputValid(document.myform.ms,0,"string",0,1,500,"商品类别描述")){	 return; }

		window.opener.document.productKindForm.name.value = document.myform.kindName.value;
		window.opener.document.productKindForm.ms.value = document.myform.ms.value;
		
		window.opener.document.productKindForm.action = "saveProductKind.html";
		window.opener.document.productKindForm.submit();
		
		window.close();
	}
</script>
</head>
<body>
<form name="myform">

<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">添加商品类别</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">商品类别名称</td>
		<td class="a2" width="35%"><input type="text" name="kindName" value=""></td>
	</tr>
	<tr height="50">
		<td class="a1" width="15%">商品类别描述</td>
		<td class="a2" width="35%">
			<textarea rows="4" style="width:80%;" name="ms"></textarea>
		</td>
	</tr>

	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="保 存" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>
