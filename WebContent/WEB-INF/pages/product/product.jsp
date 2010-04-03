<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
<title>商品维护</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>

<body align="center">
<table width="100%" height="495" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td width="25%">
			<table width="100%" border="0" height="100%" cellpadding="0" cellspacing="0" class="chart_list">
			  <tr>
			    <td width="100%" class="a2">
					<iframe width="100%" height="100%" name="kindFrame" allowTransparency="true" src="kind_list.html" border="0" frameborder="0" SCROLLING="yes"></iframe>
				</td>
			  </tr>
			</table>
		</td>
		<td width="75%">
			<table width="100%" border="0" height="100%" cellpadding="0" cellspacing="0">
			  <tr>
			    <td width="100%">
					<iframe width="100%" height="100%" name="right" allowTransparency="true" src="product_list.html" border="0" frameborder="0" SCROLLING="auto"></iframe>
				</td>
			  </tr>
			</table>			
		</td>
	</tr>
</table>

</body>
</html>
