<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>报表样式设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form name="myform" action="saveStyle.html" method="post">
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="csstitle" align="left" width="75%">&nbsp;&nbsp;&nbsp;&nbsp;<b>报表样式设置</b>
		</td>		
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_list" cellpadding="0" cellspacing="0">
	<tr>
		<td class="a1" width="20%">报表样式</td>
		<td class="a4" width="80%">
			<ww:select name="style_flag" id="style_flag" theme="simple" list="#{'01':'三分样式','00':'普通样式'}"  emptyOption="true" value="%{style_flag}"></ww:select>
			<font color="red"><ww:property value="%{msg}"/></font>	
		</td>
	</tr>	
	<tr height="40">
		<td class="a1" colspan="3">
			<input type="submit" name="button1" value="保 存" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>	
</table>
</form>
</body>
</html>
