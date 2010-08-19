<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>系统提示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body align="left">
<table width="50%" height="495" border="0" align="left" cellpadding="0" cellspacing="0">
	<tr>
		<td width="25%">
		  <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
	        <tr>
		      <td  width="100%">
			    <ww:property value="%{msg}"/><BR><BR><BR>			
			    <input type="button" name="button" value=" 返回 " class="css_button2" onclick="history.go(-1);">
		      </td>
	       </tr>
         </table>
		</td>
	</tr>
</table>
</body>
</html>


