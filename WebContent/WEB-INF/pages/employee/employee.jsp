<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>员工管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="jquery/jquery.js"></script>
<script type="text/javascript" src="js/initPageSize.js"></script>
</head>

<body align="center">
<div class="rightContentDiv" id="divContent">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td width="25%">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="chart_list">
			  <tr>
			    <td width="100%" class="a2">
					<iframe width="100%" height="600" name="deptFrame" src="listDept.html" border="0" frameborder="0" SCROLLING="auto"></iframe>
				</td>
			  </tr>
			</table>
		</td>
		<td width="75%">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  <tr>
			    <td width="100%">
					<iframe width="100%" height="600" name="right" src="listEmployee.html" border="0" frameborder="0" SCROLLING="auto"></iframe>
				</td>
			  </tr>
			</table>			
		</td>
	</tr>
</table>
</div>
</body>
</html>
