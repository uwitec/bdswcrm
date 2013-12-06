<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>设置零售税点</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script type="text/javascript">	
	//检查表单输入情况
	function subForm(){	
		if(!InputValid(document.getElementById("sd"),1,"float",1,0,99,"当前税点")){ return false; }	
		
		document.lssdForm.submit();
	}
</script>
</head>
<body>
<form name="lssdForm" action="saveLssd.html" method="post">
<table width="100%"  align="center"class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="100%">&nbsp;&nbsp;&nbsp;&nbsp;<b>设置零售税点</b></td>			
	</tr>
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<tr>
		<td class="a1" width="15%">当前税点</td>
		<td class="a2" width="85%">
			<ww:textfield name="sd" id="sd" value="%{sd}" theme="simple" /><span style="color:red">*</span>
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
