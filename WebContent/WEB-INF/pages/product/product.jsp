<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>商品维护</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>

<body align="center">
<table width="99%"border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td width="25%">
			<table width="100%" border="0"cellpadding="0" cellspacing="0" class="chart_list">
			  <tr>
			    <td width="100%" class="a2">
					<iframe width="100%" height="550" name="kindFrame" src="kind_list.html" border="0" frameborder="0" SCROLLING="yes"></iframe>
				</td>
			  </tr>
			</table>
		</td>
		<td width="75%">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  <tr>
			    <td width="100%">
					<iframe width="100%" height="550" name="right" src="product_list.html" border="0" frameborder="0" SCROLLING="auto"></iframe>
				</td>
			  </tr>
			</table>			
		</td>
	</tr>
</table>

</body>
</html>
