<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="com.opensymphony.xwork.util.OgnlValueStack" %>
<%@ page import="com.sw.cms.util.StringUtils" %>
<%@ page import="java.util.*" %>
<%
OgnlValueStack VS = (OgnlValueStack)request.getAttribute("webwork.valueStack");

Map accountMap = (Map)VS.findValue("accountMap");

String id = StringUtils.nullToStr(accountMap.get("id"));
String name = StringUtils.nullToStr(accountMap.get("name"));

%>
<html>
<head>
<title>账号初始</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/css.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/Check.js"></script>
<script language='JavaScript' src="js/date.js"></script>
<script type="text/javascript">
	function saveInfo(){

		if(!InputValid(document.getElementById("qcje"),1,"float",1,0,9999999999,"初始金额")){	 return; }
		
		if(window.confirm("确认要保存初始金额吗？保存后将不能修改。")){
			document.accountForm.submit();
		}
		
	}	
</script>
</head>
<body oncontextmenu="return false;" >
<form name="accountForm" action="initAccountJe.html" method="post">
<input type="hidden" name="account_id" value="<%=id %>">
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">
	<thead>
	<tr>
		<td colspan="4">账户初始</td>
	</tr>
	</thead>
	<tr>
		<td class="a1" width="15%">账户名称</td>
		<td class="a2" width="35%"><input type="text" name="accounts.name" id="name" value="<%=name %>" readonly></td>	
	</tr>
	<tr>
		<td class="a1" width="15%">初始金额</td>
		<td class="a2" width="35%"><input type="text" name="qcje" id="qcje" value=""></td>		
	</tr>	
</table>
<table width="100%"  align="center"  class="chart_info" cellpadding="0" cellspacing="0">	
	<tr height="35">
		<td class="a1" colspan="2">
			<input type="button" name="button1" value="确 定" class="css_button2" onclick="saveInfo();">&nbsp;&nbsp;
			<input type="button" name="button3" value="关 闭" class="css_button2" onclick="window.close();">
		</td>
	</tr>
</table>

</form>
</body>
</html>
