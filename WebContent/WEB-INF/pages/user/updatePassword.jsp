<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.StringUtils" %>
<%@ page import="java.util.*" %>
<%@ page import="com.sw.cms.model.*" %>

<%

LoginInfo loginInfo = (LoginInfo)session.getAttribute("LOGINUSER");

String msg = StringUtils.nullToStr(session.getAttribute("MSG"));
session.removeAttribute("MSG");

%>

<html>
<head>
<title>修改密码</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){
		if(!InputValid(document.userForm.curPass,1,"string",1,1,20,"当前密码")){	 return; }
		if(!InputValid(document.userForm.newPass,1,"string",1,1,20,"新密码")){	 return; }
		if(!InputValid(document.userForm.newPass,1,"string",1,1,20,"新密码确认")){	 return; }
		
		if(document.userForm.newPass.value != document.userForm.newPass2.value){
			alert("新密码与密码确认不同，请检查！");
			return;
		}
	
		document.userForm.submit();
	}
	
</script>
</head>
<body >
<form name="userForm" action="savePass.html" method="post">
<input type="hidden" name="user_name" value="<%=loginInfo.getUser_name() %>">

<BR>
<center>
<font color="red"><%=msg %></font>
</center>
<table width="50%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">修改密码</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="25%">当前密码</td>
		<td class="a2" width="75%"><input type="password" name="curPass" value=""><font color="red">*</font></td>
	</tr>
	<tr>
		<td class="a1" width="25%">新密码</td>
		<td class="a2" width="75%"><input type="password" name="newPass" value=""><font color="red">*</font></td>
	</tr>	
	<tr>
		<td class="a1" width="25%">新密码确认</td>
		<td class="a2" width="75%"><input type="password" name="newPass2" value=""><font color="red">*</font></td>
	</tr>			
	<tr height="35">
		<td class="a1" colspan="4">
			<input type="button" name="button1" value="提 交" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="reset" name="button2" value="重 置" class="css_button2">
		</td>
	</tr>	
</table>
</form>
</body>
</html>
