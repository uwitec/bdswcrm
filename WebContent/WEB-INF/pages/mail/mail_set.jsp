<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@taglib uri="/webwork" prefix="ww"%>
<html>
<head>
<title>发件箱设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script type="text/javascript">
	//保存发件邮箱设置
	function saveInfo(){
		if(!InputValid(document.getElementById("smtp"),1,"string",1,1,100,"发送邮件服务器（SMTP）")){	 return; }
		if(!InputValid(document.getElementById("user_name"),1,"string",1,1,50,"账户名")){	 return; }
		if(!InputValid(document.getElementById("password"),1,"string",1,1,20,"密码")){	 return; }
		if(!InputValid(document.getElementById("confirm_pass"),1,"string",1,1,20,"密码确认")){	 return; }
		if(document.getElementById("confirm_pass").value != document.getElementById("password").value){
			alert("密码确认与密码不同！");
			return;
		}
		if(!InputValid(document.getElementById("from_user"),1,"email",1,1,100,"发件人")){	 return; }
		
		document.myform.submit();
	}
</script>
</head>
<body>
<form name="myform" action="updateMailSet.html" method="post">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">发件箱设置</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="30%">发送邮件服务器（SMTP）</td>
		<td class="a2" width="70%">
			<ww:textfield name="mailSet.smtp" id="smtp" value="%{mailSet.smtp}" theme="simple" size="35"/>
			<span style="color:red">*</span>
		</td>		
	</tr>
	<tr>
		<td class="a1">账户名</td>
		<td class="a2">
			<ww:textfield  name="mailSet.user_name" id="user_name" value="%{mailSet.user_name}" theme="simple" size="35" />
			<span style="color:red">*</span>
		</td>
	</tr>
	<tr>
		<td class="a1">密码</td>
		<td class="a2">
			<ww:password name="mailSet.password" id="password" showPassword="true" value="%{mailSet.password}"  theme="simple"/>
			<span style="color:red">*</span>	
		</td>
	</tr>
	<tr>
		<td class="a1">密码确认</td>
		<td class="a2">
			<ww:password name="confirm_pass" id="confirm_pass" showPassword="true" value="%{mailSet.password}"  theme="simple"/>
			<span style="color:red">*</span>
		</td>
	</tr>	
	<tr>
		<td class="a1">发件人</td>
		<td class="a2">
			<ww:textfield  name="mailSet.from_user" id="from_user" value="%{mailSet.from_user}" theme="simple"  size="35"/>	
			<span style="color:red">*</span>	
		</td>		
	</tr>
	<tr>
		<td class="a1">此服务器要求安全连接（SSL）</td>
		<td class="a2">
			<ww:select list="#{'false':'否','true':'是'}" name="mailSet.is_ssl" id="is_ssl" theme="simple"></ww:select>
		</td>		
	</tr>
	<tr>
		<td class="a1">服务器端口（SMTP）</td>
		<td class="a2">
			<ww:textfield  name="mailSet.port_num" id="port_num" value="%{mailSet.port_num}" theme="simple"  size="35"/>		
		</td>		
	</tr>
	
	<tr>
		<td class="a1">备注标签</td>
		<td class="a2">
			<ww:textarea  cols="30"  rows="5" name="mailSet.remark" id="remark" value="%{mailSet.remark}" theme="simple" ></ww:textarea> 		
		</td>		
	</tr>	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value=" 提交 " class="css_button2" onclick="saveInfo();">&nbsp;
			<input type="reset" name="button2" value=" 重置 " class="css_button2">&nbsp;&nbsp;
			<input type="button" name="button1" value=" 关闭 " class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>
</form>
</body>
</html>
