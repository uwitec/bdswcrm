<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.sw.cms.util.*" %>
<%@ page import="java.util.*" %>
<%
List msgList = (List)session.getAttribute("messages");
session.invalidate();
String message = "";
if(msgList != null && msgList.size()>0){
	message = StringUtils.nullToStr(msgList.get(0));
}
%>
<html>
<head>
<title>用户登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function chklogin(){
	if(document.myform.user_name.value==""){
		alert("用户名不能为空，请输入！");
		return;
	}
	if(document.myform.password.value==""){
		alert("密码不能为空，请输入！");
		return;	
	}	
	if(document.myform.authNums.value==""){
		alert("验证码不能为空，请输入！");
		return;	
	}
	if(document.myform.user_name.value.indexOf("'")>=0||document.myform.user_name.value.indexOf("=")>=0||document.myform.password.value.indexOf("'")>=0||document.myform.password.value.indexOf("=")>=0){
		alert("用户名或密码含有非法字符，请检查！");
		return;
	}	
	document.myform.submit();
}

function f_enter(){
    if (window.event.keyCode==13){
        chklogin();
    }
}

function chgImage(){
	document.getElementById("imgCheck").src = "showImage?" + Math.random();
	document.getElementById("authNums").focus();
}
</script>
</head>
<body oncontextmenu="return false;" style="margin-top:100px;" onload="document.myform.user_name.focus();">
<form name="myform" action="login.html" method="post">
<input name="relogin_flage" type="hidden" value="1">
<table width="400" height="30"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td><B>用户登录</B></td>
	</tr>
	</thead>
</table>
<table width="400" height="200"  align="center"  class="chart_index" cellpadding="0" border="0" cellspacing="0">
	<tr><td class="a1" colspan="3"><font color="red"><%=message %></font></td></tr>
	<tr>
		<td class="a1">用户名：</td>
		<td class="a2" colspan="2"><input type="text" name="user_name" style="width:50%" value=""></td>	
	</tr>
	<tr>	
		<td class="a1">密　码：</td>
		<td class="a2" colspan="2"><input type="password" name="password" style="width:50%" value=""></td>
	</tr>
	<tr>
		<td class="a1" width="30%">验证码：</td>
		<td class="a2" width="40%"><input type="text" name="authNums" id="authNums" value="" onKeyPress="f_enter();" style="width:90%" ></td>
		<td class="a2" width="30%"><img alt="验证码" id="imgCheck" src="showImage" title="点击换一张" onclick="chgImage();" style="cursor: hand"></td>
	</tr>
	<tr>
		<td colspan="3" class="a1"><BR><BR>
			<input type="button" name="button" value=" 强制登录 " class="css_button2" onclick="chklogin();">
			<input type="reset" name="button2" value=" 重填 " class="css_button2"><BR><BR>
		</td>
	</tr>	
</table>
<BR>
</form>
</body>
</html>
