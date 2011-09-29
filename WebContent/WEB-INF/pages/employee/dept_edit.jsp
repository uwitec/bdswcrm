<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>部门管理管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type='text/javascript' src='dwr/interface/dwrService.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type="text/javascript">
	//保存部门信息
	function saveInfo(){
		if(!InputValid(document.getElementById("dept_name"),1,"string",1,1,25,"序列号")){	 return; }
		document.deptForm.submit();
	}
</script>
</head>
<body>
<form name="deptForm" action="updateDept.html" method="post">
<ww:hidden name="dept.parent_id" id="parent_id" value="%{dept.parent_id}" theme="simple"/>
<ww:hidden name="dept.dept_id" id="dept_id" value="%{dept.dept_id}" theme="simple"/>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">部门管理</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="25%">部门名称</td>
		<td class="a2" width="75%">
			<ww:textfield name="dept.dept_name" id="dept_name" value="%{dept.dept_name}" theme="simple" onblur="sendSerialNum();"/>
			<span id="msg" style="color:red"></span>
		</td>		
	</tr>
	<tr>
		<td class="a1" width="25%">部门负责人</td>
		<td class="a2" width="75%">
			<ww:textfield  name="dept.fzr" id="fzr" value="%{dept.fzr}" theme="simple" maxLength="10"/>
		</td>
	</tr>
	<tr>
		<td class="a1" width="25%">联系电话</td>
		<td class="a2" width="75%"><ww:textfield  name="dept.phone" id="phone" value="%{dept.phone}"  theme="simple" maxLength="20"/></td>
	</tr>	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="提 交" class="css_button" onclick="saveInfo();">&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button">&nbsp;&nbsp;
			<input type="button" name="button1" value="关 闭" class="css_button" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
