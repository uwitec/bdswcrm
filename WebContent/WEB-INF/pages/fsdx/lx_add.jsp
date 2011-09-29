<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.StringUtils" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>账号资料</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
		
		 
			if(document.getElementById("name").value == ""){
				alert("联系人类型不能为空，请填写！");
				return;
			}
			document.accountForm.submit(); 
		}
		
		
		
	

	
	 
	
</script>
</head>
<body oncontextmenu="return false;" >
<form name="accountForm" action="savelx.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	
	<tr align="center">
	 
		<td class="a1"   width="15%">联系人类型</td>
		<td class="a2"   width="35%"><input type="text" name="lx.name" id="name" value=""></td>
		 
	</tr>
	 
</table>
<br>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	 
	
	<tr height="35">
		<td class="a1">  
			<input type="button" name="button1" value="确 定" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			 
		</td>
	</tr>
</table>

</form>
</body>
</html>