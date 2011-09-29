<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>费用申请审批设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form name="myform" action="saveFysqSpRight.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>费用申请审批设置</b>
		</td>		
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="a1" width="20%">是否需要审批</td>
		<td class="a4" width="80%">
			<ww:select name="sp_flag" id="sp_flag" theme="simple" list="#{'01':'是','00':'否'}"  emptyOption="true" value="%{sp_flag}"></ww:select>
			<font color="red"><ww:property value="%{msg}"/></font>	
		</td>
	</tr>
	<tr>
		<td class="a1">审批权限角色名称</td>
		<td class="a4">
			<ww:checkboxlist list="roleList" name="role_id" id="role_id" listKey="role_id" listValue="role_name" theme="simple" value="%{role_id}"></ww:checkboxlist>
		</td>
	</tr>
	<tr height="40">
		<td class="a1" colspan="3">
			<input type="submit" name="button1" value="保 存" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>	
</table><BR>
注：设置费用申请是否需要审批，如果需要审批请选择“是”，否则选则“否”，选择“是”后需选择相应角色才能生效。
</form>
</body>
</html>
