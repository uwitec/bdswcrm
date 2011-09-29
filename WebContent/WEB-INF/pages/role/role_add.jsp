<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.model.Page" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="com.sw.cms.model.*" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加采购应付款</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.roleForm.role_name,1,"string",1,1,25,"角色名称")){	 return; }
		
		if(!InputValid(document.roleForm.xh,0,"int",0,0,999,"序号")){	 return; }
		
		document.roleForm.submit();
	}
</script>
</head>
<body oncontextmenu="return false;" >
<form name="roleForm" action="saveRole.html" method="post">
<table width="90%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">角色信息</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="25%">角色名称</td>
		<td class="a2" width="75%">
			<input type="text" name="role_name" value="">
		</td>		
	</tr>	
	
	<tr>
		<td class="a1" width="25%">排序号</td>
		<td class="a2" width="75%"><input type="text" name="xh" value=""></td>
	</tr>	
	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button1" value="返 回" class="css_button2" onclick="history.go(-1);">
		</td>
	</tr>
</table>

</form>
</body>
</html>
